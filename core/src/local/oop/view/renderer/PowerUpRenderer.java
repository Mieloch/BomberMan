package local.oop.view.renderer;

import com.badlogic.gdx.graphics.Texture;
import local.oop.model.arena.PowerUp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ernest on 17.01.2016.
 */
public class PowerUpRenderer extends AbstractRenderer{
    private static final String BOMB_POWERUP_PATH = "bomb_powerup.png";
    private static final String FLAME_POWERUP_PATH = "flame_powerup.png";
    private static final String SPEED_POWERUP_PATH = "speed_powerup.png";
    private final static float BLOCK_SIZE = 32;
    private Map<PowerUp, Texture> powerUpMap;

    public PowerUpRenderer(){
        initPowerupMap();
    }

    public void render(float x, float y, PowerUp type){
        sprite.begin();
        sprite.draw(powerUpMap.get(type),x*BLOCK_SIZE,y*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE);
        sprite.end();
    }

    private void initPowerupMap(){
        powerUpMap = new HashMap<>();
        powerUpMap.put(PowerUp.BOMB, createTexture(BOMB_POWERUP_PATH));
        powerUpMap.put(PowerUp.FLAME, createTexture(FLAME_POWERUP_PATH));
        powerUpMap.put(PowerUp.SPEED, createTexture(SPEED_POWERUP_PATH));
    }
}
