package com.cs246team01.bugtag;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Landon on 2/23/2017.
 */

public class GridObjectHandler {

    //this holds any object used in game
    private List<GridObject> gridObjects;

    //this will be initialized during the create method
    //of the main game
    public GridObjectHandler() {

        gridObjects = new ArrayList<GridObject>();

        Texture bugOne = new Texture("yellow_idle.png");
        Bug playerOne = new Bug(bugOne);

        gridObjects.add(playerOne);


    }

    //this is the only method we will call in the render method
    public void run(boolean move) {
        update(move);
    }

    //this method checks if we should stop the game
    //it returns a 1 if the chaser wins, 2 if the timer runs out;
    // -1 if game is still going
    public int checkWin() {
        return 0;
    }


    //checks if screen has been tapped and moves the bug
    private void update(boolean move) {
        //movement code here...
        if (move)
            gridObjects.get(0).moveUp();



    }

    //keeping it modularized
    public void draw(SpriteBatch batch) {

        for (GridObject g : gridObjects)
            batch.draw(g.getTexture(), g.getX(), g.getY());

    }


}