package org.android.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.android.R;
import org.android.adapter.ImageViewPagerAdapter;
import org.android.adapter.ItemRecyclerViewAdapter;
import org.android.adapter.WorkItemRecyclerViewAdapter;
import org.android.data.model.Item;
import org.android.data.model.PlaceModel;
import org.android.data.model.PlaceStatus;
import org.android.data.model.PlacesModel;
import org.android.data.model.TimeModel;
import org.android.data.model.WorkModel;
import org.android.dialogs.DescDialog;
import org.android.dialogs.GalleryDialog;
import org.android.dialogs.ListDialog;
import org.android.rest.MyNetworkListener;
import org.android.rest.NetworkExceptionHandler;
import org.android.rest.RequestRepository;
import org.android.util.PersianDate;
import org.android.util.Place;
import org.android.util.TimeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class services extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.stations)
    RecyclerView stations;

    @BindView(R.id.appbar_head)
    TextView mainhead;


    @BindView(R.id.appbar_subhead)
    TextView subhead;


    @BindView(R.id.txtstatus)
    TextView txtstatus;

    @BindView(R.id.cd)
    CountdownView cdw;

    @BindView(R.id.detail)
    ImageView detail;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.time)
    ImageView time;

    @BindView(R.id.contact)
    ImageView contact;

    @BindView(R.id.rf)
    SwipeRefreshLayout rf;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private ItemRecyclerViewAdapter adp;

    String phone;
    String description;
    private ArrayList<WorkModel> work;
    private int place_id = 0;


    public services() {
    }

    public static services newInstance() {
        return new services();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        ButterKnife.bind(this, view);

        rf.setOnRefreshListener(this);
        rf.setColorSchemeResources(R.color.colorAccent);
        detail.setOnClickListener(this);
        photo.setOnClickListener(this);
        contact.setOnClickListener(this);
        time.setOnClickListener(this);


        cdw.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {

                refreshOperation();
            }
        });

        adp = new ItemRecyclerViewAdapter(getActivity(), new ArrayList<Item>(), collapsingToolbarLayout);
        stations.setAdapter(adp);
        adp.clear();
        adp.notifyDataSetChanged();
        adp.setListener(new ItemRecyclerViewAdapter.OnIconClickListener() {
            @Override
            public void onIconClick(int pos, Item itm, View view) {

                mainhead.setText(itm.getPlaceModel().getName());
                phone = itm.getPlaceModel().getPhone_number();
                description = itm.getPlaceModel().getDescription();
                work = itm.getPlaceModel().getWork_times();
                place_id = itm.getPlaceModel().getId();

                int day = PersianDate.persianDayOfWeek();
                WorkModel todayWork = work.get(day);

                PlaceStatus dps = Place.getPlaceStatus(todayWork);

                TimeModel timeModel;
                String statusText;
                if (dps.isMaintenance()) {
                    subhead.setVisibility(View.VISIBLE);
                    subhead.setText(dps.getMaintenanceDesc());
                    timeModel = TimeHelper.compareDates(todayWork.maintenance_start, todayWork.maintenance_end);
                    statusText = getString(timeModel.getType() == 1 ? R.string.place_status1 : R.string.place_status2);

                } else {
                    subhead.setVisibility(View.GONE);
                    timeModel = TimeHelper.compareDates(todayWork.open_time, todayWork.close_time);
                    statusText = getString(timeModel.getType() == 1 ? R.string.place_status1 : R.string.place_status2);

                }

                txtstatus.setText(statusText);
                cdw.start(timeModel.getRemainingTime());
                collapsingToolbarLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), (dps.isOpen()) ? R.color.green_bg : R.color.red_bg));


            }
        });


        RequestRepository rr = new RequestRepository(getActivity(), services.class.getSimpleName());
        rr.getPlaces(new services.getPlacesResult(services.this));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        this.refreshOperation();
    }

    private void refreshOperation() {

        adp.clear();
        adp.notifyDataSetChanged();
        adp.setFirstSelected(-1);
        adp.setLastSelected(-1);
        rf.setRefreshing(true);
        RequestRepository rr = new RequestRepository(getActivity(), services.class.getSimpleName());
        rr.getPlaces(new services.getPlacesResult(services.this));
    }


    private class getPlacesResult implements MyNetworkListener<PlacesModel> {
        private getPlacesResult(services services) {
        }

        @Override
        public void getResult(PlacesModel result) {


            //default   ###IS POS 0 ###
            PlaceModel def = result.getData().get(0);
            mainhead.setText(def.getName());
            phone = def.getPhone_number();
            description = def.getDescription();
            work = def.getWork_times();
            place_id = def.getId();

            // get today work time
            int day = PersianDate.persianDayOfWeek();
            WorkModel todayWork = work.get(day);

            // get today place status
            PlaceStatus dps = Place.getPlaceStatus(todayWork);

            // handle maintenance mode
            TimeModel timeModel;
            String statusText;
            if (dps.isMaintenance()) {
                subhead.setVisibility(View.VISIBLE);
                subhead.setText(dps.getMaintenanceDesc());
                timeModel = TimeHelper.compareDates(todayWork.maintenance_start, todayWork.maintenance_end);
                statusText = getString(R.string.place_status3);
            } else {
                timeModel = TimeHelper.compareDates(todayWork.open_time, todayWork.close_time);
                statusText = getString(timeModel.getType() == 1 ? R.string.place_status1 : R.string.place_status2);
            }

            cdw.start(timeModel.getRemainingTime());
            txtstatus.setText(statusText);
            collapsingToolbarLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), (dps.isOpen()) ? R.color.green_bg : R.color.red_bg));


            int typeid = 0;
            int radius = Item.NO_RADIUS;
            int i = 0;

            for (PlaceModel place : result.getData()) {

                WorkModel placeWorktime = place.getWork_times().get(day);


                if (typeid != place.getPlace_type_id()) {

                    addItem(new Item(place, true));
                    radius = Item.TOP_RADIUS;

                }

                try {

                    if (result.getData().size() == i + 1) {

                        if (radius == Item.NO_RADIUS)
                            radius = Item.BOT_RADIUS;
                        else
                            radius = Item.RADIUS;
                    } else {

                        if (radius == Item.NO_RADIUS
                                && result.getData().size() != i + 1 && !place.getPlace_type_label().equalsIgnoreCase(result.getData().get(i + 1).getPlace_type_label()))
                            radius = Item.BOT_RADIUS;

                        else if (radius == Item.TOP_RADIUS && !place.getPlace_type_label().equalsIgnoreCase(result.getData().get(i + 1).getPlace_type_label()))
                            radius = Item.RADIUS;

                    }

                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }


                addItem(new Item(place, Place.getPlaceStatus(placeWorktime).isOpen() ? 1 : 0, false, radius));

                i++;
                radius = Item.NO_RADIUS;
                typeid = place.getPlace_type_id();
            }


            adp.setFirstSelected(1);

            // store new places data
            Gson gson = new Gson();
            String json = gson.toJson(result);
            PreferenceManager.getDefaultSharedPreferences(

                    getActivity()).

                    edit()
                    .

                            putString(getString(R.string.place_data_key), json).

                    apply();

            rf.setRefreshing(false);
        }

        @Override
        public void getException(NetworkExceptionHandler error) {

            Toast.makeText(getActivity(), error.error_fa_message, Toast.LENGTH_LONG).show();
            rf.setRefreshing(false);

        }

    }

    public void addItem(Item item) {
        adp.addElement(item);
    }

    public void addItems(List<Item> items) {
        adp.addElements(items);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.detail:

                new DescDialog(getActivity(), "", description).show();

                break;

            case R.id.photo:

                final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_gallery);

                final ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(getActivity(), place_id);
                final ViewPager pager = dialog.findViewById(R.id.slider);
                TextView title = dialog.findViewById(R.id.title);
                final ImageView back = dialog.findViewById(R.id.back);
                final ImageView forward = dialog.findViewById(R.id.forward);

                pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (pager.getCurrentItem() - 1 == -1)
                            back.setAlpha(0f);
                        else
                            back.setAlpha(1f);

                        if (pager.getCurrentItem() + 1 == adapter.getCount())
                            forward.setAlpha(0f);
                        else
                            forward.setAlpha(1f);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pager.setCurrentItem(pager.getCurrentItem() - 1);
                    }
                });

                forward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    }
                });

                pager.setAdapter(adapter);
                dialog.show();

                break;

            case R.id.contact:

                if (phone != null) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                }

                break;

            case R.id.time:

                new ListDialog(getActivity(), work).show();
                final Dialog listdialog = new Dialog(getActivity(), R.style.Theme_Dialog);
                listdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                listdialog.setContentView(R.layout.dialog_list);

                final RecyclerView list = listdialog.findViewById(R.id.times);
                TextView listtitle = listdialog.findViewById(R.id.title);
                //final ImageView refresh = listdialog.findViewById(R.id.ref);


                if (this.work != null) {

                    final WorkItemRecyclerViewAdapter listadapter = new WorkItemRecyclerViewAdapter(getActivity(), new ArrayList<Item>());
                    list.setAdapter(listadapter);
                    listadapter.clear();
                    listadapter.notifyDataSetChanged();

                    String lastHead = "defaultHead";

                    for (WorkModel workModel : work) {

                        if (!lastHead.equalsIgnoreCase(workModel.day_title))
                            listadapter.addElement(new Item(workModel, true));

                        listadapter.addElement(new Item(workModel, false));

                        lastHead = workModel.day_title;

                    }


                }

                break;

        }
    }


}
