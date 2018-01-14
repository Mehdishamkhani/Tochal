package org.android.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import org.android.R;
import org.android.util.FontHelper;

import java.io.File;


public class TextViewTypeFace extends android.support.v7.widget.AppCompatTextView {

    public Context mContext;
    private String fontName = "isans";

    public TextViewTypeFace(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public TextViewTypeFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }


    public TextViewTypeFace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context, attrs);
    }


    private void initializeViews(Context context, AttributeSet attrs) {

        this.mContext = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewTypeFace, 0, 0);
            fontName = a.getString(R.styleable.TextViewTypeFace_fontName);
            a.recycle();
        }
        if (!isInEditMode()) {
            fontName = (fontName != null) ? fontName : "isans";
            try {
                setTypeface(FontHelper.get(context, fontName));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }


}
