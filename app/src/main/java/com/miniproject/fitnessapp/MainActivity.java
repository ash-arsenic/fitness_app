package com.miniproject.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Button mCreateExercise;
    private Button mExerciseCategory;
    private BottomNavigationView mNavigationView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreateExercise = findViewById(R.id.create_exercise);
        mCreateExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CongratulationActivity.class);
                intent.putExtra("timeTaken", 150);
                intent.putExtra("calories", 164);
                intent.putExtra("exercise_count", 16);
                intent.putExtra("category",0);
                startActivity(intent);
            }
        });

        mExerciseCategory = findViewById(R.id.exerciseCategoryOpener);
        mExerciseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseCategoriesActivity.class);
                startActivity(intent);
            }
        });

        mNavigationView = findViewById(R.id.navigation_bar);
        mNavigationView.setSelectedItemId(R.id.navigation_home);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_home:
                        return true;

                    case R.id.navigation_add:
                        intent = new Intent(getApplicationContext(), CreateExerciseActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

//                    case R.id.navigation_meditation:
//                        intent = new Intent(getApplicationContext(), ExerciseCategoriesActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
//                        return true;

                    case R.id.navigation_report:
                        intent = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intent);
                        return true;

//                    case R.id.navigation_settings:
//                        Toast.makeText(getApplicationContext(), "Settings Clicked", Toast.LENGTH_SHORT).show();
//                        return true;

                    default:
                        return true;
                }
            }
        });
    }
}