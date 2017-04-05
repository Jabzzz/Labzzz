package com.jabzzz.labzzz.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jabzzz.labzzz.controller.*;
import com.jabzzz.labzzz.game.*;
import com.jabzzz.labzzz.controller.MainGame;

/**
 * Created by Stefan on 04.04.2017.
 */

public class GameState extends AState
{
    private MainGame theMainGame = null;

    private Player player = null;
    private Labyrinth labyrinth = null;

    public GameState(MainGame theMainGame)
    {
        this.theMainGame = theMainGame;


        theBatch = new SpriteBatch();
        theCam = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);
        theCam.position.set(theCam.viewportWidth / 2f, theCam.viewportHeight / 2f, 0);

        player = new Player(75f, 75f, theCam);
        labyrinth = new Labyrinth();
    }

    public void render()
    {

        player.render(theBatch);
        labyrinth.render(theCam);


        //theBatch.end();
    }

    public void update()
    {

    }

    public void dispose()
    {
        theBatch.dispose();
    }

    public void input(double abs, Enum dir)
    {

    }
}
