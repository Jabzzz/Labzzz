package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private Player player = null;
    private Enemy enemy = null;
    private Labyrinth labyrinth = null;



    public GameState(MainGame theMainGame, InputHandler theInputHandler)
    {
        this.theMainGame = theMainGame;
        this.theInputHandler = theInputHandler;

        theBatch = new SpriteBatch();
        theCam = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);

        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        LabyrinthBuilder lb = new LabyrinthBuilder(30);
        lb.resetLab();
        lb.createMap(2000);
        labyrinth = lb;

        player = new Player(lb.getRandomSpawnPosition(), theCam);
        enemy = new Enemy(lb.getRandomSpawnPosition());
        theCollisionHandler = new CollisionHandler(player, labyrinth);


    }

    public void render()
    {

        theCam.update();
        theBatch.setProjectionMatrix(theCam.combined);
        theInputHandler.render(theCam);
        labyrinth.render(theBatch, theCam);

        player.render(theBatch);
        enemy.render(theBatch);


    }

    public void update()
    {

        //Collision
        theCollisionHandler.update();


        player.update();
        //theMainGame.getInputHandler()
    }

    public void dispose()
    {
        theBatch.dispose();
    }

    public void input(Speed speed, Direction dir, InputSystem is, float x, float y)
    {
        player.input(speed, dir, is);
    }
}
