package com.cs246team01.bugtag;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

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

    public Bug(Texture bugImage){
        //This sets whether the bug will be the chaser randomly
        Random rand = new Random();
        isChaser = rand.nextBoolean();

        this.setTexture(bugImage);
        currentPosition = new GridPoint2(0,0);
    }



    //for drawing the bug on screen
    public void draw(){


    }
}
