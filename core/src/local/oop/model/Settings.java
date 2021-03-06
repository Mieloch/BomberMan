package local.oop.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Settings {
    private int playerOneUpKeycode;
    private int playerOneDownKeycode;
    private int playerOneLeftKeycode;
    private int playerOneRightKeycode;
    private int playerOneBombKeycode;
    private int playerTwoUpKeycode;
    private int playerTwoDownKeycode;
    private int playerTwoLeftKeycode;
    private int playerTwoRightKeycode;
    private int playerTwoBombKeycode;
    private boolean allowRepeatedPlayers = false;
    private int gameSpeed = 15;
    private Preferences prefs;
    private static Settings instance;

    public static Settings getInstance() {
        if(instance == null){
            instance = new Settings();
        }
        return instance;
    }

    private Settings(){
        if(prefs==null){
            prefs = Gdx.app.getPreferences("config");
        }
        loadValues();

    }

    public void save(Map<String, Integer> map){
        prefs.clear();
        prefs.put(map);
        prefs.flush();
        loadValues();
    }

    public Map<String, Integer> getKeycodesMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put(Setting.ONE_UP.toString(), playerOneUpKeycode);
        map.put(Setting.ONE_DOWN.toString(), playerOneDownKeycode);
        map.put(Setting.ONE_LEFT.toString(), playerOneLeftKeycode);
        map.put(Setting.ONE_RIGHT.toString(), playerOneRightKeycode);
        map.put(Setting.ONE_BOMB.toString(), playerOneBombKeycode);
        map.put(Setting.TWO_UP.toString(), playerTwoUpKeycode);
        map.put(Setting.TWO_DOWN.toString(), playerTwoDownKeycode);
        map.put(Setting.TWO_LEFT.toString(), playerTwoLeftKeycode);
        map.put(Setting.TWO_RIGHT.toString(), playerTwoRightKeycode);
        map.put(Setting.TWO_BOMB.toString(), playerTwoBombKeycode);
        return map;
    }

    private void loadValues(){
        playerOneUpKeycode = prefs.getInteger(Setting.ONE_UP.toString(), Input.Keys.W);
        playerOneDownKeycode = prefs.getInteger(Setting.ONE_DOWN.toString(), Input.Keys.S);
        playerOneLeftKeycode = prefs.getInteger(Setting.ONE_LEFT.toString(), Input.Keys.A);
        playerOneRightKeycode = prefs.getInteger(Setting.ONE_RIGHT.toString(), Input.Keys.D);
        playerOneBombKeycode = prefs.getInteger(Setting.ONE_BOMB.toString(), Input.Keys.SPACE);
        playerTwoUpKeycode = prefs.getInteger(Setting.TWO_UP.toString(), Input.Keys.UP);
        playerTwoDownKeycode = prefs.getInteger(Setting.TWO_DOWN.toString(), Input.Keys.DOWN);
        playerTwoLeftKeycode = prefs.getInteger(Setting.TWO_LEFT.toString(), Input.Keys.LEFT);
        playerTwoRightKeycode = prefs.getInteger(Setting.TWO_RIGHT.toString(), Input.Keys.RIGHT);
        playerTwoBombKeycode = prefs.getInteger(Setting.TWO_BOMB.toString(), Input.Keys.ENTER);
        allowRepeatedPlayers = prefs.getBoolean("allowRepeatedPlayers", false);
        gameSpeed = prefs.getInteger("speed", 15);
    }

    public String oneUp(){
        return Input.Keys.toString(playerOneUpKeycode);
    }

    public String oneDown(){
        return Input.Keys.toString(playerOneDownKeycode);
    }

    public String oneLeft(){
        return Input.Keys.toString(playerOneLeftKeycode);
    }

    public String oneRight(){
        return Input.Keys.toString(playerOneRightKeycode);
    }

    public String oneBomb(){
        return Input.Keys.toString(playerOneBombKeycode);
    }
    public String twoUp(){
        return Input.Keys.toString(playerTwoUpKeycode);
    }

    public String twoDown(){
        return Input.Keys.toString(playerTwoDownKeycode);
    }

    public String twoLeft(){
        return Input.Keys.toString(playerTwoLeftKeycode);
    }
    public String twoRight(){
        return Input.Keys.toString(playerTwoRightKeycode);
    }
    public String twoBomb(){
        return Input.Keys.toString(playerTwoBombKeycode);
    }

    public void setAllowRepeatedPlayers(boolean allowRepeatedPlayers) {
        this.allowRepeatedPlayers = allowRepeatedPlayers;
        prefs.putBoolean("allowRepeatedPlayers", allowRepeatedPlayers);
        prefs.flush();
    }

    public boolean isAllowRepeatedPlayers() {
        return allowRepeatedPlayers;
    }

    public void setSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
        prefs.putInteger("speed", gameSpeed);
        prefs.flush();
    }

    public int getSpeed() {
        return gameSpeed;
    }
}
