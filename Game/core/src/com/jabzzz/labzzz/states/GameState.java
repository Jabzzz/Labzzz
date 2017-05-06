package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.jabzzz.labzzz.collision.CollisionHandler;
import com.jabzzz.labzzz.controller.*;
import com.jabzzz.labzzz.entities.Enemy;
import com.jabzzz.labzzz.entities.Player;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.*;
import com.jabzzz.labzzz.controller.MainGame;

/**
 * Created by Stefan on 04.04.2017.
 */

public class GameState extends AState
{
    private MainGame theMainGame = null;

    private InputHandler theInputHandler = null;
    private CollisionHandler theCollisionHandler = null;
    public static ShapeRenderer shapeRenderer = null;

    private Player player = null;
    private Enemy enemy = null;
    private Labyrinth labyrinth = null;


    //private StretchViewport theViewPort = null;
    private ScalingViewport theViewPort = null;

    public GameState(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;
        this.theInputHandler = new InputHandler(this);

        //Gdx.input.setInputProcessor(theInputHandler);

        theBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        //theCam = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);

        //theViewPort = new ScalingViewport(Scaling.stretch, theMainGame.WIDTH * 0.5f, theMainGame.HEIGHT * 0.5f, theCam);//900, 900f * ((float) theMainGame.HEIGHT / theMainGame.WIDTH), theCam);
        //theViewPort.apply();


        LabyrinthBuilder lb = new LabyrinthBuilder(30);
        lb.resetLab();
        lb.createMap(2000);
        labyrinth = lb;

        Vector2 pos = lb.getRandomSpawnPosition();
        player = new Player(pos, labyrinth, theCam);
        enemy = new Enemy(pos, labyrinth, player);
        theCollisionHandler = new CollisionHandler(player, labyrinth);
    }

    public void render()
    {
        //Set up Camera and SpriteBatch
        theCam.position.set(player.getPosition().x + player.getVelocity().x * Gdx.graphics.getRawDeltaTime() * 40f,
                                player.getPosition().y + player.getVelocity().y * Gdx.graphics.getRawDeltaTime() * 40, 0);
        theCam.update();
        theBatch.setProjectionMatrix(theCam.combined);
        shapeRenderer.setProjectionMatrix(theCam.combined);

        labyrinth.render(theBatch, theCam);

        //Render the Game
        theInputHandler.render(theCam);

        player.render(theBatch, theCam);
        enemy.render(theBatch, theCam);
    }

    public void update(float delta)
    {

        //Collision
        theCollisionHandler.update();

        labyrinth.update();

        player.update(delta);
        enemy.update(delta);
        //theMainGame.getInputHandler()
    }

    @Override
    public void peeked()
    {
        Gdx.input.setInputProcessor(theInputHandler);
    }


    public void dispose()
    {
        theBatch.dispose();
        shapeRenderer.dispose();
    }

    public void input(Speed speed, Direction dir, InputSystem is, float x, float y)
    {
        player.input(speed, dir, is);
    }
}
