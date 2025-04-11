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
        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage(){

            setup(0,"grass",false);
            setup(1,"dirt",false);
            setup(2,"stone",true);
            setup(3,"flowing river",true);
            setup(4,"riverbank",true);
            setup(5,"StillWater",true);
            setup(6,"tree",true);
            setup(7,"botriverbank",true);
            setup(8,"leftbtriverbank",true);
            setup(9,"leftriverbank",true);
            setup(10,"rightbtriverbank",true);
            setup(11,"rightriverbank",true);

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
