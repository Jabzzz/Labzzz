package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jabzzz.labzzz.game.MainGame;

/**
 * Created by Useless on 01.04.2017.
 */

public class MenuState extends State
{

    private Texture backgroundTexture = null;
    private BitmapFont textFont = null;


    public MenuState()
    {
        backgroundTexture = new Texture("menu_background.jpg");

        textFont = new BitmapFont(Gdx.files.internal("textfont.fnt"), false);
        textFont.setColor(Color.WHITE);

    }




    @Override
    public void render()
    {
        theBatch.begin();


        theBatch.draw(backgroundTexture, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);

        textFont.draw(theBatch, "The Menu", ((MainGame.WIDTH - 150) / 2) + (int) (20 * (Math.cos(System.currentTimeMillis() * 0.01))), (int) ((MainGame.HEIGHT - 200) + 50 * Math.sin(System.currentTimeMillis() * 0.001)));


        theBatch.end();
    }

    @Override
    public void update()
    {

    }

    @Override
    public void dispose()
    {
        theBatch.dispose();
    }
}
