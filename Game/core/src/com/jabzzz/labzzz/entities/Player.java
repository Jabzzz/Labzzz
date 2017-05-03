package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jabzzz.labzzz.controller.InputData;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.Labyrinth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Player extends ACharacter
{

    private static final float SCALE = 0.7f;

    private ArrayList<InputData> inputDataList = new ArrayList<InputData>();

    private OrthographicCamera theCam;

    public Player(Vector2 position, Labyrinth labyrinth, OrthographicCamera theCam)
    {
        super(labyrinth);

        this.theCam = theCam;
        this.position = new Vector2(position);

        //Set Texture
        TextureAtlas atlas = new TextureAtlas("gamestate/texturepacker/sprites.txt");
        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();

        for(TextureAtlas.AtlasRegion region : regions)
        {
            Sprite sprite = atlas.createSprite(region.name);
            sprite.setScale(SCALE);
            sprites.put(region.name, sprite);
        }
        lastAnimation = System.currentTimeMillis();
        currentSprite = sprites.get("d1");

        //Set collision
        pictureWidth = sprites.get("d1").getRegionWidth();
        pictureHeight = sprites.get("d1").getRegionHeight();
        collisionWidth = (int) (pictureWidth * 0.31f);
        collisionHeight = (int) (pictureHeight * 0.4f);
    }

    @Override
    public void render(SpriteBatch theBatch, OrthographicCamera theCam)
    {
        theBatch.begin();

        currentSprite.setPosition(position.x - (pictureWidth / 2), position.y - (pictureHeight / 2));
        currentSprite.draw(theBatch);

        theBatch.end();
    }

    @Override
    public void update(float delta)
    {
        //Calculate Input
        calcInputData();

        //Set movement
        movement(delta);

        //Set animation
        animate();
    }

    @Override
    public void animate()
    {
        if(velocity.len() > 0.01f)
        {
            float angle = velocity.angle();

            if(angle < 45 || angle > 315)
            {
                //right
                dir = "r";
            }
            else if(angle < 135)
            {
                //up
                dir = "u";
            }
            else if(angle < 225)
            {
                //left
                dir = "l";
            }
            else
            {
                //down
                dir = "d";
            }

            //Set next Image
            if((System.currentTimeMillis() - lastAnimation) > getAnimationTime())
            {
                loop_current++;
                lastAnimation = System.currentTimeMillis();

                if(loop_current > loop_to || loop_current < loop_from)
                {
                    loop_current = loop_from;
                }
            }
        }
        else if(System.currentTimeMillis() - lastAnimation > getAnimationTime())
        {
            loop_current = loop_from;
        }

        switch(loop_current)
        {
            case 1:
                currentSprite = sprites.get(dir + "1");
                break;
            case 2:
                currentSprite = sprites.get(dir + "2");
                break;
            case 3:
                currentSprite = sprites.get(dir + "1");
                break;
            case 4:
                currentSprite = sprites.get(dir + "3");
                break;
            default:
                loop_current = loop_from;
                currentSprite = sprites.get(dir + "1");

        }
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

    public Vector2 getDecenteredPosition()
    {
        return new Vector2(position.x - (pictureWidth / 2), position.y - (pictureHeight / 2));
    }


    public Rectangle getCollisionRectangle()
    {
        return new Rectangle(getCollisionPosition().x, getCollisionPosition().y, collisionWidth, collisionHeight);
    }

    public Vector2 getCollisionPosition()
    {
        return new Vector2(getDecenteredPosition().x + (pictureWidth * 0.35f), getDecenteredPosition().y + (pictureHeight * 0.2f));
    }


}
