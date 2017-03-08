package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

import static com.cs246team01.bugtag.GridObject.TAG;

/**
 * Created by Landon on 3/7/2017.
 */

public class ButtonProcessor implements InputProcessor{

    //these variables will be used for handling user input
    public static boolean moveLeft1;
    public static boolean moveRight1;
    public static boolean moveDown1;
    public static boolean moveUp1;
    public static boolean moveLeft2;
    public static boolean moveRight2;
    public static boolean moveDown2;
    public static boolean moveUp2;
    private int gameState = 0;

    //this holds all of our button objects
    ArrayList<Button> buttons;

    public ButtonProcessor(ArrayList<Button> buttons, int state){
        moveLeft1 = false;
        moveRight1 = false;
        moveDown1 = false;
        moveUp1 = false;
        moveLeft2 = false;
        moveRight2 = false;
        moveDown2 = false;
        moveUp2 = false;

        this.buttons = buttons;
        this.gameState = state;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(gameState == 0)
        {
            gameState = 1;
            Gdx.app.log(TAG, "Game started");
        }
        else if (gameState == 1){

            int buttonPressed1 = 0;
            int buttonPressed2 = 0;

            //check for player 1 pushed buttons
            for (int i = 0; i < 4; i++) {

                if (buttons.get(i).getClickArea().contains(screenX, screenY))
                    buttonPressed1 = i + 1;
            }

            //check for player 2 pushed buttons
            for (int i = 4; i < buttons.size(); i++) {

                if (buttons.get(i).getClickArea().contains(screenX, screenY))
                    buttonPressed2 = i + 1;
            }

            if(buttonPressed1 == 0 && buttonPressed2 == 0)
            {
                //If touched anywhere except buttons, the game is paused
                gameState = 2;
                Gdx.app.log(TAG, "Game paused");
            }

            //buttons are backwards from gridObjectHandler, don't know why yet... but it works
            switch (buttonPressed1) {
                case 4:
                    moveLeft1 = true;
                    Gdx.app.log(TAG, "Pressed button 1");
                    break;
                case 3:
                    moveRight1 = true;
                    Gdx.app.log(TAG, "Pressed button 2");
                    break;
                case 2:
                    moveDown1 = true;
                    Gdx.app.log(TAG, "Pressed button 3");
                    break;
                case 1:
                    moveUp1 = true;
                    Gdx.app.log(TAG, "Pressed button 4");
                    break;

            }

            switch (buttonPressed2) {
                case 8:
                    moveLeft2 = true;
                    Gdx.app.log(TAG, "Pressed button 5");
                    break;
                case 7:
                    moveRight2 = true;
                    Gdx.app.log(TAG, "Pressed button 6");
                    break;
                case 6:
                    moveDown2 = true;
                    Gdx.app.log(TAG, "Pressed button 7");
                    break;
                case 5:
                    moveUp2 = true;
                    Gdx.app.log(TAG, "Pressed button 8");
                    break;
            }
        } else if(gameState == 2)
        {
            //Once game is paused, touching anywhere resumes the game
            gameState = 1;
            Gdx.app.log(TAG, "Game resumed");
        }

        return true;
    }

    public int getGameState()
    {
        return gameState;
    }

    public void setGameState(int state)
    {
        gameState = state;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }



    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
