package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class MeleeAttack extends Entity {
    public ArrayList<Entity> hitEnemies = new ArrayList<>();
    public Rectangle attackRectangle = new Rectangle(0,0,0,0);
    public BufferedImage image;
    public Entity originEntity;
    public int[] soundEffectsHit = new int[2];
    public int[] soundEffectsMiss = new int[2];
    public int[] soundEffectsHitBarrel = new int[2];
    public boolean soundPlayed = false;
    public boolean hitAnEnemy = false;
    public MeleeAttack(GamePanel gp){
        super(gp);
        this.gp = gp;
    }

    public void update() {

        life--;

        if(life < 0){
            gp.attacks.remove(this);
        }

        for (int i = 0; i < gp.allFightingEntities.size(); i++) {

            Entity tempEntity = gp.allFightingEntities.get(i);

            Rectangle enemyRectangle = new Rectangle(tempEntity.worldX + tempEntity.solidArea.x,
                    tempEntity.worldY + tempEntity.solidArea.y,
                    tempEntity.solidArea.width,
                    tempEntity.solidArea.height);

            if(isHostile(originEntity, tempEntity)) {
                if(enemyRectangle.intersects(attackRectangle) && !hitEnemies.contains(tempEntity) && tempEntity != originEntity){
                    if(gp.player.waveDamage == 0){
                        gp.playSE((int) (Math.random() * soundEffectsHit[0]) + soundEffectsHit[1]);
                        tempEntity.loseLife(originEntity.attackDamage, 0, originEntity, tempEntity, false, gp);
                    } else if(gp.player.waveDamage > 0){
                        tempEntity.loseLife(originEntity.attackDamage + gp.player.waveDamage, 0, originEntity, tempEntity, false, gp);
                        gp.playSE(50);
                        gp.player.waveDamage = 0;
                    }

                    hitEnemies.add(tempEntity);
                    tempEntity.actionToDamageAI(originEntity);
                    hitAnEnemy = true;
                    soundPlayed = true;
                }
            }
        }

        if(!hitAnEnemy && !soundPlayed){
            soundPlayed = true;
            gp.playSE((int)(Math.random() *  soundEffectsMiss[0]) + soundEffectsMiss[1] );
        }
    }

    public void draw(Graphics2D g2){
        int screenX = attackRectangle.x - gp.player.worldX + gp.player.screenX;
        int screenY = attackRectangle.y - gp.player.worldY + gp.player.screenY;
        g2.drawImage(image, screenX, screenY,null);
    }
}

