package com.meow.sleepnightjava.bindingadapters;

import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.meow.sleepnightjava.R;
import com.meow.sleepnightjava.Util;
import com.meow.sleepnightjava.database.SleepNight;

/**
 * A static class that stores binding adapters. This allows us to move
 * the data binding logic to XML file
 */
public class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter(value = "sleepImage")
    public static void setSleepImage(final ImageView view, @Nullable final SleepNight item) {
        if (item == null) {
            return;
        }
        // Set the sleep image in our imageview
        final int quality = item.getQuality();
        final int resId = getSleepQualityImgRes(quality);
        view.setImageResource(resId);
    }

    @BindingAdapter(value = "sleepQuality")
    public static void setSleepQualityString(final TextView view, @Nullable final SleepNight night) {
        if (night == null) {
            return;
        }
        // First, get the night's quality and the view's resources
        final int sleepQuality = night.getQuality();
        final Resources res = view.getResources();
        // and use them to convert the quality to string
        final String qualityStr = Util.convertNumericQualityToString(sleepQuality, res);
        // after that, set the TextView's text to the result string
        view.setText(qualityStr);
    }

    @BindingAdapter(value = "sleepDurationFormatted")
    public static void setSleepDurationFormatted(final TextView view, @Nullable final SleepNight item) {
        if (item == null) {
            return;
        }
        final long startTime = item.getStartTimeMillis();
        final long endTime = item.getEndTimeMillis();
        final Resources res = view.getResources();
        // Use the above variables to convert datetime to string
        final String formatted = Util.convertDurationToFormatted(startTime, endTime, res);
        // then set the text for the view
        view.setText(formatted);
    }

    private static int getSleepQualityImgRes(int quality) {
        final int rtn;
        switch (quality) {
            case 0:
                rtn = R.drawable.ic_sleep_0;
                break;
            case 1:
                rtn = R.drawable.ic_sleep_1;
                break;
            case 2:
                rtn = R.drawable.ic_sleep_2;
                break;
            case 3:
                rtn = R.drawable.ic_sleep_3;
                break;
            case 4:
                rtn = R.drawable.ic_sleep_4;
                break;
            case 5:
                rtn = R.drawable.ic_sleep_5;
                break;
            default:
                rtn = R.drawable.ic_sleep_active;
                break;
        }
        return rtn;
    }
}
