package com.jabzzz.labzzz.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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


	public static final int INDEX_WALL_START = 20;
	public static final int INDEX_WALL_END = 67;


	public static final String TITLE = "THE Game";
	public static int WIDTH = 600;
	public static int HEIGHT = 800;
	public static final int BLOCK_SIZE = 100;

	private Stack<AState> theStateStack = new Stack();


	private int frames = 0;
	private long lastOut = 0;
	
	@Override
	public void create ()
	{
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		//init printFPS
		lastOut = System.currentTimeMillis();

		pushMenuStack();
	}

	private void printFPS()
	{
		frames++;

		if(System.currentTimeMillis() - lastOut > 1000)
		{
			System.out.println("Elements: " + theStateStack.firstElement());
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

    public void pushShopStack()
	{
		if(!theStateStack.empty())
		{
			theStateStack.peek().dispose();
			theStateStack.pop();
		}
		theStateStack.push(new ShopState(this));
		System.out.println(theStateStack.firstElement()+", "+theStateStack.size());
	}

	public void pushMenuStack()
	{
		if(!theStateStack.empty())
		{
			theStateStack.peek().dispose();
			theStateStack.pop();
		}
		theStateStack.push(new MenuState(this));
		System.out.println(theStateStack.firstElement()+", "+theStateStack.size());
	}

	public void pushGameStack()
	{
		if(!theStateStack.empty())
		{
			theStateStack.peek().dispose();
			theStateStack.pop();
		}
		theStateStack.push(new GameState(this));
		System.out.println(theStateStack.firstElement()+", "+theStateStack.size());
	}




	@Override
	public void dispose ()
	{
		theStateStack.peek().dispose();
	}

}
