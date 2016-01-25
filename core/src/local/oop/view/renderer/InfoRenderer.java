package local.oop.view.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import local.oop.model.Player;
import local.oop.model.Position;
import local.oop.model.arena.BlockType;
import local.oop.model.player.PlayerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoRenderer extends AbstractRenderer {
    private BitmapFont font;
    private Map<PlayerId, RenderPosition> idPositionMap;

    public InfoRenderer() {
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        idPositionMap= new HashMap<>();
        idPositionMap.put(PlayerId.PLAYER_1, new RenderPosition(xShift, yShift-50));
        idPositionMap.put(PlayerId.PLAYER_2, new RenderPosition(xShift , yShift-80));
        idPositionMap.put(PlayerId.PLAYER_3, new RenderPosition(xShift+212, yShift - 50));
        idPositionMap.put(PlayerId.PLAYER_4, new RenderPosition(xShift+212, yShift -80));
    }

    public void render(List<Player> players) {
        for (Player player : players) {
            RenderPosition pos = idPositionMap.get(player.getId());
            sprite.begin();
            String toWrite = player.getName() + ": " + player.getLives();
            font.draw(sprite, toWrite, player.getPosition().x - font.getSpaceWidth()*toWrite.length()/2 + xShift, player.getPosition().y+ BlockType.SIZE*2 + yShift);
            font.draw(sprite, player.getName(), pos.x, pos.y);
            font.draw(sprite,"lives: "+ player.getLives(), pos.x+100, pos.y);
            sprite.end();
        }
    }

    private class RenderPosition {
        RenderPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }

        float x, y;
    }
}
