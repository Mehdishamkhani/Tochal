package org.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import org.android.data.local.ILocalDataSource;
import org.android.data.local.LocalDataSource;
import org.android.data.model.Config;
import org.android.events.UpdateApk;
import org.android.rest.MyNetworkListener;
import org.android.rest.NetworkExceptionHandler;
import org.android.rest.NetworkManager;
import org.android.rest.RequestRepository;
import org.android.util.LocaleHelper;

import de.greenrobot.event.EventBus;


public class TochalApp extends Application {

    public static SharedPreferences sSharedPreferences;
    private ILocalDataSource localDataSource;
    public static Config config;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkManager.getInstance(this, NetworkManager.REAL_SERVER);

        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        localDataSource = LocalDataSource.getInstance(this);
        Fresco.initialize(this);

        //Glide.get(this).clearMemory();
        runInBackground();

        Iconify.with(new FontAwesomeModule());


        RequestRepository rr = new RequestRepository(this, TochalApp.class.getSimpleName());
        rr.Get_Config(new getConfig());

    }


    public static class getConfig implements MyNetworkListener<Config> {
        public getConfig() {
        }

        @Override
        public void getResult(Config result) {
            //Log.d("Application Class", "result back Config");
            EventBus.getDefault().post(new UpdateApk(result));
            TochalApp.config = result;
        }

        @Override
        public void getException(NetworkExceptionHandler error) {

        }
    }

    void runInBackground() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();

            }
        }).start();
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public ILocalDataSource getLocalDataSource() {
        return localDataSource;
    }
}
