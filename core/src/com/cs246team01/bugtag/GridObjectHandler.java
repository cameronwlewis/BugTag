package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles movement and visualization for all {@link GridObject} instances
 * <p>
 * All of the GridObjects are stored in an array list. The list is iterated through and each object
 * is checked. Its position is updated if it has moved and the object is drawn accordingly.</p>
 *
 * @author everyone
 */

public class GridObjectHandler {

    //log tagger
    static final String MOVE = "MoveTagger";
    //This holds any object used in game
    private List<GridObject> gridObjects;

    //Obstacle dimensions
    private int obstacleWidth = Gdx.graphics.getHeight() / 3;
    private int obstacleHeight = Gdx.graphics.getHeight() / 3;

    //Bug Dimensions
    private int bugWidth = Gdx.graphics.getHeight() / 13;
    private int bugHeight = Gdx.graphics.getHeight() / 13;

    //Button Dimensions
    private int buttonSide = Gdx.graphics.getHeight() / 4;

    //Players declared
    public static Bug bug1_yellow;
    public static Bug bug2_red;

    /**
     * This will be initialized during the create method
     * of the main game.
     */
    GridObjectHandler() {

        gridObjects = new ArrayList<GridObject>();



        // Player Textures
        Texture bug1_texture = new Texture("bugs/yellow_idle_large.png");
        Texture bug2_texture = new Texture("bugs/red_idle.png");

        //Initialize bugs
        bug1_yellow = new Bug(bug1_texture, 1);
        bug2_red = new Bug(bug2_texture, 2);

        //Initialize Obstacles                                                                //ID
        Obstacle obstacleOne     = new Obstacle(new Texture("obstacles/Real_Pear.png"),         1);
        Obstacle obstacleTwo     = new Obstacle(new Texture("obstacles/Real_Apple.png"),        0);
        Obstacle obstacleThree   = new Obstacle(new Texture("obstacles/Real_Bread.png"),        0);
        Obstacle obstacleFour    = new Obstacle(new Texture("obstacles/Real_Watermelon.png"),   1);
        Obstacle obstacleFive    = new Obstacle(new Texture("obstacles/Real_Orange.png"),       0);
        Obstacle obstacleSix     = new Obstacle(new Texture("obstacles/Real_Blackberries.png"), 0);
        Obstacle obstacleSeven   = new Obstacle(new Texture("obstacles/Real_Blueberries.png"),  0);
        Obstacle obstacleEight   = new Obstacle(new Texture("obstacles/Real_Bananas.png"),      1);

        //Button textures
        Texture button1 = new Texture("buttons/arrow-left.png");
        Texture button2 = new Texture("buttons/arrow-right.png");
        Texture button3 = new Texture("buttons/arrow-down.png");
        Texture button4 = new Texture("buttons/arrow-up.png");
        Texture button5 = new Texture("buttons/arrow-right.png");
        Texture button6 = new Texture("buttons/arrow-left.png");
        Texture button7 = new Texture("buttons/arrow-up.png");
        Texture button8 = new Texture("buttons/arrow-down.png");

        //Add all gridobjects to the array
        gridObjects.add(bug1_yellow);
        gridObjects.add(bug2_red);
        gridObjects.add(obstacleOne);
        gridObjects.add(obstacleTwo);
        gridObjects.add(obstacleThree);
        gridObjects.add(obstacleFour);
        gridObjects.add(obstacleFive);
        gridObjects.add(obstacleSix);
        gridObjects.add(obstacleSeven);
        gridObjects.add(obstacleEight);
        gridObjects.add(new Button(1, button1));
        gridObjects.add(new Button(2, button2));
        gridObjects.add(new Button(3, button3));
        gridObjects.add(new Button(4, button4));
        gridObjects.add(new Button(5, button5));
        gridObjects.add(new Button(6, button6));
        gridObjects.add(new Button(7, button7));
        gridObjects.add(new Button(8, button8));

        //set the chaser buttons to the correct color
        int chaserID = 0;

    }

    /**
     * This is the only method we will call in the render method.
     */
    void run() {
        update();

        //update bug hit boxes after movement.
        bug1_yellow.updateHitBox();
        bug2_red.updateHitBox();
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
     * Will set the buttons of the player who is the chaser to the color of their bug
     * @param redIsChaser Who the chaser is
     */
    void setChaserButtonColor(boolean redIsChaser) {
        int bIndex = 0;
        for(GridObject current: gridObjects){ //get the index to the buttons
            if(current instanceof Button)
                break;
            bIndex++;
        }
        if (!redIsChaser) { //yellow bug buttons change
            gridObjects.get(bIndex).setTexture(new Texture("buttons/arrow-left-yellow.png"));
            gridObjects.get(bIndex + 1).setTexture(new Texture("buttons/arrow-right-yellow.png"));
            gridObjects.get(bIndex + 2).setTexture(new Texture("buttons/arrow-down-yellow.png"));
            gridObjects.get(bIndex + 3).setTexture(new Texture("buttons/arrow-up-yellow.png"));
        }
        else if (redIsChaser) { //red bug buttons change
            gridObjects.get(bIndex + 4).setTexture(new Texture("buttons/arrow-right-red.png"));
            gridObjects.get(bIndex + 5).setTexture(new Texture("buttons/arrow-left-red.png"));
            gridObjects.get(bIndex + 6).setTexture(new Texture("buttons/arrow-up-red.png"));
            gridObjects.get(bIndex + 7).setTexture(new Texture("buttons/arrow-down-red.png"));
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

                if (o.getID() == 1) {
                    if (o.getY() == 0 - (o.getTexture().getHeight()) * 1.5f) {
                        o.setY(Gdx.graphics.getHeight());
                        //X spawn is randomly generated
                        int width = Gdx.graphics.getWidth() - (Gdx.graphics.getHeight() / 2);
                        Random r = new Random();
                        o.setX(r.nextInt(width) + (Gdx.graphics.getHeight() / 8));
                    }
                }
                else if (o.getY() == 0 - o.getTexture().getHeight()) {
                    o.setY(Gdx.graphics.getHeight());
                    //X spawn is randomly generated
                    int width = Gdx.graphics.getWidth() - (Gdx.graphics.getHeight() / 2);
                    Random r = new Random();
                    o.setX(r.nextInt(width) + (Gdx.graphics.getHeight() / 8));
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
                if (((Obstacle) g).getID() == 1)
                    //draw big
                    batch.draw(g.getTexture(), g.getX(), g.getY(), obstacleWidth * 1.5f, obstacleHeight * 1.5f);
                else
                    //draw normal
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

    /**
     * Moves the bug back on screen and makes it visible
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
     * Handles input from {@link ButtonProcessor} to move player 2
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

    /**
     * Takes all of the buttons and places them in a list for the input processor
     *
     * It iterates through all of the gridobjects in our array until it reaches a button. All 8
     * of the gameplay buttons should be added at the END of the array. Once it finds a button it
     * stores the remaining objects of the gridObjects array into the buttons array.
     *
     * @return returns a list of all the gameplay buttons which are used in the {@link ButtonProcessor}.
     */

    ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<Button>();
        int bIndex = 0;
        for(GridObject current: gridObjects){
            if(current instanceof Button)
                break;
            bIndex++;
        }
        for (int i = bIndex; i < gridObjects.size(); i++) {
            Button b = (Button) gridObjects.get(i);
            buttons.add(b);
        }
        return buttons;
    }
}