package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by samvell on 05.04.17.
 */

public class Button extends com.jabzzz.labzzz.entities.AEntity
{
    private Texture btnTexture = null;
    private SpriteBatch theBatch = null;


    public Button(Texture btnTexture, int x, int y)
    {
        this.btnTexture = btnTexture;
        position.x = x;
        position.y = y;

        theBatch = new SpriteBatch();
    }



    @Override
    public void render(SpriteBatch theBatch)
    {
        theBatch.draw(btnTexture, position.x, position.y);
    }
}
