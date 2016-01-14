package local.oop.model;

import com.google.inject.Inject;
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
    public ArenaImpl(Timer timer) {
        this.timer = timer;
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
    }

    private void acquireAndExecuteCommands() {
        for (CommandSequence commandSequence : presenter.getPlayersMoves()) {
            for (Command command: commandSequence.getCommands()) {
                executeCommand(commandSequence.getPlayerId(), command);
            }
        }
    }

    private void executeCommand(String playerId, Command command) {
        PlayerPosition currentPosition = currentState.getPlayers().get(playerId);
        switch (command) {
            case RIGHT:
                nextStateBuilder.setPlayer(playerId, currentPosition.moveRight(step));
                break;
            case UP:
                nextStateBuilder.setPlayer(playerId, currentPosition.moveUp(step));
                break;
            case DOWN:
                nextStateBuilder.setPlayer(playerId, currentPosition.moveDown(step));
                break;
            case LEFT:
                nextStateBuilder.setPlayer(playerId, currentPosition.moveLeft(step));
                break;
            case BOMB:
                placeBomb(convertPlayerToBlock(currentPosition));
                break;

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
