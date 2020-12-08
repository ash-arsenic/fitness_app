package com.miniproject.fitnessapp;

public class WorkoutReport {

    private String mDate;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getWorkoutName() {
        return mWorkoutName;
    }

    public void setWorkoutName(String workoutName) {
        mWorkoutName = workoutName;
    }

    public int getWorkoutTime() {
        return mWorkoutTime;
    }

    public void setWorkoutTime(int workoutTime) {
        mWorkoutTime = workoutTime;
    }

    private String mWorkoutName;
    private int mWorkoutTime;
    private int mCalories;

    public int getCalories() {
        return mCalories;
    }

    public void setCalories(int calories) {
        mCalories = calories;
    }

    public WorkoutReport(String date, String workoutName, int workoutTime, int calories) {
        mDate = date;
        mWorkoutName = workoutName;
        mWorkoutTime = workoutTime;
        mCalories = calories;
    }

}
