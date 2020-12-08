package com.miniproject.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseReport extends SQLiteOpenHelper {

    public static final String REPORT_WORKOUT_ID = "WORKOUT_ID";
    public static final String REPORT_WORKOUT_DATE = "WORKOUT_DATE";
    public static final String REPORT_WORKOUT_NAME = "WORKOUT_NAME";
    public static final String REPORT_WORKOUT_TIME = "WORKOUT_TIME";
    public static final String REPORT_TABLE = "REPORT";
    public static final String REPORT_WORKOUT_CALORIES = "WORKOUT_CALORIES";

    public DataBaseReport(@Nullable Context context) {
        super(context, "userreport.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + REPORT_TABLE + " (" + REPORT_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REPORT_WORKOUT_DATE + " STRING , " + REPORT_WORKOUT_NAME + " STRING, " + REPORT_WORKOUT_TIME + " INTEGER, " + REPORT_WORKOUT_CALORIES + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(WorkoutReport report) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(REPORT_WORKOUT_DATE, report.getDate());
        cv.put(REPORT_WORKOUT_NAME, report.getWorkoutName());
        cv.put(REPORT_WORKOUT_TIME, report.getWorkoutTime());
        cv.put(REPORT_WORKOUT_CALORIES, report.getCalories());

        long insert = db.insert(REPORT_TABLE, null, cv);

        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<WorkoutReport> getEveryone() {
        ArrayList<WorkoutReport> returnList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + REPORT_TABLE, null);

        if(cursor.moveToFirst()) {
            do{

                String workoutDate = cursor.getString(1);
                String workoutName = cursor.getString(2);
                int workoutTime = cursor.getInt(3);
                int calories = cursor.getInt(4);

                WorkoutReport report = new WorkoutReport(workoutDate, workoutName, workoutTime, calories);
                returnList.add(report);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

}
