package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import local.oop.model.Bomb;

import java.util.HashMap;

/**
 * Created by admin on 2015-12-26.
 */
public class BombRenderer extends AbstractRenderer{
    private static final String BOMB_SHEET_PATH = "C:\\Users\\admin\\Desktop\\Sprites\\Bomb\\bomb.png";
    private static final String FIRE_SHEET_PATH = "C:\\Users\\admin\\Desktop\\Sprites\\Flame\\fire.png";

    HashMap<Bomb,Animation> bombMap;
    public BombRenderer(){
       initBombMap();
    }

    public void render(float x, float y, Bomb type){
        Animation bomb = bombMap.get(type);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = bomb.getKeyFrame(stateTime,true);
        sprite.begin();
        sprite.draw(frame,x,y);
        sprite.end();
    }

    private void initBombMap(){
        bombMap = new HashMap<Bomb, Animation>();
        bombMap.put(Bomb.FIRE,createAnimation(FIRE_SHEET_PATH,1,5,0.15f));
        bombMap.put(Bomb.NORMAL, createAnimation(BOMB_SHEET_PATH,1,3,0.2f));
    }
}
