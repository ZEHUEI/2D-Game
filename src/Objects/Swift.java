package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Swift extends SuperObject{

    public Swift(){

        name ="Swift";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Chest/swift-demo.png"));

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
