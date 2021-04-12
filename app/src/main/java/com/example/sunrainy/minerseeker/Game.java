package com.example.sunrainy.minerseeker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity {

    private int numOfmines;
    private int gameHeight;
    private int gameWidth;
    private int scanUsed=0;
    private int foundMines=0;
    private Button matrixOfButtons[][];
    private populateGameMatrix gameMatrix;
    private int numOfStartedGames;
    private int bestscore;
    public static final String PLAYED_GAME_KEY = "Number of Played Games";
    public static final String BEST_SCORE_PREFIX = "Find Best Score ";
    public final String SAVED_BOARD_PREFIX = "Saved Game";
    public final String BOARD_PREFS_NAME = "Saved Game ";
    public final String FOUND_MINES_KEY = "Found Mines";
    public final String SCAN_USED_KEY = "Number of Scan Time Used";
    private boolean savedGameFound = false;

    public Game() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadSavedSettings();
        matrixOfButtons=new Button[gameHeight][gameWidth];
        gameMatrix =new populateGameMatrix(gameHeight,gameWidth,numOfmines);
        populatebuttons();
        findSavedGame();
        loadSavedGameStats();


        if(savedGameFound){
            loadSavedGameStatus();
            gameMatrix.loadSavedGame(this,BOARD_PREFS_NAME,SAVED_BOARD_PREFIX);
        }
        else{
            numOfStartedGames++;
            saveNumOfStartedGames();
            gameMatrix.saveBoard(this,BOARD_PREFS_NAME,SAVED_BOARD_PREFIX);
        }
        updateTxtview();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public  void  onWindowFocusChanged(boolean hasfocus){
        super.onWindowFocusChanged(hasfocus);
        if(savedGameFound){
            ButtonSizes();
            for(int row=0;row <gameHeight;row++){
                for(int col=0;col<gameWidth;col++){
                    if(gameMatrix.isMine(row,col)&&gameMatrix.isFound(row,col)){
                        Button button=matrixOfButtons[row][col];
                        int newHeight=button.getHeight();
                        int newWidth=button.getWidth();
                        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.shikigame1);
                        Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,newWidth,newHeight,true);
                        Resources resources=getResources();
                        button.setBackground(new BitmapDrawable(resources,scaledBitmap));
                    }
                    if(gameMatrix.isClicked(row,col)){
                        matrixOfButtons[row][col].setText(gameMatrix.checkGrid(row,col)+"");
                        if(gameMatrix.isMine(row,col)){
                            matrixOfButtons[row][col].setTextColor(Color.BLACK);
                        }
                    }
                }
            }
        }
    }
    @SuppressLint({"SetTextI18n", "StringFormatInvalid"})
    private void updateTxtview() {
        TextView foundMined=(TextView)findViewById(R.id.txtFoundMines);
        TextView Numscan =(TextView)findViewById(R.id.txtNumScan);
        TextView NumOfGamePlayed=(TextView)findViewById(R.id.txtPlayedGame);
        TextView BestScore=(TextView)findViewById(R.id.txtBestscoresofar);
        foundMined.setText(getString(R.string.found_x_of_y_mines,foundMines,numOfmines));
        Numscan.setText(getString(R.string.scans_used,scanUsed));
        NumOfGamePlayed.setText(getString(R.string.number_of_games_played_so_far,numOfStartedGames));
        if(bestscore> gameHeight*gameWidth){
            BestScore.setText("");
        }
        else{
            BestScore.setText(getString(R.string.least_scan_record,""+bestscore));
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void checkMine(int row, int col){
        ButtonSizes();

        if(gameMatrix.isMineAndNotFound(row, col)){
            gameMatrix.setFound(row, col);
            Button button = matrixOfButtons[row][col];
            int newHeight =button.getHeight();
            int newWidth =button.getWidth();
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.shikigame1);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,newWidth,newHeight,true);
            Resources resources=getResources();
            button.setBackground(new BitmapDrawable(resources,scaledBitmap));
            foundMines++;
            refreshButton(row,col);
        }
        else{
            if(!gameMatrix.isClicked(row, col)){
                scanUsed++;
                gameMatrix.setClicked(row, col);
            }
            matrixOfButtons[row][col].setText(""+gameMatrix.checkGrid(row, col));
            if(gameMatrix.isMine(row, col)){
                matrixOfButtons[row][col].setTextColor(Color.BLACK);
            }
        }
        if(foundMines >= numOfmines)
            shutdonwGame();
        else{
            gameMatrix.saveBoard(Game.this,BOARD_PREFS_NAME,SAVED_BOARD_PREFIX);
            saveGameStatus();
        }
        updateTxtview();
    }

    private void shutdonwGame() {
        gameMatrix.clearSavedBoard(this,BOARD_PREFS_NAME,SAVED_BOARD_PREFIX);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        WinningMsg dialog =new WinningMsg();
        dialog.show(manager,"winning");
        if(scanUsed < bestscore){
            bestscore =scanUsed;
            saveBestScore();
        }
    }

    private void refreshButton(int row, int col) {
        for(int i = 0; i < gameWidth; i++){
            if(gameMatrix.isClicked(row, i)){
                matrixOfButtons[row][i].setText(""+gameMatrix.getPosition(row, i));
            }
        }
        for(int i = 0; i < gameHeight; i++){
            if(gameMatrix.isClicked(i, col)){
                matrixOfButtons[i][col].setText(""+gameMatrix.getPosition(i, col));
            }
        }
    }

    private void saveNumOfStartedGames() {
        SharedPreferences setting =getSharedPreferences(MainMenu.PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=setting.edit();
        editor.putInt(PLAYED_GAME_KEY,numOfStartedGames);
        editor.apply();
    }
    private void saveBestScore(){
        SharedPreferences setting =getSharedPreferences(MainMenu.PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=setting.edit();
        editor.putInt(BEST_SCORE_PREFIX+numOfmines+'-'+gameHeight+'x'+gameWidth,bestscore);
        editor.apply();
    }

    private void loadSavedGameStatus() {
        SharedPreferences settings=getSharedPreferences(BOARD_PREFS_NAME,MODE_PRIVATE);
        foundMines=settings.getInt(FOUND_MINES_KEY,0);
        scanUsed=settings.getInt(SCAN_USED_KEY,0);
    }
    private void saveGameStatus(){
        SharedPreferences settings=getSharedPreferences(BOARD_PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt(FOUND_MINES_KEY,foundMines);
        editor.putInt(SCAN_USED_KEY,scanUsed);
        editor.apply();
    }

    private boolean findSavedGame() {
        SharedPreferences setting =getSharedPreferences(BOARD_PREFS_NAME,MODE_PRIVATE);
        savedGameFound=setting.getBoolean(SAVED_BOARD_PREFIX+gameHeight+"x"+gameWidth+"-"+numOfmines+" "
                ,false);
        return savedGameFound;
    }
    private void loadSavedSettings() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gameHeight = settings.getInt(MainActivity.BOARD_HEIGHT_KEY, 4);
        gameWidth = settings.getInt(MainActivity.BOARD_WIDTH_KEY, 6);
        numOfmines = settings.getInt(MainActivity.MINE_NUMBER_KEY, 6);
    }

    private void loadSavedGameStats() {
        SharedPreferences settings =getSharedPreferences(MainMenu.PREFS_NAME,MODE_PRIVATE);
        numOfStartedGames=settings.getInt(PLAYED_GAME_KEY,0);
        bestscore=settings.getInt(BEST_SCORE_PREFIX+numOfmines+'-'+gameHeight+'x'+gameWidth,gameHeight*gameWidth+1);
    }

    private void ButtonSizes(){
        for(int row=0;row <gameHeight;row++){
            for(int col=0;col<gameWidth;col++){
                Button button=matrixOfButtons[row][col];
                button.setMinHeight(button.getHeight());
                button.setMaxHeight(button.getHeight());
                button.setMinWidth(button.getWidth());
                button.setMaxWidth(button.getWidth());
            }
        }
    }

    private void populatebuttons() {
        TableLayout table=(TableLayout) findViewById(R.id.tableforbtns);
        for(int row = 0; row< gameHeight;row++){
            TableRow tableRow=new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for(int col=0;col <gameWidth;col++){
                final int FINAL_ROW=row;
                final int FINAL_COL=col;
                Button button=new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                tableRow.addView(button);


                //set count number on the button
                matrixOfButtons[row][col]=button;
                button.setPadding(0,0,0,0);
                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        checkMine(FINAL_ROW,FINAL_COL);
                    }
                });



            }
        }

    }


    public static Intent makeIntent(Context context) {
        return new Intent(context,Game.class);
    }
}
