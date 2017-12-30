package org.android.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by USER on 4/23/2015.
 */
public class SettingsManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "SettingData";


//    public enum VERFICATION_STATUS
//    {
//        confirmed, unconfirmed
//    }


    public SettingsManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setDownloadApk(int VersionCode, long ids) {
        editor.putInt("VersionCode", VersionCode);
        editor.putLong("ids", ids);
        editor.commit();
    }

    public int getVersionCodeDownload() {

        return pref.getInt("VersionCode", -1);
    }

    public long getIdDOwnload() {

        return pref.getLong("ids", -1);
    }


}
