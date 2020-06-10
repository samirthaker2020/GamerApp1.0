package com.example.gamerapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

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
        }
    }
}