package org.android.util;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.view.SimpleDraweeView;


public class ImageHelper {

    public static void load(@NonNull String url, SimpleDraweeView draweeView) {
        draweeView.setImageURI(Uri.parse(url));
    }
}
