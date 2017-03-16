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



    //Game
    private SpriteBatch batch;
    private GridObjectHandler bugGame;
    private ButtonProcessor _buttonProcessor;
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

    private Win WinStatus;

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

        WinStatus = new Win();

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
               //todo: win position variables set here
               gameState = WinStatus.checkWin(bugGame);
               reset = WinStatus.isResetNeeded();

               if (gameState == 3){
                   Gdx.app.log("Game Over", "1) Should be game over.");
               }
           }

           if(gameState != 0) {
               //draw everything
               bugGame.draw(batch);
               //display timer
               game.displayTime(batch);
           }

            game.run();
        if (gameState == 3){
            Gdx.app.log("Game Over", "2) Should be game over.");
        }


        game.displayMessage(batch);

            batch.end();

            //todo: checkWin here
        if (gameState == 3){
            Gdx.app.log("Game Over", "2) Should be game over.");
        }



        _buttonProcessor.setGameState(gameState);
        if (gameState == 3){
            Gdx.app.log("Game Over", "2) Should be game over.");
        }

    }

    @Override
    public void dispose () {
        game.dispose();
        batch.dispose();
    }

}
