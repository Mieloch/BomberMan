package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import local.oop.model.arena.BlockType;

import java.util.HashMap;

public class BombRenderer extends AbstractRenderer{
    private static final String BOMB_SHEET_PATH = "bomb.png";
    private static final String FIRE_SHEET_PATH = "fire.png";

    HashMap<BlockType,Animation> bombMap;
    public BombRenderer(){
       initBombMap();
    }

    public void render(float x, float y, BlockType type){
        Animation bomb = bombMap.get(type);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = bomb.getKeyFrame(stateTime,true);
        sprite.begin();
        sprite.draw(frame,x*BlockType.SIZE,y*BlockType.SIZE,BlockType.SIZE,BlockType.SIZE);
        sprite.end();
    }

    private void initBombMap(){
        bombMap = new HashMap<>();
        bombMap.put(BlockType.FIRE,createAnimation(FIRE_SHEET_PATH,1,5,0.15f));
        bombMap.put(BlockType.BOMB, createAnimation(BOMB_SHEET_PATH,1,3,0.2f));
    }
}
