package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles movement and visualization for all {@link GridObject} instances
 * <p>
 * All of the GridObjects are stored in an array list. The list is iterated through and each object
 * is checked. Its position is updated if it has moved and the object is drawn accordingly.
 *
 * @author everyone
 */

class GridObjectHandler {

    //This holds any object used in game
    private List<GridObject> gridObjects;

    private int obstacleWidth = Gdx.graphics.getHeight() / 3;
    private int obstacleHeight = Gdx.graphics.getHeight() / 3;

    private int bugWidth = Gdx.graphics.getHeight() / 14;
    private int bugHeight = Gdx.graphics.getHeight() / 14;

    private int buttonSide = Gdx.graphics.getHeight() / 4;
    private Bug bug1;
    private Bug bug2;

    /**
     * This will be initialized during the create method
     * of the main game.
     */
    GridObjectHandler() {

        gridObjects = new ArrayList<GridObject>();

        Random rand = new Random();
        boolean randBoolean = rand.nextBoolean();

        // Player Textures
        Texture bug1_texture = new Texture("bugs/yellow_idle_large.png");
        Texture bug2_texture = new Texture("bugs/red_idle.png");

        //initialize starting positions for use in resetting game.
        // This MUST HAPPEN before initializing bugs,
        // OR hit boxes WILL NOT generate.
        GridPoint2 bug1_pos_start = new GridPoint2((Gdx.graphics.getWidth() * 6) / 10,
                Gdx.graphics.getHeight() / 2);
        GridPoint2 bug2_pos_start = new GridPoint2((Gdx.graphics.getWidth() * 4) / 10,
                Gdx.graphics.getHeight() / 2);

        //Initialize bugs
        bug1 = new Bug(bug1_texture, randBoolean, 1);
        bug2 = new Bug(bug2_texture, !randBoolean, 2);

        //Initialize Obstacles
        Obstacle obstacleOne = new Obstacle(new Texture("obstacles/Real_Pear.png"));
        Obstacle obstacleTwo = new Obstacle(new Texture("obstacles/Real_Apple.png"));
        Obstacle obstacleThree = new Obstacle(new Texture("obstacles/Real_Bread.png"));
        Obstacle obstacleFour = new Obstacle(new Texture("obstacles/Real_Watermelon.png"));
        Obstacle obstacleFive = new Obstacle(new Texture("obstacles/Real_Orange.png"));

        //Button textures
        Texture button1 = new Texture("buttons/arrow-left.png");
        Texture button2 = new Texture("buttons/arrow-right.png");
        Texture button3 = new Texture("buttons/arrow-down.png");
        Texture button4 = new Texture("buttons/arrow-up.png");
        Texture button5 = new Texture("buttons/arrow-right.png");
        Texture button6 = new Texture("buttons/arrow-left.png");
        Texture button7 = new Texture("buttons/arrow-up.png");
        Texture button8 = new Texture("buttons/arrow-down.png");

        //Add objects to the array
        gridObjects.add(bug1);
        gridObjects.add(bug2);
        gridObjects.add(obstacleOne);
        gridObjects.add(obstacleTwo);
        gridObjects.add(obstacleThree);
        gridObjects.add(obstacleFour);
        gridObjects.add(obstacleFive);
        gridObjects.add(new Button(1, button1));
        gridObjects.add(new Button(2, button2));
        gridObjects.add(new Button(3, button3));
        gridObjects.add(new Button(4, button4));
        gridObjects.add(new Button(5, button5));
        gridObjects.add(new Button(6, button6));
        gridObjects.add(new Button(7, button7));
        gridObjects.add(new Button(8, button8));
    }

   /* void resetBugPositions() {
        bug1.setPosition(bug1_pos_start);
        bug2.setPosition(bug2_pos_start);
    }*/

    /**
     * This is the only method we will call in the render method.
     */
    void run() {
        update();

    }

    /**
     * Getter for Bug[1] object.
     *
     * @return bug1 object.
     */
    Bug getBugOne() {
        return bug1;
    }

    /**
     * Getter for Bug[2] object.
     *
     * @return bug2 object.
     */
    Bug getBugTwo() {
        return bug2;
    }

    /**
     * This method checks if we should stop the game
     * It returns a 1 if the chaser wins, 2 if the timer runs out;
     * -1 if game is still going
     *
     * @param chaserWin
     * @param time
     * @return
     */
    public int checkWin(boolean chaserWin, int time) {
        if (time <= 0) {
            return 2;
        } else if (chaserWin) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Checks if screen has been tapped and moves the bug.
     * This method goes through each object in the game and determines which direction
     * they need to move. Bug movement is determined by the ButtonProcessor class.
     */
    private void update() {
        //movement code here...

        for (GridObject g : gridObjects) {

            //bug handling
            if (g instanceof Bug) {

                Bug b = (Bug) g;
                if (b.getPlayerID() == 1)
                    handleMove1(b);
                else
                    handleMove2(b);
            }

            //obstacle handling
            if (g instanceof Obstacle) {

                Obstacle o = (Obstacle) g;
                handlemoveObs(o);

                if (o.getY() == 0 - o.getTexture().getHeight()) {
                    o.setY(Gdx.graphics.getHeight());
                }
            }
        }

    }

    //Keeping it modularized
    void draw(SpriteBatch batch) {

        for (GridObject g : gridObjects) {
            if (g instanceof Button) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), buttonSide, buttonSide);
            } else if (g instanceof Bug) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), bugWidth, bugHeight);
            } else if (g instanceof Obstacle) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), obstacleWidth, obstacleHeight);
            }
        }
    }

    private void handleMove1(Bug b) {

        if (ButtonProcessor.moveUp1) {
            b.moveUp();
            ButtonProcessor.moveUp1 = false;
        }

        if (ButtonProcessor.moveDown1) {
            b.moveDown();
            ButtonProcessor.moveDown1 = false;
        }
        if (ButtonProcessor.moveLeft1) {
            b.moveLeft();
            ButtonProcessor.moveLeft1 = false;
        }
        if (ButtonProcessor.moveRight1) {
            b.moveRight();
            ButtonProcessor.moveRight1 = false;
        }

    }

    /**
     * Important Note, Movement is inverted for player bug2 buttons (upButton = moveDown())
     *
     * @param b
     */
    private void handleMove2(Bug b) {

        if (ButtonProcessor.moveUp2) {
            b.moveDown();
            ButtonProcessor.moveUp2 = false;
        }

        if (ButtonProcessor.moveDown2) {
            b.moveUp();
            ButtonProcessor.moveDown2 = false;
        }
        if (ButtonProcessor.moveLeft2) {
            b.moveRight();
            ButtonProcessor.moveLeft2 = false;
        }
        if (ButtonProcessor.moveRight2) {
            b.moveLeft();
            ButtonProcessor.moveRight2 = false;
        }
    }

    private void handlemoveObs(Obstacle o) {

        o.moveLeft();
    }

    //This takes all of the buttons and places them in a list for the input processor
    ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<Button>();
        for (int i = 7; i < gridObjects.size(); i++) {
            Button b = (Button) gridObjects.get(i);
            buttons.add(b);
        }

        return buttons;
    }

}