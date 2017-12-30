package org.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.android.R;
import org.android.data.model.Item;

import java.util.List;


public class WorkItemRecyclerViewAdapter extends RecyclerView.Adapter<WorkItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mItems;
    private Context context;


    public WorkItemRecyclerViewAdapter(Context context, List<Item> items) {
        mItems = items;
        this.context = context;
    }

    public List<Item> getItems() {

        return mItems;
    }

    public void addElement(Item item) {
        mItems.add(mItems.size(), item);
        notifyItemInserted(mItems.size());
    }

    public void addElements(List<Item> items) {
        mItems.addAll(mItems.size(), items);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Item item = mItems.get(position);

        holder.icon.setVisibility(View.GONE);

        if (item.isHeader()) {
            holder.title.setText(item.getWmodel().day_title);
            holder.itemLayer.setBackgroundColor(Color.TRANSPARENT);
            holder.itemLayer.setClickable(false);
            holder.itemLayer.setPadding(20, 20, 20, 10);
        } else {

            String _t = item.getWmodel().open_time;
            _t += " الی ";
            _t += item.getWmodel().close_time;

            holder.title.setText(_t);

            holder.itemLayer.setPadding(20, 20, 50, 20);


        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView title;
        public ImageView icon;
        public LinearLayout itemLayer;


        public ViewHolder(View view) {
            super(view);
            this.view = view;

            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
            itemLayer = (LinearLayout) view.findViewById(R.id.item);


        }
    }
}
