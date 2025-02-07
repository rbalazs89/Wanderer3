package object;

import main.GamePanel;

public class OBJ_Lectern extends SuperObject{
    public OBJ_Lectern(GamePanel gp) {
        super(gp);
        image = setup("/objects/wall/lectern");
        collision = true;
        solidArea.x = 10;
        solidArea.y = 5;
        solidArea.width = 64 - 20;
        solidArea.height = 59;
    }
}
