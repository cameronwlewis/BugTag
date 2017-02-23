package com.cs246team01.bugtag;

import java.util.List;

/**
 * Created by Landon on 2/23/2017.
 */

public class GridObjectHandler {

    //this holds any object used in game
    private List<GridObject> gridObjects;

    //this will be initialized during the create method
    //of the main game
    public GridObjectHandler(){

    //populate grid object list here
    }

    //this is the only method we will call in the render method
    public void run(){
        update();
        draw();
    }

    //this method checks if we should stop the game
    //it returns a 1 if the chaser wins, 2 if the timer runs out;
    // -1 if game is still going
    public int checkWin(){
        return 0;
    }

    private void update(){
        //movement code here...

        //then draw everything
        draw();
    }

    //keeping it modularized
    private void draw(){


    }
}
