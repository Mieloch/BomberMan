package local.oop.model;

import java.util.*;

public class ArenaState {
    List<Player> players;
    Map<BlockPosition, BlockType> blocks;

    private ArenaState() {
        this(Optional.empty());
    }

    private ArenaState(Optional<ArenaState> arenaState) {
        arenaState.ifPresent(state -> {
            players = new ArrayList<>(arenaState.get().getPlayers());
            blocks = new HashMap<>(arenaState.get().getBlocks());
        });
    }

    public Map<BlockPosition, BlockType> getBlocks() {
        return blocks;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(PlayerId playerId) {
        return players.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .get();
    }

    public static class Builder {
        private ArenaState state;

        public Builder() {
            this(null);
        }

        public Builder(ArenaState arenaState) {
            state = new ArenaState(Optional.ofNullable(arenaState));
        }

        public Builder setBlock(BlockPosition blockPosition, BlockType blockType) {
            state.blocks.put(blockPosition, blockType);
            return this;
        }

        public Builder movePlayer(PlayerId playerId, Direction direction, int step) {
            state.getPlayers()
                    .stream()
                    .filter(player -> player.getId() == playerId)
                    .findFirst()
                    .ifPresent(player -> player.move(direction, step));
            return this;
        }

        public ArenaState get() {
            return state;
        }

        public void clear() {
            state = new ArenaState(Optional.ofNullable(state));
        }
    }
}
