package com.miniproject.fitnessapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChooseExercisesActivity extends AppCompatActivity implements NewWorkoutDialog.ExampleDialogListener, ChooseExerciseTimeDialog.ChooseExerciseListener {

    List<Exercises> mAllExercises;
    private RecyclerView mShowExercises;
    public static List<Exercises> userWorkout;
    private Menu myMenu;
    private Button mCreateNewWorkout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_exercise_menu, menu);
//        myMenu = menu;
//        MenuItem item = menu.findItem(R.id.add_exercise);
//        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_exercise) {
            for (int i=0; i<userWorkout.size(); i++) {
                Log.d("ChooseExercisesActivity", userWorkout.get(i).toString());
            }

            NewWorkoutDialog dialog = new NewWorkoutDialog();
            dialog.show(getSupportFragmentManager(), "Create Workout");
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercises);

        Toolbar toolbar = findViewById(R.id.choose_exercise_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose Exercise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userWorkout = new ArrayList<>();

        DataBaseExercises db = new DataBaseExercises(this);
        mAllExercises = db.getEveryone(DataBaseExercises.CHEST_BEGINNERS);
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.CHEST_INTERMEDIATE));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.CHEST_ADVANCED));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.ABS_BEGINNERS));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.ABS_INTERMEDIATE));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.ABS_ADVANCED));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.LEG_BEGINNERS));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.LEG_INTERMEDIATE));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.LEG_ADVANCED));

        List<Exercises> unique = new ArrayList<>();
        unique.add(mAllExercises.get(0));

        boolean flag = false;

        for (int i=0; i<mAllExercises.size(); i++) {
            flag = false;
            for (int j=0; j<unique.size(); j++) {
                if (mAllExercises.get(i).getName().equals(unique.get(j).getName())) {
                    flag = true;
                }
            }
            if (!flag) {
                unique.add(mAllExercises.get(i));
            }
        }

        mShowExercises = findViewById(R.id.all_exercises);
        mShowExercises.setHasFixedSize(true);
        mShowExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mShowExercises.setAdapter(new ChooseExerciseAdapter(unique));

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(CreateExerciseActivity.mWorkoutsName);
        editor.putString("task list", json);
        editor.apply();
    }

    @Override
    public void applyData(String workoutName) {
        DatabaseUserWorkout db = new DatabaseUserWorkout(this);
        db.createTable(workoutName);
        for (int i=0; i<userWorkout.size(); i++) {
            db.addOne(userWorkout.get(i), workoutName);
            Log.d("ChooseExercisesActivity","Adding" + userWorkout.get(i).toString());
        }
        CreateExerciseActivity.mWorkoutsName.add(workoutName);
        saveData();
        super.onBackPressed();
    }

    @Override
    public void applyData(int count) {
        getSupportActionBar().setTitle(count + " Exercises");
//        myMenu.findItem(R.id.add_exercise).setVisible(true);
    }

    private class ChooseExerciseAdapter extends RecyclerView.Adapter<ChooseExerciseHolder> {

        private List<Exercises> mExercisesList;

        public ChooseExerciseAdapter(List<Exercises> exercisesList) {
            mExercisesList = exercisesList;
        }

        @NonNull
        @Override
        public ChooseExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.holder_show_exercises, parent, false);
            ChooseExerciseHolder holder = new ChooseExerciseHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChooseExerciseHolder holder, int position) {
            holder.mExerciseGif.setImageResource(getResources().getIdentifier(mExercisesList.get(position).getImage(), "drawable", getPackageName()));
            holder.mExerciseName.setText(mExercisesList.get(position).getName());

            final Exercises exercises = mExercisesList.get(position);
            holder.mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChooseExerciseTimeDialog dialog = new ChooseExerciseTimeDialog(exercises);
                    dialog.show(getSupportFragmentManager(), "Choose Time");
                }
            });
        }

        @Override
        public int getItemCount() {
            return mExercisesList.size();
        }
    }

    private class ChooseExerciseHolder extends RecyclerView.ViewHolder {

        private ImageView mExerciseGif;
        private TextView mExerciseName;
        private ImageButton mAddExerciseButton;

        public ChooseExerciseHolder(@NonNull View itemView) {
            super(itemView);
            mExerciseGif = itemView.findViewById(R.id.show_exercises_image);
            mExerciseName = itemView.findViewById(R.id.show_exercises_name);
            mAddExerciseButton = itemView.findViewById(R.id.show_exercise_add);
        }
    }

}