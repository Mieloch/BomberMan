package local.oop.model;

import local.oop.presenter.Presenter;

public interface Arena {
    ArenaState getCurrentState();
    void setPresenter(Presenter presenter);
    void start();
}
