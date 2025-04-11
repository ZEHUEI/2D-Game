package Objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class key extends SuperObject{
    GamePanel gp;

    public key(GamePanel gp){

        name ="Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Chest/demo-key.png"));
            uTool.scaleImage(image,gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
