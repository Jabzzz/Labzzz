package com.jabzzz.labzzz.ai_skills;

import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.environment.Labyrinth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 02.05.2017.
 */

public class WallDetection {
    /*
    index   Position
    0       right
    1       top
    2       left
    3       bottom

    true: isWall
    false: noWall
     */
    private boolean[] borderingBlocks = new boolean[] {false, false, false, false};

    public WallDetection()
    {

    }

    public void update(Labyrinth labyrinth, Vector2 position)
    {
        int colum = labyrinth.getColumnMapBlocksAtPosition(position);
        int row = labyrinth.getRowMapBlocksAtPosition(position);

        borderingBlocks[0] = isBlockWall(labyrinth.getMapBlockAt(row, colum + 1));
        borderingBlocks[1] = isBlockWall(labyrinth.getMapBlockAt(row + 1, colum));
        borderingBlocks[2] = isBlockWall(labyrinth.getMapBlockAt(row, colum - 1));
        borderingBlocks[3] = isBlockWall(labyrinth.getMapBlockAt(row - 1, colum));
    }

    public static boolean isBlockWall(int blockType)
    {
        return (blockType > 19) || (2 > blockType);
    }

    public List<Integer> getNoWallBlocks()
    {
        List<Integer> noWallBlocks = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++)
        {
            if(!borderingBlocks[i])
            {
                noWallBlocks.add(i);
            }
        }

        return noWallBlocks;
    }
    public List<Integer> getNoWallBlocksWithout(int withoutValue)
    {
        List<Integer> noWallBlocks = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++)
        {
            if(!borderingBlocks[i] && (i != withoutValue))
            {
                noWallBlocks.add(i);
            }
        }

        return noWallBlocks;
    }

    public boolean getBlock(int index)
    {
        return borderingBlocks[index];
    }


}
