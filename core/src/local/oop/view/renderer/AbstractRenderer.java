package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import local.oop.model.arena.ArenaImpl;
import local.oop.model.arena.BlockType;

public abstract class AbstractRenderer {
    SpriteBatch sprite;
    float stateTime;
    float xShift;
    float yShift;

    public AbstractRenderer() {
        xShift = (Gdx.graphics.getWidth() - ArenaImpl.MAP_SIZE* BlockType.SIZE)/2;
        yShift = (Gdx.graphics.getHeight() - ArenaImpl.MAP_SIZE*BlockType.SIZE)/2;
        sprite = new SpriteBatch();
        stateTime = 0;
    }

    Texture createTexture(String texturePath) {
        Texture texture = new Texture(texturePath);

        return texture;
    }

    Animation createAnimation(String sheetPath, int frameRows, int frameCols, float duration) {
        Animation animation;
        Texture forwardSheet = new Texture(Gdx.files.internal(sheetPath));
        TextureRegion[][] tmp = TextureRegion.split(forwardSheet, forwardSheet.getWidth() / frameCols, forwardSheet.getHeight() / frameRows);
        TextureRegion[] forwardFrames = new TextureRegion[frameRows * frameCols];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                forwardFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(duration, forwardFrames);
        return animation;
    }


}
