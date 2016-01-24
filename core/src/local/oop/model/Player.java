package local.oop.model;

import local.oop.model.arena.ArenaImpl;
import local.oop.model.arena.BlockPosition;
import local.oop.model.player.Direction;
import local.oop.model.player.PlayerId;

public class Player {
    private PlayerPosition position;
    private final PlayerId id;
    private int speed;
    private int power;
    private int bombs;
    private int lives;
    public final static String PAWEL = "Pawel";
    public final static String ERNEST = "Ernest";
    public final static String JACEK = "Jacek";
    public final static String SEBASTIAN = "Sebastian";
    public final static String PLAYER_1 = "Player 1";
    public final static String PLAYER_2 = "Player 2";
    private String name = "Pawel Mieloch";

    public Player(PlayerId id){
        this.id = id;
        this.lives = 3;
        this.speed = 2;
        this.power = 3;
        this.bombs = 1;
        resetPosition();
    }


    public Player(PlayerId id, PlayerPosition position) {
        this.id = id;
        this.position = position;
    }

    public BlockPosition getBlockPosition(){
        return new BlockPosition(position.x/BlockPosition.SIZE, position.y/BlockPosition.SIZE);
    }

    private void resetPosition() {
        int blockSize = BlockPosition.SIZE;
        int MAP_SIZE = ArenaImpl.MAP_SIZE;
        switch (id){
            case PLAYER_1:
                position = new PlayerPosition(0,0, Direction.DOWN);
                break;
            case PLAYER_2:
                position = new PlayerPosition((MAP_SIZE-1) *blockSize,0,Direction.DOWN);
                break;
            case PLAYER_3:
                position = new PlayerPosition(0,(MAP_SIZE-1)*blockSize,Direction.DOWN);
                break;
            case PLAYER_4:
                position = new PlayerPosition((MAP_SIZE-1)*blockSize,(MAP_SIZE-1)*blockSize,Direction.DOWN);
                break;
        }
    }

    public void move(Direction direction, int step) {
        switch (direction) {
            case UP:
                position = position.moveUp(step);
                break;
            case DOWN:
                position = position.moveDown(step);
                break;
            case LEFT:
                position = position.moveLeft(step);
                break;
            case RIGHT:
                position = position.moveRight(step);
                break;
        }
    }

    public Direction getDirection() {
        return position.getDirection();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Player that = (Player) obj;

        return this.id == that.id;
    }

    public int getLives() {
        return lives;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public void decrementBombs() {
        bombs--;
    }

    public void incrementBombs() {
        bombs++;
    }

    public void die() {
        lives--;
        resetPosition();
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PlayerId getId() {
        return id;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
