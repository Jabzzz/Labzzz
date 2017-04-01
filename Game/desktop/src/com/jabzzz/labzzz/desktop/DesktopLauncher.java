package com.jabzzz.labzzz.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jabzzz.labzzz.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();


		config.title = MainGame.TITLE;
		config.width = MainGame.WIDTH;
		config.height = MainGame.HEIGHT;

		new LwjglApplication(new MainGame(), config);
	}
}
