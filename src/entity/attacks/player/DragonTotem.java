package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class DragonTotem extends Spells{
    private int counter;
    public DragonTotem(GamePanel gp){
        super(gp);
        setValues();
        life = 10;
        maxLife = life;
        for (int i = 0; i < gp.spells.size(); i++) {
            Entity currentSpell = gp.spells.get(i);
            if(currentSpell instanceof DragonTotem){
                gp.spells.remove(currentSpell);
            }
        }
        gp.playSE(40);
        image = setup("/spell/totem/totem");
        gp.spells.add(this);
        worldX = gp.mouseH.mouseX - GamePanel.tileSize / 2 - gp.player.screenX + gp.player.worldX;
        worldY = gp.mouseH.mouseY - GamePanel.tileSize / 2 - gp.player.screenY + gp.player.worldY;
        if(worldX < 0){
            worldX = 0;
        } else if(worldX > gp.currentMapMaxCol * GamePanel.tileSize)
            worldX = (gp.currentMapMaxCol - 1) * GamePanel. tileSize;
        if(worldY < 0){
            worldY = 0;
        } else if(worldY > gp.currentMapMaxRow * GamePanel.tileSize)
            worldY = (gp.currentMapMaxRow - 1) * GamePanel. tileSize;
    }

    public void update(){
        counter ++;
        if (counter > 60){
            counter = 0;
            int middleDistance;
            int smallestDistance = 9 * GamePanel.tileSize;
            int smallestDistanceIndex = -1;
            for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                Entity currentEntity = gp.allFightingEntities.get(i);
                if(isHostile(gp.player,currentEntity)){
                    middleDistance = middleDistance(currentEntity);
                    if(middleDistance < smallestDistance) {
                        smallestDistance = middleDistance;
                        smallestDistanceIndex = i;
                    }
                }
            }
            if (smallestDistanceIndex != -1){
                new Firebolt_Dragon(gp,this,gp.allFightingEntities.get(smallestDistanceIndex),2);
            }

            life --;
            if( life <= 0){
                gp.spells.remove(this);
            }
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.drawImage(image, screenX, screenY, null);
    }

    public void setValues(){
        level = gp.player.allSpellList.allPlayerAvailableSpells[3].currentPointsOnSpell;
        life = gp.player.allSpellList.getSpellDamage(3,level);
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(3, level);
    }
}
