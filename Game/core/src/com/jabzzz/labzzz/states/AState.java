package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;

/**
 * Created by Useless on 01.04.2017.
 */

public abstract class AState
{


    protected OrthographicCamera theCam;

    protected SpriteBatch theBatch;


    public AState()
    {
        System.out.println("State - Constructor");
        theCam = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);

    }



    public abstract void render();
    public abstract void update(float delta);
    public abstract void dispose();
    public abstract void input(Speed speed, Direction dir, InputSystem is, float x, float y);

}
