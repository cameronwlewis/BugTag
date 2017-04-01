package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import static com.cs246team01.bugtag.GridObject.TAG;

/**
 * Defines the areas for the user to click on and sends movement commands to
 * grid object handler.
 * <p>
 * The ButtonProcessor has a list of static booleans and integers that determine what state the
 * game is currently in and where on the screen the user has clicked. These variables are used
 * extensively within the {@link GridObjectHandler} class.</p>
 *
 * <p>Using Rectangle objects from the libgdx library, we are able to divide the screen up into
 * sections. Rectangle has a contain method that determines if the user's finger is touching
 * within the area of the rectangle. When a Rectangle contains the user's finger it sends a
 * movement number to the GridObjectHandler which determines bug movement.</p>
 *
 * If the game is not in play mode the processor also handles changes for the state of the game
 * (ex. checking if the startGame button is pressed).
 *
 * @author Landon, Likhith
 * @since 2017-03-08
 */
public class ButtonProcessor implements InputProcessor {

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
    //this is the main menu start button
    private Button startButton;

    //the constructor takes in an array of buttons passed in from GridObjectHandler and the current
    //game state
    ButtonProcessor(ArrayList<Button> buttons, int state) {
        //initialize states
        moveLeft1  = false;
        moveRight1 = false;
        moveDown1  = false;
        moveUp1    = false;
        moveLeft2  = false;
        moveRight2 = false;
        moveDown2  = false;
        moveUp2    = false;

        this.buttons = buttons;
        startButton = GameHandler.getStartButton();
        gameState = state;
    }

    /**
     * Handles user input when they touch the screen
     *
     * <p>When the user touches down on the screen. This method checks for where on the screen they
     * have pressed using the screenX and screenY variables. There are 5 game states the processor
     * is concerned with. They are as follows:</p><br/>
     *
     * <p>-GAME_NOT_STARTED: The main menu is being displayed.
     * -GAME_STARTED: The game is currently being played. touchDown checks what buttons are pressed.
     * -GAME_PAUSED: The game is currently paused.
     * -GAME_OVER: Either the timer has run out or the bugs have touched each other.
     * -GAME_WARM_UP: New round started. A timer counts for 3 seconds letting players get ready</p>
     *
     * <p>The array of buttons is looped through to determine which one has been pressed. When it
     * is found it sets its corresponding movement boolean to true which will be used to determine
     * bug movement in the {@link GridObjectHandler}.</p>
     *
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Variables for game state tracking
        int GAME_NOT_STARTED = 0;
        int GAME_STARTED = 1;
        int GAME_PAUSED = 2;
        int GAME_OVER = 3;
        int GAME_WARM_UP = 4;

        if (gameState == GAME_NOT_STARTED) {
            //Button press animation
            startButton.setTexture(new Texture("buttons/startgame_down.png"));
        }

        if (gameState == GAME_STARTED) {
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

    /**
     * Checks if the main start button has been pressed and released
     *
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        //Variables for game state tracking
        int GAME_NOT_STARTED = 0;
        int GAME_STARTED = 1;
        int GAME_PAUSED = 2;
        int GAME_OVER = 3;
        int GAME_WARM_UP = 4;

        if (gameState == GAME_NOT_STARTED) {
            //Reset start button texture
            startButton.setTexture(new Texture("buttons/startgame_up.png"));

            //Start game warm up stage
            if (startButton.getClickArea().contains(screenX, screenY)) {
                gameState = GAME_WARM_UP;
                Gdx.app.log(TAG, "Game in warm up");
            }
        }

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                moveUp1 = true;
                Gdx.app.log(TAG, "Pressed UP key");
                break;
            case Input.Keys.RIGHT:
                moveDown1 = true;
                Gdx.app.log(TAG, "Pressed DOWN key");
                break;
            case Input.Keys.DOWN:
                moveLeft1 = true;
                Gdx.app.log(TAG, "Pressed LEFT key");
                break;
            case Input.Keys.UP:
                moveRight1 = true;
                Gdx.app.log(TAG, "Pressed RIGHT key");
                break;
            case Input.Keys.D:
                moveUp2 = true;
                Gdx.app.log(TAG, "Pressed W key");
                break;
            case Input.Keys.A:
                moveDown2 = true;
                Gdx.app.log(TAG, "Pressed S key");
                break;
            case Input.Keys.W:
                moveLeft2 = true;
                Gdx.app.log(TAG, "Pressed A key");
                break;
            case Input.Keys.S:
                moveRight2 = true;
                Gdx.app.log(TAG, "Pressed D key");
                break;
            default:
                break;
        }

        return false;
    }

    //None of the methods below are used, they are included as methods from the InputProcessor
    //interface
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
