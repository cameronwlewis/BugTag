package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import sun.applet.Main;

import static com.cs246team01.bugtag.GridObject.TAG;

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

    //Timer
    private GameTime timer;
    private boolean timerReset;

    //game-state tag
    private static final String STATE = "GameState";


    GameHandler() {

        welcome = new SpriteBatch();

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
        font.setColor(Color.RED);

        //Timer font is called digital dream
        FreeTypeFontGenerator digitalFontFT = new FreeTypeFontGenerator(Gdx.files.internal("fonts/digital-dream-skew-narrow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter digitalParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        digitalParameter.size = 40;
        digitalParameter.borderColor = Color.BLACK;
        digitalParameter.borderStraight = true;
        digitalParameter.borderWidth = 1f;
        digitalFont = digitalFontFT.generateFont(digitalParameter);
        digitalFont.setColor(Color.RED);
    }

    void run() {

        // gameState is retrieved here
        gameState = MainGame.gameState;
        //Gdx.app.log(STATE, "Game state: " + this.gameState); todo: my debug console was going nuts with this logging and I couldn't see anything else, so I had to comment it out. Feel free to un-comment it -Cameron

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

    private void warmUpGame() {

        //If game is restarted, the timer is reset to 63
        if (!timerReset) {
            timer.setTimeRemaining(63);
            timerReset = true;
        }

        //tick down the timer
        timer.run();
        if (timer.getTimeRemaining() < 60) {
            MainGame.gameState = GAME_STARTED;
        }

    }

    private void update() {

        //update timer value
        timer.run();

        if (!(timer.getTimeRemaining() > 0)) {
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

    void displayMessage(SpriteBatch batch) {
        if (gameState == GAME_NOT_STARTED) {
            welcome.begin();
            font.draw(welcome, "BUGTAG!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 2);
            font.draw(welcome, "Press anywhere to start!",
                    Gdx.graphics.getHeight() / 2,
                    Gdx.graphics.getWidth() / 4);

            welcome.end();
        } else if (gameState == GAME_WARM_UP) {
            font.draw(batch, "Game starts in",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 3);
            font.draw(batch, (timer.getTimeRemaining() - 60) + "...",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
                    Gdx.graphics.getWidth() / 4);
        } else if (gameState == GAME_PAUSED) {
            font.draw(batch, "GAME PAUSED!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 3);
            font.draw(batch, "Press anywhere to resume!",
                    Gdx.graphics.getHeight() / 3,
                    Gdx.graphics.getWidth() / 4);
        } else if (gameState == GAME_OVER) {
            font.draw(batch, "GAME OVER!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5),
                    Gdx.graphics.getWidth() / 4);
           /* if(winner == 1) {
                font.draw(batch, "Player 1 won the game!", //todo maybe make this say chaser?
                        Gdx.graphics.getHeight() / 2 ,
                        Gdx.graphics.getWidth() / 5 );
            } else if (winner == 2)
            {
                font.draw(batch, "Player 2 won the game!", //todo and make this say evader?
                        Gdx.graphics.getHeight() / 2 ,
                        Gdx.graphics.getWidth() / 5 );
            }*/
            font.draw(batch, "Press anywhere to restart!",
                    Gdx.graphics.getHeight() / 3,
                    Gdx.graphics.getWidth() / 6);
        }
    }

    void dispose() {
        font.dispose();
        digitalFont.dispose();
        welcome.dispose();
    }
}
