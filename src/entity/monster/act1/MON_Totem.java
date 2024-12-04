package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import entity.attacks.projectile.MON_PikaLightning;
import main.GamePanel;
import object.OBJ_DroppedTalentBook;

import java.awt.*;

public class MON_Totem extends Fighter {

    boolean bookDropped = false;

    public MON_Totem(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = true;
        name = "Totem";
        sleepDistance = 4;
        solidArea.x = 5;
        solidArea.y = gp.tileSize / 16 * 1;
        solidArea.width = gp.tileSize / 16 * 14;
        solidArea.height = 128;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        sleeping = true;

        //Balance:
        damageOnContactValue = 0;
        attackCoolDownValue = 150;
        attackChanceWhenAvailable = 200;
        defaultSpeed = 0;
        maxLife = 300;
        experienceValue = 100;

        attackFramePoint1 = 10;
        attackFramePoint2 = 20;
        aggroAtThisDistance = 10 * gp.tileSize;

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        image = setup("/entity/monster/act1/totem/totem");
    }

    public void draw(Graphics2D g2){
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + 3 * gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX -  3 * gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + 3 * gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - 3 * gp.tileSize < gp.player.worldY + gp.player.screenY){

            //DRAW IMAGE:
            imageCorrection();
            g2.drawImage(image, screenX, screenY, null);
        }
        // DRAW HP BAR:
        drawHpBar(g2);

        //DRAW HITBOX:
        drawHitBox(g2);
    }

    public void drawDying(){

    }

    public void imageCorrection(){
        screenX = screenX - gp.tileSize/2;
    }


    public void update() {
        if(sleeping){
            searchEnemies();
        }
        else {
            if (currentlyAttacking) {
                attacking();
            }
            if (!currentlyAttacking) {
                setActionAI();
                handleCollisionAndMovement();
            }
            handleDamageOnContact();
        }
        handleDeathRelated();
    }

    public void setActionAI(){
        checkForEnemiesCounter++;

        if(checkForEnemiesCounter > 20){
            Entity closestFightingEnemy = null;
            closestFightingEnemy = findClosestFightingEnemy();
            checkForEnemiesCounter = 0;
            if (closestFightingEnemy != null){
                targetEntity = closestFightingEnemy;

            } else targetEntity = null;
        }

        if(targetEntity != null){
            int i = random.nextInt(200) + 1;
            if(i >= attackChanceWhenAvailable && canAttack) {
                if(targetEntity != null){
                    currentlyAttacking = true;
                }
            }
        }
    }

    public void createAttackInstance(){
        new MON_PikaLightning(gp, this, targetEntity, 1, 25, 20 );
    }


    //totem cannot get frozen
    public void setFrozen(int freezeStrength){

    }

    public void setAI(int AINumber){
        aiBehaviourNumber = AINumber;
    }

    public void searchEnemies() {
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity currentEntity = gp.allFightingEntities.get(i);
            if(isHostile(this,currentEntity)){
                if(middleDistance(currentEntity) < sleepDistance * gp.tileSize){
                    sleeping = false;
                }
            }
        }

        if(life != maxLife ){
            sleeping = false;
        }

        if(!sleeping){
            if(aiBehaviourNumber == 1){
                if(gp.currentMap == 10){
                    for (int i = 0; i < 4; i++) {
                        if(gp.fighters[10][i] != null){
                            gp.fighters[10][i].sleeping = false;
                        }
                    }
                }
            }
        }
    }
    //todo quest progress
    public void dropItem(){

        if(aiBehaviourNumber == 1){
            int tempInt = 0;
            for (int i = 0; i < 4; i++) {
                if(gp.fighters[10][i] != null){

                    if(gp.fighters[10][i].isDying){
                        tempInt++;
                    }

                    if (gp.fighters[10][i] instanceof MON_Totem) {
                        MON_Totem totem = (MON_Totem) gp.fighters[10][i];
                        if (!totem.bookDropped) {
                            tempInt++;
                        }
                    }
                }
                if(gp.fighters[10][i] == null){
                    tempInt++;
                    tempInt++;
                }

            }

            if(tempInt == 8){
                if(!gp.progress.act1BookPickedUp[5]){
                    bookDropped = true;
                    OBJ_DroppedTalentBook book = new OBJ_DroppedTalentBook(gp);
                    int[] tempArray = gp.uTool.findPlaceForDroppedItem2(worldMiddleX(), worldMiddleY());
                    book.worldX = tempArray[0];
                    book.worldY = tempArray[1];
                    gp.interactObjects.add(book);
                }
            }
        }
    }
}
