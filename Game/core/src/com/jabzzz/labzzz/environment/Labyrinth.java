package com.jabzzz.labzzz.environment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.ai_skills.WallDetection;
import com.jabzzz.labzzz.controller.*;
import com.jabzzz.labzzz.states.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;


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
    private TextureRegion  wall_s_bottom      = null;
    private TextureRegion  wall_s_left_right  = null;
    private Texture  wall_s_all_sides       = null;
    private TextureRegion  wall_s_left_bottom     = null;
    private TextureRegion  wall_s_left_right_bottom   = null;

    private TextureRegion wall_b_e_left_bottom   = null;
    private Texture  wall_b_allsides    = null;
    private TextureRegion  wall_b_e_bottom      = null;
    private TextureRegion  wall_b_c_left_bottom_to_s_right    = null;
    private TextureRegion  wall_b_c_left_top_to_s_right       = null;
    private TextureRegion  wall_b_c_bottom_right  = null;
    private TextureRegion  wall_b_cs_top_left_and_bottom_right    = null;
    private TextureRegion  wall_b_cs_bottom_left_right    = null;
    private TextureRegion  wall_b_threecs_bottom_left     = null;
    private Texture  wall_b_fourcs    = null;

    private int colStart = 0;
    private int colEnd = 0;
    private int rowStart = 0;
    private int rowEnd = 0;
    private int halfDisplayX = (int) (GameState.viewPortWidth / 2);
    private int halfDisplayY = (int) (GameState.viewPortHeight / 2);

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
        wall_s_bottom      = new TextureRegion(new Texture("gamestate/environment/wall_shadow_small_oneside_bottom.png") );
        wall_s_left_right      = new TextureRegion(new Texture("gamestate/environment/wall_shadow_small_twoside_left_right.png"));
        wall_s_all_sides       = new Texture("gamestate/environment/wall_shadow_small_fourside.png");
        wall_s_left_bottom     = new TextureRegion(new Texture("gamestate/environment/wall_shadow_small_corner_left_bottom.png"));
        wall_s_left_right_bottom   = new TextureRegion(new Texture("gamestate/environment/wall_shadow_small_threeside_edge_at_top.png"));

        wall_b_e_left_bottom   = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_corner_left_bottom.png"));
        wall_b_allsides = new Texture("gamestate/environment/wall_shadow_big_fourside.png");
        wall_b_e_bottom      = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_threeside_edge_at_bottom.png"));
        wall_b_c_left_bottom_to_s_right    = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_corner_left_bottom_to_small_right.png"));
        wall_b_c_left_top_to_s_right       = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_corner_left_top_to_small_right.png"));
        wall_b_c_bottom_right  = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_corner_bottom_right.png"));
        wall_b_cs_top_left_and_bottom_right    = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_twocorners_top_left_and_bottom_right.png"));
        wall_b_cs_bottom_left_right = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_twocorners_bottom_left_right.png"));
        wall_b_threecs_bottom_left     = new TextureRegion(new Texture("gamestate/environment/wall_shadow_big_threecorners_bottom_left.png"));
        wall_b_fourcs    = new Texture("gamestate/environment/wall_shadow_big_fourcorners.png");
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
                    case 22:theBatch.draw(wall_s_bottom, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 23:theBatch.draw(wall_s_bottom, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 24:theBatch.draw(wall_s_bottom, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 25:theBatch.draw(wall_s_left_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 26:theBatch.draw(wall_s_left_right, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0,MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 27:theBatch.draw(wall_s_all_sides, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 28:theBatch.draw(wall_s_left_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 29:theBatch.draw(wall_s_left_bottom, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 30:theBatch.draw(wall_s_left_bottom, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 31:theBatch.draw(wall_s_left_bottom, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE,  0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 32:theBatch.draw(wall_s_left_right_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 33:theBatch.draw(wall_s_left_right_bottom, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 34:theBatch.draw(wall_s_left_right_bottom, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE,  0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 35:theBatch.draw(wall_s_left_right_bottom, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 36:theBatch.draw(wall_b_e_left_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 37:theBatch.draw(wall_b_e_left_bottom, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 38:theBatch.draw(wall_b_e_left_bottom, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 39:theBatch.draw(wall_b_e_left_bottom, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 40:theBatch.draw(wall_b_allsides, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 41:theBatch.draw(wall_b_e_bottom, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 42:theBatch.draw(wall_b_e_bottom, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 43:theBatch.draw(wall_b_e_bottom, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 44:theBatch.draw(wall_b_e_bottom, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 45:theBatch.draw(wall_b_c_left_bottom_to_s_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 46:theBatch.draw(wall_b_c_left_top_to_s_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 47:theBatch.draw(wall_b_c_left_bottom_to_s_right, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 48:theBatch.draw(wall_b_c_left_top_to_s_right, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 49:theBatch.draw(wall_b_c_left_top_to_s_right, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 50:theBatch.draw(wall_b_c_left_bottom_to_s_right, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 51:theBatch.draw(wall_b_c_left_top_to_s_right, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 52:theBatch.draw(wall_b_c_left_bottom_to_s_right, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 53:theBatch.draw(wall_b_c_bottom_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 54:theBatch.draw(wall_b_c_bottom_right, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 55:theBatch.draw(wall_b_c_bottom_right, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 56:theBatch.draw(wall_b_c_bottom_right, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 57:theBatch.draw(wall_b_cs_top_left_and_bottom_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 58:theBatch.draw(wall_b_cs_top_left_and_bottom_right, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 59:theBatch.draw(wall_b_cs_bottom_left_right, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 60:theBatch.draw(wall_b_cs_bottom_left_right, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 61:theBatch.draw(wall_b_cs_bottom_left_right, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 62:theBatch.draw(wall_b_cs_bottom_left_right, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 63:theBatch.draw(wall_b_threecs_bottom_left, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                        break;
                    case 64:theBatch.draw(wall_b_threecs_bottom_left, position.x+MainGame.BLOCK_SIZE, position.y, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 90f);
                        break;
                    case 65:theBatch.draw(wall_b_threecs_bottom_left, position.x, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 270f);
                        break;
                    case 66:theBatch.draw(wall_b_threecs_bottom_left, position.x+MainGame.BLOCK_SIZE, position.y+MainGame.BLOCK_SIZE, 0, 0, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE, 1, 1, 180f);
                        break;
                    case 67:theBatch.draw(wall_b_fourcs, position.x, position.y, MainGame.BLOCK_SIZE, MainGame.BLOCK_SIZE);
                }
            }
        }

        theBatch.end();

    }

    public void update()
    {

    }

    public boolean isLineWithoutBlocks(Vector2 start, Vector2 end)
    {
        ArrayList<Vector2> intersectionsWithGrid = getIntersectionsWithGrid(start, end);

        for(int i = 1; i < intersectionsWithGrid.size(); i++)
        {
            Vector2 midPoint = new Vector2(intersectionsWithGrid.get(i-1)).add(intersectionsWithGrid.get(i));
            midPoint.x *= 0.5f;
            midPoint.y *= 0.5f;
            if (WallDetection.isBlockWall(getMapBlockAtPosition(midPoint)))
                return false;
        }

        return true;
    }
    //test

    public ArrayList<Vector2> getIntersectionsWithGrid(Vector2 start, Vector2 end)
    {
        ArrayList<Vector2> intersections = new ArrayList<Vector2>();

        Line2 line = searchPathThroughBorder(start, end);

        int xStart, xEnd, yStart, yEnd;

        if(line.start.x < line.end.x)
        {
            xStart = MathUtils.ceil((line.start.x) / MainGame.BLOCK_SIZE);
            xEnd = MathUtils.floor((line.end.x) / MainGame.BLOCK_SIZE);
        }
        else
        {
            xStart = MathUtils.ceil((line.end.x) / MainGame.BLOCK_SIZE);
            xEnd = MathUtils.floor((line.start.x) / MainGame.BLOCK_SIZE);
        }

        if(start.y < end.y)
        {
            yStart = MathUtils.ceil((line.start.y) / MainGame.BLOCK_SIZE);
            yEnd = MathUtils.floor((line.end.y) / MainGame.BLOCK_SIZE);
        }
        else
        {
            yStart = MathUtils.ceil((line.end.y) / MainGame.BLOCK_SIZE);
            yEnd = MathUtils.floor((line.start.y) / MainGame.BLOCK_SIZE);
        }

        float m = (line.end.y - line.start.y) / (line.end.x - line.start.x);
        float t = line.start.y - line.start.x * m;

        for (int xGrid = xStart; xGrid <= xEnd; xGrid++)
        {
            float x = xGrid * MainGame.BLOCK_SIZE;
            float y = m * x + t;
            intersections.add(new Vector2(x, y));
        }

        for(int yGrid = yStart; yGrid <= yEnd; yGrid++)
        {
            float x;
            float y = yGrid * MainGame.BLOCK_SIZE;
            if(start.x != end.x)
                x = (y - t) / m;
            else
                x = start.x;
            intersections.add(new Vector2(x, y));
        }

        intersections.sort(new Comparator<Vector2>() {
            @Override
            public int compare(Vector2 v1, Vector2 v2) {
                if(v1.x < v2.x)
                    return 1;
                else if(v1.x == v2.x)
                    if(v1.y < v2.y)
                        return 1;
                    else
                        return -1;
                else
                    return -1;
            }
        });

        return intersections;
    }

    public Line2 searchPathThroughBorder(Vector2 start, Vector2 end)
    {
        Vector2 path = new Vector2(end).sub(start);
        Vector2 visualEnd = new Vector2(end);

        if(path.len() > Math.min(getWidth(), getHeight()) * 0.5f)
        {
            //maybe there is a shorter way
            Vector2 shorterPath;

            float yAchsisChange;
            if(start.y > getHeight() * 0.5f)
                yAchsisChange = getHeight();
            else
                yAchsisChange = - getHeight();

            shorterPath = new Vector2(end).add(0, yAchsisChange).sub(start);

            if(shorterPath.len2() < path.len2())
                visualEnd.add(0, yAchsisChange);

            float xAchsisChange;
            if(start.x > getWidth() * 0.5f)
                xAchsisChange = getWidth();
            else
                xAchsisChange = - getWidth();

            shorterPath = new Vector2(end).add(xAchsisChange,0).sub(start);

            if(shorterPath.len2() < path.len2())
                visualEnd.add(xAchsisChange, 0);
        }

        return new Line2(start, visualEnd);
    }

    public class Line2 {
        public Vector2 start, end;

        public Line2(Vector2 s, Vector2 e)
        {
            start = new Vector2(s);
            end = new Vector2(e);
        }
    }

    public int[][] getMap()
    {
        return map;
    }

    public int getWidth() { return map.length * MainGame.BLOCK_SIZE; }
    public int getHeight() { return map[0].length * MainGame.BLOCK_SIZE; }
}
