package com.example.gamerapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.gamerapp.R;

public class GameDetails extends AppCompatActivity {
String gameUrl,gamename;
    VideoView simpleVideoView;
    MediaController mediaControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            gameUrl = (String) b.get("gametrailer");
            gamename=(String) b.get("gamename");
            showvideo(gameUrl);
            getSupportActionBar().setTitle(gamename);
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
    private  void showvideo(String url)
    {
        VideoView vidView = (VideoView)findViewById(R.id.videoView_gameview);
        String vidAddress =  url;
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        vidView.stopPlayback();
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
    }
}
