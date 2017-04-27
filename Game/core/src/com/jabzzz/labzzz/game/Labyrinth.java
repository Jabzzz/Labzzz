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

public class Labyrinth
{
    protected int[][] map = null;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Texture ground = null;
    private Texture ground_wall = null;

    private Texture  wall_single        = null;
    private Texture  wall_s_bottom      = null;
    private Texture  wall_s_left        = null;
    private Texture  wall_s_right       = null;
    private Texture  wall_s_top         = null;
    private Texture  wall_s_left_right      = null;
    private Texture  wall_s_top_bottom      = null;
    private Texture  wall_s_all_sides       = null;
    private Texture  wall_s_left_bottom     = null;
    private Texture  wall_s_left_top        = null;
    private Texture  wall_s_right_bottom    = null;
    private Texture  wall_s_right_top       = null;
    private Texture  wall_s_left_right_bottom   = null;
    private Texture  wall_s_top_bottom_left     = null;
    private Texture  wall_s_left_right_top      = null;
    private Texture  wall_s_top_bottom_right    = null;

    private Texture  wall_b_e_left_bottom   = null;
    private Texture  wall_b_e_left_top      = null;
    private Texture  wall_b_e_right_bottom  = null;
    private Texture  wall_b_e_right_top     = null;
    private Texture  wall_b_allsides    = null;
    private Texture  wall_b_e_bottom      = null;
    private Texture  wall_b_e_left        = null;
    private Texture  wall_b_e_right       = null;
    private Texture  wall_b_e_top         = null;
    private Texture  wall_b_c_left_bottom_to_s_right    = null;
    private Texture  wall_b_c_left_top_to_s_right       = null;
    private Texture  wall_b_c_right_bottom_to_s_top     = null;
    private Texture  wall_b_c_right_top_to_s_bottom     = null;
    private Texture  wall_b_c_left_bottom_to_s_top      = null;
    private Texture  wall_b_c_left_top_to_s_bottom      = null;
    private Texture  wall_b_c_right_bottom_to_s_left    = null;
    private Texture  wall_b_c_right_top_to_s_left       = null;
    private Texture  wall_b_c_bottom_right  = null;
    private Texture  wall_b_c_top_right     = null;
    private Texture  wall_b_c_top_left      = null;
    private Texture  wall_b_c_bottom_left   = null;
    private Texture  wall_b_cs_top_left_and_bottom_right    = null;
    private Texture  wall_b_cs_top_right_and_bottom_left    = null;

    private int colStart = 0;
    private int colEnd = 0;
    private int rowStart = 0;
    private int rowEnd = 0;
    private int halfDisplayX = (int) (MainGame.WIDTH / 2);
    private int halfDisplayY = (int) (MainGame.HEIGHT / 2);

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

        ground = new Texture("gamestate/environment/ground.png");
        ground_wall = new Texture("gamestate/environment/ground_with_wall.png");

        wall_single = new Texture("gamestate/environment/wall_shadow_small_lonely.png");
        wall_s_bottom      = new Texture("gamestate/environment/wall_shadow_small_oneside_bottom.png");
        wall_s_left        = new Texture("gamestate/environment/wall_shadow_small_oneside_left.png");
        wall_s_right       = new Texture("gamestate/environment/wall_shadow_small_oneside_right.png");
        wall_s_top         = new Texture("gamestate/environment/wall_shadow_small_oneside_top.png");
        wall_s_left_right      = new Texture("gamestate/environment/wall_shadow_small_twoside_left_right.png");
        wall_s_top_bottom      = new Texture("gamestate/environment/wall_shadow_small_twoside_top_bottom.png");
        wall_s_all_sides       = new Texture("gamestate/environment/wall_shadow_small_fourside.png");
        wall_s_left_bottom     = new Texture("gamestate/environment/wall_shadow_small_corner_left_bottom.png");
        wall_s_left_top        = new Texture("gamestate/environment/wall_shadow_small_corner_left_top.png");
        wall_s_right_bottom    = new Texture("gamestate/environment/wall_shadow_small_corner_right_bottom.png");
        wall_s_right_top       = new Texture("gamestate/environment/wall_shadow_small_corner_right_top.png");
        wall_s_left_right_bottom   = new Texture("gamestate/environment/wall_shadow_small_threeside_edge_at_top.png");
        wall_s_top_bottom_left     = new Texture("gamestate/environment/wall_shadow_small_threeside_edge_at_right.png");
        wall_s_left_right_top      = new Texture("gamestate/environment/wall_shadow_small_threeside_edge_at_bottom.png");
        wall_s_top_bottom_right    = new Texture("gamestate/environment/wall_shadow_small_threeside_edge_at_left.png");

