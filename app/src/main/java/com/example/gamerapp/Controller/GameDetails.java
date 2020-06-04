package com.example.gamerapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.gamerapp.R;

public class GameDetails extends AppCompatActivity {
String gameUrl;
    VideoView simpleVideoView;
    MediaController mediaControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            gameUrl = (String) b.get("gamelink");
            System.out.println(gameUrl);

        }


    }
}
