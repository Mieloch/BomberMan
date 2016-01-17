package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import local.oop.GameImpl;
import local.oop.model.Settings;

import java.util.HashMap;
import java.util.Map;

public class OptionsScreen implements Screen {

    private GameImpl game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;
    private InputListener listener;
    private Settings settings;
    private TextureAtlas atlas;
    private static float labelSpacing = 30f;
    private static float textBoxWidth = 300f;
    private static float textBoxHeight = 30f;
    private Map<TextButton, String> map;

    public OptionsScreen(GameImpl game){
        this.game = game;
    }

    private void displayAlertDialog(){
        Window.WindowStyle windowStyle = new Window.WindowStyle(font, new Color(0,0,0,1), skin.getDrawable("window_01"));
        Dialog dialog = new Dialog("Settings must not be empty", windowStyle){
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

        Label label = new Label("Please correct your settings", new Label.LabelStyle(font, new Color(0, 0, 0, 1)));
        label.setAlignment(Align.center);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_01");
        textButtonStyle.over = skin.getDrawable("button_02");
        textButtonStyle.down = skin.getDrawable("button_03");
        textButtonStyle.font = font;
        textButtonStyle.fontColor = new Color(0,0,0,1);
        TextButton button = new TextButton("Ok", textButtonStyle);
        button.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialog.hide();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        dialog.getContentTable().add(label).center();
        dialog.getButtonTable().add(button).center().size(200f, 80f);
        dialog.show(stage);
        stage.addActor(dialog);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        settings = new Settings();

        atlas = new TextureAtlas(Gdx.files.internal("ui_green.atlas"));
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
        font = generator.generateFont(parameter);
        buttonStyle.font = font;

        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        Table scrollTable = new Table();

        game.getInputMultiplexer().addProcessor(stage);

        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));

