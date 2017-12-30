package org.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.android.dao.OrmCity;
import org.android.data.local.ILocalDataSource;
import org.android.events.LocationEvent;
import org.android.util.PreferencesHelper;


public class PermissionActivity extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSIONS_REQUEST_LOCATION = 101;
    private static final String LOG_TAG = PermissionActivity.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    ILocalDataSource localDataSource;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        localDataSource = ((TochalApp) getApplicationContext()).getLocalDataSource();
        if (PreferencesHelper.getInstance().isUseCurrentLocation(this)) {
            if (savedInstanceState == null) {
                askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(PermissionActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permission)) {
                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
                buildGoogleApiClient();
            }
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "" + permission + " is already granted.");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_LOCATION:
                    buildGoogleApiClient();
                    break;
            }
            if (BuildConfig.DEBUG) Log.d(LOG_TAG, "Permission granted");
        } else {
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {

            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (BuildConfig.DEBUG) Log.d(LOG_TAG, connectionResult.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (BuildConfig.DEBUG) Log.d(LOG_TAG, "onConnectionSuspended i=" + i);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(LocationEvent event) {
        Log.d(LOG_TAG, "LocationEvent location=" + event.getCity());
        OrmCity yourCity = new OrmCity((long) 1, event.getCity(), event.getRegion(),
                event.getCountry(), lastLocation.getLatitude(), lastLocation.getLongitude());
        localDataSource.saveCity(yourCity);
    }

}
