package local.oop.view.renderer;

import com.badlogic.gdx.graphics.Texture;
import local.oop.model.BlockType;

import java.util.HashMap;

/**
 * Created by admin on 2015-12-26.
 */
public class BlockRenderer extends AbstractRenderer {

    private static final String SOLID_BLOCK_PATH = "core\\assets\\SolidBlock.png";
    private static final String BACKGROUNG_TILE_PATH= "core\\assets\\BackgroundTile.png";
    private static final String EXPLODABLE_BLOCK_PATH = "core\\assets\\ExplodableBlock.png";

    HashMap<BlockType,Texture> blocksMap;

    public BlockRenderer(){
        initBlocksMap();
    }

    // renderuje texture zaleznie od typu
    public void render(float x, float y, BlockType type){
        Texture blockTexture = blocksMap.get(type);
        sprite.begin();
        sprite.draw(blockTexture,x,y,blockTexture.getHeight()*0.6f,blockTexture.getWidth()*0.6f);
        sprite.end();
    }


    // inicializuje mape gdzie kluczem jest typ bloku a wartoscia textura
    private void initBlocksMap(){
        blocksMap = new HashMap<BlockType, Texture>();
        blocksMap.put(BlockType.BACKGROUNG, createTexture(BACKGROUNG_TILE_PATH));
        blocksMap.put(BlockType.SOLID, createTexture(SOLID_BLOCK_PATH));
        blocksMap.put(BlockType.EXPLODABLE, createTexture(EXPLODABLE_BLOCK_PATH));
    }


}
