package com.cs246team01.bugtag;

/**
 * Created by Landon on 2/22/2017.
 */


import com.badlogic.gdx.math.GridPoint2;

/**
 * Class: Grid Object
 * The base class for all objects to appear on the grid
 */

abstract public class GridObject {

    //data
    private GridPoint2 currentPosition = new GridPoint2();
    private int priority;

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

    //getters
    public GridPoint2 getPosition() { return currentPosition; }
    public int getPriority()   { return priority;        }

    //method for moving the objects
    public void move(int direction){


    }

    //abstract method for drawing
    abstract void draw();

}
