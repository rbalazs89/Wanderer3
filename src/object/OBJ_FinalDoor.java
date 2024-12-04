package object;

import main.GamePanel;
import tile.MapBlock;

import java.awt.*;

public class OBJ_FinalDoor extends SuperObject {

    public OBJ_FinalDoor(GamePanel gp){
        super(gp);
        this.gp = gp;
        image = setup("/objects/door/bossdoor");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 2 * gp.tileSize;
        solidArea.height = 3 * gp.tileSize;
    }

    public void interactByOtherObject(){
        collision = false;
        solidArea.width = 0; // to avoid multiple obj interacts after no collision
        solidArea.height = 0;
        image = setup("/objects/door/bossdoor2");
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
    }
}
