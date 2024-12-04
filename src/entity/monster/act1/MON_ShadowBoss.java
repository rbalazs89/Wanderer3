package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import entity.attacks.MON_DaggerGlobe;
import entity.attacks.MON_DragonHead;
import entity.attacks.melee.MON_SkeletonKingMeleeAttack;
import entity.attacks.melee.SHADOW_MeleeAttack1;
import entity.attacks.projectile.SHADOW_Projectile;
import entity.attacks.vfx.ColdAura;
import entity.attacks.vfx.FireAura;
import entity.attacks.vfx.JumpImpactSmoke;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
        //int framesPerSprite = 60 / 10; // 60 frames divided by 10 sprites
        int deathSprite = Math.min(deathTimeCounter / 6, 8);
        image = dying[deathSprite];
 */

public class MON_ShadowBoss extends Fighter {
    private BufferedImage currentVFX;
    public int attackDirection;
    private boolean castingDagger = false;
    private boolean castingDragon = false;
    private boolean castingProjectile = false;
    private boolean meleeAttack1 = false;
    private boolean meleeAttack2 = false;
    private boolean canCastOrAttack = true;

    private boolean fullMad = false;
    private int speedRandomizerCounter = 0;
    private static final int daggersCooldown = 2100;
    private static final int meleeAttacksCoolDown = 480;
    private static final int spellCastingCoolDown = 480;
    private int daggersCooldownCounter = 0;
    private boolean daggersIsOnCooldown = false;
    private int screamCounter = 0;
    private boolean screaming = false;
    private boolean isMeleeAttackOnCooldown = false;
    private boolean isSpellCastingOnCooldown = false;
    private int meleeAttackCoolDownCounter = 0;
    private int spellCastingCoolDownCounter = 0;
    private int actionLockCounter2 = 0;
    private int actionLockCounter3 = 0;
    private int previousActionLockCounter3 = 0;
    private static final int actionLock3Point = 40;
    boolean steppedIntoFreeMovement = false;
    private boolean shouldTryToDoFreeMovement = true;
    private int hpRegenCounter = 0;
    private static final int hpRegenPoint = 60;


    public MON_ShadowBoss(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Wraith boss";
        solidArea.x = gp.tileSize / 16 * 1;
        solidArea.y = gp.tileSize / 16 * 4;
        solidArea.width = gp.tileSize / 16 * 14;
        solidArea.height = gp.tileSize / 16 * 12;

        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        movementSpritesNumber = 17;
        getImages();
        sleepDistance = 4;
        sleeping =  false;

        //
        tryToKeepThisDistance = 3; // tileSize // here only used when casting

        //Balance:
        level = 10;
        damageOnContactValue = 1;
        attackDamage = 35;
        defaultSpeed = 0;
        meleeAttackRange =  (int) (0.85 * gp.tileSize);
        maxLife = 850;
        experienceValue = 100;
        aggroAtThisDistance = 6 * gp.tileSize;
        shouldTryToAttackRange = (int) (3 * gp.tileSize);
        frozenResistance = 1;
        resist[1] = 60;
        resist[2] = 55;
        resist[3] = 30;

        ///
        life = maxLife;
        speed = defaultSpeed;
        direction = "down";
    }

    public void getImages(){
        attackUp = new BufferedImage[9];
        attackRight = new BufferedImage[9];
        attackDown = new BufferedImage[9];
        attackLeft = new BufferedImage[9];
        dying = new BufferedImage[17];
        walkUp = new BufferedImage[17];
        walkRight = new BufferedImage[17];
        walkDown = new BufferedImage[17];
        walkLeft = new BufferedImage[17];

        BufferedImage tempImage = setupImage("/entity/monster/act1/shadowboss/attackSheet");

        for (int i = 0; i < 9; i++) {
            attackUp[i] = setupSheet(tempImage, 128*i,0,128,128);
            attackRight[i] = setupSheet(tempImage, 128*i,128,128,128);
            attackDown[i] = setupSheet(tempImage, 128*i,128 * 2,128,128);
            attackLeft[8-i] = setupSheet(tempImage, 128*i,128 * 3,128,128);
        }

        tempImage = setupImage("/entity/monster/act1/shadowboss/movementSheet");
        for (int i = 0; i < 17; i++) {
            dying[i] = setupSheet(tempImage, 128 * i,0,128,128);
            walkUp[i] = setupSheet(tempImage, 128 * i,128,128,128);
            walkRight[i] = setupSheet(tempImage, 128 * i,128 * 2,128,128);
            walkDown[i] = setupSheet(tempImage, 128 * i,128 * 3,128,128);
            walkLeft[i] = setupSheet(tempImage, 128 * i,128 * 4,128,128);
        }
    }

