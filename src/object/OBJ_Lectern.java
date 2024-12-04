package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Lectern extends SuperObject{
    GamePanel gp;
    public OBJ_Lectern(GamePanel gp) {
        super(gp);
        this.gp = gp;
        image = setup("/objects/wall/lectern");
        collision = true;
        solidArea.x = 10;
        solidArea.y = 5;
        solidArea.width = 64 - 20;
        solidArea.height = 59;
    }

}
