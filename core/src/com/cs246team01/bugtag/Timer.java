package com.cs246team01.bugtag;

/**
 * For keeping track of time
 */

public class Timer {

    private int timeRemaining;

    public Timer(){
        timeRemaining = 60;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void tick(){
        timeRemaining--;
    }

    //use this method during the draw method of gridObjectHandler
    public void displayTimer(){

        //if(timeRemaining == 60)
            //find xml timer id, set its text to "1:00"

        //else if(timeRemaining >= 10)
            //find xml timer id, set its text to "0:" + remainingTime
        //else
        //find xml timer id, set its text to "0:0" + remainingTime
    }

}
