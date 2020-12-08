package com.miniproject.fitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ChooseExerciseTimeDialog extends AppCompatDialogFragment {

    private String exerciseName, exerciseImage, exerciseLink;

    private ChooseExerciseListener mListener;
    private TextView mExerciseName;
    private ImageView mExerciseImage;
    private ImageButton mExerciseLink;
    private Spinner mSpinner;
    private Exercises mExercises;

    public ChooseExerciseTimeDialog(Exercises exercises) {
        this.exerciseName = exercises.getName();
        this.exerciseImage = exercises.getImage();
        this.exerciseLink = exercises.getHelp();
        this.mExercises = exercises;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_select_time, null);

        mExerciseName = view.findViewById(R.id.dialog_description_name);
        mExerciseName.setText(exerciseName);

        mExerciseImage = view.findViewById(R.id.dialog_description_animation);
        mExerciseImage.setImageResource(getResources().getIdentifier(exerciseImage, "drawable", getActivity().getPackageName()));

        mExerciseLink = view.findViewById(R.id.dialog_description_video);
        mExerciseLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(exerciseLink));
                startActivity(intent);
            }
        });

        mSpinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_data, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.setView(view)
                .setTitle("Select Time Duration")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getActivity(), "Adding", Toast.LENGTH_SHORT).show();
                    String time = mSpinner.getSelectedItem().toString();
                    ExerciseChooseActivity.userWorkout.add(new Exercises(mExercises.getName(), Integer.parseInt(time), mExercises.getImage(), mExercises.getGuidelines(), mExercises.getHelp(), mExercises.getCalories()));
                    mListener.applyData(ExerciseChooseActivity.userWorkout.size());
                }
            });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mListener = (ChooseExerciseListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ChooseExerciseListener {
        void applyData(int count);
    }

}
