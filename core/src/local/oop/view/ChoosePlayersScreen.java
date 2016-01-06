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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import local.oop.GameImpl;

public class ChoosePlayersScreen implements Screen {

    private GameImpl game;
    private Stage stage;
    private SpriteBatch batch;
    private String[] list = {"Player", "None", "Ernest", "Pawel", "Jacek", "Sebastian"};

    public ChoosePlayersScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui-green.atlas"));
        Skin skin = new Skin();
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

        LabelStyle labelStyle = new LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));
        Label player1 = new Label("Player 1", labelStyle);
        Label player2 = new Label("Player 2", labelStyle);
        Label player3 = new Label("Player 3", labelStyle);
        Label player4 = new Label("Player 4", labelStyle);


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle(skin.getDrawable("textbox_01"), skin.getDrawable("scroll_back_hor"),skin.getDrawable("knob_01"), skin.getDrawable("scroll_back_ver"), skin.getDrawable("knob_02"));
        List.ListStyle listStyle = new List.ListStyle(buttonStyle.font, new Color(0,0,0,1), new Color(0,1,0,1), skin.getDrawable("textbox_02"));
        SelectBoxStyle selectBoxStyle = new SelectBoxStyle(buttonStyle.font, new Color(0,0,0,1),skin.getDrawable("selectbox_01"), scrollPaneStyle, listStyle);
        SelectBox<String> selectBox1 = new SelectBox<String>(selectBoxStyle);
        selectBox1.setItems(list);
        SelectBox<String> selectBox2 = new SelectBox<String>(selectBoxStyle);
        selectBox2.setItems(list);
        SelectBox<String> selectBox3 = new SelectBox<String>(selectBoxStyle);
        selectBox3.setItems(list);
        SelectBox<String> selectBox4 = new SelectBox<String>(selectBoxStyle);
        selectBox4.setItems(list);


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

        TextButton startGame = new TextButton("Start Game", buttonStyle);
        startGame.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));

        table.add(imageLogo).center().colspan(4).expandY();
        table.row();
        table.add(player1).align(Align.center);
        table.add(player2).align(Align.center);
        table.add(player3).align(Align.center);
        table.add(player4).align(Align.center);
        table.row();
        table.add(selectBox1).space(30);
        table.add(selectBox2).space(30);
        table.add(selectBox3).space(30);
        table.add(selectBox4).space(30);
        table.row();
        table.add(goBack).size(300f, 100f).space(20).colspan(2).align(Align.bottomLeft).expandY();
        table.add(startGame).size(300f, 100f).space(20).colspan(2).align(Align.bottomRight);
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

    }

    @Override
    public void dispose() {

    }
}
