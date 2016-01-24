package local.oop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import local.oop.events.PlayerInputProcessor;
import local.oop.model.arena.Arena;
import local.oop.model.arena.ArenaImpl;
import local.oop.presenter.PlayersInputCache;
import local.oop.presenter.PlayersInputCacheImpl;
import local.oop.presenter.Presenter;
import local.oop.presenter.PresenterImpl;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlayersInputCache.class).to(PlayersInputCacheImpl.class);
        bind(Presenter.class).to(PresenterImpl.class);
        bind(Arena.class).to(ArenaImpl.class);
        bind(InputProcessor.class).annotatedWith(Names.named("PlayerInputProcessor")).to(PlayerInputProcessor.class);
        bind(Game.class).to(GameImpl.class);
    }
}
