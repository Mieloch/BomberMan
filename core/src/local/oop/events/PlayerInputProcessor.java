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
    public PlayerInputProcessor(PlayersInputCache manager){
        this.manager = manager;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(settings==null){
            settings = new Settings();
        }
        Setting setting = settings
                .getKeycodesMap()
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() == keycode )
                .map(stringIntegerEntry -> Setting.valueOf(stringIntegerEntry.getKey()))
                .findFirst()
                .get();

        if(setting!=null)
            manager.movePlayer(PlayerId.getId(setting.getPlayerNumber()), setting.getCommand());

        return true;
    }
}
