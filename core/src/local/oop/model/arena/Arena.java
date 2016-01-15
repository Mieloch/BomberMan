package local.oop.model.arena;

import local.oop.model.ArenaState;
import local.oop.presenter.Presenter;

public interface Arena {
    ArenaState getCurrentState();
    void setPresenter(Presenter presenter);
    void start();
}
