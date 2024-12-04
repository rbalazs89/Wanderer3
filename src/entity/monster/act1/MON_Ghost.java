package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class MON_Ghost extends Fighter {
    int speedCounter = 0;

    public MON_Ghost(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 3;
        drawHpBar = true;
        collisionEntity = false;
        name = "Cemetery ghost";
        solidArea.x = gp.tileSize / 16 * 1;
        solidArea.y = gp.tileSize / 16 * 4;
        solidArea.width = gp.tileSize / 16 * 14;
        solidArea.height = gp.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 6 * gp.tileSize;
        movementSpritesNumber = 3;
        resist[0] = 90;

        //Balance:
        damageOnContactValue = 1;
        defaultSpeed = 1;
        meleeAttackRange =  (int) (0.75 * gp.tileSize);
        maxLife = 200;
        level = 30;
        experienceValue = 10;
        movementSpriteChangeFrame = 20;

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        walkUp = new BufferedImage[3];
        walkRight = new BufferedImage[3];
        walkDown = new BufferedImage[3];
        walkLeft = new BufferedImage[3];
        walkUp[0] = setup("/entity/monster/act1/ghost/up1", gp.tileSize, gp.tileSize);
        walkUp[1] = setup("/entity/monster/act1/ghost/up2", gp.tileSize, gp.tileSize);
        walkUp[2] = setup("/entity/monster/act1/ghost/up3", gp.tileSize, gp.tileSize);
        walkRight[0] = setup("/entity/monster/act1/ghost/right1", gp.tileSize, gp.tileSize);
        walkRight[1] = setup("/entity/monster/act1/ghost/right2", gp.tileSize, gp.tileSize);
        walkRight[2] = setup("/entity/monster/act1/ghost/right3", gp.tileSize, gp.tileSize);
        walkDown[0] = setup("/entity/monster/act1/ghost/down1", gp.tileSize, gp.tileSize);
        walkDown[1] = setup("/entity/monster/act1/ghost/down2", gp.tileSize, gp.tileSize);
        walkDown[2] = setup("/entity/monster/act1/ghost/down3", gp.tileSize, gp.tileSize);
        walkLeft[0] = setup("/entity/monster/act1/ghost/left1", gp.tileSize, gp.tileSize);
        walkLeft[1] = setup("/entity/monster/act1/ghost/left2", gp.tileSize, gp.tileSize);
        walkLeft[2] = setup("/entity/monster/act1/ghost/left3", gp.tileSize, gp.tileSize);
    }

    public void setActionAI(){
        if(!isGoingBackToSpawn){
            controlSpeed();
            randomMovement();
        }

        goBackCheckCounter++;
        if(goBackCheckCounter > 20) {
            goBackCheckCounter = 0;
            if (Math.abs(worldX - spawnX) > goBackToSpawnMaxDistance || Math.abs(worldY - spawnY) > goBackToSpawnMaxDistance) {
                isGoingBackToSpawn = true;
                speed = defaultSpeed;
                goalCol = spawnX / gp.tileSize;
                goalRow = spawnY / gp.tileSize;
            }
        }

        if(isGoingBackToSpawn){
            searchPath(goalCol, goalRow, true);
        }
    }
    public void drawDying(){

    }
    public void controlSpeed(){
        speedCounter++;
        if(speedCounter == 1){
            speed = 0;
        } else if (speedCounter == 3){
            speed = defaultSpeed;
            speedCounter = 0;
        }
    }

    //this entity gives a juice charge when killed because why not
    public void handleDeathRelated(){
        if(life <= 0){
            isDying = true;
        }

        if(isDying){
            if(deathTimeCounter == 1){
                specialOnDeath();
                playDeathSound();
                //dropItem(); doesnt drop item
                speed = 0;
                defaultSpeed = 0;
                currentlyAttacking = false;
                canAttackFromCoolDown = false;
                canAttack = false;
                damageOnContactValue = 0;
                //gp.player.experience += gp.dataBase1.levelDifferenceExperience(gp.player.level, level, experienceValue);
                gp.player.winExperience(level, experienceValue, name);
                gp.player.checkIfLvLUp();
                gp.allFightingEntities.remove(this);
            }

            deathTimeCounter++;
            if (deathTimeCounter > deathTimer){
                for (int i = 0; i < gp.fighters[1].length; i++) {
                    if(gp.fighters[gp.currentMap][i] == this){
                        gp.fighters[gp.currentMap][i] = null;
                        break;
                    }
                }
            }
        }

    }

    public void specialOnDeath(){
        gp.player.momoJuice.charge = Math.min(gp.player.momoJuice.charge + 1,gp.player.momoJuice.maxCharge);
    }

}
