package org.android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.facebook.drawee.view.SimpleDraweeView;

import org.android.R;
import org.android.data.model.MMMModel;
import org.android.data.model.PeriodsModel;
import org.android.data.WeatherType;
import org.android.data.model.WeatherModel;
import org.android.util.PersianDate;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class HoursRecyclerViewAdapter extends RecyclerView.Adapter<HoursRecyclerViewAdapter.ViewHolder> {

    private String type = WeatherType.MIN;

    public interface OnIconClickListener {
        void onIconClick(PeriodsModel weather, View view);
    }

    private OnIconClickListener listener;
    private final List<PeriodsModel> mHours;
    private SimpleDateFormat mFormatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private Context context;

    public HoursRecyclerViewAdapter(Context context, List<PeriodsModel> hours, String type) {
        mHours = hours;
        this.context = context;
        this.type = type;
    }

    public void addElement(PeriodsModel Weather) {
        mHours.add(mHours.size(), Weather);
        notifyItemInserted(mHours.size());
    }

    public void addElements(List<PeriodsModel> data) {
        mHours.addAll(mHours.size(), data);
        notifyDataSetChanged();
    }

    public void clear() {
        mHours.clear();
        notifyDataSetChanged();
    }

    public void setListener(OnIconClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hour_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        MMMModel wtype = WeatherType.getMMM(type, mHours.get(position));
        Resources res = holder.view.getResources();
        holder.time.setText(PersianDate.GetTime(mHours.get(position).plcname));
        String temperatureText = wtype.pmax + " °C";
        holder.temperature.setText(temperatureText);

        String windText = wtype.pwind + " km/h ";
        holder.wind.setText(windText);

        String humidityText = wtype.prh+" % ";
        holder.humidity.setText(humidityText);

        //String pminchill = wtype.pminchill+" °C ";
        //holder.pressure.setText(pminchill);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.weatherState);
        int id = context.getResources().getIdentifier(wtype.psymbol, "raw", context.getPackageName());
        Glide.with(context).load(id).into(imageViewTarget);

        holder.weatherState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onIconClick(mHours.get(holder.getAdapterPosition()), v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView time;
        public TextView temperature;
        public TextView wind;
        public TextView humidity;
        //public TextView pressure;
        public SimpleDraweeView weatherState;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.time = (TextView) view.findViewById(R.id.date);
            this.temperature = (TextView) view.findViewById(R.id.temperature);
            this.wind = (TextView) view.findViewById(R.id.wind);
            this.humidity = (TextView) view.findViewById(R.id.humidity);
            //this.pressure = (TextView) view.findViewById(R.id.pressure);
            this.weatherState = (SimpleDraweeView) view.findViewById(R.id.weather_state);
        }
    }
}
