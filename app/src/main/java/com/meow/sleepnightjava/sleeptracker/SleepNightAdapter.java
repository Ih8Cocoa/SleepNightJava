package com.meow.sleepnightjava.sleeptracker;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.meow.sleepnightjava.database.SleepNight;

/**
 * An adapter for the RecyclerView, which uses ListAdapter with specified callback
 */
public final class SleepNightAdapter extends ListAdapter<SleepNight, SleepNightViewHolder> {
    // Add a SleepNightListener as a field
    private final SleepNightListener listener;
    SleepNightAdapter(@NonNull SleepNightListener listener) {
        // Create the callback and pass it into ListAdapter's constructor
        super(new SleepNightDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public SleepNightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create the ViewHolder
        return SleepNightViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SleepNightViewHolder holder, int position) {
        // use getItem() to get the item in the specified position
        final SleepNight item = getItem(position);
        // call the bind method inside ViewHolder
        holder.bind(item, listener);
    }
}
