package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.game.MainGame;

/**
 * Created by Useless on 01.04.2017.
 */

public abstract class State
{


    protected OrthographicCamera theCam;


    public State()
    {

        theCam = new OrthographicCamera();
        theCam.setToOrtho(false, MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
    }



    public abstract void render(SpriteBatch theBatch);
    public abstract void update();




}
