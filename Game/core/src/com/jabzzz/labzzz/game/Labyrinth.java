package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Labyrinth {
    private int[][] map = null;

    public Labyrinth()//int amountColums, int amountRows)
    {
        //Example for a map
        map = new int[][]
            {
                    {1,1,1,1,1,1},
                    {1,0,0,0,0,1},
                    {1,0,1,1,0,1},
                    {1,0,1,1,0,1},
                    {1,0,0,0,0,1},
                    {1,1,1,1,1,1}
            };


    }

    public void render(AEntity center)
    {
        //One Unit is 50px


    }


    public void update()
    {

    }

    public int[][] getMap()
    {
        return map;
    }
}
