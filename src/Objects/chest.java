package Objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class chest extends SuperObject{
    GamePanel gp;

    public chest(GamePanel gp){

        name ="Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Chest/demo-chest.png"));
            uTool.scaleImage(image,gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
