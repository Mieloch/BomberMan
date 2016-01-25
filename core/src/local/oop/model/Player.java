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
    private int bombsLimit;
    private int lives;
    private boolean invincible;
    public final static String PAWEL = "Pawel";
    public final static String ERNEST = "Ernest";
    public final static String JACEK = "Jacek";
    public final static String SEBASTIAN = "Sebastian";
    private String name = "Pawel Mieloch";

    public Player(PlayerId id){
        this.id = id;
        this.lives = 3;
        this.speed = 2;
        this.power = 3;
        this.bombs = 1;
        this.bombsLimit = 1;
        resetPosition();
    }

    public BlockPosition getBlockPosition(){
        return new BlockPosition((position.x+(BlockPosition.SIZE/2))/BlockPosition.SIZE, (position.y+(BlockPosition.SIZE/2))/BlockPosition.SIZE);
    }

    private void resetPosition() {
        int blockSize = BlockPosition.SIZE;
        int MAP_SIZE = ArenaImpl.MAP_SIZE;
        switch (id){
            case PLAYER_1:
                position = new PlayerPosition(blockSize,blockSize, Direction.DOWN);
                break;
            case PLAYER_2:
                position = new PlayerPosition((MAP_SIZE-2) *blockSize,blockSize,Direction.DOWN);
                break;
            case PLAYER_3:
                position = new PlayerPosition(blockSize,(MAP_SIZE-2)*blockSize,Direction.DOWN);
                break;
            case PLAYER_4:
                position = new PlayerPosition((MAP_SIZE-2)*blockSize,(MAP_SIZE-2)*blockSize,Direction.DOWN);
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
        this.bombsLimit = bombs;
    }

    public int getBombsLimit() {
        return bombsLimit;
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

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
