package local.oop.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import javax.inject.Named;
import java.awt.event.KeyEvent;

@Named("PlayerInputProcessor")
public class PlayerInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}
