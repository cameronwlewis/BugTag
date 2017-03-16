package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by cameronlewis on 3/16/17.
 */

class GameWin {
    private int win_status;

    GameWin(){
        win_status = 0;
    }

    int checkWin(Bug bugGame){
        /*int bugOne_pos_x = bugGame.getBugOne().currentPosition.x;
        int bugOne_pos_y = bugGame.getBugOne().currentPosition.y;
        int bugTwo_pos_x = bugGame.getBugTwo().currentPosition.x;
        int bugTwo_pos_y = bugGame.getBugTwo().currentPosition.y;

        boolean isInRange_X = Math.abs(bugOne_pos_x - bugTwo_pos_x) <= 7;
        boolean isInRange_Y = Math.abs(bugOne_pos_y - bugTwo_pos_y) <= 44;*/

        Rectangle hi = new Rectangle();
        hi = bugGame.getBugOne().getHitBox();


         /*if (isInRange_X && isInRange_Y){
             Gdx.app.log("WinTag", "Chaser touched evader! Game over!");
             isInRange_X = false;
             isInRange_Y = false;
             //bugGame.resetBugPositions();
             //this allows us to reset one time
            // reset = false;
             return win_status = 3;
         }
         else*/
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


