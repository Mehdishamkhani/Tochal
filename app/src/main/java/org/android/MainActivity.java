package org.android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.android.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    //@Bind(R.id.frm)
    //FrameLayout frm;

    @BindView(R.id.bnve)
    BottomNavigationViewEx bnve;

    @BindView(R.id.frm)
    ViewPager vp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        // Fragment fragment = feedback.newInstance();
        //getSupportFragmentManager().beginTransaction().replace(R.id.frm,fragment).commit();
        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(vpa);
        vp.setCurrentItem(3);
        bnve.setCurrentItem(3);
        try {

            Typeface tp = Typeface.createFromAsset(getResources().getAssets(), "fonts/isans.ttf");
            bnve.setTypeface(tp);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setupWithViewPager(vp);

    }


}
