package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;

/**
 * Created by cameronlewis on 3/16/17.
 */

public class Win {
    //stuff to check for winner
    private int bugOne_pos_x;
    private int bugOne_pos_y;
    private int bugTwo_pos_x;
    private int bugTwo_pos_y;
    private boolean isInRange_X;
    private boolean isInRange_Y;
    private int win_status;

    Win(){
        win_status = 0;
    }

    /*Win(GridObjectHandler bugGame){

    }*/

    int checkWin(GridObjectHandler bugGame){
        bugOne_pos_x = bugGame.getBugOne().currentPosition.x;
        bugOne_pos_y = bugGame.getBugOne().currentPosition.y;
        bugTwo_pos_x = bugGame.getBugTwo().currentPosition.x;
        bugTwo_pos_y = bugGame.getBugTwo().currentPosition.y;
        isInRange_X = Math.abs(bugOne_pos_x - bugTwo_pos_x) <= 7;
        isInRange_Y = Math.abs(bugOne_pos_y - bugTwo_pos_y) <= 44;

         if (isInRange_X && isInRange_Y){
             Gdx.app.log("WinTag", "Chaser touched evader! Game over!");
             isInRange_X = false;
             isInRange_Y = false;
             //bugGame.resetBugPositions();
             //this allows us to reset one time
            // reset = false;
             return win_status = 3;
         }
         else
             return win_status = 1;

     }

     void resetWinStatus(){
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


