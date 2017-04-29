package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.InputData;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.Labyrinth;
import com.jabzzz.labzzz.states.GameState;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import sun.applet.Main;

/**
 * Created by Stefan on 18.04.2017.
 */

public class Enemy extends ACharacter {


    private int roleAI;
    private Vector2 target;
    private int status = 0;
    private boolean[] wallDetection;
    private boolean[] blockDetection;
    private int currentRow = -1;
    private int currentColumn = -1;

    private int colStart = 0;
    private int colEnd = 0;
    private int rowStart = 0;
    private int rowEnd = 0;
    private int halfDisplayX = (int) (MainGame.WIDTH / 2);
    private int halfDisplayY = (int) (MainGame.HEIGHT / 2);

    public Enemy(Vector2 position, Labyrinth labyrinth)
    {
        super(labyrinth);

        this.position = new Vector2(position);
        this.target  = new Vector2(position);

        texture = new Texture("gamestate/entities/enemy.gif");

        wallDetection = new boolean[] {false, false, false, false};
        blockDetection = new boolean[] {false, false, false, false};
    }

    @Override
    public void render(SpriteBatch theBatch)
    {
        theBatch.begin();

        theBatch.draw(texture, getPosition().x - 25, getPosition().y - 25, 50, 50);

        theBatch.end();
        GameState.shapeRenderer.setAutoShapeType(true);
        GameState.shapeRenderer.begin();

        GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        GameState.shapeRenderer.circle(target.x, target.y, 10);
        GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        GameState.shapeRenderer.circle(position.x, position.y, 10);

        if(blockDetection[0])
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        else
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        GameState.shapeRenderer.rect(position.x - 5 + 20, position.y - 5, 10, 10);

        if(blockDetection[1])
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        else
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        GameState.shapeRenderer.rect(position.x - 5, position.y - 5 + 20, 10, 10);

        if(blockDetection[2])
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        else
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        GameState.shapeRenderer.rect(position.x - 5 - 20, position.y - 5, 10, 10);

        if(blockDetection[3])
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        else
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        GameState.shapeRenderer.rect(position.x - 5, position.y - 25, 10, 10);

        GameState.shapeRenderer.end();
    }

    @Override
    public void update(float delta)
    {
        //Calculate Input
        calcInputData();

        //Set movement
        movement(delta);
    }

    @Override
    public void calcInputData()
    {
        updateDetection();
        //System.out.println(Arrays.toString(wallDetection) + " " + Arrays.toString(blockDetection));

        //System.out.print(blockDetection[2] + " " + blockDetection[1]);
        //System.out.print(" " + blockDetection[3]);
        //System.out.println(" " + blockDetection[0]);

        InputData id = calcControl();

        acceleration = getAccelerationFrom(id.getSpeed(), id.getDirection(), id.getInputSystem());

    }

    @Override
    public void animate()
    {

    }


    private void updateDetection()
    {
        wallDetection[0] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(0, MainGame.BLOCK_SIZE / 4)) > 19;
        wallDetection[1] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(MainGame.BLOCK_SIZE / 4, 0)) > 19;
        wallDetection[2] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(0,- MainGame.BLOCK_SIZE / 4)) > 19;
        wallDetection[3] = labyrinth.getMapBlockAtPosition(new Vector2(position).add(- MainGame.BLOCK_SIZE / 4, 0)) > 19;

        int colum = labyrinth.getColumnMapBlocksAtPosition(this.position);
        int row = labyrinth.getRowMapBlocksAtPosition(this.position);

        blockDetection[0] = labyrinth.getMapBlockAt(row, colum + 1) > 19;
        blockDetection[1] = labyrinth.getMapBlockAt(row + 1, colum) > 19;
        blockDetection[2] = labyrinth.getMapBlockAt(row, colum - 1) > 19;
        blockDetection[3] = labyrinth.getMapBlockAt(row - 1, colum) > 19;
    }
    private InputData calcControl()
    {
        if(target.dst2(position) < 40)
        if(target.dst2(position) < 30)
            status = 0;
        switch (status)
        {
            case 0:
                switch (getNewRandomAcc())
                {
                    case 0:
                        setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position), labyrinth.getColumnMapBlocksAtPosition(position)+1));
                        break;
                    case 1:
                        setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position)+1, labyrinth.getColumnMapBlocksAtPosition(position)));
                        break;
                    case 2:
                        setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position), labyrinth.getColumnMapBlocksAtPosition(position)-1));
                        break;
                    case 3:
                        setTarget(labyrinth.getMidpointFromBlock(labyrinth.getRowMapBlocksAtPosition(position)-1, labyrinth.getColumnMapBlocksAtPosition(position)));
                        break;
                }
                status = 1;
                break;
        }

        Vector2 vec = new Vector2(target).sub(position);
        vec.setLength(1f);
        Direction d = Direction.NONE;

        Vector2 wallDetAcceleration = new Vector2(Vector2.Zero);
        float wda_length = 1;
        if(wallDetection[0])
            wallDetAcceleration.add(-wda_length, 0);
        if(wallDetection[1])
            wallDetAcceleration.add(0, -wda_length);
        if(wallDetection[2])
            wallDetAcceleration.add(wda_length, 0);
        if(wallDetection[3])
            wallDetAcceleration.add(0, wda_length);

        vec.add(wallDetAcceleration);

        if (337.5 <= vec.angle() || vec.angle() < 22.5)
            d = Direction.RIGHT;
        else if (vec.angle() < 67.5)
            d = Direction.UPRIGHT;
        else if (vec.angle() < 112.5)
            d = Direction.UP;
        else if (vec.angle() < 157.5)
            d = Direction.UPLEFT;
        else if (vec.angle() < 202.5)
            d = Direction.LEFT;
        else if (vec.angle() < 247.5)
            d = Direction.DOWNLEFT;
        else if (vec.angle() < 292.5)
            d = Direction.DOWN;
        else if (vec.angle() < 337.5)
            d = Direction.DOWNRIGHT;

        return new InputData(Speed.SLOW, d, InputSystem.CLICK);
    }


    private int getNewRandomAcc()
    {
        int possibilities = new Random().nextInt(4);
        for(int i = possibilities+3; i > 0; i--)
        {
            if (!blockDetection[i%4])
            {
                return i%4;
            }
        }
        return -1;
    }

    public void setTarget(Vector2 point)
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
}
