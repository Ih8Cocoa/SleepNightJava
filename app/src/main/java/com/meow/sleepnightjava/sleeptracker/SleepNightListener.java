package com.meow.sleepnightjava.sleeptracker;

import androidx.core.util.Consumer;

import com.meow.sleepnightjava.database.SleepNight;

public interface SleepNightListener extends Consumer<Long> {
    default void onClick(final SleepNight night) {
        // get the night's ID, then call the the consumer's accept() with that ID
        final long id = night.getNightId();
        accept(id);
    }
}
