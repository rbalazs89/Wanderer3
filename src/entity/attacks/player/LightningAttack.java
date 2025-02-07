package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class LightningAttack extends Spells {
    private BufferedImage lightningImage = null;
    private final int startX;
    private final int startY;
    private int endX, endY;
    private int secondX, secondY;
    private final Random random = new Random();
    private int updateCounter = 0;
    private boolean secondTargetFound = false;
    private int firstTargetIndex = -1;
    private int secondTargetIndex = -1;
    private Entity firstTargetEntity;
    private Entity secondTargetEntity;


    public LightningAttack(GamePanel gp) {
        super(gp);
        life = 100;
        maxLife = life;
        startX = gp.player.screenMiddleX();
        startY = gp.player.screenMiddleY();
        endX = gp.mouseH.mouseX;
        endY = gp.mouseH.mouseY;
        level =  gp.player.allSpellList.allPlayerAvailableSpells[8].currentPointsOnSpell;
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getSpellDamage(8, level));
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(8, level);
        damageType = 3;
        gp.spells.add(this);
        gp.playSE(38);
    }

    public void update() {
        if(life == maxLife) {
            findValidTargetFromClick(); // checks if valid target in click area and locks spell coordinates onto it.
            if (firstTargetIndex != -1) { // bounce onto second target
                if(findSecondaryTarget()){
                    secondTargetFound = true;
                }
            }
        }

        life --;
        updateCounter++;

        if (updateCounter >= 5) {
            generateLightningImage();
            updateCounter = 0;
        }

        if(firstTargetIndex != -1 && firstTargetEntity != null){
            endX = gp.allFightingEntities.get(firstTargetIndex).screenMiddleX();
            endY = gp.allFightingEntities.get(firstTargetIndex).screenMiddleY();
            endX = firstTargetEntity.screenMiddleX();
            endY = firstTargetEntity.screenMiddleY();
            if(secondTargetIndex != -1 && secondTargetEntity != null){
                secondX = secondTargetEntity.screenMiddleX();
                secondY = secondTargetEntity.screenMiddleY();
            }
        }

        if (life <= 0){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2) {
        if (lightningImage != null) {
            g2.drawImage(lightningImage, 0, 0, null);
        }
    }

    public void findValidTargetFromClick(){
        Rectangle clickRectangle = new Rectangle(gp.mouseH.mouseX - 64, gp.mouseH.mouseY - 64, 128, 128);
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            if(gp.allFightingEntities.get(i) != gp.player && !gp.allFightingEntities.get(i).isDying && gp.player.isHostile(gp.player, gp.allFightingEntities.get(i))){
                if(gp.allFightingEntities.get(i).screenSolidRectangle().intersects(clickRectangle)){
                    endX = gp.allFightingEntities.get(i).screenMiddleX();
                    endY = gp.allFightingEntities.get(i).screenMiddleY();
                    firstTargetIndex = i;
                    firstTargetEntity = gp.allFightingEntities.get(i);
                    gp.allFightingEntities.get(i).loseLife(damage, damageType, gp.player, gp.allFightingEntities.get(i), false, gp);
                    break;
                }
            }
        }
    }

    public boolean findSecondaryTarget(){
        Rectangle secondRectangle = new Rectangle(endX - 96 * 2, endY - 96 * 2, 192 * 2, 192 * 2);
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            if(gp.allFightingEntities.get(i) != gp.player && !gp.allFightingEntities.get(i).isDying && i != firstTargetIndex && isHostile(gp.player, gp.allFightingEntities.get(i))) {
                if(gp.allFightingEntities.get(i).screenSolidRectangle().intersects(secondRectangle)){
                    secondX = gp.allFightingEntities.get(i).screenMiddleX();
                    secondY = gp.allFightingEntities.get(i).screenMiddleY();
                    gp.allFightingEntities.get(i).loseLife(damage / 2, damageType, gp.player, gp.allFightingEntities.get(i), false, gp);
                    secondTargetIndex = i;
                    secondTargetEntity = gp.allFightingEntities.get(i);
                    return true;
                }
            }
        }
        return false;
    }

    private void generateLightningImage() {
        lightningImage = new BufferedImage(gp.getWidth(), gp.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = lightningImage.createGraphics();

        g2d.setColor(new Color(100, 100, 255, 100)); // Semi-transparent blue
        g2d.setStroke(new BasicStroke(4));
        drawLightning(g2d, startX, startY, endX, endY, 7, true);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        drawLightning(g2d, startX, startY, endX, endY, 7, false);

        if(secondTargetFound) {
            g2d.setColor(new Color(100, 100, 255, 100));
            g2d.setStroke(new BasicStroke(4));
            drawLightning(g2d, endX, endY, secondX, secondY, 7, true);

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            drawLightning(g2d, endX, endY, secondX, secondY, 7, false);
        }
    }

    private void drawLightning(Graphics2D g2d, int x1, int y1, int x2, int y2, int depth, boolean drawBlue) {
        if (depth == 0) {
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            return;
        }
        int midX = (x1 + x2) / 2;
        int midY = (y1 + y2) / 2;
        int offsetX = random.nextInt(10) - 5; // Smaller deviation for branches
        int offsetY = random.nextInt(10) - 5;
        midX += offsetX;
        midY += offsetY;
        if (drawBlue) {
            g2d.setColor(new Color(100, 100, 255, 100));
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));

        if (depth > 0) {
            drawLightning(g2d, x1, y1, midX, midY, depth - 1, drawBlue);
            drawLightning(g2d, midX, midY, x2, y2, depth - 1, drawBlue);
        }

        if (!drawBlue && random.nextInt(4) == 0) { // 25% chance to create a branch
            int branchEndX = midX + random.nextInt(10) - 5;
            int branchEndY = midY + random.nextInt(10) - 5;
            drawLightning(g2d, midX, midY, branchEndX, branchEndY, depth - 1, false);
        }
    }
}
