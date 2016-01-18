package local.oop.model.arena;

import com.google.inject.Inject;
import local.oop.model.*;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;
import local.oop.presenter.Presenter;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaImpl implements Arena {
    public final static int MAP_SIZE = 25;
    Presenter presenter;
    Timer timer;
    ArenaState currentState;
    ArenaState.Builder nextStateBuilder;
    List<BlockPosition> explosions;
    int step;
    int bombTimeout = 3000;
    int fireTimeout = 1000;
    int blockResolution;
    private Level level;

    @Inject
    public ArenaImpl(Timer timer, ArenaState.Builder builder) {
        this.timer = timer;
        this.nextStateBuilder = builder;
        explosions = new ArrayList<>();
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


    private void initArenaState() {
        level = new Level(MAP_SIZE, MAP_SIZE);
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

    private boolean isMoveMakeCollision(Player player, Direction direction) {

        int speed = player.getSpeed();
        int pX = player.getPosition().x, pY = player.getPosition().y;
        switch (direction) {
            case UP:
                pY += speed;
                break;
            case DOWN:
                pY -= speed;
                break;
            case LEFT:
                pX -= speed;
                break;
            case RIGHT:
                pX += speed;
                break;
        }

        return areCornersOnFreeSpace(pX, pY, direction);

    }

    private void executeCommand(PlayerId playerId, Command command) {
        Player player = currentState.getPlayer(playerId);
        if (command == Command.BOMB) {
            if (player.getBombs() > 0) {
                placeBomb(player);
                player.decrementBombs();
            }

        } else {
            if (isMoveMakeCollision(player, command.getDirection())) {
                nextStateBuilder.movePlayer(playerId, command.getDirection(), player.getSpeed());
            }
            nextStateBuilder.movePlayer(playerId, command.getDirection(), 0);
        }
    }

    public boolean areCornersOnFreeSpace(int x, int y, Direction direction) {
        int playerSize = PlayerPosition.SIZE;
        int tolerance =3;
        int xShift = playerSize, yShift = playerSize;
        if (x % 32 == 0 || true) {
            xShift = playerSize-tolerance;
        }
        if (y % 32 == 0 || true) {
            yShift = playerSize-tolerance;
        }
        boolean leftBottomCorner;
        boolean rightBottomCorner;
        boolean leftTopCorner;
        boolean rightTopCorner;
        switch (direction) {
            case UP:
                leftTopCorner = isFreeSpace(x+tolerance, y + yShift);
                rightTopCorner = isFreeSpace(x + xShift , y + yShift);
                return leftTopCorner && rightTopCorner;

            case DOWN:
                leftBottomCorner = isFreeSpace(x+tolerance, y);
                rightBottomCorner = isFreeSpace(x + xShift, y);
                return leftBottomCorner && rightBottomCorner;

            case LEFT:
                leftBottomCorner = isFreeSpace(x, y + tolerance);
                leftTopCorner = isFreeSpace(x, y + yShift );
                return leftBottomCorner && leftTopCorner;

            case RIGHT:
                rightTopCorner = isFreeSpace(x + xShift+tolerance, y + yShift );
                rightBottomCorner = isFreeSpace(x + xShift+tolerance, y + tolerance);
                return rightTopCorner && rightBottomCorner;

        }
        return false;
    }

    public boolean isFreeSpace(int x, int y) {
        int size = BlockType.SIZE;


        if (x < 0 || x >= MAP_SIZE * 32) {
            return false;
        }
        if (y < 0 || y >= MAP_SIZE * 32) {
            return false;
        }
        int blockX = (x) / size, blockY = (y) / size;
        if (blockX < 0 || blockY < 0) {
            return false;
        }
        if (blockX >= MAP_SIZE || blockY >= MAP_SIZE) {
            return false;
        }

        if (!currentState.getBlocks().entrySet().stream().filter(entry -> entry.getKey().x == blockX && entry.getKey().y == blockY && entry.getValue() == BlockType.BOMB).collect(Collectors.toList()).isEmpty()) {
            return false;
        }
        BlockType block = currentState.getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().x == blockX)
                .filter(e -> e.getKey().y == blockY)
                .findFirst()
                .get()
                .getValue();
        return block == BlockType.BACKGROUND || block == BlockType.FIRE;
    }

    private void placeBomb(Player player) {
        BlockPosition position = convertPlayerToBlock(player.getPosition());
        nextStateBuilder.setBlock(position, BlockType.BOMB);
        timer.schedule(getBombTask(player, position), bombTimeout);
    }

    private TimerTask getBombTask(Player player, BlockPosition position) {
        return new TimerTask() {
            @Override
            public void run() {
                List<BlockPosition> explosions = getPlacesWhereFireCanBe(position, player.getPower());
                for (BlockPosition explosion : explosions) {
                    nextStateBuilder.setBlock(explosion, BlockType.FIRE);
                }
                player.incrementBombs();
                timer.schedule(getFireDisposalTask(explosions), fireTimeout);
            }
        };
    }

    private TimerTask getFireDisposalTask(List<BlockPosition> blocks) {
        return new TimerTask() {
            @Override
            public void run() {
                blocks.stream().forEach(b -> nextStateBuilder.clearBlock(b));
            }
        };
    }

    private List<BlockPosition> getPlacesWhereFireCanBe(BlockPosition position, int power) {
        return currentState.getBlocks()
                .entrySet()
                .stream()
                .filter(entry -> (entry.getKey().x == position.x && entry.getKey().y < position.y + power && entry.getKey().y > position.y - power) ||
                        (entry.getKey().y == position.y && entry.getKey().x < position.x + power && entry.getKey().x > position.x - power) &&
                                entry.getValue() != BlockType.SOLID)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private BlockPosition convertPlayerToBlock(PlayerPosition playerPosition) {
        return new BlockPosition(playerPosition.x / BlockPosition.SIZE, playerPosition.y / BlockPosition.SIZE);
    }

}
