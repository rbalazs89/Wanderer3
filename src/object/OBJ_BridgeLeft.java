package object;

import main.GamePanel;

public class OBJ_BridgeLeft extends SuperObject{

    GamePanel gp;

    public OBJ_BridgeLeft(GamePanel gp){
        super(gp);
        this.gp = gp;
        image = setup("/objects/interact/bridgeleft");
    }
}