    public void update() {
        setSpeedForFight();
        if (!frozen) {
            if(!isDying){
                if(!screaming && !meleeAttack2 && !meleeAttack1){
                    randomMovement();
                }
                searchTargetEntity();

                if(canAttack && targetEntity != null){
                    determineAttackIfAny();
                }
                executeAttackIfAny();
                if(!meleeAttack1 && !meleeAttack2){
                    findPosition();
                }
                handleCollisionAndMovement();
            }
        } else {
            handleFrozen();
        }
        handleDamageOnContact();
        handleDeathRelated();
        refresh();
        printStatus();
    }

    private void findPosition() {
        //during CASTING:
        if(castingDagger || castingDragon || castingProjectile){
            actionLockCounter2 ++; // resets at the end of handlecasting
            //checks if validTileFound
            if(actionLockCounter2 == 1){
                validTileFound = false;
                findPositionComparedToTargetIfCasting();
                if(validTileFound){
                    goalCol = spawnX / gp.tileSize;
                    goalRow = spawnY / gp.tileSize;
                }
            }
            if(validTileFound) {
                searchPath(goalCol, goalRow, false);
            }
        }
        //During melee attacking movement and direction is fixed
        //else should follow the player with the random speed given in previous
        else if(!validTileFound) {
            actionLockCounter3++;
            if(actionLockCounter3 == 1){
                int tempInt = random.nextInt(3);
                //System.out.println("tempint" + tempInt);
                if (tempInt != 0 && shouldTryToDoFreeMovement && targetEntity != null){
                    tempOnPath = true;
                    goalCol = targetEntity.worldMiddleX() / gp.tileSize;
                    goalRow = targetEntity.worldMiddleY() / gp.tileSize;
                }
            }
            if(tempOnPath && actionLockCounter3 <= actionLock3Point){
                searchPath(goalCol, goalRow, false);
            }
            if (actionLockCounter3 > actionLock3Point){
                actionLockCounter3 = 0;
            }
        }
    }
    public void decideFreeMovement(){
        if(actionLockCounter3 == previousActionLockCounter3){
            shouldTryToDoFreeMovement = true;
            actionLockCounter3 = 0;
            tempOnPath = false;
        }
        if(!castingProjectile && !castingDragon && !castingDagger){
            validTileFound = false;
        }
        previousActionLockCounter3 = actionLockCounter3;
    }

    public void attackCoolDown() {
        if(daggersIsOnCooldown){
            daggersCooldownCounter++;
            if(daggersCooldownCounter > daggersCooldown){
                daggersCooldownCounter = 0;
                daggersIsOnCooldown = false;
            }
        }
        if(isMeleeAttackOnCooldown){
            meleeAttackCoolDownCounter++;
            if(meleeAttackCoolDownCounter > meleeAttacksCoolDown){
                meleeAttackCoolDownCounter = 0;
                isMeleeAttackOnCooldown = false;
            }
        }
        if(isSpellCastingOnCooldown){
            spellCastingCoolDownCounter++;
            if(spellCastingCoolDownCounter > spellCastingCoolDown){
                spellCastingCoolDownCounter = 0;
                isSpellCastingOnCooldown = false;
            }
        }
    }

    private void executeAttackIfAny() {
        if(screaming){
            handleScreaming();
        }
        else if(meleeAttack1) {
            handleMeleeAttack1();
        } else if(meleeAttack2) {
            handleMeleeAttack2();
        } else if(castingDragon) {
            handleCastingDragon();
        } else if(castingProjectile) {
            handleCastingProjectile();
        } else if(castingDagger) {
            handleCastingDaggers();
        }
    }

    private void handleScreaming() {
        canAttack = false;
        screamCounter ++;

        if(screamCounter == 1){
            gp.playSE(72);
            currentVFX = angryVFX();
        }

        if(screamCounter > 120){
            meleeAttack1 = true;
            screaming = false;
            screamCounter = 0;
        }
    }

