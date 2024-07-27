package com.example.my_analytics_library;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class Activity3 extends AppCompatActivity {
    private MaterialButton A3_BTN_previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        findViews();
        initViews();

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
        A3_BTN_previous.setOnClickListener(View -> previousClicked());
    }

    private void previousClicked() {
        Intent intent = new Intent(Activity3.this, Activity2.class);
        startActivity(intent);
        this.finish();
    }


    private void findViews() {
        A3_BTN_previous = findViewById(R.id.A3_BTN_previous);
    }
}