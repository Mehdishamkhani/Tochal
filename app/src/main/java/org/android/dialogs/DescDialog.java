package org.android.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.android.R;


public class DescDialog extends Dialog {

    public Activity c;
    public Dialog d;
    private String description;

    public DescDialog(Activity a, String description) {
        super(a, R.style.Theme_Dialog);
        this.c = a;
        this.description = description;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_desc);

        TextView desc = findViewById(R.id.desc);
        if (description != null)
            desc.setText(description);

    }


}