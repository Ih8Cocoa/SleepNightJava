package com.meow.sleepnightjava.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// This class represents the database
@Database(entities = {SleepNight.class}, version = 1, exportSchema = false)
public abstract class SleepDatabase extends RoomDatabase {
    // Set an abstract method that returns the DAO
    public abstract SleepDao sleepDao();

    // declare this class as a Singleton
    @Nullable
    private static volatile SleepDatabase INSTANCE = null;

    public static SleepDatabase getInstance(@NonNull final Context context) {
        synchronized (SleepDatabase.class) {
            // get the instance
            SleepDatabase currentInstance = INSTANCE;
            if (currentInstance == null) {
                // The database is not initialized yet
                final Context appContext = context.getApplicationContext();
                currentInstance =
                        Room.databaseBuilder(
                                appContext, SleepDatabase.class, "sleep_history_db"
                        )
                        .fallbackToDestructiveMigration()
                        .build();
                // set back the instance
                INSTANCE = currentInstance;
            }
            return INSTANCE;
        }
    }
}
