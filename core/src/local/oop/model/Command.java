package local.oop.model;

import local.oop.model.player.Direction;

import java.util.Random;

public enum Command {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    BOMB;

    static {
        LEFT.direction = Direction.LEFT;
        RIGHT.direction = Direction.RIGHT;
        UP.direction = Direction.UP;
        DOWN.direction = Direction.DOWN;
    }

    private Direction direction = null;

    public static Command getRandomMove(){
        int i = new Random().nextInt(4);
        switch(i){
            case 0:
                return Command.RIGHT;
            case 1:
                return Command.LEFT;
            case 2:
                return Command.UP;
            case 3:
                return Command.DOWN;
        }
        return null;
    }

    public Direction getDirection() {
        return direction;
    }
}
