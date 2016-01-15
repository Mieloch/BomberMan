package local.oop.model.player;

import local.oop.model.Position;

public class PlayerPosition extends Position {

    private Direction direction;

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
}
