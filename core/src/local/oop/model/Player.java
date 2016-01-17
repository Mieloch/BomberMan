package local.oop.model;

import local.oop.model.arena.BlockPosition;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;

public class Player {
    private PlayerPosition position;
    private Direction direction;
    private final PlayerId id;
    private int speed;

    public Player(PlayerId id, int MAP_SIZE){
        this.id = id;
        int blockSize = BlockPosition.SIZE;
        switch (id){
            case PLAYER_1:
                position = new PlayerPosition(0,0, Direction.DOWN);
                break;
            case PLAYER_2:
                position = new PlayerPosition((MAP_SIZE-1) *blockSize,0,Direction.DOWN);
                break;
            case PLAYER_3:
                position = new PlayerPosition(0,(MAP_SIZE-1)*blockSize,Direction.DOWN);
                break;
            case PLAYER_4:
                position = new PlayerPosition((MAP_SIZE-1)*blockSize,(MAP_SIZE-1)*blockSize,Direction.DOWN);
                break;
        }
    }

    public Player(PlayerId id, PlayerPosition position) {
        this(id, position, Direction.DOWN);
    }

    public Player(PlayerId id, PlayerPosition position, Direction direction) {
        this.id = id;
        this.position = position;
        this.direction = direction;
    }


    public void move(Direction direction, int step) {
        switch (direction) {
            case UP:
                position = position.moveUp(step);
                break;
            case DOWN:
                position = position.moveDown(step);
                break;
            case LEFT:
                position = position.moveLeft(step);
                break;
            case RIGHT:
                position = position.moveRight(step);
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Player that = (Player) obj;

        return this.id == that.id;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PlayerId getId() {
        return id;
    }

    public PlayerPosition getPosition() {
        return position;
    }
}
