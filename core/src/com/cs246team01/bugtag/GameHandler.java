package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Tracks all variables that are not grid objects but still essential to game play.
 */

class GameHandler {

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

    private String winnerMessage;

    //Timer
    private GameTime timer;
    private boolean timerReset;

    // storage for player scores
    Preferences MyScores;

    // storage for high score
    Preferences HighScore;

    //Main Menu
    private static Button startGame;
    private int startButtonWidth;
    private int startButtonHeight;

    //game-state tag
    private static final String STATE = "GameState";
    //BOGUS COMMENTS HERE
    // non-default constructor to pass in both Bug objects to check who is the chaser at any time
    GameHandler(Bug bug1_yellow, Bug bug2_red) {
        // initialize player scores preferences files
        MyScores = Gdx.app.getPreferences("MyScores");
        HighScore = Gdx.app.getPreferences("HighScore");

        // retrieve scores
        bug1_yellow.setPlayerScore(MyScores.getInteger("YellowScores"));
        bug2_red.setPlayerScore(MyScores.getInteger("RedScores"));

        winPoint = 1;

        welcome = new SpriteBatch();

        startGame = new Button(9, new Texture("buttons/startgame.png"));
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

    }

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
    // set the greeting notifying who is the chaser
    void setChaserStatus(Bug bug1_yellow, Bug bug2_red){

        if (bug1_yellow.isChaser())
            notifyChaser = "Yellow bug is the chaser!";
        else if (bug2_red.isChaser())
            notifyChaser = "Red bug is the chaser!";
    }

    void setWinnerMessage(String message){winnerMessage = message;}

    int getGameTime(){return timer.getTimeRemaining();}

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
                bug2_red.setPlayerScore(winPoint);
                MyScores.putInteger("RedScores", bug2_red.getPlayerScore());
                MyScores.flush();
                if(HighScore.getInteger("HighScore") < bug2_red.getPlayerScore()){
                    HighScore.putInteger("HighScore", bug2_red.getPlayerScore());
                    HighScore.flush();
                }

            }
            else {
                bug1_yellow.setPlayerScore(winPoint);
                MyScores.putInteger("YellowScores", bug1_yellow.getPlayerScore());
                MyScores.flush();
                if(HighScore.getInteger("HighScore") < bug1_yellow.getPlayerScore()){
                    HighScore.putInteger("HighScore", bug1_yellow.getPlayerScore());
                    HighScore.flush();
                }
            }
        }
    }

    private void update() {

        //update timer value
        timer.run();

        if (timer.getTimeRemaining() < 0) {
            MainGame.gameState = GAME_OVER;
        }

    }

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

    void displayMessage(SpriteBatch batch, Bug bug1_yellow, Bug bug2_red) {

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
            font.draw(batch, "GAME OVER!\n" + winnerMessage + "\n\nYellow Bug score: " + bug1_yellow.getPlayerScore() +
                            "\nRed Bug Score: " + bug2_red.getPlayerScore(),
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 3);
            //font.draw(batch, "GAME OVER!",
            //        Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
            //        Gdx.graphics.getWidth() / 4);
            font.draw(batch, "\nPress anywhere to restart!",
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
