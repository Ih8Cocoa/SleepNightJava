package com.meow.sleepnightjava.sleeptracker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.meow.sleepnightjava.database.SleepNight;

/**
 * This is a DiffUtil subclass for SleepNight, which will determine
 * whether or not 2 different SleepNights are the same.
 * This information will be used by the RecyclerView to determine
 * whether an item should be re-rendered
 */
final class SleepNightDiffCallback extends DiffUtil.ItemCallback<SleepNight> {
    @Override
    public boolean areItemsTheSame(@NonNull SleepNight oldItem, @NonNull SleepNight newItem) {
        // 2 SleepNight items represents the same SleepNight
        // if they have the same ID
        return oldItem.getNightId() == newItem.getNightId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull SleepNight oldItem, @NonNull SleepNight newItem) {
        // Since equals and hashCode are overridden in the SleepNight class,
        // we can just check for equality
        return oldItem.equals(newItem);
    }
}
