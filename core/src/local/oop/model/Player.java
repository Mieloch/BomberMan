package local.oop.model;

public class Player {
    private PlayerPosition position;
    private Direction direction;
    private final PlayerId id;

    public Player(PlayerId id, PlayerPosition position) {
        this.id = id;
        this.position = position;
    }

    public PlayerId getId() {
        return id;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    void move(Direction direction, int step) {
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
}
