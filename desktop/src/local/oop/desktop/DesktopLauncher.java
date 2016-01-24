package local.oop.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import local.oop.ApplicationModule;

public class DesktopLauncher {
	public static void main (String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 768;
        config.width = 1366;
        config.resizable = false;
        Game application = injector.getInstance(Game.class);
        new LwjglApplication(application, config);

    }
}
