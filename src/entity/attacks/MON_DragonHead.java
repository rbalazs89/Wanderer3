package entity.attacks;

import entity.Entity;
import entity.attacks.player.Spells;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MON_DragonHead extends Spells {
    public BufferedImage[] images = new BufferedImage[27];
    public MON_DragonHead(GamePanel gp, int positionX, int positionY, Entity originEntity){
        super(gp);
        this.gp = gp;
        this.originEntity = originEntity;
        damageType = 1;
        damage = 50;
        stunStrength = 20;
        life = 108;
        gp.spells.add(this);
        worldX = positionX - gp.player.screenX + gp.player.worldX;
        worldY = positionY - gp.player.screenY + gp.player.worldY;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.height = 64;
        solidArea.width = 64;
        gp.entityImageLoader.matchFireDragonImages(this);
        gp.playSE(68);

    }

    public void update() {
        life --;
        if (life <= 0){
            gp.spells.remove(this);
        }
        if (life == 45){
            solidArea.x = worldX + 32;
            solidArea.y = worldY + 32;
            for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                Entity currentEntity = gp.allFightingEntities.get(i);
                if(isHostile(currentEntity, originEntity) && currentEntity != originEntity){
                    currentEntity.solidArea.x = currentEntity.worldX + currentEntity.solidArea.x;
                    currentEntity.solidArea.y = currentEntity.worldY + currentEntity.solidArea.y;
                    if(solidArea.intersects(currentEntity.solidArea)){
                        currentEntity.loseLife(damage, damageType, originEntity, currentEntity, false, gp);
                        currentEntity.receiveStun(stunStrength);
                    }
                    currentEntity.solidArea.x = currentEntity.solidAreaDefaultX;
                    currentEntity.solidArea.y = currentEntity.solidAreaDefaultY;
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX + gp.player.screenX - gp.player.worldX;
        int screenY = worldY + gp.player.screenY - gp.player.worldY;
        int imageNumber = Math.min((108 - life) / 4, 27);
        g2.drawImage(images[imageNumber], screenX, screenY, null);
        if(gp.visibleHitBox){
            g2.setColor(transparentRed);
            g2.fillRect(screenX + 32,screenY + 32,64,64);
        }
    }
}
