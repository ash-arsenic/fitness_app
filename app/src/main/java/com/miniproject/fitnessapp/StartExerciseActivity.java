package com.miniproject.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class StartExerciseActivity extends AppCompatActivity {

    private String mExerciseName;
    private int mTimeDuration;
    private boolean mStart;
    private String mImage;
    private String mGuidelines;
    private String mHelp;
    private int mCategory;
    private ArrayList<Exercises> mExercises;
    private static long timeExercise;
    public static float calories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);

        Bundle args = getIntent().getExtras();
        if(args != null) {
            mExerciseName = args.getString("name");
            mTimeDuration = args.getInt("time");
            mStart = args.getBoolean("type");
            mImage = args.getString("image");
            mGuidelines = args.getString("guidelines");
            mHelp = args.getString("help");
            mCategory = args.getInt("category");
        } else {
            mExerciseName = "Pushups";
            mTimeDuration = 30;
        }

        Log.d("Exercise", "Calories burned: " + calories);

        if(mStart) {

            mExercises = (ArrayList<Exercises>) getIntent().getSerializableExtra("data");

            if (ExercisesListFragment.count == 0) {
                timeExercise = System.currentTimeMillis();
            }

            if(ExercisesListFragment.count < mExercises.size()-1) {

                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = new ExerciseFragment(mExercises.get(ExercisesListFragment.count), mExercises.get(ExercisesListFragment.count+1), mStart);
                fm.beginTransaction().replace(R.id.start_exercise_layout, fragment)
                        .commit();
                ExercisesListFragment.count++;

            } else if(ExercisesListFragment.count == mExercises.size() - 1) {

                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = new ExerciseFragment(mExercises.get(ExercisesListFragment.count), new Exercises("No Exercise", 30, "mini_project", "abc", "abc", 12), mStart);
                fm.beginTransaction().replace(R.id.start_exercise_layout, fragment)
                        .commit();
                ExercisesListFragment.count++;

            } else{
                int time = ((int) (System.currentTimeMillis() - timeExercise)/1000);
                calories = calories * 3.5f * ExerciseCategoriesActivity.mUserWeight/1200;
                Intent intent = new Intent(getApplicationContext(), CongratulationActivity.class);
                intent.putExtra("timeTaken", (int)(System.currentTimeMillis() - timeExercise)/1000);
                intent.putExtra("calories", (int) calories);
                intent.putExtra("exercise_count", mExercises.size());
                intent.putExtra("category", mCategory);
                startActivity(intent);
                finish();

            }
        }
    }
}