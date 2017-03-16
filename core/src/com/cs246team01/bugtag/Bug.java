package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

/**
 * Class:Bug
 * This class contains the implementation of a bug, which extends GridObject
 * A bug will have a specific texture and a player ID. This object will
 * be controlled by the player. When the bug goes offscreen, it's "hide"
 * function will keep track of how long it has been there.
 * getPlayerID Returns which player controls this bug
 */

public class Bug extends GridObject {

    private static final String TAG = "DebugTagger";

    private boolean isChaser;

    //To keep track of player1 and player2 bugs
    private int playerID;

    // bug/player hit box
    private static Rectangle bug_hitbox;

    public Bug(){
    }

    Bug(Texture bugImage, boolean chaser, int playerID){
        //This sets whether the bug will be the chaser randomly
        isChaser = chaser;

        this.setTexture(bugImage);
        //Set bug in bottom left corner

        makeHitBox(bugImage);
        // make hit box for bug/player

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

    private void makeHitBox(Texture bugImage){
        float bug_height = bugImage.getHeight();
        float bug_width  = bugImage.getWidth();

        bug_hitbox = new Rectangle(currentPosition.x, currentPosition.y, bug_width, bug_height);
    }

    Rectangle getHitBox(){
        return bug_hitbox;
    }

    int getPlayerID() {
        return playerID;
    }

    //When the bug goes off of the screen
    public void hide(){

    }
}
