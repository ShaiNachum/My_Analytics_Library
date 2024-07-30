# MyAnalytics  Library for Android.

## About The Library:
  MyAnalytics is an Android library for tracking and analyzing user activity within an application.
  It provides automatic activity lifecycle tracking, with easy-to-use data retrieval and export functions.

## Features
- Automatic tracking of Android activity lifecycle events.
- Local storage of analytics data using RoomSQL database.
- Asynchronous data operations.
- Flexible querying options (by tag, activity name, date, time period)
- Average time spent calculation for activities
- Easy data export to text files functionality.
- Timestamp-based file naming for unique exports

## Installation
  To integrate MyAnalytics into your Android project add the library dependency to your app module's build.gradle:
```java
dependencies {
    ...
    implementation(project(":myAnalytics"))
}
```

###In order to be able to download the file you need:
  1) Create a file named provider_paths.xml in the res/xml/ directory:
  ```xml
  <?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path name="external_files" path="."/>
</paths>
  ```

  2) Add the following permission to your AndroidManifest.xml file:
    ```xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />aths>
    ```

  3) Make sure you have a FileProvider defined in your AndroidManifest.xml:
  ```xml
   <application
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>
        <!--
        till here need to add, and also the provider class
        -->
    </application>
  ```
4) To use the downloadAnalyticsData or downloadAnalyticsForSpecificActivity functions from your analytics library to download the file to the Downloads directory on your phone, you'll need to follow these steps:
  1) Add a click listener to your download button.
  2) Request storage permissions if needed (for Android 6.0 and above).
  3) Call the downloadAnalyticsData function and handle the callback.

Here's how you can modify your MainActivity to implement this:

```java
public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;
    ...
    private MaterialButton downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
        
    }

    private void initViews() {
        ...
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


    private void findViews() {
        ...
        downloadButton = findViewById(R.id.main_BTN_download);
    }
}
```


## Usage
The library automatically initializes itself using a ContentProvider. No manual initialization is required.
The library automatically tracks activity lifecycle events. No additional code is required for basic tracking.

exampels for using the library:

Get average time spent in activity:
```java
        AnalyticsDataHelper.getInstance().getAverageTimeSpentInActivity("MainActivity", new AnalyticsDataHelper.CallBack_AverageTime() {
            @Override
            public void onAverageTimeCalculated(double averageTimeInSeconds) {
                // Use the average time here
                Log.d("shai", "Average time spent in YourActivityName: " + averageTimeInSeconds + " seconds");
            }
        });
```

Get all activity bigger than time period:
```java
        AnalyticsDataHelper.getInstance().getAllActivityBiggerThanTimePeriod(1, new AnalyticsDataHelper.CallBack_Analytics() {
            @Override
            public void dataReady(List<Analytics> analytics) {
                printAnalytics(analytics);
            }
        });
```

DeleteAllAnalytics:
```java
        AnalyticsDataHelper.getInstance().deleteAllAnalytics();
```

Get all analytics:
```java
        AnalyticsDataHelper.getInstance().getAllAnalytics(new AnalyticsDataHelper.CallBack_Analytics() {
            @Override
            public void dataReady(List<Analytics> analytics) {
                printAnalytics(analytics);
            }
        });
```

Get all analytics by tag:
```java
        AnalyticsDataHelper.getInstance().getAllAnalyticsByTag("onActivityResumed" , new AnalyticsDataHelper.CallBack_Analytics() {
            @Override
            public void dataReady(List<Analytics> analytics) {
                printAnalytics(analytics);
            }
        });
```

Get all analytics by activity name:
```java
        AnalyticsDataHelper.getInstance().getAllAnalyticsByActivityName("Activity2", new AnalyticsDataHelper.CallBack_Analytics() {
            @Override
            public void dataReady(List<Analytics> analytics) {
                printAnalytics(analytics);
            }
        });
```

Get all analytics by activity date:
```java
        AnalyticsDataHelper.getInstance().getAllAnalyticsByActivityDate("2024-07-27", new AnalyticsDataHelper.CallBack_Analytics() {
            @Override
            public void dataReady(List<Analytics> analytics) {
                printAnalytics(analytics);
            }
        });
```

### License:
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
