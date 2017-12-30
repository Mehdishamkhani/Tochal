package org.android.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.android.R;

/**
 * Created by mehdi on 18/12/2017.
 */

public class DescDialog extends Dialog {

    private final String title;
    public Activity c;
    public Dialog d;
    private String description;

    public DescDialog(Activity a,String title , String description) {
        super(a, R.style.Theme_Dialog);
        this.c = a;
        this.description = description;
        this.title = title;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_desc);

        TextView desc = (TextView) findViewById(R.id.desc);
        TextView title = (TextView) findViewById(R.id.title);

        desc.setText(description);
        title.setText(this.title);


    }


}