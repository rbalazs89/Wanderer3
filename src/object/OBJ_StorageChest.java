package object;

import main.GamePanel;

public class OBJ_StorageChest extends SuperObject{
    public OBJ_StorageChest(GamePanel gp) {
        super(gp);
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
