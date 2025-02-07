package entity.monster.act1;

import entity.Fighter;
import entity.attacks.melee.MON_MeleeAttackStun;
import main.GamePanel;

import java.awt.*;

public class MON_Ogre extends Fighter {

    public MON_Ogre(GamePanel gp) {
        super(gp);
        this.gp = gp;
        entityType = 2;
        getImage();

        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Ogre";
        movementSpriteChangeFrame = 14;
        movementSpritesNumber = 2;

        //Balance:
        itemDropChance = 11;
        level = 5;
        damageOnContactValue = 5;
        attackSoundReferenceNumber = 33;
        attackCoolDownValue = 100;
        attackChanceWhenAvailable = 100;
        attackDamage = 40;
        defaultSpeed = 1;
        meleeAttackRange = GamePanel.tileSize;
        maxLife = 250;
        experienceValue = 310;

        aggroAtThisDistance = 3 * GamePanel.tileSize;
        shouldTryToAttackRange = (int) (2.5 * GamePanel.tileSize);

        /////
        life = maxLife;
        speed = defaultSpeed;
    }

    public void getImage(){
        gp.entityImageLoader.matchOgreImages(this);
    }

    public void createAttackInstance() {
        new MON_MeleeAttackStun(gp, attackDirection, meleeAttackRange, this, 25);
    }

    public void drawGetWalkingImage(){
        switch (direction) {
            case "up":
                if (spriteNum == 0) {
                    image = up1;
                } else if (spriteNum == 1) {
                    image = up2;
                }
                break;
            case "right":
                if (spriteNum == 0) {
                    image = right1;
                } else if (spriteNum == 1) {
                    image = right2;
                }
                break;
            case "down":
                if (spriteNum == 0) {
                    image = down1;
                } else if (spriteNum == 1) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 0) {
                    image = left1;
                } else if (spriteNum == 1) {
                    image = left2;
                }
                break;
        }
    }

    public void drawGetWalkSpriteNumber(){
        if (gp.gameState == gp.playState && !stunned && !frozen) {
            spriteCounter++;
            if (spriteCounter > movementSpriteChangeFrame) {
                spriteNum = (spriteNum + 1) % movementSpritesNumber;
                spriteCounter = 0;
            }
        }
    }

    public void drawAttackImageMelee1(){
        if(attackFrameCounter <= attackFramePoint1){
            switch (attackDirection) {
                case "up" -> image = attackUp1;
                case "right" -> image = attackRight1;
                case "down" -> image = attackDown1;
                case "left" -> image = attackLeft1;
            }
        } else {
            switch (attackDirection) {
                case "up" -> image = attackUp2;
                case "right" -> image = attackRight2;
                case "down" -> image = attackDown2;
                case "left" -> image = attackLeft2;
            }
        }
        //image correction:
        switch (attackDirection) {
            case "up" -> {
                screenX = screenX - (image.getWidth() / 2 - GamePanel.tileSize / 2);
                screenY = screenY - (image.getHeight() - GamePanel.tileSize);
            }
            case "right" -> screenY = screenY - (image.getHeight() / 2 - GamePanel.tileSize / 2);
            case "down" -> screenX = screenX - (image.getWidth() / 2 - GamePanel.tileSize / 2);
            case "left" -> {
                screenX = screenX - (image.getWidth() - GamePanel.tileSize);
                screenY = screenY - (image.getHeight() / 2 - GamePanel.tileSize / 2);
            }
        }
    }
    public void drawDying(){
        image = null;
    }
}
