package local.oop.presenter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import local.oop.model.Command;
import local.oop.model.CommandSequence;
import local.oop.model.player.PlayerId;

import java.util.*;

@Singleton
public class PlayersInputCacheImpl implements PlayersInputCache {

    private int playerCount;
    private List<CommandSequence> playerMoves;
    private Map<PlayerId, Optional<Command>> movingStates;

    @Inject
    public PlayersInputCacheImpl() {
        this.playerMoves = new ArrayList<>();
        movingStates = new HashMap<>();
    }

    @Override
    public List<CommandSequence> getPlayersMoves() {
        movingStates.entrySet().stream()
                .filter(e -> e.getValue().isPresent())
                .forEach(e -> addCommandToSequence(e.getKey(), e.getValue().get()));
        List<CommandSequence> result = playerMoves;
        playerMoves = new ArrayList<>();
        return result;
    }

    private void addCommandToSequence(PlayerId id, Command command) {
        CommandSequence sequence = playerMoves.stream()
                .filter(commandSequence -> commandSequence.getPlayerId() == id)
                .findFirst()
                .orElse(new CommandSequence(new ArrayList<>(), id));
        sequence.addCommand(command);
        playerMoves.add(sequence);
    }

    @Override
    public void setPlayerCount(int count) {
        this.playerCount = count;
    }

    @Override
    public void movePlayer(PlayerId id, Command command) {
        if (command == Command.BOMB) {
            CommandSequence sequence = playerMoves.stream()
                    .filter(commandSequence -> commandSequence.getPlayerId() == id)
                    .findFirst()
                    .orElse(new CommandSequence(new ArrayList<>(), id));
            sequence.addCommand(command);
            playerMoves.add(sequence);
        }  else {
            movingStates.put(id, Optional.of(command));
        }
    }

    @Override
    public void stopMovement(PlayerId id, Command command) {
        if (movingStates.get(id).filter(c -> c == command).isPresent()) {
            movingStates.put(id, Optional.empty());
        }
    }


    @Override
    public int getNumberOfPlayers() {
        return playerCount;
    }
}
