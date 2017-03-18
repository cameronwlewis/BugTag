package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

/**
 * This class contains the implementation of a bug, which extends GridObject
 * A bug will have a specific texture and a player ID. This object will
 * be controlled by the player. When the bug goes offscreen, its "hide"
 * function will keep track of how long it has been there.
 * getPlayerID Returns which player controls this bug.
 */

public class Bug extends GridObject {

    private static final String TAG = "DebugTagger";

    private boolean isChaser;

    //To keep track of player1 and player2 bugs
    private int playerID;
    private Direction currentDirection;

    // bug/player hit box
    private Rectangle bug_hitbox;

    public Bug() {
    }

    Bug(Texture bugImage, boolean chaser, int playerID) {
        //This sets whether the bug will be the chaser randomly
        isChaser = chaser;

        this.setTexture(bugImage);
        //Set bug in bottom left corner

        if (playerID == 1) {
            currentPosition = new GridPoint2((Gdx.graphics.getWidth() * 8) / 10,
                    Gdx.graphics.getHeight() / 2);
            this.playerID = 1;
            setCurrentDirection(Direction.Up);
        } else {
            currentPosition = new GridPoint2((Gdx.graphics.getWidth() * 2) / 10,
                    Gdx.graphics.getHeight() / 2);
            this.playerID = 2;
            setCurrentDirection(Direction.Down);
        }

        // make hit box for bug/player
        //float bug_height = bugImage.getHeight();
        //float bug_width = bugImage.getWidth();

        //updated hit box dimensions, it is set to a universal size that adapts with the different screen sizes
        float bug_height = Gdx.graphics.getHeight() / 14;
        float bug_width = Gdx.graphics.getHeight() / 14;
        bug_hitbox = new Rectangle(getX(), getY(), bug_width, bug_height);


        //Keep track of bug's position
        Gdx.app.log(TAG, this.getPosition().toString());
    }

    /**
     *
     * @param dir
     */
    void setCurrentDirection(Direction dir) {
        this.currentDirection = dir;

        switch (currentDirection) {
            case Up:
                if (playerID == 1)
                    setTexture(new Texture("bugs/bug1left.png"));
                else
                    setTexture(new Texture("bugs/bug2left.png"));
                break;
            case Down:
                if (playerID == 1)
                    setTexture(new Texture("bugs/bug1right.png"));
                else
                    setTexture(new Texture("bugs/bug2right.png"));
                break;
            case Left:
                if (playerID == 1)
                    setTexture(new Texture("bugs/bug1down.png"));
                else
                    setTexture(new Texture("bugs/bug2down.png"));
                break;
            case Right:
                if (playerID == 1)
                    setTexture(new Texture("bugs/bug1up.png"));
                else
                    setTexture(new Texture("bugs/bug2up.png"));
                break;
            default:
                break;
        }
    }

    /**
     *
     * @return
     */
    Direction getCurrentDirection() { return currentDirection; }

    void updateHitBox() {
        bug_hitbox.setPosition(getX(), getY());
    }

    // todo make a 'touchingOverBug' function here using bug_hitbox.contains()

    Rectangle getHitBox() {

        return bug_hitbox;
    }

    int getPlayerID() {
        return playerID;
    }

    //When the bug goes off of the screen
    public void hide() {

    }
}
