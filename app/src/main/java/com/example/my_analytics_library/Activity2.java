package com.example.my_analytics_library;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class Activity2 extends AppCompatActivity {

    private MaterialButton A2_BTN_next;
    private MaterialButton A2_BTN_previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

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
        A2_BTN_next.setOnClickListener(View -> nextClicked());
        A2_BTN_previous.setOnClickListener(View -> previousClicked());
    }

    private void previousClicked() {
        Intent intent = new Intent(Activity2.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void nextClicked() {
        Intent intent = new Intent(Activity2.this, Activity3.class);
        startActivity(intent);
        this.finish();
    }



    private void findViews() {
        A2_BTN_next = findViewById(R.id.A2_BTN_next);
        A2_BTN_previous = findViewById(R.id.A2_BTN_previous);
    }
}