package com.jabzzz.labzzz.game;

import java.util.Random;

/**
 * Created by Stefan on 12.04.2017.
 */

public class LabyrinthBuilder extends Labyrinth
{

    /*
		Textures - Numbering

		2 -> 19 (Ground textures, no collision)
		20 -> 49 (Wall Textures, collisable)

	 */

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

        //makeGraphicsMap();
    }


    public void makeGraphicsMap()
    {

        for(int row = 0; row < map.length; row++)
        {
            for(int col = 0; col < map.length; col++)
            {
                //if ground
                if(map[row][col]==0)
                {
                    if(row==0)
                        if(map[map.length][col]==1)
                            map[row][col]=2;    //check last row
                    else if(map[row-1][col]==1)
                        map[row][col]=2;
                }

                //if wall
                if(map[row][col]==0) {

                    checkNormal(row,col);



                }
            }


        }

    }

    private void checkNormal(int row, int col)
    {
        if((checkTop(row,col)&&checkBottom(row,col)))
        {
            if (checkLeft(row, col))
            {
                if(checkRight(row, col))
                {
                    checkDiagonals(row, col);
                }
                //? 1 e
                //1 x 0
                //? 1 e
                else
                {
                    if(checkTopLeft(row,col))
                    {
                        if(checkBottomLeft(row,col))
                            map[row][col]=42;
                        else
                            map[row][col]=50;
                    }
                    else if(checkBottomLeft(row,col))
                        map[row][col]=49;
                }
            }
            //? 1 ?
            //0 x ?
            //? 1 ?
            else
            {
                if(checkRight(row,col))
                {
                    if(checkTopRight(row,col))
                    {
                        if(checkBottomRight(row,col))
                            map[row][col]=41;
                        else
                            map[row][col]=48;
                    }
                    else
                        if(checkBottomRight(row,col))
                            map[row][col]=47;
                }
                else
                    map[row][col]=26;
            }
        }
        else if((checkLeft(row,col)&&checkRight(row,col)))
        {
            if(checkTop(row, col))
            {
                if(checkBottom(row, col))
                {
                    checkDiagonals(row, col);
                }
                //? 1 ?
                //1 x 1
                //e 0 e
                else
                {
                    if(checkTopLeft(row,col))
                    {
                        if(checkTopRight(row,col))
                            map[row][col]=40;
                        else
                            map[row][col]=46;
                    }
                    else
                    {
                        if(checkTopRight(row, col))
                            map[row][col]=52;
                    }
                }
            }
            //e 0 e
            //1 x 1
            //? ? ?
            else
                if(checkBottom(row,col))
                {
                    if(checkBottomLeft(row,col))
                    {
                        if(checkBottomRight(row,col))
                            map[row][col]=43;
                        else
                            map[row][col]=51;
                    }
                    else
                        if(checkBottomRight(row,col))
                            map[row][col]=45;
                }
                map[row][col]=25;
        }
        else if(checkBottom(row,col)&&checkRight(row,col))
        {
            //e ? e
            //? x 1
            //e 1 e
            if(checkTop(row,col))
                map[row][col]=35;
            if(checkLeft(row,col))
                map[row][col]=32;
            //e 0 e
            //0 x 1
            //e 1 ?
            if(checkBottomRight(row,col))
                map[row][col]=38;
            else
                map[row][col]=30;
        }
        else if(checkBottom(row,col)&&checkLeft(row,col))
        {
            //e 0 e
            //1 x 0
            //? 1 e
            if(checkBottomLeft(row,col))
                map[row][col]=36;
            else
                map[row][col]=28;
        }
        else if(checkTop(row,col)&&checkLeft(row,col))
        {
            //e 1 e
            //1 x ?
            //e ? e
            if(checkBottom(row,col))
                map[row][col]=33;
            if(checkTop(row,col))
                map[row][col]=34;
            //? 1 e
            //1 x 0
            //e 0 e
            if(checkTopLeft(row,col))
                map[row][col]=37;
            else
                map[row][col]=29;
        }
        else if(checkTop(row,col)&&checkRight(row,col))
        {
            //e 1 ?
            //0 x 1
            //e 0 e
            if(checkTopRight(row,col))
                map[row][col]=39;
            else
                map[row][col]=31;
        }
        else if(checkBottom(row,col))
            map[row][col]=21;
        else if(checkLeft(row,col))
            map[row][col]=22;
        else if (checkRight(row,col))
            map[row][col]=23;
        else if(checkTop(row,col))
            map[row][col]=24;
        else
            map[row][col]=20;
    }

    private void checkDiagonals(int row, int col) {
        //? 1 ?
        //1 x 1
        //? 1 ?
        if (checkBottomLeft(row, col))
        {
            if (checkBottomRight(row, col))
            {
                //? 1 ?
                //1 x 1
                //1 1 1
                if (checkTopRight(row, col))
                {
                    if (checkTopLeft(row, col))
                        map[row][col] = 44;
                    else
                        map[row][col] = 55;
                }
                if (checkTopLeft(row, col))
                    map[row][col] = 54;
                else
                    map[row][col] = 61;
            }
            //? 1 ?
            //1 x 1
            //1 1 0
            else if (checkTopRight(row, col))
            {
                if (checkTopLeft(row, col))
                    map[row][col] = 53;
                else
                    map[row][col] = 57;
            }
            if (checkTopLeft(row, col))
                map[row][col] = 59;
        }
        //? 1 ?
        //1 x 1
        //0 1 0
        else {
            if (checkTopLeft(row, col))
            {
                if (checkTopRight(row, col))
                    map[row][col]=62;
                else
                    map[row][col]=63;
            }
            else
                if (checkTopRight(row, col))
                    map[row][col]=64;
                else map[row][col]=27;
        }

        if (checkTopLeft(row, col))
        {
            if (checkTopRight(row, col))
            {
                //1 1 1
                //1 x 1
                //? 1 ?
                if(checkBottomRight(col, row))
                    if(!checkBottomLeft(col,row))
                        map[row][col]=56;
            }
        }
        else
        {
            if (checkTopRight(row, col))
            {
                //0 1 1
                //1 x 1
                //? 1 ?
                if(checkBottomLeft(row,col))
                {
                    if(checkBottomRight(row,col))
                    map[row][col] = 58;
                }
                else
                    if(checkBottomRight(row, col))
                        map[row][col]=60;
            }
            else
            {
                //0 1 0
                //1 x 1
                //? 1 ?
                if(checkBottomLeft(row,col))
                {
                    if(checkBottomRight(row,col))
                        map[row][col]=67;
                    else
                        map[row][col]=66;
                }
                else
                {
                    if(checkBottomRight(row,col))
                    {
                        map[row][col]=56;
                    }

                }
            }
        }
    }

    private boolean checkTop(int row, int col)
    {
        if(map[row-1][col]==1)
            return true;
        return false;
    }

    private boolean checkBottom(int row, int col)
    {
        if(map[row+1][col]==1)
            return true;
        return false;
    }

    private boolean checkLeft(int row, int col)
    {
        if(map[row][col-1]==1)
            return true;
        return false;
    }

    private boolean checkRight(int row, int col)
    {
        if(map[row][col+1]==1)
            return true;
        return false;
    }

    private boolean checkTopLeft(int row, int col)
    {
        if(map[row-1][col-1]==1)
            return true;
        return false;
    }

    private boolean checkTopRight(int row, int col)
    {
        if(map[row-1][col+1]==1)
            return true;
        return false;
    }

    private boolean checkBottomLeft(int row, int col)
    {
        if(map[row+1][col-1]==1)
            return true;
        return false;
    }

    private boolean checkBottomRight(int row, int col)
    {
        if(map[row+1][col+1]==1)
            return true;
        return false;
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
