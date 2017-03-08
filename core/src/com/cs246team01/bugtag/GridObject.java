package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Class: Grid Object
 * The base class for all objects to appear on the grid
 */

abstract public class GridObject {

    //use this for taggin' them bugs
    public static final String TAG = "DebugTagger";
    private static final int MAXSTEPS = 25;

    //data
    protected GridPoint2 currentPosition = new GridPoint2();
    protected int priority;
    Texture objectTexture;

    //default constructor
    public GridObject() {
        //currentPosition(0, 0);
        priority = 0;
    }

    //constructor for setting x & y positions
    public GridObject(int x, int y, int myPriority) {
        currentPosition.set(x, y);
        priority = myPriority;
    }

    //setters
    public void setPosition(GridPoint2 pos)        { currentPosition = pos; }
    public void setPriority(int myPriority)   { priority = myPriority; }
    public void setTexture(Texture myTexture) { this.objectTexture = myTexture; }

    //getters
    public GridPoint2 getPosition() { return currentPosition; }
    public int getPriority()   { return priority;        }
    public int getX() { return currentPosition.x; }
    public int getY() { return currentPosition.y; }
    public Texture getTexture() { return objectTexture; }

    //methods for moving the objects
    /**********************************************************
     * Movement comments:
     * Using LibGDX, the screen is divided in this manner (phone is held sideways)
     *
     *      x = 0                                 x = width,y = height
     * y = height--------------------------------------
     *          |                                     |
     *          |                                     |
     *          |                                     |
     *          |                                   O
     *          |                              home button
     *          |
     *          |------------------------------------- x = width
     *     y = 0                                       y = 0
     *
     * we can create a grid-like movement by determining how many steps the player
     * will take before hitting the wall and dividing the height/width by that number.
     * ex. Right movement = ( width / steps ) + current x value;
     *
     * When the player reaches the same coordinate as the max width/height, we will hide the bug off
     * of the screen for a set period of time, then place them back on the edge
     ***********************************************************************************/
    public void moveRight() {

        if (this.currentPosition.y < Gdx.graphics.getHeight()-6)
            this.currentPosition.y += Gdx.graphics.getHeight() / MAXSTEPS;
        else
            this.hide();

        //keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    public void moveLeft(){
        if(this.currentPosition.y > 6)
            this.currentPosition.y -= Gdx.graphics.getHeight() / MAXSTEPS;
        else
            this.hide();

        //keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    public void moveUp(){

        if(this.currentPosition.x > 6)
            this.currentPosition.x -= Gdx.graphics.getWidth() / MAXSTEPS;
        else
            this.hide();

        //keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    public void moveDown(){

        if(this.currentPosition.x < Gdx.graphics.getWidth() - 6 )
            this.currentPosition.x += Gdx.graphics.getWidth() / MAXSTEPS;
        else
            this.hide();

        //keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    abstract void hide();

}
