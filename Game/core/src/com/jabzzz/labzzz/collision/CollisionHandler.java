package com.jabzzz.labzzz.collision;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.game.Labyrinth;
import com.jabzzz.labzzz.game.Player;

/**
 * Created by samvell on 06.04.17.
 */

public class CollisionHandler
{

    private Player thePlayer = null;
    private Labyrinth theLabyrinth = null;


    public CollisionHandler(Player thePlayer, Labyrinth theLabyrinth)
    {
        this.thePlayer = thePlayer;
        this.theLabyrinth = theLabyrinth;
    }


    public void update()
    {
        collisionPlayerAndEnvironment();
    }

    private void collisionPlayerAndEnvironment()
    {
        Vector2 velocity = thePlayer.getVelocity();
        Vector2 dPosition = thePlayer.getDecenteredPosition().add(velocity);
        Rectangle collisionRect = thePlayer.getCollisionRectangle();

        collisionRect.setPosition(dPosition);

        int dx = (int) velocity.x;
        int dy = (int) velocity.y;

        Vector2 pBottomLeft, pBottomRight, pTopLeft, pTopRight;

        pBottomLeft = dPosition;
        pBottomRight = new Vector2(collisionRect.getX() + collisionRect.getWidth(), collisionRect.getY());
        pTopLeft = new Vector2(collisionRect.getX(), collisionRect.getY() + collisionRect.getHeight());
        pTopRight = new Vector2(pBottomRight.x, pTopLeft.y);

        int collide = 0;

        if(dx > 0)
        {
            //Move Right
            collide += theLabyrinth.getMapBlockAtPosition(pBottomRight);
            collide += theLabyrinth.getMapBlockAtPosition(pTopRight);
            System.out.print("Right");
        }
        else if (dx < 0)
        {
            //Move Left
            collide += theLabyrinth.getMapBlockAtPosition(pBottomLeft);
            collide += theLabyrinth.getMapBlockAtPosition(pTopLeft);
            System.out.print("Left");
        }

        if(dy > 0)
        {
            //Move up
            collide += theLabyrinth.getMapBlockAtPosition(pTopLeft);
            collide += theLabyrinth.getMapBlockAtPosition(pTopRight);
            System.out.print("Up");
        }
        else if(dy < 0)
        {
            //Move Down
            collide += theLabyrinth.getMapBlockAtPosition(pBottomRight);
            collide += theLabyrinth.getMapBlockAtPosition(pBottomLeft);
            System.out.print("Down");
        }

        if(collide > 0)
        {
            thePlayer.setVelocity(new Vector2(0,0));
            thePlayer.setAcceleration(new Vector2(0,0));
            System.out.println(" - Collide!");

        }


    }

}
