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
    Player winner = null;
    boolean finnish = false;

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

    public void reset(){
        players = new ArrayList<>();
        blocks = new HashMap<>();
        finnish = false;
        winner = null;
    }

    public void finnish(Player winner){
        this.winner = winner;
        this.finnish = true;
    }

    public boolean isFinnish(){
        return finnish;
    }

    public Player getWinner(){
        return winner;
    }

    public Map<BlockPosition, BlockType> getBlocks() {
        return blocks;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getPlayer(PlayerId playerId) {
        return players.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElse(null);
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

        public Builder clearBlock(BlockPosition blockPosition, BlockType previousBlockType) {
            switch (previousBlockType){
                case POWER_UP:
                    state.blocks.put(blockPosition, BlockType.FLAME_POWERUP);
                    break;
                case SPEED_UP:
                    state.blocks.put(blockPosition, BlockType.SPEED_POWERUP);
                    break;
                case EXTRA_BOMB:
                    state.blocks.put(blockPosition, BlockType.BOMB_POWERUP);
                    break;
                case BOMB:
                    state.blocks.put(blockPosition, BlockType.BOMB);
                default:
                    state.blocks.put(blockPosition, BlockType.BACKGROUND);
            }
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
                    .filter(p -> !p.equals(player))
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
