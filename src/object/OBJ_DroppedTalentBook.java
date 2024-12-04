package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_DroppedTalentBook extends SuperObject{

    public OBJ_DroppedTalentBook(GamePanel gp){
        super(gp);
        this.gp = gp;
        interactable = true;
        image = setup("/objects/pickables/talentbook");
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

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY,null);
        }

        if(showGlitter && shouldShowGlitter){
            drawInteractableGlitter(g2);
        }
    }

    public void interact(){
        gp.player.pickUpTalentBook();
        gp.interactObjects.remove(this);
        gp.progress.setTalentBook(gp.currentMap, -1);
    }
}
