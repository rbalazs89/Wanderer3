package entity;
import main.GamePanel;

import java.awt.*;

public class NPC_Villager extends Entity {

    public NPC_Villager(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        solidArea = new Rectangle(3 * gp.tileSize / 16,
                gp.tileSize * 5/ 16,
                gp.tileSize * 10 / 16,
                gp.tileSize * 11 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        setDialogue();
        collisionEntity = false;
        headDialogueString = "nomnom";
    }

    public void speak() {
        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    public void setDialogue() {
        dialogues[0] = "Hel/nlo";
        dialogues[1] = "This is my house";
        dialogues[2] = "And this is my hammer";
        dialogues[3] = "Don't hurt the cows";
    }

    public void getImage() {
        int tileSize = gp.tileSize;

        up1 = setup("/entity/npcvillager/villager_male_up_1", tileSize, tileSize);
        up2 = setup("/entity/npcvillager/villager_male_up_2", tileSize, tileSize);
        down1 = setup("/entity/npcvillager/villager_male_down_1", tileSize, tileSize);
        down2 = setup("/entity/npcvillager/villager_male_down_2", tileSize, tileSize);
        left1 = setup("/entity/npcvillager/villager_male_left_1", tileSize, tileSize);
        left2 = setup("/entity/npcvillager/villager_male_left_2", tileSize, tileSize);
        right1 = setup("/entity/npcvillager/villager_male_right_1", tileSize, tileSize);
        right2 = setup("/entity/npcvillager/villager_male_right_2", tileSize, tileSize);
    }

    public void setActionAI() {

        setActionWhenNear();

        if(targetPathFollowed){
            int goalCol = gp.player.worldMiddleX() / gp.tileSize;
            int goalRow = gp.player.worldMiddleY()/gp.tileSize;

            searchPath(goalCol, goalRow, false);
        }

        else{
            actionLockCounter ++;
            if(actionLockCounter > 120) {
                int i = random.nextInt(100) + 1;
                if (i <= 25) {
                    direction = "up";
                }
                if (25 < i && i <= 50) {
                    direction = "down";
                }
                if (50 < i && i <= 75) {
                    direction = "left";
                }
                if (75 < i && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
}
