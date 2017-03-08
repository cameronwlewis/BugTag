package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

/**
 * Class:Button
 * This class will be used to display and implement all 8 buttons
 * for the game. It will have a int ID (1 - 4 for player 1, 5 - 8 for player 2)
 * that we will use to keep track of what button is pushed and how to handle input.
 */

public class Button extends GridObject {

    private int buttonId;
    private Rectangle clickArea;

    public Button(){
        //do not use this constructor
    }

    public Button (int ID, Texture image){

        buttonId = ID;
        objectTexture = image;

        //player 1 buttons
        int buttonx = Gdx.graphics.getWidth() - (Gdx.graphics.getHeight()/4);
        int button1y = 0;
        int button2y = Gdx.graphics.getHeight()/4;
        int button3y = Gdx.graphics.getHeight()/2;
        int button4y = (3*Gdx.graphics.getHeight())/4;
        //player 2 buttons
        int buttonx2 = 0;
        int button5y = (3*Gdx.graphics.getHeight())/4;
        int button6y = Gdx.graphics.getHeight()/2;
        int button7y = Gdx.graphics.getHeight()/4;
        int button8y = 0;

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
            case 5:
                currentPosition = new GridPoint2(buttonx2, button5y);
                break;
            case 6:
                currentPosition = new GridPoint2(buttonx2, button6y);
                break;
            case 7:
                currentPosition = new GridPoint2(buttonx2, button7y);
                break;
            case 8:
                currentPosition = new GridPoint2(buttonx2, button8y);
                break;
        }

        //now set the area to be clicked. first two parameters are position, then size
        clickArea = new Rectangle(currentPosition.x,currentPosition.y,
                objectTexture.getWidth(),objectTexture.getHeight());
    }

    //we do not use this method
    // it is here to satisfy the abstract class
    public void hide(){

    }

    public Rectangle getClickArea(){
        return clickArea;
    }

}
