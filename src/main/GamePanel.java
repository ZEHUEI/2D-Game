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
    public final int maxWorldWidth = tileSize *maxWorldCol;
    public final int maxWorldHeight = tileSize *maxWorldRow;

    //FPS
    int FPS=120;

    TileManager tileM = new TileManager(this);
    MOVING move = new MOVING(this);
    Thread gameThread;
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter asset = new AssetSetter(this);
    public UI ui = new UI(this);
    public Player player = new Player(this,move);
    public SuperObject obj[] = new SuperObject[10];

    //Game state
    public int gameState;
    public final int playState =1;
    public final  int pauseState =2;


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

        gameState = playState;
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
        if(gameState == playState)
        {
            player.update();
        }
        if(gameState==pauseState)
        {

        }

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //Debug
//        long drawStart = 0;
//        if(move.checkdrawTime == true)
//        {
//            drawStart = System.nanoTime();
//        }


        tileM.draw(g2);

        for(int i=0; i<obj.length;i++){
            if(obj[i] != null){obj[i].draw(g2,this);}
        }

        player.draw(g2);
        ui.draw(g2);

//        if(move.checkdrawTime ==true)
//        {
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd -drawStart;
//            g2.setColor(Color.WHITE);
//            g2.drawString("Draw Time: "+passed,10,400);
//            System.out.println("Draw Time: "+passed);
//        }

        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}