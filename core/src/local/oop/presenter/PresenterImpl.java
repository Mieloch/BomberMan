package local.oop.presenter;

import com.google.inject.Inject;
import local.oop.model.ArenaState;

public class PresenterImpl implements Presenter {

    private PlayerManager playerManager;

    @Inject
    public PresenterImpl(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    @Override
    public ArenaState getCurrentState() {
        return null;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
