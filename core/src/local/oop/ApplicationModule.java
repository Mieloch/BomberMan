package local.oop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import local.oop.events.PlayerInputProcessor;
import local.oop.events.WindowInputProcessor;
import local.oop.model.Arena;
import local.oop.model.ArenaImpl;
import local.oop.presenter.Presenter;
import local.oop.presenter.PresenterImpl;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Presenter.class).to(PresenterImpl.class);
        bind(Arena.class).to(ArenaImpl.class);
        bind(InputProcessor.class).annotatedWith(Names.named("PlayerInputProcessor")).to(PlayerInputProcessor.class);
        bind(InputProcessor.class).annotatedWith(Names.named("WindowInputProcessor")).to(WindowInputProcessor.class);
        bind(ApplicationListener.class).to(Game.class);
    }
}
