package tool;

import main.GamePanel;

import java.util.Random;

public class ButterflyParticle {

    public int x;
    public int y;
    private float vx, vy; // Velocity components

    private final Random rand = new Random();
    //public boolean flyCloseToEachOther;
    //public BufferedImage[] images;
    private int spriteCounter = 0;
    private static final int spriteChangeFrameValue = 10;
    public int walkSpriteNum;
    private boolean ascending = false;
    private static final int maxWalkSpriteArrayLength = 8;
    public boolean fastMode = false;
    float maxSpeed = 4.0f; // Increased max speed

    public ButterflyParticle() {
        x = rand.nextInt(GamePanel.tileSize);
        y = rand.nextInt(GamePanel.tileSize);
        vx = (rand.nextFloat() - 0.5f) * 2;
        vy = (rand.nextFloat() - 0.5f) * 2;
        walkSpriteNum = rand.nextInt(maxWalkSpriteArrayLength);

    }

    public void update() {
        setWalkingSpriteNumber();

        float minX, maxX, minY, maxY; // Define bounds

        if (!fastMode) {
            minX = - GamePanel.tileSize * 0.2f;
            maxX = GamePanel.tileSize * 1.2f;
            minY = - GamePanel.tileSize * 0.2f;
            maxY = GamePanel.tileSize * 1.2f;

            // Normal movement
            vx += (rand.nextFloat() - 0.5f) * 0.2f;
            vy += (rand.nextFloat() - 0.5f) * 0.2f;
            maxSpeed = 2.0f;

        } else {

            minX = 0.25f * GamePanel.tileSize;
            maxX = 0.75f * GamePanel.tileSize;
            minY = 0.25f * GamePanel.tileSize;
            maxY = 0.75f * GamePanel.tileSize;

            vx += (rand.nextFloat() - 0.5f) * 0.3f; // Faster acceleration
            vy += (rand.nextFloat() - 0.5f) * 0.3f;
            maxSpeed = 4.0f;
        }

        // Limit the speed to prevent erratic movements
        float speed = (float) Math.sqrt(vx * vx + vy * vy);
        if (speed > maxSpeed) {
            vx = (vx / speed) * maxSpeed;
            vy = (vy / speed) * maxSpeed;
        }

        // Apply velocity
        x += vx;
        y += vy;

        // Keep the butterfly inside the defined rectangle
        if (x < minX || x > maxX) vx *= -1;
        if (y < minY || y > maxY) vy *= -1;

        x = (int) Math.max(minX, Math.min(maxX, x));
        y = (int) Math.max(minY, Math.min(maxY, y));
    }

    public void setWalkingSpriteNumber() {
        spriteCounter++;
        if (spriteCounter > spriteChangeFrameValue) {
            if (ascending) {
                walkSpriteNum++;
                if (walkSpriteNum >= maxWalkSpriteArrayLength - 1) {
                    ascending = false;
                }
            } else {
                if (walkSpriteNum > 0) { // Prevent it from going negative
                    walkSpriteNum--;
                } else {
                    ascending = true;
                }
            }
        }
    }
}
