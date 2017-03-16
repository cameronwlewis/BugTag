package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Random;

/**
 * Created by Landon on 2/23/2017.
 */

public class Obstacle extends GridObject {

    //Default Constructor
    public Obstacle() {

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Random r = new Random();

        int x = r.nextInt(width);
        int y = r.nextInt(height);

        GridPoint2 loc = new GridPoint2(x, y);

        currentPosition = loc;
    }

    //Constructor to set Texture and Location
    public Obstacle(Texture type) {
        this.setTexture(type);

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Random r = new Random();

        int x = r.nextInt(width);
        int y = r.nextInt(height);

        GridPoint2 loc = new GridPoint2(x, y);

        currentPosition = loc;
    }

    //for when an obstacle scrolls off the screen
    public void hide(){

    }
}
