package RPG.engine.world;

import RPG.engine.system.AssetManager;
import javafx.scene.image.Image;

public class MapTiler {
    public static Tile[][] subdivise(String name,int tileWidth,int tileHeight) throws Exception {
        Image map=AssetManager.getImages().get(name);
        int rows=(int)(map.getHeight()/tileHeight);
        int cols=(int)(map.getWidth()/tileWidth);
        Tile[][] tiles=new Tile[rows][cols];
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                tiles[row][col]=new Tile(new int[]{row,col});
            }
        }
        return tiles;
    }
}