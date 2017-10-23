package com.jabzzz.labzzz.ai_skills.follow_target;

import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.ai_skills.WallDetection;
import com.jabzzz.labzzz.controller.InputData;
import com.jabzzz.labzzz.entities.ACharacter;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.environment.Labyrinth;

/**
 * Created by Stefan on 06.05.2017.
 */

public abstract class AFollowTargetSkill {

    private final float accuracyTarget = 5f;
    private final float accuracyTargetSlow = 25f;
    private final float accuracyTargetNormal = 50f;

    private Vector2 target;
    protected Speed speed = Speed.SLOW;

    //Labyrinth
    protected Labyrinth labyrinth;

    protected Vector2 position;

    private boolean[] blockDetection = new boolean[] {false, false, false, false};
    protected WallDetection wallDetection;

    public AFollowTargetSkill(Labyrinth labyrinth, Vector2 position, WallDetection wallDetection)
    {
        this.labyrinth = labyrinth;
        this.position = position;
        this.wallDetection = wallDetection;
    }

    public abstract void updateTarget();

    public abstract void reset();

    public InputData update(ACharacter character)
    {
        updateDetection();

        updateTarget();

        Vector2 pathVector = searchPathThroughBorder(position, target, labyrinth);

        //Break before reaching the target
        if(pathVector.len() < accuracyTarget)
            return new InputData(Speed.NONE, Direction.NONE, InputSystem.CLICKSTOP);
        else if(pathVector.len() < accuracyTargetSlow)
            speed = Speed.SLOW;
        else if(pathVector.len() < accuracyTargetNormal && speed == Speed.FAST)
            speed = Speed.NORMAL;

        pathVector.sub(new Vector2(character.getVelocity()).setLength(character.getVelocity().len() * 10f));
        //pathVector.setLength(1f);
        Direction direction = Direction.NONE;

        //Get Acceleration for WallDetection
        Vector2 wallDetAcceleration = new Vector2(Vector2.Zero);
        float wda_length = 2f;
        if(blockDetection[0])
            wallDetAcceleration.add(-wda_length, 0);
        if(blockDetection[1])
            wallDetAcceleration.add(0, -wda_length);
        if(blockDetection[2])
            wallDetAcceleration.add(wda_length, 0);
        if(blockDetection[3])
            wallDetAcceleration.add(0, wda_length);

        pathVector.add(wallDetAcceleration);

        //Get Direction form Path-Vector
        if (337.5 <= pathVector.angle() || pathVector.angle() < 22.5)
            direction = Direction.RIGHT;
        else if (pathVector.angle() < 67.5)
            direction = Direction.UPRIGHT;
        else if (pathVector.angle() < 112.5)
            direction = Direction.UP;
        else if (pathVector.angle() < 157.5)
            direction = Direction.UPLEFT;
        else if (pathVector.angle() < 202.5)
            direction = Direction.LEFT;
        else if (pathVector.angle() < 247.5)
            direction = Direction.DOWNLEFT;
        else if (pathVector.angle() < 292.5)
            direction = Direction.DOWN;
        else if (pathVector.angle() < 337.5)
            direction = Direction.DOWNRIGHT;

        return new InputData(speed, direction, InputSystem.CLICK);
    }

    private void updateDetection()
    {
        /*blockDetection[0] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(0, MainGame.BLOCK_SIZE / 4)) > 19;
        blockDetection[1] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(MainGame.BLOCK_SIZE / 4, 0)) > 19;
        blockDetection[2] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(0,- MainGame.BLOCK_SIZE / 4)) > 19;
        blockDetection[3] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(- MainGame.BLOCK_SIZE / 4, 0)) > 19;*/
        wallDetection.update(labyrinth, position);
    }

    public static Vector2 searchPathThroughBorder(Vector2 start, Vector2 end, Labyrinth labyrinth)
    {
        Vector2 path = new Vector2(end).sub(start);

        if(path.len() > Math.min(labyrinth.getWidth(), labyrinth.getHeight()) * 0.5f)
        {
            //maybe there is a shorter way
            Vector2 visualEnd = new Vector2(end);

            Vector2 shorterPath;

            float yAchsisChange;
            if(start.y > labyrinth.getHeight() * 0.5f)
                yAchsisChange = labyrinth.getHeight();
            else
                yAchsisChange = - labyrinth.getHeight();

            shorterPath = new Vector2(end).add(0, yAchsisChange).sub(start);

            if(shorterPath.len2() < path.len2())
                visualEnd.add(0, yAchsisChange);

            float xAchsisChange;
            if(start.x > labyrinth.getWidth() * 0.5f)
                xAchsisChange = labyrinth.getWidth();
            else
                xAchsisChange = - labyrinth.getWidth();

            shorterPath = new Vector2(end).add(xAchsisChange,0).sub(start);

            if(shorterPath.len2() < path.len2())
                visualEnd.add(xAchsisChange, 0);

            path = new Vector2(visualEnd).sub(start);
        }

        return path;
    }

    protected void setTarget(Vector2 point)
    {
        target = point;

        int labWidth = labyrinth.getWidth();
        int labHeight = labyrinth.getHeight();
        while(target.x < 0)
            target.x += labWidth;
        while(target.x > labWidth)
            target.x -= labWidth;
        while(target.y < 0)
            target.y += labHeight;
        while(target.y > labHeight)
            target.y -= labHeight;
    }
    public Vector2 getTarget()
    {
        return target;
    }

    public WallDetection getWallDetection()
    {
        return wallDetection;
    }
}
