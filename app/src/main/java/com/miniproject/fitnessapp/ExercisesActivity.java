package com.miniproject.fitnessapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {

    int category;
    ArrayList<Exercises> exercises = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            category = bundle.getInt("idx");
        }

        DataBaseExercises db = new DataBaseExercises(this);

        switch(category) {
            case 0:
                exercises = db.getEveryone(DataBaseExercises.CHEST_BEGINNERS);
                break;

            case 1:
                exercises = db.getEveryone(DataBaseExercises.CHEST_INTERMEDIATE);
                break;

            case 2:
                exercises = db.getEveryone(DataBaseExercises.CHEST_ADVANCED);
                break;
            case 3:
                exercises = db.getEveryone(DataBaseExercises.ABS_BEGINNERS);
                break;
            case 4:
                exercises = db.getEveryone(DataBaseExercises.ABS_INTERMEDIATE);
                break;
            case 5:
                exercises = db.getEveryone(DataBaseExercises.ABS_ADVANCED);
                break;
            case 6:
                exercises = db.getEveryone(DataBaseExercises.LEG_BEGINNERS);
                break;
            case 7:
                exercises = db.getEveryone(DataBaseExercises.LEG_INTERMEDIATE);
                break;
            case 8:
                exercises = db.getEveryone(DataBaseExercises.LEG_ADVANCED);
                break;

            default:

        }

        newFragment(exercises);
    }

    private boolean newFragment(ArrayList<Exercises> exercisesList) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new ExercisesListFragment(exercisesList, category);
        fm.beginTransaction().add(R.id.exercises_activity, fragment).commit();
        return true;
    }

}
