package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_Mushroom extends SuperObject{

    public OBJ_Mushroom(GamePanel gp) {
        super(gp);
        interactable = true;
        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        image = setup("/objects/interact/mushroom", 32,32);
        getGlitterImages();
    }

    public void interact(){
        gp.playSE(88);

        // adds half timer if buff already up
        if(gp.player.speedFromMushroom == 1.5f){
            gp.player.mushroomCounter -= 15 * 60;
        }

        gp.ui.addMessage("You have eaten a mushroom, you feel faster.");

        gp.player.speedFromMushroom = 1.5f;
        gp.player.refreshPlayerStatsWithItems();
        interactable = false;

        image = setup("/objects/interact/mushroomeaten", 32, 32);
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX + 16, screenY + 16, null);

            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
            drawSpecial(g2);
        }
    }
}
