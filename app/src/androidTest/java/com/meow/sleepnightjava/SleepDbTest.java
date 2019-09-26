package com.meow.sleepnightjava;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.meow.sleepnightjava.database.SleepDao;
import com.meow.sleepnightjava.database.SleepDatabase;
import com.meow.sleepnightjava.database.SleepNight;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

// Test the connection to the SleepDatabase Room DB
public final class SleepDbTest {
    // first, define the database and DAO
    private SleepDao sleepDao;
    private SleepDatabase db;

    @Before
    public void createDb() {
        // create the database instance
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Since it's a test, an in-memory DB can be used
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase.class)
                // also allow main-thread queries since it's a test
                .allowMainThreadQueries()
                .build();
        // initialize the DAO
        sleepDao = db.sleepDao();
    }

    @After
    public void closeDb() {
        // Close the database after the test
        db.close();
    }

    @Test
    public void insertAndGetNight() {
        // try inserting and getting out record
        final SleepNight night = new SleepNight();
        // since it's just a test, we can block the code until completion
        sleepDao.insert(night).blockingAwait();
        final SleepNight tonight = sleepDao.getTonight().blockingGet();
        Assert.assertNotNull(tonight);
        // ensure that the quality is -1
        Assert.assertEquals(-1, tonight.getQuality());
    }
}
