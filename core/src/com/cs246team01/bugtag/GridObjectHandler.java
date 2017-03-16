package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class:GridObjectHandler
 * This class manages all the gridObjects
 */

public class GridObjectHandler {

    //This holds any object used in game
    private List<GridObject> gridObjects;

    private int obstacleWidth = Gdx.graphics.getHeight() / 3;
    private int obstacleHeight = Gdx.graphics.getHeight() / 3;

    private int bugWidth = Gdx.graphics.getHeight()/16;
    private int bugHeight = Gdx.graphics.getHeight()/16;

    private int buttonSide = Gdx.graphics.getHeight()/4;
    private boolean isChaser;
    private Bug chaser;
    private Bug evader;

    //bug starting positions
    private GridPoint2 bug1_pos_start;
    private GridPoint2 bug2_pos_start;

    //This will be initialized during the create method
    //of the main game
    GridObjectHandler() {

        gridObjects = new ArrayList<GridObject>();

        Random rand = new Random();
        isChaser = rand.nextBoolean();

        //Player Textures
        Texture bug1_texture = new Texture("bugs/yellow_idle_large.png");
        Texture bug2_texture = new Texture("bugs/red_idle.png");

        //initialize bugs
        chaser = new Bug(bug1_texture, isChaser, 1);
        evader = new Bug(bug2_texture, false, 2);

        //initialize starting positions for use in resetting game
        bug1_pos_start = new GridPoint2(1076, 540);
        bug2_pos_start = new GridPoint2(717,540);

        //Obstacle Textures
        Texture obstacleOne   = new Texture("obstacles/Real_Pear.png");
        Texture obstacleTwo   = new Texture("obstacles/Real_Apple.png");
        Texture obstacleThree = new Texture("obstacles/Real_Bread.png");
        Texture obstacleFour  = new Texture("obstacles/Real_Watermelon.png");
        Texture obstacleFive  = new Texture("obstacles/Real_Orange.png");

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
        gridObjects.add(chaser);
        gridObjects.add(evader);
        gridObjects.add(new Obstacle(obstacleOne));
        gridObjects.add(new Obstacle(obstacleTwo));
        gridObjects.add(new Obstacle(obstacleThree));
        gridObjects.add(new Obstacle(obstacleFour));
        gridObjects.add(new Obstacle(obstacleFive));
        gridObjects.add(new Button(1, button1));
        gridObjects.add(new Button(2, button2));
        gridObjects.add(new Button(3, button3));
        gridObjects.add(new Button(4, button4));
        gridObjects.add(new Button(5,button5));
        gridObjects.add(new Button(6,button6));
        gridObjects.add(new Button(7,button7));
        gridObjects.add(new Button(8,button8));
    }

    void resetBugPositions() {
        chaser.setPosition(bug1_pos_start);
        evader.setPosition(bug2_pos_start);
    }

    //This is the only method we will call in the render method
    void run() {
        update();

    }

    Bug getChaser(){
        return chaser;
    }
    Bug getEvader(){
        return evader;
    }

    //This method checks if we should stop the game
    //It returns a 1 if the chaser wins, 2 if the timer runs out;
    // -1 if game is still going
    public int checkWin(boolean chaserWin, int time) {
        if(time <= 0) {
            return 2;
        }
        else if (chaserWin) {
            return -1;
        }
        else {
            return 0;
        }
    }

    //Checks if screen has been tapped and moves the bug
    /**************************************
     * Update()
     * This method goes through each object in the game and determines which direction
     * they need to move. Bug movement is determined by the ButtonProcessor class.
     *
     *
     */
    private void update() {
        //movement code here...

        for (GridObject g : gridObjects)

            if (g instanceof Bug) {

                Bug b = (Bug) g;
                if(b.getPlayerID() == 1)
                    handleMove1(b);
                else
                    handleMove2(b);
            }
    }

    //Keeping it modularized
    void draw(SpriteBatch batch) {

        for (GridObject g : gridObjects) {
            if(g instanceof Button) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), buttonSide, buttonSide);
            }
            else if (g instanceof Bug)
            {
                batch.draw(g.getTexture(), g.getX(), g.getY(), bugWidth, bugHeight);
            }
            else if(g instanceof Obstacle) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), obstacleWidth, obstacleHeight);
            }
        }
    }

    private void handleMove1(Bug b) {

        if(ButtonProcessor.moveUp1){
            b.moveUp();
            ButtonProcessor.moveUp1 = false;
        }

        if(ButtonProcessor.moveDown1){
            b.moveDown();
            ButtonProcessor.moveDown1 = false;
        }
        if(ButtonProcessor.moveLeft1){
            b.moveLeft();
            ButtonProcessor.moveLeft1 = false;
        }
        if(ButtonProcessor.moveRight1){
            b.moveRight();
            ButtonProcessor.moveRight1 = false;
        }

    }

    //Important Note, Movement is inverted for player two buttons (upButton = moveDown())
    private void handleMove2(Bug b){

        if(ButtonProcessor.moveUp2){
            b.moveDown();
            ButtonProcessor.moveUp2 = false;
        }

        if(ButtonProcessor.moveDown2){
            b.moveUp();
            ButtonProcessor.moveDown2 = false;
        }
        if(ButtonProcessor.moveLeft2){
            b.moveRight();
            ButtonProcessor.moveLeft2 = false;
        }
        if(ButtonProcessor.moveRight2){
            b.moveLeft();
            ButtonProcessor.moveRight2 = false;
        }
    }

    //This takes all of the buttons and places them in a list for the input processor
    ArrayList<Button> getButtons(){
        ArrayList<Button> buttons = new ArrayList<Button>();
        for (int i = 7; i < gridObjects.size(); i++){
            Button b = (Button) gridObjects.get(i);
            buttons.add(b);
        }

        return buttons;
    }

}