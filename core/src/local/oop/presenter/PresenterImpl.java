package local.oop.presenter;

import com.google.inject.Inject;
import local.oop.model.arena.Arena;
import local.oop.model.ArenaState;
import local.oop.model.CommandSequence;

import java.util.List;

public class PresenterImpl implements Presenter {
    private PlayersInputCache playersInputCache;
    private Arena arena;

    @Inject
    public PresenterImpl(PlayersInputCache playersInputCache, Arena arena){
        this.playersInputCache = playersInputCache;
        this.arena = arena;
        this.arena.setPresenter(this);
    }

    @Override
    public ArenaState getCurrentState() {
        return arena.getCurrentState();
    }

    public PlayersInputCache getPlayersInputCache() {
        return playersInputCache;
    }

    @Override
    public List<CommandSequence> getPlayersMoves() {
        return playersInputCache.getPlayersMoves();
    }

    @Override
    public void startGame() {
        arena.start();
    }
}
