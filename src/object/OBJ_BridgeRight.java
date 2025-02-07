package object;

import main.GamePanel;

public class OBJ_BridgeRight extends SuperObject{


    public OBJ_BridgeRight(GamePanel gp){
        super(gp);
        image = setup("/objects/interact/bridgeright_wall");
        collision = true;
    }

    public void interactByOtherObject(){
        collision = false;
        image = setup("/objects/interact/bridgeright");
    }

}
