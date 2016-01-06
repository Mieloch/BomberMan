package local.oop.view.renderer;

import com.badlogic.gdx.graphics.Texture;
import local.oop.model.BlockType;

import java.util.HashMap;

/**
 * Created by admin on 2015-12-26.
 */
public class BlockRenderer extends AbstractRenderer {

    private static final String SOLID_BLOCK_PATH = "SolidBlock.png";
    private static final String BACKGROUNG_TILE_PATH= "BackgroundTile.png";
    private static final String EXPLODABLE_BLOCK_PATH = "ExplodableBlock.png";
    private final float BLOCK_SIZE;
    private final float SCALE = 0.6f;
    HashMap<BlockType,Texture> blocksMap;

    public BlockRenderer(){
        initBlocksMap();
        BLOCK_SIZE = blocksMap.get(BlockType.BACKGROUNG).getWidth()*SCALE;
    }

    // renderuje texture zaleznie od typu
    public void render(float x, float y, BlockType type){
        Texture blockTexture = blocksMap.get(type);
        sprite.begin();
        sprite.draw(blockTexture,x,y,BLOCK_SIZE,BLOCK_SIZE);
        sprite.end();
    }

    //renderuje caly lvl
    public void renderLevel(float x, float y, BlockType[][] level){
        for(int i=0;i<level.length;i++){
            for(int j=0;j<level[i].length;j++){
               render(x+i*BLOCK_SIZE,y+j*BLOCK_SIZE,level[i][j]);
            }
        }
    }


    // inicializuje mape gdzie kluczem jest typ bloku a wartoscia textura
    private void initBlocksMap(){
        blocksMap = new HashMap<BlockType, Texture>();
        blocksMap.put(BlockType.BACKGROUNG, createTexture(BACKGROUNG_TILE_PATH));
        blocksMap.put(BlockType.SOLID, createTexture(SOLID_BLOCK_PATH));
        Texture exlodableTexture =  createTexture(EXPLODABLE_BLOCK_PATH);

        blocksMap.put(BlockType.EXPLODABLE,exlodableTexture);
        blocksMap.put(BlockType.POWER_UP,exlodableTexture);
        blocksMap.put(BlockType.SPEED_UP,exlodableTexture);
        blocksMap.put(BlockType.EXTRA_BOMB,exlodableTexture);
    }


}
