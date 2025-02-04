package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NPC_Priest extends Entity {
    int speedCounter = 0;

    public NPC_Priest(GamePanel gp){
        super(gp);
        direction ="down";
        speed = 1;
        solidArea =new Rectangle(3*gp.tileSize /16,
                  gp.tileSize *5/16,
                  gp.tileSize *10/16,
                  gp.tileSize *11/16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        collisionEntity = false;
        headDialogueString ="You can reassign your skill points, talent points and attribute points by praying at the altar. You do keep your levels.";
    }

    public void getImage(){
        walkRight = new BufferedImage[8];
        walkLeft = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            walkRight[i] = setupSheet("/entity/npcpriest/rightsheet", i * 160, 0, 160, 128, 120, 96);
        }

        for (int i = 7; i > -1; i--) {
            walkLeft[i] = setupSheet("/entity/npcpriest/leftsheet", i * 160, 0, 160, 128, 120, 96);
        }
    }

    public void setActionAI(){
        switch (aiBehaviourNumber){
            case 0: break;
            case 1:
                followBehaviourAct1();
                break;
        }
    }

    // TODO when map ready
    public void followBehaviourAct1(){
        controlSpeed();
        setActionWhenNear();
        //setBoundAreaNPC(1,1,10,10);
        if(targetPathFollowed){
            int goalCol = 5;
            int goalRow = 5;
            searchPath(goalCol, goalRow, true);

        } else {
            setDirectionFromRandomMovement();
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
            switch(direction){
                case "left":
                case "down": image = walkLeft[spriteNum]; break;
                case "right":
                case "up": image = walkRight[spriteNum]; break;
            }

            if (gp.gameState == gp.playState) {
                spriteCounter++;
                if (spriteCounter > 10) {
                    spriteNum ++;
                    spriteNum = (spriteNum % 8);
                    spriteCounter = 0;
                }
            }
            if(actionWhenNear1){
                nearHeadDialogue(g2);
            }
            g2.drawImage(image, screenX, screenY,null);
        }
    }

    public void nearHeadDialogue(Graphics2D g2) {
        Font titleFont = new Font("Calibri", Font.PLAIN, 13);
        g2.setFont(titleFont);
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<String> lines = splitTextIntoLines(headDialogueString, fm, 130 - 2 * 10);
        int textHeight = fm.getHeight();
        int boxHeight = textHeight * lines.size() + 2 * 10;
        drawHoverGuide(headDialogueString, screenMiddleX() - 65 + 30,screenMiddleY() - boxHeight - 30, 130, g2);
    }

    public void controlSpeed(){
        speedCounter++;
        if(speedCounter == 1){
            speed = 0;
        } else if (speedCounter == 3){
            speed = 1;
            speedCounter = 0;
        }
    }


}
