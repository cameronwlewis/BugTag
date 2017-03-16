package com.cs246team01.bugtag;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.GridPoint2;

import static com.cs246team01.bugtag.GridObject.TAG;


class MainGame extends Game{

    //Start screen - can possibly remove this. But buttons show up
    private SpriteBatch welcome;

    /**
    * gameState tracks the state of the game.
    * 0 is start screen, 1 is in gameplay, and 2 is pause, 3 is game over.
    * When various screens are used. This is important to keep track of
    * how to handle touch inputs
    */
    private static int GAME_NOT_STARTED = 0;
    private static int GAME_STARTED = 1;
    private static int GAME_PAUSED = 2;
    private static int GAME_OVER = 3;
    private static int GAME_WARM_UP = 4;
    private int gameState = GAME_NOT_STARTED;

    //Game
    private SpriteBatch batch;
    private GridObjectHandler bugGame;
    private ButtonProcessor buttonProcessor;
    private int winner = 0;

    //Timer
    private GameTime timer;
    private float totalTime;

    //Font
    private FreeTypeFontGenerator fontFT;
    private FreeTypeFontParameter parameter;
    private BitmapFont font;

    // Font for timer
    private FreeTypeFontGenerator digitalFontFT;
    private FreeTypeFontParameter digitalParameter;
    private BitmapFont digitalFont;

    //Test Preferences
    private int numMoves = 0;

    //Use this for tagging bugs
    private static final String TAG = "DebugTagger";

