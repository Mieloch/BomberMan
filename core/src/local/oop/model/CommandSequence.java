package local.oop.model;

import java.util.List;

public class CommandSequence {
    private List<Command> commands;
    private String playerId;

    public CommandSequence(List<Command> commands, String playerId) {
        this.commands = commands;
        this.playerId = playerId;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getPlayerId() {
        return playerId;
    }
}
