package com.example.my_analytics_library;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.myanalytics.Analytics;
import com.example.myanalytics.AnalyticsDataHelper;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;
    private MaterialButton main_BTN_next;
    private MaterialButton downloadButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

//        AnalyticsDataHelper.getInstance().getAverageTimeSpentInActivity("MainActivity", new AnalyticsDataHelper.CallBack_AverageTime() {
//            @Override
//            public void onAverageTimeCalculated(double averageTimeInSeconds) {
//                // Use the average time here
//                Log.d("shai", "Average time spent in YourActivityName: " + averageTimeInSeconds + " seconds");
//            }
//        });


//        AnalyticsDataHelper.getInstance().getAllActivityBiggerThanTimePeriod(1, new AnalyticsDataHelper.CallBack_Analytics() {
//            @Override
//            public void dataReady(List<Analytics> analytics) {
//                printAnalytics(analytics);
//            }
//        });


        //AnalyticsDataHelper.getInstance().deleteAllAnalytics();


//        AnalyticsDataHelper.getInstance().getAll(new AnalyticsDataHelper.CallBack_Analytics() {
//            @Override
//            public void dataReady(List<Analytics> analytics) {
//                printAnalytics(analytics);
//            }
//        });


//        AnalyticsDataHelper.getInstance().getAllLogsByTag("onActivityResumed" , new AnalyticsDataHelper.CallBack_Analytics() {
//            @Override
//            public void dataReady(List<Analytics> analytics) {
//                printAnalytics(analytics);
//            }
//        });


//        AnalyticsDataHelper.getInstance().getAllLogsByActivityName("Activity2", new AnalyticsDataHelper.CallBack_Analytics() {
//            @Override
//            public void dataReady(List<Analytics> analytics) {
//                printAnalytics(analytics);
//            }
//        });


//        AnalyticsDataHelper.getInstance().getAllByActivityDate("2024-07-27", new AnalyticsDataHelper.CallBack_Analytics() {
//            @Override
//            public void dataReady(List<Analytics> analytics) {
//                printAnalytics(analytics);
//            }
//        });
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
        downloadButton.setOnClickListener(View -> checkPermissionAndDownload());
    }

    //for downloading the file:
    //from here
    private void checkPermissionAndDownload() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            // For Android 9 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);
            } else {
                //downloadAnalyticsData();
                downloadAnalyticsForSpecificActivity("MainActivity");
            }
        } else {
            // For Android 10 and above
            //downloadAnalyticsData();
            downloadAnalyticsForSpecificActivity("MainActivity");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //downloadAnalyticsData();
                downloadAnalyticsForSpecificActivity("MainActivity");
            } else {
                Toast.makeText(this, "Storage permission is required to download the file", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void downloadAnalyticsData() {
        AnalyticsDataHelper.getInstance().downloadAnalyticsData(this, new AnalyticsDataHelper.CallBack_FileDownload() {
            @Override
            public void onFileDownloaded(File file) {
                runOnUiThread(() -> {
                    String filePath = file.getAbsolutePath();
                    Log.d("FileDownload", "File downloaded to: " + filePath);
                    Toast.makeText(MainActivity.this, "File downloaded: " + filePath, Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Log.e("FileDownload", "Error downloading file", e);
                    Toast.makeText(MainActivity.this, "Error downloading file: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void downloadAnalyticsForSpecificActivity(String activityName) {
        AnalyticsDataHelper.getInstance().downloadAnalyticsDataForActivity(this, activityName, new AnalyticsDataHelper.CallBack_FileDownload() {
            @Override
            public void onFileDownloaded(File file) {
                runOnUiThread(() -> {
                    String filePath = file.getAbsolutePath();
                    Log.d("FileDownload", "File downloaded to: " + filePath);
                    Toast.makeText(MainActivity.this, "File downloaded for " + activityName + ": " + filePath, Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Log.e("FileDownload", "Error downloading file for " + activityName, e);
                    Toast.makeText(MainActivity.this, "Error downloading file for " + activityName + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void openFile(File file) {
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/plain");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Open File"));
    }
//till here


    private void nextClicked() {
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
        this.finish();
    }


    private void findViews() {
        main_BTN_next = findViewById(R.id.main_BTN_next);
        downloadButton = findViewById(R.id.main_BTN_download);
    }

    private void printAnalytics(List<Analytics> analytics) {
        for (Analytics a : analytics) {
            Log.d("shai", a.toString());
        }
    }
}