package org.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.SupportMapFragment;

import org.android.R;
import org.android.fragments.feedback;
import org.android.fragments.map;
import org.android.fragments.services;
import org.android.fragments.weather;

/**
 * Created by mehdi on 04/12/2017.
 */


public  class ViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return feedback.newInstance();
            case 1:
                return weather.newInstance();
            case 2:
                return map.newInstance();
            case 3:
                return services.newInstance();
            default:
                return services.newInstance();


        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}