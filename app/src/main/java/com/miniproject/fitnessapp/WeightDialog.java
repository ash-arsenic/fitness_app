package com.miniproject.fitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class WeightDialog extends AppCompatDialogFragment {

    private EditText mWeight;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.name_workout_dialog_layout, null);

        mWeight = view.findViewById(R.id.workout_name_user);
        mWeight.setHint("Enter Your Weight in KG");

        builder.setView(view).setTitle("Weight in KG")
                .setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean wantToClose = false;
                if (mWeight.getText().toString().matches("\\d+")) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ExerciseCategoriesActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("firstStart", false);
                    editor.putInt("weight", Integer.parseInt(mWeight.getText().toString()));
                    editor.putInt("restDuration", 10);
                    editor.apply();

                    wantToClose = true;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter Weight in KG", Toast.LENGTH_SHORT).show();
                    mWeight.setText("");
                    wantToClose = false;
                }
                if (wantToClose) {
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }
}
