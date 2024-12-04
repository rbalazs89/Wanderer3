package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
public class NPC_CaravanBro extends Entity{

    public NPC_CaravanBro(GamePanel gp) {
        super(gp);
        direction = "down";
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(3 * gp.tileSize / 16,
                gp.tileSize * 5/ 16,
                gp.tileSize * 10 / 16,
                gp.tileSize * 11 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        collisionEntity = false;
        headDialogueString = "The route to the north is blocked. Go and explore the woods to the south!";
    }

    public void updateHeadDialogue(String newDialogue){
        headDialogueString = newDialogue;
    }

    public void getImage() {
        int tileSize = gp.tileSize;
        up1 = setup("/entity/npc_caravanbro/caravan_up_1", tileSize, tileSize);
        up2 = setup("/entity/npc_caravanbro/caravan_up_2", tileSize, tileSize);
        up3 = setup("/entity/npc_caravanbro/caravan_up_3", tileSize, tileSize);
        down1 = setup("/entity/npc_caravanbro/caravan_down_1", tileSize, tileSize);
        down2 = setup("/entity/npc_caravanbro/caravan_down_2", tileSize, tileSize);
        down3 = setup("/entity/npc_caravanbro/caravan_down_3", tileSize, tileSize);
        left1 = setup("/entity/npc_caravanbro/caravan_left_1", tileSize, tileSize);
        left2 = setup("/entity/npc_caravanbro/caravan_left_2", tileSize, tileSize);
        left3 = setup("/entity/npc_caravanbro/caravan_left_3", tileSize, tileSize);
        right1 = setup("/entity/npc_caravanbro/caravan_right_1", tileSize, tileSize);
        right2 = setup("/entity/npc_caravanbro/caravan_right_2", tileSize, tileSize);
        right3 = setup("/entity/npc_caravanbro/caravan_right_3", tileSize, tileSize);
    }

    public void setActionAI() {
        switch (aiBehaviourNumber){
            case 0: break;
            case 1:
                followBehaviourAct1();
                break;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
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
                if (spriteCounter > 20) {
                    spriteNum = (spriteNum  % 3) + 1;
                    spriteCounter = 0;
                }
            }
        }
        g2.drawImage(image, screenX, screenY,null);
        if(actionWhenNear1){
            nearHeadDialogue(g2);
        }
    }

    public void followBehaviourAct1(){

        setActionWhenNear();
        setBoundAreaNPC(7,0,50,7);

        if(targetPathFollowed){
            int goalCol = 17;
            int goalRow = 2;
            searchPath(goalCol, goalRow, true);
        }
        else{
            randomMovement();
        }
    }
}
