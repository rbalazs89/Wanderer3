package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_DroppedGold extends SuperObject{

    public int goldValue = 1;

    public OBJ_DroppedGold(GamePanel gp) {
        super(gp);
        this.gp = gp;
        interactable = true;
        image = setup("/items/pouch4");
        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        getGlitterImages();
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY,null);
        }

        if(showGlitter && shouldShowGlitter){
            drawInteractableGlitter(g2);
        }
    }

    public void interact(){
        gp.ui.addMessage("You found " + goldValue + " gold in the pouch.");
        gp.player.winGold(goldValue);
        gp.progress.setProgressInteract(this);
        interactable = false;
        gp.interactObjects.remove(this);
    }
}
