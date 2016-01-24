package local.oop.ai;

import jdk.nashorn.internal.ir.Block;
import local.oop.model.ArenaState;
import local.oop.model.Command;
import local.oop.model.Player;
import local.oop.model.PlayerPosition;
import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.PlayerId;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErnestAI extends AbstractAI implements AI {

    private Player me;
    private ArenaState currentState;

    public ErnestAI(PlayerId id) {
        super(id);
    }

    @Override
    public Command makeMove(ArenaState state) {
        currentState = state;
        if(me == null)
            me = state.getPlayer(id);
        List<Player> players = state.getPlayers();
        for (BlockPosition bomb : getBombsOnMyAxis()) {
            if(!distanceFromBombIsSafe(bomb)){
                return getSafeMove(bomb);
            }
        }
        return null;
    }

    private List<BlockPosition> getBombsOnMyAxis(){
        return currentState.getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == BlockType.BOMB)
                .filter(e -> e.getKey().x == me.getBlockPosition().x ||
                        e.getKey().y == me.getBlockPosition().y)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private int getMaxPower(){
        return currentState.getPlayers().stream().mapToInt(Player::getPower).max().orElse(me.getPower());
    }

    private Command getSafeMove(BlockPosition bomb){
        if(noBlocksBetweenUs(bomb)){
            if(me.getBlockPosition().x == bomb.x){
                if( currentState
                        .getBlocks()
                        .get(new BlockPosition(me.getBlockPosition().x, me.getBlockPosition().y+1)) == BlockType.BACKGROUND)
                    return Command.UP;
                if( currentState
                        .getBlocks()
                        .get(new BlockPosition(me.getBlockPosition().x, me.getBlockPosition().y-1)) == BlockType.BACKGROUND)
                    return Command.DOWN;
            }
        }

        return Command.getRandomMove();
    }


    private boolean noBlocksBetweenUs(BlockPosition bomb){
        BlockPosition myPosition = me.getBlockPosition();
        if(myPosition.x == bomb.x)
            return currentState.getBlocks()
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().y > ((myPosition.y < bomb.y) ? myPosition.y : bomb.y))
                    .filter(e -> e.getKey().y < ((myPosition.y < bomb.y) ? bomb.y : myPosition.y))
                    .filter(e -> e.getValue() != BlockType.BACKGROUND)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                    .isEmpty();
        else if(myPosition.y == bomb.y)
            return currentState.getBlocks()
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().x > ((myPosition.x < bomb.x) ? myPosition.x : bomb.x))
                    .filter(e -> e.getKey().x < ((myPosition.x < bomb.x) ? bomb.x : myPosition.x))
                    .filter(e -> e.getValue() != BlockType.BACKGROUND)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                    .isEmpty();
        return false;
    }

    private int getDistanceX(BlockPosition bomb){
        return Math.abs(bomb.x - me.getBlockPosition().x);
    }

    private int getDistanceY(BlockPosition bomb){
        return Math.abs(bomb.y - me.getBlockPosition().y);
    }

    private boolean distanceFromBombIsSafe(BlockPosition bomb){
        return Math.max(getDistanceX(bomb), getDistanceY(bomb)) > getMaxPower();
    }
}
