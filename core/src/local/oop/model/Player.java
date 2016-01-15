package local.oop.model;

public class Player {
    private PlayerPosition position;
    private Direction direction;
    private final PlayerId id;

    public Player(PlayerId id, PlayerPosition position) {
        this(id, position, Direction.DOWN);
    }

    public Player(PlayerId id, PlayerPosition position, Direction direction) {
        this.id = id;
        this.position = position;
        this.direction = direction;
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
}
