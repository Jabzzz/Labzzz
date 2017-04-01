package com.jabzzz.labzzz.game;

/**
 * Created by Useless on 01.04.2017.
 */

public class UpdateThread extends Thread
{
    private boolean running = true;


    @Override
    public void run()
    {
        while(running){

        }



    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    public boolean getRunning ()
    {
        return this.running;
    }
}
