package com.example.sunrainy.minerseeker;
//https://www.google.ca/search?biw=1035&bih=534&tbm=isch&sa=1&ei=MRiGWqbrHcb0jwO5mo2IBg&q=%E7%A9%BA%E4%B9%8B%E5%A2%83%E7%95%8C1080&oq=%E7%A9%BA%E4%B9%8B%E5%A2%83%E7%95%8C1080&gs_l=psy-ab.3...65874.66408.0.67039.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.wmEmCqTw8WI#imgdii=PvWQEsrP71KKeM:&imgrc=3OSKZD4CKFdB1M:
//https://www.youtube.com/watch?v=92YMDisG1OU
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "Mine Seeker Preferences";
    public static final String BOARD_WIDTH_KEY = "Board Width Key";
    public static final String BOARD_HEIGHT_KEY = "Board Height Key";
    public static final String MINE_NUMBER_KEY = "Mine Number Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setskipbutton();
        setupVideoView();
    }

    private void setupVideoView() {
        VideoView videoView = (VideoView) findViewById(R.id.Welcomevideo);
        String videopath = "android.resource://com.example.sunrainy.minerseeker/" + R.raw.movie;
        Uri uri = Uri.parse(videopath);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    private void setskipbutton() {
        Button btn = (Button) findViewById(R.id.SkipBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "you clicked skip ", Toast.LENGTH_LONG).show();
                Intent intent = MainMenu.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });

    }
}

