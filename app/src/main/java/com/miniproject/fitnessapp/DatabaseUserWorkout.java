package com.miniproject.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseUserWorkout extends SQLiteOpenHelper {

    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_NAME = "EXERCISE_NAME";
    public static final String COLUMN_EXERCISE_DURATION = "EXERCISE_DURATION";
    public static final String COLUMN_EXERCISE_IMAGE = "EXERCISE_IMAGE";
    public static final String COLUMN_EXERCISE_GUIDELINES = "EXERCISE_GUIDELINES";
    public static final String COLUMN_EXERCISE_URL = "EXERCISE_URL";
    public static final String COLUMN_EXERCISE_CALORIES = "EXERCISE_CALORIES";

    public DatabaseUserWorkout(@Nullable Context context) {
        super(context, "user_workout.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(String tableName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER, " + COLUMN_EXERCISE_IMAGE + " TEXT, " + COLUMN_EXERCISE_GUIDELINES + " TEXT, " + COLUMN_EXERCISE_URL + " TEXT, " + COLUMN_EXERCISE_CALORIES + " INTEGER);");
    }

    public boolean addOne(Exercises exercises, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXERCISE_NAME, exercises.getName());
        cv.put(COLUMN_EXERCISE_DURATION, exercises.getDuration());
        cv.put(COLUMN_EXERCISE_IMAGE, exercises.getImage());
        cv.put(COLUMN_EXERCISE_GUIDELINES, exercises.getGuidelines());
        cv.put(COLUMN_EXERCISE_URL, exercises.getHelp());
        cv.put(COLUMN_EXERCISE_CALORIES, exercises.getCalories());

        long insert = db.insert(tableName, null, cv);

//        db.close();
        return insert != -1;
    }

    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
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
