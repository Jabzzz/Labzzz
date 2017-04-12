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

        int collisionMatrix[][] = {{0,0},{0,0}};
        /*
            collisionMatrix[][]
            [0][0]   [0][1]

            [1][0]   [1][1]
         */

        if(dx > 0)
        {
            //Move Right
            collisionMatrix[1][1] = theLabyrinth.getMapBlockAtPosition(pBottomRight);
            collisionMatrix[0][1] = theLabyrinth.getMapBlockAtPosition(pTopRight);

            //thePlayer.setVelocity(velocity.set(-velocity.x, velocity.y));
        }
        else if (dx < 0)
        {
            //Move Left
            collisionMatrix[1][0] = theLabyrinth.getMapBlockAtPosition(pBottomLeft);
            collisionMatrix[0][0] = theLabyrinth.getMapBlockAtPosition(pTopLeft);

            //thePlayer.setVelocity(velocity.set(-velocity.x, velocity.y));
        }

        if(dy > 0)
        {
            //Move up
            collisionMatrix[0][0] = theLabyrinth.getMapBlockAtPosition(pTopLeft);
            collisionMatrix[0][1] = theLabyrinth.getMapBlockAtPosition(pTopRight);

            //thePlayer.setVelocity(velocity.set(velocity.x, -velocity.y));
        }
        else if(dy < 0)
        {
            //Move Down
            collisionMatrix[1][1] = theLabyrinth.getMapBlockAtPosition(pBottomRight);
            collisionMatrix[1][0] = theLabyrinth.getMapBlockAtPosition(pBottomLeft);

            //thePlayer.setVelocity(velocity.set(velocity.x, -velocity.y));
        }


    }

}
