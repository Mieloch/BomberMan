package local.oop.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import local.oop.model.arena.ArenaImpl;
import local.oop.model.arena.BlockPosition;
import local.oop.model.arena.BlockType;

import java.util.HashMap;

public class BlockRenderer extends AbstractRenderer {

    private static final String SOLID_BLOCK_PATH = "solid_block.png";
    private static final String BACKGROUNG_TILE_PATH= "background_tile.png";
    private static final String EXPLODABLE_BLOCK_PATH = "explodable_block.png";
    HashMap<BlockType,Texture> blocksMap;

    public BlockRenderer(){
        initBlocksMap();
    }

    public void renderBlock(BlockPosition position, BlockType type){
        Texture blockTexture = blocksMap.get(type);
        if(blockTexture==null)
            blockTexture = blocksMap.get(BlockType.BACKGROUND);
        sprite.begin();
        sprite.draw(blockTexture,xShift + position.x*BlockType.SIZE,yShift + position.y*BlockType.SIZE,BlockType.SIZE,BlockType.SIZE);
        sprite.end();
    }

    private void initBlocksMap(){
        blocksMap = new HashMap<>();
        blocksMap.put(BlockType.BACKGROUND, createTexture(BACKGROUNG_TILE_PATH));
        blocksMap.put(BlockType.SOLID, createTexture(SOLID_BLOCK_PATH));
        Texture explodableTexture =  createTexture(EXPLODABLE_BLOCK_PATH);

        blocksMap.put(BlockType.EXPLODABLE,explodableTexture);
        blocksMap.put(BlockType.POWER_UP,explodableTexture);
        blocksMap.put(BlockType.SPEED_UP,explodableTexture);
        blocksMap.put(BlockType.EXTRA_BOMB,explodableTexture);
    }


}
