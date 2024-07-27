package com.example.myanalytics;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AnalyticsContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        Log.d("AnalyticsContentProvider", "onCreate");
        AnalyticsDataHelper.initHelper(getContext().getApplicationContext());

        // Register the ActivityLifecycleCallbacks
        if (getContext() != null && getContext().getApplicationContext() instanceof Application) {
            Application application = (Application) getContext().getApplicationContext();
            application.registerActivityLifecycleCallbacks(new AnalyticsLifecycleCallbacks());
        }

        return false;
        //return true; // Return true since the ContentProvider was successfully loaded
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
