package local.oop.ai.ernest;

import local.oop.ai.AbstractAI;
import local.oop.model.ArenaState;
import local.oop.model.Command;
import local.oop.model.Player;
import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.PlayerId;

import java.util.*;
import java.util.stream.Collectors;

public class ErnestAI extends AbstractAI {

    private Player me;
    private ArenaState currentState;
    private List<Node> nodes;
    private BlockPosition lastPlace;

    public ErnestAI(PlayerId id) {
        super(id);
    }

    @Override
    public Command makeMove(ArenaState state) {
        currentState = state;
        createNodes();
        if(me == null)
            me = state.getPlayer(id);
        for (BlockPosition bomb : getBombsOnMyAxis()) {
            if(!distanceFromBombIsSafe(bomb) && noBlocksBetweenUs(bomb)){
                return getSafeMove(bomb);
            }
        }

        if(me.getBombsLimit() > me.getBombs())
            lastPlace = null;

        if(me.getBombs()>0) {
            BlockPosition block = getSafePlaceForBomb();
            if (block != null) {
                if (block.equals(me.getBlockPosition())) {
                    return Command.BOMB;
                }
                return goToBlock(block);
            }
            BlockPosition playerBlock = getPlaceForBombingPlayer();
            if (playerBlock != null) {
                if (playerBlock.equals(me.getBlockPosition()))
                    return Command.BOMB;
                return goToBlock(playerBlock);
            }
        }

        List<BlockPosition> powerUp = getPowerUp();
        if (powerUp != null && powerUp.size()>0) {
            for (BlockPosition blockPosition : powerUp) {
                Command toReturn = goToBlock(blockPosition);
                if(toReturn!=null) {
                    return toReturn;
                }
            }
        }
        return null;
    }

    private List<BlockPosition> getPowerUp(){
        return currentState
                .getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == BlockType.BOMB_POWERUP || e.getValue() == BlockType.SPEED_POWERUP || e.getValue() == BlockType.FLAME_POWERUP).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private BlockPosition getPlaceForBombingPlayer(){
        BlockPosition myPosition = me.getBlockPosition();
        return currentState
                    .getPlayers()
                    .stream()
                    .min(Comparator.comparing((player1) -> player1.getBlockPosition().distanceFrom(myPosition)))
                    .get()
                    .getBlockPosition();
    }

    private List<Node> getDirections(Node start, Node finish){
        Map<Node, Boolean> vis;
        Map<Node, Node> prev;
        List<Node> directions = new LinkedList<>();
        Queue<Node> q = new LinkedList<>();
        Node current = start;
        vis = new HashMap<>();
        prev = new HashMap<>();
        q.add(current);
        vis.put(current, true);
        while(!q.isEmpty()){
            current = q.remove();
            if (current != null) {
                if (current.equals(finish)) {
                    break;
                } else {
                    for (Node node : current.getOutNodes()) {
                        if (!vis.containsKey(node)) {
                            q.add(node);
                            vis.put(node, true);
                            prev.put(node, current);
                        }
                    }
                }
            }
        }
        if (current != null && !current.equals(finish)){
            return null;
        }
        for(Node node = finish; node != null; node = prev.get(node)) {
            directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

    private void createNodes(){
        Map<BlockPosition, BlockType> blocks = currentState
                .getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getValue() != BlockType.SOLID)
                .filter(e -> e.getValue() != BlockType.EXPLODABLE)
                .filter(e -> e.getValue() != BlockType.POWER_UP)
                .filter(e -> e.getValue() != BlockType.EXTRA_BOMB)
                .filter(e -> e.getValue() != BlockType.SPEED_UP)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        nodes = new ArrayList<>();
        for (Map.Entry<BlockPosition, BlockType> entry : blocks.entrySet()) {
            BlockPosition block = entry.getKey();
            nodes.add(new Node(block.x, block.y, entry.getValue()));
        }
        for (Node node : nodes) {
            nodes.stream().forEach(node1 -> {
                if (node.x == node1.x) {
                    if (node.y == node1.y + 1 || node.y == node1.y - 1)
                        node.addOutNode(node1);
                }
                if (node.y == node1.y) {
                    if (node.x == node1.x + 1 || node.x == node1.x - 1)
                        node.addOutNode(node1);
                }
            });
        }
    }

    private Command goToBlock(BlockPosition block){
        BlockPosition myPosition = me.getBlockPosition();
        Node start = nodes.stream().filter(node -> node.x == myPosition.x)
                .filter(node -> node.y == myPosition.y).findFirst().orElse(null);
        Node end = nodes.stream().filter(node -> node.x == block.x)
                .filter(node -> node.y == block.y).findFirst().orElse(null);
        List<Node> list = getDirections(start, end);
        if(list != null && list.size()>1) {
            Command command = getDirection(list.get(0), list.get(1));
            if (command != null)
                switch (command) {
                    case DOWN:
                        if(!isSafe(myPosition) || isSafe(new BlockPosition(myPosition.x, myPosition.y-1)))
                            return Command.DOWN;
                        break;
                    case UP:
                        if(!isSafe(myPosition) || isSafe(new BlockPosition(myPosition.x, myPosition.y+1)))
                            return Command.UP;
                        break;
                    case LEFT:
                        if(!isSafe(myPosition) || isSafe(new BlockPosition(myPosition.x-1, myPosition.y)))
                            return Command.LEFT;
                        break;
                    case RIGHT:
                        if(!isSafe(myPosition) || isSafe(new BlockPosition(myPosition.x+1, myPosition.y)))
                            return Command.RIGHT;
                        break;
                }
        }
        return null;
    }

    private boolean isSafe(BlockPosition block){
        List<BlockPosition> list = currentState.getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == BlockType.BOMB)
                .filter(e -> e.getKey().x == block.x ||
                        e.getKey().y == block.y)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return list.isEmpty();
    }

