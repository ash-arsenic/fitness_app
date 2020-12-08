package com.miniproject.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class ExercisesListFragment extends Fragment {

    private RecyclerView mExerciseListRecyclerView;
    private ArrayList<Exercises> mExercises;
    private Button mStartExercise;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private int mWorkoutName;

    public static int count = 0;


    public ExercisesListFragment(ArrayList<Exercises> exercises, int category) {
        mExercises = exercises;
        mWorkoutName = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        mExerciseListRecyclerView = (RecyclerView) view.findViewById(R.id.exercise_list_recyclerView);
        mExerciseListRecyclerView.setHasFixedSize(true);
        mExerciseListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExerciseListRecyclerView.setAdapter(new ExerciseListAdapter(mExercises));

        mCollapsingToolbarLayout = view.findViewById(R.id.exercise_list_toolbar);
        String workoutName;
        switch(mWorkoutName) {
            case 0:
                workoutName = "CHEST BEGINNERS";
                break;

            case 1:
                workoutName = "CHEST INTERMEDIATE";
                break;

            case 2:
                workoutName = "CHEST ADVANCED";
                break;

            case 3:
                workoutName = "ABS BEGINNERS";
                break;

            case 4:
                workoutName = "ABS INTERMEDIATE";
                break;

            case 5:
                workoutName = "ABS ADVANCED";
                break;

            case 6:
                workoutName = "LEG BEGINNERS";
                break;

            case 7:
                workoutName = "LEG INTERMEDIATE";
                break;

            case 8:
                workoutName = "LEG ADVANCED";
                break;

            default:
                workoutName = "USER CREATED EXERCISE";
        }
        mCollapsingToolbarLayout.setTitle(workoutName);

        mStartExercise = (Button) view.findViewById(R.id.startExercise);
        mStartExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StartExerciseActivity.class);
                intent.putExtra("name", mExercises.get(0).getName());
                intent.putExtra("time", mExercises.get(0).getDuration());
                intent.putExtra("data", mExercises);
                intent.putExtra("image", mExercises.get(0).getImage());
                intent.putExtra("guidelines", mExercises.get(0).getGuidelines());
                intent.putExtra("help", mExercises.get(0).getHelp());
                intent.putExtra("type", true);
                intent.putExtra("category", mWorkoutName);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class ExerciseListHolder extends RecyclerView.ViewHolder {

        private TextView mExerciseName;
        private TextView mExerciseTime;
        private ConstraintLayout mConstraintLayout;
        private ImageView mExerciseImage;

        public ExerciseListHolder(@NonNull View itemView) {
            super(itemView);

            mExerciseName = itemView.findViewById(R.id.holder_exerciseName_fragment);
            mExerciseTime = itemView.findViewById(R.id.holder_exerciseTime_fragment);
            mConstraintLayout = itemView.findViewById(R.id.holder_exercise_list_fragment);
            mExerciseImage = itemView.findViewById(R.id.holder_exerciseList_image);
        }
    }

    public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListHolder> {

        private List<Exercises> mExercisesList;

        public ExerciseListAdapter(List<Exercises> exercises) {
            mExercisesList = exercises;
        }

        @NonNull
        @Override
        public ExerciseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View listItem = inflater.inflate(R.layout.holder_exercise_list_fragment, parent, false);
            ExerciseListHolder holder = new ExerciseListHolder(listItem);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ExerciseListHolder holder, final int position) {
            holder.mExerciseName.setText(mExercises.get(position).getName());
            holder.mExerciseTime.setText("00:" + String.valueOf(mExercises.get(position).getDuration()));
            holder.mExerciseImage.setImageResource(getResources().getIdentifier(mExercises.get(position).getImage(), "drawable", getActivity().getPackageName()));
            holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ExerciseDescriptionActivity.class);
                    intent.putExtra("name", holder.mExerciseName.getText().toString());
//                    intent.putExtra("time", Integer.parseInt(holder.mExerciseTime.getText().toString()));
                    intent.putExtra("image", mExercises.get(position).getImage());
                    intent.putExtra("guidelines", mExercises.get(position).getGuidelines());
                    intent.putExtra("link", mExercises.get(position).getHelp());
//                    intent.putExtra("type", false);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mExercises.size();
        }
    }

}
