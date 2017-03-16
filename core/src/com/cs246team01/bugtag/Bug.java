package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Class:Bug
 * This class contains the implementation of a bug.
 */

public class Bug extends GridObject {

    private static final String TAG = "DebugTagger";

    private boolean isChaser;

    //To keep track of player1 and player2 bugs
    private int playerID;

    public Bug(){
    }


    public Bug(Texture bugImage, boolean chaser, int playerID){
        //This sets whether the bug will be the chaser randomly
        isChaser = chaser;

        this.setTexture(bugImage);
        //Set bug in bottom left corner

        if (playerID == 1) {
            currentPosition = new GridPoint2((Gdx.graphics.getWidth() * 8) / 10,
                    Gdx.graphics.getHeight()/2);
            this.playerID = 1;
        }
        else {
            currentPosition = new GridPoint2((Gdx.graphics.getWidth() * 2) / 10,
                    Gdx.graphics.getHeight() / 2);
            this.playerID = 2;
        }

        //Keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    public int getPlayerID() {
        return playerID;
    }

    //When the bug goes off of the screen
    public void hide(){

    }
}
