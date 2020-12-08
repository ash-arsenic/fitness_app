package com.miniproject.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CreateExerciseActivity extends AppCompatActivity implements DeleteWorkoutDialog.DeleteDialogListener {

    private FloatingActionButton mCreateNewWorkout;
    private BottomNavigationView mNavigationView;
    private Intent intent;
    public static List<String> mWorkoutsName;
    private RecyclerView mUserWorkouts;
    private UserWorkoutAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        mNavigationView.setSelectedItemId(R.id.navigation_add);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);


        Toolbar toolbar = findViewById(R.id.create_workout_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Workout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mCreateNewWorkout = findViewById(R.id.create_new_workout);
        mCreateNewWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseChooseActivity.class);
                startActivity(intent);
            }
        });

        loadData();
        mUserWorkouts = findViewById(R.id.created_exercise_list);
        mUserWorkouts.setHasFixedSize(true);
        mUserWorkouts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserWorkoutAdapter(mWorkoutsName);
        mUserWorkouts.setAdapter(adapter);

        mNavigationView = findViewById(R.id.navigation_bar);
        mNavigationView.setSelectedItemId(R.id.navigation_add);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(getApplicationContext(), ExerciseCategoriesActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_add:
                        return true;

                    case R.id.navigation_report:
                        intent = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    default:
                        return true;
                }
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        mWorkoutsName = gson.fromJson(json, type);

        if (mWorkoutsName == null) {
            mWorkoutsName = new ArrayList<>();
        }

    }

    @Override
    public void applyData(boolean done) {
        if (done) {
            adapter.notifyDataSetChanged();
        }
    }

    private class UserWorkoutAdapter extends RecyclerView.Adapter<UserWorkoutHolder> {

        private List<String> mWorkoutNames;

        public UserWorkoutAdapter(List<String> workoutNames) {
            mWorkoutNames = workoutNames;
        }

        @NonNull
        @Override
        public UserWorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.holder_user_workout_list, parent, false);
            UserWorkoutHolder holder = new UserWorkoutHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserWorkoutHolder holder, final int position) {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseUserWorkout db = new DatabaseUserWorkout(CreateExerciseActivity.this);
                    ArrayList<Exercises> userExercises = db.getEveryone(mWorkoutNames.get(position));

                    Intent intent = new Intent(getApplicationContext(), StartUserCreatedExerciseActivity.class);
                    intent.putExtra("exercise", userExercises);
                    startActivity(intent);

                    for (int i=0; i<userExercises.size(); i++) {
                        Log.d("CreateExerciseActivity", userExercises.get(i).toString());
                    }
                }
            });
            holder.mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DeleteWorkoutDialog dialog = new DeleteWorkoutDialog(mWorkoutNames.get(position));
                    dialog.show(getSupportFragmentManager(), "Delete Workout");
                    return true;
                }
            });
            holder.mUserWorkout.setText(mWorkoutNames.get(position));

            DatabaseUserWorkout db = new DatabaseUserWorkout(CreateExerciseActivity.this);
            ArrayList<Exercises> userExercises = db.getEveryone(mWorkoutNames.get(position));
            holder.mUserWorkoutTime.setText(String.valueOf(userExercises.size()) + " Exercises");

            String image;
            switch (position % 4) {
                case 0:
                    image = "mini_project";
                    break;

                case 1:
                    image = "sample1";
                    break;

                case 2:
                    image = "sample2";
                    break;

                case 3:
                    image = "sample3";
                    break;

                default:
                    image = "mini_project";
                    break;
            }

            holder.mUserWorkoutImage.setImageResource(getResources().getIdentifier(image, "drawable", getPackageName()));

        }

        @Override
        public int getItemCount() {
            return mWorkoutNames.size();
        }
    }

    private static class UserWorkoutHolder extends RecyclerView.ViewHolder {

        final private TextView mUserWorkout;
        final private RelativeLayout mLinearLayout;
        final private TextView mUserWorkoutTime;
        final private ImageView mUserWorkoutImage;

        public UserWorkoutHolder(@NonNull View itemView) {
            super(itemView);
            mUserWorkout = itemView.findViewById(R.id.user_workout_name);
            mLinearLayout = itemView.findViewById(R.id.user_workout_list_holder);
            mUserWorkoutTime = itemView.findViewById(R.id.user_workout_time);
            mUserWorkoutImage = itemView.findViewById(R.id.user_workout_list_image);
        }
    }
}
