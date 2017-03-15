package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class: Grid Object
 * The base class for all objects to appear on the grid
 */

abstract public class GridObject {
    //Use this for tagging bugs
    static final String TAG = "DebugTagger";
    private static final int MAXSTEPS = 40;

    //Data
    GridPoint2 currentPosition = new GridPoint2();
    private int priority;
    Texture objectTexture;

    /*This rectangle determines the area that objects can move around in.
    * if the objects are contained within the rectangle then they can continue moving until
    * they reach the edge
    */
    protected static Rectangle playArea = new Rectangle(Gdx.graphics.getHeight()/4,0,
            //width
            Gdx.graphics.getWidth() - 2 * (Gdx.graphics.getHeight()/4) - (Gdx.graphics.getHeight()/24)
            //height
            ,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/16);


    //Default constructor
    GridObject() {
        //currentPosition(0, 0);
        priority = 0;
    }

    //Constructor for setting x & y positions
    public GridObject(int x, int y, int myPriority) {
        currentPosition.set(x, y);
        priority = myPriority;
    }

    //Setters
    public void setPosition(GridPoint2 pos)        { currentPosition = pos; }
    public void setPriority(int myPriority)   { priority = myPriority; }
    void setTexture(Texture myTexture) { this.objectTexture = myTexture; }

    //Getters
    public GridPoint2 getPosition() { return currentPosition; }
    public int getPriority()   { return priority;        }
    int getX() { return currentPosition.x; }
    int getY() { return currentPosition.y; }
    Texture getTexture() { return objectTexture; }

    //Methods for moving the objects
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

    //note: the following method names are from the perspective of bug 1's buttons
    // ex. moveRight is used for player 1's right button, and player 2's left button

    //this moves objects towards the top of the screen as shown in the diagram
    void moveRight() {

        if(playArea.contains(this.currentPosition.x,
                this.currentPosition.y + Gdx.graphics.getWidth() / MAXSTEPS))
            this.currentPosition.y += Gdx.graphics.getWidth() / MAXSTEPS;
        else
            this.hide();

        //Keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
        Gdx.app.log(TAG, "Rectangle: " + playArea.toString());
        Gdx.app.log(TAG, "Rectangle contains bug: " + playArea.contains(this.currentPosition.x ,this.currentPosition.y));
    }

    //moves towards bottom
    void moveLeft(){
        if(playArea.contains(this.currentPosition.x,
                this.currentPosition.y - Gdx.graphics.getWidth() / MAXSTEPS))
            this.currentPosition.y -= Gdx.graphics.getWidth() / MAXSTEPS;
        else
            this.hide();

        //Keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    //move towards player 2's buttons
    void moveUp(){

        if(playArea.contains(this.currentPosition.x - Gdx.graphics.getWidth() / MAXSTEPS,
                this.currentPosition.y))
            this.currentPosition.x -= Gdx.graphics.getWidth() / MAXSTEPS;
        else
            this.hide();

        //Keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    //move towards player 1
    void moveDown(){

        //if the bug will not be moving out of bounds allow it to move
        if(playArea.contains(this.currentPosition.x + Gdx.graphics.getWidth() / MAXSTEPS,
                this.currentPosition.y)) {

            this.currentPosition.x += Gdx.graphics.getWidth() / MAXSTEPS;

        }
        else{
            this.hide();

        }

        //Keep track of bug's position
        Gdx.app.log(TAG, "Rectangle: " + playArea.toString());
        Gdx.app.log(TAG, "Rectangle contains bug: " + playArea.contains(this.currentPosition.x ,this.currentPosition.y));
        //Gdx.app.log(TAG, "Position: " +this.getPosition().toString()); todo remove comment
    }

    abstract void hide();

}
