package org.android.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import org.android.R;


public class LoadingLayout extends RelativeLayout {

    public static final int STATE_LOADING = 1;
    public static final int STATE_SHOW_DATA = 2;
    public static final int STATE_SHOW_Error = 3;
    public Context mContext;
    public ProgressBar pb;
    public View vError;
    public View mainLayout;
    public String mError = "";
    private int state = STATE_LOADING;

    public LoadingLayout(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context, attrs);
    }


    String whereShowLoading = "";

    private void initializeViews(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
            whereShowLoading = a.getString(R.styleable.LoadingLayout_whereShowLoading);
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vError = inflater.inflate(R.layout.row_error, null);

        mainLayout = getChildAt(0);

        pb = new ProgressBar(mContext);
        pb.setIndeterminate(true);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (whereShowLoading != null && whereShowLoading.equals("top")) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        } else {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        }
        pb.setLayoutParams(lp);
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        vError.setLayoutParams(lp);


        addView(pb);
        addView(vError);

    }

    public void SetState(int mState) {

        this.state = mState;

        if (mState == STATE_LOADING) {
            mainLayout.setVisibility(GONE);
            pb.setVisibility(VISIBLE);
            vError.setVisibility(GONE);
        } else if (mState == STATE_SHOW_DATA) {
            pb.setVisibility(GONE);
            vError.setVisibility(GONE);
            mainLayout.setAlpha(0);
            mainLayout.setVisibility(VISIBLE);
            mainLayout.animate()
                    .alpha(1)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setDuration(500);
        } else if (mState == STATE_SHOW_Error) {
            mainLayout.setVisibility(GONE);
            pb.setVisibility(GONE);
            vError.setVisibility(VISIBLE);
        }

    }

    public int getCurrentState() {

        return this.state;
    }

    public void setError(String mError) {
        this.mError = mError;
        TextView tvError = (TextView) vError.findViewById(R.id.tvError);
        tvError.setText(mError);
        SetState(STATE_SHOW_Error);
    }
    public void setError(String mError, boolean hasReload) {
        this.mError = mError;
        TextView tvError = (TextView) vError.findViewById(R.id.tvError);
        IconTextView reload = (IconTextView) vError.findViewById(R.id.reload);

        if (hasReload)
            reload.setVisibility(View.VISIBLE);

        tvError.setText(mError);
        SetState(STATE_SHOW_Error);
    }

}
