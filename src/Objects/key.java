package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class key extends SuperObject{

    public key(){

        name ="Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Chest/demo-key.png"));

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
