package entity.npc;

import entity.NPC;
import main.GamePanel;
import tool.ButterflyParticle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Butterflies extends NPC {
    //private boolean ascending = true; // flag to determine direction for sprites
    public final ButterflyParticle[] butterflyHelper;
    private final static int butterfliesInSwarm = 7;
    boolean followPlayer = false;

    private static final int goBackToSpawnMaxDistance = 8 * GamePanel.tileSize;
    int xDistance = worldX - gp.player.worldX;
    int yDistance = worldY - gp.player.worldY;
    private int isPlayerCloseCounter = 0;
    private int tooFarCounter = 0;
    private boolean soundPlaying = false;
    private int soundCounter = 0;
    private int healCounter = 0;

    public NPC_Butterflies(GamePanel gp){
        super(gp);
        direction = "down";
        defaultSpeed = 2;
        speed = defaultSpeed;
        solidArea.x = 12;
        solidArea.y = 10;
        solidArea.width = 40;
        solidArea.height = 54;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        collisionEntity = false;

        getImage();
        headDialogueString = "";
        walking = true;
        butterflyHelper = new ButterflyParticle[butterfliesInSwarm];
        for (int i = 0; i < butterfliesInSwarm; i++) {
            butterflyHelper[i] = new ButterflyParticle();
        }
    }

    public void getImage(){
        maxWalkSpriteArrayLength = 8; // same as the helper class
        walkImages = new BufferedImage[1][maxWalkSpriteArrayLength];
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //down
            walkImages[0][i] = setupSheet("/entity/butterfly/butterfly", i * 64, 0, 64, 64, GamePanel.tileSize/3, GamePanel.tileSize/3);
        }
    }

    public void update() {
        updateParticles(); //need for drawing
        checkIfTooFarFromSpawn(); // sets isGoingBackToSpawn

        if(!isGoingBackToSpawn){
            getXYDistances();
            decideIfPlayerFollowed();
            playHealingSound();
            healUp();

            if(!followPlayer){
                checkCollision();
                setDirectionFromRandomMovement();
            } else {
                setDirectionIfFollowPlayer();
                collisionOn = false;
            }
        } else {
            goBackToSpawn();
        }

        // since butterflies have no real collision need to check separately never to go outside map
        // this function shouldn't have real relevance, because if no collision checked they always follow player anyway
        gp.cChecker.checkMapEnd(this); // this function can move the entity

        moveIfNoCollision();
    }

    public void getXYDistances(){
        xDistance = Math.abs(worldX - gp.player.worldX);
        yDistance = Math.abs(worldY - gp.player.worldY);
    }

    public void decideIfPlayerFollowed(){
        isPlayerCloseCounter ++;
        if(isPlayerCloseCounter > 20){
            isPlayerCloseCounter = 0;
            if(xDistance < 4 * GamePanel.tileSize && yDistance < 4 * GamePanel.tileSize){
                followPlayer = true;
                setParticlesToFast();
            } else {
                followPlayer = false;
            }
        }
    }

    public void draw(Graphics2D g2){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            for (int i = 0; i < butterfliesInSwarm; i++) {
                g2.drawImage(walkImages[0][butterflyHelper[i].walkSpriteNum], screenX + butterflyHelper[i].x, screenY + butterflyHelper[i].y,null);
            }

            drawSpecial(g2);
            drawSpecial(g2,screenX, screenY);
        }
    }

    public void setDirectionIfFollowPlayer(){
        if(xDistance > yDistance){
            if(worldX > gp.player.worldX){
                direction = "left";
            } else {
                direction = "right";
            }
        } else {
            if(worldY > gp.player.worldY + 15){
                direction = "up";
            } else {
                direction = "down";
            }
        }
    }
    public void updateParticles(){
        for (int i = 0; i < butterfliesInSwarm; i++) {
            butterflyHelper[i].update();
        }
    }

    public void checkIfTooFarFromSpawn(){
        tooFarCounter ++;
        if(tooFarCounter > 20){
            if (Math.abs(worldX - spawnX) > goBackToSpawnMaxDistance || Math.abs(worldY - spawnY) > goBackToSpawnMaxDistance) {
                isGoingBackToSpawn = true;
                followPlayer = false;
                speed = defaultSpeed;
                goalCol = spawnX / GamePanel.tileSize;
                goalRow = spawnY / GamePanel.tileSize;
                setParticlesToSlow();
            }
        }
    }

    public void goBackToSpawn(){
        searchPath(goalCol, goalRow, true);
        collisionOn = false;
    }

    public void setParticlesToFast(){
        for (ButterflyParticle butterflyParticle : butterflyHelper) {
            butterflyParticle.fastMode = true;
        }
    }

    public void setParticlesToSlow(){
        for (ButterflyParticle butterflyParticle : butterflyHelper) {
            butterflyParticle.fastMode = false;
        }
    }



    public void playHealingSound(){
        if(soundPlaying){
            soundCounter ++;
            if(soundCounter >= 90){
                soundCounter = 0;
                soundPlaying = false;
            }
        }
        else {
            if(xDistance < 20 && yDistance < 20){
                gp.playSE(83);
                soundPlaying = true;
            }
        }
    }

    public void healUp(){
        if(xDistance < 20 && yDistance < 20){
            healCounter ++;
            if(healCounter >= 5){
                gp.player.winLife(1);
                healCounter = 0;
            }
        }
    }

    public void executeIfPathReached(){

    }
}
