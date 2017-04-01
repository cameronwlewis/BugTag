package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Random;

/**
 * Tracks all variables that are not grid objects but still essential to game play.
 */

public class GameHandler{

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
    private BitmapFont font;
    private BitmapFont digitalFont;

    private String notifyChaser;

    int winPoint;

    // storage for high score
    Preferences HighScore;

    private String winnerMessage;

    //Timer
    private GameTime timer;
    private boolean timerReset;

    //Main Menu
    private static Button startGame;
    private int startButtonWidth;
    private int startButtonHeight;

    //game-state tag
    private static final String STATE = "GameState";

    /**
     * non-default constructor to pass in both Bug objects to check who is the chaser at any time
     */

    //BOGUS COMMENTS HERE
    // non-default constructor
    GameHandler() {
        // points given to player for a win
        winPoint = 1;

        // retrieve saved high score
        HighScore = Gdx.app.getPreferences("HighScore");

        welcome = new SpriteBatch();

        startGame = new Button(9, new Texture("buttons/startgame_up.png"));
        startButtonWidth = startGame.getTexture().getWidth();
        startButtonHeight = startGame.getTexture().getHeight();

        //Since this is a constant (or is it?)
        //we can just assign a hardcoded value
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

        Random rand = new Random();
        boolean randBoolean = rand.nextBoolean();
        GridObjectHandler.bug1_yellow.setChaser(randBoolean);
        GridObjectHandler.bug2_red.setChaser(!randBoolean);


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
     * flip-flops the chaser each round and sets the greeting notifying who is the chaser
     */
    void setChaserStatus(Bug bug1_yellow, Bug bug2_red, boolean yellowIsChaser){

        if(yellowIsChaser){
            bug1_yellow.setChaser(false);
            bug2_red.setChaser(true);
        }
        else{
            bug1_yellow.setChaser(true);
            bug2_red.setChaser(false);
        }


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

        if (_gameState == 3) {
            if (winnerMessage.contains("Red")) {
                bug2_red.saveNewScore(winPoint);
                if(HighScore.getInteger("HighScore") < bug2_red.getPlayerScore()){
                    HighScore.putInteger("HighScore", bug2_red.getPlayerScore());
                    HighScore.flush();
                }
            }
            else {
                bug1_yellow.saveNewScore(winPoint);
                if(HighScore.getInteger("HighScore") < bug1_yellow.getPlayerScore()){
                    HighScore.putInteger("HighScore", bug1_yellow.getPlayerScore());
                    HighScore.flush();
                }
            }
        }
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
        String time;
        GlyphLayout tgl = new GlyphLayout();
        float w;

        if (timer.getTimeRemaining() >= 60) {
            time = "0:60";

            tgl .setText(digitalFont, time);
            w = tgl.width;

            digitalFont.draw(batch, time,
                    ((Gdx.graphics.getWidth() - w) / 2),
                    Gdx.graphics.getWidth() / 2);
        } else if (timer.getTimeRemaining() >= 10) {
            time = "0:" + timer.getTimeRemaining();
            tgl .setText(digitalFont, time);
            w = tgl.width;

            digitalFont.draw(batch, time,
                    ((Gdx.graphics.getWidth() - w) / 2),
                    Gdx.graphics.getWidth() / 2);
        } else if (timer.getTimeRemaining() < 10 && timer.getTimeRemaining() > 0) {
            time = "0:0" + timer.getTimeRemaining();
            tgl .setText(digitalFont, time);
            w = tgl.width;

            digitalFont.draw(batch, time,
                    ((Gdx.graphics.getWidth() - w) / 2),
                    Gdx.graphics.getWidth() / 2);
        } else {

            time = "0:00";
            tgl .setText(digitalFont, time);
            w = tgl.width;

            digitalFont.draw(batch, time,
                    ((Gdx.graphics.getWidth() - w) / 2),
                    Gdx.graphics.getWidth() / 2);
            font.draw(batch, "TIME UP",
                    ((Gdx.graphics.getWidth() - w) / 2),
                    Gdx.graphics.getWidth() / 4);
        }
    }


    /**
     * displays main start menu before gameplay begins
     */
    void displayMenu() {
        if (gameState == GAME_NOT_STARTED) {

            GlyphLayout gl = new GlyphLayout();
            float w;
            String bugtagText = "BUGTAG!";
            gl.setText(font, bugtagText);
            w = gl.width;

            welcome.begin();
            font.draw(welcome, bugtagText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 2);
            welcome.draw(startGame.getTexture(), startGame.getX(), startGame.getY(), startButtonWidth, startButtonHeight);
            welcome.end();
        }
    }

    /**
     * displays all other text on screen during gameplay
     * @param batch
     */
    void displayMessage(SpriteBatch batch, Bug bug1_yellow, Bug bug2_red) {

        GlyphLayout gl = new GlyphLayout();
        float w;

        if (gameState == GAME_NOT_STARTED) {
            displayMenu();
        }

        if (gameState == GAME_WARM_UP) {

            String chaserText=  notifyChaser;
            String warmUpText = "Game starts in";
            String timerText = (timer.getTimeRemaining() - 60) + "...";

            gl.setText(font, chaserText);
            w = gl.width;

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
            String gamePausedText = "GAME PAUSED!";
            String pressResumeText = "Press anywhere to resume!";

            gl.setText(font, gamePausedText);
            w = gl.width;

            font.draw(batch, gamePausedText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 3);

            gl.setText(font, pressResumeText);
            w = gl.width;


            font.draw(batch, pressResumeText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 4);
        } else if (gameState == GAME_OVER) {

            String gameOverText = "\t      GAME OVER!\n      " + winnerMessage + "\n  Yellow Bug score: " + bug1_yellow.getPlayerScore() +
                    "\n      Red Bug Score: " + bug2_red.getPlayerScore();
            String restartText = "Press anywhere to restart!";

            gl.setText(font, gameOverText);
            w = gl.width;

            font.draw(batch, gameOverText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 2);


            /*
            String gameOverText = "GAME OVER!";
            String yellowBugScoreText = "Yellow Bug score: " + bug1_yellow.getPlayerScore();
            String redBugScoreText = "Red Bug score: " + bug2_red.getPlayerScore();
            String restartText = "Press anywhere to restart!";

            gl.setText(font, gameOverText);
            w = gl.width;

            font.draw(batch, gameOverText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 2);

            gl.setText(font, winnerMessage);
            w = gl.width;

            font.draw(batch, winnerMessage,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 3);

            gl.setText(font, yellowBugScoreText);
            w = gl.width;

            font.draw(batch, yellowBugScoreText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 4);

            gl.setText(font, redBugScoreText);
            w = gl.width;

            font.draw(batch, redBugScoreText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 5);
            */

            gl.setText(font, restartText);
            w = gl.width;

            font.draw(batch, restartText,
                    (Gdx.graphics.getWidth() - w) / 2,
                    Gdx.graphics.getWidth() / 7);
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