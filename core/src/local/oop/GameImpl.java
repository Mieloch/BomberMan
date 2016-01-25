package local.oop;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import local.oop.presenter.Presenter;
import local.oop.view.StartScreen;

public class GameImpl extends Game {
    private Presenter presenter;
    private InputProcessor playerInputProcessor;
    private InputMultiplexer inputMultiplexer;
    private static Sound soundtrack;
    public static Sound powerUp;
    public static Sound explosion;
    private static long soundId;
    private static float volume;

    @Inject
    public GameImpl(Presenter presenter, @Named("PlayerInputProcessor") InputProcessor playerInputProcessor) {
        this.presenter = presenter;
        this.playerInputProcessor = playerInputProcessor;
    }

    @Override
    public void create() {
        powerUp = Gdx.audio.newSound(Gdx.files.internal("boing.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));
        soundtrack = Gdx.audio.newSound(Gdx.files.internal("soundtrack.wav"));
        volume = 1;
        soundId = soundtrack.loop(volume);
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.setScreen(new StartScreen(this));
    }

    public static void playPowerUp(){
        powerUp.play(volume);
    }

    public static void playExplosion(){
        explosion.play(volume);
    }

    public void mute(boolean mute){
        if(mute){
            volume = 0;
        } else {
            volume = 1;
        }
        soundtrack.setVolume(soundId, volume);
    }

    @Override
    public void dispose() {
        soundtrack.dispose();
        powerUp.dispose();
        explosion.dispose();
    }

    public boolean isMuted(){
        return volume == 0;
    }

    public void disablePlayerInput(){
        inputMultiplexer.removeProcessor(playerInputProcessor);
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
