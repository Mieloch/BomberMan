package local.oop.events;

import com.badlogic.gdx.InputAdapter;
import com.google.inject.Inject;
import local.oop.model.Setting;
import local.oop.model.Settings;
import local.oop.model.player.PlayerId;
import local.oop.presenter.PlayersInputCache;

import javax.inject.Named;

@Named("PlayerInputProcessor")
public class PlayerInputProcessor extends InputAdapter {

    private Settings settings;
    private PlayersInputCache manager;

    @Inject
    public PlayerInputProcessor(PlayersInputCache manager) {
        this.manager = manager;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (settings == null) {
            settings = new Settings();
        }
        settings.getKeycodesMap()
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() == keycode)
                .map(stringIntegerEntry -> Setting.valueOf(stringIntegerEntry.getKey()))
                .findFirst()
                .ifPresent(setting -> manager.movePlayer(PlayerId.getId(setting.getPlayerNumber()), setting.getCommand()));

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (settings == null) {
            settings = new Settings();
        }
        settings.getKeycodesMap()
                .entrySet()
                .stream()
                .filter(s -> s.getValue() == keycode)
                .filter(s -> !s.getKey().equals("ONE_BOMB"))
                .filter(s -> !s.getKey().equals("TWO_BOMB"))
                .map(stringIntegerEntry -> Setting.valueOf(stringIntegerEntry.getKey()))
                .findFirst()
                .ifPresent(setting -> manager.stopMovement(PlayerId.getId(setting.getPlayerNumber()), setting.getCommand()));

        return true;
    }
}
