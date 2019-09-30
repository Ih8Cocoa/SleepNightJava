package com.meow.sleepnightjava.sleeptracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.meow.sleepnightjava.database.SleepNight;
import com.meow.sleepnightjava.databinding.ListItemSleepNightBinding;

/**
 * A ViewHolder that holds the SleepNight data
 */
final class SleepNightViewHolder extends ViewHolder {
    // the data binding object
    private final ListItemSleepNightBinding binding;

    private SleepNightViewHolder(@NonNull final ListItemSleepNightBinding binding) {
        // pass in the root item to the superclass, and set the binding field
        super(binding.getRoot());
        this.binding = binding;
    }

    static SleepNightViewHolder from(@NonNull final ViewGroup parent) {
        // Create the current ViewHolder from the parent ViewGroup
        // First, find the parent's context
        final Context parentContext = parent.getContext();
        // Then find the inflater, and use it and Data Binding to inflate the layout
        final LayoutInflater inflater = LayoutInflater.from(parentContext);
        final ListItemSleepNightBinding view =
                ListItemSleepNightBinding.inflate(inflater, parent, false);
        return new SleepNightViewHolder(view);
    }

    // a function to bind the data to the ViewHolder
    void bind(@NonNull final SleepNight item, @NonNull final SleepNightListener listener) {
        // first off, set the SleepNight and the clickListener variable inside the XML
        binding.setSleep(item);
        binding.setClickListener(listener);
        // then refreshes the binding
        binding.executePendingBindings();
    }
}
