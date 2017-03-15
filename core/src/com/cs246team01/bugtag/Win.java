package com.cs246team01.bugtag;

import com.badlogic.gdx.Gdx;

import static com.cs246team01.bugtag.GridObject.TAG;

/**
 * Created by cameronlewis on 3/14/17.
 */

public class Win {

    public void checkWin(Bug chaser, Bug evader){
        if (chaser.getPosition() == evader.getPosition())
            Gdx.app.log(TAG, "Chaser touched evader! Game over!");
    }
}

