package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import sun.applet.Main;

import static com.cs246team01.bugtag.GridObject.TAG;

/**
 * Tracks all variables that are not grid objects but still essential to game play.
 */

public class GameHandler {

    //game state variables
    private static final int GAME_NOT_STARTED = 0;
    private static final int GAME_STARTED = 1;
    private static final int GAME_PAUSED = 2;
    private static final int GAME_OVER = 3;
    private static final int GAME_WARM_UP = 4;

    //this variable is not final because it changes constantly
    private static int gameState = GAME_NOT_STARTED;

    //Start screen - can possibly remove this. But buttons show up
    private SpriteBatch welcome;
    //for all game text besides timer
    private BitmapFont font;
    //timer text
    private BitmapFont digitalFont;
    //tells players which bug is chasing
    private String notifyChaser;
    //displays winner for round
    private String winnerMessage;

    private GameScore score;

    //Timer
    private GameTime timer;
    private boolean timerReset;


    //Main Menu
    private static Button startGame;
    private int startButtonWidth;
    private int startButtonHeight;

    //game-state log tag
    private static final String STATE = "GameState";

    /**
     * default constructor initializes fonts and start button
     */

    GameHandler() {

        welcome = new SpriteBatch();

        startGame = new Button(9, new Texture("buttons/startgame.png"));
        startButtonWidth = startGame.getTexture().getWidth();
        startButtonHeight = startGame.getTexture().getHeight();


        //timer starts at 63 seconds to include warm up mode
        float totalTime = 63;
        timer = new GameTime(totalTime);
        timerReset = true;

        //Font is chewy and font size is 50 colored red
        FreeTypeFontGenerator fontFT = new FreeTypeFontGenerator(Gdx.files.internal("fonts/chewy.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.borderColor = Color.BLACK;
        parameter.borderStraight = true;
        parameter.borderWidth = 2f;
        font = fontFT.generateFont(parameter);
        font.setColor(88/255f,186/255f,232/255f,1f);

        //Timer font is called digital dream
        FreeTypeFontGenerator digitalFontFT = new FreeTypeFontGenerator(Gdx.files.internal("fonts/digital-dream-skew-narrow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter digitalParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        digitalParameter.size = 40;
        digitalParameter.borderColor = Color.BLACK;
        digitalParameter.borderStraight = true;
        digitalParameter.borderWidth = 3f;
        digitalFont = digitalFontFT.generateFont(digitalParameter);
        digitalFont.setColor(1f,1f,1f,1f);

    }

    /**
     * determines which method is being called based on the current game state
     */
    void run() {

        // gameState is retrieved here
        gameState = MainGame.gameState;

        switch (gameState) {
            case GAME_WARM_UP:
                warmUpGame();
                break;
            case GAME_STARTED:
                update();
                break;
            case GAME_OVER:
                timerReset = false;
                break;
        }
    }

    /**
     * set the greeting notifying who is the chaser
     */

    void setChaserStatus(Bug bug1_yellow, Bug bug2_red){

        if (bug1_yellow.isChaser())
            notifyChaser = "Yellow bug is the chaser!";
        else if (bug2_red.isChaser())
            notifyChaser = "Red bug is the chaser!";
    }

    void setWinnerMessage(String message){winnerMessage = message;}

    /**
     * returns time remaining on the timer
     * @return
     */
    int getGameTime(){return timer.getTimeRemaining();}

    /**
     * resets the timer to 63 and counts down to the start of gameplay
     */
    private void warmUpGame() {

        //If game is restarted, the timer is reset to 63
        if (!timerReset) {
            timer.setTimeRemaining(63);
            timerReset = true;
        }

        //tick down the timer before game begins
        timer.run();
        if (timer.getTimeRemaining() < 60) {
            MainGame.gameState = GAME_STARTED;
        }

    }


    void calculateScore(int _gameState, Bug bug1_yellow, Bug bug2_red){
        if (gameState == 3)
            score.awardWinPoints(winnerMessage, bug1_yellow, bug2_red);
    }

    /**
     * runs the timer and checks if it runs out
     */
    private void update() {

        //update timer value
        timer.run();

        if (timer.getTimeRemaining() < 0) {
            MainGame.gameState = GAME_OVER;
        }

    }

    /**
     * draws timer to screen
     * @param batch
     */
    void displayTime(SpriteBatch batch) {
        //Display timer
        if (timer.getTimeRemaining() >= 60) {
            digitalFont.draw(batch, "0:60",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
                    Gdx.graphics.getWidth() / 2);
        } else if (timer.getTimeRemaining() >= 10) {
            digitalFont.draw(batch, "0:" + timer.getTimeRemaining(),
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
                    Gdx.graphics.getWidth() / 2);
        } else if (timer.getTimeRemaining() < 10 && timer.getTimeRemaining() > 0) {
            digitalFont.draw(batch, "0:0" + timer.getTimeRemaining(),
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
                    Gdx.graphics.getWidth() / 2);
        } else {
            digitalFont.draw(batch, "0:00",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
                    Gdx.graphics.getWidth() / 2);
            font.draw(batch, "TIME UP",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 10),
                    Gdx.graphics.getWidth() / 3);
        }
    }

    /**
     * displays main start menu before gameplay begins
     */
    void displayMenu() {
        if (gameState == GAME_NOT_STARTED) {
            welcome.begin();
            font.draw(welcome, "BUGTAG!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 8),
                    Gdx.graphics.getWidth() / 2);
            welcome.draw(startGame.getTexture(), startGame.getX(), startGame.getY(), startButtonWidth, startButtonHeight);
            welcome.end();
        }
    }

    /**
     * displays all other text on screen during gameplay
     * @param batch
     */
    void displayMessage(SpriteBatch batch) {

        GlyphLayout gl = new GlyphLayout();

        if (gameState == GAME_NOT_STARTED) {
            displayMenu();
        }
//            welcome.begin();
//            font.draw(welcome, "BUGTAG!",
//                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
//                    Gdx.graphics.getWidth() / 2);
//            font.draw(welcome, "Press anywhere to start!",
//                    Gdx.graphics.getHeight() / 2,
//                    Gdx.graphics.getWidth() / 4);
//
//            welcome.end();
//        } else
//
        if (gameState == GAME_WARM_UP) {

            String chaserText=  notifyChaser;
            String warmUpText = "Game starts in";
            String timerText = (timer.getTimeRemaining() - 60) + "...";

            gl.setText(font, chaserText);
            float w = gl.width;

            font.draw(batch,gl,
                   ((Gdx.graphics.getWidth() - w) / 2),
                    Gdx.graphics.getWidth() / 3);
            gl.setText(font,warmUpText);
            w = gl.width;

            font.draw(batch, gl,
                    ((Gdx.graphics.getWidth()- w) / 2),
                    Gdx.graphics.getWidth() / 4);

            gl.setText(font,timerText);
            w = gl.width;

            font.draw(batch, gl,
                    ((Gdx.graphics.getWidth()- w) / 2),
                    Gdx.graphics.getWidth() / 5);


        } else if (gameState == GAME_PAUSED) {
            font.draw(batch, "GAME PAUSED!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 3);
            font.draw(batch, "Press anywhere to resume!",
                    Gdx.graphics.getHeight() / 3,
                    Gdx.graphics.getWidth() / 4);
        } else if (gameState == GAME_OVER) {
            font.draw(batch, winnerMessage + "\n     GAME OVER!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 3);
            //font.draw(batch, "GAME OVER!",
            //        Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
            //        Gdx.graphics.getWidth() / 4);
            font.draw(batch, "    Press anywhere to restart!",
                    Gdx.graphics.getHeight() / 3,
                    Gdx.graphics.getWidth() / 6);
        }
    }


    public static Button getStartButton() {
        return startGame;
    }

    void dispose() {
        font.dispose();
        digitalFont.dispose();
        welcome.dispose();
    }
}
