package local.oop.presenter;

import com.google.inject.Inject;
import local.oop.ai.AI;
import local.oop.ai.ErnestAI;
import local.oop.ai.RandomAI;
import local.oop.model.Command;
import local.oop.model.Player;
import local.oop.model.arena.Arena;
import local.oop.model.ArenaState;
import local.oop.model.CommandSequence;
import local.oop.model.player.PlayerId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class PresenterImpl implements Presenter {
    private PlayersInputCache playersInputCache;
    private Arena arena;
    private HashMap<String, AI> playerMap;
    private List<AI> aiInGame;

    @Inject
    public PresenterImpl(PlayersInputCache playersInputCache, Arena arena) {
        this.playersInputCache = playersInputCache;
        this.arena = arena;
        this.arena.setPresenter(this);
        aiInGame = new ArrayList<>();
        initPlayerMap();
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
        for (AI ai : aiInGame) {

            Command command = ai.makeMove(arena.getCurrentState());
            if(command != null)
                playersInputCache.movePlayer(ai.getPlayerId(), command);
        }
        return playersInputCache.getPlayersMoves();

    }


    @Override
    public void startGame() {
        arena.start();
    }

    @Override
    public void setPlayers(List<String> names) {
        List<Player> playerList = new ArrayList<>();
        for(int i = 0; i < names.size(); i++){
            PlayerId id = PlayerId.getId(i+1);
            playerList.add(new Player(id));
            switch (names.get(i)){
                case Player.PAWEL:
                    aiInGame.add(new RandomAI(id));
                    break;
                case Player.ERNEST:
                    aiInGame.add(new ErnestAI(id));
                    break;
                case Player.JACEK:
                    aiInGame.add(new RandomAI(id));
                    break;
                case Player.SEBASTIAN:
                    aiInGame.add(new RandomAI(id));
                    break;
            }
        }
        arena.getCurrentState().setPlayers(playerList);
    }

    private void initPlayerMap() {
        playerMap = new HashMap<>();
        playerMap.put("Pawel", new RandomAI(PlayerId.PLAYER_1));
        playerMap.put("Ernest", new ErnestAI(PlayerId.PLAYER_2));
        playerMap.put("Jacek", new RandomAI(PlayerId.PLAYER_3));
        playerMap.put("Sebastian", new RandomAI(PlayerId.PLAYER_4));
    }
}
