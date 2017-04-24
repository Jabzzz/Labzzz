package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.InputData;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.Labyrinth;

import java.util.HashMap;

/**
 * Created by Stefan on 24.04.2017.
 */

public abstract class ACharacter extends AEntity
{

    private static final int ANIMATION_TIME = 700;


    protected Vector2 acceleration = new Vector2();
    protected Vector2 velocity = new Vector2();
    protected float maxVelocity = 0f;

    protected HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    protected Sprite currentSprite = new Sprite();
    protected String dir = "d";
    protected int loop_current = 1;
    protected int loop_from = 1;
    protected int loop_to = 4;
    protected long lastAnimation = 0;

    protected int pictureWidth = 20;
    protected int pictureHeight = 20;
    protected int collisionWidth = 20;
    protected int collisionHeight = 20;

    protected ShapeRenderer shapeRenderer = new ShapeRenderer();
    protected Texture texture;

    public ACharacter(Labyrinth l)
    {
        super(l);
    }

    public abstract void render(SpriteBatch sb);
    public void update(float delta)
    {
        //Calculate Input
        calcInputData();

        //Set movement
        movement(delta);

        //Set animation
        animate();
    }

    protected void animate()
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

    protected void movement(float delta)
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

    private float getAnimationTime()
    {
        return ANIMATION_TIME / (velocity.len() + 1);
    }

    public abstract void calcInputData();


    protected Vector2 getAccelerationFrom(Speed speed, Direction dir, InputSystem is)
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

}
