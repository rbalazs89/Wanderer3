package entity.attacks.player;

import entity.Entity;
import entity.attacks.player.Spells;
import main.GamePanel;
import org.w3c.dom.ls.LSOutput;
import tool.DamageNumber;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FrozenOrb extends Spells {

    private final int  endWorldX, endWorldY, startWorldX, startWorldY;
    private static double speed; // for this class use different speed
    private float progress; // to track the animation progress
    private float rotationAngle; // to track the rotation angle
    private int counter = 0;
    public FrozenOrb(GamePanel gp) {
        super(gp);
        setValues();
        speed = 0.5; // Example speed, can be adjusted
        this.progress = 0f; // Start progress at 0
        this.rotationAngle = 0f; // Start rotation angle at 0
        life = 0; // only used to reset hitentities array, not to remove spell
        auraRadiusSpell = 20;
        damageType = 2; // cold element

        //this class only used by player
        //this class uses progression instead of life value for update access
        int endX = gp.mouseH.mouseX;
        int endY = gp.mouseH.mouseY;

        startWorldX = gp.player.worldMiddleX();
        startWorldY = gp.player.worldMiddleY();

        endWorldX = endX + gp.player.worldX - gp.player.screenX;
        endWorldY = endY + gp.player.worldY - gp.player.screenY;

        gp.playSE(37);
        gp.spells.add(this);
    }

    public void update() {
        life ++;
        //add possibility to hit same enemies

        if(life > 40){
            life = 0;
            hitEnemies.clear();
        }

        //shoot icicles from itself:
        counter ++;
        if(counter > 5){
            new IceBolt_FB(gp,this);
            counter = 0;
        }

        // Update the progress based on speed
        if (progress < 1.0f) {
            progress += speed / 100.0f; // Adjust denominator to control speed

            worldX = (int)(startWorldX + progress * (endWorldX - startWorldX));
            worldY = (int)(startWorldY + progress * (endWorldY - startWorldY));

        } else {
            progress = 1.0f; // Ensure it stops at the end
        }

        rotationAngle += 5; // Adjust the increment to control rotation speed
        if (rotationAngle >= 360) {
            rotationAngle -= 360; // Keep the angle within 0-360 degrees
        }

        //hit detection:
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {

            Entity currentEntity = gp.allFightingEntities.get(i);

            int distance = (int) Math.sqrt(Math.pow(worldX - currentEntity.worldMiddleX(), 2)
                    + Math.pow(worldY - currentEntity.worldMiddleY(), 2));

            if(isHostile(gp.player, currentEntity)) {
                if(distance < currentEntity.auraRadius() + auraRadiusSpell && !hitEnemies.contains(currentEntity) && currentEntity != gp.player){
                    hitEnemies.add(currentEntity);
                    currentEntity.loseLife(damage, damageType, gp.player, currentEntity, false, gp);
                    currentEntity.actionToDamageAI(gp.player);
                }
            }
        }

        if(progress >= 1){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2) {

        // Calculate current position based on progress
        // means screenX and screenY
        int currentX = worldX + gp.player.screenX - gp.player.worldX;
        int currentY = worldY + gp.player.screenY - gp.player.worldY;

        drawIceOrb(g2, currentX, currentY);

        if(gp.visibleHitBox){
            g2.setColor(transparentRed);
            g2.fillOval(currentX - auraRadiusSpell, currentY - auraRadiusSpell, 2 * auraRadiusSpell, 2 * auraRadiusSpell);
        }
    }

    private void drawIceOrb(Graphics2D g2, int x, int y) {
        int radius = 20; // Example radius
        BufferedImage orbImage = createOrbImage(radius);

        AffineTransform old = g2.getTransform();
        g2.translate(x - radius, y - radius); // Center the orb at (x, y)
        g2.drawImage(orbImage, 0, 0, null);
        g2.setTransform(old);
    }

    private BufferedImage createOrbImage(int radius) {
        int diameter = radius * 2;
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // Create a gradient for the orb
        RadialGradientPaint gradient = new RadialGradientPaint(
                new Point(radius, radius), radius,
                new float[]{0.0f, 1.0f},
                new Color[]{new Color(173, 216, 230, 180), new Color(0, 0, 139, 180)}
        );
        g2.setPaint(gradient);
        g2.fillOval(0, 0, diameter, diameter);

        // Apply rotation for the crystals
        AffineTransform old = g2.getTransform();
        g2.rotate(Math.toRadians(rotationAngle), radius, radius);

        // Add ice crystals pointing towards the center
        g2.setColor(new Color(255, 255, 255, 150));
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45); // 8 crystals at 45-degree intervals
            int crystalLength = radius / 2;
            int crystalX = (int) (radius + Math.cos(angle) * (radius - crystalLength));
            int crystalY = (int) (radius + Math.sin(angle) * (radius - crystalLength));

            AffineTransform crystalTransform = g2.getTransform();
            g2.translate(crystalX, crystalY);
            g2.rotate(angle + Math.PI / 2); // Rotate to point towards the center

            int crystalWidth = crystalLength / 4;
            g2.fillPolygon(new int[]{0, crystalWidth / 2, -crystalWidth / 2},
                    new int[]{-crystalLength, crystalLength / 4, crystalLength / 4}, 3);

            g2.setTransform(crystalTransform);
        }

        // Swirling lines
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < 360; i += 45) {
            g2.drawArc(radius / 2, radius / 2, radius, radius, i, 20);
        }

        g2.setTransform(old);
        g2.dispose();
        return image;
    }

    public void setValues(){
        level = gp.player.allSpellList.allPlayerAvailableSpells[6].currentPointsOnSpell;
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getIceOrbData(level)[0]);
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(6, level);
    }
}