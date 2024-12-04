package object.puzzle.harrypotterpuzzle;

import main.GamePanel;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_HarryPotterPuzzle extends SuperObject {

    //called in a1 map 10, as object 0.

    public OBJ_HarryPotterPuzzle(GamePanel gp){
        super(gp);
        this.gp = gp;
        if(!gp.progress.act1BookPickedUp[6]){
            interactable = true;
        } else interactable = false;
        solidArea.y = 32;
        solidArea.height = 64;
        getUpdatedImage();
        getGlitterImages();
    }

    public void interact(){
        gp.ui.commandNum = 0;
        gp.ui.previousCommandNum = 0;
        gp.gameState = gp.puzzleStateHarryPotter;
        if(!gp.harryPotterPuzzle.startFireRemoved){
            gp.harryPotterPuzzle.startFireAdded = true;
            gp.obj[10][29] = new OBJ_FireObject(gp);
            gp.obj[10][29].worldX = 12 * gp.tileSize;
            gp.obj[10][29].worldY = 14 * gp.tileSize;
        }
    }

    public void getUpdatedImage(){
        image = new BufferedImage(160, 160, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)image.createGraphics();
        for (int i = 0; i < 7; i++) {
            if(!gp.harryPotterPuzzle.potions[i].drank){
                g2.drawImage(gp.harryPotterPuzzle.potions[i].image, 20, i * 19,19, 19, null);
            }
        }
        g2.dispose();
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX +  gp.tileSize  > gp.player.worldX - gp.player.screenX &&
                worldX -  gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY +  gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY -  gp.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY,null);

            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
        }
    }
}
