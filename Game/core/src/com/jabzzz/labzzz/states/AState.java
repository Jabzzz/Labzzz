package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.controller.MainGame;

/**
 * Created by Useless on 01.04.2017.
 */

public abstract class AState
{


    protected OrthographicCamera theCam;

    protected SpriteBatch theBatch;


    public AState()
    {

        theBatch = new SpriteBatch();
        theCam = new OrthographicCamera();
        theCam.setToOrtho(false, MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
    }



    public abstract void render();
    public abstract void update();
    public abstract void dispose();
    public abstract void input(int x, int y);

}
