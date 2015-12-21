package local.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import local.oop.model.ArenaState;
import local.oop.presenter.Presenter;

public class Game extends ApplicationAdapter {
    private Presenter presenter;
    private InputProcessor playerInputProcessor;
    private InputProcessor windowInputProcessor;

    @Inject
    public Game(Presenter presenter, @Named("PlayerInputProcessor") InputProcessor playerInputProcessor,
                @Named("WindowInputProcessor") InputProcessor windowInputProcessor) {
        this.presenter = presenter;
        this.playerInputProcessor = playerInputProcessor;
        this.windowInputProcessor = windowInputProcessor;
    }

    @Override
    public void create() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(windowInputProcessor);
        inputMultiplexer.addProcessor(playerInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        ArenaState arenaState = presenter.getCurrentState();
    }
}
