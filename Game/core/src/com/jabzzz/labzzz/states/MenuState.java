package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.Button;

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

    private Rectangle recPlay = null;


    public MenuState(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;
        
        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //init Textures
        theBatch = new SpriteBatch();
        backgroundTexture = new Texture("menu_background.jpg");

        Texture btnTexture = new Texture(Gdx.files.internal("playbtn.png"));

        //init Play-Button
        playButton = new Button(btnTexture,
                (MainGame.WIDTH - btnTexture.getWidth()) / 2,
                (MainGame.HEIGHT - btnTexture.getHeight()) / 4);
        Rectangle recPlay = new Rectangle(MainGame.WIDTH - btnTexture.getWidth() / 2, MainGame.HEIGHT - btnTexture.getHeight() / 4, btnTexture.getWidth(), btnTexture.getHeight());

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

        //draw Background
        theBatch.draw(backgroundTexture, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        //draw Text
        textFont.draw(theBatch, "The Menu", xText, yText);
        //draw Button
        playButton.render(theBatch);

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
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y)
    {
        /*System.out.println(recPlay.contains(x, y));
        if (recPlay.contains(x, y))
            theMainGame.popStack();*/
        theMainGame.popStack();
    }

}
