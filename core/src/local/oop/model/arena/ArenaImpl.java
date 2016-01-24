package local.oop.model.arena;

import com.google.inject.Inject;
import local.oop.model.*;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;
import local.oop.presenter.Presenter;

import java.util.*;

public class ArenaImpl implements Arena {
    public final static int MAP_SIZE = 11;
    Presenter presenter;
    Timer timer;
    ArenaState currentState;
    ArenaState.Builder nextStateBuilder;
    List<BlockPosition> explosions;
    int bombTimeout = 3000;
    int fireTimeout = 1000;

    @Inject
    public ArenaImpl() {
        this.timer = new Timer();
        explosions = new ArrayList<>();
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
        nextStateBuilder = new ArenaState.Builder(new Level(MAP_SIZE, MAP_SIZE).getGeneratedLevel());
        currentState = nextStateBuilder.get();
    }

    private TimerTask getLoopTask() {
        return new TimerTask() {
            @Override
            public void run() {
                loop();
            }
        };
    }

    private void checkPlayersLives(){
        currentState.getPlayers().stream().filter(player -> player.getLives() == 0).forEach(player -> nextStateBuilder.removePlayer(player));
    }

    private void loop() {
        checkPlayersLives();
        isOnFire();
        acquireAndExecuteCommands();
        currentState = nextStateBuilder.get();
        if(currentState.getPlayers().size() == 1){
            currentState.finnish(currentState.getPlayers().stream().findFirst().get());
            timer.cancel();
        }
    }

    private void acquireAndExecuteCommands() {
        for (CommandSequence commandSequence : presenter.getPlayersMoves()) {
            for (Command command : commandSequence.getCommands()) {
                executeCommand(commandSequence.getPlayerId(), command);
            }
        }
    }

    private void executeCommand(PlayerId playerId, Command command) {
        Player player = currentState.getPlayer(playerId);
        if(player != null) {
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
    }

    private boolean isMoveMakeCollision(Player player, Direction direction) {

        Optional<Map.Entry<BlockPosition, BlockType>> entryOptional;
        BlockPosition blockPosition = null;
        int pX = player.getPosition().x;
        int pY = player.getPosition().y;
        int boardSize = MAP_SIZE * BlockType.SIZE;
        int offsetX = 0, offsetY = 0;
        BlockType block = null;
        switch (direction) {
            case UP:
                if (pY > boardSize)
                    return false;
                pY = (pY + BlockType.SIZE) / 32;
                pX = (pX + BlockType.SIZE / 2) / 32;
                offsetY = 1;
                break;
            case DOWN:
                if (pY < 0)
                    return false;
                pY = pY / 32;
                pX = (pX + BlockType.SIZE / 2) / 32;
                offsetY = -1;
                break;
            case LEFT:
                if (pX < 0)
                    return false;
                pY = (pY + BlockType.SIZE / 2) / 32;
                pX = pX / 32;
                offsetX = -1;
                break;
            case RIGHT:
                if (pX > boardSize)
                    return false;
                pY = (pY + BlockType.SIZE / 2) / 32;
                pX = (pX + BlockType.SIZE) / 32;
                offsetX = 1;
                break;
        }
        final int fPX = pX;
        final int fPY = pY;
        entryOptional = currentState.getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().x == fPX)
                .filter(e -> e.getKey().y == fPY)
                .findFirst();
        if (entryOptional.isPresent()) {
            block = entryOptional.get().getValue();
            blockPosition = entryOptional.get().getKey();
        }
        if (block != null) {
            if (block == BlockType.BOMB) {
                final int fMiddlePX = (player.getPosition().x + BlockType.SIZE / 2) / BlockType.SIZE;
                final int fMiddlePY = (player.getPosition().y + BlockType.SIZE / 2) / BlockType.SIZE;
                entryOptional = currentState.getBlocks()
                        .entrySet()
                        .stream()
                        .filter(e -> e.getKey().x == fMiddlePX)
                        .filter(e -> e.getKey().y == fMiddlePY)
                        .findFirst();
                if (entryOptional.isPresent()) {
                    block = entryOptional.get().getValue();
                }
                if (block == BlockType.BOMB) {
                    final int fOffsetX = offsetX;
                    final int fOffsetY = offsetY;
                    entryOptional = currentState.getBlocks()
                            .entrySet()
                            .stream()
                            .filter(e -> e.getKey().x == fMiddlePX + fOffsetX)
                            .filter(e -> e.getKey().y == fMiddlePY + fOffsetY)
                            .findFirst();
                    if (entryOptional.isPresent()) {
                        return entryOptional.get().getValue() == BlockType.BACKGROUND;
                    }
                }
                return false;
            } else if(block == BlockType.BOMB_POWERUP){
                player.setBombs(3);
                nextStateBuilder.setBlock(blockPosition, BlockType.BACKGROUND);
            } else if(block == BlockType.FLAME_POWERUP){
                player.setPower(6);
                nextStateBuilder.setBlock(blockPosition, BlockType.BACKGROUND);
            } else if(block == BlockType.SPEED_POWERUP){
                player.setSpeed(4);
                nextStateBuilder.setBlock(blockPosition, BlockType.BACKGROUND);
            }
            return block == BlockType.BACKGROUND;
        }
        return false;
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
                List<BlockPosition> explosions = currentState.getPlacesWhereFireCanBe(position, player.getPower());
                Map<BlockPosition, BlockType> map = new HashMap<>();
                for (BlockPosition explosion : explosions) {
                    map.put(explosion, currentState.getBlocks().get(explosion));
                    nextStateBuilder.setBlock(explosion, BlockType.FIRE);
                }
                player.incrementBombs();
                timer.schedule(getFireDisposalTask(map), fireTimeout);
            }
        };
    }

    private void isOnFire() {
        List<Player> players = currentState.getPlayers();
        for (Player player : players) {
            int x = player.getPosition().x, y = player.getPosition().y;
            int playerCenterX = (x + (PlayerPosition.SIZE / 2));
            int playerCenterY = (y + (PlayerPosition.SIZE / 2));
            BlockPosition playerCenter = new BlockPosition(playerCenterX / BlockType.SIZE, playerCenterY / BlockType.SIZE);
            boolean playerIsOnFire = currentState.getBlocks().entrySet().stream().anyMatch(e -> e.getValue() == BlockType.FIRE && (e.getKey().equals(playerCenter)));
            if (playerIsOnFire) {
                player.die();
            }

        }
    }

    private TimerTask getFireDisposalTask(Map<BlockPosition, BlockType> blocks) {
        return new TimerTask() {
            @Override
            public void run() {
               Set<Map.Entry<BlockPosition, BlockType>> set = blocks.entrySet();
                for (Map.Entry<BlockPosition, BlockType> blockPositionBlockTypeEntry : set) {
                    if(currentState.getBlocks().get(blockPositionBlockTypeEntry.getKey()) != BlockType.BOMB){
                        blocks.entrySet().stream().forEach(b -> nextStateBuilder.clearBlock(b.getKey(), b.getValue()));
                    }
                }
            }
        };
    }



    private BlockPosition convertPlayerToBlock(PlayerPosition playerPosition) {
        return new BlockPosition((playerPosition.x + PlayerPosition.SIZE / 2) / BlockPosition.SIZE, (playerPosition.y + PlayerPosition.SIZE / 2) / BlockPosition.SIZE);
    }

}
