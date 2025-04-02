package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class door extends SuperObject{

    public door(){

        name ="Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/House/door.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
        collision =true;

    }
}
