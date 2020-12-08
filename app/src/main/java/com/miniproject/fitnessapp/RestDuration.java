package com.miniproject.fitnessapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ankushgrover.hourglass.Hourglass;

public class RestDuration extends Fragment {

    private TextView mRestDuration;
    private Boolean mBackPressed = false;
    private Hourglass mHourglass;
    private Exercises mExercises;
    private TextView mExerciseName;
    private TextView mExerciseTime;
    private ImageView mExerciseImage;
    private Button mSkipRestDuration;
    private ProgressBar mProgressBar;
    private ConstraintLayout mConstraintLayout;
    private Boolean anotherActivityOpened = false;
    private MediaPlayer oneMediaPlayer;
    private MediaPlayer twoMediaPlayer;
    private MediaPlayer threeMediaPlayer;
    private MediaPlayer startMediaPlayer;

    public RestDuration(Exercises exercises) {
        mExercises = exercises;
    }

    @Override
    public void onResume() {
        if(anotherActivityOpened) {
            mHourglass.resumeTimer();
            anotherActivityOpened = false;
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        oneMediaPlayer.release();
        twoMediaPlayer.release();
        threeMediaPlayer.release();
        startMediaPlayer.release();
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_duration, container, false);
//        final int[] progress = {10};
        final long time = ExerciseCategoriesActivity.mRestDuration * 1000;

        mProgressBar = view.findViewById(R.id.progressBar);

        oneMediaPlayer = MediaPlayer.create(getActivity(), R.raw.one);
        twoMediaPlayer = MediaPlayer.create(getActivity(), R.raw.two);
        threeMediaPlayer = MediaPlayer.create(getActivity(), R.raw.three);
        startMediaPlayer = MediaPlayer.create(getActivity(), R.raw.start_stop);


        mRestDuration = view.findViewById(R.id.restDuration);
        mHourglass = new Hourglass(time, 1000) {
            @Override
            public void onTimerTick(long timeRemaining) {
                mRestDuration.setText(String.valueOf(timeRemaining/1000));
                if(!mBackPressed) {
                    if(timeRemaining == 3000) {
                        threeMediaPlayer.start();
                    } else if(timeRemaining == 2000) {
                        twoMediaPlayer.start();
                    } else if(timeRemaining == 1000) {
                        oneMediaPlayer.start();
                    } else if(timeRemaining == time) {
                        startMediaPlayer.start();
                    }
                }
                mProgressBar.setProgress((int) (((time - timeRemaining)*100)/time));
            }

            @Override
            public void onTimerFinish() {
                if(!mBackPressed) {
                    mRestDuration.setText("GO!");
//                    startMediaPlayer.start();
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(intent);
                }
            }
        };
        mHourglass.startTimer();

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

        mExerciseName = view.findViewById(R.id.rest_duration_name);
        mExerciseName.setText(mExercises.getName());

        mExerciseTime = view.findViewById(R.id.rest_duration_time);
        mExerciseTime.setText("00:" + String.valueOf(mExercises.getDuration()));

        mExerciseImage = view.findViewById(R.id.rest_duration_image);
        mExerciseImage.setImageResource(getResources().getIdentifier(mExercises.getImage(), "drawable", getActivity().getPackageName()));

        mSkipRestDuration = view.findViewById(R.id.rest_duration_skip);
        mSkipRestDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackPressed = true;
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

        mConstraintLayout = view.findViewById(R.id.rest_duration_constraintLayout);
        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anotherActivityOpened = true;
                mHourglass.pauseTimer();
                Intent intent = new Intent(getActivity(), ExerciseDescriptionActivity.class);
                intent.putExtra("name", mExercises.getName());
                intent.putExtra("guidelines", mExercises.getGuidelines());
                intent.putExtra("image", mExercises.getImage());
                intent.putExtra("link", mExercises.getHelp());
                startActivity(intent);
            }
        });

        return view;
    }
}