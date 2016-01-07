package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import local.oop.GameImpl;
import local.oop.Level;
import local.oop.model.BlockType;
import local.oop.model.Bomb;
import local.oop.model.Direction;
import local.oop.presenter.Presenter;
import local.oop.view.renderer.BlockRenderer;
import local.oop.view.renderer.BombRenderer;
import local.oop.view.renderer.PlayerRenderer;

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

    private void renderTest(){
        playerRenderer.render(50,50, Direction.LEFT);
        playerRenderer.render(100,50, Direction.RIGHT);
        playerRenderer.render(200,50, Direction.UP);
        playerRenderer.render(300,50, Direction.DOWN);
        bombRenderer.render(300,300, Bomb.NORMAL);
        bombRenderer.render(300,400, Bomb.FIRE);

    }

    private void levelRenderTest(){
        Level l = new Level(15,15);
       BlockType[][] lev =  l.getEnumLevel();
        blockRenderer.renderLevel(100,100,lev);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        levelRenderTest();
        renderTest();
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
}
