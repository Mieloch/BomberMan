package local.oop.model;

import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;

public class Player {
    private PlayerPosition position;
    private Direction direction;
    private final PlayerId id;
    private int speed;
    private int power;

    public Player(PlayerId id, PlayerPosition position) {
        this(id, position, Direction.DOWN);
    }

    public Player(PlayerId id, PlayerPosition position, Direction direction) {
        this.id = id;
        this.position = position;
        this.direction = direction;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
