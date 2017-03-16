package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;

/**
 * For keeping track of time.
 * Shows up as a timer on the screen, counting down from 60 seconds.
 */
class GameTime {

    private float time;
    private int remainingSeconds;

    /**
     * Constructor to set the total amount of time.
     * @param totaltime the total amount of time available.
     */
    GameTime(float totaltime) { time = totaltime; }

    /**
     * Getter to return the amount of time remaining for the user before game ends.
     * @return The remaining amount of time, in seconds.
     */
    int getTimeRemaining() { return remainingSeconds; }

    /**
     * Setter to change the total amount of time remaining.
     * @param totalTime the amount desired to be set.
     */
    void setTimeRemaining(float totalTime) {
        time = totalTime;
    }

    /**
     * Responsible for running and displaying the timer on the screen.
     */
    void run() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        time -= deltaTime;
        remainingSeconds = ((int)time);
    }
}
