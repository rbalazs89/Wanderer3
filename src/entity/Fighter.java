package entity;

import entity.attacks.melee.MON_MeleeAttack;
import main.GamePanel;
import tool.DamageNumber;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Fighter extends Entity {
    //public int AIBehaviourNumber = 0;
    public int attackStunStrength;
    public int level = 1;
    int armor;
    public int[] resist = new int[4];
    public int contactGraceCounter = 0;
    public boolean contactGraced = false;
    public int contactGraceFrame = 30;
    public boolean drawHpBar = false;
    public int experienceValue = 1;
    public int meleeAttackRange = gp.tileSize ;
    public int aggroAtThisDistance = 3 * gp.tileSize;
    //draw related:
    public int frozenCounter = 0;
    public BufferedImage frozenPicture;
    public boolean frozenImageSaved;

    //AI RELATED:
    public boolean validTileFound = false;
    public int sleepDistance = 8;
    public int checkForEnemiesCounter = 0;
    public int goBackCheckCounter = 0;
    public boolean tempRandomMovement;
    public boolean tempOnPath;
    public boolean canAttackFromCoolDown = true;
    public boolean canAttack = true;
    public int attackFramePoint1 = 30;
    public int attackFramePoint2 = 60;
    public boolean currentlyAttacking = false;
    public int goBackToSpawnMaxDistance = gp.tileSize * 7;
    public int goBackAtThisTargetDistance = gp.tileSize * 7;
    public Entity targetEntity;
    public int goalCol = 0;
    public int goalRow = 0;
    public String attackDirection;
    public int attackCoolDownCounter;
    public int attackCoolDownValue = 100; //melee attack cooldown
    public int tryToKeepThisDistance = 3; // means 3 tileSize
    // 100 = 1% chance every frame when target inside range + not on cd
    // 1 = 100% chance to attack every frame when inside range + not on cd
    public int attackChanceWhenAvailable = 100;
    public int shouldTryToAttackRange = (int) (2.5 * gp.tileSize);
    public static final Color transparentRed = new Color(255, 0, 0, 128);
    public int screenX, screenY;
    public int screenXWOCorrection, screenYWOCorrectionY;
    public int frozenResistance = 0; // higher number -> comes alive faster if frozen

    public BufferedImage[] dying;
    public BufferedImage[] attackUp;
    public BufferedImage[] attackRight;
    public BufferedImage[] attackDown;
    public BufferedImage[] attackLeft;
    public BufferedImage[] hurt;
    public boolean isBoss = false;
    public int itemDropChance = 10;
    /** always called 2 times
     * if its 10 -> 10% chance -> called 2 times -> ~ 1% chance to drop 2 items, ~19% chance to drop 1 item
     *  15 -> 30% chance
     *  20 -> 50% chance -> ... etc
     *  100 -> 90% chance
     */

    /*
    private void drawStunned() {

        int castSprite = (int)(stunnedCounter / (25.0 / 9.0)) + 1;
        if (castSprite > 9) {
            castSprite = 9;
            stunnedCounter = 0;
        }
        switch (direction){
            case "up":{
                image = hurtUp[castSprite - 1];
                break;
      //int framesPerSprite = 60 / 10; // 60 frames divided by 10 sprites
        int deathSprite = Math.min(deathTimeCounter / 6, 9);
        image = dying[deathSprite]; */

    public Fighter(GamePanel gp) {
        super(gp);
        this.gp = gp;
        for(int i = 0; i < 4; i ++){
            resist[i] = 0;
        }
        entityType = 2;
        defaultSpeed = 1;
        speed = defaultSpeed;
        attackSoundReferenceNumber = 33;
    }

    public void update() {
        if(sleeping){
            searchEnemies();
        }
        else {
            reactToDamage();
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
            handleDamageOnContact();
        }
        handleDeathRelated();
    }

    public void draw(Graphics2D g2){

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            if(!isDying && !currentlyAttacking) {
                drawGetWalkingImage();
                drawGetWalkSpriteNumber();
                walkImageCorrection();
            }

            else if(currentlyAttacking && attackDirection != null && !isDying){
                drawAttackImageMelee1();
                attackImageCorrection();
            }

            // DEATH IMAGES:
            if(isDying){
                drawDying();
                dyingImageCorrection();
            }
            if(frozen && !frozenImageSaved){
                frozenPicture = addBlueTint(image);
                frozenImageSaved = true;
            }

            //DRAW IMAGE:
            if(!frozen) {
                g2.drawImage(image, screenX, screenY, null);
            } else {
                g2.drawImage(frozenPicture, screenX, screenY, null);
            }

            // DRAW HP BAR:
            drawHpBar(g2);

            //DRAW HITBOX:
            drawHitBox(g2);
        }
    }

    public void dyingImageCorrection() {

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
                if (75 < i && i <= 100) {
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

        // lose aggro if distance too big from target
        goBackCheckCounter ++;
        if(goBackCheckCounter >=  20) {
            if (targetEntity != null) {
                if (targetEntity.middleDistance(this) > 12 * gp.tileSize) {
                    targetEntity = null;
                    speed = defaultSpeed * 2;
                    isGoingBackToSpawn = true;
                    goalCol = spawnX / gp.tileSize;
                    goalRow = spawnY / gp.tileSize;
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
                goalCol = spawnX / gp.tileSize;
                goalRow = spawnY / gp.tileSize;
                goBackCheckCounter = 0;
            }
        }

        // handle going back
        if(isGoingBackToSpawn){
            searchPath(goalCol, goalRow, true);
        }
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
    }

    public void drawGetWalkingImage(){
        switch (direction) {
            case "up":
                image = walkUp[spriteNum];
                break;
            case "right":
                image = walkRight[spriteNum];
                break;
            case "down":
                image = walkDown[spriteNum];
                break;
            case "left":
                image = walkLeft[spriteNum];
                break;
        }
    }

    public void walkImageCorrection(){

    }


    public void drawHitBox(Graphics2D g2){
        if(gp.visibleHitBox) {
            g2.setColor(transparentRed);
            g2.fillRect(worldX + gp.player.screenX - gp.player.worldX + solidArea.x, worldY + gp.player.screenY - gp.player.worldY + solidArea.y, solidArea.width, solidArea.height);
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

    public void drawAttackImageMelee1(){
        attackSpriteNum = Math.min(attackFrameCounter / (attackFramePoint2/9), 8);

        switch (attackDirection) {
            case "up":
                image = attackUp[attackSpriteNum];
                break;
            case "right":
                image = attackRight[attackSpriteNum];
                break;
            case "down":
                image = attackDown[attackSpriteNum];
                break;
            case "left":
                image = attackLeft[attackSpriteNum];
                break;
        }
    }
    public void attackImageCorrection(){
        /*
        switch (attackDirection) {
            case "up":
                screenX = screenX - (image.getWidth()/2 - gp.tileSize/2);
                screenY = screenY - (image.getHeight() - gp.tileSize);
                break;
            case "right":
                screenY = screenY - (image.getHeight()/2 - gp.tileSize/2);
                break;
            case "down":
                screenX = screenX - (image.getWidth()/2 - gp.tileSize/2);
                break;
            case "left":
                screenX = screenX - (image.getWidth() - gp.tileSize);
                screenY = screenY - (image.getHeight()/2 - gp.tileSize/2);
                break;
        }*/
    }

    public void drawDying(){
        //int framesPerSprite = 60 / 10; // 60 frames divided by 10 sprites
        int deathSprite = Math.min(deathTimeCounter / 6, 8);
        image = dying[deathSprite];
    }

    public void drawHpBar(Graphics2D g2){
        if(drawHpBar) {
            int hpBarX = worldX - gp.player.worldX + gp.player.screenX;
            int hpBarY = worldY - gp.player.worldY + gp.player.screenY;
            double oneScale = (double) gp.tileSize / maxLife;
            double hpBarValue = oneScale * life;
            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(hpBarX - 1, hpBarY - 1, gp.tileSize + 2, 12);
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(hpBarX, hpBarY, (int) hpBarValue, 10);
        }
    }

    public void handleFrozen() {
        frozenCounter = frozenCounter - 1 - frozenResistance;
        if (frozenCounter <= 0) {
            frozen = false;
            frozenImageSaved = false;
            frozenCounter = 0;
        }
    }

    public void setActionAI(){
        //TODO allow target change if multiple targets available

        if(targetPathFollowed){
            setActionAIPath();
        } else {
            randomMovement();

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
                    goalCol = spawnX / gp.tileSize;
                    goalRow = spawnY / gp.tileSize;
                }
            }

            if(isGoingBackToSpawn){
                searchPath(goalCol, goalRow, true);
            }
        }
    }

    public void findPositionComparedToTarget() {
        goalCol = targetEntity.worldMiddleX() / gp.tileSize;
        goalRow = targetEntity.worldMiddleY() / gp.tileSize;
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

        //tbd handle which phase can be stunned/knockbacked etc
        if(attackFrameCounter == attackFramePoint1) {
                createAttackInstance();
            }

        if(attackFrameCounter >= attackFramePoint2){
            attackFrameCounter = 0;
            currentlyAttacking = false;
        }
    }

    public void createAttackInstance() {
        new MON_MeleeAttack(gp, attackFramePoint2 - attackFramePoint1, attackDirection, meleeAttackRange, this);
    }

    public void monsterDamagesPlayerOnContact(){
        if(!contactGraced){
            gp.player.life = gp.player.life - damageOnContactValue;
            new DamageNumber(damageOnContactValue, this, gp.player, true, gp);
            gp.playSE(45);
            contactGraced = true;
        }
    }

    public void handleStunned() {
        stunStrength--;
        if(stunStrength <= 0){
            stunStrength = 0;
            stunned = false;
        }
    }

    public void attackCoolDown() {
        if(!canAttackFromCoolDown){
            attackCoolDownCounter ++;
            if(attackCoolDownCounter >= attackCoolDownValue){
                canAttackFromCoolDown = true;
                attackCoolDownCounter = 0;
            }
        }
    }

    public Entity findClosestFightingEnemy(){
        Entity returnThis = null;
        int tempDistance = aggroAtThisDistance;
        for (int i = 0; i < gp.allFightingEntities.size(); i++) {
            Entity currentEntity = gp.allFightingEntities.get(i);
            if(isHostile(this, currentEntity)){
                int distance = currentEntity.middleDistance(this);
                if(distance < tempDistance){
                    tempDistance = distance;
                    returnThis = currentEntity;
                }
            }
        }
        return returnThis;
    }

    public void actionToDamageAI(Entity damageReceivedFrom) {

    }

    public void handleDamageOnContact(){
        if(gp.cChecker.checkPlayer(this)){
            if(!contactGraced){
                gp.player.life = gp.player.life - damageOnContactValue;
                if(damageOnContactValue > 0){
                    new DamageNumber(damageOnContactValue, this, gp.player, true, gp);
                }
                if(!isDying){
                    gp.playSE(45);
                }
                contactGraced = true;
            }
        }

        if (contactGraced && contactGraceCounter < contactGraceFrame) {
            contactGraceCounter++;
        }
        if (contactGraceCounter >= contactGraceFrame) {
            contactGraceCounter = 0;
            contactGraced = false;
        }
    }

    public void handleDeathRelated(){
        if(life <= 0){
            isDying = true;
        }

        if(isDying){
            if(deathTimeCounter == 1){
                specialOnDeath();
                playDeathSound();
                dropItem();
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

    public void handleCollisionAndMovement(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //gp.cChecker.checkEntity(this,gp.npc); // npc no collision anyway
        //gp.cChecker.checkEntity(this, gp.fighters); // check monster with eachother
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
    }

    public void winLife(int value){
        if (gp.gameState == gp.playState){
            life = Math.min(value + life, maxLife);
        }
    }

    public void death(){

    }


    public void setFrozen(int freezeStrength){
        frozenCounter = frozenCounter + freezeStrength;
        frozen = true;
    }

    public void loseLife(int value, int type, Entity originEntity, Entity sufferingEntity, boolean isContactDamage,
                         GamePanel gp) {
        // 0: physical
        // 1: fire
        // 2: cold
        // 3: lightning
        // 4: pure

        if (type == 0){
            double resistPercentage = 1 - (double)(resist[0]) / 100;
            value = (int)Math.ceil((double)value * resistPercentage - armor);
        }

        else if (value != 4){
            double resistPercentage = 1 - (double)(resist[type]) / 100;
            value = (int)Math.ceil((double)value * resistPercentage);
        }

        if(gp.gameState == gp.playState) {
            value = Math.max(1, value);
            life = life - value;
            new DamageNumber(value, originEntity, sufferingEntity, false, gp);
            if (life < 0) {
                life = 0;
            }
        }
    }

    public void playDeathSound() {

    }

    public void receiveStun(int strength){
        if(!currentlyAttacking){
            stunned = true;
            stunStrength = strength;
        }
    }

    public void dropItem(){
        Random random = new Random();
        int chance1 = random.nextInt(itemDropChance) + 1;
        if(chance1 >= 10){
            itemDrop(level, 0, isBoss);
        }

        int chance2 = random.nextInt(itemDropChance) + 1;
        if(chance2 >= 10){
            itemDrop(level, 0, isBoss);
        }
    }

    public void reactToDamage(){

    }

    public void setAI(int AINumber){
        aiBehaviourNumber = AINumber;
    }

    public void specialOnDeath(){

    }

}
