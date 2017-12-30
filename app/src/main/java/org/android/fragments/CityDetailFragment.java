package org.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.android.R;
import org.android.adapter.DaysViewPagerAdapter;
import org.android.data.model.DayPager;
import org.android.data.model.ForecastModel;
import org.android.data.model.PeriodsModel;
import org.android.data.WeatherType;
import org.android.util.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;


public class CityDetailFragment extends Fragment {

    private DaysViewPagerAdapter viewPagerAdapter;
    private FrameLayout progressFrame;
    private TabLayout tabLayout;
    private boolean waitAnimations;
    private PreferencesHelper pref;
    private ForecastModel data;
    private String type = WeatherType.MIN;

    public CityDetailFragment() {

    }

    public static CityDetailFragment getInstance(String type) {
        Bundle args = new Bundle();
        args.putString("TYPE", type);
        CityDetailFragment cdf = new CityDetailFragment();
        cdf.setArguments(args);
        return cdf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = PreferencesHelper.getInstance();
        Fresco.initialize(getActivity());

        String ds = pref.getJsonData(getActivity());

        this.type = getArguments().getString("TYPE");

        if (ds != null && !ds.equalsIgnoreCase(getString(R.string.empty))) {

            try {
                Gson gson = new GsonBuilder().create();
                data = gson.fromJson(ds, ForecastModel.class);

            } catch (JsonSyntaxException e) {

                e.printStackTrace();
            }

            makeView();
        }

    }


    private void makeView() {
        //Utility.sortWeatherHour(forecasts);
        if (data != null) {
            addDaysToViewPager(data);
        } else {
            showError();
        }
    }

    private void addDaysToViewPager(ForecastModel model) {
        List<DayPager> days = new ArrayList<>();
        DayPager day = new DayPager();
        day.setHours(new ArrayList<PeriodsModel>());
        day.setType(type);
        days.add(day);
        String lastDay = model.getForecast().getPeriods().get(0).plcdayname;

        for (PeriodsModel period : model.getForecast().getPeriods()) {

            if (lastDay.equalsIgnoreCase(period.plcdayname)) {
                day.getHours().add(period);
            } else {
                day = new DayPager();
                day.setHours(new ArrayList<PeriodsModel>());
                day.setType(type);
                day.getHours().add(period);
                days.add(day);
            }

            lastDay = period.plcdayname;

        }

        if (viewPagerAdapter != null) {
            viewPagerAdapter.addDays(days);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        FragmentManager fragmentManager = getChildFragmentManager();

        viewPagerAdapter = new DaysViewPagerAdapter(fragmentManager, new ArrayList<DayPager>(), data.getForecast().getGmtissued());
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.clear();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressFrame = (FrameLayout) getActivity().findViewById(R.id.progressFrame);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPagerAdapter.clear();
        tabLayout.setVisibility(View.VISIBLE);
        if (!waitAnimations && data != null) {
            loadContent();
        }
    }

    public void waitAnimations() {
        waitAnimations = true;
    }

    public void loadContent() {
        makeView();
        waitAnimations = false;
    }

    public void clearContent() {
        tabLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showProgressBar(boolean show) {
        if (progressFrame == null) {
            return;
        }
        if (show) {
            progressFrame.setVisibility(View.VISIBLE);
        } else {
            progressFrame.setVisibility(View.GONE);
        }
    }


    public void showError() {
        View view = getActivity().findViewById(R.id.viewpager);
        if (view != null) {
            String text = getResources().getString(R.string.error) + ": ";
            text += getResources().getString(R.string.failed_to_load_weather);
            Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
        }
    }
}
