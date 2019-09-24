package com.meow.sleepnightjava;

import android.content.res.Resources;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Locale;

// Just a class that store utility functions
public final class Util {
    private Util() {
    }

    public static String convertNumericQualityToString(final int quality, final Resources res) {
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

    public static String convertLongToDateFormat(final long systemTime) {
        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("EEEE MMM-dd-yyyy ' Time: 'HH:mm", Locale.US);
        return dateFormat.format(systemTime);
    }

    public static Spanned formatNights() {
        return null;
    }
}
