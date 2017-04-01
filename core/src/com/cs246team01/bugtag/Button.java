package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

/**
 * This class will be used to display and implement all 8 buttons
 * for the game. It will have a int ID (1 - 4 for player 1, 5 - 8 for player 2)
 * that we will use to keep track of what button is pushed and how to handle input.
 */

public class Button extends GridObject {

    private Rectangle clickArea;
    private int ID;

    public Button() {
        //Do not use this constructor
    }

    /**
     * This constructor takes the Textures and assigns
     *
     * @param ID    Integers to number each button
     * @param image Texture to use for buttons
     */
    Button(int ID, Texture image) {

        this.ID = ID;
        objectTexture = image;

        //Player 1 buttons
        int buttonx = Gdx.graphics.getWidth() - (Gdx.graphics.getHeight() / 4);
        int button1y = 0;
        int button2y = Gdx.graphics.getHeight() / 4;
        int button3y = Gdx.graphics.getHeight() / 2;
        int button4y = (3 * Gdx.graphics.getHeight()) / 4;
        //Player 2 buttons
        int buttonx2 = 0;
        int button5y = (3 * Gdx.graphics.getHeight()) / 4;
        int button6y = Gdx.graphics.getHeight() / 2;
        int button7y = Gdx.graphics.getHeight() / 4;
        int button8y = 0;
        //StartGame Button
        int startGameX = (Gdx.graphics.getWidth()/2)-(objectTexture.getWidth()/2);
        int startGameY = (Gdx.graphics.getHeight()/2)-(objectTexture.getHeight()/2);

        switch (ID) {
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
            case 9:
                currentPosition = new GridPoint2(startGameX, startGameY);
                break;
        }

        //Now set the area to be clicked. first two parameters are position, then size
        if (ID == 9)
        {
            clickArea = new Rectangle(currentPosition.x, currentPosition.y,
                    objectTexture.getWidth(), objectTexture.getHeight());
        }
        else {
            clickArea = new Rectangle(currentPosition.x, currentPosition.y,
                    Gdx.graphics.getHeight() / 4, Gdx.graphics.getHeight() / 4);
        }
    }

    /**
     * Getter to obtain the area which can be touched by the user
     *
     * @return the area able to be touched.
     */
    Rectangle getClickArea() {
        return clickArea;
    }

    int getButtonID() { return ID; }

    // We do not use these methods
    // They are here to satisfy the abstract class
    void hideTop() {
    }

    void hideLeft() {
    }

    void hideRight() {
    }

    void hideDown() {
    }

    boolean isHiding(){
        return false;
    }

}
