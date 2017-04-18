package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;

/**
 * Created by Useless on 18.04.2017.
 */

public class ShopState extends AState{

    private MainGame theMainGame = null;

    private Texture shopGuy = null;

    public ShopState(MainGame theMainGame) {
        this.theMainGame = theMainGame;

        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //init Textures
        theBatch = new SpriteBatch();
        shopGuy = new Texture("shop.png");
    }

    @Override
    public void render() {
        theCam.update();
        theBatch.setProjectionMatrix(theCam.combined);

        theBatch.begin();

        //draw Background
        theBatch.draw(shopGuy, 0, MainGame.HEIGHT/4*3, MainGame.WIDTH, MainGame.HEIGHT/4);

        theBatch.end();
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {
        theBatch.dispose();
    }

    @Override
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y) {

    }
}
