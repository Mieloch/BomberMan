package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import local.oop.GameImpl;
import local.oop.model.ArenaState;
import local.oop.model.arena.*;
import local.oop.model.player.Direction;
import local.oop.model.Player;
import local.oop.model.player.PlayerId;
import local.oop.model.PlayerPosition;
import local.oop.presenter.Presenter;
import local.oop.view.renderer.BlockRenderer;
import local.oop.view.renderer.BombRenderer;
import local.oop.view.renderer.PlayerRenderer;
import local.oop.view.renderer.PowerUpRenderer;

import java.util.List;
import java.util.Map;

public class GameScreen implements Screen {

    private GameImpl game;
    private Presenter presenter;
    private PlayerRenderer playerRenderer;
    private BombRenderer bombRenderer;
    private BlockRenderer blockRenderer;
    private PowerUpRenderer powerUpRenderer;

    public GameScreen(GameImpl game) {
        playerRenderer = new PlayerRenderer();
        bombRenderer = new BombRenderer();
        blockRenderer = new BlockRenderer();
        powerUpRenderer = new PowerUpRenderer();
        this.game = game;
        this.presenter = game.getPresenter();
        presenter.startGame();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ArenaState arenaState = presenter.getCurrentState();
        renderLevel(arenaState.getBlocks());
        renderPlayers(arenaState.getPlayers());
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
        for (Map.Entry<BlockPosition, BlockType> entry : blocks.entrySet()) {
            blockRenderer.renderBlock(entry.getKey(),entry.getValue());
            switch (entry.getValue()){
                case BOMB:
                    bombRenderer.render(entry.getKey().x, entry.getKey().y, BlockType.BOMB);
                    break;
                case FIRE:
                    bombRenderer.render(entry.getKey().x, entry.getKey().y, BlockType.FIRE);
                    break;
                case BOMB_POWERUP:
                    powerUpRenderer.render(entry.getKey().x, entry.getKey().y, BlockType.BOMB_POWERUP);
                    break;
                case FLAME_POWERUP:
                    powerUpRenderer.render(entry.getKey().x, entry.getKey().y, BlockType.FLAME_POWERUP);
                    break;
                case SPEED_POWERUP:
                    powerUpRenderer.render(entry.getKey().x, entry.getKey().y, BlockType.SPEED_POWERUP);
                    break;

            }
        }
    }
}
