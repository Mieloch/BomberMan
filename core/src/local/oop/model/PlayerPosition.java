package local.oop.model;

public class PlayerPosition extends Position {
    public PlayerPosition(int x, int y) {
        super(x, y);
    }

    PlayerPosition moveRight(int x) {
        return new PlayerPosition(this.x + x, y);
    }

    PlayerPosition moveLeft(int x) {
        return new PlayerPosition(this.x - x, y);
    }

    PlayerPosition moveUp(int y) {
        return new PlayerPosition(x, this.y + y);
    }

    PlayerPosition moveDown(int y) {
        return new PlayerPosition(x, this.y - y);
    }

}
