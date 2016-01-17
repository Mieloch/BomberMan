package local.oop.presenter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import local.oop.model.Command;
import local.oop.model.CommandSequence;
import local.oop.model.Player;
import local.oop.model.PlayerPosition;
import local.oop.model.arena.BlockPosition;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;

import java.util.*;

@Singleton
public class PlayerManagerImpl implements PlayerManager{

    private List<Player> players;
    private Presenter presenter;

    @Inject
    public PlayerManagerImpl(Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public List<CommandSequence> getPlayersMoves(){
        return new ArrayList<>();
    }

    @Override
    public void setPlayerCount(int count){
        players = new ArrayList<>();
        for(int i = 0; i<count; i++){
            players.add(new Player(PlayerId.getId(i), presenter.getCurrentState().getMAP_SIZE()));
        }
    }

    @Override
    public void movePlayer(PlayerId id, Command command) {
        players.stream().filter(player -> player.getId()==id).findFirst().ifPresent(player1 -> player1.move(command.getDirection(), player1.getSpeed()));
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public int getNumberOfPlayers(){
        return players.size();
    }
}
