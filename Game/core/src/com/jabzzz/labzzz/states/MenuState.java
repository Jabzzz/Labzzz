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

    private int xText = 0, yText = 0;
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


        textFont.draw(theBatch, "The Menu", xText, yText);


        theBatch.end();
    }

    @Override
    public void update()
    {
        xText = (int) (((MainGame.WIDTH - 150) / 2) + (20 * (Math.cos(System.currentTimeMillis() * 0.01))));
        yText = (int) ((MainGame.HEIGHT - 200) + 50 * Math.sin(System.currentTimeMillis() * 0.001));
    }

    @Override
    public void dispose()
    {
        theBatch.dispose();
    }

    @Override
    public void input(int x, int y)
    {

    }

}
