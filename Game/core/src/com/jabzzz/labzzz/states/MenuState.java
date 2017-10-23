package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;

/**
 * Created by Useless on 01.04.2017.
 */

public class MenuState extends AState implements InputProcessor
{


    private MainGame theMainGame = null;

    private Stage myStage = null;

    private Table myTable = null;
    private Table statusTable = null;
    private Table sceneTable = null;
    private Table topTable = null;
    private Table bottomTable = null;

    private int xText = 0, yText = 0;
    private Texture backgroundTexture = null;
    private BitmapFont textFont = null;
    private TextField menuText = null;

    private TextButton playButton = null;
    private TextButton exitButton = null;
    private TextButton shopButton = null;
    private TextButton settingsButton = null;
    private TextButton multiplayerButton = null;

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


        Drawable btnTop = new NinePatchDrawable(new NinePatch (processNinePatchFile("menustate/btn_top.9.png")));
        Drawable btnBottom = new NinePatchDrawable(new NinePatch (processNinePatchFile("menustate/btn_bottom.9.png")));
        Drawable background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("menustate/background.png"))));

        //init Textfont

        textFont = new BitmapFont(Gdx.files.internal("textfonts/textfont.fnt"), false);
        textFont.setColor(Color.WHITE);
        textFont.getData().setScale(1.5f);

        TextButton.TextButtonStyle textButtonStyleTop = new TextButton.TextButtonStyle();
        textButtonStyleTop.font = textFont;
        textButtonStyleTop.fontColor = Color.WHITE;
        textButtonStyleTop.up = btnTop;

        TextButton.TextButtonStyle textButtonStyleBottom = new TextButton.TextButtonStyle();
        textButtonStyleBottom.font = textFont;
        textButtonStyleBottom.fontColor = Color.WHITE;
        textButtonStyleBottom.up = btnBottom;

       // menuText = new TextField("The Menu", textStyle);

        //init Play-Button
        playButton = new TextButton("Play",textButtonStyleBottom);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                theMainGame.pushGameStack();
            }
        });

        //init Exit-Button

        exitButton = new TextButton("Exit", textButtonStyleTop);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //init Shop-Button

        shopButton = new TextButton("Shop",textButtonStyleBottom);

        shopButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                theMainGame.pushShopStack();
            }
        });

        //init Multiplayer-Button

        multiplayerButton = new TextButton("Multiplayer",textButtonStyleBottom);

        multiplayerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //theMainGame.pushShopStack();
            }
        });

        //init Settings-Button

        settingsButton = new TextButton("Settings", textButtonStyleTop);

        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //theMainGame.pushShopStack();
            }
        });

        //init statusTable

        statusTable = new Table();
        statusTable.background(btnTop);
        //statusTable.debug();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = textFont;
        labelStyle.fontColor = Color.WHITE;

        statusTable.row().expand().pad(10).fill();
        statusTable.add(new Label("RedDragon",labelStyle)).prefSize(Value.percentWidth(.30f, statusTable),Value.percentHeight(.30f, statusTable));
        statusTable.add(new Label("9999 x",labelStyle));
        Texture ingameMoney = new Texture(Gdx.files.internal("menustate/ingame_money.png"));
        statusTable.add(new Image(ingameMoney));
        statusTable.row().expand().pad(0,10,10,10).fill();
        statusTable.add(new Label("Lvl: over 9000",labelStyle));
        statusTable.add(new Label("9999 x",labelStyle));
        Texture realMoney = new Texture(Gdx.files.internal("menustate/ingame_money.png"));
        statusTable.add(new Image(ingameMoney));

        //init topTable

        topTable = new Table();
        topTable.add(settingsButton);
        topTable.add(statusTable).expand().fill();
        topTable.add(exitButton);

        //init bottomTable

        bottomTable = new Table();
        bottomTable.add(playButton).fillY().prefWidth(MainGame.WIDTH/5*2);
        bottomTable.add(shopButton);
        bottomTable.add(multiplayerButton).prefWidth(MainGame.WIDTH/5*2);

        //init sceneTable
        sceneTable = new Table();
        sceneTable.background(background);

        //init Table and add Objects
        myTable = new Table();

        //myTable.background(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));

        //myTable.setHeight(Gdx.graphics.getHeight());
        //myTable.setWidth(Gdx.graphics.getWidth());
        myTable.setFillParent(true);

        myTable.row();
        myTable.add(topTable).fill();
        myTable.row();
        myTable.add(sceneTable).minHeight(MainGame.HEIGHT/2).expand().fill();
        myTable.row();
        myTable.add(bottomTable).fill();
        //myTable.debug();

        //Stage add Objects
        //myStage.addActor(menuText);

        myStage.addActor(myTable);
        System.out.println(myTable.getParent()+", "+myTable.getWidth()+", " +myTable.getHeight());
    }


    private static NinePatch processNinePatchFile(String fname) {
        final Texture t = new Texture(Gdx.files.internal(fname));
        final int width = t.getWidth() - 2;
        final int height = t.getHeight() - 2;
        return new NinePatch(new TextureRegion(t, 1, 1, width, height), 3, 3, 3, 3);
    }


    @Override
    public void render()
    {
        theCam.update();

        //menuText.setHeight(yText);

        myStage.draw();
    }

    private Vector2 backPosition = new Vector2(0,0);

    @Override
    public void update(float delta)
    {
        /*yText = (int) ( 50 * Math.sin(System.currentTimeMillis() * 0.001));
        System.out.println(yText);*/
    }

    @Override
    public void peeked()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y) {
    }

}
