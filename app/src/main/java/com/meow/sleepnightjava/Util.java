package com.meow.sleepnightjava;

import android.content.res.Resources;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.core.text.HtmlCompat;

import com.meow.sleepnightjava.database.SleepNight;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

// Just a class that store utility functions
public final class Util {
    private Util() {
    }

    private static String convertNumericQualityToString(final int quality, final Resources res) {
        String qualityString = res.getString(R.string.three_ok);
        switch (quality) {
            case -1:
                qualityString = "--";
                break;
            case 0:
                qualityString = res.getString(R.string.zero_very_bad);
                break;
            case 1:
                qualityString = res.getString(R.string.one_poor);
                break;
            case 2:
                qualityString = res.getString(R.string.two_soso);
                break;
            case 4:
                qualityString = res.getString(R.string.four_pretty_good);
                break;
            case 5:
                qualityString = res.getString(R.string.five_excellent);
                break;
        }
        return qualityString;
    }

    private static String convertLongToDateFormat(final long systemTime) {
        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("EEEE MMM-dd-yyyy ' Time: 'HH:mm", Locale.US);
        return dateFormat.format(systemTime);
    }

    public static Spanned formatNights(final List<SleepNight> nights, final Resources res) {
        // pls help
        final String title = res.getString(R.string.title);
        final String startTimeStr = "<br>" + res.getString(R.string.start_time) + "\t";
        final String endTimeStr = res.getString(R.string.end_time) + "\t";
        final String qualityStr = "<br>" + res.getString(R.string.quality) + "\t";
        final String hoursSleptStr = "<br>" + res.getString(R.string.hours_slept) + ":\t";
        final String brbr = "<br><br>";
        final StringBuilder sb = new StringBuilder();
        sb.append(title);
        for (SleepNight night : nights) {
            final long startTimeMillis = night.getStartTimeMillis();
            final long endTimeMillis = night.getEndTimeMillis();
            final String startTimeMillisStr = convertLongToDateFormat(startTimeMillis);
            sb.append(startTimeStr);
            sb.append(startTimeMillisStr);
            sb.append("<br>");
            if (startTimeMillis != endTimeMillis) {
                final String endTimeMillisStr = convertLongToDateFormat(endTimeMillis);
                final int quality = night.getQuality();
                final String qualityNumericStr = convertNumericQualityToString(quality, res);
                sb.append(endTimeStr);
                sb.append(endTimeMillisStr);
                sb.append(qualityStr);
                sb.append(qualityNumericStr);
                sb.append(hoursSleptStr);
                sb.append((endTimeMillis - startTimeMillis) / 3600000);
                sb.append(":");
                sb.append((endTimeMillis - startTimeMillis) / 60000);
                sb.append(":");
                sb.append((endTimeMillis - startTimeMillis) / 1000);
                sb.append(brbr);
            }
        }

        final String finalString = sb.toString();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return HtmlCompat.fromHtml(finalString, HtmlCompat.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(finalString, Html.FROM_HTML_MODE_LEGACY);
    }

    private static void appendTab(StringBuilder sb) {
        sb.append("\t");
    }
}
