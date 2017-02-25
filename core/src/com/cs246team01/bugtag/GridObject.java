package com.cs246team01.bugtag;

/**
 * Created by Landon on 2/22/2017.
 */


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Class: Grid Object
 * The base class for all objects to appear on the grid
 */

abstract public class GridObject {

    //data
    protected GridPoint2 currentPosition = new GridPoint2();
    protected int priority;
    Texture objectTexture;

    //default constructor
    public GridObject() {
        currentPosition.set(0, 0);
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

    //method for moving the objects
    public void move(int direction) {

    }

    //abstract method for drawing
    abstract void draw();

}
