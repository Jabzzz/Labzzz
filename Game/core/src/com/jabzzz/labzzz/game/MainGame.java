package com.jabzzz.labzzz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jabzzz.labzzz.states.MenuState;
import com.jabzzz.labzzz.states.State;

import java.util.Stack;

public class MainGame extends ApplicationAdapter
{


	public static final String TITLE = "THE Game";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 850;




	private MenuState theMenuState = null;
	private UpdateThread theUpdateThread = null;

	private Stack<State> theStateStack = new Stack();


	private int frames = 0;
	private long lastOut = 0;



	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");


		//init printFPS
		lastOut = System.currentTimeMillis();


		//init MenuState
		theStateStack.add(theMenuState = new MenuState());


		theUpdateThread = new UpdateThread();

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
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		printFPS();

		theStateStack.peek().render();

	}


	public void update()
	{

	}


	
	@Override
	public void dispose ()
	{
		theStateStack.peek().dispose();
	}
}
