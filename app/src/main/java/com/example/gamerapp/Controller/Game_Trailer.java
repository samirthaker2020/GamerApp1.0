package com.example.gamerapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.gamerapp.R;

public class Game_Trailer extends AppCompatActivity {
String gametrailer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__trailer);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            gametrailer = extras.getString("gametrailer");
            // and get whatever type user account id is
            Toast.makeText(getApplicationContext(),gametrailer, Toast.LENGTH_SHORT).show();
            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            VideoView vidView = (VideoView)findViewById(R.id.myVideo);
            String vidAddress =  gametrailer;
            Uri vidUri = Uri.parse(vidAddress);
            vidView.setVideoURI(vidUri);
            vidView.start();
            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(vidView);
            vidView.setMediaController(vidControl);
        }
    }
}