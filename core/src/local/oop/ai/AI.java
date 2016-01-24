package local.oop.ai;

import local.oop.model.ArenaState;
import local.oop.model.Command;
import local.oop.model.player.PlayerId;

public interface AI {
     Command makeMove(ArenaState state);
     PlayerId getPlayerId();
}
