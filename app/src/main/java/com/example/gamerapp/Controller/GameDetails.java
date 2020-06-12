package com.example.gamerapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.gamerapp.Adapter.TabViewPagerAdapter;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.example.gamerapp.Tabs.GameCastcrew;
import com.example.gamerapp.Tabs.GameDescription;
import com.example.gamerapp.Tabs.GameOverview;
import com.example.gamerapp.Tabs.ReviewRead;
import com.example.gamerapp.Tabs.ReviewWrite;
import com.google.android.material.tabs.TabLayout;

public class GameDetails extends AppCompatActivity {
String gameUrl,gamename;
        String gameid;
    VideoView simpleVideoView;
    MediaController mediaControls;
    private TabLayout t1;
    private ViewPager v1;
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
            gameid=(String) b.get("gameid") ;

            if(gameid!=null)
            {
                Constants.CUURENT_GAMEID=gameid;
            }
            showvideo(gameUrl);
            getSupportActionBar().setTitle(gamename);
        }
        t1= (TabLayout) findViewById(R.id.tab1);
        v1=(ViewPager) findViewById(R.id.vpager1);
        TabViewPagerAdapter adapter=new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new GameOverview(),"Overview");
        adapter.AddFragment(new GameDescription(),"Synopsis");
        adapter.AddFragment(new GameCastcrew(),"Cast & Crew");
        adapter.AddFragment(new ReviewRead(),"Read Review");
        adapter.AddFragment(new ReviewWrite(),"Write Review");

        v1.setAdapter(adapter);
        t1.setupWithViewPager(v1);

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
