package local.oop.presenter;

import local.oop.model.CommandSequence;

import java.util.List;
import java.util.Map;

public interface PlayerManager {
    List<CommandSequence> getPlayersMoves();
    void setPlayerCount(int count);
    int getNumberOfPlayers();
}