package com.miniproject.fitnessapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ankushgrover.hourglass.Hourglass;

public class ExerciseFragment extends Fragment {

    private final Exercises mExercises;
    private final boolean mStart;
    private TextView mTimeDuration;
    private Boolean mBackPressed = false;
    private Hourglass mHourglass;
    private boolean mPauseClicked = false;
    private boolean mAnotherActivityStarted = false;
    private final Exercises mNextExercise;
    private ProgressBar mProgressBar;
    private MediaPlayer oneMediaPlayer;
    private MediaPlayer twoMediaPlayer;
    private MediaPlayer threeMediaPlayer;
    private MediaPlayer stopMediaPlayer;

    public ExerciseFragment(Exercises exercises, Exercises nextExercise, Boolean start) {
        mExercises = exercises;
        mStart = start;
        mNextExercise = nextExercise;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        oneMediaPlayer.release();
        twoMediaPlayer.release();
        threeMediaPlayer.release();
        stopMediaPlayer.release();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        if(mAnotherActivityStarted) {
            mHourglass.resumeTimer();
            mAnotherActivityStarted = false;
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_exercise, container, false);

        TextView exerciseName = view.findViewById(R.id.exerciseName);
        exerciseName.setText(mExercises.getName());

        mTimeDuration = view.findViewById(R.id.display_time);
        mTimeDuration.setText(String.valueOf(mExercises.getDuration()));

        final long startTime = System.currentTimeMillis();
        Button skipExercise = view.findViewById(R.id.skip_exercise);
        skipExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackPressed = true;
                StartExerciseActivity.calories += ((int)(System.currentTimeMillis() - startTime)/1000) * mExercises.getCalories() * ExerciseCategoriesActivity.mUserWeight / 60;
                Fragment fragment = new RestDuration(mNextExercise);
                getFragmentManager().beginTransaction().replace(R.id.start_exercise_layout, fragment).commit();
            }
        });

        ImageView exerciseAnimation = view.findViewById(R.id.exerciseAnimation);
        exerciseAnimation.setImageResource(getResources().getIdentifier(mExercises.getImage(), "drawable", getActivity().getPackageName()));

        mProgressBar = view.findViewById(R.id.exercise_progressBar);
        mProgressBar.setProgress(10);
        final long timeForProgressBar = mExercises.getDuration()*1000;

        oneMediaPlayer = MediaPlayer.create(getActivity(), R.raw.one);
        twoMediaPlayer = MediaPlayer.create(getActivity(), R.raw.two);
        threeMediaPlayer = MediaPlayer.create(getActivity(), R.raw.three);
        stopMediaPlayer = MediaPlayer.create(getActivity(), R.raw.lets_go);


        if(mStart) {
            mHourglass = new Hourglass(mExercises.getDuration()*1000, 1000) {
                @Override
                public void onTimerTick(long timeRemaining) {
                    mTimeDuration.setText(String.format("00:%02d", timeRemaining / 1000));

                    if(!mBackPressed) {
                        if(timeRemaining == 3000) {
                            threeMediaPlayer.start();
                        } else if(timeRemaining == 2000) {
                            twoMediaPlayer.start();
                        } else if(timeRemaining == 1000) {
                            oneMediaPlayer.start();
                        } else if(timeRemaining == timeForProgressBar) {
                            stopMediaPlayer.start();
                        }
                    }

                    mProgressBar.setProgress((int) (((timeForProgressBar - timeRemaining)*100)/timeForProgressBar));
                }

                @Override
                public void onTimerFinish() {
                    if(!mBackPressed) {
                        mTimeDuration.setText("DONE!");
//                        stopMediaPlayer.start();
                        StartExerciseActivity.calories += (System.currentTimeMillis() - startTime)/1000 * mExercises.getCalories();
                        Fragment fragment = new RestDuration(mNextExercise);
                        getFragmentManager().beginTransaction().replace(R.id.start_exercise_layout, fragment).commit();
                    }
                }
            };
            mHourglass.startTimer();

            final Button pauseButton = view.findViewById(R.id.pauseExercise);
            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mPauseClicked) {
                        mHourglass.resumeTimer();
                        mPauseClicked = false;
                        pauseButton.setText("Pause");
                    } else {
                        mHourglass.pauseTimer();
                        mPauseClicked = true;
                        pauseButton.setText("Resume");
                    }
                }
            });
        }

        RelativeLayout exerciseInstrutions = view.findViewById(R.id.exercise_instructions);
        exerciseInstrutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHourglass.pauseTimer();
                mAnotherActivityStarted = true;
                Intent intent = new Intent(getActivity(), ExerciseDescriptionActivity.class);
                intent.putExtra("name", mExercises.getName());
                intent.putExtra("guidelines", mExercises.getGuidelines());
                intent.putExtra("image", mExercises.getImage());
                intent.putExtra("link", mExercises.getHelp());
                startActivity(intent);
            }
        });

        ImageButton utube = view.findViewById(R.id.getHelp);
        utube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHourglass.pauseTimer();
                mAnotherActivityStarted = true;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mExercises.getHelp()));
                startActivity(intent);
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == keyEvent.KEYCODE_BACK) {
//                    getFragmentManager().popBackStack();
                    mBackPressed = true;
                    ExercisesListFragment.count = 0;
                    getActivity().onBackPressed();
                    return true;
                }

                return false;
            }
        });
        return view;
    }
}
