package Objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Swift extends SuperObject{
    GamePanel gp;

    public Swift(GamePanel gp){

        name ="Swift";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Chest/swift-demo.png"));
            uTool.scaleImage(image,gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
