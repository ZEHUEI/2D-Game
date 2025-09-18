package Objects;

import Entity.Entity;
import main.GamePanel;

public class shield extends Entity {
    public shield(GamePanel gp){
        super(gp);

        name="woodshield";
        down1=setup("/Weapons/woodshield",gp.tileSize,gp.tileSize);
        defenseValue=1;

    }
}
