package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import main.GamePanel;

import java.awt.*;

public class MON_MimicDoor extends Fighter {
    private int wakingUpCounter = 0;
    public MON_MimicDoor(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 10;
        getImage();
        sleeping = true;
        sleepDistance = 3;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = GamePanel.tileSize;
        solidArea.height = GamePanel.tileSize;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        collisionEntity = true;
        name = "Mimic door ";

        //Balance:
        damageOnContactValue = 5;
        defaultSpeed = 0;
        maxLife = 400;
        level = 10;
        experienceValue = 300;

        /////
        drawHpBar = false;
        life = maxLife;
        speed = defaultSpeed;
    }


    public void getImage(){
        up1 = setup("/entity/monster/act1/mimicdoor/mimic1", 64, 64);
        up2 = setup("/entity/monster/act1/mimicdoor/mimic2", 64, 64);
        up3 = setup("/entity/monster/act1/mimicdoor/mimic3", 64, 64);
        up4 = setup("/entity/monster/act1/mimicdoor/mimic4", 64, 64);
    }


    public void update(){
        if(sleeping) {
            searchEnemies();
        } else {
            wakingUp();
            handleDamageOnContact();
            handleDeathRelated();
        }
    }

    private void wakingUp() {
        if(wakingUpCounter < 60){
            wakingUpCounter ++;
            if(wakingUpCounter == 1){
                entityType = 2;
                gp.playSE(55);
            }
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(wakingUpCounter < 15) {
            image = up1;
        } else if(wakingUpCounter < 30){
            image = up2;
        } else if(wakingUpCounter < 45){
            image = up3;
        } else if(wakingUpCounter < 60){
            image = up4;
        }

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize,null);
        }

        drawHpBar(g2);

        if(!sleeping){
            drawHitBox(g2);
        }
    }

    public void searchEnemies() {
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity currentEntity = gp.allFightingEntities.get(i);
            if(currentEntity != this){
                if(middleDistance(currentEntity) < sleepDistance * GamePanel.tileSize){
                    sleeping = false;
                }
            }
        }
        if(life != maxLife ){
            sleeping = false;
        }
        if(!sleeping){
            drawHpBar = true;
        }
    }

    //mimic doesn't drop item
    public void dropItem(){

    }
}
