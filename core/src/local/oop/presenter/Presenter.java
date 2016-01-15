package local.oop.presenter;

import local.oop.model.arena.ArenaState;
import local.oop.model.CommandSequence;

import java.util.List;


public interface Presenter {
    ArenaState getCurrentState();
    PlayerManager getPlayerManager();
    List<CommandSequence> getPlayersMoves();
    void startGame();
}
