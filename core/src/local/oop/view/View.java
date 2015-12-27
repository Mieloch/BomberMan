package local.oop.view;

import local.oop.model.ArenaState;

/**
 * Created by echomil on 2015-12-25.
 */
public interface View {

    //renderuje plansze na podstawie jej stanu
    public void renderArena(ArenaState arenaState);
}
