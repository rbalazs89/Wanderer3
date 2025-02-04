package entity.attacks.melee;

import entity.Entity;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MON_MeleeAttackStun extends Entity {
    //uses different image, but functionality same
    Rectangle attackRectangle = new Rectangle();
    Entity originEntity;
    int damageType;
    BufferedImage[] images = new BufferedImage[8];
    BufferedImage image2;
    int multiplier;
    int attackStunStrength;
    public MON_MeleeAttackStun(GamePanel gp, String Melee_AttackDirection, int Melee_attackRange, Entity originEntity, int attackStunStrength){
        super(gp);
        this.gp = gp;
        //this.life = duration * 2;
        this.life = 50;
        this.originEntity = originEntity;
        this.attackStunStrength = attackStunStrength;
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
        getImages();
    }

    public void getImages(){
        BufferedImage tempImage = setupImage("/spell/attack2/meleeattack");
        for (int i = 0; i < 7; i++) {
            images[i] = setupSheet(tempImage, i*128,0,128,128,attackRectangle.width + 20,attackRectangle.height + 20);
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
                        tempEntity.receiveStun(attackStunStrength);
                    }
                }
            }
        }

    }

    public void draw(Graphics2D g2){
        if(multiplier != 0){
            image2 = images[spriteCounter / multiplier];
        } else image = images[0];

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(gp.visibleHitBox){
            g2.drawImage(image, screenX, screenY,null);
        }
        g2.drawImage(image2, screenX - 10, screenY - 10, null);
    }
}


