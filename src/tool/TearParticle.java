package tool;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class TearParticle {
    public int x, y;
    public double initialX, initialY;
    public int lifespan, age;
    public Entity originEntity;
    public GamePanel gp;
    public boolean leftSide;
    private double angle; // Current angle in the arc
    private final double radius; // Slightly randomized arc size

    public TearParticle(int x, int y, int lifespan, Entity originEntity, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.lifespan = lifespan;
        this.age = 0;
        this.originEntity = originEntity;
        this.gp = gp;
        this.leftSide = true;

        // Randomize parameters for variation
        // Random starting angle offset
        double startAngle = (Math.random() - 0.5) * Math.PI * 0.5; // -45° to +45° offset
        this.radius = 12 + Math.random() * 6; // Radius between 12-18 pixels
        this.angle = (leftSide ? Math.PI : 0) + startAngle; // Apply random offset
    }

    public void update() {
        age++;
        double progress = Math.min((double) age / lifespan, 1.0);
        double angularVelocity = Math.PI / lifespan; // Maintains half-circle motion

        // Update angle based on direction
        angle += leftSide ? -angularVelocity : angularVelocity;

        // Base circular motion with random start angle
        double baseX = radius * Math.cos(angle);
        double baseY = -radius * Math.sin(angle); // Negative for counter-clockwise

        // Add downward progression and slight horizontal drift
        x = (int) (initialX + baseX + (progress * 5 * (leftSide ? -1 : 1)));
        y = (int) (initialY + baseY + (progress * 25));
    }

    // Draw method remains unchanged
    public void draw(Graphics2D g2) {
        int screenX = originEntity.worldX - gp.player.worldX + gp.player.screenX;
        int screenY = originEntity.worldY - gp.player.worldY + gp.player.screenY;

        if (originEntity.worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                originEntity.worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                originEntity.worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                originEntity.worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY) {
            float alpha = 1.0f - (float) age / lifespan;
            alpha = Math.max(alpha, 0f);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2.setColor(new Color(255, 215, 0));
            g2.fillOval(screenX + x, screenY + y, 5, 5);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}