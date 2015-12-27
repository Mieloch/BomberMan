package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import local.oop.model.Direction;

import java.util.HashMap;


/**
 * KLASA RENDERUJACA ANIMACJE GRACZA
 * Created by echomil on 2015-12-26.
 */
public class PlayerRenderer extends AbstractRenderer{

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS= 2;
    private final String FRONT_SHEET_PATH = "C:\\Users\\admin\\Desktop\\Sprites\\Bomberman 3\\all\\front3.png";
    private final String BACK_SHEET_PATH = "C:\\Users\\admin\\Desktop\\Sprites\\Bomberman 3\\all\\back3.png";
    private final String SIDE_SHEET_PATH = "C:\\Users\\admin\\Desktop\\Sprites\\Bomberman 3\\all\\side3.png";

    HashMap<Direction, Animation> animationMap;


    public PlayerRenderer(){
        initAnimationMap();
    }

    //renderuje animacje gracza zaleznie od jego kierunku
    public void render(float x, float y, Direction direction){
        Animation animation = animationMap.get(direction);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animation.getKeyFrame(stateTime,true);
        sprite.begin();
        if(direction == Direction.LEFT && !frame.isFlipX()){
            frame.flip(true,false);
        }
        if(frame.isFlipX() && direction == Direction.RIGHT){
            frame.flip(true,false);
        }
        sprite.draw(frame,x,y,frame.getRegionWidth()/2,frame.getRegionHeight()/2);
        sprite.end();
    }





    //tworzy mape gdzie kluczem jest kierunek a wartoscia animacja ruchu dla tego kierunku
    private void initAnimationMap(){
        animationMap = new HashMap<Direction, Animation>();
        animationMap.put(Direction.DOWN,createAnimation(FRONT_SHEET_PATH,FRAME_ROWS,FRAME_COLS,0.125f));
        animationMap.put(Direction.UP,createAnimation(BACK_SHEET_PATH,FRAME_ROWS,FRAME_COLS,0.125f));
        Animation sideAnimSheet = createAnimation(SIDE_SHEET_PATH,FRAME_ROWS,FRAME_COLS,0.125f);
        animationMap.put(Direction.LEFT,sideAnimSheet);
        animationMap.put(Direction.RIGHT,sideAnimSheet);
    }

}
