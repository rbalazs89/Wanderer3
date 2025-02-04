package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_MomoRefillOnce extends SuperObject{

    public OBJ_MomoRefillOnce(GamePanel gp, int pictureNumber){
        super(gp);
        getGlitterImages();
        interactable = true;
        name = "pumpkin";

        if(pictureNumber == 1){
            image = setup("/objects/interact/pumpkin");
        }
        else if(pictureNumber == 2){
            image = setup("/objects/interact/pumpkin2");
        } else if(pictureNumber == 3){
            image = setup("/objects/interact/pumpkin3");
        } else if (pictureNumber == 4){
            image = setup("/objects/interact/pumpkin4");
        }

        solidArea.x = 256 / 2 - 32;
        solidArea.y = image.getHeight() / 2 - 32;
        solidArea.width = 64;
        solidArea.height = image.getHeight() / 2;
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

    public void interact(){
        if(gp.player.momoJuice.charge != gp.player.momoJuice.maxCharge){
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.player.momoJuice.refill();
            gp.interactObjects.remove(this);
            interactable = false;
        } else {
            gp.ui.addMessage("The pumpkin juice bottle is already full");
        }
    }
}
