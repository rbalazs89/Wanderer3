package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Horse extends Entity {

    public NPC_Horse(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        defaultSpeed = 1;
        solidArea = new Rectangle(3 * GamePanel.tileSize / 16,
                GamePanel.tileSize * 5/ 16,
                GamePanel.tileSize * 10 / 16,
                GamePanel.tileSize * 11 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        collisionEntity = false;
        headDialogueString = "*neigh* You can put items in the chest in the camp. *neigh*";
    }

    public void getImage(){
        up1 = setup("/entity/npchorse/horse_up_1", GamePanel.tileSize, GamePanel.tileSize);
        up2 = setup("/entity/npchorse/horse_up_2", GamePanel.tileSize, GamePanel.tileSize);
        up3 = setup("/entity/npchorse/horse_up_3", GamePanel.tileSize, GamePanel.tileSize);
        down1 = setup("/entity/npchorse/horse_down_1", GamePanel.tileSize, GamePanel.tileSize);
        down2 = setup("/entity/npchorse/horse_down_2", GamePanel.tileSize, GamePanel.tileSize);
        down3 = setup("/entity/npchorse/horse_down_3", GamePanel.tileSize, GamePanel.tileSize);
        left1 = setup("/entity/npchorse/horse_left_1", GamePanel.tileSize, GamePanel.tileSize);
        left2 = setup("/entity/npchorse/horse_left_2", GamePanel.tileSize, GamePanel.tileSize);
        left3 = setup("/entity/npchorse/horse_left_3", GamePanel.tileSize, GamePanel.tileSize);
        right1 = setup("/entity/npchorse/horse_right_1", GamePanel.tileSize, GamePanel.tileSize);
        right2 = setup("/entity/npchorse/horse_right_2", GamePanel.tileSize, GamePanel.tileSize);
        right3 = setup("/entity/npchorse/horse_right_3", GamePanel.tileSize, GamePanel.tileSize);
    }

    public void setActionAI(){
        switch (aiBehaviourNumber){
            case 0: break;
            case 1:
                followBehaviourAct1();
                break;
        }
    }


    public void followBehaviourAct1(){
        decideIfPlayerNear();
        if(worldX < 19 * GamePanel.tileSize || worldY > GamePanel.tileSize * 7){
            targetPathFollowed = true;
        }
        if(targetPathFollowed){
            int goalCol = 25;
            int goalRow = 1;
            searchPath(goalCol, goalRow, true);

        } else {
            setDirectionFromRandomMovement();
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            switch(direction) {
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    break;
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3                                                                                                                                       ;
                    }
                    break;
            }
            if (gp.gameState == gp.playState) {
                spriteCounter++;
                if (spriteCounter > 10) {
                    spriteNum = (spriteNum  % 3) + 1;
                    spriteCounter = 0;
                }
            }
        }
        if(actionWhenNear1){
            nearHeadDialogue(g2);
        }
        g2.drawImage(image, screenX, screenY,null);
    }
}
