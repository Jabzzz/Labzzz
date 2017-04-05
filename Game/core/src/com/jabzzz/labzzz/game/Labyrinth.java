package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

    public void render(AEntity center, OrthographicCamera theCam)
    {
        ShapeRenderer sr = new ShapeRenderer();
        theCam.update();
        sr.setProjectionMatrix(theCam.combined);

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
