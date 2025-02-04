package entity.attacks.player;

import entity.Entity;
import entity.Fighter;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class IceBolt extends Spells{

    public IceBolt(GamePanel gp){
        super(gp);
        setValues();
        image = setup("/spell/icebolt/icebolt");

        auraRadiusSpell = 20;
        life = 150;
        maxLife = life;
        speed = 5;
        damageType = 2; // cold element

        mouseLocationX = gp.mouseH.mouseX;
        mouseLocationY = gp.mouseH.mouseY;

        int centerX = gp.getWidth() / 2;
        int centerY = gp.getHeight() / 2;
        double angle = Math.atan2(mouseLocationY - centerY, mouseLocationX - centerX);
        angle = Math.toDegrees(angle);

        if (angle < 0) {
            angle += 360;
        }

        if (angle >= 45 && angle < 135) {
            direction = "down";
        } else if (angle >= 135 && angle < 225) {
            direction = "left";
        } else if (angle >= 225 && angle < 315) {
            direction = "up";
        } else {
            direction = "right";
        }

        double rotationRequired = Math.toRadians (angle);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;

        worldX = gp.player.worldX;
        worldY = gp.player.worldY;

        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        double deltaX = speed * Math.cos(Math.toRadians(angle));
        double deltaY = speed * Math.sin(Math.toRadians(angle));

        roundedDeltaX1 = (int) Math.round(deltaX);
        roundedDeltaY1 = (int) Math.round(deltaY);

        //adjust every 4th frame
        int shouldBeX = (int) Math.round(deltaX * 4);
        int shouldBeY = (int) Math.round(deltaY * 4);

        xDiff = shouldBeX - roundedDeltaX1 * 4;
        yDiff = shouldBeY - roundedDeltaY1 * 4;

        originEntityXAtCast = gp.player.screenX;
        originEntityYAtCast = gp.player.screenY;

        gp.playSE(36);
        gp.spells.add(this);
    }

    public void update(){
        collision(); // delete projectile if hits collision tile
        life--;

        adjustCounter++;

        if(adjustCounter == 4){
            adjustCounter = 0;
            worldX +=  xDiff;
            worldY +=  yDiff;
        }

        worldX += roundedDeltaX1;
        worldY += roundedDeltaY1;

        for (int i = 0; i < gp.allFightingEntities.size(); i++) {

            Entity currentEntity = gp.allFightingEntities.get(i);

            int distance = (int) Math.sqrt(Math.pow(worldX + 32 - currentEntity.worldMiddleX(), 2)
                    + Math.pow(worldY + 32 - currentEntity.worldMiddleY(), 2));

            if(isHostile(gp.player, currentEntity)) {
                if(distance < currentEntity.auraRadius() + auraRadiusSpell && currentEntity != gp.player){
                    currentEntity.loseLife(damage, damageType, gp.player, currentEntity, false, gp);
                    currentEntity.actionToDamageAI(gp.player);
                    ((Fighter)(currentEntity)).setFrozen(60);
                    life = 0;
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
        level = gp.player.allSpellList.allPlayerAvailableSpells[4].currentPointsOnSpell;
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getSpellDamage(4,level));
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(4, level);
    }

    public void collision(){
        gp.cChecker.checkTile(this);

        if(worldX < 0 || worldY < 0 || worldX > gp.currentMapMaxCol * GamePanel.tileSize -  1.1 * GamePanel.tileSize || worldY > gp.currentMapMaxRow * GamePanel.tileSize - 1.5 * GamePanel.tileSize){
            life = 0;
        }

        if(collisionOn){
            life = 0;
        }
    }
}
