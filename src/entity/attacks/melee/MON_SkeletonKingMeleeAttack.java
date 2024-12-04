package entity.attacks.melee;

import entity.Entity;
import entity.attacks.vfx.ParticleEffectForAttack;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;

public class MON_SkeletonKingMeleeAttack extends Entity {
    Rectangle attackRectangle = new Rectangle();
    Entity originEntity;
    int damageType;
    int multiplier;
    public MON_SkeletonKingMeleeAttack(GamePanel gp, int duration, String Melee_AttackDirection, int Melee_attackRange, Entity originEntity){
        super(gp);
        this.gp = gp;
        //this.life = duration * 2;
        this.life = 50;
        this.originEntity = originEntity;
        maxLife = life;
        gp.attacks.add(this);
        //int middleX = originEntity.worldX - gp.player.worldX + gp.player.screenX + gp.tileSize / 2;
        //int middleY = originEntity.worldY - gp.player.worldY + gp.player.screenY + gp.tileSize / 2;
        int middleX = originEntity.screenMiddleX();
        int middleY = originEntity.screenMiddleY();
        damageType = 0; //physical
        switch (Melee_AttackDirection) {
            case "up":
                attackRectangle.x = middleX - gp.tileSize;
                attackRectangle.y = middleY - gp.tileSize / 2 - Melee_attackRange;
                attackRectangle.width = gp.tileSize * 2;
                attackRectangle.height = gp.tileSize / 2 + Melee_attackRange;
                break;
            case "right":
                attackRectangle.x = middleX;
                attackRectangle.y = middleY - gp.tileSize;
                attackRectangle.width = gp.tileSize / 2 + Melee_attackRange;
                attackRectangle.height = gp.tileSize * 2;
                break;
            case "down":
                attackRectangle.x = middleX - gp.tileSize;
                attackRectangle.y = middleY;
                attackRectangle.width = gp.tileSize * 2;
                attackRectangle.height = gp.tileSize / 2 + Melee_attackRange;
                break;
            case "left":
                attackRectangle.x = middleX - gp.tileSize/2 - Melee_attackRange;
                attackRectangle.y = middleY - gp.tileSize;
                attackRectangle.width = gp.tileSize / 2 + Melee_attackRange;
                attackRectangle.height = gp.tileSize * 2;
                break;
        }

        worldX = attackRectangle.x - gp.player.screenX + gp.player.worldX;
        worldY = attackRectangle.y - gp.player.screenY + gp.player.worldY;
        image = setup("/other/redtransparent", attackRectangle.width, attackRectangle.height);
        multiplier = life / 7;
    }


    public void update(){
        life --;
        spriteCounter++;
        if (life <= 0 ){
            gp.attacks.remove(this);
        }

        // only check hitbox first frame, rest is only to draw if visible hitbox is on
        if (life == maxLife - 1){

            //this screenX is only for particle effect, not hitbox calculation
            int screenX = worldX - gp.player.worldX + gp.player.screenX + attackRectangle.width / 2 - 15;
            int screenY = worldY - gp.player.worldY + gp.player.screenY + attackRectangle.height / 2 - 15;
            new ParticleEffectForAttack(gp, screenX, screenY, screenX + 120, screenY + 120);

            for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                Entity tempEntity = gp.allFightingEntities.get(i);
                if(isHostile(originEntity, tempEntity) && tempEntity != originEntity){

                    Rectangle potentialTargetRectangle = new Rectangle(
                            tempEntity.screenX() + tempEntity.solidArea.x,
                            tempEntity.screenY() + tempEntity.solidArea.y,
                            tempEntity.solidArea.width,
                            tempEntity.solidArea.height
                    );
                    if(potentialTargetRectangle.intersects(attackRectangle)){
                        tempEntity.loseLife(originEntity.attackDamage, 0, originEntity, tempEntity, false, gp);
                        tempEntity.receiveStun(25);
                    }
                }
            }
        }

    }

    public void draw(Graphics2D g2){
        /*if(multiplier != 0){
            image2 = images[spriteCounter / multiplier];
        } else image = images[0];

        */
        if(gp.visibleHitBox){
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            g2.drawImage(image, screenX, screenY,null);
        }
        //g2.drawImage(image2, screenX - 10, screenY - 10, null);
    }
}
