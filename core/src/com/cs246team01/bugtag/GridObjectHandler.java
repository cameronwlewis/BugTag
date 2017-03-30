package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

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

    static final String MOVE = "MoveTagger";
    //This holds any object used in game
    private List<GridObject> gridObjects;

    //Obstacle dimensions
    private int obstacleWidth = Gdx.graphics.getHeight() / 3;
    private int obstacleHeight = Gdx.graphics.getHeight() / 3;

    //Bug Dimensions
    private int bugWidth = Gdx.graphics.getHeight() / 14;
    private int bugHeight = Gdx.graphics.getHeight() / 14;

    //Button Dimensions
    private int buttonSide = Gdx.graphics.getHeight() / 4;



    //Players declared
    private Bug bug1_yellow;
    private Bug bug2_red;

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

        /*//initialize starting positions for use in resetting game.
        // This MUST HAPPEN before initializing bugs,
        // OR hit boxes WILL NOT generate.
        GridPoint2 bug1_pos_start = new GridPoint2((Gdx.graphics.getWidth() * 6) / 10,
                Gdx.graphics.getHeight() / 2);
        GridPoint2 bug2_pos_start = new GridPoint2((Gdx.graphics.getWidth() * 4) / 10,
                Gdx.graphics.getHeight() / 2);*/

        //Initialize bugs
        bug1_yellow = new Bug(bug1_texture, randBoolean, 1);
        bug2_red = new Bug(bug2_texture, !randBoolean, 2);

        //Initialize Obstacles
        Obstacle obstacleOne   = new Obstacle(new Texture("obstacles/Real_Pear.png"));
        Obstacle obstacleTwo   = new Obstacle(new Texture("obstacles/Real_Apple.png"));
        Obstacle obstacleThree = new Obstacle(new Texture("obstacles/Real_Bread.png"));
        Obstacle obstacleFour  = new Obstacle(new Texture("obstacles/Real_Watermelon.png"));
        Obstacle obstacleFive  = new Obstacle(new Texture("obstacles/Real_Orange.png"));

        //Button textures
        Texture button1 = new Texture("buttons/arrow-left.png");
        Texture button2 = new Texture("buttons/arrow-right.png");
        Texture button3 = new Texture("buttons/arrow-down.png");
        Texture button4 = new Texture("buttons/arrow-up.png");
        Texture button5 = new Texture("buttons/arrow-right.png");
        Texture button6 = new Texture("buttons/arrow-left.png");
        Texture button7 = new Texture("buttons/arrow-up.png");
        Texture button8 = new Texture("buttons/arrow-down.png");
        Texture startButton = new Texture("buttons/startgame.png");

        //Add objects to the array
        gridObjects.add(bug1_yellow);
        gridObjects.add(bug2_red);
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
        gridObjects.add(new Button(9, startButton));
    }


    /**
     * This is the only method we will call in the render method.
     */
    void run() {
        update();

    }

    /**
     * Getter for Bug[1] object.
     *
     * @return bug1_yellow object.
     */
    Bug getBugOne() {
        return bug1_yellow;
    }

    /**
     * Getter for Bug[2] object.
     *
     * @return bug2_red object.
     */
    Bug getBugTwo() {
        return bug2_red;
    }

    /**
     * Checks if screen has been tapped and moves the bug.
     * This method goes through each object in the game and determines which direction
     * they need to move. Bug movement is determined by the ButtonProcessor class.
     */
    private void update() {
        //movement code here...

        for (GridObject g : gridObjects) {

            //if object is bug and is not hiding check for movement
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
            } else if (g instanceof Bug && !((Bug) g).isHiding()) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), bugWidth, bugHeight);
            } else if (g instanceof Obstacle) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), obstacleWidth, obstacleHeight);
            }
            //make the bug visible after 3 seconds
            if(g instanceof Bug && ((Bug) g).isHiding()){
                final Bug b = (Bug) g;
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        if(b.isHiding()) {
                            makeVisible(b);
                            b.setRefreshed(false);
                            Gdx.app.log(MOVE, "isRefreshed: " + b.isRefreshed());
                        }
                    }
                }
                        ,3);//delay in seconds
        }
            if (g instanceof Bug && !((Bug) g).isRefreshed()){
                final Bug b = (Bug) g;
                Timer.schedule(new Timer.Task(){
                                   @Override
                                   public void run() {
                                       if(!b.isRefreshed())
                                           b.setRefreshed(true);
                                   }
                               }
                        ,3);//delay in seconds
            }

        }



    }

    /** moves the bug back on screen and makes it visible
     *
     * @param b
     */
    private void makeVisible(Bug b) {
        //wherever the bug is hiding, move it the opposite direction
        if(b.isHiding()) {
            if (b.isHidingLeft())
                b.moveRight();
            if (b.isHidingRight())
                b.moveLeft();
            if (b.isHidingTop())
                b.moveDown();
            if (b.isHidingDown())
                b.moveUp();
            //make it visible
            b.setHiding(false);
        }

    }

    /**
     * Handles input from {@link ButtonProcessor} to move player 1
     *
     * The method takes taps received from player 1's buttons and checks if the player is currently
     * hiding. If not it moves any direction that has been pressed and changes the direction of the
     * image.
     *
     * If the bug is hiding it checks for several rules. First, the bug cannot continue to move
     * farther away from the screen. Second, if the bug moves towards the screen it immediately
     * becomes visible.
     *
     * @param b
     */
    private void handleMove1(Bug b) {
            //move up if we are not hiding at top already
            if (ButtonProcessor.moveUp1 && !b.isHidingTop()) {
                b.moveUp();
                ButtonProcessor.moveUp1 = false;
                //now handle invisible movement
                if(b.isHidingDown()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Up)
                    b.setCurrentDirection(Direction.Up);
            }
            //Down
            if (ButtonProcessor.moveDown1 && !b.isHidingDown()) {
                b.moveDown();
                ButtonProcessor.moveDown1 = false;
                //now handle invisible movement
                if(b.isHidingTop()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Down)
                    b.setCurrentDirection(Direction.Down);
            }
            //Left
            if (ButtonProcessor.moveLeft1 && !b.isHidingLeft()) {
                b.moveLeft();
                ButtonProcessor.moveLeft1 = false;
                //now handle invisible movement
                if(b.isHidingRight()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Left)
                    b.setCurrentDirection(Direction.Left);
            }
            //Right
            if (ButtonProcessor.moveRight1 && !b.isHidingRight()) {
                b.moveRight();
                ButtonProcessor.moveRight1 = false;
                //now handle invisible movement
                if(b.isHidingLeft()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Right)
                    b.setCurrentDirection(Direction.Right);
            }

    }


    /**
     * Handles input from {@link ButtonProcessor} to move player 1
     *
     * Important Note, Movement is inverted for player bug2_red buttons (upButton = moveDown())
     * The method takes taps received from player 2's buttons and checks if the player is currently
     * hiding. If not it moves any direction that has been pressed and changes the direction of the
     * image.
     *
     * If the bug is hiding it checks for several rules. First, the bug cannot continue to move
     * farther away from the screen. Second, if the bug moves towards the screen it immediately
     * becomes visible. Invisble movement calls are also inverted from player 1's movement
     *
     * @param b
     */
    private void handleMove2(Bug b) {

            //Down
            if (ButtonProcessor.moveUp2 && !b.isHidingDown()) {
                b.moveDown();
                ButtonProcessor.moveUp2 = false;
                //now handle invisible movement
                if(b.isHidingTop()) {
                    b.setHiding(false);
                }


                //set direction
                if (b.getCurrentDirection() != Direction.Down)
                    b.setCurrentDirection(Direction.Down);
            }
            //Up
            if (ButtonProcessor.moveDown2 && !b.isHidingTop()) {
                b.moveUp();
                ButtonProcessor.moveDown2 = false;
                //now handle invisible movement
                if(b.isHidingDown()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Up)
                    b.setCurrentDirection(Direction.Up);
            }
            //Right
            if (ButtonProcessor.moveLeft2 && !b.isHidingRight()) {
                b.moveRight();
                ButtonProcessor.moveLeft2 = false;
                //now handle invisible movement
                if(b.isHidingLeft()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Right)
                    b.setCurrentDirection(Direction.Right);
            }
            //Left
            if (ButtonProcessor.moveRight2 && !b.isHidingLeft()) {
                b.moveLeft();
                ButtonProcessor.moveRight2 = false;
                //now handle invisible movement
                if(b.isHidingRight()) {
                    b.setHiding(false);
                }

                //set direction
                if (b.getCurrentDirection() != Direction.Left)
                    b.setCurrentDirection(Direction.Left);
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