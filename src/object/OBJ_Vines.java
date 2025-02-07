package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_Vines extends SuperObject{

    public OBJ_Vines(GamePanel gp) {
        super(gp);
        image = setup("/objects/wall/vines",GamePanel.tileSize * 2,GamePanel.tileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 64 * 2;
        solidArea.height = 64;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY, null);

            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
            drawSpecial(g2);
        }
    }
}
