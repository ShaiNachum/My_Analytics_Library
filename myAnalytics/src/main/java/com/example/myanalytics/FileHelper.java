package com.example.myanalytics;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileHelper {
    public static File writeToFile(Context context, String baseFileName, String content) throws IOException {
        // Create a timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

        // Create a unique filename with the timestamp
        String fileName = baseFileName.replace(".txt", "") + "_" + timeStamp + ".txt";

        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 and above
            file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        } else {
            // For Android 9 and below
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs();
            }
            file = new File(downloadsDir, fileName);
        }

        FileWriter writer = new FileWriter(file);
        writer.append(content);
        writer.flush();
        writer.close();
        return file;
    }
}