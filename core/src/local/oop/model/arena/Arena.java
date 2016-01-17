package local.oop.model.arena;

import local.oop.model.ArenaState;
import local.oop.presenter.Presenter;

public interface Arena {
    int MAP_SIZE = 25;
    ArenaState getCurrentState();
    void setPresenter(Presenter presenter);
    void start();
}
