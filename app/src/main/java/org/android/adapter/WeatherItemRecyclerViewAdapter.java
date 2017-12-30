package org.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.android.R;
import org.android.data.WeatherType;
import org.android.data.model.Item;
import org.android.data.model.WeatherModel;

import java.util.List;


public class WeatherItemRecyclerViewAdapter extends RecyclerView.Adapter<WeatherItemRecyclerViewAdapter.ViewHolder> {

    private int lastSelected = -1;
    public int firstSelected = -1;
    private WeatherModel weather;

    public interface OnIconClickListener {
        void onIconClick(int pos, Item weather, View view);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(int pos, Item weather, View view);
    }

    private OnIconClickListener listener;
    private final List<Item> mItems;
    private Context context;

    private OnItemLongClickListener listener2;

    public WeatherItemRecyclerViewAdapter(Context context, List<Item> items) {
        mItems = items;
        this.context = context;
    }


    public void setWeather(WeatherModel weather) {
        this.weather = weather;
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

    public Item getSelectedItem() {

        return mItems.get(lastSelected > -1 ? lastSelected : 0);
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

    public void setListener(OnItemLongClickListener listener) {
        this.listener2 = listener;
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
        holder.title.setText(item.getTitle());


        if (weather != null) {
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.icon);
            int id = context.getResources().getIdentifier(WeatherType.getMMM(item.getType(), weather.getPeriods().get(0)).psymbol, "raw", context.getPackageName());
            Glide.with(context).load(id).into(holder.icon);
        }

        if (lastSelected == -1 && firstSelected != -1)
            mItems.get(firstSelected).setSelected(true);

        holder.icon.setVisibility(View.VISIBLE);

        final boolean sel = mItems.get(position).isSelected();
        if (sel) {
            switch (item.getRadius()) {
                case Item.TOP_RADIUS:
                    holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_radius_top));
                    break;
                case Item.BOT_RADIUS:
                    holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_radius_bot));
                    break;
                case Item.RADIUS:
                    holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_radius));
                    break;
                default:
                    holder.itemLayer.setBackground(ContextCompat.getDrawable(context, R.drawable.list_border_green));
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
        holder.itemLayer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (lastSelected >= 0)
                    mItems.get(lastSelected).setSelected(false);
                if (firstSelected >= 0)
                    mItems.get(firstSelected).setSelected(false);

                lastSelected = position;
                mItems.get(position).setSelected(true);
                notifyDataSetChanged();

                if (listener2 != null)
                    listener2.OnItemLongClick(position, mItems.get(position), view);
                return false;
            }
        });


        holder.itemLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastSelected >= 0)
                    mItems.get(lastSelected).setSelected(false);
                if (firstSelected >= 0)
                    mItems.get(firstSelected).setSelected(false);

                lastSelected = position;
                mItems.get(position).setSelected(true);
                notifyDataSetChanged();

                if (listener != null) {
                    listener.onIconClick(position, mItems.get(position), v);
                }
            }
        });

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
        public View border;


        public ViewHolder(View view) {
            super(view);
            this.view = view;

            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
            itemLayer = (LinearLayout) view.findViewById(R.id.item);

        }
    }
}
