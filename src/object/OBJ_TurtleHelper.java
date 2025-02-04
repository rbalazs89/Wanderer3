package object;

import entity.npc.NPC_FreedTortoise;
import entity.npc.NPC_TurnedOverTortoise;
import main.GamePanel;

import java.awt.*;

public class OBJ_TurtleHelper extends SuperObject{
    int NPCIndexInContainer = -1;
    int worldXFromContainer = -1;
    int worldYFromContainer = -1;
    public OBJ_TurtleHelper(GamePanel gp){
        super(gp);
        //this.gp = gp;
        interactable = true;
        getGlitterImages();
        image = setup("/nullimage");
    }

    public void interact(){
        gp.playSE(80);

        //special: remove related npc and get its data to instantiate next NPC
        for (int i = 0; i < GamePanel.maxNPC; i++) {
            if(gp.npc[gp.currentMap][i] != null){
                if(gp.npc[gp.currentMap][i] instanceof NPC_TurnedOverTortoise){
                    worldXFromContainer = gp.npc[gp.currentMap][i].worldX;
                    worldYFromContainer = gp.npc[gp.currentMap][i].worldY;
                    NPCIndexInContainer = i;
                    gp.npc[gp.currentMap][i] = null;
                    break;
                }
            }
        }

        //nulls itself:
        for (int i = 0; i < GamePanel.maxOBJ; i++) {
            if(gp.obj[gp.currentMap][i] != null){
                if(gp.obj[gp.currentMap][i] instanceof OBJ_TurtleHelper){
                    gp.obj[gp.currentMap][i] = null;
                    gp.interactObjects.remove(this);
                    break;
                }
            }
        }

        //replace NPC with next NPC
        spawnNextNPC();

        // update quest:
        gp.progress.act1InteractedObjects[4] = true;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX +  gp.tileSize  > gp.player.worldX - gp.player.screenX &&
                worldX -  gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY +  gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY -  gp.tileSize < gp.player.worldY + gp.player.screenY){
            //g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);

            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
        }
    }

    public void spawnNextNPC(){
        gp.npc[gp.currentMap][NPCIndexInContainer] = new NPC_FreedTortoise(gp);
        gp.npc[gp.currentMap][NPCIndexInContainer].worldX = worldXFromContainer;
        gp.npc[gp.currentMap][NPCIndexInContainer].worldY = worldYFromContainer;
    }
}
