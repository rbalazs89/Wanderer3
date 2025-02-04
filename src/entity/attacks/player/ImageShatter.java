package entity.attacks.player;

import entity.Entity;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ImageShatter extends Spells {
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final int playerWorldAtStartX;
    private final int playerWorldAtStartY;
    private CopyOnWriteArrayList<Particle> particles;
    private final Random random = new Random();

    public ImageShatter(GamePanel gp, int startX, int startY, int endX, int endY) {
        super(gp);
        maxLife = 200;
        life = maxLife;
        damageType = 2;
        damage = (int)(gp.player.spellDmgModifier * gp.player.allSpellList.getSpellDamage(5, gp.player.allSpellList.allPlayerAvailableSpells[5].currentPointsOnSpell));
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.particles = new CopyOnWriteArrayList<>();
        generateParticles();
        gp.spells.add(this);
        playerWorldAtStartY = gp.player.worldY;
        playerWorldAtStartX = gp.player.worldX;
        auraRadiusSpell = 128;
        originEntity = gp.player;
        switch (gp.player.direction) {
            case "up":
                image = setup("/spell/imageshatter/imageshatterup");
                break;
            case "right":
                image = setup("/spell/imageshatter/imageshatterright");
                break;
            case "down":
                image = setup("/spell/imageshatter/imageshatterdown");
                break;
            case "left":
                image = setup("/spell/imageshatter/imageshatterleft");
                break;
        }
    }
    public void update() {
        life--;
        if (life == 100) {
            gp.playSE(25);
            generateParticles();

            for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                Entity tempEntity = gp.allFightingEntities.get(i);
                if(getEntityMidDistanceFromCoord(playerWorldAtStartX - gp.player.worldX + gp.player.screenX + 32,playerWorldAtStartY - gp.player.worldY + gp.player.screenY + 36, gp.allFightingEntities.get(i)) < auraRadiusSpell && gp.allFightingEntities.get(i) != originEntity){
                    if(isHostile(originEntity, tempEntity)){
                        tempEntity.loseLife(damage, damageType, gp.player, tempEntity, false, gp);
                        tempEntity.actionToDamageAI(originEntity);
                    }
                }
            }

        }
        if (life < 100) {
            for (Particle particle : particles) {
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
            for (Particle particle : particles) {
                particle.draw(g2);
                particle.drawX = particle.x - gp.player.worldX + playerWorldAtStartX;
                particle.drawY = particle.y - gp.player.worldY + playerWorldAtStartY;
            }
        }
        if(life > 100){
            g2.drawImage(image,startX - 16 - gp.player.worldX + playerWorldAtStartX, startY - 16 - gp.player.worldY + playerWorldAtStartY, null);
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



    private class Particle {
        private int drawX, drawY;
        private int x, y;
        private double velocityX, velocityY;
        private int lifespan;
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
            g2.setColor(new Color(0, 191, 255));
            g2.fillOval(drawX, drawY, 5, 5);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}