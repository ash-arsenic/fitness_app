package com.miniproject.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseExercises extends SQLiteOpenHelper {
    public static final String CHEST_BEGINNERS = "CHEST_BEGINNERS";
    public static final String CHEST_INTERMEDIATE = "CHEST_INTERMEDIATE";
    public static final String CHEST_ADVANCED = "CHEST_ADVANCED";
    public static final String ABS_BEGINNERS = "ABS_BEGINNERS";
    public static final String ABS_INTERMEDIATE = "ABS_INTERMEDIATE";
    public static final String ABS_ADVANCED = "ABS_ADVANCED";
    public static final String LEG_BEGINNERS = "LEG_BEGINNERS";
    public static final String LEG_INTERMEDIATE = "LEG_INTERMEDIATE";
    public static final String LEG_ADVANCED = "LEG_ADVANCED";

    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_NAME = "EXERCISE_NAME";
    public static final String COLUMN_EXERCISE_DURATION = "EXERCISE_DURATION";
    public static final String COLUMN_EXERCISE_IMAGE = "EXERCISE_IMAGE";
    public static final String COLUMN_EXERCISE_GUIDLINES = "EXERCISE_GUIDLINES";
    public static final String COLUMN_EXERCISE_URL = "EXERCISE_URL";
    public static final String COLUMN_EXERCISE_CALORIES = "EXERCISE_CALORIES";

    public DataBaseExercises(@Nullable Context context) {
        super(context, "exercises.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CHEST_BEGINNERS + " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + CHEST_INTERMEDIATE + " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + CHEST_ADVANCED+ " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + ABS_BEGINNERS+ " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + ABS_INTERMEDIATE+ " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + ABS_ADVANCED+ " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + LEG_BEGINNERS+ " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + LEG_INTERMEDIATE + " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + LEG_ADVANCED + " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDLINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");

        setChestBeginnersTable(sqLiteDatabase);
        setChestIntermediateTable(sqLiteDatabase);
        setChestAdvancedTable(sqLiteDatabase);
        setAbsBeginnersTable(sqLiteDatabase);
        setAbsIntermediateTable(sqLiteDatabase);
        setAbsAdvancedTable(sqLiteDatabase);
        setLegBeginnersTable(sqLiteDatabase);
        setLegIntermediateTable(sqLiteDatabase);
        setLegAdvancedTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void setChestBeginnersTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Jumping Jacks", "Inclined Push-ups", "Knee Push-Ups", "Push-Ups", "Wide Arm Push-Ups", "Inclined Push-Ups", "Hindu Push-Ups", "Cobra Stretch"};
        int[] exerciseTime = {30, 20, 20, 20, 20, 20, 20, 30};
        String[] images = {"jumping_jack", "inclined_pushups_gif", "knee_pushups", "pushups_gif", "wide_arm_pushups_gif", "inclined_pushups_gif", "desi_pushups_gif", "cobra_stretch"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {10, 8, 8, 8, 8, 8, 8, 4};

        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), CHEST_BEGINNERS, sqLiteDatabase);
        }
    }


    private void setChestIntermediateTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Jumping Jacks", "Knee Push-Ups", "Push-Ups", "Wide Arm Push-Ups", "Hindu Push-Ups", "T Push-Ups", "Knee Push-Ups", "Hindu Push-Ups", "Decline Push-Ups", "Cobra Stretch"};
        int[] exerciseTime = {30, 24, 24, 24, 24, 24, 24, 24, 24, 30};
        String[] images = {"jumping_jack", "knee_pushups", "pushups_gif", "wide_arm_pushups_gif", "desi_pushups_gif", "t_pushups", "knee_pushups", "desi_pushups_gif","declined_pushups_gif", "cobra_stretch"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {10, 8, 8, 8, 8, 8, 8, 8, 8, 4};

        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), CHEST_INTERMEDIATE, sqLiteDatabase);
        }
    }

    private void setChestAdvancedTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Jumping Jacks", "Burpees", "Push-Ups", "Hindu Push-Ups", "T Push-Ups", "Diamond Push-Ups", "Hindu Push-Ups", "Spiderman Push-Ups", "Decline Push-Ups", "Burpees", "Cobra Stretch"};
        int[] exerciseTime = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        String[] images = {"jumping_jack", "burpees", "pushups_gif", "desi_pushups_gif", "t_pushups", "diamond_pushups_gif", "desi_pushups_gif", "spiderman_pushups", "declined_pushups_gif", "burpees", "cobra_stretch"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {10, 9, 8, 8, 8, 8, 8, 8, 8, 9, 4};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), CHEST_ADVANCED, sqLiteDatabase);
        }
    }

    private void setAbsBeginnersTable(SQLiteDatabase sqLiteDatabase) {
        
        String[] exerciseNames = {"Jumping Jacks", "Abdominal Crunches", "Russian Twist", "Mountain Climber", "Heel Touch", "Leg Raises", "Plank", "Abdominal Crunches", "Russian Twist", "Mountain Climber", "Heel Touch", "Leg Raises", "Plank", "Cobra Stretch"};
        int[] exerciseTime = {30, 20, 20, 20, 20, 20, 20, 30, 20, 20, 20, 20, 30, 30};
        String[] images = {"jumping_jack", "abdominal_crunches", "russian_twist", "mountain_climber", "heel_touch", "leg_raise", "plank", "abdominal_crunches", "russian_twist", "mountain_climber", "heel_touch", "leg_raise", "plank", "cobra_stretch"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {10, 5, 5, 8, 4, 7, 5, 5, 5, 8, 4, 7, 5, 4};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), ABS_BEGINNERS, sqLiteDatabase);
        }
    }
    private void setAbsIntermediateTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Jumping Jacks", "Heel Touch", "Crossover Crunches", "Mountain Climber",
                "Side bridge Left", "Side bridge Right", "Butt Bridge", "Bicycle Crunches", "V-Up", "Heel Touch",
                "Abdominal Crunches", "Plank", "Crossover Crunches", "Leg Raises", "Bicycle Crunches", "T Push-Ups",
                "Side Bridge Right", "Side Bridge Left", "Cobra Stretch", "Spine Stretch Left", "Spine Stretch Right"};

        int[] exerciseTime = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        String[] images = {"jumping_jack", "heel_touch", "crossover_crunches", "mountain_climber", "side_bridge_left", "side_bridge_right", "butt_bridge", "bicycle_crunches", "v_up",
                "heel_touch", "abdominal_crunches", "plank", "crossover_crunches", "leg_raise", "bicycle_crunches", "t_pushups",
                "side_bridge_left", "side_bridge_right", "cobra_stretch", "spine_lumbar_twist_left", "spine_lumbar_twist_right"};

        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {8, 4, 5, 8, 5, 5, 6, 6, 6, 4, 5, 5, 5, 6, 6, 8, 5, 5, 4, 2, 2};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), ABS_INTERMEDIATE, sqLiteDatabase);
        }
    }
    private void setAbsAdvancedTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Jumping Jacks", "Sit Ups", "Side bridge left", "Side bridge right",
                "Abdominal Crunches", "Bicycle Crunches", "V UP", "T Push-Ups", "Russian Twist", "Abdominal Crunches",
                "Butt Bridge", "Heel Touch", "Mountain Climber", "Crossover Crunches", "V-Up", "Plank",
                "Side Bridge Left", "Side Bridge Right", "Cobra Stretch", "Spine Stretch Left", "Spine Stretch Right"};

        int[] exerciseTime = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        String[] images = {"jumping_jack", "sit_ups", "side_bridge_left", "side_bridge_right", "abdominal_crunches", "bicycle_crunches", "v_up", "t_pushups", "russian_twist",
                "abdominal_crunches", "butt_bridge", "heel_touch", "mountain_climber", "crossover_crunches", "v-up", "plank",
                "side_bridge_left", "side_bridge_right", "cobra_stretch", "spine_lumbar_twist_left", "spine_lumbar_twist_right"};

        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {8, 8, 5, 5, 5, 6, 6, 8, 5, 5, 6, 4, 8, 5, 6, 5, 5, 5, 4, 2, 2};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), ABS_ADVANCED, sqLiteDatabase);
        }
    }


    private void setLegBeginnersTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Side Hop", "Squats", "Squats", "Backward Lunge", "Backward Lunge", "Donkey Kicks Left", "Donkey Kicks Right",
                "Donkey Kicks Left", "Donkey Kicks Right", "Knee Chest Stretch Left", "Knee Chest Stretch Right", "Wall Calf Raises", "Wall Calf Raises",
                "Calf Stretch Left", "Calf Stretch Right"};
        int[] exerciseTime = {30, 20, 20, 20, 20, 20, 20, 20, 20, 30, 30, 20, 20, 30, 30};
        String[] images = {"side_hop", "squats", "squats", "backward_lunges", "backward_lunges", "donkey_kicks", "donkey_kicks",
                "donkey_kicks", "donkey_kicks", "knee_to_chest_stretch_left", "knee_to_chest_stretch_right", "calf", "calf",
                "calf_stretch_left", "calf_stretch_right"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {9, 6, 6, 5, 5, 4, 4, 4, 4, 2, 2, 4, 4, 2, 2};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), LEG_BEGINNERS, sqLiteDatabase);
        }
    }
    private void setLegIntermediateTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Jumping Jacks", "Squats", "Squats", "Squats", "Fire Hydrant Left", "Fire Hydrant Right", "Fire Hydrant Left", "Fire Hydrant Right", "Fire Hydrant Left", "Fire Hydrant Right",
                "Lunges", "Lunges", "Lunges", "Jumping Squats", "Jumping Squats", "Jumping Squats", "Wall Sit", "Knee Chest Stretch Left", "Knee Chest Stretch Right",
                "Wall Calf Raises", "Wall Calf Raises", "Wall Calf Raises", "Calf Stretch Left", "Calf Stretch Right"};
        int[] exerciseTime = {30, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30, 30, 30, 30, 30, 30, 30};
        String[] images = {"jumping_jack", "squats", "squats", "squats", "fire_hydrant", "fire_hydrant", "fire_hydrant", "fire_hydrant", "fire_hydrant", "fire_hydrant",
                "lunges", "lunges", "lunges", "jumping_squats", "jumping_squats", "jumping_squats", "wall_sit", "knee_to_chest_stretch_left", "knee_to_chest_stretch_right",
                "calf", "calf", "calf", "calf_stretch_left", "calf_stretch_right"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {8, 6, 6, 6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9, 9, 9, 8, 2, 2, 4, 4, 4, 2, 2};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), LEG_INTERMEDIATE, sqLiteDatabase);
        }
    }
    private void setLegAdvancedTable(SQLiteDatabase sqLiteDatabase) {

        String[] exerciseNames = {"Burpees", "Squats", "Squats", "Squats", "Lunges", "Lunges", "Lunges",
                "Side Leg Circles Left", "Side Leg Circles Right", "Side Leg Circles Left", "Side Leg Circles Right", "Side Leg Circles Left", "Side Leg Circle Right",
                "Jumping Squats", "Jumping Squats", "Jumping Squats", "Donkey Kick Back Left", "Donkey Kick Back Right", "Donkey Kick Back Left", "Donkey Kick Back Right", "Donkey Kick Back Left", "Donkey Kick Back Right",
                "Wall Sit", "Knee Chest Stretch Left", "Knee Chest Stretch Right", "Butterfly Stretch",
                "Wall Calf Raises", "Wall Calf Raises", "Wall Calf Raises", "Calf Stretch Left", "Calf sStretch Right"};
        int[] exerciseTime = {20, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 40, 30, 30, 30, 40, 40, 40, 30, 30};
        String[] images = {"burpees", "squats", "squats", "squats", "lunges", "lunges", "lunges",
                "side_leg_circles", "side_leg_circles", "side_leg_circles", "side_leg_circles", "side_leg_circles", "side_leg_circles",
                "jumping_squats", "jumping_squats", "jumping_squats" , "donkey_kicks", "donkey_kicks", "donkey_kicks", "donkey_kicks", "donkey_kicks", "donkey_kicks",
                "wall_sit", "knee_to_chest_stretch_left", "knee_to_chest_stretch_right", "butterfly_stretch",
                "calf", "calf", "calf", "calf_stretch_left", "calf_stretch_right"};
        String[] exerciseGuidlines = {"1. Get down on all fours, placing your hands slightly wider than your shoulder.\n2. Straighten your arm and legs.\n3. Lower your body until your chest nearly touches the floor.\n4. Pause then push yourself back up.\n5. Repeat.",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat",
                "1. Stand with the bar on your upper-back, and your feet shoulder width apart.\n2. Squat down by pushing your knees to the side while moving hips back.\n3. Break parallel by squatting down until your hips are lower than your knees.\n4. Repeat"};

        String[] exerciseUrl = {"http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM",
                "http://www.youtube.com/watch?v=5X7WWVTrBvM"};

        int [] calories = {9, 6, 6, 6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9, 9, 9, 3, 3, 3, 3, 3, 3, 8, 2, 2, 2, 4, 4, 4, 2, 2};
        for(int i=0; i<exerciseTime.length; i++) {
            addOne(new Exercises(exerciseNames[i], exerciseTime[i], images[i], exerciseGuidlines[i], exerciseUrl[i], calories[i]), LEG_ADVANCED, sqLiteDatabase);
        }
    }


    public boolean addOne(Exercises exercises, String tableName, SQLiteDatabase db) {

//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXERCISE_NAME, exercises.getName());
        cv.put(COLUMN_EXERCISE_DURATION, exercises.getDuration());
        cv.put(COLUMN_EXERCISE_IMAGE, exercises.getImage());
        cv.put(COLUMN_EXERCISE_GUIDLINES, exercises.getGuidelines());
        cv.put(COLUMN_EXERCISE_URL, exercises.getHelp());
        cv.put(COLUMN_EXERCISE_CALORIES, exercises.getCalories());

        long insert = db.insert(tableName, null, cv);

//        db.close();

        return insert != -1;
    }

    public ArrayList<Exercises> getEveryone(String tableName) {

        ArrayList<Exercises> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ tableName, null);

        if(cursor.moveToFirst()) {

            do{

                String exerciseName = cursor.getString(1);
                int timeDuratiion = cursor.getInt(2);
                String exerciseImage = cursor.getString(3);
                String exerciseGuidelines = cursor.getString(4);
                String exerciseUrl = cursor.getString(5);
                int exerciseCalories = cursor.getInt(6);
                Exercises exercises = new Exercises(exerciseName, timeDuratiion, exerciseImage, exerciseGuidelines, exerciseUrl, exerciseCalories);

                returnList.add(exercises);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

}