    @Override
    public void create () {
        Preferences numMovesPrefs = Gdx.app.getPreferences("MOVES");
        numMoves = numMovesPrefs.getInteger("moves", 0);

        //

        //Debugging only - remove
        Gdx.app.log(TAG,"The number of moves are " + numMoves);

        //Since this is a constant (or is it?)
        //we can just assign a hardcoded value
        totalTime = 63;
        timer = new GameTime(totalTime);

        //Font is comic sans and font size is 50 colored red
        fontFT = new FreeTypeFontGenerator(Gdx.files.internal("fonts/chewy.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 50;
        parameter.borderColor = Color.BLACK;
        parameter.borderStraight = true;
        parameter.borderWidth = 2f;
        font = fontFT.generateFont(parameter);
        font.setColor(Color.RED);

        //Timer font is called digital dream
        digitalFontFT = new FreeTypeFontGenerator(Gdx.files.internal("fonts/digital-dream-skew-narrow.ttf"));
        digitalParameter = new FreeTypeFontParameter();
        digitalParameter.size = 40;
        digitalParameter.borderColor = Color.BLACK;
        digitalParameter.borderStraight = true;
        digitalParameter.borderWidth = 1f;
        digitalFont = digitalFontFT.generateFont(digitalParameter);
        digitalFont.setColor(Color.RED);

        batch = new SpriteBatch();
        welcome = new SpriteBatch();

        bugGame = new GridObjectHandler();

        // gameState is initially set right here
        buttonProcessor = new ButtonProcessor(bugGame.getButtons(), gameState);
        Gdx.input.setInputProcessor(buttonProcessor);
    }

    @Override
    public void render () {
        super.render();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // gameState is retrieved here
        gameState = buttonProcessor.getGameState();

        if(gameState == GAME_NOT_STARTED)
        {
            //TODO Start button? And background graphics
            welcome.begin();

            displayMessage();

            welcome.end();
        }else if(gameState == GAME_WARM_UP)
        {
            //If game is restarted, the timer will be less
            // than 0 and must be set to 63 again.
            if(timer.getTimeRemaining() <= 0){
                timer.setTimeRemaining(63);
            }

            batch.begin();

            bugGame.draw(batch);

            displayTime();

            displayMessage();

            timer.run();
            if(timer.getTimeRemaining() < 60){
                buttonProcessor.setGameState(GAME_STARTED);
            }

            batch.end();
        }
        else if(gameState == GAME_STARTED)
        {
            batch.begin();

            //This is where we run our game
            bugGame.run();

            //stuff to check for winner
            int bugOne_pos_x = bugGame.getBugOne().currentPosition.x;
            int bugOne_pos_y = bugGame.getBugOne().currentPosition.y;
            int bugTwo_pos_x = bugGame.getBugTwo().currentPosition.x;
            int bugTwo_pos_y = bugGame.getBugTwo().currentPosition.y;
            boolean isInRange_X = Math.abs(bugOne_pos_x - bugTwo_pos_x) <= 7;
            boolean isInRange_Y = Math.abs(bugOne_pos_y - bugTwo_pos_y) <= 44;

            bugGame.draw(batch);

            //moved timer code into this method
            displayTime();

            batch.end();

            //update timer value
            timer.run();

            if(!(timer.getTimeRemaining() > 0)){
                buttonProcessor.setGameState(GAME_OVER);
            }

            if (isInRange_X && isInRange_Y){
                Gdx.app.log(TAG, "Chaser touched evader! Game over!");
                isInRange_X = false;
                isInRange_Y = false;
                bugGame.resetBugPositions();
                buttonProcessor.setGameState(GAME_OVER);
            }

            //Gdx.app.log(TAG, "Time Remaining is " + timer.getTimeRemaining());
        }
        else if (gameState == GAME_PAUSED)
        {
            batch.begin();

            bugGame.draw(batch);

            displayTime();

            displayMessage();

            batch.end();
        }
        else if (gameState == GAME_OVER)
        {
            batch.begin();

            bugGame.resetBugPositions();

            bugGame.draw(batch);

            displayTime();

            displayMessage();

            bugGame.resetBugPositions();

            //Set winner variable too. The first frame
            // won't have the result but that should be fine

            //Do something with SharedPrefs,
            //like setting the high score, etc.

            batch.end();
        }

    }

    @Override
    public void dispose () {
        welcome.dispose();
        batch.dispose();
        font.dispose();
        digitalFont.dispose();
    }

    private void displayTime(){
        //Display timer
        if(timer.getTimeRemaining() >= 60) {
            digitalFont.draw(batch, "0:60",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
                    Gdx.graphics.getWidth() / 2);
        } else if(timer.getTimeRemaining() >= 10) {
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

    private void displayMessage(){
        if(gameState == GAME_NOT_STARTED) {
            font.draw(welcome, "BUGTAG!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5) ,
                    Gdx.graphics.getWidth() / 2 );
            font.draw(welcome, "Press anywhere to start!",
                    Gdx.graphics.getHeight() / 2 ,
                    Gdx.graphics.getWidth() / 4 );

        } else if(gameState == GAME_WARM_UP)
        {
            font.draw(batch, "Game starts in",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5) ,
                    Gdx.graphics.getWidth() / 3 );
            font.draw(batch, (timer.getTimeRemaining() - 60) + "...",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12) ,
                    Gdx.graphics.getWidth() / 4 );
        }
        else if (gameState == GAME_PAUSED) {
            font.draw(batch, "GAME PAUSED!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5) ,
                    Gdx.graphics.getWidth() / 3 );
            font.draw(batch, "Press anywhere to resume!",
                    Gdx.graphics.getHeight() / 3 ,
                    Gdx.graphics.getWidth() / 4 );
        } else if (gameState == GAME_OVER) {
            font.draw(batch, "GAME OVER!",
                    Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 5) ,
                    Gdx.graphics.getWidth() / 4 );
            if(winner == 1) {
                font.draw(batch, "Player 1 won the game!", //todo maybe make this say chaser?
                        Gdx.graphics.getHeight() / 2 ,
                        Gdx.graphics.getWidth() / 5 );
            } else if (winner == 2)
            {
                font.draw(batch, "Player 2 won the game!", //todo and make this say evader?
                        Gdx.graphics.getHeight() / 2 ,
                        Gdx.graphics.getWidth() / 5 );
            }
            font.draw(batch, "Press anywhere to restart!",
                    Gdx.graphics.getHeight() / 3 ,
                    Gdx.graphics.getWidth() / 6 );
        }
    }

}

