package main;

import Entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidarea.x;
        int entityRightWorldX = entity.worldX + entity.solidarea.x + entity.solidarea.width;
        int entityTopWorldY = entity.worldY + entity.solidarea.y;
        int entityBottomWorldY = entity.worldY + entity.solidarea.y + entity.solidarea.height;

        int entityLeftcol = entityLeftWorldX/gp.tileSize;
        int entityRightcol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            case"up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftcol][entityTopRow];
                tileNum2 =gp.tileM.mapTileNum[entityRightcol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true||gp.tileM.tile[tileNum2].collision ==true)
                {
                    entity.collisionON = true;
                }
                break;
            case"down":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftcol][entityBottomRow];
                tileNum2 =gp.tileM.mapTileNum[entityRightcol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true||gp.tileM.tile[tileNum2].collision ==true)
                {
                    entity.collisionON = true;
                }
                break;
            case"left":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftcol][entityTopRow];
                tileNum2 =gp.tileM.mapTileNum[entityLeftcol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true||gp.tileM.tile[tileNum2].collision ==true)
                {
                    entity.collisionON = true;
                }
                break;
            case"right":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightcol][entityTopRow];
                tileNum2 =gp.tileM.mapTileNum[entityRightcol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true||gp.tileM.tile[tileNum2].collision ==true)
                {
                    entity.collisionON = true;
                }
                break;

        }

    }

    public int checkObj(Entity entity, boolean player){

        int index = 999;

        for(int i =0; i < gp.obj.length;i++)
        {
            if(gp.obj[i] != null)
            {
                //get entities solid area position
                entity.solidarea.x = entity.worldX + entity.solidarea.x;
                entity.solidarea.y = entity.worldY + entity.solidarea.y;

                //get obj solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction){
                    case"up":
                        entity.solidarea.y -= entity.speed;
                        if(entity.solidarea.intersects(gp.obj[i].solidArea))
                        {
                            if(gp.obj[i].collision ==true)
                            {
                                entity.collisionON = true;
                            }
                            if(player ==true)
                            {
                                index =i;
                            }
                        }
                        break;
                    case"down":
                        entity.solidarea.y += entity.speed;
                        if(entity.solidarea.intersects(gp.obj[i].solidArea))
                        {
                            if(gp.obj[i].collision ==true)
                            {
                                entity.collisionON = true;
                            }
                            if(player ==true)
                            {
                                index =i;
                            }
                        }
                        break;
                    case"left":
                        entity.solidarea.x -= entity.speed;
                        if(entity.solidarea.intersects(gp.obj[i].solidArea))
                        {
                            if(gp.obj[i].collision ==true)
                            {
                                entity.collisionON = true;
                            }
                            if(player ==true)
                            {
                                index =i;
                            }
                        }
                        break;
                    case"right":
                        entity.solidarea.x += entity.speed;
                        if(entity.solidarea.intersects(gp.obj[i].solidArea))
                        {
                            if(gp.obj[i].collision ==true)
                            {
                                entity.collisionON = true;
                            }
                            if(player ==true)
                            {
                                index =i;
                            }
                        }
                        break;
                }
                entity.solidarea.x = entity.solidAreaDefaultX;
                entity.solidarea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

            }
        }

        return index;
    }
}
