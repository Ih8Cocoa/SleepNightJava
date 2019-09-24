package com.meow.sleepnightjava.sleeptracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.meow.sleepnightjava.database.SleepDao;

public final class SleepTrackerViewModel extends AndroidViewModel {
    // we need a final DAO instance
    private final SleepDao sleepDao;

    public SleepTrackerViewModel(final SleepDao dao, @NonNull Application application) {
        super(application);
        sleepDao = dao;
    }
}
