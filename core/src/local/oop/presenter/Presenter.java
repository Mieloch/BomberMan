package local.oop.presenter;

import local.oop.model.ArenaState;
import local.oop.model.CommandSequence;

import java.util.List;

public interface Presenter {
    ArenaState getCurrentState();
    PlayersInputCache getPlayersInputCache();
    List<CommandSequence> getPlayersMoves();
    void startGame();
    void setPlayers(List<String> names);
}
