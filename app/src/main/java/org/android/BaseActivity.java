package org.android;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.android.dialogs.LoadingDialog;
import org.android.events.DimissLoadingDialog;
import org.android.events.ShowLoadingDialog;
import org.android.events.UpdateApk;
import org.android.util.SettingsManager;
import org.android.util.Utils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import de.greenrobot.event.EventBus;


public class BaseActivity extends AppCompatActivity {


    public boolean mIsActvityShow = false;


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                DownloadManager.Query query = new DownloadManager.Query();
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                SettingsManager sm = new SettingsManager(getApplicationContext());
                query.setFilterById(sm.getIdDOwnload());
                assert dm != null;
                Cursor c = dm.query(query);
                if (c.moveToFirst()) {
                    int uriColumnIndex = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        install();
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onStart() {
        this.mIsActvityShow = true;
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        this.mIsActvityShow = false;

        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    LoadingDialog ld;

    public void onEventMainThread(ShowLoadingDialog erf) {
        ld = LoadingDialog.newInstance(erf.title);
        ld.show(getSupportFragmentManager(), "");
    }

    public void onEventMainThread(DimissLoadingDialog erf) {
        if (ld != null)
            ld.dismiss();
    }


    public boolean checkApkDownloadOK(long id) {
        DownloadManager.Query query = new DownloadManager.Query();
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        SettingsManager sm = new SettingsManager(getApplicationContext());
        query.setFilterById(sm.getIdDOwnload());
        assert dm != null;
        Cursor c = dm.query(query);
        if (c.moveToFirst()) {
            int columnIndex = c
                    .getColumnIndex(DownloadManager.COLUMN_STATUS);
            return DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex);
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(UpdateApk erf) throws IOException {

        SettingsManager sm = new SettingsManager(getApplicationContext());
        boolean lastDownloadState = checkApkDownloadOK(sm.getIdDOwnload());
        int lastVersionCode = sm.getVersionCodeDownload();


        if (BuildConfig.VERSION_CODE < Integer.valueOf(erf.mConfig.latest_version))//BuildConfig.VERSION_CODE<erf.mConfig.android.latest
        {


            if (BuildConfig.VERSION_CODE >= Integer.valueOf(erf.mConfig.latest_support_version)) {
                MaterialDialog md = new MaterialDialog.Builder(BaseActivity.this)
                        .title(R.string.update)
                        .content(R.string.update_text)
                        .positiveText(R.string.accept)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                install();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .buttonsGravity(GravityEnum.END)
                        .titleGravity(GravityEnum.START)
                        .contentGravity(GravityEnum.START)
                        .negativeText(R.string.dialog_no)
                        .show();

            } else {
                File a = new File(Environment.getExternalStorageDirectory() + Utils.App_Folder_Name + Utils.App_Folder_Update + Utils.Apk_download_Name);
                if (lastDownloadState && lastVersionCode == Integer.valueOf(erf.mConfig.latest_version) && a.exists())//lastDownloadState&&lastVersionCode==erf.mConfig.android.latest
                {
                    install();
                } else {
                    update(erf);
                }

            }
        }

    }


    public void update(UpdateApk erf) throws IOException {
        SettingsManager sm = new SettingsManager(getApplicationContext());

        String name = Environment.getExternalStorageDirectory().getAbsolutePath();

        Utils.makeMainFolder();


        File direct = new File(name + Utils.App_Folder_Name + Utils.App_Folder_Update);

        if (!direct.exists()) {
            direct.mkdirs();
        }


        if (isFileExists(Utils.App_Folder_Name + Utils.App_Folder_Update + Utils.Apk_download_Name)) {
            deleteFile(Utils.App_Folder_Name + Utils.App_Folder_Update + Utils.Apk_download_Name);
        }
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(erf.mConfig.app_path));
        String localdownloadPath = Utils.App_Folder_Name + Utils.App_Folder_Update;

        File fileDir = new File(localdownloadPath);
        if (fileDir.isDirectory()) {
            request.setDestinationInExternalPublicDir(localdownloadPath, Utils.Apk_download_Name);
        } else {
            fileDir.mkdirs();
            request.setDestinationInExternalPublicDir(localdownloadPath, Utils.Apk_download_Name);
        }
        long enqueue = 0;
        if (dm != null) {
            enqueue = dm.enqueue(request);
        }
        sm.setDownloadApk(Integer.valueOf(erf.mConfig.latest_version), enqueue);
    }


    public void install() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + Utils.App_Folder_Name + Utils.App_Folder_Update + Utils.Apk_download_Name)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean isFileExists(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();


    }

    public boolean deleteFile(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.delete();

    }
}



