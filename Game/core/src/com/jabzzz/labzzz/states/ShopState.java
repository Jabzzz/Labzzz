package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.entities.Button;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;

/**
 * Created by Useless on 18.04.2017.
 */

public class ShopState extends AState{

    private MainGame theMainGame = null;

    private Texture shopGuy = null;
    private Button menuBtn = null;

    public ShopState(MainGame theMainGame) {
        this.theMainGame = theMainGame;

        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //init Textures
        theBatch = new SpriteBatch();
        shopGuy = new Texture(Gdx.files.internal("shop.png"));

        Texture btnMenuTexture = new Texture(Gdx.files.internal("menubtn.png"));
        menuBtn = new Button(btnMenuTexture, MainGame.WIDTH-btnMenuTexture.getWidth(), MainGame.HEIGHT-btnMenuTexture.getHeight());
    }

    @Override
    public void render() {
        theCam.update();
        theBatch.setProjectionMatrix(theCam.combined);

        theBatch.begin();

        //draw shopGuy
        theBatch.draw(shopGuy, 0, MainGame.HEIGHT/4*3, MainGame.WIDTH, MainGame.HEIGHT/4);

        //draw menubtn

        menuBtn.render(theBatch);

        theBatch.end();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        theBatch.dispose();
    }

    @Override
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y) {
        if(menuBtn.isClicked(x,y)==true)
            theMainGame.pushMenuStack();
    }
}
