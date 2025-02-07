package entity.npc;

import entity.NPC;
import main.GamePanel;
import tool.TearParticle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class NPC_ArrowChild extends NPC {
    private boolean fallingPhase = true;
    private int fallingCounter = 0;
    private static final int fallDistanceToRight = 3 * GamePanel.tileSize;
    private static final int fallingDistanceToDown = 5 * GamePanel.tileSize;
    private static final int stopFallingAtThisFrame = 100;
    public int startX;
    public int startY;
    public int addXFromFall = 0;
    public int addYFromFall = 0;
    public int cryingCounter = 0;
    public CopyOnWriteArrayList<TearParticle> particles = new CopyOnWriteArrayList<>();
    public boolean onSpawn = true;

    private boolean isSoundPlaying = false;
    private int soundCounter = 0;

    public NPC_ArrowChild(GamePanel gp){
        super(gp);
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(GamePanel.tileSize / 16,
                                  GamePanel.tileSize / 16,
                                  GamePanel.tileSize * 14 / 16,
                                  GamePanel.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collisionEntity = false;

        spriteChangeFrameValue = 20;
        headDialogueString = "";
        startX = worldX;
        startY = worldY;
        getImage();
        gp.playSE(84);
    }

    public void getImage(){
        maxWalkSpriteArrayLength = 9;
        walkDown = new BufferedImage[maxWalkSpriteArrayLength]; // use as falling
        walkUp = new BufferedImage[maxWalkSpriteArrayLength]; // use as 90 deg transformed hurt image for crying

        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            walkDown[i] = setupSheet("/entity/npckid/falling", i * 128,0, 128, 128, 64, 64);
            walkUp[i] = setupSheet("/entity/npckid/crying", i * 128,0, 128, 128, 64, 64);
        }
        image = walkDown[0];
    }



    public void update() {
        if (fallingPhase) {
            fallingCounter++;

            float progress = (float) fallingCounter / stopFallingAtThisFrame;
            progress = Math.min(progress, 1.0f); // Ensure it doesn't exceed 1

            // **X movement: curve (cubic)**
            float xProgress = 1 - (float) Math.pow(1 - progress, 3); // Starts fast, slows down
            addXFromFall = startX + (int) (fallDistanceToRight * xProgress);

            // **Y movement: quadratic gravity effect**
            float yProgress = progress * progress; // Slow at first, then faster
            addYFromFall = startY + (int) (fallingDistanceToDown * yProgress);

            // stop falling at the last frame
            if (fallingCounter >= stopFallingAtThisFrame) {
                fallingPhase = false;
                worldX = spawnX + fallDistanceToRight;
                worldY = spawnY + fallingDistanceToDown;
            }

            getFallingSprite();
        } else {
            getCryingSprite();
            createTearParticle();
            updateParticles();

            createSound();
        }
    }

    public void draw(Graphics2D g2){
        int screenX;
        int screenY;
        if(fallingPhase){
            screenX = worldX + addXFromFall - gp.player.worldX + gp.player.screenX;
            screenY = worldY + addYFromFall - gp.player.worldY + gp.player.screenY;
        } else {
            screenX = worldX - gp.player.worldX + gp.player.screenX;
            screenY = worldY - gp.player.worldY + gp.player.screenY;
        }
        if(worldX + 5 * GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - 5 * GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + 5 * GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - 5 * GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            g2.drawImage(image, screenX, screenY,null);
            for (TearParticle particle : particles) {
                particle.draw(g2);
            }
        }
    }

    public void getFallingSprite(){
        int currentSprite = (int) ((fallingCounter / (float) stopFallingAtThisFrame) * maxWalkSpriteArrayLength);

        currentSprite = Math.min(currentSprite, maxWalkSpriteArrayLength - 1);

        image = walkDown[currentSprite];
    }

    public void getCryingSprite(){
        if(onSpawn){
            cryingCounter ++;
            int currentSprite = (int) ((cryingCounter / (float) 30) * maxWalkSpriteArrayLength);
            currentSprite = Math.min(currentSprite, maxWalkSpriteArrayLength - 1);
            image = walkUp[currentSprite];
            if(cryingCounter >=  120 + random.nextInt(500)){
                cryingCounter = 0;
                onSpawn = false;
            }
        } else {
            cryingCounter ++;
            int currentSprite = (int) ((cryingCounter / (float) 70) * maxWalkSpriteArrayLength);
            currentSprite = Math.min(currentSprite, maxWalkSpriteArrayLength - 1);
            image = walkUp[currentSprite];
            if(cryingCounter >=  120 + random.nextInt(500)){
                cryingCounter = 0;
            }
        }
    }

    public void createTearParticle(){
        if (random.nextInt(21) == 20){
            particles.add(new TearParticle(45,25, 100, this, gp));
        }
    }

    public void createSound(){
        if(isSoundPlaying){
            soundCounter ++;
            if(soundCounter >= 300){
                isSoundPlaying = false;
                soundCounter = 0;
            }
        }

        if(!isSoundPlaying){
            if(random.nextInt(100) + 1 == 100) {
                if (Math.abs(worldX - gp.player.worldX) < GamePanel.tileSize * 4 && Math.abs(worldY - gp.player.worldY) < GamePanel.tileSize * 4){
                    gp.playSE(85 + random.nextInt(3));
                    isSoundPlaying = true;
                }
            }
        }
    }

    public void updateParticles(){
        for (TearParticle particle : particles) {
            particle.update();
        }
        for (int i = 0; i < particles.size(); i++) {
            if(particles.get(i).age > particles.get(i).lifespan){
                particles.remove(particles.get(i));
            }
        }
    }
}