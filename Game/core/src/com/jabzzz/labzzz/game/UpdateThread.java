package com.jabzzz.labzzz.game;

/**
 * Created by Useless on 01.04.2017.
 */

public class UpdateThread extends Thread
{

    private MainGame theMainGame = null;
    private double nsPerTick = 1000000000D/60D;

    public UpdateThread(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;

        System.out.println("UpdateThread got constructed!");
    }

    private boolean running = true;


    @Override
    public void run()
    {
        System.out.println("UpdateThread started!");

        long lastTime = System.nanoTime();

        int ticks   = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0D;  //how many nanoseconds have gone by so far

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/nsPerTick;
            lastTime = now;
            boolean shouldUpdate = false;

            while (delta >=1)
            {
                ticks++;
                delta -= 1;
                shouldUpdate = true;
            }


            //sleep to prevent overload
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(shouldUpdate)
            {
                theMainGame.update();
            }

            if(System.currentTimeMillis() - lastTimer >= 1000)
            {
                lastTimer += 1000;
                System.out.println("TPS: " + ticks);
                ticks= 0;
            }
        }
    }

    public void setUpdatesPerSecond(double UPS)
    {
        this.nsPerTick = 1000000000D/UPS;
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
