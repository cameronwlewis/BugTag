package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;

/**
 * Created by Landon on 2/23/2017.
 */


public class Bug extends GridObject {

    private static final String TAG = "DebugTagger";

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
        //set bug in bottom left corner
        currentPosition = new GridPoint2(Gdx.graphics.getWidth()- bugImage.getWidth(),
                                         Gdx.graphics.getHeight()- bugImage.getHeight());
        //keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }



    //when the bug goes off of the screen
    public void hide(){

    }
}
