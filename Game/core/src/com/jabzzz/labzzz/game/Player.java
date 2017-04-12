package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Input;
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

    private Vector2 acceleration = new Vector2();
    private Vector2 velocity = new Vector2();

    private float maxVelocity = 0f;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private OrthographicCamera theCam;

    public Player(float x, float y, OrthographicCamera theCam)
    {
        System.out.println("Player - Constructor");

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
        //MaxSpeed
        Vector2 totalAcceleration = new Vector2(Vector2.Zero);
        totalAcceleration.add(this.acceleration);

        //Friction
        Vector2 frictionAcceleration = new Vector2(Vector2.Zero);
        frictionAcceleration.sub(new Vector2(velocity).setLength(0.05f));
        if(frictionAcceleration.len2() > velocity.len2())
        {
            frictionAcceleration = new Vector2(Vector2.Zero);
            frictionAcceleration.sub(velocity);
        }
        totalAcceleration.add(frictionAcceleration);


        position.add(velocity);
        if(new Vector2(velocity).add(totalAcceleration).len() <= maxVelocity)
        {
            velocity.add(totalAcceleration);
        }
        else
        {
            velocity.add(frictionAcceleration);
        }
    }

    public void input(Speed speed, Direction dir, InputSystem is)
    {
        switch (is)
        {
            case CLICK:
            default:
                this.acceleration = getAccelerationFrom(speed, dir, is);
                break;
            case CLICKSTOP:
                this.acceleration.set(0,0);
                break;
        }
    }
    private Vector2 getAccelerationFrom(Speed speed, Direction dir, InputSystem is)
    {
        if(is == InputSystem.CLICKSTOP)
            return new Vector2(0,0);

        float accelerationAbs = 0.4f;
        switch (speed)
        {
            case SLOW:
                maxVelocity = 1f;
                break;
            case NORMAL:
                maxVelocity = 2f;
                break;
            case FAST:
                maxVelocity = 4f;
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
