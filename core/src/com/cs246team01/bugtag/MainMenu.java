package com.cs246team01.bugtag;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Main Menu : A class to display a menu on startup
 * Created by josh on 3/25/17.
 */

public class MainMenu implements Screen {

    private MainGame game;
    private Texture startButton;

    public MainMenu(MainGame myGame) {

        this.game  = myGame;
        startButton = new Texture("buttons/startgame.png");
    }

    /** Called when this screen becomes the current screen for a {@link Game}. */
    public void show (){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game.batch.begin();

        int centerX = (Gdx.graphics.getWidth()/2)-(startButton.getWidth()/2);
        int centerY = (Gdx.graphics.getHeight()/2)-(startButton.getHeight()/2);

        //game.batch.draw(startButton, centerX, centerY);

        //game.batch.end();
    }

    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    public void render (float delta){

    }

    /** @see ApplicationListener#resize(int, int) */
    public void resize (int width, int height){

    }

    /** @see ApplicationListener#pause() */
    public void pause (){

    }

    /** @see ApplicationListener#resume() */
    public void resume (){

    }

    /** Called when this screen is no longer the current screen for a {@link Game}. */
    public void hide (){

    }

    /** Called when this screen should release all resources. */
    public void dispose (){

    }

}
