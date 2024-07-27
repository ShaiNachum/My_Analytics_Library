package com.example.my_analytics_library;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myanalytics.Analytics;
import com.example.myanalytics.AnalyticsDataHelper;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MaterialButton main_BTN_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

        //AnalyticsDataHelper.getInstance().addActivity("MainActivity", "onCreate", System.currentTimeMillis());
        AnalyticsDataHelper.getInstance().getAllActivityBiggerThanTimePeriod(1, new AnalyticsDataHelper.CallBack_Analytics() {
            @Override
            public void dataReady(List<Analytics> analytics) {
                printAnalytics(analytics);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initViews() {
        main_BTN_next.setOnClickListener(View -> nextClicked());
    }

    private void nextClicked() {
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
        this.finish();
    }


    private void findViews() {
        main_BTN_next = findViewById(R.id.main_BTN_next);
    }

    private void printAnalytics(List<Analytics> analytics) {
        for (Analytics a : analytics) {
            Log.d("shai", a.toString());
        }
    }
}