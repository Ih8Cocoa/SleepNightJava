package com.meow.sleepnightjava.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

// Mark this interface as Data-Access Object
@Dao
public interface SleepDao {
    // Add methods to this DAO
    @Insert
    Completable insert(final SleepNight night);

    @Update
    Completable update(final SleepNight night);

    @Delete
    Completable delete(final SleepNight night);

    // Select all nights order by ID descending order
    @Query("SELECT * FROM daily_sleep_quality WHERE nightId = :id LIMIT 1")
    Maybe<SleepNight> get(final long id);

    // Delete everything from the database
    @Query("DELETE FROM daily_sleep_quality")
    Completable deleteAll();

    // Get everything from DB as LiveData, order by ID in descending order
    @Query("SELECT * FROM daily_sleep_quality ORDER BY nightId DESC")
    LiveData<List<SleepNight>> getAllNights();

    // Get the most recent night
    @Query("SELECT * FROM daily_sleep_quality ORDER BY nightId DESC LIMIT 1")
    Maybe<SleepNight> getTonight();
}
