package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Doggy extends Entity {
    public NPC_Doggy(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        solidArea = new Rectangle(3 * GamePanel.tileSize / 16,
                GamePanel.tileSize * 5/ 16,
                GamePanel.tileSize * 10 / 16,
                GamePanel.tileSize * 11 / 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        collisionEntity = false;
        headDialogueString = " *Woof* Did you know that, if you restart the game, all monsters will reappear. *Woof*";
    }

    public void getImage() {

        walkUp = new BufferedImage[3];
        walkRight = new BufferedImage[3];
        walkDown = new BufferedImage[3];
        walkLeft = new BufferedImage[3];

        movementSpriteChangeFrame = 5;
        movementSpritesNumber = 3;

        for (int i = 0; i < 3; i++) {
            walkDown[i] = setupSheet("/entity/npcdoggy/doggysheet", i * 48, 0, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            walkLeft[i] = setupSheet("/entity/npcdoggy/doggysheet", i * 48, 48, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            walkRight[i] = setupSheet("/entity/npcdoggy/doggysheet", i * 48, 96, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            walkUp[i] = setupSheet("/entity/npcdoggy/doggysheet", i * 48, 144, 48, 48, 64, 64);
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY) {

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
            case "left" -> image = walkLeft[spriteNum];
            case "right" -> image = walkRight[spriteNum];
            case "up" -> image = walkUp[spriteNum];
            case "down" -> image = walkDown[spriteNum];
        }
    }


    public void setActionAI() {
        decideIfPlayerNear();
        setDirectionFromRandomMovement();
    }
}
