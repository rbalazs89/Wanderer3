package entity.attacks;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class BRO_MeleeAttack extends Entity {
    Rectangle attackRectangle = new Rectangle();
    Entity originEntity;
    int damageType;
    int multiplier;
    public BRO_MeleeAttack(GamePanel gp, String Melee_AttackDirection, int Melee_attackRange, Entity originEntity){
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
            case "up" -> {
                attackRectangle.x = middleX - GamePanel.tileSize;
                attackRectangle.y = middleY - GamePanel.tileSize / 2 - Melee_attackRange;
                attackRectangle.width = GamePanel.tileSize * 2;
                attackRectangle.height = GamePanel.tileSize / 2 + Melee_attackRange;
            }
            case "right" -> {
                attackRectangle.x = middleX;
                attackRectangle.y = middleY - GamePanel.tileSize;
                attackRectangle.width = GamePanel.tileSize / 2 + Melee_attackRange;
                attackRectangle.height = GamePanel.tileSize * 2;
            }
            case "down" -> {
                attackRectangle.x = middleX - GamePanel.tileSize;
                attackRectangle.y = middleY;
                attackRectangle.width = GamePanel.tileSize * 2;
                attackRectangle.height = GamePanel.tileSize / 2 + Melee_attackRange;
            }
            case "left" -> {
                attackRectangle.x = middleX - GamePanel.tileSize / 2 - Melee_attackRange;
                attackRectangle.y = middleY - GamePanel.tileSize;
                attackRectangle.width = GamePanel.tileSize / 2 + Melee_attackRange;
                attackRectangle.height = GamePanel.tileSize * 2;
            }
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
            gp.playSE(65);
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
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        if(gp.visibleHitBox){
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            g2.drawImage(image, screenX, screenY,null);
        }
    }
}

