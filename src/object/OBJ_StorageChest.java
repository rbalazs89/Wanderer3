package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_StorageChest extends SuperObject{
    GamePanel gp;
    public OBJ_StorageChest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        interactable = true;
        getGlitterImages();
        image = setup("/objects/interact/treasurechest");
    }

    public void interact(){
        gp.playSE(24);
        gp.gameState = gp.inventoryState;
        gp.inventorySubState = gp.inventoryStorageState1;
    }
}
