package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import entity.attacks.melee.MON_SkeletonKingMeleeAttack;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class MON_SkeletonKing extends Fighter {

    int switchTargetCounter = 0;

    public MON_SkeletonKing(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Skeleton king";
        sleepDistance = 4;
        solidArea.x = 2;
        solidArea.y = GamePanel.tileSize / 16;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = 62;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 10 * GamePanel.tileSize;
        attackSoundReferenceNumber = -1;
        sleeping = true;
        isBoss = true;

        //Balance:
        resist[1] = 15;
        level = 10;
        damageOnContactValue = 10;
        attackCoolDownValue = 215;
        attackChanceWhenAvailable = 99;
        attackDamage = 101;
        defaultSpeed = 1;
        meleeAttackRange = (int) (1.3 * GamePanel.tileSize);
        maxLife = 700;
        experienceValue = 1200;

        attackFramePoint1 = 75;
        attackFramePoint2 = 150;
        aggroAtThisDistance = 15 * GamePanel.tileSize;
        shouldTryToAttackRange = (int) (2.5 * GamePanel.tileSize);

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {

        walkUp = new BufferedImage[9];
        walkRight = new BufferedImage[9];
        walkDown = new BufferedImage[9];
        walkLeft = new BufferedImage[9];
        dying = new BufferedImage[5];
        attackUp = new BufferedImage[9];
        attackRight = new BufferedImage[9];
        attackDown = new BufferedImage[9];
        attackLeft = new BufferedImage[9];

        for (int i = 0; i < 9; i++) {
            walkUp[i] = setupSheet2("/entity/monster/act1/skeletonking/up", i * 128,0,128, 128);
            walkRight[i] = setupSheet2("/entity/monster/act1/skeletonking/right", i * 128,0,128, 128);
            walkDown[i] = setupSheet2("/entity/monster/act1/skeletonking/down", i * 128,0,128, 128);
            walkLeft[8-i] = setupSheet2("/entity/monster/act1/skeletonking/left", i * 128,0,128, 128);
            attackUp[i] = setupSheet2("/entity/monster/act1/skeletonking/attackup", i * 128,0,128, 128);
            attackRight[i] = setupSheet2("/entity/monster/act1/skeletonking/attackright", i * 128,0,128, 128);
            attackDown[i] = setupSheet2("/entity/monster/act1/skeletonking/attackdown", i * 128,0,128, 128);
            attackLeft[8-i] = setupSheet2("/entity/monster/act1/skeletonking/attackleft", i * 128,0,128, 128);
        }

        for (int i = 0; i < 5; i++) {
            dying[i] = setupSheet2("/entity/monster/act1/skeletonking/dying", i * 128, 0, 128, 128);
        }

    }

    public void attackImageCorrection(){
        switch (attackDirection) {
            case "up" -> {
                screenX = screenX - 48;
                screenY = screenY - 44;
            }
            case "right" -> screenY = screenY - GamePanel.tileSize;
            case "down" -> {
                screenX = screenX - 20;
                screenY = screenY - 26;
            }
            case "left" -> {
                screenX = screenX - GamePanel.tileSize;
                screenY = screenY - GamePanel.tileSize;
            }
        }
    }

    public void walkImageCorrection(){
        switch (direction) {
            case "up" -> {
                screenX = screenX - 52;
                screenY = screenY - 34;
            }
            case "right" -> {
                screenX = screenX - 16;
                screenY = screenY - 32;
            }
            case "down" -> {
                screenX = screenX - 8;
                screenY = screenY - 32;
            }
            case "left" -> {
                screenX = screenX - 50;
                screenY = screenY - 32;
            }
        }
    }

    public void dyingImageCorrection(){
        screenX = screenX + 55;
        screenY = screenY + 32;
    }

    public void drawDying(){
        /**int framesPerSprite = 60 / 10; // 60 frames divided by 10 sprites*/
        int deathSprite = Math.min(deathTimeCounter / 12, 4);
        image = dying[deathSprite];
        screenX = screenX - GamePanel.tileSize;
        screenY = screenY - GamePanel.tileSize;
    }

    public void playDeathSound(){
        gp.playSE(61);
    }

    public void setActionAI(){
        //TODO allow target change if multiple targets available

        if(targetPathFollowed){
            setActionAIPath();
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

    public void setActionAIPath(){
        if(targetEntity != null){
            if(targetEntity.isDying){
                targetPathFollowed = false;
            }
            if(actionLockCounter == 0){
                int i = random.nextInt(3); // 0, 1 , 2
                if(i < 2){
                    tempOnPath = true;
                } else {
                    tempRandomMovement = true;
                }
            }

            actionLockCounter ++;

            if(tempOnPath){

                findPositionComparedToTarget();
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
                if(targetEntity != null){
                    if(middleDistance(targetEntity) < shouldTryToAttackRange){
                        currentlyAttacking = true;
                    }
                }
            }
        }

        switchTargetCounter ++;
        if (switchTargetCounter >= 60){
            switchTargetCounter = 0;
            Entity closestEnemy = findClosestFightingEnemy();
            if (closestEnemy != null) {
                targetEntity = closestEnemy;
            }
        }

        goBackCheckCounter ++;
        if(goBackCheckCounter >=  20) {
            if (targetEntity != null) {
                if (targetEntity.middleDistance(this) > 12 * GamePanel.tileSize) {
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
    }


    public void createAttackInstance() {
        new MON_SkeletonKingMeleeAttack(gp, attackFramePoint2 - attackFramePoint1, attackDirection, meleeAttackRange, this);
    }

    public void attacking(){
        if(targetEntity == null){
            attackFrameCounter = 0;
            currentlyAttacking = false;
            return;
        }
        attackFrameCounter ++;

        if (attackFrameCounter == 1) {
            canAttackFromCoolDown = false;
            int targetX = targetEntity.screenMiddleX();
            int targetY = targetEntity.screenMiddleY();

            int fighterX = screenMiddleX();
            int fighterY = screenMiddleY();

            int deltaX = targetX - fighterX;
            int deltaY = targetY - fighterY;

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX < 0) {
                    attackDirection = "left";
                } else {
                    attackDirection = "right";
                }
            } else {
                if (deltaY < 0) {
                    attackDirection = "up";
                } else {
                    attackDirection = "down";
                }
            }
        }

        if(attackFrameCounter == 10){
            gp.playSE(63);
        }

        if(attackFrameCounter == attackFramePoint1) {
            createAttackInstance();
        }

        if(attackFrameCounter >= attackFramePoint2){
            attackFrameCounter = 0;
            currentlyAttacking = false;
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
            gp.playSE(64);
        }
    }

    public void specialOnDying(){
        if(gp.currentMap == 7){
            if(!gp.progress.act1InteractedObjects[2]){
                if(gp.obj[7][1] != null){
                    gp.obj[7][1].interactable = true;
                    gp.interactObjects.add(gp.obj[7][1]);
                }
            }
        }
    }

    public void dropItem(){
        itemDrop(level, 0, isBoss);
    }
}