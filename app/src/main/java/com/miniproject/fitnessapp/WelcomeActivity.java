package com.miniproject.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
                boolean firstStart = sharedPreferences.getBoolean("firstStart", true);

                if (firstStart) {
                    WeightDialog dialog = new WeightDialog();
                    dialog.show(getSupportFragmentManager(), "Enter Weight");
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, ExerciseCategoriesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}