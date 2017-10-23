package com.jabzzz.labzzz.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private Enemy[] enemies = null;
    private Labyrinth labyrinth = null;


    //private StretchViewport theViewPort = null;
    private Viewport theViewPort = null;

    public GameState(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;
        this.theInputHandler = new InputHandler(this);

        //Gdx.input.setInputProcessor(theInputHandler);

        theBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        theCam = new OrthographicCamera();

        theViewPort = new FillViewport(MainGame.WIDTH * 0.5f, MainGame.HEIGHT * 0.5f, theCam);//(Scaling.stretch, theMainGame.WIDTH * 0.5f, theMainGame.HEIGHT * 0.5f, theCam);
        theViewPort.setScreenSize(MainGame.WIDTH, MainGame.HEIGHT);
        theViewPort.apply();


        LabyrinthBuilder lb = new LabyrinthBuilder(30);
        lb.resetLab();
        lb.createMap(2000);
        labyrinth = lb;

        Vector2 pos = lb.getRandomSpawnPosition();
        player = new Player(pos, labyrinth, theCam);
        enemies = new Enemy[] {
                new Enemy(pos, labyrinth, player)
                /*new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player),
                new Enemy(lb.getRandomSpawnPosition(), labyrinth, player)*/
        };

        theCollisionHandler = new CollisionHandler(player, labyrinth);

        System.out.println("<GameState>");
        System.out.println("\tGame-Size: " + MainGame.WIDTH + "x" + MainGame.HEIGHT);
        System.out.println("\tViewPort: " + theViewPort.getClass().getSimpleName());

        System.out.println("</GameState>");
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
        for(Enemy enemy : enemies)
            enemy.render(theBatch, theCam);


    }

    public void update(float delta)
    {

        //Collision
        theCollisionHandler.update();

        labyrinth.update();

        player.update(delta);
        for(Enemy enemy : enemies)
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
