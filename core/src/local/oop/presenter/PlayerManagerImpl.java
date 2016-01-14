package local.oop.presenter;

import com.google.inject.Singleton;
import local.oop.model.CommandSequence;

import java.util.*;

@Singleton
public class PlayerManagerImpl implements PlayerManager{

    private List<String> players;

    @Override
    public List<CommandSequence> getPlayersMoves(){
        return new ArrayList<>();
    }

    @Override
    public void setPlayerCount(int count){
        String uniqueID = UUID.randomUUID().toString();
        players = new ArrayList<>();
        for(int i = 0; i<count; i++){
            players.add(uniqueID);
            uniqueID = UUID.randomUUID().toString();
        }
    }

    @Override
    public int getNumberOfPlayers(){
        return players.size();
    }
}
