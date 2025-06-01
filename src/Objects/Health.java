package Objects;

import Entity.Entity;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Health extends Entity {

    public Health(GamePanel gp){

        super(gp);
        name = "HEART";
        image = setup("/Object/fullheart");
        image2 = setup("/Object/halfheart");
        image3 = setup("/Object/noheart");

    }
}
