package entity.attacks.projectile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class SHADOW_Projectile extends MON_ProjectilesWithSprite {

    // image = images[Math.min(19,(maxLife - life) / (maxLife/20))];
    public BufferedImage[] images;

    public SHADOW_Projectile(GamePanel gp, Entity originEntity, Entity targetEntity, int accuracy) {
        super(gp);
        this.gp = gp;
        gp.playSE(17);
        auraRadiusSpell = 20;
        this.originEntity = originEntity;
        this.accuracy = accuracy;
        life = 300;
        maxLife = life;
        speed = 5;
        damageType = 2;
        damage = 21;
        //image = setup("/spell/redfireball/fireball", 64, 64); // placeholder image
        getImages();

        worldX = originEntity.worldMiddleX() - images[0].getWidth() / 2;
        worldY = originEntity.worldMiddleY() - images[0].getHeight() / 2;

        int distance = (int) Math.sqrt(Math.pow(worldX + 32 - targetEntity.worldMiddleX(), 2)
                + Math.pow(worldY + 32 - targetEntity.worldMiddleY(), 2));

        double baseAngle = Math.atan2(targetEntity.worldMiddleY() - originEntity.worldMiddleY(), targetEntity.worldMiddleX() - originEntity.worldMiddleX());

        double maxDeviationRadians = Math.toRadians(45.0);
        double accuracyFactor = accuracy / 10.0; // Scales accuracy between 0 and 1
        double angleDeviation = (new Random().nextDouble() * 2 - 1) * maxDeviationRadians * accuracyFactor;

        double finalAngle = baseAngle + angleDeviation;

        if(finalAngle > Math.PI / 4 && finalAngle < Math.PI / 4 * 3){
            direction = "down";
        } else if(finalAngle >= Math.PI  * 3 / 4 && finalAngle < Math.PI / 4 * 5){
            direction = "left";
        } else if(finalAngle >= Math.PI  * 5 / 4 && finalAngle < Math.PI / 4 * 7){
            direction = "up";
        } else {
            direction = "right";
        }

        double projectileAimLocationX = originEntity.worldMiddleX() + Math.cos(finalAngle) * distance;
        double projectileAimLocationY = originEntity.worldMiddleY() + Math.sin(finalAngle) * distance;

        double projectileStartLocationX = originEntity.worldMiddleX();
        double projectileStartLocationY = originEntity.worldMiddleY();

        double rotationRequired = Math.atan2(projectileAimLocationY - projectileStartLocationY, projectileAimLocationX - projectileStartLocationX);

        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, images[0].getWidth() / 2, images[0].getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        double deltaX = speed * Math.cos(rotationRequired);
        double deltaY = speed * Math.sin(rotationRequired);

        roundedDeltaX1 = (int) Math.round(deltaX);
        roundedDeltaY1 = (int) Math.round(deltaY);

        originEntityAtCastX = originEntity.worldX - gp.player.worldX + gp.player.screenX;
        originEntityAtCastY = originEntity.worldY - gp.player.worldY + gp.player.screenY;

        int shouldBeX = (int) Math.round(deltaX * 4);
        int shouldBeY = (int) Math.round(deltaY * 4);

        xDiff = shouldBeX - roundedDeltaX1 * 4;
        yDiff = shouldBeY - roundedDeltaY1 * 4;

        gp.spells.add(this);
    }

    public void getImages(){
        gp.entityImageLoader.matchBlueAttackProjectile(this);
    }

    public void update(){
        life--;
        collision();
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
                if(life > 40 && distance < auraRadiusSpell +  currentEntity.auraRadius() && !hitEnemies.contains(currentEntity) && currentEntity != originEntity) {
                    hitEnemies.add(currentEntity);
                    currentEntity.loseLife(damage, damageType, originEntity, currentEntity, false, gp);
                    currentEntity.actionToDamageAI(originEntity);
                    if(attackStunStrength > 0){
                        currentEntity.receiveStun(attackStunStrength);
                    }
                    life = 40;
                }
            }
        }
        if (life == 39){
            roundedDeltaX1 = roundedDeltaX1/3;
            roundedDeltaY1 = roundedDeltaY1/3;
        }
        if(life <= 0){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        /**
         attackSpriteNum = Math.min((attackFrameCounter) / ( maxattackframes/ 9 ), 8);
         attack instance creation at 120th frame
         finish attacking at 140th frame
         casting animation number = 2;
         max animation sprites = 9;
         attackSpriteNum = Math.min((((attackFrameCounter - 120) / ((140 - 120) / 9)) + 3), 8);
         */

        if(life == maxLife || life == maxLife - 1){
            spriteNum = 0;
        } else if( life == maxLife - 4){
            spriteNum = 1;
        } else if (life == maxLife - 8){
            spriteNum = 2;
        } else if (life < maxLife - 8 && life > 40){
            spriteNum = 3;
        } else if (life < 40){
            spriteNum = 3 + (40 - life) * 11 / 40;
        }

        image = images[Math.min(14,spriteNum)];


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

    public void collision(){
        if (life > 40){
            gp.cChecker.checkTile(this);

            if(worldX < 0 || worldY < 0 || worldX > gp.currentMapMaxCol * GamePanel.tileSize -  1.1 * GamePanel.tileSize || worldY > gp.currentMapMaxRow * GamePanel.tileSize - 1.5 * GamePanel.tileSize){
                life = 40;
            }

            if(collisionOn){
                life = 40;
            }
        }
    }
}
