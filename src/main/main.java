package main;

import javax.swing.*;

public class main {
    public static void main(String[] args){
    JFrame window = new JFrame();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
//declare logo from resource folder
    ImageIcon logo = new ImageIcon(".//res//Logo//logo.png");
    window.setTitle("Dragons, Dragons and more DRAGONS");

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);
    //set the icon
    window.setIconImage(logo.getImage());

    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);
    gamePanel.setupGame();

    gamePanel.startGameThread();
    }
}
