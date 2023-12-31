package org.android.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.android.R;


public class LoadingDialog extends DialogFragment {

    String title = "";

    public LoadingDialog() {
    }

    public static LoadingDialog newInstance(String title) {
        LoadingDialog fragment = new LoadingDialog();
        Bundle args = new Bundle();
        args.putSerializable("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = (String) getArguments().getSerializable("title");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
//        Window window = getDialog().getWindow();
//        window.setLayout(getResources().getInteger(R.integer.category_dialog_width_size), 125);
//        window.setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
            lp.dimAmount = 0.7f;
        }


        TextView tvTitle = view.findViewById(R.id.tvLoadingTitle);
        if (title.length() > 0)
            tvTitle.setText(title);

        return view;
    }

}
