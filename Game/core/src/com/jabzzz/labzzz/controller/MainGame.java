package com.jabzzz.labzzz.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jabzzz.labzzz.states.MenuState;
import com.jabzzz.labzzz.states.AState;

import java.util.Stack;

public class MainGame extends ApplicationAdapter
{


	public static final String TITLE = "THE Game";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 850;



	private MenuState theMenuState = null;
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


		//init MenuState
		theStateStack.add(theMenuState = new MenuState(this));


		theUpdateThread = new UpdateThread(this);
		theUpdateThread.start();

		theInputHandler = new InputHandler(theStateStack);
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


		printFPS();

		theStateStack.peek().render();

	}


	public void update()
	{
		theStateStack.peek().update();
	}


	public void popStack()
    {
        theStateStack.pop();
    }

	
	@Override
	public void dispose ()
	{
		theStateStack.peek().dispose();
		theUpdateThread.setRunning(false);
	}
}
