package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.game.Labyrinth;

/**
 * Created by Stefan on 04.04.2017.
 */

public abstract class AEntity
{
    protected Vector2 position = null;
    protected Labyrinth labyrinth = null;

    public AEntity() {
        position = new Vector2();
    }
    public AEntity(Labyrinth labyrinth)
    {
        this();
        this.labyrinth = labyrinth;
    }

    public abstract void render(SpriteBatch theBatch, OrthographicCamera theCam);

    public Vector2 getPosition()
    {
        return position;
    }

}
