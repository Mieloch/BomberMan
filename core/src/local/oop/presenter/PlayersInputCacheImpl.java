package local.oop.presenter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import local.oop.model.Command;
import local.oop.model.CommandSequence;
import local.oop.model.Player;
import local.oop.model.player.PlayerId;

import java.util.*;

@Singleton
public class PlayersInputCacheImpl implements PlayersInputCache {

    private int playerCount;
    private List<CommandSequence> playerMoves;

    @Inject
    public PlayersInputCacheImpl(){
        this.playerMoves = new ArrayList<>();
    }

    @Override
    public List<CommandSequence> getPlayersMoves(){
        return playerMoves;
    }

    @Override
    public void setPlayerCount(int count){
        this.playerCount = count;
    }

    @Override
    public void movePlayer(PlayerId id, Command command) {
        CommandSequence sequence = playerMoves.stream().filter(commandSequence -> commandSequence.getPlayerId()==id).findFirst().orElse(new CommandSequence(new ArrayList<>(), id));
        sequence.addCommand(command);
        playerMoves.add(sequence);
    }

    @Override
    public int getNumberOfPlayers(){
        return playerCount;
    }
}
