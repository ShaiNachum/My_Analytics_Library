package com.example.myanalytics;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Analytics.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AnalyticsDao analyticsDao();
}
