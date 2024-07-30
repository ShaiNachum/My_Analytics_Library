# MyAnalytics  Library for Android.

## About The Library:
  MyAnalytics is an Android library for tracking and analyzing user activity within an application.
  It provides automatic activity lifecycle tracking, with easy-to-use data retrieval and export functions.

## Features
- Automatic tracking of Android activity lifecycle events.
- Local storage of analytics data using Room database.
- Asynchronous data operations.
- Data export to text file functionality.
- Average time spent calculation for activities

## Installation
  To integrate MyAnalytics into your Android project add the library dependency to your app module's build.gradle:
```java
dependencies {
    ...
    implementation(project(":myAnalytics"))
}
```

  In order to be able to download the file you need:
  1) Add XML file called provider_paths with the content:
  ```xml
  <?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path name="external_files" path="."/>
</paths>
  ```

2) Add the required permissions to your app's Manifest:
  ```xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />aths>
  ```


