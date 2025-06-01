package Objects;

import Entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class door extends Entity {

    public door(GamePanel gp){
        super(gp);
        name ="Door";
        down1 =setup("/House/door");
        collision =true;

        solidarea.x = 0;
        solidarea.y = 16;
        solidarea.width = 48;
        solidarea.height = 32;
        solidAreaDefaultX = solidarea.x;
        solidAreaDefaultY = solidarea.y;

    }
}