    private Command getDirection(Node start, Node end){
        if(start.x == end.x){
            if(start.y > end.y)
                return Command.DOWN;
            if(start.y < end.y)
                return Command.UP;
        }
        if(start.y == end.y){
            if(start.x > end.x)
                return Command.LEFT;
            if(start.x < end.x)
                return Command.RIGHT;
        }
        return null;
    }

    private BlockPosition getSafePlaceForBomb(){
        if(lastPlace!=null)
            return lastPlace;
        BlockPosition myPosition = me.getBlockPosition();
        List<BlockPosition> blocks = currentState
                .getBlocks()
                .entrySet()
                .stream()
                .filter(e -> Math.abs(e.getKey().x - myPosition.x)<6)
                .filter(e -> Math.abs(e.getKey().y - myPosition.y)<6)
                .filter(e -> e.getValue() == BlockType.BACKGROUND)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        Collections.shuffle(blocks);
        for (BlockPosition block : blocks) {
            for (BlockPosition blockPosition : blocks) {
                if(goToBlock(blockPosition)!=null && block.x != blockPosition.x && block.y != blockPosition.y && isSurrounded(blockPosition)){
                    lastPlace = blockPosition;
                    return blockPosition;
                }
            }
        }
        return null;
    }

    private boolean isSurrounded(BlockPosition block){
        Map<BlockPosition, BlockType> map = currentState.getBlocks().entrySet().stream().filter(e -> Math.sqrt(Math.pow(Math.abs(e.getKey().x- block.x),2) + Math.pow(Math.abs(e.getKey().y- block.y), 2)) < 2 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for(Map.Entry<BlockPosition, BlockType> entry : map.entrySet()) {
            BlockPosition blockPosition = entry.getKey();
            BlockType type = entry.getValue();
            if (block.x == blockPosition.x) {
                if (block.y == blockPosition.y + 1 || block.y == blockPosition.y - 1)
                    if(type == BlockType.EXPLODABLE)
                        return true;
            }
            if (block.y == blockPosition.y) {
                if (block.x == blockPosition.x + 1 || block.x == blockPosition.x - 1)
                    if(type == BlockType.EXPLODABLE)
                        return true;
            }
        }
        return false;
    }

    private boolean isPath(BlockPosition first, BlockPosition second){
        Node start = nodes.stream().filter(node -> node.x == first.x)
                .filter(node -> node.y == first.y).findFirst().orElse(null);
        Node end = nodes.stream().filter(node -> node.x == second.x)
                .filter(node -> node.y == second.y).findFirst().orElse(null);
        List<Node> list = getDirections(start, end);
        return list != null && list.size()>0;
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
        List<BlockPosition> positionList = currentState
                .getBlocks()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().x != bomb.x)
                .filter(e -> e.getKey().y != bomb.y)
                .filter(e -> e.getValue() == BlockType.BACKGROUND)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for (BlockPosition blockPosition : positionList) {
            Command toReturn = goToBlock(blockPosition);
            if (toReturn != null)
                return toReturn;
        }

        return null;
    }


    private boolean noBlocksBetweenUs(BlockPosition bomb){
        BlockPosition myPosition = me.getBlockPosition();
        if(myPosition.x == bomb.x)
            return currentState.getBlocks()
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().x == myPosition.x)
                    .filter(e -> e.getKey().y > ((myPosition.y < bomb.y) ? myPosition.y : bomb.y))
                    .filter(e -> e.getKey().y < ((myPosition.y < bomb.y) ? bomb.y : myPosition.y))
                    .filter(e -> e.getValue() != BlockType.BACKGROUND)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                    .isEmpty();
        else if(myPosition.y == bomb.y) {
            List<BlockPosition> list = currentState.getBlocks()
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().y == myPosition.y)
                    .filter(e -> e.getKey().x > ((myPosition.x < bomb.x) ? myPosition.x : bomb.x))
                    .filter(e -> e.getKey().x < ((myPosition.x < bomb.x) ? bomb.x : myPosition.x))
                    .filter(e -> e.getValue() != BlockType.BACKGROUND)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            return list.isEmpty();
        }
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
