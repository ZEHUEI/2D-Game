package Entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    protected GamePanel gp;

    public int worldX , worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage atkup1 , atkup2 , atkdown1 , atkdown2 , atkL1 , atkL2 , atkR1, atkR2;
    public String direction = "down";

    public int spriteCounter =0;
    public int spriteNum = 1;

    public Rectangle solidarea = new Rectangle(0,0,48,48);
    public Rectangle atkarea = new Rectangle(0,0,0,0);
    public  int solidAreaDefaultX ,solidAreaDefaultY;
    public boolean collisionON =false;
    public int actionLockCounter = 0;
    public int dyingcounter = 0;
    public boolean iframe = false;
    public int iframecounter = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean HPbarOn = false;
    public int HPbarCounter = 0;


    String dialogue[] = new String[20];
    int dialogueIndex =0;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; //0 is player, 1 is npc , 2 is monster

    //Char stats
    public int maxHealth;
    public int life;
    public int level;
    public int strength;
    public int dex;
    public int def;
    public int vigor;
    public int exp;
    public int nextLvlExp;
    public int coins;
    public Entity currentWeapon;
    public Entity offHand;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void setAction(){}

    public void damageReaction(){}
    public void speak(){
        if(dialogue[dialogueIndex] == null){
            dialogueIndex =0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case"up":direction = "down"; break;
            case"down":direction = "up"; break;
            case"left":direction = "right"; break;
            case"right":direction = "left"; break;
        }
    }
    public void update(){
        setAction();

        collisionON = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObj(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.monster);
        gp.cChecker.checkPlayer(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true)
        {
            if(gp.player.iframe == false)
            {
                gp.player.life -=1;
                gp.player.iframe = true;
            }
        }

        //from player.java
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
        if(spriteCounter > 10) {
            if (spriteNum == 1) {spriteNum = 2;}
            else if (spriteNum == 2) {spriteNum = 1;}
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize <gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize <gp.player.worldY + gp.player.screenY)
        {

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

            //monster hp bar
            if(this.type == 2 && HPbarOn == true){
                double oneScale = (double)gp.tileSize/maxHealth;
                double HPbarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)HPbarValue,10);

                HPbarCounter++;
                if(HPbarCounter > 1200){
                    HPbarCounter = 0;
                    HPbarOn = false;
                }
            }

            if(iframe == true)
            {
                HPbarOn = true;
                HPbarCounter = 0;
                changeAplha(g2,0.4f);
            }
            if(dying == true)
            {
                dyingAnimation(g2);
            }


            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidarea.x ,screenY + solidarea.y , solidarea.width,solidarea.height );
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingcounter++;
        int i=5;
        if(dyingcounter <= i)
        {
            changeAplha(g2,0f);
        }
        if(dyingcounter > i && dyingcounter<= i*2)
        {
            changeAplha(g2,1f);
        }
        if(dyingcounter > i*2 && dyingcounter<=i*3)
        {
            changeAplha(g2,0f);
        }
        if(dyingcounter > i*3 && dyingcounter<=i*4)
        {
            changeAplha(g2,1f);
        }
        if(dyingcounter > i*4 && dyingcounter<=i*5)
        {
            changeAplha(g2,0f);
        }
        if(dyingcounter > i*5 && dyingcounter<=i*6)
        {
            changeAplha(g2,1f);
        }
        if(dyingcounter > i*6 && dyingcounter<=i*7)
        {
            changeAplha(g2,0f);
        }
        if(dyingcounter > i*7 && dyingcounter<=i*8)
        {
            changeAplha(g2,1f);
        }
        if(dyingcounter > i*8)
        {
            dying = false;
            alive = false;
        }
    }

    public void changeAplha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath,int width,int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image= uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
