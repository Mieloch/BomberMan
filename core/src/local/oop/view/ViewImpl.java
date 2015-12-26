package local.oop.view;

import local.oop.model.ArenaState;
import local.oop.model.Direction;

/**
 * Created by echomil on 2015-12-25.
 */
public class ViewImpl implements View {

    PlayerRenderer playerRenderer;
    public ViewImpl() {
        playerRenderer = new PlayerRenderer();
    }

    @Override
    public void renderArena(ArenaState arenaState) {
        playerRenderer.render(50,50, Direction.LEFT);
        playerRenderer.render(100,50, Direction.RIGHT);
        playerRenderer.render(200,50, Direction.UP);
        playerRenderer.render(300,50, Direction.DOWN);
    }

}
