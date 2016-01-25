package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import local.oop.model.Settings;

import java.util.ArrayList;

public class AbstractScreen implements Screen{

    final static float LABEL_SPACING = 30f;
    final static float TEXT_BOX_WIDTH = 300f;
    final static float TEXT_BOX_HEIGHT = 30f;
    final static float BUTTON_WIDTH = 200f;
    final static float BUTTON_HEIGHT = 80f;
    GameImpl game;
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;
    Skin skin;
    Settings settings;
    TextureAtlas atlas;
    TextButton.TextButtonStyle buttonStyle;
    Dialog dialog;
    Label.LabelStyle labelStyle;
    SelectBox.SelectBoxStyle selectBoxStyle;
    List.ListStyle listStyle;
    ScrollPane.ScrollPaneStyle scrollPaneStyle;
    Table table;
    Image imageLogo;

    @Override
    public void show() {
        stage = new Stage();
        batch = new SpriteBatch();
        settings = new Settings();
        table = new Table();
        atlas = new TextureAtlas(Gdx.files.internal("ui_green.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("eightbitwonder.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        generator.dispose();
        imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button_01");
        buttonStyle.over = skin.getDrawable("button_02");
        buttonStyle.down = skin.getDrawable("button_03");
        buttonStyle.font = font;
        buttonStyle.fontColor = new Color(0, 0, 0, 1);
        scrollPaneStyle = new ScrollPane.ScrollPaneStyle(skin.getDrawable("textbox_01"), skin.getDrawable("scroll_back_hor"),skin.getDrawable("knob_01"), skin.getDrawable("scroll_back_ver"), skin.getDrawable("knob_02"));
        listStyle = new List.ListStyle(buttonStyle.font, new Color(0,0,0,1), new Color(0,1,0,1), skin.getDrawable("textbox_02"));
        selectBoxStyle = new SelectBox.SelectBoxStyle(buttonStyle.font, new Color(0,0,0,1),skin.getDrawable("selectbox_01"), scrollPaneStyle, listStyle);
        labelStyle = new Label.LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));
        game.getInputMultiplexer().addProcessor(stage);
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        style.checkboxOff = skin.getDrawable("icon_sound_off");
        style.checkboxOn = skin.getDrawable("icon_sound_on");
        style.font = font;
        style.fontColor = new Color( 1, 1, 1 ,1);
        CheckBox checkBox = new CheckBox(null, style);
        checkBox.setX(0);
        checkBox.setY(Gdx.graphics.getHeight() - checkBox.getHeight());
        checkBox.setChecked(!game.isMuted());
        checkBox.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.mute(!checkBox.isChecked());
            }
        });
        stage.addActor(checkBox);
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
        batch.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }

    void displayAlertDialog(String title, String description, java.util.List<Button> buttons){
        Window.WindowStyle windowStyle = new Window.WindowStyle(font, new Color(0,0,0,1), skin.getDrawable("window_01"));
        dialog = new Dialog(title, windowStyle){
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

        Label label = new Label(description, new Label.LabelStyle(font, new Color(0, 0, 0, 1)));
        label.setAlignment(Align.center);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_01");
        textButtonStyle.over = skin.getDrawable("button_02");
        textButtonStyle.down = skin.getDrawable("button_03");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = new Color(0,0,0,1);

        if(buttons == null)
            buttons = new ArrayList<>();

        if(buttons.isEmpty()) {
            TextButton button = new TextButton("Ok", textButtonStyle);
            button.addListener(new InputListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dialog.hide();
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }
            });
            buttons.add(button);
        }
        dialog.getContentTable().add(label).center();
        for (Button button : buttons) {
            dialog.getButtonTable().add(button).center().size(BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        dialog.show(stage);
        stage.addActor(dialog);
    }
}
