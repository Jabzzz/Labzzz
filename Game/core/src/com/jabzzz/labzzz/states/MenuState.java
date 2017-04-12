package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private Button exitButton = null;

    private Rectangle recPlay = null;
    private Rectangle recExit = null;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();


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

        //init Play-Button
        playButton = new Button(btnPlayTexture,
                (MainGame.WIDTH - btnPlayTexture.getWidth()) / 2,
                (MainGame.HEIGHT - btnPlayTexture.getHeight()) / 4);

        //init Exit-Button
        exitButton = new Button(btnExitTexture,
                (MainGame.WIDTH - btnExitTexture.getWidth()) / 2,
                (int)(MainGame.HEIGHT - 1.5*(btnExitTexture.getHeight()) / 4));

        recPlay = new Rectangle(playButton.getPosition().x, playButton.getPosition().x, btnPlayTexture.getWidth(), btnPlayTexture.getHeight());
        recExit = new Rectangle(exitButton.getPosition().x, exitButton.getPosition().x, btnExitTexture.getWidth(), btnExitTexture.getHeight());

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

        /*shapeRenderer.setProjectionMatrix(theCam.combined);

        shapeRenderer.setAutoShapeType(true);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(recPlay.getX(), recPlay.getY(), recPlay.getWidth(), recPlay.getHeight());
        shapeRenderer.end();*/
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
        if(is==InputSystem.CLICK)
        {
            //System.out.println(recPlay.contains(x, y) + "   " + recPlay.getX() + "   " + recPlay.getY() + "   " + recPlay.getWidth() + "   " + recPlay.getHeight() + "  x " + x + "   y  " + y);
            if (recPlay.contains(x, y))
                theMainGame.popStack();
        }
    }

}
