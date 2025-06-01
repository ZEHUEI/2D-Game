package Tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp =gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage(){

        //grass n walkway
        setup(0,"green",false);
        setup(1,"grass0",false);
        setup(2,"grass1",false);
        setup(3,"grass2",false);
        setup(4,"walkway0",false);
        setup(5,"walkway1",false);
        setup(6,"walkway2",false);
        setup(7,"walkway3",false);
        setup(8,"walkway4",false);
        setup(9,"walkway5",false);
        setup(10,"walkway6",false);
        setup(11,"walkway7",false);

        //ocean n corner
        setup(12,"StillWater",true);
        setup(13,"ocean0",true);
        setup(14,"ocean1",true);
        setup(15,"ocean2",true);
        setup(16,"ocean3",true);
        setup(17,"ocean4",true);
        setup(18,"ocean5",true);
        setup(19,"ocean6",true);
        setup(20,"ocean7",true);
        setup(21,"oceancorner0",true);
        setup(22,"oceancorner1",true);
        setup(23,"oceancorner2",true);
        setup(24,"oceancorner3",true);

//        //sand
//        setup(25,"sand",false);
//        setup(26,"sanddot",false);
//        setup(27,"sandstone",false);
//        setup(28,"sandcorner0",false);
//        setup(29,"sandcorner1",false);
//        setup(30,"sandcorner2",false);
//        setup(31,"sandcorner3",false);


        //river
        setup(25,"botriverbank",true);
        setup(26,"leftbtriverbank",true);
        setup(27,"leftriverbank",true);
        setup(28,"rightbtriverbank",true);
        setup(29,"rightriverbank",true);
        setup(30,"riverbank",true);
        setup(31,"sideleft",true);
        setup(32,"sideright",true);
        setup(33,"flowing river",true);

        //tree n bushes # bushes will be an item placed on top for char to hide
        setup(34,"tree",true);
        setup(35,"topleft",false);
        setup(36,"topright",false);
        setup(37,"botleft",false);
        setup(38,"botright",false);
//        setup(34,"rightriverbank",false);


    }
    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/"+ imageName+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize    );
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/Map/worldmap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col =0;
            int row=0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow)
            {
                String line = br.readLine();

                while(col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol)
                {
                    col =0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
        }
    }
        public void draw (Graphics2D g2){
        int w = 100;

            int worldcol = 0;
            int worldrow = 0;


            while (worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow) {

                int tileNum = mapTileNum[worldcol][worldrow];

                int worldX = worldcol * gp.tileSize;
                int worldY = worldrow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                   worldX - gp.tileSize <gp.player.worldX + gp.player.screenX &&
                   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                   worldY - gp.tileSize <gp.player.worldY + gp.player.screenY)
                {
                    g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }
                worldcol++;

                if (worldcol == gp.maxWorldCol) {
                    worldcol = 0;
                    worldrow++;
                }
            }
        }



    }
