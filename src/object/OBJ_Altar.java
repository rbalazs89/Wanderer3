package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_Altar extends SuperObject{

    GamePanel gp;

    public OBJ_Altar(GamePanel gp) {
        super(gp);
        this.gp = gp;
        interactable = true;
        image = setup("/objects/interact/altarchurch");
        solidArea.x = 128 - 15;
        solidArea.y = 220 - 15;
        solidArea.width = 30;
        solidArea.height = 30;
        getGlitterImages();
    }

    public void interact(){
        gp.player.resetLevels();
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + 5 * gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - 5 * gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + 5 * gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - 5 * gp.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY,null);
        }

        if(showGlitter && shouldShowGlitter){
            drawInteractableGlitter(g2);
        }
    }
}
