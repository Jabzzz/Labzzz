package com.jabzzz.labzzz.game;

import java.util.Random;

/**
 * Created by Stefan on 12.04.2017.
 */

public class LabyrinthBuilder extends Labyrinth {

    private int deletedBlocks;

    public LabyrinthBuilder(int mapLength)
    {
        super(mapLength);
        deletedBlocks = 0;
    }

    public void resetLab()
    {
        for(int row = 0; row < map.length; row++) {
            for (int colum = 0; colum < map[row].length; colum++) {
                map[row][colum] = 0;
            }
        }
    }

    private int rateCurrentMap()
    {
        int rating = 0;

        int row, colum;
        for(row = 0; row < map.length; row++) {
            for (colum = 0; colum < map[row].length; colum++) {
                if(map[row][colum] == 0)
                {
                    int amountFreeBlocks = getAmountFreeBlocksNextTo(row, colum);
                    switch (amountFreeBlocks)
                    {
                        case 0:
                            rating -=4;
                            break;
                        case 1:
                            rating -=1;
                            break;
                        case 2:
                            rating +=4;
                            break;
                        case 3:
                            rating += 3;
                            break;
                        case 4:
                            rating += 1;
                            break;
                    }
                }
            }
        }

        return rating;
    }
    private int getAmountFreeBlocksNextTo(int row, int colum)
    {
        int amountBlocks = 0;
        if(getMapBlockAt(row-1,colum) == 0)
            amountBlocks++;
        if(getMapBlockAt(row+1,colum) == 0)
            amountBlocks++;
        if(getMapBlockAt(row,colum-1) == 0)
            amountBlocks++;
        if(getMapBlockAt(row,colum+1) == 0)
            amountBlocks++;
        return amountBlocks;
    }

    public void createMap(int maxSteps)
    {
        Random random = new Random();
        deletedBlocks = 0;
        int amountFreeBlocks;

        for(int i = 0; i < maxSteps; i++)
        {
            amountFreeBlocks = map.length * map[0].length - deletedBlocks;
            int nextBlock = random.nextInt(amountFreeBlocks);

            int[][] oldMap = map.clone();
            for(int k = 0; k < oldMap.length; k++)
            {
                oldMap[k] = map[k].clone();
            }

            int oldRating = rateCurrentMap();
            countFreeBlocksAndSet(nextBlock);
            int newRating = rateCurrentMap();
            System.out.println("Map Rating:" + oldRating  + ":" + newRating);

            if(oldRating > newRating) {
                map = oldMap;
            }
        }
    }

    private void countFreeBlocksAndSet(int index)
    {
        for(int row = 0; row < map.length; row++) {
            for (int colum = 0; colum < map[row].length; colum++) {
                if(map[row][colum] == 0)
                {
                    if(index == 0)
                    {
                        map[row][colum] = 1;
                        return;
                    }
                    else
                    {
                        index--;
                    }
                }
            }
        }
    }
}
