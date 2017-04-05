package com.jabzzz.labzzz.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stefan on 04.04.2017.
 */

public abstract class AEntity
{
    protected Vector2 position = null;

    public AEntity()
    {
        position = new Vector2();
    }

<<<<<<< Updated upstream

    public abstract void render(SpriteBatch theBatch);
=======
    public Vector2 getPosition()
    {
        return position;
    }
>>>>>>> Stashed changes

}
