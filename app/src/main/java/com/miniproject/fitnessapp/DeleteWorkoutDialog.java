package com.miniproject.fitnessapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class DeleteWorkoutDialog extends AppCompatDialogFragment {

    private TextView mUserWorkoutName;
    private DeleteDialogListener mListener;
    private String workoutName;

    public DeleteWorkoutDialog(String workoutName) {
        this.workoutName = workoutName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.delete_dialog_view, null);

        mUserWorkoutName = view.findViewById(R.id.name_of_deleting_workout);
        mUserWorkoutName.setText(workoutName);

        builder.setView(view)
                .setTitle("Delete Workout")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseUserWorkout db = new DatabaseUserWorkout(getActivity().getApplicationContext());
                        db.deleteTable(workoutName);
                        CreateExerciseActivity.mWorkoutsName.remove(workoutName);
                        saveData();
                        mListener.applyData(true);
                    }
                });

        return builder.create();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(CreateExerciseActivity.mWorkoutsName);
        editor.putString("task list", json);
        editor.apply();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mListener = (DeleteDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface DeleteDialogListener {
        void applyData(boolean done);
    }

}