    private void searchTargetEntity() {
        checkForEnemiesCounter++;
        Entity closestFightingEnemy = null;
        if(checkForEnemiesCounter > 20){
            closestFightingEnemy = findClosestFightingEnemy();
            checkForEnemiesCounter = 0;
        }
        if (closestFightingEnemy != null) {
            targetEntity = closestFightingEnemy;
        }
    }

    private void determineAttackIfAny() {

        if(middleDistance(targetEntity) < 10 * gp.tileSize && !daggersIsOnCooldown){
            castingDagger = true;
            daggersIsOnCooldown = true;
            canAttack = false;
            attackDirection = decideMeleeAttackDirection();
            return;
        }

        if(!isMeleeAttackOnCooldown){
            int tempRandom = random.nextInt(100);
            if(tempRandom == 0){
                if(middleDistance(targetEntity) < 3 * gp.tileSize){
                    screaming = true;
                    attackDirection = decideMeleeAttackDirection();
                    canAttack = false;
                    isMeleeAttackOnCooldown = true;
                    return;
                }
            }
            else if (tempRandom == 1){
                if(middleDistance(targetEntity) < 3 * gp.tileSize) {
                    meleeAttack2 = true;
                    canAttack = false;
                    attackDirection = decideMeleeAttackDirection();
                    isMeleeAttackOnCooldown = true;
                    return;
                }
            }
        }

        if(!isSpellCastingOnCooldown){
            int tempRandom = random.nextInt(100);
            if(tempRandom == 0) {
                if (middleDistance(targetEntity) < 8 * gp.tileSize) {
                    castingProjectile = true;
                    canAttack = false;
                    attackDirection = decideMeleeAttackDirection();
                    isSpellCastingOnCooldown = true;
                    return;
                }
            }
            if(tempRandom == 1){
                if(middleDistance(targetEntity) < 8 * gp.tileSize){
                    castingDragon = true;
                    canAttack = false;
                    attackDirection = decideMeleeAttackDirection();
                    isSpellCastingOnCooldown = true;
                }
            }
        }
    }

    public void setSpeedForFight() {
        speedRandomizerCounter ++;
        if(speedRandomizerCounter > 130){
            speed = random.nextInt(3) + 2;
            speedRandomizerCounter = 0;
        }

        if(castingDragon || castingProjectile || castingDagger) {
            speed = 1;
            speedRandomizerCounter = 131;
        }

        if(screaming || meleeAttack1 || meleeAttack2 || isDying){
            speed = 0;
            speedRandomizerCounter = 131;
        }
    }

    private void handleCastingDaggers() {
        attackFrameCounter++;

        if(attackFrameCounter == 1){
            gp.playSE(70);
            new MON_DaggerGlobe(gp, 0, - 60, this);
        } else if (attackFrameCounter == 2){
            new MON_DaggerGlobe(gp, - 30, - 30, this);
        } else if (attackFrameCounter == 3){
            new MON_DaggerGlobe(gp, + 30, - 30, this);
        }

        //set up at 80 frame, has to match the daggerGlobe counter
        else if(attackFrameCounter >= 100){
            attackFrameCounter = 0;
            canAttack = true;
            castingDagger = false;
            actionLockCounter2 = 0; // to govern pathfind
        }
    }

    private void handleCastingProjectile() {
        attackFrameCounter++;
        if(attackFrameCounter == 1){
            new ColdAura(gp,this);
        }

        if(attackFrameCounter == 120){
            int castThisMany = random.nextInt(2) + 1;
            for (int i = 0; i <castThisMany; i++) {
                new SHADOW_Projectile(gp,this,targetEntity, 4);
            }
        }

        if(attackFrameCounter == 140){
            castingProjectile = false;
            canAttack = true;
            attackFrameCounter = 0;
            actionLockCounter2 = 0; // to govern pathfind
        }
    }

    private void handleCastingDragon() {
        attackFrameCounter++;
        if(attackFrameCounter == 1){
            new FireAura(gp,this);
        }

        if(attackFrameCounter == 80){
            new MON_DragonHead(gp,targetEntity.screenX() - 32, targetEntity.screenY() - 32, this);
        }

        if(attackFrameCounter == 100){
            castingDragon = false;
            canAttack = true;
            actionLockCounter2 = 0; // to govern path
            attackFrameCounter = 0;
        }
    }

