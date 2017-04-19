package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.*;
import com.jabzzz.labzzz.states.GameState;

import java.util.ArrayList;
import java.util.Random;

import sun.applet.Main;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Labyrinth {
    protected int[][] map = null;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Texture wall = null;
    private Texture ground = null;

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

        wall = new Texture("wall.png");
        ground = new Texture("ground.png");

    }

    public int getMapBlockAt(int row, int col)
    {
        int rowInMatrix = mod(row, map.length);
        int colInMatrix = mod(col, map.length);

        return map[rowInMatrix][colInMatrix];
    }
    public int getMapBlockAtPosition(Vector2 position)
    {
        int col = MathUtils.floor(position.x / MainGame.BLOCK_SIZE);
        int row = MathUtils.floor(position.y / MainGame.BLOCK_SIZE);

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
                    return getMidpointFromBlock(row, colum);
                }
            }
        }


        return null;
    }

    public Vector2 getRandomSpawnPosition()
    {
        ArrayList<Vector2> freeBlocks = new ArrayList<Vector2>();

        for(int row = 0; row < map.length; row++)
        {
            for (int colum = 0; colum < map[row].length; colum++)
            {
                if(getMapBlockAt(row, colum) == 0)
                {
                    freeBlocks.add(getMidpointFromBlock(row, colum));
                }
            }
        }

        int randomNumber = new Random().nextInt(freeBlocks.size());

        return freeBlocks.get(randomNumber);
    }

    public Vector2 getMidpointFromBlock(int row, int colum)
    {
        return new Vector2(colum * MainGame.BLOCK_SIZE + MainGame.BLOCK_SIZE / 2, row * MainGame.BLOCK_SIZE + MainGame.BLOCK_SIZE / 2);
    }


    public void render(SpriteBatch theBatch, OrthographicCamera theCam)
    {
        theBatch.begin();

        Vector2 sizeDisplay = new Vector2(MainGame.WIDTH, MainGame.HEIGHT);
        Vector2 position = new Vector2();

        //One Unit is 50px
        int halfDisplayX = MathUtils.ceil(sizeDisplay.x * 0.5f);
        int halfDisplayY = MathUtils.ceil(sizeDisplay.y * 0.5f);

        int colStart = MathUtils.floor((theCam.position.x - halfDisplayX) / MainGame.BLOCK_SIZE);
        int colEnd = MathUtils.floor((theCam.position.x + halfDisplayX) / MainGame.BLOCK_SIZE);
        int rowStart = MathUtils.floor((theCam.position.y - halfDisplayY) / MainGame.BLOCK_SIZE);
        int rowEnd = MathUtils.floor((theCam.position.y + halfDisplayY) / MainGame.BLOCK_SIZE);

        for(int row = rowStart; row <= rowEnd; row++)
        {
            for(int colum = colStart; colum <= colEnd; colum++) {
                if (getMapBlockAt(row, colum) == 1) {
                    position.set(MainGame.BLOCK_SIZE * colum, MainGame.BLOCK_SIZE * row);

                    theBatch.draw(wall, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                }
                else if (getMapBlockAt(row, colum) == 0) {
                    position.set(MainGame.BLOCK_SIZE * colum, MainGame.BLOCK_SIZE * row);

                    theBatch.draw(ground, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                }
            }
        }

        theBatch.end();

    }

    public void update()
    {

    }

    public int[][] getMap()
    {
        return map;
    }
}
