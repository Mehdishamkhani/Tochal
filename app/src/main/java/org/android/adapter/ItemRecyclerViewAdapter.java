package org.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.android.R;
import org.android.data.model.Item;
import org.android.data.model.WorkModel;
import org.android.util.TimeHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final CollapsingToolbarLayout collapsingToolbarLayout;
    private int lastSelected = -1;
    public int firstSelected = -1;


    public interface OnIconClickListener {
        void onIconClick(int pos, Item weather, View view);
    }

    private OnIconClickListener listener;
    private final List<Item> mItems;
    private Context context;


    public ItemRecyclerViewAdapter(Context context, List<Item> items, CollapsingToolbarLayout collapsingToolbarLayout) {
        mItems = items;
        this.context = context;
        this.collapsingToolbarLayout = collapsingToolbarLayout;
    }

    public void setFirstSelected(int pos) {

        this.firstSelected = pos;
    }


    public void setLastSelected(int lastSelected) {
        this.lastSelected = lastSelected;
    }

    public List<Item> getItems() {

        return mItems;
    }


    public int getLastSelected() {
        return lastSelected;
    }

    public Item getItem(int position) {

        return mItems.get(position);

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

    public void setListener(OnIconClickListener listener) {
        this.listener = listener;
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
        final int icon = item.getIcon();
        final int temp = position;


        if (item.isHeader()) {
            holder.title.setText(item.getPlaceModel().getPlace_type_label());

            holder.itemLayer.setBackgroundColor(Color.TRANSPARENT);
            holder.icon.setVisibility(View.GONE);
            holder.itemLayer.setClickable(false);
            holder.itemLayer.setPadding(20, 35, 20, 20);


        } else {

            holder.title.setText(item.getPlaceModel().getName());

            if (lastSelected == -1 && firstSelected != -1)
                mItems.get(firstSelected).setSelected(true);

            holder.icon.setVisibility(View.VISIBLE);

            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, (icon == 1) ? R.drawable.open : R.drawable.close));

            final boolean sel = mItems.get(position).isSelected();

            if (sel) {
                switch (item.getRadius()) {
                    case Item.TOP_RADIUS:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, (icon == 1) ? R.drawable.bg_green_radius_top : R.drawable.bg_red_radius_top));
                        break;
                    case Item.BOT_RADIUS:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, (icon == 1) ? R.drawable.bg_green_radius_bot : R.drawable.bg_red_radius_bot));
                        break;
                    case Item.RADIUS:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, (icon == 1) ? R.drawable.bg_green_radius : R.drawable.bg_red_radius));
                        break;
                    default:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, (icon == 1) ? R.drawable.list_border_green : R.drawable.list_border_red));
                }
            } else {
                switch (item.getRadius()) {
                    case Item.TOP_RADIUS:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_white_radius_top));
                        break;
                    case Item.BOT_RADIUS:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_white_radius_bot));
                        break;
                    case Item.RADIUS:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_white_radius));
                        break;
                    default:
                        holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.list_border));
                }
            }


            holder.itemLayer.setPadding(20, 20, 20, 20);

            holder.itemLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (lastSelected >= 0)
                        mItems.get(lastSelected).setSelected(false);
                    if (firstSelected >= 0)
                        mItems.get(firstSelected).setSelected(false);

                    lastSelected = temp;
                    mItems.get(temp).setSelected(true);
                    collapsingToolbarLayout.setBackgroundColor(ContextCompat.getColor(context, (icon == 1) ? R.color.green_bg : R.color.red_bg));
                    notifyDataSetChanged();

                    if (listener != null) {

                        try {
                            Item ipos = mItems.get(position);
                            listener.onIconClick(position, ipos, v);

                        } catch (ArrayIndexOutOfBoundsException e) {

                            e.printStackTrace();
                        }
                    }
                }
            });

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
