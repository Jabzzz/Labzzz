package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by samvell on 05.04.17.
 */

public class Button extends com.jabzzz.labzzz.entities.AEntity
{
    private Texture btnTexture = null;
    private SpriteBatch theBatch = null;
    private Rectangle recBtn = null;


    public Button(Texture btnTexture, int x, int y)
    {
        this.btnTexture = btnTexture;
        position.x = x;
        position.y = y;

        theBatch = new SpriteBatch();

        recBtn = new Rectangle(x,y,btnTexture.getWidth(), btnTexture.getHeight());
    }



    @Override
    public void render(SpriteBatch theBatch)
    {
        theBatch.draw(btnTexture, position.x, position.y);
    }

    public boolean isClicked(float x, float y)
    {
        if(recBtn.contains(x,y))
            return true;
        else return false;
    }
}
