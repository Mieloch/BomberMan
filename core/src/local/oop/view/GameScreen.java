package local.oop.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import local.oop.GameImpl;
import local.oop.model.ArenaState;
import local.oop.model.arena.*;
import local.oop.model.Player;
import local.oop.presenter.Presenter;
import local.oop.view.renderer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameScreen extends AbstractScreen {

    private Presenter presenter;
    private PlayerRenderer playerRenderer;
    private BombRenderer bombRenderer;
    private BlockRenderer blockRenderer;
    private PowerUpRenderer powerUpRenderer;
    private InfoRenderer infoRenderer;

    public GameScreen(GameImpl game) {
        this.game = game;
        playerRenderer = new PlayerRenderer();
        bombRenderer = new BombRenderer();
        blockRenderer = new BlockRenderer();
        powerUpRenderer = new PowerUpRenderer();
        infoRenderer= new InfoRenderer();
        this.presenter = game.getPresenter();
        presenter.startGame();
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
        infoRenderer.render(arenaState.getPlayers());
        stage.act(delta);
        stage.draw();

        batch.begin();
        batch.end();
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
        TextButton button = new TextButton("Play again", buttonStyle);
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

        TextButton button1 = new TextButton("Exit", buttonStyle);
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
        List<Button> buttons = new ArrayList<>();
        buttons.add(button);
        buttons.add(button1);
        displayAlertDialog("Game over", player.getName() + " wins", buttons);
        presenter.getCurrentState().reset();
    }
}
