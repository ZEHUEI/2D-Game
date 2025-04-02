package main;

import Entity.Player;
import Objects.SuperObject;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    final int originalTilesSize = 16;
    final int scale = 3;

    public int tileSize = originalTilesSize * scale;//48x48px
    public int maxScreenColumn = 32;
    public int maxScreenRow =18;
    //map has to be 31px in column X 17px in row game in 1488x1080
    public int screenWidth = tileSize * maxScreenColumn;
    public int screenHeight = tileSize * maxScreenRow;

    //world map parameters
    public final int maxWorldCol = 96;
    public final int maxWorldRow = 50;

    //FPS
    int FPS=120;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    MOVING move = new MOVING();
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter asset = new AssetSetter(this);
    Thread gameThread;

    //ENTITY
    public Player player = new Player(this,move);
    public SuperObject obj[] = new SuperObject[10];


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(move);
        this.setFocusable(true);
    }

    public void setupGame(){
        asset.setObject();

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawingInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta +=(currentTime - lastTime) / drawingInterval;
            lastTime = currentTime;

            if(delta >=1)
            {
                update();

                repaint();

                delta--;
            }

        }
    }
    public void update(){
        player.update();

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        for(int i=0; i<obj.length;i++){
            if(obj[i] != null){obj[i].draw(g2,this);}
        }

        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i){

        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}