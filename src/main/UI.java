package main;

import Entity.Entity;
import Objects.Health;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font pixelplay_40, arial_80B;
    BufferedImage fullheart, halfheart, noheart;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gamefinish = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0:first screen 1:second etc...

    public UI(GamePanel gp) {
        this.gp = gp;

        //pixel game font
        try{
            InputStream is = getClass().getResourceAsStream("/fonts/pixelplay.ttf");
            pixelplay_40 = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (FontFormatException e){
            e.printStackTrace();
        }

        //create hud obj
        Entity Health = new Health(gp);
        fullheart = Health.image;
        halfheart= Health.image2;
        noheart= Health.image3;
    }


    public void showMessage(String text) {
        message = text;
        messageOn = true;

    }

    //120 times a sex
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pixelplay_40);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        //play state
        if(gp.gameState == gp.playState)
        {
            drawPlayerHealth();
        }
        if(gp.gameState == gp.pauseState)
        {
            drawPlayerHealth();
            drawPauseScreen();
        }

        //dialogue state
        if(gp.gameState == gp.dialogueState)
        {
            drawPlayerHealth();
            drawDialogueScreen();
        }
        //title state
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }
    }
    public void drawTitleScreen(){

        if(titleScreenState == 0)
        {
            //tile background
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

            //title words
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96f));
            String text = "Brr Brr Patapim";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //shadow effect
            g2.setColor(Color.gray);
            g2.drawString(text,x+5,y+5);
            //MAIN TEXT
            g2.setColor(Color.white);
            g2.drawString(text,x,y);

            //image (i dont really like it)
//        x = gp.screenWidth/3;
//        y +=gp.tileSize * 2;
//        g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

            //menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 5;
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
            if(commandNum == 0)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
            if(commandNum == 1)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "QUIT GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
            if(commandNum == 2)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }


        }
        else if(titleScreenState == 1)
        {
            //class selection screen
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y =gp.tileSize *3;
            g2.drawString(text,x,y);

            text = "fighter";
            x = getXforCenteredText(text);
            y +=gp.tileSize *3;
            g2.drawString(text,x,y);
            if(commandNum == 0)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "mage";
            x = getXforCenteredText(text);
            y +=gp.tileSize ;
            g2.drawString(text,x,y);
            if(commandNum == 1)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "assasin";
            x = getXforCenteredText(text);
            y +=gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 2)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "BACK";
            x = getXforCenteredText(text);
            y +=gp.tileSize * 2;
            g2.drawString(text,x,y);
            if(commandNum == 3)
            {
                g2.drawString(">",x-gp.tileSize,y);
            }
        }
    }
    public void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80f));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){

        //window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = (gp.tileSize*4);

        drawWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        x+=gp.tileSize;
        y+=gp.tileSize;

        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawWindow(int x,int y, int width, int height){

        Color c =new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXforCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x= gp.screenWidth/2 - length/2;
        return x;
    }

    public void drawPlayerHealth(){
        //debug player life with this
//        gp.player.life = 5;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //blank hearts
        while(i < gp.player.maxHealth/2)
        {
            g2.drawImage(noheart,x,y,null);
            i++;
            x += 50;
        }

        //current health
         x = gp.tileSize/2;
         y = gp.tileSize/2;
         i = 0;

         while(i <gp.player.life)
         {
             g2.drawImage(halfheart,x,y,null);
             i++;
             if(i < gp.player.life)
             {
                 g2.drawImage(fullheart,x,y,null);
             }
             i++;
             x +=50;
         }
    }
}
