package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Pumpkin extends Entity {
    int speedCounter = 0;

    public NPC_Pumpkin(GamePanel gp){
        super(gp);
        direction = "down";
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(1 * GamePanel.tileSize / 16,
                GamePanel.tileSize * 1/ 16,
                GamePanel.tileSize * 14 / 16,
                GamePanel.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        collisionEntity = false;
        headDialogueString = "You can refill your bottle by harvesting the delicious pumpkin.";

    }

    public void getImage(){

        walkUp = new BufferedImage[8];
        walkRight = new BufferedImage[8];
        walkDown = new BufferedImage[8];
        walkLeft = new BufferedImage[8];

        movementSpriteChangeFrame = 5;
        movementSpritesNumber = 8;

        for (int i = 0; i < movementSpritesNumber; i++) {
            walkDown[i] = setupSheet("/entity/npcjuice/pumpkinnpcsheet",i * 77,0,77,77,77,77);
        }
        for (int i = 0; i < movementSpritesNumber; i++) {
            walkLeft[i] = setupSheet("/entity/npcjuice/pumpkinnpcsheet",i * 77,77,77,77,77,77);
        }
        for (int i = 0; i < movementSpritesNumber; i++) {
            walkRight[i] = setupSheet("/entity/npcjuice/pumpkinnpcsheet",i * 77,154,77,77,77,77);
        }
        for (int i = 0; i < movementSpritesNumber; i++) {
            walkUp[i] = setupSheet("/entity/npcjuice/pumpkinnpcsheet",i * 77,231,77,77,77,77);
        }
    }

    public void setActionAI(){

        controlSpeed();
        setActionWhenNear();

        setBoundAreaNPC(20,19,39,43);

        if(targetPathFollowed){
            int goalCol = 33;
            int goalRow = 33;
            searchPath(goalCol, goalRow, true);
        }
        else{
            setDirectionFromRandomMovement();
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            getWalkImage();
            drawGetWalkSpriteNumber();
            if(actionWhenNear1){
                nearHeadDialogue(g2);
            }

            g2.drawImage(image, screenX, screenY,null);

        }
    }

    public void drawGetWalkSpriteNumber(){
        if (gp.gameState == gp.playState && !stunned && !frozen) {
            spriteCounter++;
            if (spriteCounter > movementSpriteChangeFrame) {
                spriteNum = (spriteNum + 1) % movementSpritesNumber;
                spriteCounter = 0;
            }
        }
    }

    public void getWalkImage() {
        switch (direction) {
            case "left":
                image = walkLeft[spriteNum];
                break;
            case "right":
                image = walkRight[spriteNum];
                break;
            case "up":
                image = walkUp[spriteNum];
                break;
            case "down":
                image = walkDown[spriteNum];
                break;
        }
    }

    public void controlSpeed(){
        speedCounter++;
        if(speedCounter == 1){
            speed = 0;
        } else if (speedCounter == 2){
            speed = defaultSpeed;
            speedCounter = 0;
        }
    }
}
