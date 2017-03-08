package com.cs246team01.bugtag;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainGame extends Game{
	private SpriteBatch batch;

	private GameTime timer;
	private float totalTime;
	private FreeTypeFontGenerator fontFT;
	private FreeTypeFontParameter parameter;

	private BitmapFont font;

    private GridObjectHandler bugGame;

	//TEST PREFERENCES
	private int numMoves = 0;

	//use this for taggin' them bugs
	private static final String TAG = "DebugTagger";

	@Override
	public void create () {
		Preferences numMovesPrefs = Gdx.app.getPreferences("MOVES");
		numMoves = numMovesPrefs.getInteger("moves", 0);

		//Debugging only - remove
		Gdx.app.log(TAG,"The number of moves are " + numMoves);

		//Since this is a constant (or is it?)
		//we can just assign a hardcoded value
		totalTime = 60;
		timer = new GameTime(totalTime);

		//Font is comic sans and font size is 50 colored red
		fontFT = new FreeTypeFontGenerator(Gdx.files.internal("comic-sans.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 50;
		font = fontFT.generateFont(parameter);
		font.setColor(Color.RED);

		batch = new SpriteBatch();

		bugGame = new GridObjectHandler();

		ButtonProcessor buttonProcessor = new ButtonProcessor(bugGame.getButtons());
        Gdx.input.setInputProcessor(buttonProcessor);
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

        //This is where we run our game
		bugGame.run();

		bugGame.draw(batch);

		//moved timer code into this method
		displayTime();



		batch.end();

		//update timer value
		timer.run();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
    }


	public void displayTime(){
		//Display timer
		if(timer.getTimeRemaining() >= 10) {
			font.draw(batch, "0:" + timer.getTimeRemaining(),
					Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
					Gdx.graphics.getWidth() / 2);
		} else if (timer.getTimeRemaining() < 10 && timer.getTimeRemaining() > 0) {
			font.draw(batch, "0:0" + timer.getTimeRemaining(),
					Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
					Gdx.graphics.getWidth() / 2);
		} else {
			font.draw(batch, "0:00",
					Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 12),
					Gdx.graphics.getWidth() / 2);
			font.draw(batch, "TIME UP",
					Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 8),
					Gdx.graphics.getWidth() / 3);
		}

	}
	/*@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		numMoves++;

		Preferences numMovesPrefs = Gdx.app.getPreferences("MOVES");
		numMovesPrefs.putInteger("moves", numMoves);
		numMovesPrefs.flush();

		if (screenX < Gdx.graphics.getWidth()/2)
			moveInt = 1;
		else if (screenX > Gdx.graphics.getWidth()/2)
			moveInt = 3;
		return false;

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case 19: moveInt = 4;//up
				break;
			case 20: moveInt = 2;//down
				break;
			case 21: moveInt = 1;//left
				break;
			case 22: moveInt = 3;//right
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}*/

}
