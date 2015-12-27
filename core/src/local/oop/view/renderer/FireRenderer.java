package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by admin on 2015-12-27.
 */
public class FireRenderer extends AbstractRenderer {
    private Animation fireAnimation;


    public FireRenderer(){
    }

    public void render(float x, float y){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = fireAnimation.getKeyFrame(stateTime,true);
        sprite.begin();
        sprite.draw(frame,x,y);
        sprite.end();
    }

}
