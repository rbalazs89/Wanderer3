package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;


@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class IceBolt_FB extends Spells {
    //this class is only used by FrozenOrb spell entity
    private final int endWorldX;
    private final int endWorldY;
    private final int startWorldX;
    private final int startWorldY;
    private final int speed;
    private float progress; // to track the animation progress
    private final double angleInRadians;
    private int currentX, currentY, middleX, middleY;

    public IceBolt_FB(GamePanel gp, Entity originEntity) {
        super(gp);

        this.originEntity = originEntity;
        startWorldX = originEntity.worldX;
        startWorldY = originEntity.worldY;
        damageType = 2;

        if (originEntity instanceof Spells) {
            this.damage = ((Spells) originEntity).damage / 5;
        } else {
            // Handle the case where originEntity is not a Spells instance
            this.damage = 5; // Default damage or some other logic
        }

        //Random random = new Random();
        // to track the angle of the bolt
        float angle = random.nextInt(360);
        int distance = 700;
        angleInRadians = Math.toRadians(angle);
        endWorldX = (int) (startWorldX + distance * Math.cos(angleInRadians));
        endWorldY = (int) (startWorldY + distance * Math.sin(angleInRadians));

        this.speed = 1; // Example speed, can be adjusted
        this.progress = 0f; // Start progress at 0

        auraRadiusSpell = 10;

        gp.spells.add(this);
    }

    public void update() {

        if (progress < 1.0f) {
            worldX = (int)(startWorldX + progress * (endWorldX - startWorldX));
            worldY = (int)(startWorldY + progress * (endWorldY - startWorldY));

            currentX = worldX + gp.player.screenX - gp.player.worldX;
            currentY = worldY + gp.player.screenY - gp.player.worldY;

            int boltLength = 30;
            middleX = (int) (currentX + (boltLength / 2) * Math.cos(angleInRadians));
            middleY = (int) (currentY + (boltLength / 2) * Math.sin(angleInRadians));

            progress += speed / 100.0f; // Adjust denominator to control speed
        } else {
            progress = 1.0f; // Ensure it stops at the end
        }

        for (int i = 0; i < gp.allFightingEntities.size(); i++) {

            Entity currentEntity = gp.allFightingEntities.get(i);

            int distance = (int) Math.sqrt(Math.pow(middleX - currentEntity.screenMiddleX(), 2)
                    + Math.pow(middleY - currentEntity.screenMiddleY(), 2));

            if(isHostile(gp.player, currentEntity)) {
                if(distance < currentEntity.auraRadius() + auraRadiusSpell && currentEntity != gp.player && !hitEnemies.contains(currentEntity)){
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
        drawIceBolt(g2, currentX, currentY);
        if(gp.visibleHitBox){
            g2.setColor(transparentRed);
            g2.fillOval(middleX - auraRadiusSpell, middleY - auraRadiusSpell, 2 * auraRadiusSpell, 2 * auraRadiusSpell);
        }
    }

    private void drawIceBolt(Graphics2D g2, int x, int y) {

        int boltLength = 30; // Example length of the ice bolt
        int boltWidth = 10; // Example width of the ice bolt

        Path2D iceBolt = new Path2D.Double();
        iceBolt.moveTo(0, -boltWidth / 2.0);
        iceBolt.lineTo(boltLength, 0);
        iceBolt.lineTo(0, boltWidth / 2.0);
        iceBolt.closePath();

        AffineTransform old = g2.getTransform();
        g2.translate(x, y);
        g2.rotate(angleInRadians);


        int alpha = 255;
        int blue = 139;
        GradientPaint gradient = new GradientPaint(
                0, -boltWidth / 2,
                new Color(173, 216, 230),
                boltLength, boltWidth / 2,
                new Color(0, 0, blue, alpha)
        );
        g2.setPaint(gradient);
        g2.fill(iceBolt);

        // Outline
        g2.setColor(new Color(173, 216, 230, 180));
        g2.setStroke(new BasicStroke(2));
        g2.draw(iceBolt);
        g2.setTransform(old);
    }
}