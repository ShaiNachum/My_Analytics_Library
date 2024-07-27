package com.example.myanalytics;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AnalyticsDataHelper {
    private static AnalyticsDataHelper instance;
    private static AppDatabase appDatabase;



    private AnalyticsDataHelper(Context context) {
//        appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AnalyticsDB.db")
//                .addMigrations(MIGRATION_1_2)
//                .build();

        // use the below instead of the above in order to avoid working with threads. (not recommended)

        appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "MinLogsDB.db")
                // allow queries on the main thread.
                // Don't do this on a real app! See PersistenceBasicSample for an example.
                // .allowMainThreadQueries()
                .build();
    }

    private Migration MIGRATION_1_2 = new Migration(1, 2) { // if you add new column, update version in AppDatabase class.
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //example: add new column to table (if it doesn't exist, priority is the name of the new column)
            //database.execSQL("ALTER TABLE analytics_data ADD COLUMN priority INTEGER NOT NULL DEFAULT '1'");
        }
    };


    public static AnalyticsDataHelper getInstance() {
        return instance;
    }

    public static AnalyticsDataHelper initHelper(Context context) {
        if (instance == null) {
            instance = new AnalyticsDataHelper(context);
        }

        return instance;
    }


    public interface CallBack_Analytics {
        void dataReady(List<Analytics> analytics);
    }

    public void addActivity(String activity_name, String tag, long time_period) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
        String time = now.format(formatter);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.analyticsDao().insertAll(
                        new Analytics(date, time, activity_name, tag, time_period)
                );
            }
        }).start();
    }



    public void getAllLogsByTag(String tag, CallBack_Analytics callBack_Analytics) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAllByTag(tag);
                if (callBack_Analytics != null) {
                    callBack_Analytics.dataReady(analytics);
                }
            }
        }).start();
    }


    public void getAllLogsByActivityName(String activity_name, CallBack_Analytics callBack_Analytics) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAllByActivityName(activity_name);
                if (callBack_Analytics != null) {
                    callBack_Analytics.dataReady(analytics);
                }
            }
        }).start();
    }

    public void getAllByActivityDate(String date, CallBack_Analytics callBack_Analytics) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAllByActivityDate(date);
                if (callBack_Analytics != null) {
                    callBack_Analytics.dataReady(analytics);
                }
            }
        }).start();
    }

    public void getAllActivityBiggerThanTimePeriod(long time_period, CallBack_Analytics callBack_Analytics) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAllActivityBiggerThanTimePeriod(time_period);
                if (callBack_Analytics != null) {
                    callBack_Analytics.dataReady(analytics);
                }
            }
        }).start();
    }

    public void getAll(CallBack_Analytics callBack_logs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAll();
                if (callBack_logs != null) {
                    callBack_logs.dataReady(analytics);
                }
            }
        }).start();
    }

    public void deleteAllAnalytics() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.analyticsDao().deleteAll();
            }
        }).start();
    }

    //new from here

    public void getAllDataAsString(CallBack_AnalyticsString callBack_analyticsString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAll();
                StringBuilder stringBuilder = new StringBuilder();
                for (Analytics analytic : analytics) {
                    stringBuilder.append(analytic.toString()).append("\n");
                }
                if (callBack_analyticsString != null) {
                    callBack_analyticsString.dataReady(stringBuilder.toString());
                }
            }
        }).start();
    }

    public interface CallBack_AnalyticsString {
        void dataReady(String analyticsData);
    }

    public void downloadAnalyticsData(Context context, CallBack_FileDownload callBack_fileDownload) {
        getAllDataAsString(new CallBack_AnalyticsString() {
            @Override
            public void dataReady(String analyticsData) {
                try {
                    File file = FileHelper.writeToFile(context, "analytics_data.txt", analyticsData);
                    if (callBack_fileDownload != null) {
                        callBack_fileDownload.onFileDownloaded(file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (callBack_fileDownload != null) {
                        callBack_fileDownload.onError(e);
                    }
                }
            }
        });
    }

    public void downloadAnalyticsDataForActivity(Context context, String activityName, CallBack_FileDownload callBack_fileDownload) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Analytics> analytics = appDatabase.analyticsDao().getAllByActivityName(activityName);
                StringBuilder stringBuilder = new StringBuilder();
                for (Analytics analytic : analytics) {
                    stringBuilder.append(analytic.toString()).append("\n");
                }
                String analyticsData = stringBuilder.toString();

                try {
                    File file = FileHelper.writeToFile(context, "analytics_" + activityName + ".txt", analyticsData);
                    //File file = FileHelper.writeToFile(context, "analytics_data.txt", analyticsData);
                    if (callBack_fileDownload != null) {
                        callBack_fileDownload.onFileDownloaded(file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (callBack_fileDownload != null) {
                        callBack_fileDownload.onError(e);
                    }
                }
            }
        }).start();
    }

    public interface CallBack_FileDownload {
        void onFileDownloaded(File file);
        void onError(Exception e);
    }

}
