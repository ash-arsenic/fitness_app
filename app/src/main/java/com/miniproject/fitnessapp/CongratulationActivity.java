package com.miniproject.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CongratulationActivity extends AppCompatActivity {

    private TextView mTimeTaken;
    private TextView mExerciseCount;
    private TextView mCaloriesBurned;
    private Button mShareWorkout;
    private Button mRestartWorkout;
    private Button mNextButton;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        Bundle args = getIntent().getExtras();
        int time = args.getInt("timeTaken");
        int calories = args.getInt("calories");
        int exerciseCount = args.getInt("exercise_count");
        final int category = args.getInt("category");

        ExercisesListFragment.count = 0;
        StartExerciseActivity.calories = 0;

        int min = time/60;
        int temp = time-min*60;
        int sec = temp%60;

        String timeFormat = String.format("%02d:%02d", min, sec);
        switch(category) {
            case 0:
                workoutName = "CHEST BEGINNERS";
                break;

            case 1:
                workoutName = "CHEST INTERMEDIATE";
                break;

            case 2:
                workoutName = "CHEST ADVANCED";
                break;

            case 3:
                workoutName = "ABS BEGINNERS";
                break;

            case 4:
                workoutName = "ABS INTERMEDIATE";
                break;

            case 5:
                workoutName = "ABS ADVANCED";
                break;

            case 6:
                workoutName = "LEG BEGINNERS";
                break;

            case 7:
                workoutName = "LEG INTERMEDIATE";
                break;

            case 8:
                workoutName = "LEG ADVANCED";
                break;

            default:
                workoutName = "USER CREATED EXERCISE";
        }

        String date = "18/11/2020";

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        DataBaseReport db = new DataBaseReport(this);
        db.addOne(new WorkoutReport(date, workoutName, time, calories));

        mTimeTaken = findViewById(R.id.timeTaken);
        mTimeTaken.setText(timeFormat);

        mCaloriesBurned = findViewById(R.id.calories);
        mCaloriesBurned.setText(String.valueOf(calories));

        mExerciseCount = findViewById(R.id.congratulation_exercise_count);
        mExerciseCount.setText(String.valueOf(exerciseCount));

        mShareWorkout = findViewById(R.id.congratulation_share_workout);
        mShareWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "C'mon workout with me!!! and prove yourself");
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(ReportActivity.SHARED_PREFS, MODE_PRIVATE);
        int sharedCalories = sharedPreferences.getInt(ReportActivity.CALORIES, 0);
        int sharedTime = sharedPreferences.getInt(ReportActivity.TIME, 0);
        int sharedWorkouts = sharedPreferences.getInt(ReportActivity.WORKOUTS, 0);

        sharedCalories += calories;
        sharedTime = sharedTime + (time);
        sharedWorkouts += exerciseCount;

        saveData(sharedCalories, sharedTime, sharedWorkouts);

        mRestartWorkout = findViewById(R.id.congratlation_restart_workout);
        if(category < 10) {
            mRestartWorkout.setClickable(true);
            mRestartWorkout.setTextColor(Color.parseColor("#6200EE"));
        } else {
            mRestartWorkout.setClickable(false);
            mRestartWorkout.setTextColor(Color.GRAY);
        }
        mRestartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category < 10) {
                    Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                    intent.putExtra("idx", category);
                    startActivity(intent);
                    finish();
                } else {

                }
            }
        });

        mNextButton = findViewById(R.id.congratulation_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CongratulationActivity.this, ReportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void saveData(int calories, int time, int workout) {
        SharedPreferences sharedPreferences = getSharedPreferences(ReportActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ReportActivity.CALORIES, calories);
        editor.putInt(ReportActivity.TIME, time);
        editor.putInt(ReportActivity.WORKOUTS, workout);

        editor.apply();
    }
}