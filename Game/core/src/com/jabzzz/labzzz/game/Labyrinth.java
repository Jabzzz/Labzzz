package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.*;

import java.util.Random;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Labyrinth {
    protected int[][] map = null;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Labyrinth(int mapLength)//int amountColums, int amountRows)
    {
        //Example for a map
        /*map = new int[][]
            {
                    {1,1,1,0,1,1},
                    {1,0,0,0,0,1},
                    {1,0,1,0,0,1},
                    {1,0,1,1,1,1},
                    {0,0,0,0,0,0},
                    {1,1,1,0,1,1}
            };*/
        map = new int[mapLength][mapLength];
    }

    public int getMapBlockAt(int row, int col)
    {
        int rowInMatrix = mod(row, map.length);
        int colInMatrix = mod(col, map.length);

        return map[rowInMatrix][colInMatrix];
    }
    public int getMapBlockAtPosition(Vector2 position)
    {
        int col = MathUtils.floor(position.x / 50);
        int row = MathUtils.floor(position.y / 50);

        return getMapBlockAt(row, col);
    }
    private int mod(int x, int y)
    {
        return (((x % y) + y) % y);
    }

    public Vector2 getSpawnPosition()
    {

        for(int row = 0; row < map.length; row++)
        {
            for (int colum = 0; colum < map[row].length; colum++)
            {
                if(getMapBlockAt(row, colum) == 0)
                {
                    return new Vector2(colum * 50 + 25, row * 50 + 25);
                }
            }
        }


        return null;
    }


    public void render(OrthographicCamera theCam)
    {
        theCam.update();
        shapeRenderer.setProjectionMatrix(theCam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);

        Vector2 sizeDisplay = new Vector2(MainGame.WIDTH, MainGame.HEIGHT);
        int blockSize = 50;
        Vector2 position = new Vector2();

        //One Unit is 50px
        int halfDisplayX = MathUtils.ceil(sizeDisplay.x * 0.5f);
        int halfDisplayY = MathUtils.ceil(sizeDisplay.y * 0.5f);

        int colStart = MathUtils.floor((theCam.position.x - halfDisplayX) / blockSize);
        int colEnd = MathUtils.floor((theCam.position.x + halfDisplayX) / blockSize);
        int rowStart = MathUtils.floor((theCam.position.y - halfDisplayY) / blockSize);
        int rowEnd = MathUtils.floor((theCam.position.y + halfDisplayY) / blockSize);


        for(int row = rowStart; row <= rowEnd; row++)
        {
            for(int colum = colStart; colum <= colEnd; colum++) {
                if (getMapBlockAt(row, colum) == 1) {
                    position.set(50 * colum, 50 * row);

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
