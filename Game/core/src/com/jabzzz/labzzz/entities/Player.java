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

public class Player extends AEntity
{
    private static final int ANIMATION_TIME = 700;

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

    private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    private Sprite currentSprite = new Sprite();
    private String dir = "d";
    private int loop_current = 1;
    private int loop_from = 1;
    private int loop_to = 4;
    private long lastAnimation = 0;


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
            sprites.put(region.name, sprite);
        }
        lastAnimation = System.currentTimeMillis();
        currentSprite = sprites.get("d1");

        //Set collision
        pictureWidth = sprites.get("d1").getRegionWidth();
        pictureHeight = sprites.get("d1").getRegionHeight();
        collisionWidth = (int) (pictureWidth * 0.5f);
        collisionHeight = (int) (pictureHeight * 0.4f);
    }

    public void render(SpriteBatch theBatch)
    {
        theBatch.begin();

        currentSprite.setPosition(position.x - (pictureWidth / 2), position.y - (pictureHeight / 2));
        currentSprite.draw(theBatch);

        theBatch.end();
    }

    public void update(float delta)
    {
        //Set animation
        animate();

        //Calculate Input
        calcInputData();

        //Set movement
        movement(delta);
    }

    private void animate()
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
                    System.out.println("loop: " + loop_current);
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
                currentSprite = sprites.get(dir + loop_from);
                loop_current = loop_from;

        }

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

        addToPosition(velocity.scl(delta + 1));

        if(new Vector2(velocity).add(totalAcceleration).len() <= maxVelocity)
        {
            velocity.add(totalAcceleration.scl(delta + 1));
        }
        else
        {
            velocity.add(frictionAcceleration.scl(delta + 1));
        }
    }

    private float getAnimationTime()
    {
        return ANIMATION_TIME / (velocity.len() + 1);
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
        return new Rectangle(getCollisionPosition().x, getCollisionPosition().y, collisionWidth, collisionHeight);
    }

    public Vector2 getCollisionPosition()
    {
        return new Vector2(getDecenteredPosition().x + (pictureWidth * 0.25f), getDecenteredPosition().y + (pictureHeight * 0.1f));
    }


}
