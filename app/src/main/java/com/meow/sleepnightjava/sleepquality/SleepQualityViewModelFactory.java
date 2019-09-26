package com.meow.sleepnightjava.sleepquality;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.meow.sleepnightjava.database.SleepDao;

public final class SleepQualityViewModelFactory implements ViewModelProvider.Factory {
    // Pass these into the ViewModel
    private final SleepDao dao;
    private final long nightId;

    public SleepQualityViewModelFactory(SleepDao dao, long nightId) {
        this.dao = dao;
        this.nightId = nightId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final boolean isAssignable = modelClass.isAssignableFrom(SleepQualityViewModel.class);
        if (isAssignable) {
            return (T) new SleepQualityViewModel(dao, nightId);
        }
        throw new IllegalArgumentException("Invalid ViewModel detected");
    }
}
