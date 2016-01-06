package local.oop.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    private int upKeycode;
    private int downKeycode;
    private int leftKeycode;
    private int rightKeycode;
    private Preferences prefs;

    public Settings(){
        prefs = Gdx.app.getPreferences("config");
        upKeycode = prefs.getInteger("up", Input.Keys.W);
        downKeycode = prefs.getInteger("down", Input.Keys.S);
        leftKeycode = prefs.getInteger("left", Input.Keys.A);
        rightKeycode = prefs.getInteger("right", Input.Keys.D);
    }

    public void save(){
        prefs.putInteger("up", upKeycode);
        prefs.putInteger("down", downKeycode);
        prefs.putInteger("left", leftKeycode);
        prefs.putInteger("right", rightKeycode);
        prefs.flush();
    }

    public int getDownKeycode() {
        return downKeycode;
    }

    public int getLeftKeycode() {
        return leftKeycode;
    }

    public int getRightKeycode() {
        return rightKeycode;
    }

    public int getUpKeycode() {
        return upKeycode;
    }

    public void setDownKeycode(int downKeycode) {
        this.downKeycode = downKeycode;
    }

    public void setLeftKeycode(int leftKeycode) {
        this.leftKeycode = leftKeycode;
    }

    public void setRightKeycode(int rightKeycode) {
        this.rightKeycode = rightKeycode;
    }

    public void setUpKeycode(int upKeycode) {
        this.upKeycode = upKeycode;
    }
}
