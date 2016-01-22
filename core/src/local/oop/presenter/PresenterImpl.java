package local.oop.presenter;

import com.google.inject.Inject;
import local.oop.ai.AI;
import local.oop.ai.RandomAI;
import local.oop.model.Command;
import local.oop.model.arena.Arena;
import local.oop.model.ArenaState;
import local.oop.model.CommandSequence;
import local.oop.model.player.PlayerId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class PresenterImpl implements Presenter {
    private PlayersInputCache playersInputCache;
    private Arena arena;
    private HashMap<String,AI> playerMap;
    private List<AI> aiInGame;

    @Inject
    public PresenterImpl(PlayersInputCache playersInputCache, Arena arena){
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
        return playersInputCache.getPlayersMoves();

    }

    private void startAI(){
        Thread aiThread = new Thread((Runnable) () -> {
            while(true){
                for (AI ai : aiInGame) {
                    Command command = ai.makeMove(arena.getCurrentState());
                    playersInputCache.movePlayer(ai.getPlayerId(),command);
                }
            }
        });
        Executor ex = Executors.newSingleThreadExecutor();
        ex.execute(aiThread);
    }

    @Override
    public void startGame() {
        arena.start();
        startAI();
    }

    @Override
    public void setPlayers(List<String> names) {
        names.stream().forEach(s -> {
            aiInGame.add(playerMap.get(s));
        });
    }

    private void initPlayerMap(){
        playerMap = new HashMap<>();
        playerMap.put("Pawel", new RandomAI(PlayerId.PLAYER_1));
        playerMap.put("Ernest", new RandomAI(PlayerId.PLAYER_3));
        playerMap.put("Jacek", new RandomAI(PlayerId.PLAYER_2));
    }
}
