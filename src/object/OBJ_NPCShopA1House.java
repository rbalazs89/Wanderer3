package object;

import item.InventoryPage;
import item.InventorySlot;
import main.GamePanel;

import java.awt.*;

public class OBJ_NPCShopA1House extends SuperObject{
    InventoryPage shopInventory = new InventoryPage(gp);
    public OBJ_NPCShopA1House(GamePanel gp) {
        super(gp);
        this.gp = gp;
        interactable = true;
        image = null;
        getGlitterImages();
        generateA1Shop();
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
        }
        if(showGlitter){
            drawInteractableGlitter(g2);
        }
    }

    public void interact(){
        gp.playSE(20);
        gp.gameState = gp.inventoryState;
        gp.inventorySubState = gp.inventoryShopState;
        gp.allInventoryPages.currentShopInventory = shopInventory;
    }

    public void generateA1Shop() {
        shopInventory.startX = 7;
        shopInventory.startY = 190;
        shopInventory.xDiff = 52;
        shopInventory.yDiff = 52;
        shopInventory.maxRow = 11;
        shopInventory.maxColumn = 9;
        shopInventory.slots = new InventorySlot[shopInventory.maxRow][shopInventory.maxColumn];
        for (int i = 0; i < shopInventory.maxRow; i++) {
            for (int j = 0; j < shopInventory.maxColumn; j++) {
                shopInventory.slots[i][j] = new InventorySlot();
            }
        }
        shopInventory.slots[0][0].placedItem = gp.itemGenerator.generateItemBasedOnID(101);
        shopInventory.slots[0][1].placedItem = gp.itemGenerator.generateItemBasedOnID(201);
        shopInventory.slots[0][2].placedItem = gp.itemGenerator.generateItemBasedOnID(301);
        shopInventory.slots[0][3].placedItem = gp.itemGenerator.generateItemBasedOnID(401);
        shopInventory.slots[0][4].placedItem = gp.itemGenerator.generateItemBasedOnID(501);
        shopInventory.slots[0][5].placedItem = gp.itemGenerator.generateItemBasedOnID(601);
        shopInventory.slots[0][6].placedItem = gp.itemGenerator.generateItemBasedOnID(701);
        shopInventory.slots[0][7].placedItem = gp.itemGenerator.generateItemBasedOnID(801);
        if(!gp.progress.act1BookPickedUp[2]){
            shopInventory.slots[0][8].placedItem = gp.itemGenerator.generateBookItem1();
        }
        if(!gp.progress.act1BookPickedUp[3]){
            shopInventory.slots[1][0].placedItem = gp.itemGenerator.generateBookItem2();
        }
    }
}
