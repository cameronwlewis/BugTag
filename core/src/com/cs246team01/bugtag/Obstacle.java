package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Random;

/**
 * Class : Obstacle
 * A grid-object obstacle that will obstruct view of the player onscreen
 * Obstacles onscreen will appear in a random location at the start of the
 * game, and move in a direction continuously throughout. When a player
 * moves underneath an obstacle, their exact location will be hidden from
 * view.
 */

public class Obstacle extends GridObject {

    int ID;

    //Constructor to set Texture and Location
    Obstacle(Texture type, int myID) {
        this.setTexture(type);
        ID = myID;

        int width = Gdx.graphics.getWidth() - (Gdx.graphics.getHeight() / 2);
        int height = Gdx.graphics.getHeight();

        Random r = new Random();

        int x = r.nextInt(width) + (Gdx.graphics.getHeight() / 8);
        int y = r.nextInt(height);

        currentPosition = new GridPoint2(x, y);
    }

    void setID(int myID) { ID = myID; }
    int  getID()         { return ID; }

    /**
     * Move Left
     * This function when called, moves the GridObject to the left
     */
    void moveLeft() {

        this.currentPosition.y -= 1;

        //Keep track of object's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    //methods for abstract class
    void hideTop() {
    }
    void hideLeft() {
    }
    void hideRight() {
    }
    void hideDown() {
    }
    boolean isHiding(){
        return false;
    }

}
