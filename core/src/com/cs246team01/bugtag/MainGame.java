package com.cs246team01.bugtag;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * gameState tracks the state of the game.
 * 0 is start screen, 1 is in gameplay, and 2 is pause, 3 is game over.
 * When various screens are used. This is important to keep track of
 * how to handle touch inputs
 */
class MainGame extends Game {

    //Game
    private SpriteBatch batch;
    private GridObjectHandler bugGame;
    private ButtonProcessor _buttonProcessor;
    private int winner = 0;
    private GameHandler game;
    private GameWin winStatus;

    //keeps track of current gameState
    //it starts off as GAME_NOT_STARTED
    static int gameState = 0;

    private boolean reset;

    //Use this for tagging bugs
    private static final String TAG = "DebugTagger";
    private static final String WIN = "WinTag";

    @Override
    public void create() {
        Preferences numMovesPrefs = Gdx.app.getPreferences("MOVES");
        int numMoves = numMovesPrefs.getInteger("moves", 0);

        //Debugging only - remove
        Gdx.app.log(TAG, "The number of moves are " + numMoves);

        batch = new SpriteBatch();

        winStatus = new GameWin();

        bugGame = new GridObjectHandler();
        game = new GameHandler();
        game.setChaserStatus(bugGame.getBugOne(), bugGame.getBugTwo());
        reset = false;

        // gameState is initially set right here
        _buttonProcessor = new ButtonProcessor(bugGame.getButtons(), gameState);
        Gdx.input.setInputProcessor(_buttonProcessor);
    }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameState = _buttonProcessor.getGameState();

        //reset the grid objects if it is game over
        if (gameState == 4 && reset) {
            bugGame = null;
            bugGame = new GridObjectHandler();
            game.setChaserStatus(bugGame.getBugOne(), bugGame.getBugTwo());
            reset = false;
        }

        batch.begin();

        //This is where we move our objects and set win variables
        if (gameState == 1) {
            bugGame.run();

            //update hit boxes after movement. todo: should maybe be moved to GameHandler
            bugGame.getBugOne().updateHitBox();
            bugGame.getBugTwo().updateHitBox();

            //stuff to check for winner todo: perhaps it would be best to move this to GameHandler so we're not passing around so many objects
            gameState = winStatus.checkWin(bugGame, game.getGameTime());
        }

        if (gameState != 0) {
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
        //if timer is run out, the gameState will be 3 and we will reset
        if(gameState == 3) {
            reset = true;
            // make sure we notify the winner
            game.setWinnerMessage(winStatus.whoIsWinner()); // todo: move to GameHandler
        }

        _buttonProcessor.setGameState(gameState);
    }

    @Override
    public void dispose() {
        game.dispose();
        batch.dispose();
    }

}
