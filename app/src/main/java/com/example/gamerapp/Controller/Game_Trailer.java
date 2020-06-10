package com.example.gamerapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.gamerapp.R;

public class Game_Trailer extends AppCompatActivity {
String gametrailer,gamename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__trailer);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (extras != null) {
            gametrailer = extras.getString("gametrailer");
            gamename=extras.getString("gamename");
            // and get whatever type user account id is
           // Toast.makeText(getApplicationContext(),gametrailer, Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle(gamename);
          //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //          WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                startActivityAfterCleanup(MainPage.class);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void startActivityAfterCleanup(Class<?> cls) {

        Intent intent = new Intent(getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}