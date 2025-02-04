package entity.attacks.projectile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;

public class MON_ProjectilesWithSprite extends Entity {
    public int attackStunStrength;
    public static final Color transparentRed = new Color(255, 0, 0, 128);
    public int accuracy = 3; // 0 -> accurate, 10 -> 0...45 degree miss randomly generated
    Entity originEntity;
    public AffineTransformOp op;
    int auraRadiusSpell;
    public int roundedDeltaX1;
    public int roundedDeltaY1;
    public int yDiff;
    public int xDiff;
    public int originEntityAtCastX;
    public int originEntityAtCastY;
    public int adjustCounter = 0;
    public ArrayList<Entity> hitEnemies = new ArrayList<>();
    public boolean hitAnEnemy;
    public boolean soundPlayed;
    public int damageType;
    public int damage;
    public MON_ProjectilesWithSprite(GamePanel gp){
        super(gp);
        this.gp = gp;
    }

    public void update(){
        life--;
        worldX += roundedDeltaX1;
        worldY += roundedDeltaY1;
        adjustCounter++;

        if(adjustCounter == 4){
            adjustCounter = 0;
            worldX +=  xDiff;
            worldY +=  yDiff;
        }

        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity currentEntity = gp.allFightingEntities.get(i);
            if(isHostile(originEntity,  currentEntity)){
                int distance  = (int) Math.sqrt(Math.pow(worldX + 32 -  currentEntity.worldMiddleX(), 2)
                        + Math.pow(worldY + 32 -  currentEntity.worldMiddleY(), 2));
                if(distance < auraRadiusSpell +  currentEntity.auraRadius() && !hitEnemies.contains(currentEntity) &&  currentEntity != originEntity) {
                    hitEnemies.add( currentEntity);
                    currentEntity.loseLife(damage, damageType, originEntity, currentEntity, false, gp);
                    currentEntity.actionToDamageAI(originEntity);

                    if(attackStunStrength > 0){
                        currentEntity.receiveStun(attackStunStrength);
                    }
                    hitAnEnemy = true;
                    soundPlayed = true;
                    //life = 0; //deletes projectile

                }
            }
        }
        if(life <= 0){
            gp.spells.remove(this);
        }

    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(op.filter(image, null), screenX, screenY, null);
        }

        if(gp.visibleHitBox) {
            g2.setColor(transparentRed);
            int radius = auraRadiusSpell;
            g2.fillOval(screenX + 32 - radius, screenY + 32 - radius, 2 * radius, 2 * radius);
        }
    }
}
