package Monster;

import Entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Slime extends Entity {
    public BufferedImage land1;
    public int monsterStage = 1;
    public Slime(GamePanel gp) {
        super(gp);

        name = "Slime";
        type = 2;
        speed = 1 ;
        maxHealth = 2;
        life = maxHealth;

        solidarea.x = 3;
        solidarea.y = 12;
        solidarea.width = 42;
        solidarea.height = 36;
        solidAreaDefaultX = solidarea.x;
        solidAreaDefaultY = solidarea.y;

        getImage();
    }

    public void getImage(){

        up1 = setup("/Monster/normal",gp.tileSize,gp.tileSize);
        up2 = setup("/Monster/jump",gp.tileSize,gp.tileSize);
        down1 = setup("/Monster/normal",gp.tileSize,gp.tileSize);
        down2 = setup("/Monster/jump",gp.tileSize,gp.tileSize);
        left1 = setup("/Monster/normal",gp.tileSize,gp.tileSize);
        left2 = setup("/Monster/jump",gp.tileSize,gp.tileSize);
        right1 = setup("/Monster/normal",gp.tileSize,gp.tileSize);
        right2 = setup("/Monster/jump",gp.tileSize,gp.tileSize);

        land1 = setup("/Monster/land",gp.tileSize,gp.tileSize);

    }

    @Override
    public void update() {
        setAction();

        collisionON = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObj(this, false);
        gp.cChecker.checkPlayer(this);

        if (!collisionON) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            monsterStage++;
            if (monsterStage > 3) monsterStage = 1;
            spriteCounter = 0;
        }
        if(iframe == true){
            iframecounter++;
            if(iframecounter > 40) {
                iframe = false;
                iframecounter =0;
            }
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            //use tenary cause ukwim
            switch (direction) {
                case "up":
                    image = (monsterStage == 1) ? up1 : (monsterStage == 2 ? up2 : land1);
                    break;
                case "down":
                    image = (monsterStage == 1) ? down1 : (monsterStage == 2 ? down2 : land1);
                    break;
                case "left":
                    image = (monsterStage == 1) ? left1 : (monsterStage == 2 ? left2 : land1);
                    break;
                case "right":
                    image = (monsterStage == 1) ? right1 : (monsterStage == 2 ? right2 : land1);
                    break;
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

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidarea.x, screenY + solidarea.y, solidarea.width, solidarea.height);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
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

}
