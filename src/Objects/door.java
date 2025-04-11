package Objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class door extends SuperObject{
    GamePanel gp;

    public door(GamePanel gp){

        name ="Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/House/door.png"));
            uTool.scaleImage(image,gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }
        collision =true;

    }
}
