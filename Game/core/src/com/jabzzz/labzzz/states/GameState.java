package com.jabzzz.labzzz.states;

import com.jabzzz.labzzz.game.*;

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

    }

    public void update()
    {
        theBatch.begin();



        theBatch.end();
    }

    public void dispose()
    {
        theBatch.dispose();
    }

    public void input(int x, int y)
    {

    }
}
