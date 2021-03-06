package local.oop.presenter;

import com.google.inject.Inject;
import local.oop.ai.AI;
import local.oop.ai.PawelAi;
import local.oop.ai.ernest.ErnestAI;
import local.oop.ai.RandomAI;
import local.oop.model.Command;
import local.oop.model.Player;
import local.oop.model.arena.Arena;
import local.oop.model.ArenaState;
import local.oop.model.CommandSequence;
import local.oop.model.player.PlayerId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PresenterImpl implements Presenter {
    private PlayersInputCache playersInputCache;
    private Arena arena;
    private List<AI> aiInGame;

    @Inject
    public PresenterImpl(PlayersInputCache playersInputCache, Arena arena) {
        this.playersInputCache = playersInputCache;
        this.arena = arena;
        this.arena.setPresenter(this);
        aiInGame = new ArrayList<>();
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

    public void removeAI(PlayerId id){
        aiInGame = aiInGame.stream().filter(ai -> ai.getPlayerId() != id).collect(Collectors.toList());
    }

    @Override
    public void resetAll() {
        arena.stop();
        aiInGame = new ArrayList<>();
        PlayerId.reset();
        arena.getCurrentState().setPlayers(new ArrayList<>());
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
            Player player = new Player(id);
            player.setName(names.get(i));
            playerList.add(player);
            switch (names.get(i)){
                case Player.PAWEL:
                    aiInGame.add(new PawelAi(id));
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
                case Player.PLAYER_1:
                    player.setRealPlayer(1);
                    PlayerId.setFirst(player.getId());
                    break;
                case Player.PLAYER_2:
                    player.setRealPlayer(2);
                    PlayerId.setSecond(player.getId());
                    break;
            }
        }
        arena.getCurrentState().setPlayers(playerList);
    }
}
