package local.oop.view.renderer;

import com.badlogic.gdx.graphics.Texture;
import local.oop.model.arena.BlockType;

import java.util.HashMap;
import java.util.Map;

public class PowerUpRenderer extends AbstractRenderer{
    private static final String BOMB_POWERUP_PATH = "bomb_powerup.png";
    private static final String FLAME_POWERUP_PATH = "flame_powerup.png";
    private static final String SPEED_POWERUP_PATH = "speed_powerup.png";
    private Map<BlockType, Texture> powerUpMap;

    public PowerUpRenderer(){
        initPowerupMap();
    }

    public void render(float x, float y, BlockType type){
        sprite.begin();
        sprite.draw(powerUpMap.get(type),x*BlockType.SIZE,y*BlockType.SIZE,BlockType.SIZE,BlockType.SIZE);
        sprite.end();
    }

    private void initPowerupMap(){
        powerUpMap = new HashMap<>();
        powerUpMap.put(BlockType.BOMB_POWERUP, createTexture(BOMB_POWERUP_PATH));
        powerUpMap.put(BlockType.FLAME_POWERUP, createTexture(FLAME_POWERUP_PATH));
        powerUpMap.put(BlockType.SPEED_POWERUP, createTexture(SPEED_POWERUP_PATH));
    }
}
