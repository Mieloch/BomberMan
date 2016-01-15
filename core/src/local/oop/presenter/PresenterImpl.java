package local.oop.presenter;

import com.google.inject.Inject;
import local.oop.model.arena.Arena;
import local.oop.model.arena.ArenaState;
import local.oop.model.CommandSequence;

import java.util.List;

public class PresenterImpl implements Presenter {
    private PlayerManager playerManager;
    private Arena arena;

    @Inject
    public PresenterImpl(PlayerManager playerManager, Arena arena){
        this.playerManager = playerManager;
        this.arena = arena;
        arena.setPresenter(this);
    }

    @Override
    public ArenaState getCurrentState() {
        return arena.getCurrentState();
    }

    @Override
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public List<CommandSequence> getPlayersMoves() {
        return playerManager.getPlayersMoves();
    }

    @Override
    public void startGame() {
        arena.start();
    }
}
