package local.oop.model;

import java.util.List;

public class CommandSequence {
    private List<Command> commands;
    private int playerId;

    public CommandSequence(List<Command> commands, int playerId) {
        this.commands = commands;
        this.playerId = playerId;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public int getPlayerId() {
        return playerId;
    }
}
