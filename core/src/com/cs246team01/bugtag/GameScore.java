package com.cs246team01.bugtag;

/**
 * Created by cameronlewis on 3/31/17.
 */

class GameScore {
    int winPoints;

    GameScore(){
        winPoints = 1000;
    }

    void awardWinPoints(String winnerMessage, Bug bug1_yellow, Bug bug2_red){
        if (winnerMessage.contains("Red"))
            bug2_red.setPlayerScore(winPoints);
        else
            bug1_yellow.setPlayerScore(winPoints);
    }
}
