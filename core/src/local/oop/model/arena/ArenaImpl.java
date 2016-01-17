package local.oop.model.arena;

import com.google.inject.Inject;
import local.oop.model.*;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;
import local.oop.presenter.Presenter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ArenaImpl implements Arena {
    public final static int MAP_SIZE = 17;
    Presenter presenter;
    Timer timer;
    ArenaState currentState;
    ArenaState.Builder nextStateBuilder;
    List<BlockPosition> explosions;
    int step;
    int bombTimeout;
    int blockResolution;
    private Level level;

    @Inject
    public ArenaImpl(Timer timer, ArenaState.Builder builder) {
        this.timer = timer;
        this.nextStateBuilder = builder;
        currentState = nextStateBuilder.get();
        initArenaState();

    }

    @Override
    public ArenaState getCurrentState() {
        return currentState;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.currentState = this.nextStateBuilder.setPresenter(presenter).get();
    }

    @Override
    public void start() {
        timer.schedule(getLoopTask(), 0, 25);
    }


    private void initArenaState(){
        level = new Level(MAP_SIZE,MAP_SIZE);
        nextStateBuilder = new ArenaState.Builder(level.getGeneratedLevel());
        currentState = nextStateBuilder.get();
        nextStateBuilder.clear();

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

    private boolean isMoveMakeCollision(Player player, Direction direction){

        int speed = player.getSpeed();
        int pX = player.getPosition().x, pY = player.getPosition().y;
        switch (direction) {
            case UP:
                pY +=speed;
                break;
            case DOWN:
                pY -=speed;
                break;
            case LEFT:
                pX -=speed;
                break;
            case RIGHT:
                pY +=speed;
                break;
        }
        BlockPosition playerBlockPosition = convertPlayerToBlock(new PlayerPosition(pX,pY,direction));
       return level.areAllCornersOnFreeSpace(playerBlockPosition.x,playerBlockPosition.y);

    }

    private void executeCommand(PlayerId playerId, Command command) {
        Player player = currentState.getPlayer(playerId);
        if (command == Command.BOMB) {
            placeBomb(convertPlayerToBlock(player.getPosition()),player.getPower());

        } else {
            if(isMoveMakeCollision(player,command.getDirection())){
                nextStateBuilder.movePlayer(playerId, command.getDirection(), 0);
            }
            nextStateBuilder.movePlayer(playerId, command.getDirection(), player.getSpeed());
        }
    }

    private void placeBomb(BlockPosition position, int power) {
        nextStateBuilder.setBlock(position, BlockType.BOMB);
        timer.schedule(getBombTask(position, power), bombTimeout);
    }

    private TimerTask getBombTask(BlockPosition position, int power) {
        return new TimerTask() {
            @Override
            public void run() {
                explosions.addAll(level.getBlockWhereFireCanBe(position,power));
            }
        };
    }

    private BlockPosition convertPlayerToBlock(PlayerPosition playerPosition) {
        return new BlockPosition(playerPosition.x / BlockPosition.SIZE, playerPosition.y / BlockPosition.SIZE);
    }

}
