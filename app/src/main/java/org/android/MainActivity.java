package org.android;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.android.adapter.ViewPagerAdapter;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    //@Bind(R.id.frm)
    //FrameLayout frm;

    @BindView(R.id.bnve)
    BottomNavigationViewEx bnve;

    @BindView(R.id.frm)
    ViewPager vp;
    private final int PERMISSIONS_READ_EXTERNAL_STORAGE = 5544;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        permissionsCheck(Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE);


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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.permission_need, Toast.LENGTH_LONG).show();
                }
            }

        }
    }


    public void permissionsCheck(String... permissions) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            List<String> list = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permission);
                }
            }
            if (list.size() > 0)
                ActivityCompat.requestPermissions(this, list.toArray(new String[list.size()]), PERMISSIONS_READ_EXTERNAL_STORAGE);

        }
    }


}
