package Entity;

import main.GamePanel;
import main.MOVING;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    MOVING move;

    public final int screenX;
    public final int screenY;
    public int hasKey =0;
    public boolean buffActive = false;
    public int buffTime =0;
    public static final int buffduration= 180;

    public Player(GamePanel gp, MOVING move){
        this.gp = gp;
        this.move= move;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidarea = new Rectangle();
        solidarea.x =6;
        solidarea.y=21;
        solidAreaDefaultX = solidarea.x;
        solidAreaDefaultY = solidarea.y;
        solidarea.width =36;
        solidarea.height =27;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){

        worldX = gp.tileSize * 22;
        worldY = gp.tileSize * 15;
        speed =4;
        direction = "down";
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/walk1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/walk2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
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
            String objName = gp.obj[i].name;

            switch(objName){
                case"Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case"Door":
                    if(hasKey > 0){
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Door Opened!");
                    }
                    else {
                        gp.ui.showMessage("Key Needed!");
                    }
                    break;
                case"Swift":
                    //gp.playSE(); make a special effect sound and add in sound.java
                    if(!buffActive){
                        speed+=2;
                        buffActive = true;
                        buffTime =0;
                    }
                    gp.obj[i] = null;
                    gp.ui.showMessage("SPEED UP!");
                    break;
                case"Chest":
                    //gp.playSE(); make a special effect sound and add in sound.java
                    gp.ui.gamefinish = true;
                    gp.stopMusic();
                    //gp.playSE();
                    break;
            }
        }
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

        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

    }
}
