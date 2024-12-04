package tool;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class DamageNumber {
    GamePanel gp;
    int maxLife = 100;
    int life = maxLife;
    Color color;
    int worldX;
    int worldY;
    int screenX;
    int screenY;
    Random random = new Random();
    int value;
    private Font baseFont = new Font("SansSerif", Font.BOLD, 20);

    public DamageNumber(int value, Entity originEntity, Entity attackedEntity, boolean isContactDamage, GamePanel gp){
        this.gp = gp;
        this.value = value;
        // contact damage: (only done to the player)
        if(isContactDamage){
            if(gp.visibleDamageNumbersDoneToYou) {
                gp.damageNumbers.add(this);
            } else {
                return;
            }
            color = new Color(65,10,10);
        }

        // damage done to you:
        else if(originEntity.entityType == 0 || originEntity.entityType == 5){
            if(gp.visibleDamageNumbersDoneByYou) {
                gp.damageNumbers.add(this);
            } else {
                return;
            }
            color = new Color(255,255,255);
        }

        // damage done by you:
        else {
            if(gp.visibleDamageNumbersDoneByYou) {
                gp.damageNumbers.add(this);
            } else {
                return;
            }
            color = new Color(255,10,10);
        }
        worldX = attackedEntity.worldMiddleX() + random.nextInt(41) - 20;
        worldY = attackedEntity.worldMiddleY() + random.nextInt(41) - 20;;
    }

    public void draw(Graphics2D g2){
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Calculate progress as a percentage of maxLife
        float progress = 1.0f - ((float) life / maxLife);

        // Scale the size: Grow initially (1.0 to 1.5), then shrink (1.5 to 0.8)
        float scale;
        if (progress < 0.5f) {
            scale = 1.0f + 1.0f * progress; // Grow from 1.0 to 1.5
        } else {
            scale = 1.5f - 0.7f * (progress - 0.5f) * 2; // Shrink from 1.5 to 0.8
        }

        // Fade out: Reduce alpha from 255 to 0
        int alpha = Math.max(0, (int) (255 * (1.0f - progress)));

        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

        // Apply scaling
        Font scaledFont = baseFont.deriveFont(baseFont.getSize2D() * scale);
        g2.setFont(scaledFont);

        // Draw the damage number
        String s = String.valueOf(value);
        g2.drawString(s, screenX, screenY);
    }

    public void update() {
        life--;

        if (life <= 0) {
            gp.damageNumbers.remove(this);
        }
    }
}
