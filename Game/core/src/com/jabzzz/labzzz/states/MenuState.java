package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.Texture;
import com.jabzzz.labzzz.game.MainGame;

/**
 * Created by Useless on 01.04.2017.
 */

public class MenuState extends State
{

    Texture backgroundTexture = null;


    public MenuState()
    {
        backgroundTexture = new Texture("menu_background.jpg");


    }




    @Override
    public void render()
    {
        theBatch.begin();


        theBatch.draw(backgroundTexture, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);


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
