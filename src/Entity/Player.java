package Entity;

import main.GamePanel;
import main.MOVING;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    MOVING move;

    public final int screenX;
    public final int screenY;
    public int hasKey =0;
    public boolean buffActive = false;
    public int buffTime =0;
    public static final int buffduration= 180;
    public int standCounter = 0;
    public boolean atkCancel = false;

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

        //atk area
        atkarea.width = 36;
        atkarea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerATK();
    }
    public void setDefaultValues(){

        worldX = gp.tileSize * 85;
        worldY = gp.tileSize * 7;
        speed =4;
        direction = "down";

        //player stats
        maxHealth = 4;
        life = maxHealth;
    }

    public void getPlayerImage(){

        up1 = setup("/Player/up1",gp.tileSize,gp.tileSize);
        up2 = setup("/Player/up2",gp.tileSize,gp.tileSize);
        down1 = setup("/Player/walk1",gp.tileSize,gp.tileSize);
        down2 = setup("/Player/walk2",gp.tileSize,gp.tileSize);
        left1 =setup("/Player/left1",gp.tileSize,gp.tileSize);
        left2 =setup("/Player/left2",gp.tileSize,gp.tileSize);
        right1 =setup("/Player/right1",gp.tileSize,gp.tileSize);
        right2 =setup("/Player/right2",gp.tileSize,gp.tileSize);

    }

    public void getPlayerATK(){
        atkup1 = setup("/Attack/atkup1",gp.tileSize,gp.tileSize * 2);
        atkup2 = setup("/Attack/atkup2",gp.tileSize,gp.tileSize * 2);
        atkdown1 = setup("/Attack/atkdown1",gp.tileSize,gp.tileSize * 2);
        atkdown2 = setup("/Attack/atkdown2",gp.tileSize,gp.tileSize * 2);
        atkL1 = setup("/Attack/atkL1",gp.tileSize * 2,gp.tileSize);
        atkL2 = setup("/Attack/atkL2",gp.tileSize * 2,gp.tileSize);
        atkR1 = setup("/Attack/atkR1",gp.tileSize * 2,gp.tileSize);
        atkR2 = setup("/Attack/atkR2",gp.tileSize * 2,gp.tileSize);

    }
    public void update(){
        if(attacking)
        {
            attacking();
        }
        else if(move.up == true|| move.down == true|| move.left == true ||
                move.right == true || move.enter == true){
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

            //check monster collision
            int monsterindex = gp.cChecker.checkEntity(this,gp.monster);
            contactMonster(monsterindex);
            //check event
            gp.eHandler.checkEvent();


            //if collision is true player dont move
            if(collisionON ==false && move.enter == false)
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

            if(move.enter == true && atkCancel == false){
                gp.playSE(6);
                attacking = true;
                spriteCounter = 0;
            }

            atkCancel = false;
            gp.move.enter = false;

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

        //iframe
        if(iframe == true){
            iframecounter++;
            if(iframecounter > 120)
            {
                iframe = false;
                iframecounter =0;
            }
        }
    }

    private void contactMonster(int i) {
        if (i != 999) {
            if(iframe == false)
            {
                gp.playSE(4);
                life -=1;
                iframe = true;
            }

        }
    }
    public void damageMonster(int i){
        if(i !=999){
            if(gp.monster[i].iframe == false)
            {
                gp.playSE(5);
                gp.monster[i].life -= 1;
                gp.monster[i].iframe = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <=0)
                {
                    gp.playSE(5);
                    gp.monster[i].dying = true;
                }
            }
        }
    }
    public void attacking(){
        spriteCounter++;

        if(spriteCounter <=5){
            spriteNum =1;
        }
        if(spriteCounter >5 && spriteCounter <= 25)
        {
            spriteNum =2;
            //save current worldX and worldY area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidarea.width;
            int solidAreaHeight = solidarea.height;
            // adjust player world X n Y for atk collision
            switch (direction){
                case "up":
                    worldY -= atkarea.height;
                    break;
                case "down":
                    worldY +=atkarea.height;
                    break;
                case "left":
                    worldX -=atkarea.width;
                    break;
                case "right":
                    worldX +=atkarea.width;
                    break;
            }
            //atk area is our solid area
            solidarea.width = atkarea.width;
            solidarea.height = atkarea.height;

            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidarea.width = solidAreaWidth;
            solidarea.height = solidAreaHeight;

        }
        if(spriteCounter >25){
            spriteNum =1;
            spriteCounter =0;
            attacking = false;
        }
    }

    //interact with obj
    public void Interact(int i) {
        if (i != 999) {
            String objName = gp.obj[i].name;

            switch (objName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Door Opened!");
                    } else {
                        gp.ui.showMessage("Key Needed!");
                    }
                    break;
                case "Swift":
                    //gp.playSE(); make a special effect sound and add in sound.java
                    if (!buffActive) {
                        speed += 2;
                        buffActive = true;
                        buffTime = 0;
                    }
                    gp.obj[i] = null;
                    gp.ui.showMessage("SPEED UP!");
                    break;
                case "Object":
                    //gp.playSE(); make a special effect sound and add in sound.java
                    gp.ui.gamefinish = true;
                    gp.stopMusic();
                    //gp.playSE();
                    break;
            }
        }
    }

    public void interactNPC(int i){
        if(gp.move.enter == true)
        {
            if(i != 999){
                    atkCancel = true;
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
                }
                else {
                    gp.playSE(6);
                    attacking = true;
                    }
                }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction){
            case "up":
                if(attacking == false){
                    if(spriteNum == 1){image =up1;}
                    if(spriteNum == 2) {image = up2;}
                }
                if(attacking == true)
                {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){image =atkup1;}
                    if(spriteNum == 2) {image = atkup2;}
                }
                break;
            case "down":
                if(attacking == false) {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if(attacking == true)
                {
                    if(spriteNum == 1){image =atkdown1;}
                    if(spriteNum == 2) {image = atkdown2;}
                }
                break;
            case "left":
                if(attacking == false) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if(attacking == true)
                {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){image =atkL1;}
                    if(spriteNum == 2) {image = atkL2;}
                }
                break;
            case "right":
                if(attacking == false) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if(attacking == true)
                {
                    if(spriteNum == 1){image =atkR1;}
                    if(spriteNum == 2) {image = atkR2;}
                }
                break;

        }
        if(iframe == true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image,tempScreenX,tempScreenY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));



        
        //debug text
//        g2.setFont(new Font("Arial",Font.PLAIN,26));
//        g2.setColor(Color.WHITE);
//        g2.drawString("iframe:" + iframecounter,10,400);
//        player size debugg
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidarea.x ,screenY + solidarea.y , solidarea.width,solidarea.height );
    }
}
