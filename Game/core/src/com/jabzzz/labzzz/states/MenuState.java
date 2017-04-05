package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.controller.MainGame;

/**
 * Created by Useless on 01.04.2017.
 */

public class MenuState extends AState
{


    private MainGame theMainGame = null;

    private int xText = 0, yText = 0;
    private Texture backgroundTexture = null;
    private BitmapFont textFont = null;


    public MenuState(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;

        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //init Textures
        theBatch = new SpriteBatch();

        backgroundTexture = new Texture("menu_background.jpg");

        //init Textfont
        textFont = new BitmapFont(Gdx.files.internal("textfont.fnt"), false);
        textFont.setColor(Color.WHITE);

    }




    @Override
    public void render()
    {
        theCam.update();
        theBatch.setProjectionMatrix(theCam.combined);


        theBatch.begin();

        theBatch.draw(backgroundTexture, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        textFont.draw(theBatch, "The Menu", xText, yText);

        theBatch.end();
    }


    @Override
    public void update()
    {

        yText = (int) ((MainGame.HEIGHT - 200) + 50 * Math.sin(System.currentTimeMillis() * 0.001));

        xText = MainGame.WIDTH / 2 - 50;
    }

    @Override
    public void dispose()
    {
        theBatch.dispose();
    }

    @Override
    public void input(double abs, Enum dir)
    {
        theMainGame.popStack();
    }

}
