package org.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ahmadnemati.wind.WindView;
import com.github.ahmadnemati.wind.enums.TrendType;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

import org.android.CityDetailActivity;
import org.android.R;
import org.android.adapter.WeatherItemRecyclerViewAdapter;
import org.android.data.model.ForecastModel;
import org.android.data.model.Item;
import org.android.data.model.MMMModel;
import org.android.data.model.PlaceModel;
import org.android.data.model.PlacesModel;
import org.android.data.model.WeatherModel;
import org.android.data.WeatherType;
import org.android.rest.MyNetworkListener;
import org.android.rest.NetworkExceptionHandler;
import org.android.rest.RequestRepository;
import org.android.util.FontHelper;
import org.android.views.LoadingLayout;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class weather extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.weather)
    WeatherView mWeatherView;

    @BindView(R.id.windview)
    WindView windView;

    @BindView(R.id.stations)
    RecyclerView stations;

    @BindView(R.id.appbar_head)
    TextView mainhead;


    @BindView(R.id.list_head)
    TextView list_head;

    @BindView(R.id.rf)
    SwipeRefreshLayout rf;

    @BindView(R.id.loading)
    LoadingLayout loading;

    private WeatherItemRecyclerViewAdapter mAdapter;
    private String type = WeatherType.MIN;
    private WeatherModel forecast;
    private PlacesModel places;

    public weather() {
    }

    public static weather newInstance() {
        return new weather();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, v);

        if (getActivity() != null)
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        loading.SetState(LoadingLayout.STATE_LOADING);
        rf.setOnRefreshListener(this);
        rf.setColorSchemeResources(R.color.colorAccent);


        mAdapter = new WeatherItemRecyclerViewAdapter(getActivity(), new ArrayList<Item>());
        mAdapter.setListener(new WeatherItemRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int pos, Item weather, View view) {
                clickOperation(weather);
                goToDetail();
            }
        });

        mAdapter.setListener(new WeatherItemRecyclerViewAdapter.OnIconClickListener() {
            @Override
            public void onIconClick(int pos, Item weather, View view) {
                clickOperation(weather);
            }
        });

        stations.setAdapter(mAdapter);
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();


        windView.setWindSpeed(0);
        windView.setWindSpeedUnit(getString(R.string.wind_unit));
        windView.setWindText(getString(R.string.wind));
        windView.setPressureUnit(getString(R.string.meter));
        windView.setBarometerText(getString(R.string.pflevel));
        windView.setPressure(0);
        windView.setTrendType(TrendType.NONE);
        windView.setTypeface(FontHelper.get(getActivity(), getString(R.string.isans)));
        windView.start();
        windView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetail();
            }
        });


        if (places != null && places.getData().size() > 0 && forecast != null) {

            showingDataOpt();

        } else {
            final RequestRepository rr = new RequestRepository(getActivity(), weather.class.getSimpleName());
            rr.getStations(new MyNetworkListener<PlacesModel>() {
                @Override
                public void getResult(PlacesModel result) {

                    rr.getWeathers(new getWeatherResult(weather.this));
                    places = result;

                }

                @Override
                public void getException(NetworkExceptionHandler error) {

                }
            });
        }


        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loading.getCurrentState() == LoadingLayout.STATE_SHOW_Error)
                    refreshOperation();
            }
        });


        return v;
    }

    private void clickOperation(Item weather) {

        mainhead.setText(weather.getTitle());
        type = weather.getType();
        windView.setWindSpeed(WeatherType.getMMM(type, forecast.getPeriods().get(0)).pwind);
        setWeatherViewStatus(WeatherType.getMMM(type, forecast.getPeriods().get(0)));
    }

    private void goToDetail() {

        Intent in = new Intent(getActivity(), CityDetailActivity.class);
        in.putExtra(getString(R.string.head), mainhead.getText());
        in.putExtra(getString(R.string.type), type);
        startActivity(in);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onRefresh() {

        refreshOperation();
    }

    private void refreshOperation() {

        loading.SetState(LoadingLayout.STATE_LOADING);
        rf.setRefreshing(true);
        mAdapter.clear();
        mAdapter.setFirstSelected(-1);
        mAdapter.setLastSelected(-1);
        mAdapter.notifyDataSetChanged();
        final RequestRepository rr = new RequestRepository(getActivity(), weather.class.getSimpleName());

        rr.getStations(new MyNetworkListener<PlacesModel>() {
            @Override
            public void getResult(PlacesModel result) {

                rr.getWeathers(new getWeatherResult(weather.this));
                places = result;

            }

            @Override
            public void getException(NetworkExceptionHandler error) {

            }
        });
    }

    private class getWeatherResult implements MyNetworkListener<ForecastModel> {


        getWeatherResult(weather weather) {
        }

        @Override
        public void getResult(ForecastModel result) {


            if (result != null && result.getForecast().getPeriods().size() > 0) {

                forecast = result.getForecast();

                try {

                    Gson gson = new Gson();
                    String json = gson.toJson(result);
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString(getString(R.string.pref_data_key), json).apply();

                    showingDataOpt();

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


        @Override
        public void getException(NetworkExceptionHandler error) {

            View v = null;
            if (getActivity() != null)
                v = getActivity().findViewById(R.id.frm);
            if (v != null)
                Snackbar.make(v, error.error_fa_message, Snackbar.LENGTH_LONG).show();


            loading.setError(error.error_fa_message, true);
            rf.setRefreshing(false);

        }
    }

    private void showingDataOpt() {

        mAdapter.setWeather(forecast);

        if (places != null && places.getData().size() > 0) {

            int i = 0;
            int radius = Item.NO_RADIUS;
            for (PlaceModel placeModel : places.getData()) {

                if (places.getData().size() == 1)
                    radius = Item.RADIUS;
                else {
                    if (i == 0)
                        radius = Item.TOP_RADIUS;
                    else if (i + 1 == places.getData().size())
                        radius = Item.BOT_RADIUS;
                }

                addItem(new Item(placeModel.getName(), R.drawable.close, placeModel.getHeight(), radius));
                i++;
                radius = Item.NO_RADIUS;

            }


            //default
            try {
                if (places.getData().size() > 0) {
                    list_head.setText(places.getData().get(0).getPlace_type_label());
                    mAdapter.setFirstSelected(0);
                    type = places.getData().get(0).getHeight();
                    windView.setPressure(forecast.getPeriods().get(0).pflevel);
                    windView.setWindSpeed(WeatherType.getMMM(type, forecast.getPeriods().get(0)).pwind);
                    setWeatherViewStatus(WeatherType.getMMM(type, forecast.getPeriods().get(0)));
                    mainhead.setText(mAdapter.getItems().get(0).getTitle());
                }
            } catch (IndexOutOfBoundsException | NullPointerException e) {

                e.printStackTrace();
            }

        }


        rf.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
        loading.SetState(LoadingLayout.STATE_SHOW_DATA);

    }


    private void setWeatherViewStatus(MMMModel mmmModel) {


        switch (mmmModel.plcphrase) {
            case "light_snow":
            case "heavy_snow":
            case "snow_shwrs":
                mWeatherView.setWeather(Constants.WeatherStatus.SNOW);
                break;

            case "light_rain":
            case "heavy_rain":
            case "mod_rain":
            case "rain_shwrs":
                mWeatherView.setWeather(Constants.WeatherStatus.RAIN);

            default:
                mWeatherView.setWeather(Constants.WeatherStatus.SUN);

        }

        if (!mWeatherView.isActivated())
            mWeatherView.startAnimation();

    }

    public void addItem(Item item) {
        mAdapter.addElement(item);
    }

    public void addItems(List<Item> items) {
        mAdapter.addElements(items);
    }

}
