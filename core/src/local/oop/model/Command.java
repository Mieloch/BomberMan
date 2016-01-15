package local.oop.model;

public enum Command {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    BOMB;

    private Direction direction = null;

    static {
        LEFT.direction = Direction.LEFT;
        RIGHT.direction = Direction.RIGHT;
        UP.direction = Direction.UP;
        DOWN.direction = Direction.DOWN;
    }

    public Direction getDirection() {
        return direction;
    }
}
