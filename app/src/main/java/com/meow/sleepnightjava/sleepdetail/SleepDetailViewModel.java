package com.meow.sleepnightjava.sleepdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meow.sleepnightjava.database.SleepDao;
import com.meow.sleepnightjava.database.SleepNight;

public final class SleepDetailViewModel extends ViewModel {
    // store the details of one single SleepNight
    private final long sleepNightKey;
    private final SleepDao sleepDao;

    private final MediatorLiveData<SleepNight> night = new MediatorLiveData<>();

    // signal the navigation back to SleepTracker
    private final MutableLiveData<Boolean> navigateBack = new MutableLiveData<>(false);

    public LiveData<SleepNight> getNight() {
        return night;
    }

    public LiveData<Boolean> getNavigateBack() {
        return navigateBack;
    }

    public SleepDetailViewModel(long sleepNightKey, SleepDao sleepDao) {
        this.sleepNightKey = sleepNightKey;
        this.sleepDao = sleepDao;
        final LiveData<SleepNight> nightLiveData = sleepDao.getLiveData(sleepNightKey);
        // add the nightLiveData to the mediator's observation
        night.addSource(nightLiveData, night::setValue);
    }

    public void doneNavigating() {
        navigateBack.setValue(false);
    }

    public void onClose() {
        navigateBack.setValue(true);
    }
}
