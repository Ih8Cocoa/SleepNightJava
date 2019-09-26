package com.meow.sleepnightjava.sleeptracker;

import android.app.Application;
import android.content.res.Resources;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.meow.sleepnightjava.Util;
import com.meow.sleepnightjava.database.SleepDao;
import com.meow.sleepnightjava.database.SleepNight;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

public final class SleepTrackerViewModel extends AndroidViewModel {
    // we need a final DAO instance
    private final SleepDao sleepDao;

    private final MutableLiveData<SleepNight> tonight = new MutableLiveData<>();
    private final LiveData<List<SleepNight>> nights;

    // transform original "nights" data into HTML
    private final LiveData<Spanned> nightsString;
    private final List<Disposable> disposables = new ArrayList<>();

    // Use LiveData to check whether the buttons should be visible
    private final LiveData<Boolean> startButtonVisible;
    private final LiveData<Boolean> stopButtonVisible;
    private final LiveData<Boolean> clearButtonVisible;

    // This will signal an event to navigate to SleepQuality fragment
    private final MutableLiveData<SleepNight> navigateToSleepQuality = new MutableLiveData<>();

    // This will signal whether to show a snackbar or not
    private final MutableLiveData<Boolean> showSnackbarEvent = new MutableLiveData<>(false);

    SleepTrackerViewModel(final SleepDao dao, @NonNull Application application) {
        super(application);
        sleepDao = dao;
        nights = sleepDao.getAllNights();
        nightsString = Transformations.map(nights, data -> {
            final Resources appResource = application.getResources();
            return Util.formatNights(data, appResource);
        });
        // since clearButtonVisible has dependency on nights,
        // initialize it inside the constructor
        initializeTonight();
        startButtonVisible = Transformations.map(
                tonight, n -> n == null
        );
        stopButtonVisible = Transformations.map(
                tonight, n -> n != null
        );
        clearButtonVisible = Transformations.map(
                nights, list -> !list.isEmpty()
        );
    }

    public LiveData<Spanned> getNightsString() {
        return nightsString;
    }

    public LiveData<SleepNight> getNavigateToSleepQuality() {
        return navigateToSleepQuality;
    }

    public LiveData<Boolean> getShowSnackbarEvent() {
        return showSnackbarEvent;
    }

    public LiveData<Boolean> getStartButtonVisible() {
        return startButtonVisible;
    }

    public LiveData<Boolean> getStopButtonVisible() {
        return stopButtonVisible;
    }

    public LiveData<Boolean> getClearButtonVisible() {
        return clearButtonVisible;
    }

    public void doneNavigating() {
        navigateToSleepQuality.setValue(null);
    }

    public void doneShowingSnackbar() {
        showSnackbarEvent.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }

    public void onStartTracking() {
        final SleepNight night = new SleepNight();
        final Disposable d = sleepDao.insert(night)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initializeTonight);
        disposables.add(d);
    }

    public void onStopTracking() {
        final SleepNight oldNight = tonight.getValue();
        if (oldNight == null) {
            return;
        }
        final long currentTime = System.currentTimeMillis();
        oldNight.setEndTimeMillis(currentTime);

        // update the night
        final Disposable d = sleepDao.update(oldNight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // After stopping, make a signal to navigate to the SleepQuality fragment
                .subscribe(() -> navigateToSleepQuality.setValue(oldNight));
        disposables.add(d);
    }

    public void onClear() {
        final Disposable d = sleepDao.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> tonight.setValue(null));
        disposables.add(d);
        // beside setting "tonight" to null, signal the fragment to show the snackbar
        showSnackbarEvent.setValue(true);
    }

    private void initializeTonight() {
        final Disposable d = sleepDao.getTonight()
                .flatMap(tonight -> {
                    // if the end time is different from the start time,
                    // it is not tonight, so set the Maybe value to empty
                    final long startTime = tonight.getStartTimeMillis();
                    final long endTime = tonight.getEndTimeMillis();
                    if (startTime != endTime) {
                        return Maybe.empty();
                    }
                    return Maybe.just(tonight);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // once the result is back, set tonight's value
                        tonight::setValue,
                        // flush all errors
                        Functions.ON_ERROR_MISSING,
                        // is the Maybe is empty, set tonight variable to null
                        () -> tonight.setValue(null)
                );
        disposables.add(d);
    }
}
