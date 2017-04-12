package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jabzzz.labzzz.controller.*;
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


    private Player player = null;
    private Labyrinth labyrinth = null;



    public GameState(MainGame theMainGame, InputHandler theInputHandler)
    {
        this.theMainGame = theMainGame;
        this.theInputHandler = theInputHandler;

        theBatch = new SpriteBatch();
        theCam = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);

        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        player = new Player(75f, 75f, theCam);

        LabyrinthBuilder lb = new LabyrinthBuilder();
        lb.resetLab();
        lb.createMap(2000);
        labyrinth = lb;
    }

    public void render()
    {

        theInputHandler.render(theCam);

        player.render(theBatch);
        labyrinth.render(theCam);

    }

    public void update()
    {

        //Collision

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
