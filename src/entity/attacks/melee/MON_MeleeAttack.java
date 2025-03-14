package entity.attacks.melee;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MON_MeleeAttack extends Entity {
    Rectangle attackRectangle = new Rectangle();
    Entity originEntity;
    int damageType;
    BufferedImage[] images = new BufferedImage[9];
    BufferedImage image2;
    int multiplier;
    public MON_MeleeAttack(GamePanel gp, int duration, String Melee_AttackDirection, int Melee_attackRange, Entity originEntity){
        super(gp);
        this.gp = gp;
        this.life = 50;
        this.originEntity = originEntity;
        maxLife = life;
        gp.attacks.add(this);
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
            /*
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
                break;*/
        }

        worldX = attackRectangle.x - gp.player.screenX + gp.player.worldX;
        worldY = attackRectangle.y - gp.player.screenY + gp.player.worldY;
        image = setup("/other/redtransparent", attackRectangle.width, attackRectangle.height);
        multiplier = life / 7;
        getImages();
    }

    public void getImages(){
        BufferedImage tempImage = setupImage("/spell/attack/attacksheet1");
        for (int i = 0; i < 8; i++) {
            images[i] = setupSheet(tempImage, i * 140,0 , 140, 140, attackRectangle.width, attackRectangle.height);
        }
    }

    public void update(){
        life --;
        spriteCounter++;
        if (life <= 0 ){
            gp.attacks.remove(this);
        }

        // only check hitbox first frame, rest is only to draw if visible hitbox is on
        if (life == maxLife - 1){
            if(originEntity.attackSoundReferenceNumber != -1){
                gp.playSE(originEntity.attackSoundReferenceNumber);
            }
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
        if(multiplier != 0){
            image2 = images[spriteCounter / multiplier];
        }
        else image = images[0];

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(gp.visibleHitBox){
            g2.drawImage(image, screenX, screenY,null);
        }
        g2.drawImage(image2, screenX, screenY, null);
    }
}
