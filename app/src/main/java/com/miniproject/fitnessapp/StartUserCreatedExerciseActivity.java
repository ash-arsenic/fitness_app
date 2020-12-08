package com.miniproject.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class StartUserCreatedExerciseActivity extends AppCompatActivity {

    private ArrayList<Exercises> mExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_user_created_exercise);

        mExercises = (ArrayList<Exercises>) getIntent().getSerializableExtra("exercise");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new ExercisesListFragment(mExercises, 99);
        fm.beginTransaction().replace(R.id.activity_start_user_created_exercise, fragment).commit();

    }
}