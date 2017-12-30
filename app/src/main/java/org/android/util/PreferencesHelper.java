package org.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.android.TochalApp;

import org.greenrobot.greendao.annotation.NotNull;
import org.android.R;


public class PreferencesHelper {

    private static PreferencesHelper sInstance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //singleton
    public static PreferencesHelper getInstance() {
        if (sInstance == null) {
            sInstance = new PreferencesHelper();
        }
        return sInstance;
    }

    public PreferencesHelper() {
        this.sharedPreferences = TochalApp.getSharedPreferences();
        this.editor = this.sharedPreferences.edit();
    }


    public void SetJsonData(Context context, @NotNull String data) {

        editor.putString(context.getString(R.string.pref_data_key), data);
        editor.apply();

    }


    public String getJsonData(Context context) {
        return sharedPreferences.getString(context.getString(R.string.pref_data_key), context.getString(R.string.empty));
    }


    //for save data in SharedPreferences
    public void setUnits(Context context, @NotNull String units) {
        editor.putString(context.getString(R.string.pref_unit_key), units);
        editor.apply();
    }

    //for the loading of data from SharedPreferences
    public String getUnits(Context context) {
        return sharedPreferences.getString(context.getString(R.string.pref_unit_key),
                Utility.getDefaultUnit());
    }

    public void setSyncInterval(Context context, @NotNull int interval) {
        editor.putInt(context.getString(R.string.pref_sync_key), interval);
        editor.apply();
    }

    public String getSyncInterval(Context context) {
        return sharedPreferences.getString(context.getString(R.string.pref_sync_key)
                , context.getString(R.string.pref_sync_default_value));
    }


    public void setUseCurrentLocation(Context context, boolean useLocation) {
        editor.putBoolean(context.getString(R.string.pref_use_current_location_key), useLocation);
        editor.apply();
    }

    public boolean isUseCurrentLocation(Context context) {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_use_current_location_key), true);
    }

    public String getSelectedLang(Context context, String defaultLanguage) {
        return sharedPreferences.getString(context.getString(R.string.pref_selected_lang_key), defaultLanguage);
    }

}
