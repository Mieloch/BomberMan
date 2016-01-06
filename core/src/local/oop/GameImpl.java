package local.oop;

import com.badlogic.gdx.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import local.oop.presenter.Presenter;
import local.oop.view.StartScreen;

public class GameImpl extends Game {
    private Presenter presenter;
    private InputProcessor playerInputProcessor;
    private InputProcessor windowInputProcessor;
    private InputMultiplexer inputMultiplexer;

    @Inject
    public GameImpl(Presenter presenter, @Named("PlayerInputProcessor") InputProcessor playerInputProcessor,
                    @Named("WindowInputProcessor") InputProcessor windowInputProcessor) {
        this.presenter = presenter;
        this.playerInputProcessor = playerInputProcessor;
        this.windowInputProcessor = windowInputProcessor;
    }

    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(windowInputProcessor);
        inputMultiplexer.addProcessor(playerInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.setScreen(new StartScreen(this));
    }

    public Presenter getPresenter(){
        return this.presenter;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
