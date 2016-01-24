package local.oop.ai.ernest;

import local.oop.model.arena.BlockType;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private BlockType type;
    public int x;
    public int y;
    private List<Node> outNodes;

    public Node(int x, int y, BlockType type) {
        this.type = type;
        this.outNodes = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = (prime + x) * (result + y);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    public void addOutNode(Node node){
        this.outNodes.add(node);
    }

    public List<Node> getOutNodes() {
        return outNodes;
    }

    public BlockType getType() {
        return type;
    }
}
