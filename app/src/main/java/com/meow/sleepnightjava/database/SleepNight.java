package com.meow.sleepnightjava.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

// The SleepNight Entity class
@Entity(tableName = "daily_sleep_quality")
public final class SleepNight {
    @PrimaryKey(autoGenerate = true)
    private long nightId = 0;

    @ColumnInfo(name = "sleep_quality")
    private int quality = -1;

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    @ColumnInfo(name = "start_time_milliseconds")
    private long startTimeMillis = System.currentTimeMillis();

    @ColumnInfo(name = "end_time_milliseconds")
    private long endTimeMillis = startTimeMillis;

    public int getQuality() {
        return quality;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    public long getNightId() {
        return nightId;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setEndTimeMillis(long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }

    public void setNightId(long nightId) {
        this.nightId = nightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SleepNight that = (SleepNight) o;
        return getNightId() == that.getNightId() &&
                getQuality() == that.getQuality() &&
                getStartTimeMillis() == that.getStartTimeMillis() &&
                getEndTimeMillis() == that.getEndTimeMillis();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNightId(), getQuality(), getStartTimeMillis(), getEndTimeMillis());
    }
}
