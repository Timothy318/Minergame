package com.example.sunrainy.minerseeker;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Option extends AppCompatActivity {
    private int saveheight;
    private int savewidth;
    private int saveminesnum;
    private int timesofgames;
    private int bestScore;
    private String boardsizeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        OptionBack();
        loadSavedSettings();
        OptionOkaybtn();
        ResetbestBtn();
        Resettimesbtn();
        radioNumOfMine();
        radioBoardsize();
        updateTextView();
    }
    private void ResetbestBtn() {
        Button btn = (Button)findViewById(R.id.ResetBestScorebtn);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                clearBestScore();
                bestScore = saveheight*savewidth+1;
                updateTextView();
            }
        });
    }


    private void clearNumOfGameStarted() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Game.PLAYED_GAME_KEY);
        editor.apply();
    }

    private void Resettimesbtn(){
        Button btn = (Button)findViewById(R.id.ResetNumPlayed);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clearNumOfGameStarted();
                timesofgames = 0;
                updateTextView();
            }
        });
    }

    private void loadSavedSettings() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        saveheight = settings.getInt(MainActivity.BOARD_HEIGHT_KEY, 4);
        savewidth = settings.getInt(MainActivity.BOARD_WIDTH_KEY, 6);
        saveminesnum = settings.getInt(MainActivity.MINE_NUMBER_KEY, 6);
        timesofgames = settings.getInt(Game.PLAYED_GAME_KEY, 0);
        bestScore = settings.getInt(Game.BEST_SCORE_PREFIX+saveminesnum+'-'+saveheight+'x'+savewidth, saveheight*savewidth+1);
    }

    private void updateTextView() {
        TextView txtTimesplayed  = (TextView)findViewById(R.id.OptionsNumOfGamePlayed);
        TextView txtBestScore = (TextView)findViewById(R.id.OptionsBestRecord);
        txtTimesplayed .setText(getString(R.string.number_of_games_played_so_far, timesofgames));
        if(bestScore > savewidth*saveheight)
            txtBestScore.setText(getString(R.string.least_scan_record, getString(R.string.not_found)));
        else
            txtBestScore.setText(getString(R.string.least_scan_record, ""+bestScore));
    }

    private void radioBoardsize() {
        RadioGroup grp = (RadioGroup)findViewById(R.id.rdgrpSizeOfBoard);
        String[] sizesOfBoard = getResources().getStringArray(R.array.board_size);
        for (String aSizesOfBoard : sizesOfBoard) {
            RadioButton btn = new RadioButton(this);
            btn.setText(aSizesOfBoard);
            btn.setShadowLayer(3.0f, 1.0f, 1.0f, Color.BLACK);
            grp.addView(btn);
            if(aSizesOfBoard.equals(saveheight+"x"+savewidth)){
                btn.setChecked(true);
            }
        }
    }
    private void radioNumOfMine() {
        RadioGroup grp = (RadioGroup)findViewById(R.id.rdgrpNumOfMines);
        int[] arrNumOfMines = getResources().getIntArray(R.array.number_of_mines);
        for (int numOfMine : arrNumOfMines) {
            RadioButton btn = new RadioButton(this);
            btn.setText(numOfMine + "");
            btn.setShadowLayer(3.0f, 1.0f, 1.0f, Color.BLACK);
            grp.addView(btn);
            if(numOfMine == saveminesnum) {
                btn.setChecked(true);
            }
        }
    }

    private void OptionOkaybtn() {
        Button btn = (Button)findViewById(R.id.btnOptionsOkay);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RadioGroup grpMine = (RadioGroup)findViewById(R.id.rdgrpNumOfMines);
                RadioGroup grpBoardSize = (RadioGroup)findViewById(R.id.rdgrpSizeOfBoard);
                int mineselected = grpMine.getCheckedRadioButtonId();
                int selectedBoardSizeId = grpBoardSize.getCheckedRadioButtonId();
                if(mineselected !=-1) {
                    RadioButton selectedMineBtn = (RadioButton) findViewById(mineselected);
                    saveminesnum = Integer.parseInt(selectedMineBtn.getText().toString());
                }
                if(selectedBoardSizeId != -1){
                    RadioButton selectedBoardSizeBtn = (RadioButton)findViewById(selectedBoardSizeId);
                    boardsizeString = selectedBoardSizeBtn.getText().toString();
                    String sizeOfBoardInArray[] = boardsizeString.split("x");
                    saveheight = Integer.parseInt(sizeOfBoardInArray[0]);
                    savewidth = Integer.parseInt(sizeOfBoardInArray[1]);
                }
                saveSettings();
                finish();
            }
        });
    }

    private void saveSettings(){
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MainActivity.BOARD_HEIGHT_KEY, saveheight);
        editor.putInt(MainActivity.BOARD_WIDTH_KEY, savewidth);
        editor.putInt(MainActivity.MINE_NUMBER_KEY, saveminesnum);
        editor.apply();
    }

    private void clearBestScore(){
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putInt(MainActivity.BOARD_HEIGHT_KEY, saveheight);
        editor.putInt(MainActivity.BOARD_WIDTH_KEY, savewidth);
        editor.putInt(MainActivity.MINE_NUMBER_KEY, saveminesnum);
        editor.putInt(Game.PLAYED_GAME_KEY, timesofgames);
        editor.apply();
    }



    public static Intent makeIntent(Context context) {
        return new Intent(context,Option.class);
    }
    private void OptionBack() {
        Button btn=(Button) findViewById(R.id.backbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Option.this,"you Cicked back button", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
