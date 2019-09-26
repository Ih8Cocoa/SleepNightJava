package com.meow.sleepnightjava.sleepquality;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meow.sleepnightjava.database.SleepDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class SleepQualityViewModel extends ViewModel {
    // We need a key to reference the sleep that we're doing the rating on,
    // and a DAO instance
    private final SleepDao dao;
    private final long nightId;

    // Signal the navigation back to SleepTracker
    private final MutableLiveData<Boolean> navigateBackToTracker =
            new MutableLiveData<>(false);

    // unsub when the ViewModel is destroyed
    private final List<Disposable> disposables = new ArrayList<>();

    SleepQualityViewModel(SleepDao dao, long nightId) {
        this.dao = dao;
        this.nightId = nightId;
    }

    public LiveData<Boolean> getNavigateBackToTracker() {
        return navigateBackToTracker;
    }

    public void onSetSleepQuality(final int quality) {
        final Disposable d = dao.get(nightId)
                .flatMapCompletable(night -> {
                    night.setQuality(quality);
                    return dao.update(night);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> navigateBackToTracker.setValue(true));
        disposables.add(d);
    }

    public void doneNavigating() {
        navigateBackToTracker.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (Disposable d : disposables) {
            d.dispose();
        }
    }
}
