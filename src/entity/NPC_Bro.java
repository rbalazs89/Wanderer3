package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Bro extends Entity{

    public NPC_Bro(GamePanel gp) {
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
        headDialogueString = "Go help your brother in the cellar.";
    }

    public void getImage() {

        walkUp = new BufferedImage[9];
        walkRight = new BufferedImage[9];
        walkDown = new BufferedImage[9];
        walkLeft = new BufferedImage[9];


        BufferedImage tempImage = setupImage("/entity/allied/bro/brosheet");
        for (int i = 0; i < 9; i++) {
            walkUp[i] = setupSheet(tempImage, i * 128,0,128, 128, 96, 96);
            walkRight[i] = setupSheet(tempImage, i * 128,128,128, 128, 96, 96);
            walkDown[i] = setupSheet(tempImage,i * 128,256,128, 128, 96,96);
            walkLeft[8-i] = setupSheet(tempImage, i * 128,384,128, 128, 96,96);
        }

        movementSpriteChangeFrame = 5;
        movementSpritesNumber = 9;
    }

    public void setActionAI() {
        setActionWhenNear();
        randomMovement();
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

            //walk image correction for this npc
            switch (direction) {
                case "up" :
                    screenX = screenX - 16;
                    screenY = screenY - 10;
                    break;
                case "right" :
                    screenX = screenX - 5;
                    screenY = screenY - 16;
                    break;
                case "down" :
                    screenX = screenX - 16;
                    screenY = screenY - 18;
                    break;
                case "left" :
                    screenX = screenX - 30;
                    screenY = screenY - 20;
                    break;
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
}
