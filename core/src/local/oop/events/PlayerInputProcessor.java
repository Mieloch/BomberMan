package local.oop.events;

import com.badlogic.gdx.InputAdapter;

import javax.inject.Named;

@Named("PlayerInputProcessor")
public class PlayerInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}
