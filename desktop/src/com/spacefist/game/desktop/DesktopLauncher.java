package com.spacefist.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spacefist.game.SpaceFistGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width      = 1680;
		config.height     = 1050;
		config.fullscreen = false;
		config.title      = "Space Fist";

		new LwjglApplication(new SpaceFistGame(), config);
	}
}