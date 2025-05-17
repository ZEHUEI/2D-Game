package main;

import Entity.NPC_light;
import Objects.Swift;
import Objects.chest;
import Objects.door;
import Objects.key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

    }
    public void setNPC(){
        gp.npc[0] = new NPC_light(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
}
