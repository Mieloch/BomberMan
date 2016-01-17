package local.oop.model;

public enum Setting {

    ONE_UP(Command.UP, 1),
    ONE_DOWN(Command.DOWN, 1),
    ONE_LEFT(Command.LEFT, 1),
    ONE_RIGHT(Command.RIGHT, 1),
    ONE_BOMB(Command.BOMB, 1),
    TWO_UP(Command.UP, 2),
    TWO_DOWN(Command.DOWN, 2),
    TWO_LEFT(Command.LEFT, 2),
    TWO_RIGHT(Command.RIGHT, 2),
    TWO_BOMB(Command.BOMB, 2);


    private Command command;
    private int playerNumber;

    Setting(Command command, int playerNumber) {
        this.command = command;
        this.playerNumber = playerNumber;
    }

    public Command getCommand() {
        return command;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

}
