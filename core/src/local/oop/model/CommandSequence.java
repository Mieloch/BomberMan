package local.oop.model;

import java.util.List;

public class CommandSequence {
    private List<Command> commands;
    private PlayerId playerId;

    public CommandSequence(List<Command> commands, PlayerId playerId) {
        this.commands = commands;
        this.playerId = playerId;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }
}
