package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MOVING implements KeyListener {
    GamePanel gp;
    public boolean up, down, left, right;
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
        if(code == KeyEvent.VK_W)
        {
            up = true;
        }
        if(code == KeyEvent.VK_A)
        {
            left = true;
        }
        if(code == KeyEvent.VK_S)
        {
            down = true;
        }
        if(code == KeyEvent.VK_D)
        {
            right = true;
        }

        if(code == KeyEvent.VK_ESCAPE)
        {
            if(gp.gameState == gp.playState)
            {
                gp.gameState = gp.pauseState;
            }
            else if(gp.gameState == gp.pauseState)
            {
                gp.gameState = gp.playState;
            }
        }

        if(code == KeyEvent.VK_T)
        {
           if(checkdrawTime == false)
           {
               checkdrawTime = true;
           }
           else if(checkdrawTime ==true)
           {
               checkdrawTime = false;
           }
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
