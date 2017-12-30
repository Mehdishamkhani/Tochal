package org.android.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import org.android.R;
import org.android.adapter.ImageViewPagerAdapter;
import org.android.adapter.ViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mehdi on 18/12/2017.
 */

public class GalleryDialog extends Dialog {

    public FragmentManager fm;
    public Activity c;
    public Dialog d;

    @BindView(R.id.slider)
    ViewPager vp;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.forward)
    ImageView forward;

    public GalleryDialog(Activity a, FragmentManager fm) {
        super(a, R.style.Theme_Dialog);
        this.c = a;
        this.fm = fm;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gallery);
        ButterKnife.bind(this);

    }


}

