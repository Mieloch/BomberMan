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
import com.google.common.base.Utf8;
import local.oop.GameImpl;
import local.oop.model.Settings;

public class OptionsScreen implements Screen {

    private GameImpl game;
    private Stage stage;
    private SpriteBatch batch;
    private InputListener listener;
    private Settings settings;

    public OptionsScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        settings = new Settings();

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
        table.setFillParent(true);

        game.getInputMultiplexer().addProcessor(stage);

        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));

        final TextButton.TextButtonStyle buttonStyle1 = new TextButton.TextButtonStyle();
        buttonStyle1.up = skin.getDrawable("textbox_01");
        buttonStyle1.checkedFontColor = new Color(0.7f, 0, 0.7f, 1);
        buttonStyle1.font = buttonStyle.font;
        buttonStyle1.fontColor = buttonStyle.fontColor;
        Label.LabelStyle labelStyle = new Label.LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));

        Label up = new Label("Move up", labelStyle);
        final TextButton bUp = new TextButton("W", buttonStyle1);
        Label down = new Label("Move down", labelStyle);
        final TextButton bDown = new TextButton("S", buttonStyle1);
        Label left = new Label("Move left", labelStyle);
        final TextButton bLeft = new TextButton("A", buttonStyle1);
        Label right = new Label("Move right", labelStyle);
        final TextButton bRight = new TextButton("D", buttonStyle1);

        listener = new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if(bUp.isChecked()){
                    settings.setUpKeycode(character);
                    bUp.setText(String.valueOf(character));
                    bUp.setChecked(false);
                } else if(bDown.isChecked()){
                    settings.setDownKeycode(character);
                    bDown.setText(String.valueOf(character));
                    bDown.setChecked(false);
                } else if(bLeft.isChecked()){
                    settings.setLeftKeycode(character);
                    bLeft.setText(String.valueOf(character));
                    bLeft.setChecked(false);
                } else if(bRight.isChecked()){
                    settings.setRightKeycode(character);
                    bRight.setText(String.valueOf(character));
                    bRight.setChecked(false);
                }
                return true;
            }
        };

        stage.addCaptureListener(listener);

        ButtonGroup<TextButton> textButtonButtonGroup = new ButtonGroup<TextButton>(bUp, bDown, bLeft, bRight);

        TextButton save = new TextButton("Save settings", buttonStyle);
        save.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                settings.save();
                game.setScreen(new StartScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        TextButton goBack = new TextButton("Go back", buttonStyle);
        goBack.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                game.setScreen(new StartScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });



        table.add(imageLogo).center().colspan(2);
        table.row();
        table.add(up).left().space(38.4f);
        table.add(bUp).right();
        table.row();
        table.add(down).left().space(38.4f);
        table.add(bDown).right();
        table.row();
        table.add(left).left().space(38.4f);
        table.add(bLeft).right();
        table.row();
        table.add(right).left().space(38.4f);
        table.add(bRight).right();
        table.row();
        table.add(goBack).left().size(300f, 100f).fillX();
        table.add(save).right().size(300f, 100f).fillX();
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
