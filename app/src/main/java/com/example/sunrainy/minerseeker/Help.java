package com.example.sunrainy.minerseeker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setuphelpbackbtn();
    }

    private void setuphelpbackbtn() {
        Button btn=(Button) findViewById(R.id.helpbackbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Help.this,"you Cicked back button",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context,Help.class);
    }
}
