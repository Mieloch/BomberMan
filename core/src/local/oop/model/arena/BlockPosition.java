package local.oop.model.arena;

import local.oop.model.Position;

public class BlockPosition extends Position {

    public static final int SIZE = 32;
    public BlockPosition(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public double distanceFrom(BlockPosition position){
        return Math.sqrt(Math.pow(Math.abs(x- position.x),2) + Math.pow(Math.abs(y- position.y), 2));
    }
}
