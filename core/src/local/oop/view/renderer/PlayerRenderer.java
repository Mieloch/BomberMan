package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import local.oop.model.player.Direction;
import local.oop.model.Player;
import local.oop.model.player.PlayerId;

import java.util.HashMap;

public class PlayerRenderer extends AbstractRenderer {

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;
    private final static String FRONT_SHEET_PATH = "player_sprites/front";
    private final static String BACK_SHEET_PATH = "player_sprites/back";
    private final static String SIDE_SHEET_PATH = "player_sprites/side";
    private final static String FILE_EXTENSION = ".png";
    private final static float SCALE = 0.492f;


    private HashMap<PlayerId, HashMap<Direction, Animation>> playerAnimationMap;


    public PlayerRenderer() {
        initPlayerAnimationMap();
    }

    public void renderPlayer(Player player) {
        HashMap<Direction, Animation> animationMap = playerAnimationMap.get(player.getId());
        Animation animation = animationMap.get(player.getDirection());
        render(player.getPosition().x, player.getPosition().y, player.getDirection(), animation);
    }


    private void render(float x, float y, Direction direction, Animation animation) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animation.getKeyFrame(stateTime, true);
        sprite.begin();

        if (direction == Direction.LEFT && !frame.isFlipX()) {
            frame.flip(true, false);
        }
        if (frame.isFlipX() && direction == Direction.RIGHT) {
            frame.flip(true, false);
        }
        sprite.draw(frame,xShift+ x, yShift+y, frame.getRegionWidth()*SCALE, frame.getRegionHeight()*SCALE);

        sprite.end();

    }

    private void initPlayerAnimationMap() {
        playerAnimationMap = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            HashMap<Direction, Animation> animationMap = new HashMap<>();
            animationMap.put(Direction.DOWN, createAnimation(FRONT_SHEET_PATH + i + FILE_EXTENSION, FRAME_ROWS, FRAME_COLS, 0.125f));
            animationMap.put(Direction.UP, createAnimation(BACK_SHEET_PATH + i + FILE_EXTENSION, FRAME_ROWS, FRAME_COLS, 0.125f));
            Animation sideAnimSheet = createAnimation(SIDE_SHEET_PATH + i + FILE_EXTENSION, FRAME_ROWS, FRAME_COLS, 0.125f);
            animationMap.put(Direction.LEFT, sideAnimSheet);
            animationMap.put(Direction.RIGHT, sideAnimSheet);
            playerAnimationMap.put(PlayerId.getId(i), animationMap);
        }

    }

}
