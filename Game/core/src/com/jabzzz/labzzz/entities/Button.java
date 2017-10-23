package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by samvell on 05.04.17.
 */

public class Button extends com.badlogic.gdx.scenes.scene2d.ui.Button
{
    private Texture btnTexture = null;
    private SpriteBatch theBatch = null;
    private Rectangle recBtn = null;
    protected Vector2 position = null;


    public Button(Texture btnTexture)
    {
        super(new TextureRegionDrawable(new TextureRegion(btnTexture)));

        /*this.btnTexture = btnTexture;

        position = new Vector2(x, y);
        position.x = x;
        position.y = y;

        theBatch = new SpriteBatch();

        recBtn = new Rectangle(x,y,btnTexture.getWidth(), btnTexture.getHeight());*/
    }



    /*public void render(SpriteBatch theBatch)
    {
        //theBatch.draw(btnTexture, position.x, position.y);
    }

    public boolean isClicked(float x, float y)
    {
        return recBtn.contains(x, y);
    }

    public Vector2 getPosition()
    {
        return this.position;
    }
    */
}
