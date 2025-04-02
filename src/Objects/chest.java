package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class chest extends SuperObject{

    public chest(){

        name ="Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Chest/demo-chest.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
