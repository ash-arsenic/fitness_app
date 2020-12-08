package com.miniproject.fitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LegExerciseFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Exercises> mAllExercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_all_exercises, container, false);

        mRecyclerView = view.findViewById(R.id.show_exercises_by_category);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mAllExercises = new ArrayList<>();
        DataBaseExercises db = new DataBaseExercises(getActivity().getApplicationContext());

        mAllExercises.addAll(db.getEveryone(DataBaseExercises.LEG_BEGINNERS));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.LEG_INTERMEDIATE));
        mAllExercises.addAll(db.getEveryone(DataBaseExercises.LEG_ADVANCED));

        List<Exercises> unique = new ArrayList<>();
        unique.add(mAllExercises.get(0));

        boolean flag = false;

        for (int i=0; i<mAllExercises.size(); i++) {
            flag = false;
            for (int j=0; j<unique.size(); j++) {
                if (mAllExercises.get(i).getName().equals(unique.get(j).getName())) {
                    flag = true;
                }
            }
            if (!flag) {
                unique.add(mAllExercises.get(i));
            }
        }
        ChooseExerciseAdapter adapter = new ChooseExerciseAdapter(unique);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private class ChooseExerciseAdapter extends RecyclerView.Adapter<ChooseExerciseHolder> {

        private List<Exercises> mExercisesList;

        public ChooseExerciseAdapter(List<Exercises> exercisesList) {
            mExercisesList = exercisesList;
        }

        @NonNull
        @Override
        public ChooseExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.holder_show_exercises, parent, false);
            ChooseExerciseHolder holder = new ChooseExerciseHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChooseExerciseHolder holder, int position) {
            holder.mExerciseGif.setImageResource(getResources().getIdentifier(mExercisesList.get(position).getImage(), "drawable", getActivity().getPackageName()));
            holder.mExerciseName.setText(mExercisesList.get(position).getName());

            final Exercises exercises = mExercisesList.get(position);
            holder.mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChooseExerciseTimeDialog dialog = new ChooseExerciseTimeDialog(exercises);
                    dialog.show(getFragmentManager(), "Choose Time");
                }
            });
        }

        @Override
        public int getItemCount() {
            return mExercisesList.size();
        }
    }

    public static class ChooseExerciseHolder extends RecyclerView.ViewHolder {

        private ImageView mExerciseGif;
        private TextView mExerciseName;
        private ImageButton mAddExerciseButton;

        public ChooseExerciseHolder(@NonNull View itemView) {
            super(itemView);
            mExerciseGif = itemView.findViewById(R.id.show_exercises_image);
            mExerciseName = itemView.findViewById(R.id.show_exercises_name);
            mAddExerciseButton = itemView.findViewById(R.id.show_exercise_add);
        }
    }
}
