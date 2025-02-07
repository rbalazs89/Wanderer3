package entity.npc;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class NPC_Cow extends Entity {

    public NPC_Cow(GamePanel gp){
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
        headDialogueString = " *Moo* Stay away from the caves in the northwest. Its crawling with dangerous monsters. *Moo*";
    }

    public void setActionAI(){
        setDirectionFromRandomMovement();
        decideIfPlayerNear();
    }

    public void getImage() {
        up1 = setup("/entity/npccow/cow_up_1", GamePanel.tileSize, GamePanel.tileSize);
        up2 = setup("/entity/npccow/cow_up_2", GamePanel.tileSize, GamePanel.tileSize);
        down1 = setup("/entity/npccow/cow_down_1", GamePanel.tileSize, GamePanel.tileSize);
        down2 = setup("/entity/npccow/cow_down_2", GamePanel.tileSize, GamePanel.tileSize);
        left1 = setup("/entity/npccow/cow_left_1", GamePanel.tileSize, GamePanel.tileSize);
        left2 = setup("/entity/npccow/cow_left_2", GamePanel.tileSize, GamePanel.tileSize);
        right1 = setup("/entity/npccow/cow_right_1", GamePanel.tileSize, GamePanel.tileSize);
        right2 = setup("/entity/npccow/cow_right_2", GamePanel.tileSize, GamePanel.tileSize);
    }
}
