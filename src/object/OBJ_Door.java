package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Door extends SuperObject{
    public OBJ_Door(GamePanel gp) {
        super(gp);
        solidArea = new Rectangle(0,0,64, 64);
        name = "Door";
        image = setup("/entity/monster/act1/mimicdoor/mimic1");
        collision = true;
    }

    public void interactByOtherObject() {
        collision = false;
        image = null;
    }
}
