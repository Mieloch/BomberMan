package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import local.oop.model.ArenaState;
import local.oop.model.BlockType;
import local.oop.model.Bomb;
import local.oop.model.Direction;
import local.oop.view.renderer.BlockRenderer;
import local.oop.view.renderer.BombRenderer;
import local.oop.view.renderer.PlayerRenderer;

/**
 * Created by echomil on 2015-12-25.
 */
public class ViewImpl implements View {

    private PlayerRenderer playerRenderer;
    private BombRenderer bombRenderer;
    private BlockRenderer blockRenderer;
    public ViewImpl() {
        playerRenderer = new PlayerRenderer();
        bombRenderer = new BombRenderer();
        blockRenderer = new BlockRenderer();
    }

    @Override
    public void renderArena(ArenaState arenaState) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playerRenderer.render(50,50, Direction.LEFT);
        playerRenderer.render(100,50, Direction.RIGHT);
        playerRenderer.render(200,50, Direction.UP);
        playerRenderer.render(300,50, Direction.DOWN);
        bombRenderer.render(300,300, Bomb.NORMAL);
        bombRenderer.render(300,400, Bomb.FIRE);
        blockRenderer.render(500,500, BlockType.BACKGROUNG);
        blockRenderer.render(500,700, BlockType.EXPLODABLE);
        blockRenderer.render(500,600, BlockType.SOLID);
    }

}
