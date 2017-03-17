package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Random;

/**
 * Class:Obstacle
 * A grid-object obstacle that will obstruct view of the player onscreen
 * Obstacles onscreen will appear in a random location at the start of the
 * game, and move in a direction continuously throughout. When a player
 * moves underneath an obstacle, their exact location will be hidden from
 * view.
 */

public class Obstacle extends GridObject {

    //Default Constructor
    public Obstacle() { //todo: weird...it says this constructor is never used -Cameron

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Random r = new Random();

        int x = r.nextInt(width);
        int y = r.nextInt(height);

        GridPoint2 loc = new GridPoint2(x, y);

        currentPosition = loc;
    }

    //Constructor to set Texture and Location
    Obstacle(Texture type) {
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
    public void hide() {

    }
}
