package com.jabzzz.labzzz.game;

import java.util.Random;

/**
 * Created by Stefan on 12.04.2017.
 */

public class LabyrinthBuilder extends Labyrinth {

    /*
        Textures - Numbering

		2 -> 19 (Ground textures, no collision)
		20 -> 67 (Wall Textures, collisable)

	 */

    private int deletedBlocks;

    public LabyrinthBuilder(int mapLength) {
        super(mapLength);
        deletedBlocks = 0;
    }

    public void resetLab() {
        for (int row = 0; row < map.length; row++) {
            for (int colum = 0; colum < map[row].length; colum++) {
                map[row][colum] = 0;
            }
        }
    }

    private int rateCurrentMap() {
        int rating = 0;

        int row, colum;
        for (row = 0; row < map.length; row++) {
            for (colum = 0; colum < map[row].length; colum++) {
                if (map[row][colum] == 0) {
                    int amountFreeBlocks = getAmountFreeBlocksNextTo(row, colum);
                    switch (amountFreeBlocks) {
                        case 0:
                            rating -= 4;
                            break;
                        case 1:
                            rating += 1;
                            break;
                        case 2:
                            rating += 4;
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

    private int getAmountFreeBlocksNextTo(int row, int colum) {
        int amountBlocks = 0;
        if (getMapBlockAt(row - 1, colum) == 0)
            amountBlocks++;
        if (getMapBlockAt(row + 1, colum) == 0)
            amountBlocks++;
        if (getMapBlockAt(row, colum - 1) == 0)
            amountBlocks++;
        if (getMapBlockAt(row, colum + 1) == 0)
            amountBlocks++;
        return amountBlocks;
    }

    public void createMap(int maxSteps) {
        Random random = new Random();
        deletedBlocks = 0;
        int amountFreeBlocks;

        for (int i = 0; i < maxSteps; i++) {
            amountFreeBlocks = map.length * map[0].length - deletedBlocks;
            int nextBlock = random.nextInt(amountFreeBlocks);

            int[][] oldMap = map.clone();
            for (int k = 0; k < oldMap.length; k++) {
                oldMap[k] = map[k].clone();
            }

            int oldRating = rateCurrentMap();
            countFreeBlocksAndSet(nextBlock);
            int newRating = rateCurrentMap();

            if (oldRating > newRating) {
                map = oldMap;
            }
        }

        //createStaticMap();
        //printMap();

        makeGraphicsMap();
        printMap();
    }

    private void printMap() {
        String mapString = "";
        for (int row = map.length - 1; row >= 0; row--) {
            for (int colum = 0; colum < map[row].length; colum++) {
                if (colum == map[row].length - 1)
                    mapString += getMapBlockAt(row, colum) + "\n";
                else
                    mapString += getMapBlockAt(row, colum) + ", ";
            }
        }
    }

    private void createStaticMap() {
        //for debug only
        int[][] newMap = new int[3][3];
        newMap[2] = new int[]{0, 0, 0};
        newMap[1] = new int[]{0, 1, 0};
        newMap[0] = new int[]{1, 1, 1};
        map = newMap;
    }


    private void makeGraphicsMap() {
        int[][] newMap = map.clone();
        for (int k = 0; k < newMap.length; k++) {
            newMap[k] = map[k].clone();
        }

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                //if ground
                if (map[row][col] == 0) {
                    if (getMapBlockAt(row + 1, col) == 0)
                        newMap[row][col] = 2;
                    else
                        newMap[row][col] = 3;
                }

                //if wall
                if (getMapBlockAt(row, col) == 1) {

                    newMap = checkBlock(row, col, newMap);

                }
            }


        }

        map = newMap;

    }

    private int[][] checkBlock(int r, int c, int[][] newMap) {
        if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 40;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 67;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 63;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 64;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 65;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 66;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 59;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 60;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 61;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 62;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 53;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 54;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 55;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 56;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 57;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 58;
        else if (!cTop(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c))
            newMap[r][c] = 45;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && !cBtm(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 46;
        else if (cTop(r, c) && !cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cLft(r, c))
            newMap[r][c] = 47;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtmRgt(r, c) && cBtm(r, c) && !cLft(r, c))
            newMap[r][c] = 48;
        else if (cTop(r, c) && !cRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 49;
        else if (cTop(r, c) && !cRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 50;
        else if (!cTop(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cBtmLft(r, c) && cLft(r, c))
            newMap[r][c] = 51;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtm(r, c) && cLft(r, c) && !cTopLft(r, c))
            newMap[r][c] = 52;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtm(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 41;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cLft(r, c))
            newMap[r][c] = 42;
        else if (cTop(r, c) && !cRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 43;
        else if (!cTop(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c))
            newMap[r][c] = 44;
        else if (!cTop(r, c) && !cRgt(r, c) && cBtm(r, c) && cBtmLft(r, c) && cLft(r, c))
            newMap[r][c] = 36;
        else if (cTop(r, c) && !cRgt(r, c) && !cBtm(r, c) && cLft(r, c) && cTopLft(r, c))
            newMap[r][c] = 37;
        else if (!cTop(r, c) && cRgt(r, c) && cBtmRgt(r, c) && cBtm(r, c) && !cLft(r, c))
            newMap[r][c] = 38;
        else if (cTop(r, c) && cTopRgt(r, c) && cRgt(r, c) && !cBtm(r, c) && !cLft(r, c))
            newMap[r][c] = 39;
        else if (!cTop(r, c)  && cRgt(r, c)  && cBtm(r, c) && cLft(r, c))
            newMap[r][c] = 32;
        else if (cTop(r, c) &&  !cRgt(r, c) &&  cBtm(r, c)  && cLft(r, c))
            newMap[r][c] = 33;
        else if (cTop(r, c) &&  cRgt(r, c) &&  !cBtm(r, c)  && cLft(r, c))
            newMap[r][c] = 34;
        else if (cTop(r, c) &&  cRgt(r, c) &&  cBtm(r, c)  && !cLft(r, c))
            newMap[r][c] = 35;
        else if (!cTop(r, c) && cRgt(r, c) && !cBtm(r, c) && cLft(r, c))
            newMap[r][c] = 25;
        else if (cTop(r, c) && !cRgt(r, c) && cBtm(r, c) && !cLft(r, c))
            newMap[r][c] = 26;
        else if (cTop(r, c) && cRgt(r, c) && cBtm(r, c) && cLft(r, c))
            newMap[r][c] = 27;
        else if (cBtm(r, c) && cLft(r, c))
            newMap[r][c] = 28;
        else if (cTop(r, c) && cLft(r, c))
            newMap[r][c] = 29;
        else if (cBtm(r, c) && cRgt(r, c))
            newMap[r][c] = 30;
        else if (cTop(r, c) && cRgt(r, c))
            newMap[r][c] = 31;
        else if (cBtm(r, c))
            newMap[r][c] = 21;
        else if (cLft(r, c))
            newMap[r][c] = 22;
        else if (cRgt(r, c))
            newMap[r][c] = 23;
        else if (cTop(r, c))
            newMap[r][c] = 24;
        else
            newMap[r][c] = 20;


        return newMap;
    }

    private boolean cTop(int row, int col) {
        return getMapBlockAt(row + 1, col) == 1;
    }

    private boolean cBtm(int row, int col) {
        return getMapBlockAt(row - 1, col) == 1;
    }

    private boolean cLft(int row, int col) {
        return getMapBlockAt(row, col - 1) == 1;
    }

    private boolean cRgt(int row, int col) {
        return getMapBlockAt(row, col + 1) == 1;
    }

    private boolean cTopLft(int row, int col) {
        return getMapBlockAt(row + 1, col - 1) == 1;
    }

    private boolean cTopRgt(int row, int col) {
        return getMapBlockAt(row + 1, col + 1) == 1;
    }

    private boolean cBtmLft(int row, int col) {
        return getMapBlockAt(row - 1, col - 1) == 1;
    }

    private boolean cBtmRgt(int row, int col) {
        return getMapBlockAt(row - 1, col + 1) == 1;
    }

    private void countFreeBlocksAndSet(int index) {
        for (int row = 0; row < map.length; row++) {
            for (int colum = 0; colum < map[row].length; colum++) {
                if (map[row][colum] == 0) {
                    if (index == 0) {
                        map[row][colum] = 1;
                        return;
                    } else {
                        index--;
                    }
                }
            }
        }
    }
}
