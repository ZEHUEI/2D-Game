package main;

import Entity.Entity;
import Entity.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    final int originalTilesSize = 16;
    final int scale = 3;

    public int tileSize = originalTilesSize * scale;//48x48px
    public int maxScreenColumn = 16;
    public int maxScreenRow =15;
    //map has to be 31px in column X 17px in row game in 1488x1080 change if want as got some problem #fixed 13/4/2025
    public int screenWidth = tileSize * maxScreenColumn;
    public int screenHeight = tileSize * maxScreenRow;

    //world map parameters change this if want too!!!
    public final int maxWorldCol = 95;
    public final int maxWorldRow = 50;
    public final int maxWorldWidth = tileSize *maxWorldCol;
    public final int maxWorldHeight = tileSize *maxWorldRow;

    //FPS
    int FPS=120;

    TileManager tileM = new TileManager(this);
    public MOVING move = new MOVING(this);
    Thread gameThread;
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter asset = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public Player player = new Player(this,move);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game state
    public int gameState;
    public final int titleState =0;
    public final int playState =1;
    public final  int pauseState =2;
    public final int dialogueState = 3;
    public final int characterState = 4;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(move);
        this.setFocusable(true);
    }

    public void setupGame(){
        asset.setObject();
        asset.setNPC();
        asset.setMonster();
//        playMusic(0);

        gameState = titleState;
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
            for(int i = 0; i< npc.length;i++){
                if(npc[i] != null){npc[i].update();}
            }
            for(int i = 0; i< monster.length;i++){
                if(monster[i] != null)
                {
                    if(monster[i].alive == true && monster[i].dying == false)
                    {
                        monster[i].update();
                    }
                    if(monster[i].alive == false)
                    {
                        monster[i] = null;
                    }
                }
            }
        }
        if(gameState==pauseState)
        {
            music.stop();
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

        //title screen
        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        //other state
        else{
            tileM.draw(g2);
            entityList.add(player);


            for(int i = 0; i < npc.length;i++)
            {
                if(npc[i] != null)
                {
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i<obj.length; i++)
            {
                if(obj[i] != null)
                {
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length;i++)
            {
                if(monster[i] != null)
                {
                    entityList.add(monster[i]);
                }
            }
            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });
            // draw entity
            for(int i=0;i<entityList.size(); i++)
            {
                entityList.get(i).draw(g2);
            }

            //empty the entity list
                entityList.clear();


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
    }
    public void playMusic(int i){
        music.setFile(i);
//        music.play();
//        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}