package com.jabzzz.labzzz.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Player extends AEntity {

    private Vector2 acceleration;
    private Vector2 velocity;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private OrthographicCamera theCam;

    public Player(float x, float y, OrthographicCamera theCam)
    {
        position.set(x,y);
        this.theCam = theCam;
    }

    public void render(SpriteBatch theBatch)
    {
        theCam.position.set(getPosition(), 0);
        theCam.update();
        shapeRenderer.setProjectionMatrix(theCam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);

        shapeRenderer.circle(getPosition().x, getPosition().y, 10);

        shapeRenderer.end();
    }

    public void update()
    {
        position.add(velocity);
        velocity.add(acceleration);
        acceleration.set(0,0);

    }

    public void input(Speed speed, Direction dir)
    {
        if(speed != Speed.NONE)
        {
            this.acceleration = getAccelerationFrom(speed, dir);
        }
    }
    private Vector2 getAccelerationFrom(Speed speed, Direction dir)
    {
        float accelerationAbs = 0;
        switch (speed)
        {
            case SLOW:
                accelerationAbs = 0.5f;
                break;
            case NORMAL:
                accelerationAbs = 1.0f;
                break;
            case FAST:
                accelerationAbs = 2.0f;
                break;
        }
        switch (dir)
        {
            case UPRIGHT:
                return new Vector2(1,1).setLength(accelerationAbs);
            case UP:
                return new Vector2(0,1).setLength(accelerationAbs);
            case UPLEFT:
                return new Vector2(-1,1).setLength(accelerationAbs);
            case LEFT:
                return new Vector2(-1,0).setLength(accelerationAbs);
            case DOWNLEFT:
                return new Vector2(-1,-1).setLength(accelerationAbs);
            case DOWN:
                return new Vector2(0,-1).setLength(accelerationAbs);
            case DOWNRIGHT:
                return new Vector2(1,-1).setLength(accelerationAbs);
            case RIGHT:
                return new Vector2(1,0).setLength(accelerationAbs);
        }
        return new Vector2(0,0);
    }
}
