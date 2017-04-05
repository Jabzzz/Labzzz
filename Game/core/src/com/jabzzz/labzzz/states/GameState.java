package com.jabzzz.labzzz.states;

import com.jabzzz.labzzz.controller.*;
import com.jabzzz.labzzz.game.*;
import com.jabzzz.labzzz.controller.MainGame;

/**
 * Created by Stefan on 04.04.2017.
 */

public class GameState extends AState
{
    private Player player = null;
    private Labyrinth labyrinth = null;

    public GameState()
    {
        super();

        player = new Player();
        labyrinth = new Labyrinth();
    }

    public void render()
    {
        theBatch.begin();

        //theBatch.draw(null, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);

        theBatch.end();
    }

    public void update()
    {
        
    }

    public void dispose()
    {
        theBatch.dispose();
    }

    public void input(int x, int y)
    {

    }
}
