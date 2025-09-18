package Objects;

import Entity.Entity;
import main.GamePanel;
public class sword extends Entity {
    public sword(GamePanel gp){
        super(gp);

        name="Normal Sword";
        down1=setup("/Weapons/sword",gp.tileSize,gp.tileSize);
        attackValue =1;
    }

}
