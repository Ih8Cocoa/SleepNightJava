package com.meow.sleepnightjava.sleeptracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.meow.sleepnightjava.database.SleepDao;

public final class SleepTrackerViewModelFactory implements ViewModelProvider.Factory {
    // We need a data source (DAO) and the application
    // to put into the ViewModel constructor
    private final SleepDao sleepDao;
    private final Application app;

    public SleepTrackerViewModelFactory(final SleepDao sleepDao, final Application app) {
        this.sleepDao = sleepDao;
        this.app = app;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final boolean isAssignable = modelClass.isAssignableFrom(SleepTrackerViewModel.class);
        if (isAssignable) {
            return (T) new SleepTrackerViewModel(sleepDao, app);
        }
        // Incorrect ViewModel detected
        throw new IllegalArgumentException("Incorrect ViewModel detected");
    }
}
