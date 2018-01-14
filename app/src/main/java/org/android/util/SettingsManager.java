package org.android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "SettingData";


    @SuppressLint("CommitPrefEdits")
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
