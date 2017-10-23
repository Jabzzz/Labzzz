package com.jabzzz.labzzz.ai_skills;

import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.environment.Labyrinth;

import java.util.Random;

/**
 * Created by Stefan on 06.05.2017.
 */

public class RandomWalk extends FollowTargetSkill {

    private enum WalkState {WALKING, ON_TARGET}
    private enum BlockType {LEFT, RIGHT, ABOVE, BELOW}

    private WalkState walkstate;
    private int lastWalkBlock = -1;

    private float accuracyTarget = 30;

    public RandomWalk(Labyrinth labyrinth, WallDetection wallDetection, Vector2 positionAi)
    {
        super(labyrinth, positionAi, wallDetection);

        setTarget(new Vector2(position));
    }

    public void reset()
    {
        lastWalkBlock = -1;
        walkstate = WalkState.ON_TARGET;
    }

    public void updateTarget()
    {
        if(getTarget().dst(position) < accuracyTarget)
            walkstate = WalkState.ON_TARGET;

        if(walkstate == WalkState.ON_TARGET) {
            switch (getNewRandomTargetBlock()) {
                case 0:
                    lastWalkBlock = 2;
                    setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position), labyrinth.getColumnMapBlocksAtPosition(position) + 1));
                    break;
                case 1:
                    lastWalkBlock = 3;
                    setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position) + 1, labyrinth.getColumnMapBlocksAtPosition(position)));
                    break;
                case 2:
                    lastWalkBlock = 0;
                    setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position), labyrinth.getColumnMapBlocksAtPosition(position) - 1));
                    break;
                case 3:
                    lastWalkBlock = 1;
                    setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position) - 1, labyrinth.getColumnMapBlocksAtPosition(position)));
                    break;
            }
            walkstate = WalkState.WALKING;
        }

        speed = Speed.SLOW;
    }

    private int getNewRandomTargetBlock()
    {
        java.util.List<Integer> noWallBlocks = wallDetection.getNoWallBlocksWithout(lastWalkBlock);
        if(noWallBlocks.size() != 0)
            return noWallBlocks.get((new Random()).nextInt(noWallBlocks.size()));
        else
            return lastWalkBlock;
    }
}