    private void handleMeleeAttack2() {
        attackFrameCounter++;
        if(attackFrameCounter == 1){
            new JumpImpactSmoke(gp,this);
        }
        if(attackFrameCounter < 10){
            speed = 9;
            switch (attackDirection){
                case 0:{
                    direction = "down";
                    break;
                }
                case 1:{
                    direction = "left";
                    break;
                }
                case 2:{
                    direction = "up";
                    break;
                }
                case 3:{
                    direction = "right";
                    break;
                }
            }
        }
        //original 10...30
        if(attackFrameCounter >= 10 && attackFrameCounter < 60){
            speed = 3;
            switch (attackDirection){
                case 0:{
                    direction = "up";
                    break;
                }
                case 1:{
                    direction = "right";
                    break;
                }
                case 2:{
                    direction = "down";
                    break;
                }
                case 3:{
                    direction = "left";
                    break;
                }
            }
        }

        if(attackFrameCounter == 20){
            gp.playSE(63);
        }

        else if(attackFrameCounter == 60){
            new MON_SkeletonKingMeleeAttack(gp,50,direction, 100, this);
        }

        else if(attackFrameCounter == 80){
            canAttack = true;
            meleeAttack2 = false;
            attackFrameCounter = 0;
        }
    }

    private void handleMeleeAttack1() {
        attackFrameCounter ++;
        int point = 16;
        if (attackFrameCounter == point) {
            new SHADOW_MeleeAttack1(gp, attackDirection, 70, this);
        } else if (attackFrameCounter == point * 2) {
            attackDirection = (attackDirection + 1) % 4;
        } else if (attackFrameCounter == point * 3) {
            new SHADOW_MeleeAttack1(gp, attackDirection, 70, this);
        } else if (attackFrameCounter == point * 4) {
            attackDirection = (attackDirection + 1) % 4;
        } else if (attackFrameCounter == point * 5) {
            new SHADOW_MeleeAttack1(gp, attackDirection, 70, this);
        } else if (attackFrameCounter == point * 6) {
            attackDirection = (attackDirection + 1) % 4;
        } else if (attackFrameCounter == point * 7) {
            new SHADOW_MeleeAttack1(gp, attackDirection, 70, this);
        } else if (attackFrameCounter == point * 8) {
            attackFrameCounter = 0;
            canAttack = true;
            meleeAttack1 = false;
        }
    }


