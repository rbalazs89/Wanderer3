package entity.attacks.player;

import entity.Entity;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Firebolt_Dragon extends Spells{
    //this class is only used by dragon totem to spawn missiles
    int accuracy;
    int originEntityAtCastX;
    int originEntityAtCastY;

    public Firebolt_Dragon(GamePanel gp, Entity originEntity, Entity targetEntity, int accuracy){
        super(gp);
        this.gp = gp;
        setValues();
        gp.playSE(17);
        auraRadiusSpell = 20;
        this.originEntity = originEntity;
        this.accuracy = accuracy;
        life = 200;
        maxLife = life;
        speed = 6;
        damageType = 1;
        image = setup("/spell/totem/firebolt");

        worldX = originEntity.worldMiddleX() - image.getWidth() / 2;
        worldY = originEntity.worldMiddleY() - image.getHeight() / 2;

        int distance  = (int) Math.sqrt(Math.pow(worldX + 32 - targetEntity.worldMiddleX(), 2)
                + Math.pow(worldY + 32 - targetEntity.worldMiddleY(), 2));

        double baseAngle = Math.atan2(targetEntity.worldMiddleY() - originEntity.worldMiddleY(), targetEntity.worldMiddleX() - originEntity.worldMiddleX());

        double maxDeviationRadians = Math.toRadians(45.0);
        double accuracyFactor = accuracy / 10.0; // Scales accuracy between 0 and 1
        double angleDeviation = (new Random().nextDouble() * 2 - 1) * maxDeviationRadians * accuracyFactor;

        double finalAngle = baseAngle + angleDeviation;

        double projectileAimLocationX = originEntity.worldMiddleX() + Math.cos(finalAngle) * distance;
        double projectileAimLocationY = originEntity.worldMiddleY() + Math.sin(finalAngle) * distance;

        double projectileStartLocationX = originEntity.worldMiddleX();
        double projectileStartLocationY = originEntity.worldMiddleY();

        double rotationRequired = Math.atan2(projectileAimLocationY - projectileStartLocationY, projectileAimLocationX - projectileStartLocationX);

        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired,image.getWidth() / 2, image.getHeight() / 2);
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

    public void update(){

        worldX += roundedDeltaX1;
        worldY += roundedDeltaY1;
        adjustCounter++;

        if(adjustCounter == 4){
            adjustCounter = 0;
            worldX +=  xDiff;
            worldY +=  yDiff;
        }

        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity tempEntity = gp.allFightingEntities.get(i);
            if(isHostile(gp.player, tempEntity)){
                int distance  = (int) Math.sqrt(Math.pow(worldX + 32 - tempEntity.worldMiddleX(), 2)
                        + Math.pow(worldY + 32 - tempEntity.worldMiddleY(), 2));
                if(distance < auraRadiusSpell + tempEntity.auraRadius() && !hitEnemies.contains(tempEntity) && tempEntity != originEntity) {

                    hitEnemies.add(tempEntity);
                    tempEntity.loseLife(damage, damageType, gp.player, tempEntity, false, gp);
                    tempEntity.actionToDamageAI(originEntity);
                    hitAnEnemy = true;
                    soundPlayed = true;
                    life = 0; //deletes projectile
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

    public void setValues(){
        level = gp.player.allSpellList.allPlayerAvailableSpells[3].currentPointsOnSpell;
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getSpellDamage(3,level));
    }
}
