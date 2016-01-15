package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import local.oop.GameImpl;
import local.oop.Level;
import local.oop.model.*;
import local.oop.presenter.Presenter;
import local.oop.view.renderer.BlockRenderer;
import local.oop.view.renderer.BombRenderer;
import local.oop.view.renderer.PlayerRenderer;

import java.util.Map;

public class GameScreen implements Screen {

    private GameImpl game;
    private Presenter presenter;
    private PlayerRenderer playerRenderer;
    private BombRenderer bombRenderer;
    private BlockRenderer blockRenderer;

    public GameScreen(GameImpl game) {
        playerRenderer = new PlayerRenderer();
        bombRenderer = new BombRenderer();
        blockRenderer = new BlockRenderer();
        this.game = game;
        this.presenter = game.getPresenter();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ArenaState arenaState = presenter.getCurrentState();
        renderPlayers(arenaState.getPlayers());
        renderLevel(arenaState.getBlocks());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void renderPlayers(Map<String, PlayerPosition> players) {
        for (PlayerPosition playerPosition : players.values()) {
            playerRenderer.renderPlayer(playerPosition);
        }
    }
    
    private void renderLevel(Map<BlockPosition, BlockType> blocks){
        for (Map.Entry<BlockPosition, BlockType> blockPositionBlockTypeEntry : blocks.entrySet()) {
            blockRenderer.renderBlock(blockPositionBlockTypeEntry.getKey(),blockPositionBlockTypeEntry.getValue());
        }
    }
}
