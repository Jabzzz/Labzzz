package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.InputData;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.Labyrinth;

import java.util.ArrayList;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Player extends AEntity
{

    private Vector2 acceleration = new Vector2();
    private Vector2 velocity = new Vector2();

    private ArrayList<InputData> inputDataList = new ArrayList<InputData>();

    private float maxVelocity = 0f;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private OrthographicCamera theCam;


    private int pictureWidth = 20;
    private int pictureHeight = 20;
    private int collisionWidth = 20;
    private int collisionHeight = 20;

    public Player(Vector2 position, Labyrinth labyrinth, OrthographicCamera theCam)
    {
        super(labyrinth);

        this.theCam = theCam;
        this.position = new Vector2(position);

        shapeRenderer.setAutoShapeType(true);

        System.out.println("Player - Constructor");
    }

    public void render(SpriteBatch theBatch)
    {
        shapeRenderer.setProjectionMatrix(theCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(getPosition().x, getPosition().y, 10);
        shapeRenderer.rect(getCollisionRectangle().x, getCollisionRectangle().y, getCollisionRectangle().width, getCollisionRectangle().height);

        shapeRenderer.end();
    }

    public void update(float delta)
    {
        //Calculate Input
        calcInputData();

        //Set movement
        movement(delta);

        //Set animation
        animate();
    }

    private void animate()
    {

    }


    private void movement(float delta)
    {
        //MaxSpeed
        Vector2 totalAcceleration = new Vector2(Vector2.Zero);
        totalAcceleration.add(this.acceleration);

        //Friction
        Vector2 frictionAcceleration = new Vector2(Vector2.Zero);
        frictionAcceleration.sub(new Vector2(velocity).setLength(0.1f));
        if(frictionAcceleration.len2() > velocity.len2())
        {
            frictionAcceleration = new Vector2(Vector2.Zero);
            frictionAcceleration.sub(velocity);
        }
        totalAcceleration.add(frictionAcceleration);

        addToPosition(velocity);

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
        pushInputData(new InputData(speed, dir, is));
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

    public synchronized void pushInputData(InputData inputData)
    {
        inputDataList.add(inputData);
    }

    public synchronized void calcInputData()
    {
        InputData inputData;
        while(!inputDataList.isEmpty())
        {
            inputData = inputDataList.get(0);
            inputDataList.remove(0);
            this.acceleration = getAccelerationFrom(inputData.getSpeed(), inputData.getDirection(), inputData.getInputSystem());
        }

    }

    public Vector2 getVelocity()
    {
        return this.velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public void setAcceleration(Vector2 acceleration)
    {
        this.acceleration = acceleration;
    }

    public Vector2 getAcceleration()
    {
        return this.acceleration;
    }

    public Vector2 getDecenteredPosition()
    {
        return new Vector2(position.x - (pictureWidth / 2), position.y - (pictureHeight / 2));
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position.x + (pictureWidth / 2), position.y + (pictureHeight / 2));
    }

    public void addToPosition(Vector2 velocity)
    {
        position.add(velocity);

        int labWidth = labyrinth.getWidth();
        int labHeight = labyrinth.getHeight();
        while(position.x < 0)
            position.x += labWidth;
        while(position.x > labWidth)
            position.x -= labWidth;
        while(position.y < 0)
            position.y += labHeight;
        while(position.y > labHeight)
            position.y -= labHeight;

    }

    public Rectangle getCollisionRectangle()
    {
        return new Rectangle(getDecenteredPosition().x, getDecenteredPosition().y, collisionWidth, collisionHeight);
    }


}
