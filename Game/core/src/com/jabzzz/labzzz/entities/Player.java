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

public class Player extends ACharacter
{
    private ArrayList<InputData> inputDataList = new ArrayList<InputData>();

    private OrthographicCamera theCam;

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

    public void input(Speed speed, Direction dir, InputSystem is)
    {
        pushInputData(new InputData(speed, dir, is));
    }



    public synchronized void pushInputData(InputData inputData)
    {
        inputDataList.add(inputData);
    }



    public Rectangle getCollisionRectangle()
    {
        return new Rectangle(getDecenteredPosition().x, getDecenteredPosition().y, collisionWidth, collisionHeight);
    }


}
