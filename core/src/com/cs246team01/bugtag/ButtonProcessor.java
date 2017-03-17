package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

import static com.cs246team01.bugtag.GridObject.TAG;

/**
 * Defines the areas for the user to click on and sends movement commands to
 * grid object handler.
 * <p>
 * The ButtonProcessor has a list of static booleans and integers that determine what state the
 * game is currently in and where on the screen the user has clicked. These variables are used
 * extensively within the {@link GridObjectHandler} class.
 *
 * @author Landon, Likhith
 * @since 2017-03-08
 */
class ButtonProcessor implements InputProcessor {

    //These variables will be used for handling user input
    static boolean moveLeft1;
    static boolean moveRight1;
    static boolean moveDown1;
    static boolean moveUp1;
    static boolean moveLeft2;
    static boolean moveRight2;
    static boolean moveDown2;
    static boolean moveUp2;
    private static int gameState = 0;

    //This holds all of our button objects
    private ArrayList<Button> buttons;

    ButtonProcessor(ArrayList<Button> buttons, int state) {
        moveLeft1 = false;
        moveRight1 = false;
        moveDown1 = false;
        moveUp1 = false;
        moveLeft2 = false;
        moveRight2 = false;
        moveDown2 = false;
        moveUp2 = false;

        this.buttons = buttons;
        gameState = state;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Variables for game state tracking
        int GAME_NOT_STARTED = 0;
        int GAME_STARTED = 1;
        int GAME_PAUSED = 2;
        int GAME_OVER = 3;
        int GAME_WARM_UP = 4;
        if (gameState == GAME_NOT_STARTED) {
            //Start game warm up stage
            gameState = GAME_WARM_UP;
            Gdx.app.log(TAG, "Game in warm up");
        } else if (gameState == GAME_STARTED) {
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

            //If touched anywhere except buttons, the game is paused
            if (buttonPressed1 == 0 && buttonPressed2 == 0) {
                gameState = GAME_PAUSED;
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
        } else if (gameState == GAME_PAUSED) {
            //Once game is paused, touching anywhere resumes the game
            gameState = GAME_STARTED;
            Gdx.app.log(TAG, "Game resumed");
        } else if (gameState == GAME_OVER) {
            //If game is over, touching the screen would restart the game
            gameState = GAME_WARM_UP;
            Gdx.app.log(TAG, "Game restarted");
        }

        return true;
    }

    /**
     * Getter for the game state.
     *
     * @return returns the gameState variable.
     */
    static int getGameState() {
        return gameState;
    }

    void setGameState(int state) {
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
