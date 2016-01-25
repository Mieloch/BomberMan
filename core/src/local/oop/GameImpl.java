package local.oop;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Timer;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import local.oop.presenter.Presenter;
import local.oop.view.AbstractScreen;
import local.oop.view.StartScreen;

public class GameImpl extends Game {
    private Presenter presenter;
    private InputProcessor playerInputProcessor;
    private InputMultiplexer inputMultiplexer;

    @Inject
    public GameImpl(Presenter presenter, @Named("PlayerInputProcessor") InputProcessor playerInputProcessor) {
        this.presenter = presenter;
        this.playerInputProcessor = playerInputProcessor;
    }

    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.setScreen(new StartScreen(this));
    }

    public void setPlayerInputProcessor(){
        inputMultiplexer.addProcessor(playerInputProcessor);
    }

    public Presenter getPresenter(){
        return this.presenter;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
