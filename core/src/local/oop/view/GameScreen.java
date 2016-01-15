package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import local.oop.GameImpl;
import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;
import local.oop.model.player.Direction;
import local.oop.model.player.Player;
import local.oop.model.player.PlayerId;
import local.oop.model.player.PlayerPosition;
import local.oop.presenter.Presenter;
import local.oop.view.renderer.BlockRenderer;
import local.oop.view.renderer.BombRenderer;
import local.oop.view.renderer.PlayerRenderer;

import java.util.List;
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

    public void renderPlayerTest(){
        playerRenderer.renderPlayer(new Player(PlayerId.PLAYER_1,new PlayerPosition(100,100, Direction.DOWN)));
        playerRenderer.renderPlayer(new Player(PlayerId.PLAYER_2,new PlayerPosition(200,100,Direction.DOWN)));
        playerRenderer.renderPlayer(new Player(PlayerId.PLAYER_3,new PlayerPosition(300,100,Direction.DOWN)));
        playerRenderer.renderPlayer(new Player(PlayerId.PLAYER_4,new PlayerPosition(400,100,Direction.DOWN)));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderPlayerTest();
       /* ArenaState arenaState = presenter.getCurrentState();
        renderPlayers(arenaState.getPlayers());
        renderLevel(arenaState.getBlocks());*/
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

    private void renderPlayers( List<Player> players) {
        for (Player player : players) {
            playerRenderer.renderPlayer(player);
        }
    }
    
    private void renderLevel(Map<BlockPosition, BlockType> blocks){
        for (Map.Entry<BlockPosition, BlockType> blockPositionBlockTypeEntry : blocks.entrySet()) {
            blockRenderer.renderBlock(blockPositionBlockTypeEntry.getKey(),blockPositionBlockTypeEntry.getValue());
        }
    }
}
