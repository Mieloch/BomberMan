package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import local.oop.GameImpl;

public class StartScreen extends AbstractScreen {

    public StartScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        table.setFillParent(true);

        TextButton play = new TextButton("Play", buttonStyle);
        play.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                game.setScreen(new PlayersNumberScreen(game));
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
    }
}
