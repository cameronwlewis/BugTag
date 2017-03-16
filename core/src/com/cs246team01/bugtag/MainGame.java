package com.cs246team01.bugtag;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class MainGame extends Game{



    /**
    * gameState tracks the state of the game.
    * 0 is start screen, 1 is in gameplay, and 2 is pause, 3 is game over.
    * When various screens are used. This is important to keep track of
    * how to handle touch inputs
    */

    //stuff to check for winner
    private int bugOne_pos_x;
    private int bugOne_pos_y;
    private int bugTwo_pos_x;
    private int bugTwo_pos_y;
    private boolean isInRange_X;
    private boolean isInRange_Y;

    //Game
    private SpriteBatch batch;
    private GridObjectHandler bugGame;
    private ButtonProcessor buttonProcessor;
    private int winner = 0;
    private GameHandler game;

    //keeps track of current gameState
    //it starts off as GAME_NOT_STARTED
    static int gameState = 0;

    private boolean reset;


    //Test Preferences
    private int numMoves = 0;

    //Use this for tagging bugs
    private static final String TAG = "DebugTagger";
    private static final String WIN = "WinTag";

    @Override
    public void create () {
        Preferences numMovesPrefs = Gdx.app.getPreferences("MOVES");
        numMoves = numMovesPrefs.getInteger("moves", 0);

        //Debugging only - remove
        Gdx.app.log(TAG,"The number of moves are " + numMoves);

        batch = new SpriteBatch();

        bugGame = new GridObjectHandler();
        game = new GameHandler();
        reset = true;

        // gameState is initially set right here
        buttonProcessor = new ButtonProcessor(bugGame.getButtons(), gameState);
        Gdx.input.setInputProcessor(buttonProcessor);


    }

    @Override
    public void render () {
        super.render();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            gameState = buttonProcessor.getGameState();

        //reset the grid objects if it is game over
        if(gameState == 4 && !reset){
            bugGame = null;
            bugGame = new GridObjectHandler();
            reset = true;
        }

            batch.begin();

            //This is where we move our objects and set win variables
           if(gameState == 1) {
               bugGame.run();

               //stuff to check for winner
               bugOne_pos_x = bugGame.getBugOne().currentPosition.x;
               bugOne_pos_y = bugGame.getBugOne().currentPosition.y;
               bugTwo_pos_x = bugGame.getBugTwo().currentPosition.x;
               bugTwo_pos_y = bugGame.getBugTwo().currentPosition.y;
               isInRange_X = Math.abs(bugOne_pos_x - bugTwo_pos_x) <= 7;
               isInRange_Y = Math.abs(bugOne_pos_y - bugTwo_pos_y) <= 44;
           }

           if(gameState != 0) {
               //draw everything
               bugGame.draw(batch);
               //display timer
               game.displayTime(batch);
           }

            game.run();

            game.displayMessage(batch);

            batch.end();


            if (isInRange_X && isInRange_Y){
                Gdx.app.log(WIN, "Chaser touched evader! Game over!");
                isInRange_X = false;
                isInRange_Y = false;
                //bugGame.resetBugPositions();
                gameState = 3;
                //this allows us to reset one time
                reset = false;
            }

            buttonProcessor.setGameState(gameState);
        }

    @Override
    public void dispose () {
        game.dispose();
        batch.dispose();
    }

}
