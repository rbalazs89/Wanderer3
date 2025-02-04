package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import entity.attacks.projectile.MON_FireBall;
import main.GamePanel;
import object.OBJ_DroppedTalentBook;

import java.util.ArrayList;
import java.util.Collections;

public class MON_RedWizard extends Fighter {

    public MON_RedWizard(GamePanel gp){
        super(gp);
        this.gp = gp;
        getImages();
        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        attackDamage = 0;
        name = "Red wizard";
        movementSpriteChangeFrame = 14;
        movementSpritesNumber = 3;
        sleeping = true;
        sleepDistance = 5;
        isBoss = true;

        //Balance:
        level = 7;
        resist[1] = 45;
        resist[2] = 45;
        resist[3] = 45;
        damageOnContactValue = 1;
        experienceValue = 1100;
        defaultSpeed = 3;
        maxLife = 665;
        attackCoolDownValue = 50;
        tryToKeepThisDistance = 4;

        goBackAtThisTargetDistance = GamePanel.tileSize * 20;
        goBackToSpawnMaxDistance = GamePanel.tileSize * 30;
        aggroAtThisDistance = GamePanel.tileSize * 9;
        shouldTryToAttackRange = GamePanel.tileSize * 9;
        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    public void getImages(){
        left1 = setup("/entity/monster/act1/wizard/wizard_left_1", GamePanel.tileSize, GamePanel.tileSize);
        left2 = setup("/entity/monster/act1/wizard/wizard_left_2", GamePanel.tileSize, GamePanel.tileSize);
        left3 = setup("/entity/monster/act1/wizard/wizard_left_3", GamePanel.tileSize, GamePanel.tileSize);
        right1 = setup("/entity/monster/act1/wizard/wizard_right_1", GamePanel.tileSize, GamePanel.tileSize);
        right2 = setup("/entity/monster/act1/wizard/wizard_right_2", GamePanel.tileSize, GamePanel.tileSize);
        right3 = setup("/entity/monster/act1/wizard/wizard_right_3", GamePanel.tileSize, GamePanel.tileSize);
        attackLeft1 = setup("/entity/monster/act1/wizard/spell_left_1", GamePanel.tileSize * 3 / 2, GamePanel.tileSize);
        attackLeft2 = setup("/entity/monster/act1/wizard/spell_left_2", GamePanel.tileSize * 3 / 2, GamePanel.tileSize);
        attackRight1 = setup("/entity/monster/act1/wizard/spell_right_1", GamePanel.tileSize * 3 / 2, GamePanel.tileSize);
        attackRight2 = setup("/entity/monster/act1/wizard/spell_right_2", GamePanel.tileSize * 3 / 2, GamePanel.tileSize);
        death1 = setup("/entity/monster/act1/wizard/wizard_death", GamePanel.tileSize, GamePanel.tileSize);
    }

    public void attacking(){
        attackFrameCounter ++;
        if (attackFrameCounter == 1) {
            canAttackFromCoolDown = false;
            int targetX = targetEntity.screenMiddleX();
            int targetY = targetEntity.screenMiddleY();

            int playerX = screenMiddleX();
            int playerY = screenMiddleY();

            int deltaX = targetX - playerX;
            int deltaY = targetY - playerY;

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX < 0) {
                    attackDirection = "left";
                } else {
                    attackDirection = "right";
                }
            }
        }

        if(attackFrameCounter == attackFramePoint1) {
            if(targetEntity != null) {
                new MON_FireBall(gp, this, targetEntity, 3);
            }
        }

