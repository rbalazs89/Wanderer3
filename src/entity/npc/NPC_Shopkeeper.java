package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Shopkeeper extends Entity {

    public NPC_Shopkeeper(GamePanel gp) {
        super(gp);
        direction = "down";
        defaultSpeed = 0;
        speed = defaultSpeed;
        solidArea = new Rectangle(GamePanel.tileSize / 16,
                GamePanel.tileSize / 16,
                GamePanel.tileSize * 14 / 16,
                GamePanel.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage() {
        walkDown = new BufferedImage[8];
        movementSpriteChangeFrame = 5;
        movementSpritesNumber = 8;

        BufferedImage tempImage = setupImage("/entity/npcshopkeeper/idle");
        for (int i = 0; i < 8; i++) {
            walkDown[i] = setupSheet(tempImage, i * 64, 0, 64, 64);
        }
    }

    public void setActionAI() {

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
}

