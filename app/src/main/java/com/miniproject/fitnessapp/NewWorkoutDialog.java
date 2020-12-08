package com.miniproject.fitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NewWorkoutDialog extends AppCompatDialogFragment {

    private ExampleDialogListener mListener;
    private EditText mUserWorkoutName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.name_workout_dialog_layout, null);

        mUserWorkoutName = view.findViewById(R.id.workout_name_user);

        builder.setView(view)
                .setTitle("Create Workout")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String workoutName = mUserWorkoutName.getText().toString();
                        if (workoutName.matches("\\w+")) {
                            if (CreateExerciseActivity.mWorkoutsName.contains(workoutName)) {
                                Toast.makeText(getActivity().getApplicationContext(), "Workout already exist", Toast.LENGTH_SHORT).show();
                            } else {
                                mListener.applyData(workoutName);
                            }
                        } else {
                            mUserWorkoutName.setText("");
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid Format. eg. a-z, 0-9, A-Z, _", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mListener = (ExampleDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyData(String workoutName);
    }

}