        wall_b_e_left_bottom   = new Texture("gamestate/environment/wall_shadow_big_corner_left_bottom.png");
        wall_b_e_left_top      = new Texture("gamestate/environment/wall_shadow_big_corner_left_top.png");
        wall_b_e_right_bottom  = new Texture("gamestate/environment/wall_shadow_big_corner_right_bottom.png");
        wall_b_e_right_top     = new Texture("gamestate/environment/wall_shadow_big_corner_right_top.png");
        wall_b_allsides = new Texture("gamestate/environment/wall_shadow_big_fourside.png");
        wall_b_e_bottom      = new Texture("gamestate/environment/wall_shadow_big_threeside_edge_at_bottom.png");
        wall_b_e_left        = new Texture("gamestate/environment/wall_shadow_big_threeside_edge_at_left.png");
        wall_b_e_right       = new Texture("gamestate/environment/wall_shadow_big_threeside_edge_at_right.png");
        wall_b_e_top         = new Texture("gamestate/environment/wall_shadow_big_threeside_edge_at_top.png");
        wall_b_c_left_bottom_to_s_right    = new Texture("gamestate/environment/wall_shadow_big_corner_left_bottom_to_small_right.png");
        wall_b_c_left_top_to_s_right       = new Texture("gamestate/environment/wall_shadow_big_corner_left_top_to_small_right.png");
        wall_b_c_right_bottom_to_s_top     = new Texture("gamestate/environment/wall_shadow_big_corner_right_bottom_to_small_top.png");
        wall_b_c_right_top_to_s_bottom     = new Texture("gamestate/environment/wall_shadow_big_corner_right_top_to_small_bottom.png");
        wall_b_c_left_bottom_to_s_top      = new Texture("gamestate/environment/wall_shadow_big_corner_left_bottom_to_small_top.png");
        wall_b_c_left_top_to_s_bottom      = new Texture("gamestate/environment/wall_shadow_big_corner_left_top_to_small_bottom.png");
        wall_b_c_right_bottom_to_s_left    = new Texture("gamestate/environment/wall_shadow_big_corner_right_bottom_to_small_left.png");
        wall_b_c_right_top_to_s_left       = new Texture("gamestate/environment/wall_shadow_big_corner_right_top_to_small_left..png");
        wall_b_c_bottom_right  = new Texture("gamestate/environment/wall_shadow_big_corner_bottom_right.png");
        wall_b_c_top_right     = new Texture("gamestate/environment/wall_shadow_big_corner_top_right.png");
        wall_b_c_top_left      = new Texture("gamestate/environment/wall_shadow_big_corner_top_left.png");
        wall_b_c_bottom_left   = new Texture("gamestate/environment/wall_shadow_big_corner_bottom_left.png");
        wall_b_cs_top_left_and_bottom_right    = new Texture("gamestate/environment/wall_shadow_big_twocorners_top_left_and_bottom_right.png");
        wall_b_cs_top_right_and_bottom_left    = new Texture("gamestate/environment/wall_shadow_big_twocorners_top_right_and_bottom_left.png");
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
    public int getColumnMapBlocksAtPosition(Vector2 position)
    {
        return MathUtils.floor(position.x / MainGame.BLOCK_SIZE);
    }
    public int getRowMapBlocksAtPosition(Vector2 position)
    {
        return MathUtils.floor(position.y / MainGame.BLOCK_SIZE);
    }
    private int mod(int x, int y)
    {
        return (((x % y) + y) % y);
    }

