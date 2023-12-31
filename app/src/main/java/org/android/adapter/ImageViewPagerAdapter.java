package org.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.android.R;
import org.android.data.model.GalleryModel;
import org.android.data.model.PictureModel;
import org.android.fragments.services;
import org.android.rest.MyNetworkListener;
import org.android.rest.NetworkExceptionHandler;
import org.android.rest.RequestRepository;
import org.android.views.LoadingLayout;

import java.util.ArrayList;
import java.util.List;




public class ImageViewPagerAdapter extends PagerAdapter {


    private Context mContext;
    int resId = 0;
    private List<String> list = new ArrayList<>();

    public ImageViewPagerAdapter(final Context context, int id , final LoadingLayout gload) {
        mContext = context;

        if (id > 0) {

            gload.SetState(LoadingLayout.STATE_LOADING);

            RequestRepository rr = new RequestRepository(context, ImageViewPagerAdapter.class.getSimpleName());
            rr.getGallery(id, new MyNetworkListener<GalleryModel>() {
                @Override
                public void getResult(GalleryModel result) {



                    for (PictureModel gm : result.getData()) {

                        list.add(gm.getUrl_image());

                    }

                    gload.SetState(LoadingLayout.STATE_SHOW_DATA);
                    notifyDataSetChanged();
                }

                @Override
                public void getException(NetworkExceptionHandler error) {

                    gload.setError(error.error_fa_message);
                }
            });

        }


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,
                            @NonNull Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {


        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.fragment_slider, collection, false);
        ImageView iv = layout.findViewById(R.id.img);
        Glide.with(mContext).load(list.get(position)).fitCenter().crossFade()
                .into(iv);

        collection.addView(layout);
        return layout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}