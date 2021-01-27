package com.statyja.game.desktop;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.statyja.game.MainGrind;

public class DesktopLauncher {
	public static void main (String[] arg) {
		final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		config.renderInterval = 1f/60;
		new HeadlessApplication(new MainGrind(), config);
	}
}
