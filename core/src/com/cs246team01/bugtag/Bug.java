package com.cs246team01.bugtag;

import java.util.Random;

/**
 * Created by Landon on 2/23/2017.
 */

public class Bug extends GridObject {

    private boolean isChaser;

    public Bug(){
        //This sets whether the bug will be the chaser randomly
        Random rand = new Random();
        isChaser = rand.nextBoolean();
    }

    //for drawing the bug on screen
    public void draw(){


    }
}
