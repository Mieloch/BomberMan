package local.oop.model;

import java.util.HashMap;
import java.util.Map;

public class ArenaState {
    Map<String, PlayerPosition> players;
    Map<BlockPosition, BlockType> blocks;

    private ArenaState() {
        players = new HashMap<>();
        blocks = new HashMap<>();
    }

    private ArenaState(ArenaState arenaState) {
        players = new HashMap<>(arenaState.getPlayers());
        blocks = new HashMap<>(arenaState.getBlocks());
    }

    public Map<BlockPosition, BlockType> getBlocks() {
        return blocks;
    }

    public Map<String, PlayerPosition> getPlayers() {
        return players;
    }

    public static class Builder {
        private ArenaState state;

        public Builder() {
            state = new ArenaState();
        }

        public Builder(ArenaState arenaState) {
            state = new ArenaState(arenaState);
        }

        public Builder setBlock(BlockPosition blockPosition, BlockType blockType) {
            state.blocks.put(blockPosition, blockType);
            return this;
        }

        public Builder setPlayer(String playerId, PlayerPosition playerPosition) {
            state.players.put(playerId, playerPosition);
            return this;
        }

        public ArenaState get() {
            return state;
        }
    }
}
