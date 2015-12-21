package local.oop.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import local.oop.ApplicationModule;

public class DesktopLauncher {
	public static void main (String[] arg) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        ApplicationListener application = injector.getInstance(ApplicationListener.class);
        new LwjglApplication(application, config);
    }
}
