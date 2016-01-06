package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import local.oop.GameImpl;

public class StartScreen implements Screen {

    private GameImpl game;
    private Stage stage;
    private SpriteBatch batch;

    public StartScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui-green.atlas"));
        Skin skin = new Skin();
        skin.addRegions(atlas);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button_01");
        buttonStyle.over = skin.getDrawable("button_02");
        buttonStyle.down = skin.getDrawable("button_03");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("eightbitwonder.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 20;
        buttonStyle.fontColor = new Color(0, 0, 0, 1);
        buttonStyle.font = generator.generateFont(parameter);

        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);

        game.getInputMultiplexer().addProcessor(stage);

        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));


        TextButton play = new TextButton("Play", buttonStyle);
        play.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                game.setScreen(new ChoosePlayersScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        TextButton options = new TextButton("Options", buttonStyle);
        options.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                game.setScreen(new OptionsScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        TextButton exit = new TextButton("Exit", buttonStyle);
        exit.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                Gdx.app.exit();
            }
        });


        table.add(imageLogo).center();
        table.row();
        table.add(play).size(300f, 100f).space(20);
        table.row();
        table.add(options).size(300f, 100f).space(20);
        table.row();
        table.add(exit).size(300f, 100f).space(20);
        stage.addActor(table);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        batch.begin();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
