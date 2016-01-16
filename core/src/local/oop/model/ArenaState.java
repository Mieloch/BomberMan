package local.oop.model;

import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaState {
    List<Player> players;
    Map<BlockPosition, BlockType> blocks;

    private ArenaState() {
        this(Optional.empty());

    }

    private ArenaState(Optional<ArenaState> arenaState) {
        if(arenaState.isPresent()) {
            arenaState.ifPresent(state -> {
                players = new ArrayList<>(arenaState.get().getPlayers());
                blocks = new HashMap<>(arenaState.get().getBlocks());
            });
        }
        else{
            if(players == null){
                players = new ArrayList<>();
            }
            if(blocks == null){
                blocks = new HashMap<>();
            }

        }

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
            state.players
                    .stream()
                    .filter(player -> player.getId() == playerId)
                    .findFirst()
                    .ifPresent(player -> player.move(direction, step));
            return this;
        }

        public Builder addPlayer(Player player) {
            if (state.players.stream().noneMatch(p -> p.equals(player))) {
                state.players.add(player);
            } else {
                throw new RuntimeException("Player already exists");
            }
            return this;
        }

        public Builder removePlayer(Player player) {
            state.players = state.getPlayers().stream()
                    .filter(p -> p.equals(player))
                    .collect(Collectors.toList());
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
