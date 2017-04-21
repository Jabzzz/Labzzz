package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.entities.Button;

/**
 * Created by Useless on 01.04.2017.
 */

public class MenuState extends AState
{


    private MainGame theMainGame = null;

    private int xText = 0, yText = 0;
    private Texture backgroundTexture = null;
    private BitmapFont textFont = null;

    private Button playButton = null;
    private Button exitButton = null;
    private Button shopButton = null;


    public MenuState(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;
        
        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //init Textures
        theBatch = new SpriteBatch();
        backgroundTexture = new Texture("menu_background.jpg");

        Texture btnPlayTexture = new Texture(Gdx.files.internal("playbtn.png"));
        Texture btnExitTexture = new Texture(Gdx.files.internal("exitbtn.png"));
        Texture btnShopTexture = new Texture(Gdx.files.internal("shopbtn.png"));

        //init Play-Button
        playButton = new Button(btnPlayTexture,
                (MainGame.WIDTH - btnPlayTexture.getWidth()) / 2,
                (MainGame.HEIGHT + 5*btnPlayTexture.getHeight()) / 4);

        //init Exit-Button

        exitButton = new Button(btnExitTexture,
                (MainGame.WIDTH - btnExitTexture.getWidth()) / 2,
                (MainGame.HEIGHT - 5*btnExitTexture.getHeight()) / 4);

        //init Shop-Button

        shopButton = new Button(btnShopTexture,
                (MainGame.WIDTH - btnShopTexture.getWidth()) / 2,
                (MainGame.HEIGHT) / 4);

        //init Textfont

        textFont = new BitmapFont(Gdx.files.internal("textfont.fnt"), false);
        textFont.setColor(Color.WHITE);
        textFont.getData().setScale(1.5f);

    }




    @Override
    public void render()
    {
        theCam.update();
        theBatch.setProjectionMatrix(theCam.combined);

        theBatch.begin();

        //draw Background
        theBatch.draw(backgroundTexture, backPosition.x, backPosition.y, MainGame.WIDTH, MainGame.HEIGHT);
        //draw Text
        textFont.draw(theBatch, "The Menu", xText, yText);
        //draw Button
        playButton.render(theBatch);
        exitButton.render(theBatch);
        shopButton.render(theBatch);

        theBatch.end();
    }

    private Vector2 backPosition = new Vector2(0,0);

    @Override
    public void update(float delta)
    {
        yText = (int) ((MainGame.HEIGHT - 200) + 50 * Math.sin(System.currentTimeMillis() * 0.001));

        xText = MainGame.WIDTH / 3;
    }

    @Override
    public void dispose()
    {
        theBatch.dispose();
    }

    @Override
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y)
    {
        if(is==InputSystem.CLICK)
        {
            //System.out.println(recPlay.contains(x, y) + "   " + recPlay.getX() + "   " + recPlay.getY() + "   " + recPlay.getWidth() + "   " + recPlay.getHeight() + "  x " + x + "   y  " + y);
            if (playButton.isClicked(x,y)==true)
                theMainGame.pushGameStack();
                //theMainGame.popStack();

            if (exitButton.isClicked(x,y)==true)
                theMainGame.dispose();

            if (shopButton.isClicked(x,y)==true)
                theMainGame.pushShopStack();

        }
    }

}
