package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import local.oop.model.arena.Bomb;

import java.util.HashMap;

public class BombRenderer extends AbstractRenderer{
    private static final String BOMB_SHEET_PATH = "bomb.png";
    private static final String FIRE_SHEET_PATH = "fire.png";
    private final float BLOCK_SIZE = 32;

    HashMap<Bomb,Animation> bombMap;
    public BombRenderer(){
       initBombMap();
    }

    public void render(float x, float y, Bomb type){
        Animation bomb = bombMap.get(type);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = bomb.getKeyFrame(stateTime,true);
        sprite.begin();
        sprite.draw(frame,x*BLOCK_SIZE,y*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE);
        sprite.end();
    }

    private void initBombMap(){
        bombMap = new HashMap<>();
        bombMap.put(Bomb.FIRE,createAnimation(FIRE_SHEET_PATH,1,5,0.15f));
        bombMap.put(Bomb.NORMAL, createAnimation(BOMB_SHEET_PATH,1,3,0.2f));
    }
}
