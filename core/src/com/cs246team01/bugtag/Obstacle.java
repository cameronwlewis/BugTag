package com.cs246team01.bugtag;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class Obstacle extends GridObject {

    //Default Constructor
    public Obstacle() {
    }

    //Constructor to set Texture and Location
    public Obstacle(Texture type, int x, int y) {
        this.setTexture(type);

        GridPoint2 startLoc = new GridPoint2(x, y);
        setPosition(startLoc);
    }

    //For when an obstacle scrolls off the screen
    public void hide(){
    }
}
