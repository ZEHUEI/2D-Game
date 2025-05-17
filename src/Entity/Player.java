package Entity;

import main.GamePanel;
import main.MOVING;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

public class Player extends Entity{
    MOVING move;

    public final int screenX;
    public final int screenY;
    public boolean buffActive = false;
    public int buffTime =0;
    public static final int buffduration= 180;
    public int standCounter = 0;

    public Player(GamePanel gp, MOVING move){
        super(gp);

        this.move= move;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidarea = new Rectangle();
        solidarea.x =9;
        solidarea.y=21;
        solidAreaDefaultX = solidarea.x;
        solidAreaDefaultY = solidarea.y;
        solidarea.width =30;
        solidarea.height =27;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){

        worldX = gp.tileSize * 85;
        worldY = gp.tileSize * 7;
        speed =4;
        direction = "down";
    }

    public void getPlayerImage(){

        up1 = setup("/Player/up1");
        up2 = setup("/Player/up2");
        down1 = setup("/Player/walk1");
        down2 = setup("/Player/walk2");
        left1 =setup("/Player/left1");
        left2 =setup("/Player/left2");
        right1 =setup("/Player/right1");
        right2 =setup("/Player/right2");

    }
    public void update(){
        if(move.up == true|| move.down == true|| move.left == true || move.right == true){
            if(move.up == true)
            {
                direction = "up";
            }
            if(move.down == true)
            {
                direction = "down";
            }
            if(move.left == true)
            {
                direction = "left";
            }
            if(move.right == true)
            {
                direction = "right";
            }

            //check tile collision
            collisionON = false;
            gp.cChecker.checkTile(this);

            //check obj collision
            int objindex = gp.cChecker.checkObj(this,true);
            Interact(objindex);

            //cjeck npc collision
            int npcindex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcindex);

            //if collision is true player dont move
            if(collisionON ==false)
            {
                switch(direction){
                    case"up":
                        worldY -= speed;
                        break;
                    case"down":
                        worldY += speed;
                        break;
                    case"left":
                        worldX -= speed;
                        break;
                    case"right":
                        worldX += speed;
                        break;

                }
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum ==1) spriteNum =2;
                else if(spriteNum ==2) spriteNum =1;
                spriteCounter =0;
            }
        }
        else {
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if(buffActive){
            buffTime++;
            if(buffTime >=buffduration)
            {
                speed-=2;
                buffActive = false;
                buffTime =0;
            }
        }

    }
    public void Interact(int i){
        if(i != 999){

            }
        }

    public void interactNPC(int i){
        if(i != 999){
            if(gp.move.enter == true)
            {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.move.enter = false;
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image =up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image =down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image =left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image =right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;

        }

        g2.drawImage(image,screenX,screenY,null);
//        player size debugg
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidarea.x ,screenY + solidarea.y , solidarea.width,solidarea.height );
    }
}
