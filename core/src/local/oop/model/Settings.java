package local.oop.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

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
    private Preferences prefs;

    public Settings(){
        prefs = Gdx.app.getPreferences("config");
        playerOneUpKeycode = prefs.getInteger("oneup", Input.Keys.W);
        playerOneDownKeycode = prefs.getInteger("onedown", Input.Keys.S);
        playerOneLeftKeycode = prefs.getInteger("oneleft", Input.Keys.A);
        playerOneRightKeycode = prefs.getInteger("oneright", Input.Keys.D);
        playerOneBombKeycode = prefs.getInteger("onebomb", Input.Keys.SPACE);
        playerTwoUpKeycode = prefs.getInteger("twoup", Input.Keys.UP);
        playerTwoDownKeycode = prefs.getInteger("twodown", Input.Keys.DOWN);
        playerTwoLeftKeycode = prefs.getInteger("twoleft", Input.Keys.LEFT);
        playerTwoRightKeycode = prefs.getInteger("tworight", Input.Keys.RIGHT);
        playerTwoBombKeycode = prefs.getInteger("twobomb", Input.Keys.ENTER);
    }

    public void save(Map<String, Integer> map){
        prefs.put(map);
        prefs.flush();
    }

    public Map<String, Integer> getKeycodesMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("oneup", playerOneUpKeycode);
        map.put("onedown", playerOneDownKeycode);
        map.put("oneleft", playerOneLeftKeycode);
        map.put("oneright", playerOneRightKeycode);
        map.put("onebomb", playerOneBombKeycode);
        map.put("twoup", playerTwoUpKeycode);
        map.put("twodown", playerTwoDownKeycode);
        map.put("twoleft", playerTwoLeftKeycode);
        map.put("tworight", playerTwoRightKeycode);
        map.put("twobomb", playerTwoBombKeycode);
        return map;
    }

    public int getPlayerOneDownKeycode() {
        return playerOneDownKeycode;
    }

    public int getPlayerOneLeftKeycode() {
        return playerOneLeftKeycode;
    }

    public int getPlayerOneRightKeycode() {
        return playerOneRightKeycode;
    }

    public int getPlayerOneUpKeycode() {
        return playerOneUpKeycode;
    }

    public int getPlayerOneBombKeycode() {
        return playerOneBombKeycode;
    }

    public int getPlayerTwoBombKeycode() {
        return playerTwoBombKeycode;
    }

    public int getPlayerTwoDownKeycode() {
        return playerTwoDownKeycode;
    }

    public int getPlayerTwoLeftKeycode() {
        return playerTwoLeftKeycode;
    }

    public int getPlayerTwoRightKeycode() {
        return playerTwoRightKeycode;
    }

    public int getPlayerTwoUpKeycode() {
        return playerTwoUpKeycode;
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
}
