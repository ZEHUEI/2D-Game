package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int prevEventX, prevEventY;
    boolean canTouchEvent;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row<gp.maxWorldRow)
        {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectX = eventRect[col][row].x;
            eventRect[col][row].eventRectY = eventRect[col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col=0;
                row++;
            }

        }
    }


    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - prevEventX);
        int yDistance = Math.abs(gp.player.worldY - prevEventY);
        int Distance = Math.max(xDistance,yDistance);
        if(Distance>gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent == true){
            if (hitbox(85, 12, "any") == true) {
                damagePit(85,12,gp.dialogueState);
            }
            if (hitbox(86, 12, "any") == true) {
                healingPit(86,12,gp.dialogueState);
            }
            if (hitbox(85, 10, "any") == true) {
                teleport(85,10,gp.dialogueState);
            }
        }
    }

    //check hitbox like collision
    public boolean hitbox(int col, int row, String reqDirection) {
        boolean hitbox = false;

        gp.player.solidarea.x = gp.player.worldX + gp.player.solidarea.x;
        gp.player.solidarea.y = gp.player.worldY + gp.player.solidarea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidarea.intersects(eventRect[col][row])&& eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hitbox = true;

                prevEventX = gp.player.worldX;
                prevEventY = gp.player.worldY;
            }
        }
        gp.player.solidarea.x = gp.player.solidAreaDefaultX;
        gp.player.solidarea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectX;
        eventRect[col][row].y = eventRect[col][row].eventRectY;

        return hitbox;
    }

    //one time dmg unless i move away alot
    public void damagePit(int col,int row,int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "DMG TAKEN";
        gp.player.life -= 1;
        //activate only one time event, put at teleport
//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPit(int col,int row,int gameState) {
        if (gp.move.enter == true) {
            gp.gameState = gameState;
            gp.player.atkCancel = true;
//            gp.playSE(7);
            gp.ui.currentDialogue = "YOU HEALED";
            gp.player.life = gp.player.maxHealth;
            gp.asset.setMonster();
        }

        gp.move.enter = false;
    }

    public void teleport(int col,int row,int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "teleport";
        gp.player.worldX = gp.tileSize * 85;
        gp.player.worldY = gp.tileSize * 7;
    }
}
