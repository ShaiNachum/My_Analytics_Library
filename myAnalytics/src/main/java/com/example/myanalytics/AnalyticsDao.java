package com.example.myanalytics;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnalyticsDao {

    @Insert
    void insertAll(Analytics... analytics);

    @Delete
    void delete(Analytics analytics);

    @Update
    void updateLogs(Analytics... analytics);

    @Query("SELECT * FROM analytics_data")
    List<Analytics> getAll();

    @Query("SELECT * FROM analytics_data where tag LIKE :tag")
    List<Analytics> getAllByTag(String tag);

    @Query("SELECT * FROM analytics_data where activity_name LIKE :activityName")
        List<Analytics> getAllByActivityName(String activityName);

    @Query("SELECT * FROM analytics_data where date LIKE :date")
    List<Analytics> getAllByActivityDate(String date);

    @Query("SELECT * FROM analytics_data where  time_period > :time_period")
    List<Analytics> getAllActivityBiggerThanTimePeriod(long time_period);

    @Query("DELETE FROM analytics_data WHERE 1")
    void deleteAll();

    @Query("SELECT AVG(time_period) FROM analytics_data WHERE activity_name = :activityName AND tag = 'onActivityStopped'")
    double getAverageTimeSpentInActivity(String activityName);
}
