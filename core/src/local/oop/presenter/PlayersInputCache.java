package local.oop.presenter;

import local.oop.model.Command;
import local.oop.model.CommandSequence;
import local.oop.model.player.PlayerId;

import java.util.List;

public interface PlayersInputCache {
    List<CommandSequence> getPlayersMoves();
    void setPlayerCount(int count);
    int getNumberOfPlayers();
    void movePlayer(PlayerId id, Command command);
    void stopMovement(PlayerId id, Command command);
}