        if(attackFrameCounter == attackFramePoint2){
            attackFrameCounter = 0;
            currentlyAttacking = false;
        }
    }

    public void setActionAI(){
        //TODO allow target change if multiple targets available
        if(targetPathFollowed){
            if(targetEntity != null){

                if(actionLockCounter == 0){
                    int i = random.nextInt(10);
                    if(i < 9){
                        tempOnPath = true;
                    } else {
                        tempRandomMovement = true;
                    }
                }
                actionLockCounter ++;

                if(tempOnPath){
                    if(actionLockCounter == 1) {
                        findPositionComparedToTarget();
                    }
                    if(actionLockCounter == 60){
                        speed = defaultSpeed;
                        validTileFound = false;
                    }
                    if(!validTileFound && actionLockCounter == 1){
                        goalCol = spawnX / GamePanel.tileSize;
                        goalRow = spawnY / GamePanel.tileSize;
                        speed = defaultSpeed * 2;
                        winLife( 30);
                    }

                    searchPath(goalCol, goalRow, false);

                } else if(tempRandomMovement && actionLockCounter == 1){
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
                    if (75 < i) {
                        direction = "right";
                    }
                }
                if(actionLockCounter >= 60){
                    actionLockCounter = 0;
                    tempOnPath = false;
                    tempRandomMovement = false;
                }

                int i = random.nextInt(100) + 1;
                if(i >= attackChanceWhenAvailable && canAttackFromCoolDown && canAttack) {
                    if(middleDistance(targetEntity) < shouldTryToAttackRange){
                        currentlyAttacking = true;
                    }
                }
            }

            goBackCheckCounter ++;
            if(goBackCheckCounter >=  20) {
                if (targetEntity != null) {
                    if (targetEntity.middleDistance(this) > goBackAtThisTargetDistance * GamePanel.tileSize) {
                        targetEntity = null;
                        speed = defaultSpeed * 2;
                        isGoingBackToSpawn = true;
                        goalCol = spawnX / GamePanel.tileSize;
                        goalRow = spawnY / GamePanel.tileSize;
                        goBackCheckCounter = 0;
                    }
                }
            }

            //lose aggro if distance too big

            if(goBackCheckCounter >=  20) {
                if (Math.abs(worldX - spawnX) > goBackToSpawnMaxDistance || Math.abs(worldY - spawnY) > goBackToSpawnMaxDistance) {
                    targetEntity = null;
                    isGoingBackToSpawn = true;
                    speed = defaultSpeed * 2;
                    goalCol = spawnX / GamePanel.tileSize;
                    goalRow = spawnY / GamePanel.tileSize;
                    goBackCheckCounter = 20;
                }
            }

            // handle going back
            if(isGoingBackToSpawn){
                searchPath(goalCol, goalRow, true);
            }

        } else {

            setDirectionFromRandomMovement();
            // only perform this calculation 3 times per second, not every frame:
            checkForEnemiesCounter++;
            Entity closestFightingEnemy = null;
            if(checkForEnemiesCounter > 20){
                closestFightingEnemy = findClosestFightingEnemy();
                checkForEnemiesCounter = 0;
            }

            if (closestFightingEnemy != null) {
                targetPathFollowed = true;
                targetEntity = closestFightingEnemy;
                actionLockCounter = 0;
            }

            //go back if wanders off too far
            goBackCheckCounter++;
            if(goBackCheckCounter > 20) {
                goBackCheckCounter = 0;
                if (Math.abs(worldX - spawnX) > goBackToSpawnMaxDistance || Math.abs(worldY - spawnY) > goBackToSpawnMaxDistance) {
                    targetEntity = null;
                    isGoingBackToSpawn = true;
                    speed = defaultSpeed * 2;
                    goalCol = spawnX / GamePanel.tileSize;
                    goalRow = spawnY / GamePanel.tileSize;
                }
            }

            if(isGoingBackToSpawn){
                searchPath(goalCol, goalRow, true);
            }
        }
    }

    public void drawDying(){
        image = death1;
    }

    public void playDeathSound(){
        gp.playSE(54);
    }

    public void findPositionComparedToTarget() {

        int targetCol = targetEntity.worldMiddleX() / GamePanel.tileSize;
        int targetRow = targetEntity.worldMiddleY() / GamePanel.tileSize;

        String relativeDirection;

        int targetX = targetEntity.screenMiddleX();
        int targetY = targetEntity.screenMiddleY();

        int fighterX = screenMiddleX();
        int fighterY = screenMiddleY();

        int deltaX = targetX - fighterX;
        int deltaY = targetY - fighterY;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX < 0) {
                relativeDirection = "right";
            } else {
                relativeDirection = "left";
            }
        } else {
            if (deltaY < 0) {
                relativeDirection = "down";
            } else {
                relativeDirection = "up";
            }
        }

        int i = random.nextInt(3);
        switch (relativeDirection) {
            case "up":
                if (i == 0) {
                    findAvailableTileGroup(targetCol - 2, targetRow - tryToKeepThisDistance);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol, targetRow - tryToKeepThisDistance);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + 2, targetRow - tryToKeepThisDistance);
                    }
                } else if ( i == 1){
                    findAvailableTileGroup(targetCol + 2, targetRow - tryToKeepThisDistance);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - 2, targetRow - tryToKeepThisDistance);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol, targetRow - tryToKeepThisDistance);
                    }
                } else { // i == 2
                    findAvailableTileGroup(targetCol, targetRow - tryToKeepThisDistance);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + 2, targetRow - tryToKeepThisDistance);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - 2, targetRow - tryToKeepThisDistance);
                    }
                }
                break;
            case "right":
                if (i == 0) {
                    findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow + 2);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow - 2);
                    }
                } else if ( i == 1){
                    findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow - 2);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow + 2);
                    }
                } else { // i == 2
                    findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow + 2);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow - 2);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + tryToKeepThisDistance, targetRow);
                    }
                }
                break;
            case "down":
                if (i == 0) {
                    findAvailableTileGroup(targetCol - 2, targetRow + tryToKeepThisDistance);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol, targetRow + tryToKeepThisDistance);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + 2, targetRow + tryToKeepThisDistance);
                    }
                } else if ( i == 1){
                    findAvailableTileGroup(targetCol + 2, targetRow + tryToKeepThisDistance);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - 2, targetRow + tryToKeepThisDistance);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol, targetRow + tryToKeepThisDistance);
                    }

                } else { // i == 2
                    findAvailableTileGroup(targetCol, targetRow + tryToKeepThisDistance);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol + 2, targetRow + tryToKeepThisDistance);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - 2, targetRow + tryToKeepThisDistance);
                    }
                }
                break;

            case "left":
                if (i == 0) {
                    findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow + 2);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow - 2);
                    }
                } else if ( i == 1){
                    findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow - 2);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow + 2);
                    }
                } else { // i == 2
                    findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow + 2);
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow - 2);
                    }
                    if(!validTileFound){
                        findAvailableTileGroup(targetCol - tryToKeepThisDistance, targetRow);
                    }
                }
                break;
        }
    }

    public void drawAttackImageMelee1(){
        if(attackFrameCounter <= attackFramePoint1){
            switch (attackDirection) {
                case "right" -> image = attackRight1;
                case "left" -> image = attackLeft1;
            }
        }
        else{
            switch (attackDirection) {
                case "right" -> image = attackRight2;
                case "left" -> image = attackLeft2;
            }
        }
        //image correction:
        switch (attackDirection) {
            case "up" -> {
                screenX = screenX - (image.getWidth() / 2 - GamePanel.tileSize / 2);
                screenY = screenY - (image.getHeight() - GamePanel.tileSize);
            }
            case "right" -> screenY = screenY - (image.getHeight() / 2 - GamePanel.tileSize / 2);
            case "down" -> screenX = screenX - (image.getWidth() / 2 - GamePanel.tileSize / 2);
            case "left" -> {
                screenX = screenX - (image.getWidth() - GamePanel.tileSize);
                screenY = screenY - (image.getHeight() / 2 - GamePanel.tileSize / 2);
            }
        }
    }
    public void drawGetWalkSpriteNumber(){
        if (gp.gameState == gp.playState && !stunned && !frozen) {
            spriteCounter++;
            if (spriteCounter > movementSpriteChangeFrame) {
                spriteNum = (spriteNum + 1) % movementSpritesNumber;
                spriteCounter = 0;
            }
        }
    }
    public void searchEnemies() {
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity currentEntity = gp.allFightingEntities.get(i);
            if(isHostile(this,currentEntity)){
                if(middleDistance(currentEntity) < sleepDistance * GamePanel.tileSize){
                    sleeping = false;
                }
            }
        }

        if(life != maxLife ){
            sleeping = false;
        }
        if(!sleeping){
            gp.playSE(51);
        }
    }
    public void drawGetWalkingImage(){
        switch (direction) {
            case "up":
            case "right":
                if (spriteNum == 0) {
                    image = right1;
                } else if (spriteNum == 1) {
                    image = right2;
                } else if (spriteNum == 2) {
                    image = right3;
                }
                break;
            case "down":
            case "left":
                if (spriteNum == 0) {
                    image = left1;
                } else if (spriteNum == 1) {
                    image = left2;
                } else if (spriteNum == 2) {
                    image = left3;
                }
                break;
        }
    }

    public boolean findAvailableTile(int col, int row){
        if(col > 0 && row > 0 && col < gp.currentMapMaxCol && row < gp.currentMapMaxRow){
            if(!gp.tileM.tile[gp.tileM.mapTileNum[col][row]].collision){
                validTileFound = true;
                goalRow = row;
                goalCol = col;
                return true;
            }
        }
        return false;
    }

    /*
    public void findAvailableTileGroup2(int col, int row){
        if(findAvailableTile(col - 1, row - 1)){
            return;
        }
        if(findAvailableTile(col , row - 1)){
            return;
        }
        if(findAvailableTile(col + 1, row - 1)){
            return;
        }
        if(findAvailableTile(col - 1, row)){
            return;
        }
        if(findAvailableTile(col, row)){
            return;
        }
        if(findAvailableTile(col + 1, row)){
            return;
        }
        if(findAvailableTile(col - 1, row + 1)){
            return;
        }
        if(findAvailableTile(col, row + 1)){
            return;
        }
        if(findAvailableTile(col + 1, row + 1)){
            return;
        }
    }
    */

    public void findAvailableTileGroup(int col, int row) {
        ArrayList<int[]> tileCoordinates = new ArrayList<>();
        tileCoordinates.add(new int[]{col - 1, row - 1});
        tileCoordinates.add(new int[]{col, row - 1});
        tileCoordinates.add(new int[]{col + 1, row - 1});
        tileCoordinates.add(new int[]{col - 1, row});
        tileCoordinates.add(new int[]{col, row});
        tileCoordinates.add(new int[]{col + 1, row});
        tileCoordinates.add(new int[]{col - 1, row + 1});
        tileCoordinates.add(new int[]{col, row + 1});
        tileCoordinates.add(new int[]{col + 1, row + 1});

        Collections.shuffle(tileCoordinates);

        for (int[] coords : tileCoordinates) {
            if (findAvailableTile(coords[0], coords[1])) {
                return;
            }
        }
    }

    public void dropItem(){
        if(!gp.progress.act1BookPickedUp[1]){
            OBJ_DroppedTalentBook book = new OBJ_DroppedTalentBook(gp);
            int[] tempArray = gp.uTool.findPlaceForDroppedItem2(worldMiddleX(), worldMiddleY());
            book.worldX = tempArray[0];
            book.worldY = tempArray[1];
            gp.interactObjects.add(book);
        } else {
           itemDrop(level, 0, true);
        }
    }
}
