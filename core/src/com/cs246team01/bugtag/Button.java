package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Class:Button
 * This class will be used to display and implement all 8 buttons
 * for the game. It will have a int ID (1 - 4 for player 1, 5 - 8 for player 2)
 * that we will use to keep track of what button is pushed and how to handle input.
 */

public class Button extends GridObject {

    private int buttonId;


    public Button(){
        //do not use this constructor
    }

    public Button (int ID, Texture image){

        buttonId = ID;
        objectTexture = image;

        int buttonx = Gdx.graphics.getWidth() - (Gdx.graphics.getHeight()/4);
        int button1y = 0;
        int button2y = Gdx.graphics.getHeight()/4;
        int button3y = Gdx.graphics.getHeight()/2;
        int button4y = (3*Gdx.graphics.getHeight())/4;

        switch(buttonId){
            case 1:
                currentPosition = new GridPoint2(buttonx, button1y);
                break;
            case 2:
                currentPosition = new GridPoint2(buttonx, button2y);
                 break;
            case 3:
                currentPosition = new GridPoint2(buttonx, button3y);
                 break;
            case 4:
                currentPosition = new GridPoint2(buttonx, button4y);
                break;
        }

    }

    //we do not use this method
    // it is here to satisfy the abstract class
    public void hide(){

    }
}