    public Vector2 getMidpointFromBlock(int row, int colum)
    {
        return new Vector2(colum * MainGame.BLOCK_SIZE + MainGame.BLOCK_SIZE / 2, row * MainGame.BLOCK_SIZE + MainGame.BLOCK_SIZE / 2);
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
                if((getMapBlockAt(row, colum) == 2)||(getMapBlockAt(row, colum) == 3))
                {
                    freeBlocks.add(getMidpointFromBlock(row, colum));
                    //System.out.println(freeBlocks.size());
                }
            }
        }

        int randomNumber = new Random().nextInt(freeBlocks.size());

        return freeBlocks.get(randomNumber);
    }



    public void render(SpriteBatch theBatch, OrthographicCamera theCam)
    {
        theBatch.begin();
        Vector2 position = new Vector2();

        colStart = MathUtils.floor((theCam.position.x - halfDisplayX) / MainGame.BLOCK_SIZE);
        colEnd = MathUtils.floor((theCam.position.x + halfDisplayX) / MainGame.BLOCK_SIZE);
        rowStart = MathUtils.floor((theCam.position.y - halfDisplayY) / MainGame.BLOCK_SIZE);
        rowEnd = MathUtils.floor((theCam.position.y + halfDisplayY) / MainGame.BLOCK_SIZE);

        for(int row = rowStart; row <= rowEnd; row++)
        {
            for(int colum = colStart; colum <= colEnd; colum++) {
                /*if (getMapBlockAt(row, colum) == 1) {
                    position.set(MainGame.BLOCK_SIZE * colum, MainGame.BLOCK_SIZE * row);

                    theBatch.draw(wall_single, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                }
                else if (getMapBlockAt(row, colum) == 0) {
                    position.set(MainGame.BLOCK_SIZE * colum, MainGame.BLOCK_SIZE * row);

                    theBatch.draw(ground, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                }*/
                position.set(MainGame.BLOCK_SIZE * colum, MainGame.BLOCK_SIZE * row);
                switch(getMapBlockAt(row,colum))
                {
                    case 2:theBatch.draw(ground, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 3:theBatch.draw(ground_wall, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;

                    case 20:theBatch.draw(wall_single, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 21:theBatch.draw(wall_s_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 22:theBatch.draw(wall_s_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 23:theBatch.draw(wall_s_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 24:theBatch.draw(wall_s_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 25:theBatch.draw(wall_s_left_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 26:theBatch.draw(wall_s_top_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 27:theBatch.draw(wall_s_all_sides, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 28:theBatch.draw(wall_s_left_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 29:theBatch.draw(wall_s_left_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 30:theBatch.draw(wall_s_right_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 31:theBatch.draw(wall_s_right_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 32:theBatch.draw(wall_s_left_right_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 33:theBatch.draw(wall_s_top_bottom_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 34:theBatch.draw(wall_s_left_right_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 35:theBatch.draw(wall_s_top_bottom_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;

                    case 36:theBatch.draw(wall_b_e_left_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 37:theBatch.draw(wall_b_e_left_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 38:theBatch.draw(wall_b_e_right_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 39:theBatch.draw(wall_b_e_right_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 40:theBatch.draw(wall_b_allsides, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 41:theBatch.draw(wall_b_e_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 42:theBatch.draw(wall_b_e_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 43:theBatch.draw(wall_b_e_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 44:theBatch.draw(wall_b_e_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 45:theBatch.draw(wall_b_c_left_bottom_to_s_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 46:theBatch.draw(wall_b_c_left_top_to_s_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 47:theBatch.draw(wall_b_c_right_bottom_to_s_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 48:theBatch.draw(wall_b_c_right_top_to_s_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 49:theBatch.draw(wall_b_c_left_bottom_to_s_top, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 50:theBatch.draw(wall_b_c_left_top_to_s_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 51:theBatch.draw(wall_b_c_right_bottom_to_s_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 52:theBatch.draw(wall_b_c_right_top_to_s_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 53:theBatch.draw(wall_b_c_bottom_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 54:theBatch.draw(wall_b_c_top_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 55:theBatch.draw(wall_b_c_top_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 56:theBatch.draw(wall_b_c_bottom_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 57:theBatch.draw(wall_b_cs_top_left_and_bottom_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 58:theBatch.draw(wall_b_cs_top_right_and_bottom_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
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

    public int getWidth() { return map.length * MainGame.BLOCK_SIZE; }
    public int getHeight() { return map[0].length * MainGame.BLOCK_SIZE; }
}
