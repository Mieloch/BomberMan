package local.oop.model.arena;

import com.google.inject.Inject;
import local.oop.model.*;
import local.oop.model.player.PlayerId;
import local.oop.model.player.PlayerPosition;
import local.oop.presenter.Presenter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ArenaImpl implements Arena {
    Presenter presenter;
    Timer timer;
    ArenaState currentState;
    ArenaState.Builder nextStateBuilder;
    List<BlockPosition> explosions;
    int step;
    int bombTimeout;
    int blockResolution;

    @Inject
    public ArenaImpl(Timer timer, ArenaState.Builder builder) {
        this.timer = timer;
        this.nextStateBuilder = builder;
        currentState = nextStateBuilder.get();
    }

    @Override
    public ArenaState getCurrentState() {
        return currentState;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        timer.schedule(getLoopTask(), 0, 25);
    }

    private TimerTask getLoopTask() {
        return new TimerTask() {
            @Override
            public void run() {
                loop();
            }
        };
    }

    private void loop() {
        nextStateBuilder = new ArenaState.Builder(currentState);
        acquireAndExecuteCommands();
        currentState = nextStateBuilder.get();
        nextStateBuilder.clear();
    }

    private void acquireAndExecuteCommands() {
        for (CommandSequence commandSequence : presenter.getPlayersMoves()) {
            for (Command command : commandSequence.getCommands()) {
                executeCommand(commandSequence.getPlayerId(), command);
            }
        }
    }

    private void executeCommand(PlayerId playerId, Command command) {
        if (command == Command.BOMB) {
            placeBomb(convertPlayerToBlock(currentState.getPlayer(playerId).getPosition()));
        } else {
            nextStateBuilder.movePlayer(playerId, command.getDirection(), step);
        }
    }

    private void placeBomb(BlockPosition position) {
        nextStateBuilder.setBlock(position, BlockType.BOMB);
        timer.schedule(getBombTask(position), bombTimeout);
    }

    private TimerTask getBombTask(BlockPosition position) {
        return new TimerTask() {
            @Override
            public void run() {
                explosions.add(position);
            }
        };
    }

    private BlockPosition convertPlayerToBlock(PlayerPosition playerPosition) {
        return new BlockPosition(playerPosition.x / blockResolution, playerPosition.y / blockResolution);
    }

}
