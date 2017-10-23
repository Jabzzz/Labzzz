package com.jabzzz.labzzz.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jabzzz.labzzz.controller.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();


		config.title = MainGame.TITLE;
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.fullscreen = false;
		config.foregroundFPS = 60;

		System.out.println("<DesktopLauncher>");
		System.out.println("\tTitle: " + config.title);
		System.out.println("\tResizeable: " + config.resizable);
		System.out.println("\tFullscreen: " + config.fullscreen);
		System.out.println("\tWindow-Size: " + config.width + "x" + config.height);
		System.out.println("\tFPS: " + config.foregroundFPS);
		System.out.println("</DesktopLauncher>");

		new LwjglApplication(new MainGame(), config);
	}
}
