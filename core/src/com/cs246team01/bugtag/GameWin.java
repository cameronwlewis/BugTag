package com.cs246team01.bugtag;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by cameronlewis on 3/16/17.
 */

class GameWin {
    private int win_status;

    GameWin(){
        win_status = 0;
    }

    int checkWin(GridObjectHandler bugGame){
        Rectangle bug1_hitbox = bugGame.getBugOne().getHitBox();
        Rectangle bug2_hitbox = bugGame.getBugTwo().getHitBox();

        if (bug1_hitbox.contains(bug2_hitbox.getX(), bug2_hitbox.getY())){
            Bug bug1_testing = bugGame.getBugOne();
            Bug bug2_testing = bugGame.getBugTwo();
            return win_status = 3;
        }
         else
             return win_status = 1;
     }

     private void resetWinStatus(){
         win_status = 0;
     }

     boolean isResetNeeded(){
         if (win_status == 3) {
             resetWinStatus();
             return true;
         }
         else{
             resetWinStatus();
             return false;
         }
     }
}


