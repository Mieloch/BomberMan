package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
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
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont font;
    private SpriteBatch batch;

    public GameScreen(GameImpl game) {
        playerRenderer = new PlayerRenderer();
        bombRenderer = new BombRenderer();
        blockRenderer = new BlockRenderer();
        powerUpRenderer = new PowerUpRenderer();
        this.game = game;
        this.presenter = game.getPresenter();
        batch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("ui_green.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("eightbitwonder.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        stage = new Stage();
        presenter.startGame();
        game.getInputMultiplexer().addProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ArenaState arenaState = presenter.getCurrentState();
        if(arenaState.isFinnish()){
            displayAlertDialog(arenaState.getWinner());
        } else {
            renderLevel(arenaState.getBlocks());
            renderPlayers(arenaState.getPlayers());
        }

        stage.act(delta);
        stage.draw();

        batch.begin();
        batch.end();
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

    private void displayAlertDialog(Player player){
        Window.WindowStyle windowStyle = new Window.WindowStyle(font, new Color(0,0,0,1), skin.getDrawable("window_01"));
        Dialog dialog = new Dialog("Game over", windowStyle){
            @Override
            public float getPrefWidth() {
                return Gdx.graphics.getWidth() / 2;
            }

            @Override
            public float getPrefHeight() {
                return Gdx.graphics.getHeight() / 3;
            }
        };
        dialog.pad(40f);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

        Label label = new Label(player.getName() + " wins", new Label.LabelStyle(font, new Color(0, 0, 0, 1)));
        label.setAlignment(Align.center);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_01");
        textButtonStyle.over = skin.getDrawable("button_02");
        textButtonStyle.down = skin.getDrawable("button_03");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = new Color(0,0,0,1);
        TextButton button = new TextButton("Play again", textButtonStyle);
        button.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                throw new IllegalStateException(); // tak jest dobrze
            }
        });

        TextButton button1 = new TextButton("Exit", textButtonStyle);
        button1.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        dialog.getContentTable().add(label).center();
        dialog.getButtonTable().add(button).center().size(200f, 80f);
        dialog.getButtonTable().add(button1).center().size(200f, 80f);
        dialog.show(stage);
        stage.addActor(dialog);
        presenter.getCurrentState().reset();
    }
}
