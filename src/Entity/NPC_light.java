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
        dialogue[0]= wrapText("你好，안녕하세요,こんにちは . . . 看来你不理解我  . . . 不理解我 . . . 不理解我 不理解我不 ... 不理 .. . . .不理解我",40);
        dialogue[1]= wrapText("Welcome fellow Hollowed One!",40);
        dialogue[2]= wrapText("I will be your voice throughout the NEW WORLD",35);
        dialogue[3]= wrapText("Let’s touch grass — fantasy grass.",40);
        dialogue[4]= wrapText(" . . . heh",40);
        dialogue[5]= wrapText("May our journey blaze legends",40);

    }

    //dialogue helper as text goes out if too long
    public String wrapText(String text, int maxLineLength){
        StringBuilder wrapped = new StringBuilder();
        int linelength =0;

        for(String word:text.split(" ")){
            if(linelength + word.length() > maxLineLength){
                wrapped.append("\n");
                linelength=0;
            }
            wrapped.append(word).append(" ");
            linelength += word.length()+1;
        }
        return wrapped.toString().trim();
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

