package com.jabzzz.labzzz.collision;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.game.Labyrinth;
import com.jabzzz.labzzz.entities.Player;

/**
 * Created by samvell on 06.04.17.
 */

public class CollisionHandler
{

    private Player thePlayer = null;
    private Labyrinth theLabyrinth = null;

    private Vector2 lastVelocity = new Vector2();


    public CollisionHandler(Player thePlayer, Labyrinth theLabyrinth)
    {
        this.thePlayer = thePlayer;
        this.theLabyrinth = theLabyrinth;
    }


    public void update()
    {
        //currentPositionCollision();

        nextStepCollisionPvE();
    }

    private void currentPositionCollision()
    {
        Vector2 velocity = new Vector2(lastVelocity);
        Vector2 position = new Vector2(thePlayer.getDecenteredPosition());
        Rectangle collisionRect = new Rectangle(thePlayer.getCollisionRectangle());
        Vector2 collisionPoints[] = getCollisionPoints(collisionRect);

        //Set last velocity
        if(!thePlayer.getVelocity().isZero(0.1f))
            lastVelocity = thePlayer.getVelocity();

        if(collides(collisionPoints))
        {
            velocity.rotate(180);
            velocity.setLength(0.5f);

            for(int i = 0; i < 5; i++)
            {
                collisionRect.setPosition(position.add(velocity));

                collisionPoints = getCollisionPoints(collisionRect);

                boolean collide = false;

                for(int ii = 0; ii < 4; ii++)
                {
                    if(theLabyrinth.getMapBlockAtPosition(collisionPoints[ii]) == 1)
                        collide = true;
                }

                if(!collide)
                    break;

                velocity.add(velocity);
            }
            thePlayer.setVelocity(new Vector2(0,0));
  //          thePlayer.setAcceleration(new Vector2(0,0));
            thePlayer.setPosition(position);
        }
    }

    private boolean collides(Vector2[] collisionPoints)
    {
        for(int i = 0; i < collisionPoints.length; i++)
            if(theLabyrinth.getMapBlockAtPosition(collisionPoints[i]) == 1)
                return true;

        return false;
    }


    private void nextStepCollisionPvE()
    {
        //Get Values
        Vector2 velocity = new Vector2(thePlayer.getVelocity());
        Vector2 dPosition = thePlayer.getDecenteredPosition().add(velocity);
        Rectangle collisionRect = thePlayer.getCollisionRectangle();

        //Calc new Position and Speed-Direction
        collisionRect.setPosition(dPosition);

        //Set Player-Collision-Points
        Vector2 collisionPoints[] = getCollisionPoints(collisionRect);

        //Proof Collision
        int collisionMatrix[][] = getCollisionMatrix(velocity.x, velocity.y, collisionPoints);

        //Set Player-Speed (2-Point Collision)

        boolean onePointCollision = false;
        boolean twoPointCollision = false;

        Vector2 dPoint = new Vector2();

        if(isCollisionUP(collisionMatrix) || isCollisionDOWN(collisionMatrix))
        {
            thePlayer.setVelocity(velocity.set(velocity.x, 0));
            twoPointCollision = true;
        }

        if(isCollisionLEFT(collisionMatrix) || isCollisionRIGHT(collisionMatrix))
        {
            thePlayer.setVelocity(velocity.set(0, velocity.y));
            if(twoPointCollision)
                thePlayer.setVelocity(new Vector2(0, 0));
        }

        if(!twoPointCollision)
        {
            if(collisionMatrix[0][0] == 1)
            {
                dPoint = collisionPoints[2];
                onePointCollision = true;
            }
            else if(collisionMatrix[0][1] == 1)
            {
                dPoint = collisionPoints[3];
                onePointCollision = true;
            }
            else if(collisionMatrix[1][0] == 1)
            {
                dPoint = collisionPoints[0];
                onePointCollision = true;
            }
            else if(collisionMatrix[1][1] == 1)
            {
                dPoint = collisionPoints[1];
                onePointCollision = true;
            }
        }

        if(onePointCollision && !twoPointCollision)
        {
            dPoint.add(new Vector2(velocity.x, -1f * velocity.y));

            if(theLabyrinth.getMapBlockAtPosition(dPoint) == 1)
            {
                thePlayer.setVelocity(new Vector2(0,velocity.y));
            }
            else
            {
                thePlayer.setVelocity(new Vector2(velocity.x,0));
            }
        }
    }

    private Vector2[] getCollisionPoints(Rectangle collisionRect)
    {
        Vector2 points[] =
                            {
                                //BottomLeft
                                new Vector2(collisionRect.getX(), collisionRect.getY()),
                                //BottomRight
                                new Vector2(collisionRect.getX() + collisionRect.getWidth(), collisionRect.getY()),
                                //TopLeft
                                new Vector2(collisionRect.getX(), collisionRect.getY() + collisionRect.getHeight()),
                                //TopRight
                                new Vector2(collisionRect.getX() + collisionRect.getWidth(), collisionRect.getY() + collisionRect.getHeight())
                            };

        return points;
    }

    private int[][] getCollisionMatrix(float dx, float dy, Vector2[] collisionPoints)
    {
        int collisionMatrix[][] = {{0,0},{0,0}};
        /*
            collisionMatrix[][]
            [0][0]   [0][1]

            [1][0]   [1][1]
         */

        if(dx > 0)
        {
            //Move Right
            collisionMatrix[1][1] = theLabyrinth.getMapBlockAtPosition(collisionPoints[1]);
            collisionMatrix[0][1] = theLabyrinth.getMapBlockAtPosition(collisionPoints[3]);
        }
        else if (dx < 0)
        {
            //Move Left
            collisionMatrix[1][0] = theLabyrinth.getMapBlockAtPosition(collisionPoints[0]);
            collisionMatrix[0][0] = theLabyrinth.getMapBlockAtPosition(collisionPoints[2]);
        }

        if(dy > 0)
        {
            //Move up
            collisionMatrix[0][0] = theLabyrinth.getMapBlockAtPosition(collisionPoints[2]);
            collisionMatrix[0][1] = theLabyrinth.getMapBlockAtPosition(collisionPoints[3]);
        }
        else if(dy < 0)
        {
            //Move Down
            collisionMatrix[1][1] = theLabyrinth.getMapBlockAtPosition(collisionPoints[1]);
            collisionMatrix[1][0] = theLabyrinth.getMapBlockAtPosition(collisionPoints[0]);
        }

        return collisionMatrix;
    }


    private boolean isCollisionUP(int collisionMatrix[][])
    {
        return (collisionMatrix[0][0] == 1 && collisionMatrix[0][1] == 1);// &&
                //collisionMatrix[1][0] == 0 && collisionMatrix[1][1] == 0);
    }

    private boolean isCollisionDOWN(int collisionMatrix[][])
    {
        return (collisionMatrix[1][0] == 1 && collisionMatrix[1][1] == 1);// &&
                //collisionMatrix[0][0] == 0 && collisionMatrix[0][1] == 0);
    }

    private boolean isCollisionLEFT(int collisionMatrix[][])
    {
        return (collisionMatrix[0][0] == 1 && collisionMatrix[1][0] == 1);// &&
                //collisionMatrix[0][1] == 0 && collisionMatrix[1][1] == 0);
    }

    private boolean isCollisionRIGHT(int collisionMatrix[][])
    {
        return (collisionMatrix[0][1] == 1  && collisionMatrix[1][1] == 1);// &&
                //collisionMatrix[0][0] == 0 && collisionMatrix[1][0] == 0);
    }

}
