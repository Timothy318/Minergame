package com.example.sunrainy.minerseeker;

/**
 * Created by sunrainy on 2018/2/15.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class populateGameMatrix {
    private int height;
    private int width;
    private int numberOfMines;
    private grid matrixOfMines [][];

    populateGameMatrix(int height, int width, int numOfMines) {
        this.height = height;
        this.width = width;
        this.matrixOfMines = new grid[height][width];
        this.numberOfMines = numOfMines;
        populateGrid();
    }

    public int checkGrid(int row, int col){
        if(matrixOfMines[row][col].isMineAndNotFound()){
            return -1;
        }
        else{
            return matrixOfMines[row][col].getNum();
        }
    }

    private void populateGrid(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++)
                matrixOfMines[i][j] = new grid(false, 0);
        }
        int mineRow, mineCol;
        List<int[]> listOfPositions = new ArrayList<>();
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                listOfPositions.add(new int[]{i, j});
            }
        }
        for(int i = 0; i < numberOfMines; i++){
            int selectedPosition = new Random().nextInt(listOfPositions.size());
            mineRow = listOfPositions.get(selectedPosition)[0];
            mineCol = listOfPositions.get(selectedPosition)[1];
            listOfPositions.remove(selectedPosition);
            matrixOfMines[mineRow][mineCol].setMine();
            for(int j = 0; j < height; j++) {
                matrixOfMines[j][mineCol].incNum();
            }
            for(int j = 0; j < width; j++) {
                matrixOfMines[mineRow][j].incNum();
            }
        }

    }

    public int getPosition(int row, int col) {
        return matrixOfMines[row][col].getNum();
    }

    public boolean isClicked(int row, int col){
        return matrixOfMines[row][col].isClicked();
    }

    public boolean isMineAndNotFound(int row, int col){
        return matrixOfMines[row][col].isMineAndNotFound();
    }

    public void setClicked(int row, int col){
        matrixOfMines[row][col].setClicked();
    }

    public void setFound(int row, int col){
        matrixOfMines[row][col].setFound();
        for(int i = 0; i < height; i++){
            if(matrixOfMines[i][col].getNum() >= 0)
                matrixOfMines[i][col].decNum();
        }
        for(int i = 0; i < width; i++){
            if(matrixOfMines[row][i].getNum()>=0)
                matrixOfMines[row][i].decNum();
        }
    }

    public void saveBoard(Context context, String prefName, String preFix){
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        String newPreFix = preFix+height+"x"+width+"-"+numberOfMines+" ";
        String strToPut;
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                strToPut = matrixOfMines[row][col].getNum()+"-"+matrixOfMines[row][col].isMine()+"-"+matrixOfMines[row][col].isFound()+"-"+matrixOfMines[row][col].isClicked();
                editor.putString(newPreFix+row+"x"+col, strToPut);
            }
        }
        editor.putBoolean(newPreFix, true);
        editor.apply();
    }

    public void clearSavedBoard(Context context, String prefName, String preFix){
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putBoolean(preFix, false);
        editor.apply();
    }

    public void loadSavedGame(Context context, String prefName, String preFix){
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String newPreFix = preFix+height+"x"+width+"-"+numberOfMines+" ";
        if(settings.getBoolean(newPreFix, false)){
            for(int row = 0; row < height; row++){
                for(int col = 0; col < width; col++){
                    matrixOfMines[row][col] = new grid(settings.getString(newPreFix+row+"x"+col, ""));
                }
            }
        }
    }

    public boolean isMine(int row, int col){
        return matrixOfMines[row][col].isMine();
    }

    public boolean isFound(int row, int col){
        return matrixOfMines[row][col].isFound();
    }

}

