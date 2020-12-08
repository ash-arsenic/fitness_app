package com.miniproject.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExerciseCategoriesActivity extends AppCompatActivity {

    public static int mUserWeight;
    public static int mRestDuration;

    private RecyclerView mCategoriesRecyclerView;
    private final String[][] exercise = { {"Chest Workout", "5 Min", "Beginners", "chest_beginner"},
            {"Chest Workout", "7 Min", "Intermediate", "home_page"},
            {"Chest Workout", "11 Min", "Advanced", "chest_advanced"},
            {"Abs Workout", "6 Min", "Beginner", "abs_beginner"},
            {"Abs Workout", "14 Min", "Intermediate", "abs_intermediate"},
            {"Abs Workout", "15 Min", "Advanced", "abs_advanced"},
            {"Leg Workout", "8 Min", "Beginner", "leg_beginner"},
            {"Leg Workout", "14 Min", "Intermediate", "leg_intermediate"},
            {"Leg Workout", "20 Min", "Advanced", "leg_advanced"},
    };

    private Intent intent;
    private BottomNavigationView mNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.chat_with_us) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.update_weight) {
            WeightDialog dialog = new WeightDialog();
            dialog.show(getSupportFragmentManager(), "Update Weight");
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_categories);


        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        mUserWeight = sharedPreferences.getInt("weight", 1);
        mRestDuration = sharedPreferences.getInt("restDuration", 10);

        Toolbar toolbar = findViewById(R.id.home_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fitness App");

        Log.d("Exercise", "Weight: " + mUserWeight + " Rest Duration: " + mRestDuration);

        mCategoriesRecyclerView = (RecyclerView) findViewById(R.id.exerciseCategoriesRecyclerView);
        mCategoriesRecyclerView.setHasFixedSize(true);
        mCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoriesRecyclerView.setAdapter(new ExerciseCategoryAdapter(exercise));

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

    public class ExerciseCategoryAdapter extends RecyclerView.Adapter<ExerciseCategoryHolder> {

        private String[][] categories;

        public ExerciseCategoryAdapter(String[][] data) {
            categories = data;
        }

        @NonNull
        @Override
        public ExerciseCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View listItem = inflater.inflate(R.layout.holder_exercise_category, parent, false);
            ExerciseCategoryHolder holder = new ExerciseCategoryHolder(listItem);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseCategoryHolder holder, final int position) {
            holder.mExerciseCategory.setText(categories[position][0]);
            holder.mWorkoutTime.setText(categories[position][1]);
            holder.mWorkoutIntensity.setText(categories[position][2]);
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                    intent.putExtra("idx", position);
                    startActivity(intent);
                }
            });
            holder.mBackgroundImage.setImageResource(getResources().getIdentifier(categories[position][3], "drawable", getPackageName()));
        }

        @Override
        public int getItemCount() {
            return categories.length;
        }
    }

    public class ExerciseCategoryHolder extends RecyclerView.ViewHolder {

        final private TextView mExerciseCategory;
        final private ConstraintLayout mLinearLayout;
        final private TextView mWorkoutTime;
        final private TextView mWorkoutIntensity;
        final private ImageView mBackgroundImage;

        public ExerciseCategoryHolder(@NonNull View itemView) {
            super(itemView);
            mExerciseCategory = itemView.findViewById(R.id.exerciseCategoryCardView);
            mLinearLayout = itemView.findViewById(R.id.linearLayoutCardView);
            mWorkoutTime = itemView.findViewById(R.id.workout_time);
            mWorkoutIntensity = itemView.findViewById(R.id.workout_intensity);
            mBackgroundImage = itemView.findViewById(R.id.background_image);
        }
    }

}