package com.miniproject.fitnessapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ExerciseChooseActivity extends AppCompatActivity  implements NewWorkoutDialog.ExampleDialogListener, ChooseExerciseTimeDialog.ChooseExerciseListener  {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public static List<Exercises> userWorkout;
    private Menu myMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_exercise_menu, menu);
        myMenu = menu;
        MenuItem item = menu.findItem(R.id.add_exercise);
        item.setVisible(false);
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
        setContentView(R.layout.activity_exercise_choose);

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


        ////////////////////////////////////////////////////////////////////////////////////////////
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("All");
        arrayList.add("Chest");
        arrayList.add("Abs");
        arrayList.add("Leg");

        prepareViewPager(mViewPager, arrayList);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        ExerciseChooseAdapter adapter = new ExerciseChooseAdapter(getSupportFragmentManager());

        AllExerciseFragment fragment = new AllExerciseFragment();
        ChestExerciseFragment fragment1 = new ChestExerciseFragment();
        AbsExerciseFragment fragment2 = new AbsExerciseFragment();
        LegExerciseFragment fragment3 = new LegExerciseFragment();

        adapter.addFragment(fragment, arrayList.get(0));
        adapter.addFragment(fragment1, arrayList.get(1));
        adapter.addFragment(fragment2, arrayList.get(2));
        adapter.addFragment(fragment3, arrayList.get(3));

        viewPager.setAdapter(adapter);
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
    public void applyData(int count) {
        getSupportActionBar().setTitle(count + " Exercises");
        myMenu.findItem(R.id.add_exercise).setVisible(true);
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

    public static class ExerciseChooseAdapter extends FragmentPagerAdapter {

        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        public ExerciseChooseAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }

}