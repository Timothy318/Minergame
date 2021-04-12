package com.example.sunrainy.minerseeker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    public static final String PREFS_NAME = "Mine Seeker Preferences";
    public static final String Game_WIDTH_KEY = "Board Width Key";
    public static final String Game_HEIGHT_KEY = "Board Height Key";
    public static final String MINE_NUMBER_KEY = "Mine Number Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupHelpbtn();
        setupPlayGamebtn();
        setupOptionbtn();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context,MainMenu.class);
    }

    private void setupHelpbtn() {
        Button btn =(Button) findViewById(R.id.Helpbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "you clicked Help ", Toast.LENGTH_LONG).show();
                Intent intent=Help.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });

    }

    private void setupPlayGamebtn() {
        Button btn =(Button) findViewById(R.id.Playgame);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "you clicked play game ", Toast.LENGTH_LONG).show();
                Intent intent=Game.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });

    }

    private void setupOptionbtn() {
        Button btn =(Button) findViewById(R.id.Optionsbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "you clicked option ", Toast.LENGTH_LONG).show();
                Intent intent=Option.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });

    }
}
