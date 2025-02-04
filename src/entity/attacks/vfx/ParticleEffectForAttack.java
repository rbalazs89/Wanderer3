package entity.attacks.vfx;

import entity.attacks.player.Spells;
import main.GamePanel;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleEffectForAttack extends Spells {
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final int playerWorldAtStartX;
    private final int playerWorldAtStartY;
    private CopyOnWriteArrayList<ParticleEffectForAttack.Particle> particles;
    private final Random random = new Random();

    public ParticleEffectForAttack(GamePanel gp, int startX, int startY, int endX, int endY) {
        super(gp);
        maxLife = 101;
        life = maxLife;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.particles = new CopyOnWriteArrayList<>();
        gp.spells.add(this);
        playerWorldAtStartY = gp.player.worldY;
        playerWorldAtStartX = gp.player.worldX;
    }

    public void update() {
        life--;
        if (life == 100) {
            generateParticles();
        }

        if (life < 100) {
            for (ParticleEffectForAttack.Particle particle : particles) {
                particle.update();
                if (particle.isDead()) {
                    particles.remove(particle);
                }
            }
            if (particles.isEmpty()) {
                gp.spells.remove(this);
            }
        }
    }

    public void draw(Graphics2D g2) {
        if(life < 100) {
            for (ParticleEffectForAttack.Particle particle : particles) {
                particle.draw(g2);
                particle.drawX = particle.x - gp.player.worldX + playerWorldAtStartX;
                particle.drawY = particle.y - gp.player.worldY + playerWorldAtStartY;
            }
        }
    }

    private void generateParticles() {
        int particleCount = 80; // Adjust particle count as needed
        for (int i = 0; i < particleCount; i++) {
            int x = startX + random.nextInt((endX - startX)/4);
            int y = startY + random.nextInt((endY - startY)/4);
            double velocityX = (random.nextDouble() - 0.5) * 3; // Random velocity between -1.5 and 1.5
            double velocityY = (random.nextDouble() - 0.5) * 3;
            if (random.nextDouble() < 0.7) { // 70% of particles go upwards
                velocityY -= random.nextDouble() * 3; // Random velocity between -3 and 0
            } else { // 30% of particles go downwards
                velocityY += random.nextDouble() * 3; // Random velocity between 0 and 3
            }
            int lifespan = random.nextInt(60) + 30; // Lifespan between 30 and 90 frames
            particles.add(new Particle(x, y, velocityX, velocityY, lifespan));
        }
    }

    private static class Particle {
        private int drawX, drawY;
        private int x, y;
        private final double velocityX;
        private final double velocityY;
        private final int lifespan;
        private int age;

        public Particle(int x, int y, double velocityX, double velocityY, int lifespan) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.lifespan = lifespan;
            this.age = 0;
        }

        public void update() {
            x += velocityX;
            y += velocityY;
            age++;
        }

        public boolean isDead() {
            return age >= lifespan;
        }

        public void draw(Graphics2D g2) {
            float alpha = 1.0f - (float) age / lifespan; // Fade out effect
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(new Color(112, 128, 144));
            g2.fillOval(drawX, drawY, 5, 5);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
