package com.miniproject.fitnessapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDescriptionActivity extends AppCompatActivity {

    private TextView mExerciseName;
    private TextView mExerciseGuidelines;
    private ImageButton mExerciseReference;
    private ImageView mExerciseAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);

        String name = "Push ups", image = "mini_project", link = "http://www.youtube.com/watch?v=5X7WWVTrBvM", guidelines = "One\nTwo\nRepeat";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            name = bundle.getString("name");
            image = bundle.getString("image");
            link = bundle.getString("link");
            guidelines = bundle.getString("guidelines");
        }

        mExerciseName = findViewById(R.id.exercise_description_name);
        mExerciseName.setText(name);

        mExerciseGuidelines = findViewById(R.id.exercise_description_guidelines);
        mExerciseGuidelines.setText(guidelines);

        mExerciseReference = findViewById(R.id.exercise_description_video);
        final String finalLink = link;
        mExerciseReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalLink));
                startActivity(intent);
            }
        });

        mExerciseAnime = findViewById(R.id.exercise_description_animation);
        mExerciseAnime.setImageResource(getResources().getIdentifier(image, "drawable", getApplicationContext().getPackageName()));
    }
}