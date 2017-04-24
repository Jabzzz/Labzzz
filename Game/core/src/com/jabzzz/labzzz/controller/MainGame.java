package com.jabzzz.labzzz.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.states.GameState;
import com.jabzzz.labzzz.states.MenuState;
import com.jabzzz.labzzz.states.AState;
import com.jabzzz.labzzz.states.ShopState;

import java.util.Stack;

public class MainGame extends ApplicationAdapter
{
	/*
		Textures - Numbering

		0 -> 19 (Ground textures, no collision)
		20 -> 49 (Wall Textures, collisable)

	 */



	public static final String TITLE = "THE Game";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 850;
	public static final int BLOCK_SIZE = 40;


	private GameState theGameState = null;
	private MenuState theMenuState = null;
	private ShopState theShopState = null;
	private UpdateThread theUpdateThread = null;
	private InputHandler theInputHandler = null;

	private Stack<AState> theStateStack = new Stack();


	private int frames = 0;
	private long lastOut = 0;


	
	@Override
	public void create ()
	{
		//init printFPS
		lastOut = System.currentTimeMillis();

		//init InputHandler
		theInputHandler = new InputHandler(this);

		//init GameState
		theStateStack.add(theGameState = new GameState(this, theInputHandler));

		//init ShopState
		theStateStack.add(theShopState = new ShopState(this));

		//init MenuState
		theStateStack.add(theMenuState = new MenuState(this));

		//theUpdateThread = new UpdateThread(this);
		//theUpdateThread.start();

		//theUpdateThread.setUpdatesPerSecond(60);
	}

	private void printFPS()
	{
		frames++;

		if(System.currentTimeMillis() - lastOut > 1000)
		{
			System.out.println("FPS: " + frames);
			frames = 0;
			lastOut = System.currentTimeMillis();
		}
	}


	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);

		theStateStack.peek().update(Gdx.graphics.getRawDeltaTime());

		printFPS();

		theStateStack.peek().render();
	}


	public void update()
	{
	//	theStateStack.peek().update(Gdx.graphics.getRawDeltaTime());
	}

	public void input(Speed speed, Direction dir, InputSystem is, float x, float y)
	{
		theStateStack.peek().input(speed, dir, is, x, y);
	}


	public void popStack()
    {
        theStateStack.pop();
    }

    public void pushShopStack() { theStateStack.push(theShopState);}

	public void pushMenuStack() { theStateStack.push(theMenuState);}

	public void pushGameStack() { theStateStack.push(theGameState);}


	@Override
	public void dispose ()
	{
		//theStateStack.peek().dispose();
		theGameState.dispose();
		theMenuState.dispose();
	//	theUpdateThread.setRunning(false);
		//Gdx.app.exit();
	}

	public InputHandler getInputHandler()
	{
		return theInputHandler;
	}

}
