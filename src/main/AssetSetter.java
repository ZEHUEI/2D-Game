package main;

import Entity.NPC_light;
import Monster.Slime;
import Objects.door;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new door(gp);
        gp.obj[0].worldX = gp.tileSize * 21;
        gp.obj[0].worldY = gp.tileSize * 22;

        gp.obj[1] = new door(gp);
        gp.obj[1].worldX = gp.tileSize * 22;
        gp.obj[1].worldY = gp.tileSize * 22;

    }
    public void setNPC(){
        gp.npc[0] = new NPC_light(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
    public void setMonster(){
        int i=0;
        gp.monster[i] = new Slime(gp);
        gp.monster[i].worldX = gp.tileSize*20;
        gp.monster[i].worldY = gp.tileSize*20;
        i++;
        gp.monster[i] = new Slime(gp);
        gp.monster[i].worldX = gp.tileSize*19;
        gp.monster[i].worldY = gp.tileSize*20;
        i++;
        gp.monster[i] = new Slime(gp);
        gp.monster[i].worldX = gp.tileSize*18;
        gp.monster[i].worldY = gp.tileSize*20;
        i++;
        gp.monster[i] = new Slime(gp);
        gp.monster[i].worldX = gp.tileSize*17;
        gp.monster[i].worldY = gp.tileSize*20;
        i++;
        gp.monster[i] = new Slime(gp);
        gp.monster[i].worldX = gp.tileSize*16;
        gp.monster[i].worldY = gp.tileSize*20;
        i++;
        gp.monster[i] = new Slime(gp);
        gp.monster[i].worldX = gp.tileSize*15;
        gp.monster[i].worldY = gp.tileSize*20;

    }
}
