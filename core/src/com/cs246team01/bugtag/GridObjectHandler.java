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
    public void run(int move) {
        update(move);
    }

    //this method checks if we should stop the game
    //it returns a 1 if the chaser wins, 2 if the timer runs out;
    // -1 if game is still going
    public int checkWin() {
        return 0;
    }


    //checks if screen has been tapped and moves the bug

    /**************************************
     * Update(int move)
     * This method goes through each object in the game and determines which direction
     * they need to move. It passes in a movement direction which will be determined by a
     * control pad on the screen
     *
     * @param move
     */
    private void update(int move) {
        //movement code here...

        for (GridObject g : gridObjects)

            if (g instanceof Bug) {

                Bug b = (Bug) g;
                handleMove(b, move);
            }


    }

    //keeping it modularized
    public void draw(SpriteBatch batch) {

        for (GridObject g : gridObjects)
            batch.draw(g.getTexture(), g.getX(), g.getY());

    }


    public void handleMove(Bug b, int moveInt) {

        switch (moveInt) {
            case 1:
                b.moveLeft();
                break;
            case 2:
                b.moveUp();
                break;
            case 3:
                b.moveRight();
                break;
            case 4:
                b.moveDown();
                break;
        }


    }
}