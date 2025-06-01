package Objects;

import Entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class chest extends Entity {

    public chest(GamePanel gp){
        super(gp);

        name = "Chest";
        down1 = setup("/Object/demo-chest");
        collision = true;
    }
}
