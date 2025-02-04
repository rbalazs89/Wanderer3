package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import main.GamePanel;

public class MON_Skeleton extends Fighter {
    int switchTargetCounter = 0;

    public MON_Skeleton(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Skeleton";
        sleepDistance = 4;
        solidArea.x = 2;
        solidArea.y = GamePanel.tileSize / 16;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = 62;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 10 * GamePanel.tileSize;
        attackSoundReferenceNumber = 62;
        sleeping = true;

        //Balance:
        resist[0] = 34;
        damageOnContactValue = 10;
        attackCoolDownValue = 150;
        attackChanceWhenAvailable = 99;
        attackDamage = 25;
        defaultSpeed = 1;
        meleeAttackRange =  (int) (0.95 * GamePanel.tileSize);
        maxLife = 250;
        experienceValue = 100;

        attackFramePoint1 = 50;
        attackFramePoint2 = 100;
        aggroAtThisDistance = 15 * GamePanel.tileSize;
        shouldTryToAttackRange = (int) (2.5 * GamePanel.tileSize);

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        gp.entityImageLoader.matchImagesSkeleton(this);
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
}
