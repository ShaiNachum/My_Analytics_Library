package com.example.myanalytics;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "analytics_data")
public class Analytics {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public String date = "";

    @ColumnInfo(name = "time")
    public String time = "";

    @ColumnInfo(name = "tag")
    public String tag = "";

    @ColumnInfo(name = "activity_name")
    public String activity_name = "";

    @ColumnInfo(name = "time_period")
    public long time_period = 0;

    public Analytics() {}

    public Analytics(String date, String time, String activity_name, String tag, long time_period) {
        this.time = time;
        this.date = date;
        this.tag = tag;
        this.activity_name = activity_name;
        this.time_period = time_period;
    }

    public String getTime() {
        return time;
    }

    public Analytics setTime(String time) {
        this.time = time;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Analytics setDate(String date) {
        this.date = date;
        return this;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public Analytics setActivity_name(String activity_name) {
        this.activity_name = activity_name;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Analytics setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public long getTime_period() {
        return time_period;
    }

    public Analytics setTime_period(long time_period) {
        this.time_period = time_period;
        return this;
    }

    @Override
    public String toString() {
        return "Analytics{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", tag='" + tag + '\'' +
                ", activity_name='" + activity_name + '\'' +
                ", time_period=" + time_period +
                '}';
    }
}
