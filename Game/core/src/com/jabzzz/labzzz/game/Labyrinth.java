package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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
                    {1,1,1,0,1,1},
                    {1,0,0,0,0,1},
                    {1,0,1,0,0,1},
                    {1,0,1,1,1,1},
                    {0,0,0,0,0,0},
                    {1,1,1,0,1,1}
            };


    }

    public int getMapBlockAt(int row, int col)
    {
        int rowInMatrix = mod(row, map.length);
        int colInMatrix = mod(col, map.length);

        return map[rowInMatrix][colInMatrix];
    }
    private int mod(int x, int y)
    {
        return (((x % y) + y) % y);
    }

    public void render(OrthographicCamera theCam)
    {
        theCam.update();
        shapeRenderer.setProjectionMatrix(theCam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);

        Vector2 sizeDisplay = new Vector2(500, 850);
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
