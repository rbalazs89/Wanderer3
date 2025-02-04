package entity.allied;

import entity.Entity;
import entity.Fighter;
import entity.attacks.BRO_MeleeAttack;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class A_Brother extends Fighter {
    int deathRegenCounter = 0;
    int switchTargetCounter = 0;

    public A_Brother(GamePanel gp){
        super(gp);
        entityType = 5;
        drawHpBar = true;
        collisionEntity = false;
        name = "bro";
        solidArea.x = 12;
        solidArea.y = 10;
        solidArea.width = 40;
        solidArea.height = 54;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        goBackToSpawnMaxDistance = 10 * GamePanel.tileSize;
        sleepDistance = 4;
        sleeping = true;
        damageOnContactValue = 0;
        getImages();
        headDialogueString = "Let us clear this cellar. \n\nDo not worry about me in the fight, I won't die.";

        //Balance:
        attackCoolDownValue = 110;
        attackChanceWhenAvailable = 100;
        attackDamage = 25;
        defaultSpeed = 1;
        meleeAttackRange =  (int) (1.3 * GamePanel.tileSize);
        maxLife = 200;
        experienceValue = 100;
        attackStunStrength = 20;

        attackFramePoint1 = 30;
        attackFramePoint2 = 60;
        aggroAtThisDistance = 4 * GamePanel.tileSize;
        shouldTryToAttackRange = (int) (2.5 * GamePanel.tileSize);

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    //bro cannot get stunned
    public void receiveStun(int strength) {

    }

    public void getImages(){
        walkUp = new BufferedImage[9];
        walkRight = new BufferedImage[9];
        walkDown = new BufferedImage[9];
        walkLeft = new BufferedImage[9];
        attackUp = new BufferedImage[9];
        attackRight = new BufferedImage[9];
        attackDown = new BufferedImage[9];
        attackLeft = new BufferedImage[9];
        dying = new BufferedImage[9];


        BufferedImage tempImage = setupImage("/entity/allied/bro/brosheet");
        for (int i = 0; i < 9; i++) {
            walkUp[i] = setupSheet(tempImage, i * 128,0,128, 128, 96, 96);
            walkRight[i] = setupSheet(tempImage,  i * 128,128,128, 128, 96, 96);
            walkDown[i] = setupSheet(tempImage,  i * 128,256,128, 128, 96,96);
            walkLeft[8-i] = setupSheet(tempImage,  i * 128,384,128, 128, 96,96);
            attackUp[i] = setupSheet(tempImage,  i * 128,512,128, 128, 96,96);
            attackRight[i] = setupSheet(tempImage,  i * 128,640,128, 128, 96,96);
            attackDown[i] = setupSheet(tempImage,  i * 128,768,128, 128, 96,96);
            attackLeft[8-i] = setupSheet(tempImage,  i * 128,896,128, 128,96,96);
            dying[i] = setupSheet(tempImage,  i * 128,1024,128, 128, 96, 96);
        }
    }



    public void walkImageCorrection(){
        switch (direction) {
            case "up" -> {
                screenX = screenX - 16;
                screenY = screenY - 10;
            }
            case "right" -> {
                screenX = screenX - 5;
                screenY = screenY - 16;
            }
            case "down" -> {
                screenX = screenX - 16;
                screenY = screenY - 18;
            }
            case "left" -> {
                screenX = screenX - 30;
                screenY = screenY - 20;
            }
        }
    }
    public void attackImageCorrection(){
        switch (attackDirection) {
            case "up":
                screenX = screenX - 10;
                screenY = screenY - 35;
                break;
            case "right":
                break;
            case "down":
                screenX = screenX - 10;
                break;
            case "left":
                screenX = screenX - 30;
                screenY = screenY - 11;
                break;
        }
    }



    public void createAttackInstance() {
        new BRO_MeleeAttack(gp, attackDirection, meleeAttackRange, this);
    }

    public void draw(Graphics2D g2){
        super.draw(g2);
        //this.g2 = g2;
        if(actionWhenNear1){
            nearHeadDialogue(g2);
        }
    }

    public void handleDeathRelated(){
        if(life <= 0){
            isDying = true;
        }

        if(isDying){
            if(deathTimeCounter == 1){
                entityType = 10;
                speed = 0;
                defaultSpeed = 0;
                currentlyAttacking = false;
                canAttackFromCoolDown = false;
                canAttack = false;
                damageOnContactValue = 0;
            }

            deathTimeCounter++;
            deathRegenCounter++;
            if(deathRegenCounter > 10){
                life ++;
                deathRegenCounter = 0;
            }
            if(life >= maxLife){
                revive();
            }
        }
    }

    public void revive(){
        speed = 2;
        defaultSpeed = 2;
        canAttack = true;
        canAttackFromCoolDown = true;
        entityType = 5;
        isDying = false;
        attackFrameCounter = 0;
        deathTimeCounter = 0;
        targetPathFollowed = false;
    }

    public void update() {
        actionNearCounter();

        if(sleeping){
            searchEnemies();
        }
        else {
            attackCoolDown();
            if (!frozen && !stunned) {
                if (currentlyAttacking) {
                    attacking();
                }
                if (!currentlyAttacking) {
                    setActionAI(); // BIG AI
                    handleCollisionAndMovement(); // to do maybe separate collision and movement if I implement knockback?
                }
            }
            if (frozen) {
                handleFrozen();
            }
            if (stunned){
                handleStunned();
            }
            //handleDamageOnContact();
        }
        handleDeathRelated();
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

        if(middleDistance(gp.player) < (int)(1.5 * GamePanel.tileSize)) {
            sleeping = false;
            actionWhenNear1 = true;
        }
    }

    public void setActionAI(){
        //TODO allow target change if multiple targets available

        if(targetPathFollowed){
            setActionAIPath();
        } else {
            //randomMovement();
            goalCol = gp.player.worldMiddleX() / gp.tileSize;
            goalRow = gp.player.worldMiddleY() / gp.tileSize;

            searchPath(goalCol, goalRow, true);

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
    }

    public void actionNearCounter(){
        if(actionWhenNear1){
            actionWhenNearCounter++;
            if(actionWhenNearCounter > 500) {
                actionWhenNearCounter = 0;
                actionWhenNear1 = false;
                speed = 2; defaultSpeed = 2;
            }
        }
    }
}
