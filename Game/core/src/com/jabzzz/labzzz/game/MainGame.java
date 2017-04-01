package com.jabzzz.labzzz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends ApplicationAdapter
{
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	private int frames = 0;
	private long lastOut = 0;

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		frames++;

		if(System.currentTimeMillis() - lastOut > 1000)
		{
			System.out.println("FPS: " + frames);
			frames = 0;
			lastOut = System.currentTimeMillis();
		}


		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		img.dispose();
	}
}
