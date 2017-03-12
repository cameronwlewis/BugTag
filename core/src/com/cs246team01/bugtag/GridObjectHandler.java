package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class:GridObjectHandler
 * This class manages all the gridObjects
 */

public class GridObjectHandler {

    //this holds any object used in game
    private List<GridObject> gridObjects;

    int obstacleWidth = Gdx.graphics.getHeight() / 3 ;
    int obstacleHeight = Gdx.graphics.getHeight() / 3;

    int bugWidth = Gdx.graphics.getHeight()/16;
    int bugHeight = Gdx.graphics.getHeight()/16;

    int buttonSide = Gdx.graphics.getHeight()/4;

    //this will be initialized during the create method
    //of the main game
    public GridObjectHandler() {

        gridObjects = new ArrayList<GridObject>();

        Random rand = new Random();
        boolean isChaser = rand.nextBoolean();

        //Player Textures
        Texture bugOne = new Texture("bugs/yellow_idle_large.png");
        Texture bugTwo = new Texture("bugs/red_idle.png");

        //Obstacle Textures
        Texture obstacleOne   = new Texture("obstacles/lettuce_large.png");
        Texture obstacleTwo   = new Texture("obstacles/apple_large.png");
        Texture obstacleThree = new Texture("obstacles/bread_large.png");
        Texture obstacleFour  = new Texture("obstacles/tomato_large.png");
        Texture obstacleFive  = new Texture("obstacles/orange.png");

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
        gridObjects.add(new Bug(bugOne, isChaser, 1));
        gridObjects.add(new Bug(bugTwo, false, 2));
        gridObjects.add(new Obstacle(obstacleOne, 1000, 600));
        gridObjects.add(new Obstacle(obstacleTwo, 900, 300));
        gridObjects.add(new Obstacle(obstacleThree, 1500, 400));
        gridObjects.add(new Obstacle(obstacleFour, 200, 500));
        gridObjects.add(new Obstacle(obstacleFive, 900, 900));
        gridObjects.add(new Button(1, button1));
        gridObjects.add(new Button(2, button2));
        gridObjects.add(new Button(3, button3));
        gridObjects.add(new Button(4, button4));
        gridObjects.add(new Button(5,button5));
        gridObjects.add(new Button(6,button6));
        gridObjects.add(new Button(7,button7));
        gridObjects.add(new Button(8,button8));


    }

    //This is the only method we will call in the render method
    public void run() {
        update();
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
        } else {
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
    public void draw(SpriteBatch batch) {

        for (GridObject g : gridObjects) {
            if(g instanceof Button) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), buttonSide, buttonSide);
            } else if (g instanceof Bug)
            {
                batch.draw(g.getTexture(), g.getX(), g.getY(), bugWidth, bugHeight);
            } else if(g instanceof Obstacle) {
                batch.draw(g.getTexture(), g.getX(), g.getY(), obstacleWidth, obstacleHeight);
            }
        }
    }

    public void handleMove1(Bug b) {

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
    public void handleMove2(Bug b){

        if(ButtonProcessor.moveUp2 == true){
            b.moveDown();
            ButtonProcessor.moveUp2 = false;

        }

        if(ButtonProcessor.moveDown2 == true){
            b.moveUp();
            ButtonProcessor.moveDown2 = false;
        }
        if(ButtonProcessor.moveLeft2 == true){
            b.moveRight();
            ButtonProcessor.moveLeft2 = false;
        }
        if(ButtonProcessor.moveRight2 == true){
            b.moveLeft();
            ButtonProcessor.moveRight2 = false;
        }
    }

    //this takes all of the buttons and places them in a list for the input processor
    public ArrayList<Button> getButtons(){
        ArrayList<Button> buttons = new ArrayList<Button>();
        for (int i = 7; i < gridObjects.size(); i++){
            Button b = (Button) gridObjects.get(i);
            buttons.add(b);
        }

        return buttons;
    }
}