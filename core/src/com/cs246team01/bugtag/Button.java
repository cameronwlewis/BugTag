package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

/**
 * This class will be used to display and implement all 8 buttons
 * for the game. It will have a int ID (1 - 4 for player 1, 5 - 8 for player 2)
 * that we will use to keep track of what button is pushed and how to handle input.
 * Created by Landon on 2/28/2017.
 */

public class Button extends GridObject {

    private int buttonId;


    public Button(){
        //do not use this constructor
    }

    public Button (int ID, Texture image){

        buttonId = ID;
        objectTexture = image;

        switch(buttonId){
            case 1:
                currentPosition = new GridPoint2(Gdx.graphics.getWidth()-objectTexture.getWidth(), 0 );
                break;
            case 2:
                currentPosition = new GridPoint2((Gdx.graphics.getWidth()-objectTexture.getWidth()),
                        objectTexture.getHeight());
                         break;
            case 3:
                currentPosition = new GridPoint2((Gdx.graphics.getWidth()-objectTexture.getWidth()),
                        objectTexture.getHeight() * 2);
                        break;
            case 4:
                currentPosition = new GridPoint2(Gdx.graphics.getWidth()-objectTexture.getWidth(),
                        objectTexture.getHeight()* 3);
                break;

        }

    }


    //we do not use this method
    // it is here to satisfy the abstract class
    public void hide(){

    }
}
