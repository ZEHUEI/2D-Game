package Entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_light extends Entity{

    public NPC_light(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        solidarea = new Rectangle();
        solidarea.x =0;
        solidarea.y=0;
        solidAreaDefaultX = solidarea.x;
        solidAreaDefaultY = solidarea.y;
        solidarea.width =48;
        solidarea.height =48;

        getPlayerImage();
        setDialogue();
    }
    public void getPlayerImage(){

        up1 = setup("/NPC/NPCup1",gp.tileSize,gp.tileSize);
        up2 = setup("/NPC/NPCup2",gp.tileSize,gp.tileSize);
        down1 = setup("/NPC/NPCdown1",gp.tileSize,gp.tileSize);
        down2 = setup("/NPC/NPCdown2",gp.tileSize,gp.tileSize);
        left1 =setup("/NPC/NPCleft1",gp.tileSize,gp.tileSize);
        left2 =setup("/NPC/NPCleft2",gp.tileSize,gp.tileSize);
        right1 =setup("/NPC/NPCright1",gp.tileSize,gp.tileSize);
        right2 =setup("/NPC/NPCright2",gp.tileSize,gp.tileSize);

    }

    public void setDialogue(){
        dialogue[0]= "Hello my drilla";
        dialogue[1]= "Welcome to the new world";
        dialogue[2]= "I will be ur voice";
        dialogue[3]= "Goodlick";
    }
    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak(){
        super.speak();
    }
}

