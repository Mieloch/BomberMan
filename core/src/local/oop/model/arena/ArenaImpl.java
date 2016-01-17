package local.oop.model.arena;

import com.google.inject.Inject;
import local.oop.model.*;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;
import local.oop.model.PlayerPosition;
import local.oop.presenter.Presenter;

import java.util.*;

public class ArenaImpl implements Arena {
    Presenter presenter;
    Timer timer;
    ArenaState currentState;
    ArenaState.Builder nextStateBuilder;
    List<BlockPosition> explosions;
    int step;
    int bombTimeout;
    int blockResolution;
    private final int MAP_SIZE = 17;
    private Level level;
    private Map<PlayerId,PlayerPosition> spawningMap;

    @Inject
    public ArenaImpl(Timer timer, ArenaState.Builder builder) {
        this.timer = timer;
        this.nextStateBuilder = builder;
        currentState = nextStateBuilder.get();
        level = new Level(MAP_SIZE,MAP_SIZE);
        initSpawningMap();
        initArenaState();

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

    private void initSpawningMap(){
        spawningMap = new HashMap<>();
        int blockSize = BlockPosition.SIZE;
        spawningMap.put(PlayerId.PLAYER_1,new PlayerPosition(0*blockSize,0*blockSize,Direction.DOWN));
        spawningMap.put(PlayerId.PLAYER_2,new PlayerPosition((MAP_SIZE-1) *blockSize,0*blockSize,Direction.DOWN));
        spawningMap.put(PlayerId.PLAYER_3,new PlayerPosition(0*blockSize,(MAP_SIZE-1)*blockSize,Direction.DOWN));
        spawningMap.put(PlayerId.PLAYER_4,new PlayerPosition((MAP_SIZE-1)*blockSize,(MAP_SIZE-1)*blockSize,Direction.DOWN));
    }

    private void initArenaState(){
        nextStateBuilder = new ArenaState.Builder(currentState);
        loadBlocksToState();
        createPlayers();
        currentState = nextStateBuilder.get();
        nextStateBuilder.clear();

    }

    private void createPlayers(){ // tmp until someone make method to take player count from player choose screen
        nextStateBuilder.addPlayer(new Player(PlayerId.PLAYER_1,spawningMap.get(PlayerId.PLAYER_1)));
        nextStateBuilder.addPlayer(new Player(PlayerId.PLAYER_2,spawningMap.get(PlayerId.PLAYER_2)));
    }

    private void loadBlocksToState(){

        BlockType[][] blocks = level.getEnumLevel();
        for(int i = 0; i< MAP_SIZE; i++){
            for(int j=0;j<MAP_SIZE;j++){
                nextStateBuilder.setBlock(new BlockPosition(i,j), blocks[i][j]).get();
            }
        }
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

       return level.areAllCornersOnFreeSpace(pX,pY);

    }

    private void executeCommand(PlayerId playerId, Command command) {
        Player player = currentState.getPlayer(playerId);
        if (command == Command.BOMB) {
            placeBomb(convertPlayerToBlock(player.getPosition()),player.getPower());

        } else {
            if(isMoveMakeCollision(player,command.getDirection())){
                nextStateBuilder.movePlayer(playerId, command.getDirection(), 0);
            }
            nextStateBuilder.movePlayer(playerId, command.getDirection(), step);
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
        return new BlockPosition(playerPosition.x / blockResolution, playerPosition.y / blockResolution);
    }

}
