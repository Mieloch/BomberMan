package local.oop.model;

import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.Direction;

public class PlayerPosition extends Position {

    private Direction direction;

    public static final int SIZE = 32;

    public PlayerPosition(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    PlayerPosition moveRight(int x) { return new PlayerPosition(this.x + x, y, Direction.RIGHT);}

    PlayerPosition moveLeft(int x) {
        return new PlayerPosition(this.x - x, y, Direction.LEFT);
    }

    PlayerPosition moveUp(int y) {
        return new PlayerPosition(x, this.y + y, Direction.UP);
    }

    PlayerPosition moveDown(int y) {
        return new PlayerPosition(x, this.y - y, Direction.DOWN);
    }

    public Direction getDirection() {
        return direction;
    }

    public BlockPosition getBlockPosition(){
        return new BlockPosition((x+SIZE/2)/ BlockType.SIZE, (y+SIZE/2)/ BlockType.SIZE);
    }
}
