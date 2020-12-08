package com.miniproject.fitnessapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PlayVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type");

        VideoView videoView = findViewById(R.id.play_video);
        ImageView imageView = findViewById(R.id.show_image);
        if (type.equals("video")) {

            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            String uri = bundle.getString("uri");

            videoView.setVideoURI(Uri.parse(uri));

            MediaController controller = new MediaController(this);
            controller.setAnchorView(videoView);

            videoView.setMediaController(controller);
            videoView.start();

        } else {
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

            String link = bundle.getString("link");

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            Picasso.get().load(link).resize(width, height).into(imageView);
//            imageView.setImageURI(Uri.parse(link));
        }

    }
}