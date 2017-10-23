package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    private Stage myStage = null;

    private Table myTable = null;

    private Image shopGuy = null;
    private Button menuBtn = null;
    private ScrollPane myScrollPane = null;

    public ShopState(final MainGame theMainGame) {
        this.theMainGame = theMainGame;

        //init Stage and set InputProcessor
        myStage = new Stage();
        Gdx.input.setInputProcessor(myStage);

        //init Camera
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        //init Textures
        theBatch = new SpriteBatch();
        shopGuy = new Image(new Texture(Gdx.files.internal("shopstate/shop.png")));

        Texture btnMenuTexture = new Texture(Gdx.files.internal("shopstate/menubtn.png"));
        menuBtn = new Button(btnMenuTexture);
        menuBtn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                theMainGame.pushMenuStack();
            }
        });

        //init Scrollpane
        /*myScrollPane = new ScrollPane(menuBtn);
        myScrollPane.setPosition(0, 50);
        myScrollPane.setSize(600, 620);*/

        //init Table and add Objects
        myTable = new Table();
        myTable.add(shopGuy).top().expand().fillX();
        myTable.add(menuBtn).top().minWidth(menuBtn.getMinWidth());
        myTable.row().fill();
        myTable.debug();
        myTable.setFillParent(true);

        //Stage add Objects
        myStage.addActor(myTable);
    }

    @Override
    public void render() {
        theCam.update();
        /*theBatch.setProjectionMatrix(theCam.combined);

        theBatch.begin();

        //draw shopGuy
        theBatch.draw(shopGuy, 0, MainGame.HEIGHT/4*3, MainGame.WIDTH, MainGame.HEIGHT/4);

        //draw menubtn

        menuBtn.render(theBatch);

        //myScrollPane.draw(theBatch, 0);

        theBatch.end();*/

        myStage.draw();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void peeked()
    {

    }

    @Override
    public void dispose() {
        theBatch.dispose();
    }

    @Override
    public void input(Speed speed, Direction dir, InputSystem is, float x, float y) {
    }
}
