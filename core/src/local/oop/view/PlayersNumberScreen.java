package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import local.oop.GameImpl;
import local.oop.view.ChoosePlayersScreen;
import local.oop.view.StartScreen;

public class PlayersNumberScreen implements Screen {

    private GameImpl game;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private String[] list = {"2", "3", "4"};


    public PlayersNumberScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        atlas = new TextureAtlas(Gdx.files.internal("ui-green.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button_01");
        buttonStyle.over = skin.getDrawable("button_02");
        buttonStyle.down = skin.getDrawable("button_03");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("eightbitwonder.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        buttonStyle.fontColor = new Color(0, 0, 0, 1);
        buttonStyle.font = generator.generateFont(parameter);

        stage = new Stage();
        Table table = new Table();

        game.getInputMultiplexer().addProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));
        Label player1 = new Label("Number of players", labelStyle);


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle(skin.getDrawable("textbox_01"), skin.getDrawable("scroll_back_hor"),skin.getDrawable("knob_01"), skin.getDrawable("scroll_back_ver"), skin.getDrawable("knob_02"));
        List.ListStyle listStyle = new List.ListStyle(buttonStyle.font, new Color(0,0,0,1), new Color(0,1,0,1), skin.getDrawable("textbox_02"));
        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle(buttonStyle.font, new Color(0,0,0,1),skin.getDrawable("selectbox_01"), scrollPaneStyle, listStyle);
        SelectBox<String> selectBox = new SelectBox<>(selectBoxStyle);
        selectBox.setItems(list);


        TextButton goBack = new TextButton("Go Back", buttonStyle);
        goBack.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StartScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        TextButton choosePlayers = new TextButton("next", buttonStyle);
        choosePlayers.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.getPresenter().getPlayerManager().setPlayerCount(Integer.valueOf(selectBox.getSelected()));
                game.setScreen(new ChoosePlayersScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));

        table.add(imageLogo).center().expandY().colspan(2);
        table.row();
        table.add(player1).align(Align.center).colspan(2);
        table.row();
        table.add(selectBox).space(30).colspan(2);
        table.row();
        table.add(goBack).size(300f, 100f).space(20).align(Align.bottomLeft).expandY();
        table.add(choosePlayers).size(300f, 100f).space(20).align(Align.bottomRight);
        table.setFillParent(true);
        table.pad(50f);
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
        batch.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();

    }
}
