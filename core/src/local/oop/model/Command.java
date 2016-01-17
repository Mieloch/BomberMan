package local.oop.model;

import local.oop.model.player.Direction;

public enum Command {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    BOMB,
    STOP;

    static {
        LEFT.direction = Direction.LEFT;
        RIGHT.direction = Direction.RIGHT;
        UP.direction = Direction.UP;
        DOWN.direction = Direction.DOWN;
    }

    private Direction direction = null;

    public Direction getDirection() {
        return direction;
    }
}
