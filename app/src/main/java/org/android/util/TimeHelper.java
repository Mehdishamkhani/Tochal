package org.android.util;

import org.android.data.model.TimeModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeHelper {


    private static final String inputFormat = "HH:mm";


    public static TimeModel compareDates(String compareStringOne, String compareStringTwo) {

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        Date date = parseDate(hour + ":" + minute);
        Date dateCompareOne = parseDate(compareStringOne);
        Date dateCompareTwo = parseDate(compareStringTwo);

        TimeModel timeModel = TimeModel.getInstance();

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)) {

            timeModel.setRemainingTime(dateCompareTwo.getTime() - date.getTime());
            timeModel.setType(1);

        } else {
            if (dateCompareOne.getTime() < date.getTime())
                timeModel.setRemainingTime(86400000 + (dateCompareOne.getTime() - date.getTime()));
            else
                timeModel.setRemainingTime(dateCompareOne.getTime() - date.getTime());
            timeModel.setType(2);
        }

        return timeModel;

    }


    public static boolean getStationStatus(String compareStringOne, String compareStringTwo) {

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        Date date = parseDate(hour + ":" + minute);
        Date dateCompareOne = parseDate(compareStringOne);
        Date dateCompareTwo = parseDate(compareStringTwo);

        return dateCompareOne.before(date) && dateCompareTwo.after(date);

    }

    private static Date parseDate(String date) {

        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.getDefault());

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }



}
