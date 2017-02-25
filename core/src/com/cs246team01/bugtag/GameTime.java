package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;

/**
 * For keeping track of time
 */

public class GameTime {

    private float time;
    private int remainingSeconds;

    public GameTime(float totaltime){
        time = totaltime;
    }

    public int getTimeRemaining() {
        return remainingSeconds;
    }

    public void run(){
        float deltaTime = Gdx.graphics.getDeltaTime();

        time -= deltaTime;
        remainingSeconds = ((int)time);
    }
}
