package entity.attacks.player;

import entity.Entity;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

public class FireWall extends Spells {
    public BufferedImage[] images = new BufferedImage[8];
    private int fireWallCounter = 0;
    private int imageNumber = 0;
    //private final int originEntityXAtCast;
    //private final int originEntityYAtCast;
    private final int x10,x20,x30,x40,y10,y20,y30,y40;
    private int x1,x2,x3,x4,y1,y2,y3,y4;
    private int counter = 0;
    private final Color transparentRed = new Color(255, 0, 0, 128);

    public FireWall(GamePanel gp) {
        super(gp);

        gp.playSE(17);
        getLvlDependent();
        damageType = 1; // fire element

        mouseLocationX = gp.mouseH.mouseX;
        mouseLocationY = gp.mouseH.mouseY;

        worldX = mouseLocationX - gp.player.screenX + gp.player.worldX;
        worldY = mouseLocationY - gp.player.screenY + gp.player.worldY;

        originEntityXAtCast = gp.player.worldX;
        originEntityYAtCast = gp.player.worldY;

        BufferedImage tempImage = setupImage("/spell/firewall/flameSheet");

        for (int i = 0; i < 8; i++) {
            images[i] = setupSheet(tempImage, i * 64, 0, 64,64);
        }

        life = 7 * 60;

        maxLife = life;
        gp.spells.add(this);

        int heroCenterX = gp.screenWidth/2;
        int heroCenterY = gp.screenHeight/2;

        int rectLength = 64;
        int rectWidth = 300;
        int halfLength = rectLength / 2;
        int halfWidth = rectWidth / 2;

        double angle = Math.atan2(mouseLocationY - heroCenterY, mouseLocationX - heroCenterX);
        double perpendicularAngle = angle - Math.PI / 2;

        double dx1 = Math.cos(perpendicularAngle) * halfWidth;
        double dy1 = Math.sin(perpendicularAngle) * halfWidth;
        double dx2 = Math.cos(angle) * halfLength;
        double dy2 = Math.sin(angle) * halfLength;

        x10 = (int)(mouseLocationX - dx1 - dx2);
        y10 = (int)(mouseLocationY - dy1 - dy2);
        x20 = (int)(mouseLocationX + dx1 - dx2);
        y20 = (int)(mouseLocationY + dy1 - dy2);
        x30 = (int)(mouseLocationX + dx1 + dx2);
        y30 = (int)(mouseLocationY + dy1 + dy2);
        x40 = (int)(mouseLocationX - dx1 + dx2);
        y40 = (int)(mouseLocationY - dy1 + dy2);

        //TODO sound effect
    }

    public void update() {
        life --;

        fireWallCounter ++;
        if(fireWallCounter > 5){
            imageNumber = (imageNumber + 1) % 8;
            fireWallCounter = 0;
        }

        x1 = x10 + originEntityXAtCast - gp.player.worldX;
        x2 = x20 + originEntityXAtCast - gp.player.worldX;
        x3 = x30 + originEntityXAtCast - gp.player.worldX;
        x4 = x40 + originEntityXAtCast - gp.player.worldX;

        y1 = y10 + originEntityYAtCast - gp.player.worldY;
        y2 = y20 + originEntityYAtCast - gp.player.worldY;
        y3 = y30 + originEntityYAtCast - gp.player.worldY;
        y4 = y40 + originEntityYAtCast - gp.player.worldY;

        counter ++;
        if(counter >= 30 || life == maxLife - 1){
            counter = 0;

            GeneralPath path = new GeneralPath();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.lineTo(x4, y4);
            path.closePath();

            Area spellArea = new Area(path);

            for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                Entity currentEntity = gp.allFightingEntities.get(i);
                if (isHostile(currentEntity, gp.player)) {
                    Rectangle entityHitbox = currentEntity.screenSolidRectangle();
                    Area entityArea = new Area(entityHitbox);
                    entityArea.intersect(spellArea);

                    if (!entityArea.isEmpty()) {
                        currentEntity.loseLife(damage, damageType, gp.player, currentEntity, false, gp);
                    }
                }
            }
        }

        if(life <= 0){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2) {

        if(gp.visibleHitBox){
            GeneralPath path = new GeneralPath();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.lineTo(x4, y4);
            path.closePath();

            g2.setColor(transparentRed);
            g2.fill(path);
        }

        int midX1 = (x2 + x3) / 2;
        int midY1 = (y2 + y3) / 2;
        int midX2 = (x1 + x4) / 2;
        int midY2 = (y1 + y4) / 2;

        image = images[imageNumber];


        for (int i = 0; i < 6; i++) {
            double t = (i + 0.5) / 6.0; // Shift by half t to start earlier
            int px = (int) ((1 - t) * midX1 + t * midX2);
            int py = (int) ((1 - t) * midY1 + t * midY2);

            g2.setColor(Color.BLACK);
            g2.drawString("X", px, py);
            g2.drawImage(image,px - 32, py - 32, null);
        }
    }

    public void getLvlDependent(){
        int level = gp.player.allSpellList.allPlayerAvailableSpells[1].currentPointsOnSpell;
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(1,level);
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getSpellDamage(1, level));
    }
}