package main;

import Objects.chest;
import Objects.door;
import Objects.key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0] = new key();
        gp.obj[0].worldX = 5 * gp.tileSize;
        gp.obj[0].worldY = 5 * gp.tileSize;

        gp.obj[1] = new key();
        gp.obj[1].worldX = 7 * gp.tileSize;
        gp.obj[1].worldY = 7 * gp.tileSize;

        gp.obj[2] = new door();
        gp.obj[2].worldX = 6 * gp.tileSize;
        gp.obj[2].worldY = 6 * gp.tileSize;

        gp.obj[3] = new chest();
        gp.obj[3].worldX = 5 * gp.tileSize;
        gp.obj[3].worldY = 4 * gp.tileSize;
    }
}
