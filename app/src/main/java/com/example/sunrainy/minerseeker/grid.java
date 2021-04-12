package com.example.sunrainy.minerseeker;

/**
 * Created by sunrainy on 2018/2/15.
 */

public class grid {
    private boolean isMine = false;
    private boolean found = false;
    private boolean clicked = false;
    private int num = 0;
    grid(boolean isMine, int numToDisplay){
        this.isMine = isMine;
        this.num = numToDisplay;
    }

    grid(String str){
        if(str.length()!=0){
            String[] spiltedPrefs = str.split("-");
            num = Integer.parseInt(spiltedPrefs[0]);
            isMine = Boolean.parseBoolean(spiltedPrefs[1]);
            found = Boolean.parseBoolean(spiltedPrefs[2]);
            clicked = Boolean.parseBoolean(spiltedPrefs[3]);
        }
    }

    public int getNum(){
        return this.num;
    }

    public void setNum(int num){
        this.num = num;
    }

    public void incNum(){
        this.num+=1;
    }

    public void decNum(){
        this.num-=1;
    }

    public void setFound(){
        this.found = true;
    }

    public void setClicked(){
        this.clicked = true;
    }

    public void setMine(){
        this.isMine = true;
    }

    public boolean isMineAndNotFound(){
        return this.isMine && !this.found;
    }

    public boolean isClicked(){
        return this.clicked;
    }

    public boolean isMine(){
        return isMine;
    }

    public boolean isFound(){
        return found;
    }
}
