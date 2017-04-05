package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Labyrinth {
    private int[][] map = null;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Labyrinth()//int amountColums, int amountRows)
    {
        //Example for a map
        map = new int[][]
            {
                    {1,1,1,1,1,1},
                    {1,0,0,0,0,1},
                    {1,0,1,0,0,1},
                    {1,0,1,1,1,1},
                    {1,0,0,0,0,1},
                    {1,1,1,1,1,1}
            };


    }

    public void render(OrthographicCamera theCam)
    {
        theCam.update();
        shapeRenderer.setProjectionMatrix(theCam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);

        Vector2 position = new Vector2();
        //One Unit is 50px
        for(int row = 0; row < map.length; row++)
        {
            for(int colum = 0; colum < map[row].length; colum++) {
                if (map[row][colum] == 1) {
                    position.set(50 * (map[row].length - colum - 1), 50 * row);

                    shapeRenderer.rect(position.x, position.y, 50, 50);
                }
            }
        }

        shapeRenderer.end();

    }


    public void update()
    {

    }

    public int[][] getMap()
    {
        return map;
    }
}