    public void draw(Graphics2D g2){
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            if(canAttack) {
                drawGetWalkingImage();
                drawGetWalkSpriteNumber();
                walkImageCorrection();
            } else {
                if(screaming){
                    drawScreaming(g2);
                    attackImageCorrection();
                }
                else if(meleeAttack1) {
                    drawAttackImageMelee1();
                    attackImageCorrection();
                } else if(castingDragon){
                    drawAttackImageCastingDragon();
                    attackImageCorrection();
                } else if(castingProjectile){
                    drawAttackImageCastingProjectile();
                    attackImageCorrection();
                } else if(castingDagger){
                    drawAttackImageCastinDagger();
                    attackImageCorrection();
                } else if (meleeAttack2){
                    drawAttackImageMelee2();
                    attackImageCorrection();
                }
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

            if(image == null){
                System.out.println("scream" + attackFrameCounter + " " + attackSpriteNum);
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

    private void drawAttackImageCastinDagger() {
        /**
         attackSpriteNum = Math.min((attackFrameCounter) / ( maxattackframes/ 9 ), 8);
         attack instance creation at 80th frame
         finish attacking at 100th frame
         casting animation number = 2;
         max animation sprites = 9;
         */
        if(attackFrameCounter == 0 || attackFrameCounter == 1){
            attackSpriteNum = 0;
        } else if(attackFrameCounter == 7){
            attackSpriteNum = 1;
        } else if (attackFrameCounter == 14){
            attackSpriteNum = 2;
        } else if(attackFrameCounter > 80){
            attackSpriteNum = Math.min((((attackFrameCounter - 80) / ((100 - 80) / 9)) + 3), 8);
        }

        switch (attackDirection){
            case 0:
                image = attackUp[attackSpriteNum];
                break;
            case 1:
                image = attackRight[attackSpriteNum];
                break;
            case 2:
                image = attackDown[attackSpriteNum];
                break;
            case 3:
                image = attackLeft[attackSpriteNum];
                break;
        }
    }

    private void drawAttackImageCastingProjectile() {
        /**
         attackSpriteNum = Math.min((attackFrameCounter) / ( maxattackframes/ 9 ), 8);
         attack instance creation at 120th frame
         finish attacking at 140th frame
         casting animation number = 2;
         max animation sprites = 9;
         attackSpriteNum = Math.min((((attackFrameCounter - 120) / ((140 - 120) / 9)) + 3), 8);
         */
        if(attackFrameCounter == 0 || attackFrameCounter == 1){
            attackSpriteNum = 0;
        } else if(attackFrameCounter == 7){
            attackSpriteNum = 1;
        } else if (attackFrameCounter == 14){
            attackSpriteNum = 2;
        } else if(attackFrameCounter > 120){
            attackSpriteNum = Math.min((((attackFrameCounter - 120) / ((140 - 120) / 9)) + 3), 8);
        }

        switch (attackDirection){
            case 0:
                image = attackUp[attackSpriteNum];
                break;
            case 1:
                image = attackRight[attackSpriteNum];
                break;
            case 2:
                image = attackDown[attackSpriteNum];
                break;
            case 3:
                image = attackLeft[attackSpriteNum];
                break;
        }
    }

    private void drawAttackImageCastingDragon() {
        /**
        attackSpriteNum = Math.min((attackFrameCounter) / ( maxattackframes/ 9 ), 8);
        attack instance creation at 80th frame
         finish attacking at 110th frame
         casting animation number = 2;
         max animation sprites = 9;
         */
        if(attackFrameCounter == 0 || attackFrameCounter == 1){
            attackSpriteNum = 0;
        } else if(attackFrameCounter == 7){
            attackSpriteNum = 1;
        } else if (attackFrameCounter == 14){
            attackSpriteNum = 2;
        } else if(attackFrameCounter > 80){
            attackSpriteNum = Math.min((((attackFrameCounter - 80) / ((110 - 80) / 9)) + 3), 8);
        }


        switch (attackDirection){
            case 0:
                image = attackUp[attackSpriteNum];
                break;
            case 1:
                image = attackRight[attackSpriteNum];
                break;
            case 2:
                image = attackDown[attackSpriteNum];
                break;
            case 3:
                image = attackLeft[attackSpriteNum];
                break;
        }
    }

    private void drawScreaming(Graphics2D g2) {
        switch (attackDirection){
            case 0:
                image = attackUp[0];
                break;
            case 1:
                image = attackRight[0];
                break;
            case 2:
                image = attackDown[0];
                break;
            case 3:
                image = attackLeft[0];
                break;
        }
        g2.drawImage(currentVFX, screenX - 20, screenY - 30, null);
        screenX = screenX + random.nextInt(5) - 10;
        screenY = screenY + random.nextInt(5) - 10;
    }

    public void walkImageCorrection(){
        switch (direction) {
            case "up":
                screenX -= 9;
                screenY -= 25;
                break;

            case "right":
                screenX -= 40;
                screenY -= 10;
                break;

            case "down":
                screenX -= 58;
                screenY -= 34;
                break;

            case "left":
                screenX -= 28;
                screenY -= 10;
                break;
        }
    }


    public void drawAttackImageMelee1(){
        attackSpriteNum = Math.min((attackFrameCounter%60) / (60/9), 8);
        switch (attackDirection) {
            case 0:
                image = attackUp[attackSpriteNum];
                break;
            case 1:
                image = attackRight[attackSpriteNum];
                break;
            case 2:
                image = attackDown[attackSpriteNum];
                break;
            case 3:
                image = attackLeft[attackSpriteNum];
                break;
        }
    }

    public void drawAttackImageMelee2(){
        /**first 10 frames move back
         * until 30th frame move forward
         * at 60th frame attack
         * 80th frame attack end
         * reference: attackSpriteNum = Math.min((((attackFrameCounter - 80) / ((100 - 80) / 9)) + 3), 8);
         * */

        if(attackFrameCounter < 40){
            attackSpriteNum = 0;
        } else {
            attackSpriteNum = Math.min((attackFrameCounter - 40) / (40 / 9), 8);
        }

        switch (attackDirection) {
            case 0:
                image = attackUp[attackSpriteNum];
                break;
            case 1:
                image = attackRight[attackSpriteNum];
                break;
            case 2:
                image = attackDown[attackSpriteNum];
                break;
            case 3:
                image = attackLeft[attackSpriteNum];
                break;
        }
    }
    public void attackImageCorrection(){
        switch (attackDirection) {
            case 0:
                screenX -= 10;
                screenY -= 30;
                break;

            case 1:
                screenX -= 20;
                screenY -= 34;
                break;

            case 2:
                screenX -= 41;
                screenY -= 15;
                break;

            case 3:
                screenX -= 50;
                screenY -= 36;
                break;
        }
    }

    public void drawDying(){
        //int framesPerSprite = 60 / 10; // 60 frames divided by 10 sprites
        int deathSprite = Math.min(deathTimeCounter / (180/17), 16);
        image = dying[deathSprite];
    }

    public void handleDeathRelated(){
        if(life <= 0){
            isDying = true;
        }
        if(isDying){
            gp.music.fadeOutMusic();
            if(deathTimeCounter == 1){
                if(gp.obj[11][1] != null){
                    gp.obj[11][1].interactByOtherObject();
                }
                playDeathSound();
                dropItem();
                attackFrameCounter = 0;
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
            if (deathTimeCounter > 300){
                gp.music.fadeOutMusicChangeBack();
                for (int i = 0; i < gp.fighters[1].length; i++) {
                    if(gp.fighters[gp.currentMap][i] == this){
                        gp.fighters[gp.currentMap][i] = null;
                        break;
                    }
                }
            }
        }
    }

    // helpers methods:
    public int decideMeleeAttackDirection(){
        int targetX = targetEntity.screenMiddleX();
        int targetY = targetEntity.screenMiddleY();

        int fighterX = screenMiddleX();
        int fighterY = screenMiddleY();

        int deltaX = targetX - fighterX;
        int deltaY = targetY - fighterY;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX < 0) {
                return 3;
            } else {
                return 1;
            }
        } else {
            if (deltaY < 0) {
                return 0;
            } else {
                return 2;
            }
        }
    }

    public BufferedImage angryVFX() {
        int width = 200;
        int height = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        Color[] colors = {Color.RED, Color.WHITE};

        int numTriangles = 20;

        double angleRange = Math.toRadians(140);
        double startAngle = Math.toRadians(200);

        double angleStep = angleRange / numTriangles;

        int base = 15;
        int triangleHeight = 80;
        int innerRadius = 40;

        for (int i = 0; i < numTriangles; i++) {
            double angle = startAngle + i * angleStep;

            int x1 = (int) (width / 2 + Math.cos(angle) * innerRadius);
            int y1 = (int) (height / 2 + Math.sin(angle) * innerRadius);
            int x2 = (int) (x1 + Math.cos(angle - angleStep / 2) * base);
            int y2 = (int) (y1 + Math.sin(angle - angleStep / 2) * base);
            int x3 = (int) (x1 + Math.cos(angle + angleStep / 2) * base);
            int y3 = (int) (y1 + Math.sin(angle + angleStep / 2) * base);
            int x4 = (int) (x1 + Math.cos(angle) * triangleHeight);
            int y4 = (int) (y1 + Math.sin(angle) * triangleHeight);

            // Choose a color
            g2.setColor(colors[i % colors.length]);

            // Draw the triangle
            int[] xPoints = {x1, x2, x4, x3};
            int[] yPoints = {y1, y2, y4, y3};
            g2.fillPolygon(xPoints, yPoints, 4);
        }

        int newWidth = 100;
        int newHeight = 100;
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);

        g2.dispose();
        return scaledImage;
    }

    public void printStatus(){
        /*if(targetEntity != null){
            System.out.println(targetEntity.name);
        }
         */
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

    public void dropItem(){
        itemDrop(level, 0, isBoss);
        itemDrop(level, 0, isBoss);
    }

    //HANDLE POSITION COMPARED TO PLAYER:

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

    public void findPositionComparedToTargetIfCasting() {

        int targetCol = targetEntity.worldMiddleX() / gp.tileSize;
        int targetRow = targetEntity.worldMiddleY() / gp.tileSize;

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
                } else if ( i == 2){
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
                } else if ( i == 2){
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

                } else if ( i == 2){
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
                } else if ( i == 2){
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

    public void refresh(){
        attackCoolDown();
        HPRegen();
        decideFreeMovement();
    }

    public void HPRegen(){
        hpRegenCounter++;
        if(hpRegenCounter >= hpRegenPoint){
            winLife(1);
            hpRegenCounter = 0;
        }
    }
}
