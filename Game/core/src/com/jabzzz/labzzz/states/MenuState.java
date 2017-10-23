package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.entities.Button;

import java.awt.SystemTray;

/**
 * Created by Useless on 01.04.2017.
 */

public class MenuState extends AState implements InputProcessor
{


    private MainGame theMainGame = null;

    private Stage myStage = null;

    private Table myTable = null;
    private Table textTable = null;

    private int xText = 0, yText = 0;
    private Texture backgroundTexture = null;
    private BitmapFont textFont = null;
    private TextField menuText = null;

    private Button playButton = null;
    private Button exitButton = null;
    private Button shopButton = null;

    private ScalingViewport theViewPort = null;


    public MenuState(final MainGame theMainGame)
    {
        this.theMainGame = theMainGame;

        //init Stage and set InputProcessor
        myStage = new Stage();
        Gdx.input.setInputProcessor(myStage);
        
        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //theViewPort = new ScalingViewport(Scaling.stretch, MainGame.WIDTH * 0.5f, MainGame.HEIGHT * 0.5f, theCam);//900, 900f * ((float) theMainGame.HEIGHT / theMainGame.WIDTH), theCam);
        //theViewPort.apply();


        //theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);



        //init Textures
        theBatch = new SpriteBatch();
        backgroundTexture = new Texture("menustate/menu_background.jpg");

        Texture btnPlayTexture = new Texture(Gdx.files.internal("menustate/playbtn.png"));
        Texture btnExitTexture = new Texture(Gdx.files.internal("menustate/exitbtn.png"));
        Texture btnShopTexture = new Texture(Gdx.files.internal("menustate/shopbtn.png"));

        //init Play-Button
        playButton = new Button(btnPlayTexture,
                (MainGame.WIDTH - btnPlayTexture.getWidth()) / 2,
                (MainGame.HEIGHT + 5*btnPlayTexture.getHeight()) / 4);


        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                theMainGame.pushGameStack();
            }
        });

        //init Exit-Button

        exitButton = new Button(btnExitTexture,
                (MainGame.WIDTH - btnExitTexture.getWidth()) / 2,
                (MainGame.HEIGHT - 5*btnExitTexture.getHeight()) / 4);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                theMainGame.dispose();
            }
        });

        //init Shop-Button

        shopButton = new Button(btnShopTexture,
                (MainGame.WIDTH - btnShopTexture.getWidth()) / 2,
                (MainGame.HEIGHT) / 4);

        shopButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                theMainGame.pushShopStack();
            }
        });

        //init Textfont

        textFont = new BitmapFont(Gdx.files.internal("textfonts/textfont.fnt"), false);
        textFont.setColor(Color.WHITE);
        textFont.getData().setScale(1.5f);

        TextField.TextFieldStyle textStyle = new TextField.TextFieldStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        menuText = new TextField("The Menu", textStyle);
        textTable = new Table();
        textTable.add(menuText).minWidth(180);

        //init Table and add Objects
        myTable = new Table();

        myTable.background(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));

        myTable.add(textTable).expand(false, true).minHeight(200).minWidth(200);
        myTable.row().fill();
        myTable.add(playButton).space(10);
        myTable.row().fill();
        myTable.add(shopButton).space(10);
        myTable.row().fill();
        myTable.add(exitButton).space(10).padBottom(110);
        myTable.row().fill();
        myTable.debug();
        myTable.setFillParent(true);

        //Stage add Objects
        //myStage.addActor(menuText);
        myStage.addActor(myTable);
    }




    @Override
    public void render()
    {
        theCam.update();

        menuText.setHeight(yText);

        myStage.draw();
    }

    private Vector2 backPosition = new Vector2(0,0);

    @Override
    public void update(float delta)
    {
        yText = (int) ( 50 * Math.sin(System.currentTimeMillis() * 0.001));
    }

    @Override
    public void peeked()
    {

    }

    @Override
    public void dispose()
    {
        textFont.dispose();
        myStage.dispose();
    }

    @Override
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y) {
    }

}
