package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MOVING implements KeyListener {
    GamePanel gp;
    public boolean up, down, left, right,enter;
    boolean checkdrawTime = false;

    public MOVING(GamePanel gp)
    {
        this.gp =gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //tilte state
        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        //play state
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        //pause state
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
            }

        //dialogue state
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        //characteer state
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
    }

    public void titleState(int code){
        if(gp.ui.titleScreenState == 0)
        {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum< 0){
                    gp.ui.commandNum =2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState = 1;
                }
                if(gp.ui.commandNum == 1){
                    //add ;ater
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        else if(gp.ui.titleScreenState == 1)
        {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum< 0){
                    gp.ui.commandNum =3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0){
                    //fighter stats
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    //mage stats
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 2){
                    //assasin stats
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 3){
                    gp.ui.titleScreenState = 0;
                }
            }
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W) {
            up = true;
        }
        if (code == KeyEvent.VK_A) {
            left = true;
        }
        if (code == KeyEvent.VK_S) {
            down = true;
        }
        if (code == KeyEvent.VK_D) {
            right = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enter = true;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }

        if (code == KeyEvent.VK_T) {
            if (checkdrawTime == false) {
                checkdrawTime = true;
            } else if (checkdrawTime == true) {
                checkdrawTime = false;
            }
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_ESCAPE) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
                gp.music.stop();
            }

            else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
                gp.music.play();
            }

        }
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_W) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_A) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_S) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_D) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C)
        {
            gp.gameState=gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            up = false;
        }
        if(code == KeyEvent.VK_A)
        {
            left = false;
        }
        if(code == KeyEvent.VK_S)
        {
            down = false;
        }
        if(code == KeyEvent.VK_D)
        {
            right = false;
        }

    }
}
