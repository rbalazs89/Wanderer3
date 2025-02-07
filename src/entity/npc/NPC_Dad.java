package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Dad extends Entity {
    int speedCounter;

    public NPC_Dad(GamePanel gp) {
        super(gp);
        direction = "down";
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(1 * gp.tileSize / 16,
                gp.tileSize * 1 / 16,
                gp.tileSize * 14 / 16,
                gp.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        collisionEntity = false;
        if(!gp.progress.act1InteractedObjects[2]){
            headDialogueString = "Go help your brother in the cellar!";
        } else {
            headDialogueString = "Good job clearing the cellar!";
        }

    }

    public void getImage() {

        walkUp = new BufferedImage[8];
        walkRight = new BufferedImage[8];
        walkDown = new BufferedImage[8];
        walkLeft = new BufferedImage[8];

        movementSpriteChangeFrame = 5;
        movementSpritesNumber = 8;

        for (int i = 0; i < 8; i++) {
            walkDown[i] = setupSheet("/entity/npcdad/dad", i * 77, 0, 77, 77, 77, 77);
        }
        for (int i = 0; i < 8; i++) {
            walkLeft[i] = setupSheet("/entity/npcdad/dad", i * 77, 77, 77, 77, 77, 77);
        }
        for (int i = 0; i < 8; i++) {
            walkRight[i] = setupSheet("/entity/npcdad/dad", i * 77, 154, 77, 77, 77, 77);
        }
        for (int i = 0; i < 8; i++) {
            walkUp[i] = setupSheet("/entity/npcdad/dad", i * 77, 231, 77, 77, 77, 77);
        }
    }

    public void setActionAI() {
        controlSpeed();
        decideIfPlayerNear();
        setDirectionFromRandomMovement();
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            getWalkImage();
            drawGetWalkSpriteNumber();
            if (actionWhenNear1) {
                nearHeadDialogue(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
        }
    }

    public void drawGetWalkSpriteNumber() {
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

    public void controlSpeed() {
        speedCounter++;
        if (speedCounter == 1) {
            speed = 0;
        } else if (speedCounter == 2) {
            speed = defaultSpeed;
            speedCounter = 0;
        }
    }
}
