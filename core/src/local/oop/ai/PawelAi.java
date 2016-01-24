package local.oop.ai;

import local.oop.model.ArenaState;
import local.oop.model.Command;
import local.oop.model.Player;
import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.PlayerId;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Echomil on 2016-01-24.
 */
public class PawelAi extends AbstractAI {

    private ArenaState state;
    private Player player;
    private BlockPosition playerBlock;
    private List<BlockPosition> trace;
    Map<BlockPosition, Boolean> visited;
    Map<BlockPosition, BlockPosition> prev;
    Map<BlockPosition, List<BlockPosition>> graph;
    boolean back = false;


    public PawelAi(PlayerId id) {
        super(id);
    }


    private Map<BlockPosition, List<BlockPosition>> createGraph() {
        Map<BlockPosition, List<BlockPosition>> graph = new HashMap<>();
        state.getBlocks().entrySet().stream().filter(e -> e.getValue() != BlockType.SOLID).forEach(e -> graph.put(e.getKey(), findOutBlocks(e.getKey(), BlockType.BACKGROUND)));
        return graph;
    }

    private List<BlockPosition> findFirstSafeSpot(BlockPosition start, List<BlockPosition> notSafe) {
        List<BlockPosition> directions = new LinkedList<>();
        BlockPosition stop = null;
        Queue<BlockPosition> q = new LinkedList<>();
        BlockPosition current = start;
        visited = new HashMap<>();
        prev = new HashMap<>();
        q.add(current);
        visited.put(current, true);
        while (!q.isEmpty()) {
            current = q.remove();
            final BlockPosition tmp = current;
            if (notSafe.stream().noneMatch(e -> e.equals(tmp)) && state.getBlocks().entrySet().stream().anyMatch(e -> e.getKey().equals(tmp) && e.getValue().equals(BlockType.BACKGROUND))) {

                stop = tmp;
                break;

            } else {
                List<BlockPosition> nastepniki = graph.get(current);
                for (BlockPosition blockPosition : nastepniki) {
                    if (!visited.containsKey(blockPosition)) {
                        prev.put(blockPosition, current);
                        q.add(blockPosition);
                        visited.put(blockPosition, true);
                    }
                }
            }

        }

        for (BlockPosition node = stop; node != null; node = prev.get((node))) {
            directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

    private List<BlockPosition> findFirstBlockToDestroy(BlockPosition start) {
        List<BlockPosition> directions = new LinkedList<>();
        BlockPosition stop = null;
        Queue<BlockPosition> q = new LinkedList<>();
        BlockPosition current = start;
        visited = new HashMap<>();
        prev = new HashMap<>();
        q.add(current);
        visited.put(current, true);
        while (!q.isEmpty()) {
            current = q.remove();
            final BlockPosition tmp = current;
            List<BlockPosition> explodables = findOutBlocks(current, BlockType.EXPLODABLE);
            if (state.getBlocks().entrySet().stream().anyMatch(e -> e.getKey().equals(tmp) && e.getValue().equals(BlockType.BACKGROUND) && !explodables.isEmpty())) {

                stop = tmp;
                break;

            } else {
                List<BlockPosition> nastepniki = graph.get(current);
                for (BlockPosition blockPosition : nastepniki) {
                    if (!visited.containsKey(blockPosition)) {
                        prev.put(blockPosition, current);
                        q.add(blockPosition);
                        visited.put(blockPosition, true);
                    }
                }
            }

        }

        for (BlockPosition node = stop; node != null; node = prev.get((node))) {
            directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

    private List<BlockPosition> findPathToEnemy(BlockPosition start, BlockPosition stop) {
        List<BlockPosition> directions = new LinkedList<>();
        Queue<BlockPosition> q = new LinkedList<>();
        BlockPosition current = start;
        visited = new HashMap<>();
        prev = new HashMap<>();
        q.add(current);
        visited.put(current, true);
        while (!q.isEmpty()) {
            current = q.remove();

            if (current.equals(stop)) {
                break;
            } else {
                List<BlockPosition> nastepniki = graph.get(current);
                for (BlockPosition blockPosition : nastepniki) {
                    if (!visited.containsKey(blockPosition)) {
                        prev.put(blockPosition, current);
                        q.add(blockPosition);
                        visited.put(blockPosition, true);
                    }
                }
            }

        }
        if (!current.equals(stop)) {
            return null;
        }
        for (BlockPosition node = stop; node != null; node = prev.get((node))) {
            directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

/*    private BlockPosition tmp(BlockPosition position, BlockPosition target, List<BlockPosition> odwiedzone){
       List<BlockPosition> nastepnicy = findOutBlocks(position);
        odwiedzone.add(position);
        for (BlockPosition blockPosition : nastepnicy) {
            if(odwiedzone.indexOf(blockPosition) != -1){
                tmp(blockPosition,target,odwiedzone);
            }

        }
    }*/

    private List<BlockPosition> findOutBlocks(BlockPosition position, BlockType type) {
        List<BlockPosition> outBlocks = new ArrayList<>();
        if (isTypeOnPosition(position.x + 1, position.y, type)) {
            outBlocks.add(new BlockPosition(position.x + 1, position.y));
        }
        if (isTypeOnPosition(position.x, position.y + 1, type)) {
            outBlocks.add(new BlockPosition(position.x, position.y + 1));
        }
        if (isTypeOnPosition(position.x - 1, position.y, type)) {
            outBlocks.add(new BlockPosition(position.x - 1, position.y));
        }
        if (isTypeOnPosition(position.x, position.y - 1, type)) {
            outBlocks.add(new BlockPosition(position.x, position.y - 1));
        }
        return outBlocks;
    }

    private Command getDirection(BlockPosition toVisit) {

        int xDiff = playerBlock.x - toVisit.x;
        int yDiff = playerBlock.y - toVisit.y;
        if (xDiff > 0) {
            return Command.LEFT;
        } else if (xDiff < 0) {
            return Command.RIGHT;
        }
        if (yDiff > 0) {
            return Command.DOWN;
        } else if (yDiff < 0) {
            return Command.UP;
        }
        trace.remove(0);
        if (trace.isEmpty()) {
            if (back) {
                back = false;
                return null;
            } else {
                back = true;
                return Command.BOMB;
            }
        }
        return getDirection(trace.get(0));
    }

    /*    private List<BlockPosition> findDangerousPlaces(){

        }*/

    private List<BlockPosition> createNotSafeList() {
        List<BlockPosition> notSafe = new ArrayList<>(40);
        List<BlockPosition> bombs = state.getBlocks().entrySet().stream().filter(e -> e.getValue().equals(BlockType.BOMB)).map(e -> e.getKey()).collect(Collectors.toList());
        for (BlockPosition bomb : bombs) {
            notSafe.addAll(state.getPlacesWhereFireCanBe(bomb, 5));
        }
        return notSafe;
    }

    @Override
    public Command makeMove(ArenaState state) {
        this.state = state;
        player = state.getPlayer(this.id);
        playerBlock = player.getPosition().getBlockPosition();
        graph = createGraph();
        List<BlockPosition> enemyPositions = state.getPlayers().stream().filter(e -> e.getId() != this.id).map(e -> e.getPosition().getBlockPosition()).collect(Collectors.toList());
        if (enemyPositions.isEmpty()) {
            trace = findFirstSafeSpot(playerBlock, createNotSafeList());
        } else {
            BlockPosition enemyBlock = enemyPositions.get(new Random().nextInt(enemyPositions.size()));
            if (trace == null || trace.isEmpty()) {
                if (back) {
                    trace = findFirstSafeSpot(playerBlock, createNotSafeList());
                } else {
                    trace = findPathToEnemy(playerBlock, enemyBlock);
                    if (trace == null || trace.isEmpty()) {
                        trace = findFirstBlockToDestroy(playerBlock);
                    }
                }
            }
        }

        if (trace != null && !trace.isEmpty()) {
            return getDirection(trace.get(0));

        } else {
            return null;
        }


    }

    private boolean isTypeOnPosition(int x, int y, BlockType type) {
        return state.getBlocks().entrySet().stream().anyMatch(e -> e.getKey().equals(new BlockPosition(x, y)) && e.getValue().equals(type));
    }


}
