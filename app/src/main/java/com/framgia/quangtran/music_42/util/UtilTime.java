package com.framgia.quangtran.music_42.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UtilTime {
    private static final int MILLITIME = 1000;
    private static final int HOURS_TO_SECONDS = 3600;
    private static final int HUNDRED = 100;
    private static final int ONE_HOUR = 60;
    private static final String HOUR_DEFAULT = "00:00";
    private static final String TIME_FORMAT = "mm:ss";

    public String TotalTime(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        return dateFormat.format(milliseconds);
    }

    //update progressbar
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;
        long currentSeconds = (int) (currentDuration / MILLITIME);
        long totalSeconds = (int) (totalDuration / MILLITIME);
        double phantram = (double) (currentSeconds * HUNDRED / totalSeconds);
        percentage = ((double) (currentSeconds * HUNDRED / totalSeconds));
        return percentage.intValue();
    }

    //progress tracking then song stop here
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / MILLITIME;
        currentDuration = (int) ((((double) progress) / HUNDRED) * totalDuration);
        return currentDuration * MILLITIME;
    }
}
