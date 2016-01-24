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
    public  List<BlockPosition> getPlacesWhereFireCanBe(BlockPosition pos, int pow) {
        boolean blockRight = false;
        boolean blockLeft = false;
        boolean blockUp = false;
        boolean blockDown = false;
        List<BlockPosition> list = new ArrayList<>();
        Map<BlockPosition, BlockType> map = getBlocks();
        for(int i = 0; i<pow; i++){
            BlockPosition right = new BlockPosition(pos.x+i, pos.y);
            BlockPosition left = new BlockPosition(pos.x-i, pos.y);
            BlockPosition up = new BlockPosition(pos.x, pos.y+i);
            BlockPosition down = new BlockPosition(pos.x, pos.y-i);
            BlockType rightType = map.get(right);
            BlockType leftType = map.get(left);
            BlockType upType = map.get(up);
            BlockType downType = map.get(down);
            if(!blockRight)
                blockRight = checkBlock(list, rightType, right);
            if(!blockLeft)
                blockLeft = checkBlock(list, leftType, left);
            if(!blockUp)
                blockUp = checkBlock(list, upType, up);
            if(!blockDown)
                blockDown = checkBlock(list, downType, down);
        }
        return list;

    }

    private boolean checkBlock(List<BlockPosition> list, BlockType type, BlockPosition position){
        if(type != null && type != BlockType.SOLID){
            list.add(position);
            if(type == BlockType.EXPLODABLE)
                return true;
        } else {
            return true;
        }
        return false;
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

        public Builder removePlayer(Player player) {
            state.players = state.getPlayers().stream()
                    .filter(p -> !p.equals(player))
                    .collect(Collectors.toList());
            return this;
        }

        public ArenaState get() {
            return state;
        }
    }
}
