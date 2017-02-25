package com.cs246team01.bugtag;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs246team01.bugtag.GridObjectHandler;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture playerOne;

    GridObjectHandler bugGame;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		playerOne = new Texture("yellow_idle.png");
		bugGame = new GridObjectHandler();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        //this is where we run our game
		bugGame.run();

		//batch.draw(img, 0, 0);
		bugGame.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
