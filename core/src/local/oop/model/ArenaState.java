package local.oop.model;

import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;
import local.oop.presenter.Presenter;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaState {
    List<Player> players;
    Map<BlockPosition, BlockType> blocks;
    Presenter presenter;

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
        if(players.isEmpty() && presenter != null){
            for (int i = 1; i <= presenter.getPlayersInputCache().getNumberOfPlayers(); i++) {
                players.add(new Player(PlayerId.getId(i)));
            }
        }
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
            this((ArenaState) null);
        }

        public Builder(ArenaState arenaState) {
            state = new ArenaState(Optional.ofNullable(arenaState));
        }

        public Builder(Map<BlockPosition, BlockType> blocks) {
            state = new ArenaState();
            state.blocks = blocks;
        }

        public Builder setPresenter(Presenter presenter){
            state.presenter = presenter;
            return this;
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

        public Builder clearBlock(BlockPosition blockPosition) {
            state.blocks.put(blockPosition, BlockType.BACKGROUND);
            return this;
        }

        public Builder addPlayers(List<Player> players){
            players.forEach(this::addPlayer);
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
