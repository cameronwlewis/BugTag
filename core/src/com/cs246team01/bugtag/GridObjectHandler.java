package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Landon on 2/23/2017.
 */

public class GridObjectHandler {

    //this holds any object used in game
    private List<GridObject> gridObjects;

    //this will be initialized during the create method
    //of the main game
    public GridObjectHandler() {

        gridObjects = new ArrayList<GridObject>();

        Random rand = new Random();
        boolean isChaser = rand.nextBoolean();

        //Player Textures
        Texture bugOne = new Texture("yellow_idle_large.png");
        Texture bugTwo = new Texture("red_idle.png");

        //Obstacle Textures
        Texture obstacleOne   = new Texture("lettuce_large.png");
        Texture obstacleTwo   = new Texture("apple_large.png");
        Texture obstacleThree = new Texture("bread_large.png");
        Texture obstacleFour  = new Texture("tomato_large.png");
        Texture obstacleFive  = new Texture("pineapple.png");

        //Button textures
        Texture button1 = new Texture("arrow-left.png");
        Texture button2 = new Texture("arrow-right.png");
        Texture button3 = new Texture("arrow-down.png");
        Texture button4 = new Texture("arrow-up.png");
        Texture button5 = new Texture("arrow-right.png");
        Texture button6 = new Texture("arrow-left.png");
        Texture button7 = new Texture("arrow-up.png");
        Texture button8 = new Texture("arrow-down.png");

        //Add objects to the array
        gridObjects.add(new Bug(bugOne, isChaser));
        gridObjects.add(new Obstacle(obstacleOne, 1000, 600));
        gridObjects.add(new Obstacle(obstacleTwo, 900, 300));
        gridObjects.add(new Obstacle(obstacleThree, 1500, 400));
        gridObjects.add(new Obstacle(obstacleFour, 200, 500));
        gridObjects.add(new Obstacle(obstacleFive, 900, 900));
        gridObjects.add(new Button(1, button1));
        gridObjects.add(new Button(2, button2));
        gridObjects.add(new Button(3, button3));
        gridObjects.add(new Button(4, button4));


    }

    //this is the only method we will call in the render method
    public void run(int move) {
        update(move);
    }

    //this method checks if we should stop the game
    //it returns a 1 if the chaser wins, 2 if the timer runs out;
    // -1 if game is still going
    public int checkWin() {
        return 0;
    }


    //checks if screen has been tapped and moves the bug

    /**************************************
     * Update(int move)
     * This method goes through each object in the game and determines which direction
     * they need to move. It passes in a movement direction which will be determined by a
     * control pad on the screen
     *
     * @param move
     */
    private void update(int move) {
        //movement code here...

        for (GridObject g : gridObjects)

            if (g instanceof Bug) {

                Bug b = (Bug) g;
                handleMove(b, move);
            }


    }

    //keeping it modularized
    public void draw(SpriteBatch batch) {

        for (GridObject g : gridObjects)
            batch.draw(g.getTexture(), g.getX(), g.getY());

    }


    public void handleMove(Bug b, int moveInt) {

        switch (moveInt) {
            case 1:
                b.moveLeft();
                break;
            case 2:
                b.moveUp();
                break;
            case 3:
                b.moveRight();
                break;
            case 4:
                b.moveDown();
                break;
        }


    }

//    public void resume() {
//        Preferences texture = Gdx.app.getPreferences("TEXTURE");
//
//        String textureName = texture.getString("TEXTURE","DEFAULT");
//
//
//    }
//
//    public void pause() {
//        Preferences texture = ;
//
//        GridObject go = gridObjects.get(0);
//        Texture saveTexture = go.getTexture();
//        String textureName = saveTexture.toString();
//
//        texture.putString("color",textureName);
//
//
//    }
}