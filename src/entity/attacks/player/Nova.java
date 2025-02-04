package entity.attacks.player;
import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class Nova extends Spells {
    private final int x;
    private final int y;
    private final int maxRadius;
    private int currentRadius;
    private final int expansionSpeed;
    private boolean expanding;
    private final int thickness;
    private final Random random;

    public Nova(GamePanel gp) {
        super(gp);
        this.currentRadius = 0;
        this.expanding = true;
        damageType = 3;
        level =  gp.player.allSpellList.allPlayerAvailableSpells[9].currentPointsOnSpell;
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getSpellDamage(9, level));
        random = new Random();
        originEntity = gp.player;
        maxLife = 300;
        life = maxLife;
        this.expansionSpeed = 2;
        this.thickness = 2;
        this.maxRadius = 300;
        this.x = gp.player.screenMiddleX();
        this.y = gp.player.screenMiddleY();
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(9, level);
        gp.spells.add(this);
    }

    public void update() {
        if (expanding) {
            currentRadius += expansionSpeed;
            if (currentRadius >= maxRadius) {
                expanding = false;
                life = 0;
            }
        }
        if(life == maxLife){
            gp.playSE(28);
        }
        life --;
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity tempEntity = gp.allFightingEntities.get(i);
            if(getEntityMidDistanceFromCoord(x,y, tempEntity) < currentRadius && !hitEnemies.contains(tempEntity) && tempEntity != originEntity){
                if(isHostile(originEntity, tempEntity)) {
                    hitEnemies.add(tempEntity);
                    tempEntity.loseLife(damage, damageType, gp.player, tempEntity, false, gp);
                    tempEntity.actionToDamageAI(originEntity);
                    gp.playSE(29);
                }
            }
        }
        if(life <= 0){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2) {
        if (expanding) {
            g2.setStroke(new BasicStroke(thickness));
            for (int i = 0; i < 5; i++) {
                int alpha2 = Math.max(0, 255 - (int) (255.0 * (currentRadius + i) / maxRadius));
                drawJaggedArc(g2, x, y, currentRadius + i, thickness, alpha2);
            }
        }
    }

    private void drawJaggedArc(Graphics2D g2, int centerX, int centerY, int radius, int thickness, int alpha) {
        Path2D path = new Path2D.Double();
        int points = 100; // Reduced points for performance
        double angleStep = 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            double angle = i * angleStep;
            double randomRadius = radius + random.nextInt(thickness * 2) - thickness;
            double x = centerX + randomRadius * Math.cos(angle);
            double y = centerY + randomRadius * Math.sin(angle);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();

        Color startColor = new Color(128, 220, 255, alpha);
        Color endColor = new Color(255, 255, 255, alpha);

        g2.setPaint(new GradientPaint(centerX, centerY, startColor, centerX + radius, centerY + radius, endColor, true));
        g2.draw(path);
    }
}
