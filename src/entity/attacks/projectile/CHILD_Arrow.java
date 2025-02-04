package entity.attacks.projectile;

import entity.Entity;
import main.GamePanel;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class CHILD_Arrow extends MON_ProjectilesWithSprite {

    // accuracy = 0 -> accurate, accuracy = 10 -> 0...45 degree miss randomly generated
    public CHILD_Arrow(GamePanel gp, Entity originEntity, Entity targetEntity, int accuracy, int attackStunStrength){
        super(gp);
        this.gp = gp;
        this.attackStunStrength = attackStunStrength;
        gp.playSE(82);
        auraRadiusSpell = 20;
        this.originEntity = originEntity;
        this.accuracy = accuracy;
        life = 230;
        maxLife = life;
        speed = 5;
        damageType = 0;
        damage = 1;
        image = setup("/spell/arrow/arrow");

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
}