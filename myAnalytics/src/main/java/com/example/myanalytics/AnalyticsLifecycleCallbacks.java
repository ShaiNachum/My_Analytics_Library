package com.example.myanalytics;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class AnalyticsLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private Map<Activity, Long> startTimeMap = new HashMap<>();


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // Record the start time of the activity
        startTimeMap.put(activity, System.currentTimeMillis());

        // Record the creation of the activity
        AnalyticsDataHelper.getInstance().addActivity(
                activity.getLocalClassName(),
                "onActivityCreated",
                0 // Time period is 0 since it's just created
        );
    }

    @Override
    public void onActivityStarted(Activity activity) {
        AnalyticsDataHelper.getInstance().addActivity(
                activity.getLocalClassName(),
                "onActivityStarted",
                0 // Time period is 0 since it's just destroyed
        );
    }

    @Override
    public void onActivityResumed(Activity activity) {
        AnalyticsDataHelper.getInstance().addActivity(
                activity.getLocalClassName(),
                "onActivityResumed",
                0 // Time period is 0 since it's just destroyed
        );
    }

    @Override
    public void onActivityPaused(Activity activity) {
        AnalyticsDataHelper.getInstance().addActivity(
                activity.getLocalClassName(),
                "onActivityPaused",
                0 // Time period is 0 since it's just destroyed
        );
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // Calculate the time spent in the activity
        Long startTime = startTimeMap.get(activity);
        if (startTime != null) {
            long timeSpentMillis = System.currentTimeMillis() - startTime;
            long timeSpentSeconds = timeSpentMillis / 1000; // Convert milliseconds to seconds
            // Record the time spent in the activity
            AnalyticsDataHelper.getInstance().addActivity(
                    activity.getLocalClassName(),
                    "onActivityStopped",
                    timeSpentSeconds
            );
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        AnalyticsDataHelper.getInstance().addActivity(
                activity.getLocalClassName(),
                "onActivitySaveInstanceState",
                0 // Time period is 0 since it's just destroyed
        );
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // Record the destruction of the activity
        AnalyticsDataHelper.getInstance().addActivity(
                activity.getLocalClassName(),
                "onActivityDestroyed",
                0 // Time period is 0 since it's just destroyed
        );
        // Remove the start time from the map
        startTimeMap.remove(activity);
    }
}