        final TextButton.TextButtonStyle buttonStyle1 = new TextButton.TextButtonStyle();
        buttonStyle1.up = skin.getDrawable("textbox_01");
        buttonStyle1.checkedFontColor = new Color(0.7f, 0, 0.7f, 1);
        buttonStyle1.font = buttonStyle.font;
        buttonStyle1.fontColor = buttonStyle.fontColor;
        Label.LabelStyle labelStyle = new Label.LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));

        Label playerOne = new Label("Player One", labelStyle);
        Label lOneUp = new Label("Move up", labelStyle);
        final TextButton bOneUp = new TextButton(settings.oneUp(), buttonStyle1);
        Label lOneDown = new Label("Move down", labelStyle);
        final TextButton bOneDown = new TextButton(settings.oneDown(), buttonStyle1);
        Label lOneLeft = new Label("Move left", labelStyle);
        final TextButton bOneLeft = new TextButton(settings.oneLeft(), buttonStyle1);
        Label lOneRight = new Label("Move right", labelStyle);
        final TextButton bOneRight = new TextButton(settings.oneRight(), buttonStyle1);
        Label lOneBomb = new Label("Plant bomb", labelStyle);
        final TextButton bOneBomb = new TextButton(settings.oneBomb(), buttonStyle1);

        Label playerTwo = new Label("Player Two", labelStyle);
        Label lTwoUp = new Label("Move up", labelStyle);
        final TextButton bTwoUp = new TextButton(settings.twoUp(), buttonStyle1);
        Label lTwoDown = new Label("Move down", labelStyle);
        final TextButton bTwoDown = new TextButton(settings.twoDown(), buttonStyle1);
        Label lTwoLeft = new Label("Move left", labelStyle);
        final TextButton bTwoLeft = new TextButton(settings.twoLeft(), buttonStyle1);
        Label lTwoRight = new Label("Move right", labelStyle);
        final TextButton bTwoRight = new TextButton(settings.twoRight(), buttonStyle1);
        Label lTwoBomb = new Label("Plant bomb", labelStyle);
        final TextButton bTwoBomb = new TextButton(settings.twoBomb(), buttonStyle1);

        map = new HashMap<>();
        map.put(bOneUp, "oneup");
        map.put(bOneDown, "onedown");
        map.put(bOneLeft, "oneleft");
        map.put(bOneRight, "oneright");
        map.put(bOneBomb, "onebomb");
        map.put(bTwoUp, "twoup");
        map.put(bTwoDown, "twodown");
        map.put(bTwoLeft, "twoleft");
        map.put(bTwoRight, "tworight");
        map.put(bTwoBomb, "twobomb");



        final ButtonGroup<TextButton> textButtonButtonGroup = new ButtonGroup<>(bOneUp, bOneDown, bOneLeft, bOneRight, bOneBomb, bTwoUp, bTwoDown, bTwoLeft, bTwoRight, bTwoBomb);
        textButtonButtonGroup.setMaxCheckCount(1);
        textButtonButtonGroup.setMinCheckCount(0);

        listener = new InputListener(){

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                TextButton checkedButton = textButtonButtonGroup.getChecked();
                if(checkedButton!=null) {
                    for(TextButton button : textButtonButtonGroup.getButtons()){
                        if(button.getText().toString().equals(Input.Keys.toString(keycode))){
                            button.setText("");
                        }
                    }
                    checkedButton.setText(Input.Keys.toString(keycode));
                    checkedButton.setChecked(false);
                }
                return true;
            }
        };

        stage.addCaptureListener(listener);

        TextButton save = new TextButton("Save settings", buttonStyle);
        save.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                Map<String, Integer> settingsMap = new HashMap<>();
                boolean displayalert = false;
                for(TextButton button1 : textButtonButtonGroup.getButtons()){
                    if(button1.getText().toString().isEmpty()){
                        displayalert = true;
                    }
                    settingsMap.put(map.get(button1), Input.Keys.valueOf(button1.getText().toString()));
                }
                if(displayalert){
                    displayAlertDialog();
                } else {
                    settings.save(settingsMap);
                    game.setScreen(new StartScreen(game));
                    game.getInputMultiplexer().removeProcessor(stage);
                }
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


        ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle();
        scrollPaneStyle.vScroll = skin.getDrawable("scroll_back_hor");
        scrollPaneStyle.vScrollKnob = skin.getDrawable("knob_04");

        scrollTable.add(playerOne).colspan(2);
        scrollTable.row();
        scrollTable.add(lOneUp).space(labelSpacing).left().expandX();
        scrollTable.add(bOneUp).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lOneDown).space(labelSpacing).left().expandX();
        scrollTable.add(bOneDown).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lOneLeft).space(labelSpacing).left().expandX();
        scrollTable.add(bOneLeft).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lOneRight).space(labelSpacing).left().expandX();
        scrollTable.add(bOneRight).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lOneBomb).space(labelSpacing).left().expandX();
        scrollTable.add(bOneBomb).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);

        scrollTable.row();

        scrollTable.add(playerTwo).colspan(2);
        scrollTable.row();
        scrollTable.add(lTwoUp).space(labelSpacing).left().expandX();
        scrollTable.add(bTwoUp).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lTwoDown).space(labelSpacing).left().expandX();
        scrollTable.add(bTwoDown).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lTwoLeft).space(labelSpacing).left().expandX();
        scrollTable.add(bTwoLeft).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lTwoRight).space(labelSpacing).left().expandX();
        scrollTable.add(bTwoRight).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.row();
        scrollTable.add(lTwoBomb).space(labelSpacing).left().expandX();
        scrollTable.add(bTwoBomb).space(labelSpacing).right().size(textBoxWidth, textBoxHeight);
        scrollTable.pad(50f);
//        scrollTable.padRight(50f);

        ScrollPane scrollPane = new ScrollPane(scrollTable, scrollPaneStyle);
        scrollPane.setVariableSizeKnobs(false);

        table.add(imageLogo).center().colspan(2).minHeight(imageLogo.getPrefHeight());
        table.row();
        table.add(scrollPane).colspan(2).fillX();
        table.row();
        table.add(goBack).left().size(300f, 100f).fillX();
        table.add(save).right().size(300f, 100f).fillX();
        table.pad(50f);


        stage.addActor(table);
        stage.setScrollFocus(scrollPane);
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
        batch.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
