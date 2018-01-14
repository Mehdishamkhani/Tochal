package org.android.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.android.R;
import org.android.adapter.HoursRecyclerViewAdapter;
import org.android.data.model.PeriodsModel;

import java.util.ArrayList;
import java.util.List;


public class DayWeatherFragment extends Fragment {

    public final static String DATA = "DATA";
    private HoursRecyclerViewAdapter mAdapter;

    public DayWeatherFragment() {

    }

    public static DayWeatherFragment getInstance(List<PeriodsModel> periodsModels, String type) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(DATA, (ArrayList<? extends Parcelable>) periodsModels);
        args.putString("TYPE", type);
        DayWeatherFragment dayWeatherFragment = new DayWeatherFragment();
        dayWeatherFragment.setArguments(args);
        return dayWeatherFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tabs, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.hour_list);
        try {
            @SuppressWarnings("ConstantConditions")
            List<PeriodsModel> data = getArguments().getParcelableArrayList(DATA);
            String type = getArguments().getString(getString(R.string.type));
            setupRecyclerView(recyclerView, type);
            addWeathersToList(data);
        } catch (RuntimeException e) {

            e.printStackTrace();
        }


        return view;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, String type) {

        mAdapter = new HoursRecyclerViewAdapter(getActivity(), new ArrayList<PeriodsModel>(), type);
        mAdapter.setListener(new HoursRecyclerViewAdapter.OnIconClickListener() {
            @Override
            public void onIconClick(PeriodsModel weather, View view) {
            }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void addWeathersToList(List<PeriodsModel> data) {
        mAdapter.addElements(data);
    }

    public void addWeatherToList(PeriodsModel weather) {
        mAdapter.addElement(weather);
    }


}