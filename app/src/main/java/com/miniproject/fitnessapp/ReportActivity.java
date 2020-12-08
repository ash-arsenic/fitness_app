package com.miniproject.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import sun.bob.mcalendarview.MCalendarView;

public class ReportActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CALORIES = "calories";
    public static final String TIME = "time";
    public static final String WORKOUTS = "workouts";

    private MCalendarView mCalendarView;
    private TextView mTotalCalories;
    private TextView mTotalWorkouts;
    private TextView mTotalMinutes;
    private RecyclerView mRecyclerView;
    private Intent intent;
    private BottomNavigationView mNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_rest_duration_menu) {
            ChangeRestDurationDialog dialog = new ChangeRestDurationDialog();
            dialog.show(getSupportFragmentManager(), "Change Rest Duration");
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mNavigationView.setSelectedItemId(R.id.navigation_report);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mCalendarView = findViewById(R.id.report_calender);

        Toolbar toolbar = findViewById(R.id.report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Report");

        DataBaseReport db = new DataBaseReport(this);
        List<WorkoutReport> data = db.getEveryone();


        for(int i=0; i<data.size(); i++) {
            String date = data.get(i).getDate();
            String parts[] = date.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            mCalendarView.markDate(year, month, day);
        }

        mRecyclerView = findViewById(R.id.report_exercise_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ReportWorkoutAdapter(data));

        mTotalWorkouts = findViewById(R.id.report_total_workout);
        mTotalMinutes = findViewById(R.id.report_total_time);
        mTotalCalories = findViewById(R.id.report_total_calories);
        loadData();

        mNavigationView = findViewById(R.id.navigation_bar);
        mNavigationView.setSelectedItemId(R.id.navigation_report);
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
                        intent = new Intent(getApplicationContext(), CreateExerciseActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_report:
                        return true;

                    default:
                        return true;
                }
            }
        });

    }

    public class ReportWorkoutAdapter extends RecyclerView.Adapter<ReportWorkoutHolder> {

        private List<WorkoutReport> mWorkoutReportList;

        public ReportWorkoutAdapter(List<WorkoutReport> workoutReportList) {
            mWorkoutReportList = workoutReportList;
        }

        @NonNull
        @Override
        public ReportWorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.holder_report_workout, parent, false);
            ReportWorkoutHolder holder = new ReportWorkoutHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ReportWorkoutHolder holder, int position) {
            holder.mWorkoutName.setText(mWorkoutReportList.get(position).getWorkoutName());
            holder.mWorkoutDate.setText(mWorkoutReportList.get(position).getDate());
            int time = mWorkoutReportList.get(position).getWorkoutTime();
            int min = time/60;
            int temp = time-min*60;
            int sec = temp%60;
            String timeFormat = String.format("%02d:%02d", min, sec);
            holder.mWorkoutTime.setText(timeFormat + " min");
            holder.mWorkoutCalories.setText(mWorkoutReportList.get(position).getCalories() + " kcal");

            String image = "";
            switch (position % 4 ) {
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
            }
            holder.mWorkoutImage.setImageResource(getResources().getIdentifier(image, "drawable", getPackageName()));
        }

        @Override
        public int getItemCount() {
            return mWorkoutReportList.size();
        }
    }

    public class ReportWorkoutHolder extends RecyclerView.ViewHolder {

        private TextView mWorkoutName;
        private TextView mWorkoutTime;
        private TextView mWorkoutDate;
        private TextView mWorkoutCalories;
        private ImageView mWorkoutImage;

        public ReportWorkoutHolder(@NonNull View itemView) {
            super(itemView);

            mWorkoutName = itemView.findViewById(R.id.holder_exerciseName_report);
            mWorkoutTime = itemView.findViewById(R.id.holder_exerciseTime_report);
            mWorkoutDate = itemView.findViewById(R.id.holder_report_date);
            mWorkoutCalories = itemView.findViewById(R.id.holder_exercise_calories_report);
            mWorkoutImage = itemView.findViewById(R.id.holder_report_image);
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int calories = sharedPreferences.getInt(CALORIES, 0);
        int time = sharedPreferences.getInt(TIME, 0);
        int workouts = sharedPreferences.getInt(WORKOUTS, 0);

        mTotalCalories.setText(String.valueOf(calories));
        int min = time/60;
        int temp = time-min*60;
        int sec = temp%60;
        String timeFormat = String.format("%02d:%02d", min, sec);
        mTotalMinutes.setText(timeFormat);
        mTotalWorkouts.setText(String.valueOf(workouts));
    }

}