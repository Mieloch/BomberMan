package local.oop.presenter;

import local.oop.model.CommandSequence;

import java.util.Map;

public interface PlayerManager {
    Map<String, CommandSequence> getPlayersMoves();
    void setPlayerCount(int count);
    int getNumberOfPlayers();
}
