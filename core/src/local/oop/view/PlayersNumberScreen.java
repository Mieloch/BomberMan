package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import local.oop.GameImpl;

public class PlayersNumberScreen extends AbstractScreen {

    private String[] list = {"2", "3", "4"};

    public PlayersNumberScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        Label player1 = new Label("Number of players", labelStyle);
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
                game.getPresenter().getPlayersInputCache().setPlayerCount(Integer.valueOf(selectBox.getSelected()));
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
    }
}
