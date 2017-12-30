package org.android.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import org.android.BuildConfig;
import org.android.TochalApp;

import java.io.File;
import java.util.Date;
import java.util.Random;



/**
 * Created by USER on 9/21/2015.
 */
public class Utils {


    public final static String App_Folder_Name = "/tochal/";
    public final static String App_Folder_Image = "image/";
    public final static String App_Folder_Update = "update/";
    public final static String Apk_download_Name = "tochal.apk";
    public final static Integer SocketTimeout = 90000;

    public static final String[] persianDigits = {
            "\u0660", // ٠
            "\u0661", // ١
            "\u0662", // ٢
            "\u0663", // ٣
            "\u0664", // ٤
            "\u0665", // ٥
            "\u0666", // ٦
            "\u0667", // ٧
            "\u0668", // ٨
            "\u0669"  // ٩
    };
    public static Uri tempPath = null;


    public static void makeMainFolder() {
        File parenttempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Utils.App_Folder_Name);
        File nomedia = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Utils.App_Folder_Name + ".nomedia");
        if (!parenttempDir.exists()) {
            parenttempDir.mkdir();

        }

        if (!nomedia.exists()) {
            try {
                nomedia.createNewFile();
            } catch (Exception ex) {
                Log.e("erorr sakhte file", "khataa too sakhte update folder");
            }
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    public static String EnglishDigitToPersianDigit(int value, int countOfDigit) {
        String pDigit = "";
        while (value / 10 != 0) {
            pDigit = persianDigits[value % 10] + pDigit;
            value = value / 10;
        }
        pDigit = persianDigits[value % 10] + pDigit;

        if (countOfDigit != -1) {
            int dif = countOfDigit - pDigit.length();
            for (int i = 0; i < dif; i++)
                pDigit = persianDigits[0] + pDigit;
        }
        return pDigit;
    }

    public static Random rand = new Random(new Date().getTime());

    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        return (int) (rand.nextDouble() * max) + min;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    public class HELP_OVERLAY_EVENT_HOME_Tab {
    }
//    final MaterialDialog md;
//    md=new MaterialDialog.Builder(mContext)
//            .title("دانلود مجدد")
//    .content("فایل خراب هست،آیا دوباره میخواهید آن را دانلود کنید؟")
//    .positiveText("بلی")
//    .callback(new MaterialDialog.ButtonCallback() {
//        @Override
//        public void onPositive(MaterialDialog dialog) {
//            BooksRepository br=new BooksRepository(mContext);
//            Book tempB1=br.getByID(tempBook.ID);
//            tempB1.mustDownload=true;
//            br.update(tempB1);
//            for(int i=0;i<mDataset.size();i++)
//            {
//                if(mDataset.get(i).ID==tempB1.ID)
//                {
//                    mDataset.set(i,tempB1);
//                    break;
//                }
//            }
//            notifyDataSetChanged();
//            Utils.StartDownloadFile(mContext, tempB1.ID, tempB1.fileNamePDF, Integer.valueOf(tempB1.fileSizePDF)
//                    , tempB1.fileNameAudio, Integer.valueOf(tempB1.fileSizeAudio), tempB1.type, tempB1.title);
//
//        }
//    })
//            .buttonsGravity(GravityEnum.END)
//    .titleGravity(GravityEnum.START)
//    .contentGravity(GravityEnum.START)
//    .negativeText("خیر")
//    .show();

}
