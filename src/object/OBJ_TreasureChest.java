package object;

import main.GamePanel;

public class OBJ_TreasureChest extends SuperObject{
    public int chestType;
    //1 quest chest
    //2 cave lvl standard treasure chest
    public OBJ_TreasureChest(GamePanel gp) {
        super(gp);
        getImage();
        interactable = true;
        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        getGlitterImages();
    }

    public void getImage(){
        image = setup("/objects/interact/treasureclosed");
    }

    public void interact(){
        //TODO add sound
        image = setup("/objects/interact/treasureopen");
        interactable = false;
        dropItem();
    }

    public void progressInteract(){
        image = setup("/objects/interact/treasureopen");
        interactable = false;
    }

    public void dropItem() {
        if(chestType == 1){
            if(!gp.progress.act1InteractedObjects[2]){
                if(gp.obj[7][1] != null){
                    if(gp.obj[7][1] == this){
                        OBJ_DroppedItem myItem  = new OBJ_DroppedItem(gp);
                        myItem.item = gp.itemGenerator.generateItemBasedOnID(2);
                        myItem.image = addTransparentBracketForItemDrop(myItem.item.imageInventory);
                        gp.uTool.itemDropFromChest(this, myItem);
                    }
                }
            }
        } else if (chestType == 2){
            itemDrop(10,1,true);
        }
    }


}
