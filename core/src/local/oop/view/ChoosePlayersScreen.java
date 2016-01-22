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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import local.oop.GameImpl;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ChoosePlayersScreen implements Screen {

    private GameImpl game;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont font;
    private SpriteBatch batch;
    private String[] list = {"", "Player 1", "Player 2", "Ernest", "Pawel", "Jacek", "Sebastian"};

    public ChoosePlayersScreen(GameImpl game){
        this.game = game;
    }

    private void displayAlertDialog(){
        Window.WindowStyle windowStyle = new Window.WindowStyle(font, new Color(0,0,0,1), skin.getDrawable("window_01"));
        Dialog dialog = new Dialog("Player must have selection", windowStyle){
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

        game.getInputMultiplexer().addProcessor(stage);

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle(skin.getDrawable("textbox_01"), skin.getDrawable("scroll_back_hor"),skin.getDrawable("knob_01"), skin.getDrawable("scroll_back_ver"), skin.getDrawable("knob_02"));
        List.ListStyle listStyle = new List.ListStyle(buttonStyle.font, new Color(0,0,0,1), new Color(0,1,0,1), skin.getDrawable("textbox_02"));
        SelectBoxStyle selectBoxStyle = new SelectBoxStyle(buttonStyle.font, new Color(0,0,0,1),skin.getDrawable("selectbox_01"), scrollPaneStyle, listStyle);
        LabelStyle labelStyle = new LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));

        java.util.List<SelectBox<String>> selectBoxList = new ArrayList<>();
        java.util.List<Label> labelList = new ArrayList<>();
        for(int i = 0; i < game.getPresenter().getPlayersInputCache().getNumberOfPlayers(); i++){
            SelectBox<String> selectBox = new SelectBox<>(selectBoxStyle);
            selectBox.setItems(list);
            selectBox.setSelectedIndex(i+1);
            selectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    for(SelectBox<String> box : selectBoxList){

                        String boxSelected = box.getSelected();
                        /**
                         * It is HIGHLY advised not to do unchecked cast
                         * but in that case we can be sure that actor invoking event is of type SelectBox
                         * We cannot check it though, because SelectBox is of generic type
                         */
                        try {
                            @SuppressWarnings("unchecked")
                                SelectBox<String> actorBox = ((SelectBox<String>) actor);

                            String actorSelected = actorBox.getSelected();
                            if(!box.equals(actorBox)) {
                                if (boxSelected.equals(actorSelected)) {
                                    box.setSelected("");
                                }
                            }
                        } catch (ClassCastException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            selectBoxList.add(selectBox);
            Label player = new Label("Player " + (i+1), labelStyle);
            labelList.add(player);
        }



        TextButton goBack = new TextButton("Go Back", buttonStyle);
        goBack.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayersNumberScreen(game));
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
                boolean showAlertDialog = false;
                for(SelectBox<String> box : selectBoxList){
                    if(box.getSelected().isEmpty()){
                        showAlertDialog = true;
                    }
                }
                if(showAlertDialog){
                    displayAlertDialog();
                } else {
                    game.setScreen(new GameScreen(game));
                    java.util.List selectedPlayerList = selectBoxList.stream().map(e -> e.getSelected()).collect(toList());
                    game.getPresenter().setPlayers(selectedPlayerList);
                    game.getInputMultiplexer().removeProcessor(stage);
                }
            }
        });

        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bomberman_logo.png")))));

        table.add(imageLogo).center().colspan(labelList.size()).expandY().size(imageLogo.getPrefWidth(), imageLogo.getPrefHeight());
        table.row();
        for(Label label : labelList){
            table.add(label).align(Align.center).fillY();
        }
        table.row();
        for(SelectBox<String> selectBox : selectBoxList){
            table.add(selectBox).space(30).fillY();
        }
        table.row();
        Table buttonTable = new Table();
        buttonTable.add(goBack).size(300f, 100f).space(20).align(Align.bottomLeft).expand();
        buttonTable.add(startGame).size(300f, 100f).space(20).align(Align.bottomRight);
        table.add(buttonTable).colspan(labelList.size()).expandY().fill();
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        font.dispose();
        batch.dispose();

    }
}
