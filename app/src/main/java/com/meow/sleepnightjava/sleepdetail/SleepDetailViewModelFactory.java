package com.meow.sleepnightjava.sleepdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.meow.sleepnightjava.database.SleepDao;

public final class SleepDetailViewModelFactory implements ViewModelProvider.Factory {
    // need these args to initialize the ViewModel
    private final long sleepNightKey;
    private final SleepDao dao;

    public SleepDetailViewModelFactory(long sleepNightKey, SleepDao dao) {
        this.sleepNightKey = sleepNightKey;
        this.dao = dao;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final boolean isAssignable = modelClass.isAssignableFrom(SleepDetailViewModel.class);
        if (isAssignable) {
            return (T) new SleepDetailViewModel(sleepNightKey, dao);
        }
        throw new IllegalArgumentException("Invalid ViewModel detected");
    }
}
