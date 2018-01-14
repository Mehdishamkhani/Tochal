package org.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.android.R;
import org.android.data.model.ForecastModel;
import org.android.data.model.PlaceModel;
import org.android.data.model.PlacesModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class map extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;


    public map() {

    }


    public static map newInstance() {
        return new map();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        //LatLng velenjak = new LatLng(35.8197644, 51.4048397);
        //mMap.addMarker(new MarkerOptions().position(velenjak).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.8578595, 51.3843905), 12.0f));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        String ds = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.place_data_key), getString(R.string.empty));

        if (!ds.equalsIgnoreCase(getString(R.string.empty))) {

            try {

                Gson gson = new GsonBuilder().create();
                PlacesModel data = gson.fromJson(ds, PlacesModel.class);
                for (PlaceModel placesModel : data.getData()) {

                    LatLng latLng = new LatLng(Double.valueOf(placesModel.getLat()), Double.valueOf(placesModel.getLng()));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(placesModel.getName()));

                    Log.d(this.getTag(), placesModel.getLat() + " - " + placesModel.getLng());
                }


            } catch (JsonSyntaxException e) {

                e.printStackTrace();
            }

        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, v);

        try {
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
