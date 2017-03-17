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
    int bugOne_pos_x;
    int bugOne_pos_y;
    int bugTwo_pos_x;
    int bugTwo_pos_y;
    boolean isInRange_X;
    boolean isInRange_Y;

    //Game
    private SpriteBatch batch;
    private GridObjectHandler bugGame;
    private ButtonProcessor _buttonProcessor;
    private int winner = 0;
    private GameHandler game;
    private GameWin winStatus;

    //keeps track of current gameState
    //it starts off as GAME_NOT_STARTED
    public static int gameState = 0;

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

        winStatus = new GameWin();

        bugGame = new GridObjectHandler();
        game = new GameHandler();
        reset = false;

        // gameState is initially set right here
        _buttonProcessor = new ButtonProcessor(bugGame.getButtons(), gameState);
        Gdx.input.setInputProcessor(_buttonProcessor);


    }

    @Override
    public void render () {
        super.render();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            gameState = _buttonProcessor.getGameState();

        //reset the grid objects if it is game over
        if(gameState == 4 && reset){
            bugGame = null;
            bugGame = new GridObjectHandler();
            reset = false;
        }

            batch.begin();

            //This is where we move our objects and set win variables
           if(gameState == 1) {
               bugGame.run();

               //update hit boxes after movement. todo: no worries, these can be moved elsewhere -Cameron
               bugGame.getBugOne().updateHitBox();
               bugGame.getBugTwo().updateHitBox();

               //stuff to check for winner
               gameState = winStatus.checkWin(bugGame);
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

            // check to see if we need to reset
            reset = winStatus.isResetNeeded();

            _buttonProcessor.setGameState(gameState);
        }

    @Override
    public void dispose () {
        game.dispose();
        batch.dispose();
    }

}
