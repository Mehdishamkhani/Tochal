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


public class Utils {


    public final static String App_Folder_Name = "/tochal/";
    public final static String App_Folder_Image = "image/";
    public final static String App_Folder_Update = "update/";
    public final static String Apk_download_Name = "tochal.apk";
    public final static Integer SocketTimeout = 90000;

    private static final String[] persianDigits = {
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


    @SuppressWarnings("ResultOfMethodCallIgnored")
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
        return dp * (metrics.densityDpi / 160f);
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }


    public static String EnglishDigitToPersianDigit(int value, int countOfDigit) {
        StringBuilder pDigit = new StringBuilder();
        while (value / 10 != 0) {
            pDigit.insert(0, persianDigits[value % 10]);
            value = value / 10;
        }
        pDigit.insert(0, persianDigits[value % 10]);

        if (countOfDigit != -1) {
            int dif = countOfDigit - pDigit.length();
            for (int i = 0; i < dif; i++)
                pDigit.insert(0, persianDigits[0]);
        }
        return pDigit.toString();
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
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }
        return phrase.toString();
    }

}
