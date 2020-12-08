package com.miniproject.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String EXERCISE_TABLE = "EXERCISE";
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_NAME = "EXERCISE_NAME";
    public static final String COLUMN_EXERCISE_DURATION = "EXERCISE_DURATION";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "userexercises.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + EXERCISE_TABLE + " (" + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_NAME + " TEXT, " + COLUMN_EXERCISE_DURATION + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Exercises exercise) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXERCISE_NAME, exercise.getName());
        cv.put(COLUMN_EXERCISE_DURATION, exercise.getDuration());

        long insert = db.insert(EXERCISE_TABLE, null, cv);

        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Exercises> getEveryone() {
        ArrayList<Exercises> returnList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXERCISE_TABLE, null);

        if(cursor.moveToFirst()) {
            do{

                String exerciseName = cursor.getString(1);
                int exerciseDuration = cursor.getInt(2);

                Exercises exercises = new Exercises(exerciseName, exerciseDuration, "mini_project", "default", "http://www.google.com", 12);
                returnList.add(exercises);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

}
