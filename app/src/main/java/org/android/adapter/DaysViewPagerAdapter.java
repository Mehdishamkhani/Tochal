package org.android.adapter;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import org.android.data.model.DayPager;
import org.android.data.model.PeriodsModel;
import org.android.fragments.DayWeatherFragment;
import org.android.util.PersianDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DaysViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String date;
    private List<DayPager> mDays;

    public DaysViewPagerAdapter(FragmentManager fm, List<DayPager> days, String date) {
        super(fm);
        mDays = days;
        this.date = date;


    }

    public void addDay(DayPager day) {
        mDays.add(day);
        notifyDataSetChanged();
    }

    public void addDays(List<DayPager> days) {
        mDays.addAll(days);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        PeriodsModel periodsModel = mDays.get(position).getHours().get(0);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date();
        try {
            d = dt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PersianDate pd = new PersianDate();
        pd.getJalaliDate(d, periodsModel.pdom);
        String dow = PersianDate.displayDayofWeek(periodsModel.pdayname);
        return pd.FinalDay + "  \n" + dow;


    }

    @Override
    public Fragment getItem(int position) {
        List<PeriodsModel> periodsModel = mDays.get(position).getHours();
        return DayWeatherFragment.getInstance(periodsModel, mDays.get(position).getType());
    }

    @Override
    public int getCount() {
        return mDays != null ? mDays.size() : 0;
    }

    public void clear() {
        mDays.clear();
        notifyDataSetChanged();
    }
}
