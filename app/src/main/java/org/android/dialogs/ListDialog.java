package org.android.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.android.R;
import org.android.adapter.ItemRecyclerViewAdapter;
import org.android.adapter.WorkItemRecyclerViewAdapter;
import org.android.data.model.Item;
import org.android.data.model.WorkModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mehdi on 18/12/2017.
 */

public class ListDialog extends Dialog {

    public Activity c;
    public Dialog d;

    @BindView(R.id.times)
    RecyclerView list;

    @BindView(R.id.title)
    TextView title;

    //@BindView(R.id.ref)
   // ImageView refresh;

    List<WorkModel> work;

    private WorkItemRecyclerViewAdapter adp;

    public ListDialog(Activity a, List<WorkModel> work) {
        super(a, R.style.Theme_Dialog);
        this.c = a;

        this.work = work;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list);
        ButterKnife.bind(this);

        if (this.work != null) {


            adp = new WorkItemRecyclerViewAdapter(c, new ArrayList<Item>());
            list.setAdapter(adp);
            adp.clear();
            adp.notifyDataSetChanged();

            String lastHead = "defaultHead";

            for (WorkModel model : work) {

                if (!lastHead.equalsIgnoreCase(model.day_title))
                    addItem(new Item(model, true));

                addItem(new Item(model, false));

                lastHead = model.day_title;

            }



        }

    }

    public void addItem(Item item) {
        adp.addElement(item);
    }

    public void addItems(List<Item> items) {
        adp.addElements(items);
    }


}

