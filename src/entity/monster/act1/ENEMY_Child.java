package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import entity.attacks.projectile.CHILD_Arrow;
import entity.npc.NPC_ArrowChild;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ENEMY_Child extends Fighter {
    public final int maxAttackImage = 9;
    public final int maxWalkImage = 9;
    public final int walkSpriteChangeFrameValue = 5;
    public final int attackSpriteChangeFrameValue = 5;

    public ENEMY_Child(GamePanel gp){
        super(gp);
        //todo remove attackdirecttion
        attackDirection = "down";
        itemDropChance = 0;
        attackCoolDownValue = 220;
        entityType = 2;
        drawHpBar = false;
        collisionEntity = false;
        name = "annoying child";
        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 20 * GamePanel.tileSize;

        //Balance:
        level = 1;
        damageOnContactValue = 0;
        attackChanceWhenAvailable = 90;
        attackDamage = 1;
        defaultSpeed = 0;
        meleeAttackRange =  (int) (0.85 * GamePanel.tileSize);
        maxLife = 1;
        experienceValue = 1;

        attackFramePoint1 = 40;
        attackFramePoint2 = 80;
        aggroAtThisDistance = 12 * GamePanel.tileSize;
        shouldTryToAttackRange = 7 * GamePanel.tileSize;

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    public void getImages(){
        attackDown = new BufferedImage[maxAttackImage];
        walkDown = new BufferedImage[maxWalkImage];
        for (int i = 0; i < maxWalkImage; i++) {
            attackDown[i] = setupSheet("/entity/monster/act1/kid/bow", i * 128,0,128, 128, GamePanel.tileSize, GamePanel.tileSize);
            walkDown[i] = setupSheet("/entity/monster/act1/kid/walkdown", i * 128,0,128, 128, GamePanel.tileSize, GamePanel.tileSize);
        }
    }

    public void update() {
        if(sleeping){
            searchEnemies();
        }
        else {
            attackCoolDown();
            if (currentlyAttacking) {
                attacking();
            }
            if (!currentlyAttacking) {
                setActionAI(); // BIG AI
            }
        }
        handleDeathRelated();
    }

    public void setActionAI(){
        if(targetPathFollowed){
            setActionAIPath();
        } else {
            // only perform this calculation 3 times per second, not every frame:
            checkForEnemiesCounter++;
            Entity closestFightingEnemy = null;
            if(checkForEnemiesCounter > 20){
                closestFightingEnemy = findClosestFightingEnemy();
                checkForEnemiesCounter = 0;
            }

            if (closestFightingEnemy != null) {
                targetEntity = closestFightingEnemy;
                actionLockCounter = 0;
                targetPathFollowed = true;
            }
        }
    }

    public void setActionAIPath(){
        if(targetEntity != null){
            int i = random.nextInt(100) + 1;
            if(i >= attackChanceWhenAvailable && canAttackFromCoolDown && canAttack) {
                if(targetEntity != null){
                    if(middleDistance(targetEntity) < shouldTryToAttackRange){
                        currentlyAttacking = true;
                        spriteCounter = 0;
                    }
                }
            }
        }

        // lose aggro if distance too big from target
        goBackCheckCounter ++;
        if(goBackCheckCounter >=  20) {
            if (targetEntity != null) {
                if (targetEntity.middleDistance(this) > 12 * GamePanel.tileSize) {
                    targetEntity = null;
                    targetPathFollowed = false;
                    goBackCheckCounter = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2){

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            if(!isDying && !currentlyAttacking) {
                getWalkingImage();
                drawGetWalkSpriteNumber();
            }

            else if(currentlyAttacking && !isDying){
                getAttackImage();
                attackImageCorrection();
            }

            //DRAW IMAGE:
            g2.drawImage(image, screenX, screenY, null);

            //DRAW HITBOX:
            drawHitBox(g2);
        }
    }

    //do in draw method instead when
    public void setWalkingSpriteNumber() {
        spriteCounter++;
        if (spriteCounter > walkSpriteChangeFrameValue) {
            walkSpriteNum = (walkSpriteNum + 1) % maxWalkSpriteArrayLength;
            spriteCounter = 0;
        }
    }

    public void getWalkingImage(){
        image = walkDown[walkSpriteNum];
    }

    public void attacking(){
        if(targetEntity == null){
            attackFrameCounter = 0;
            currentlyAttacking = false;
            return;
        }
        attackFrameCounter ++;

        if(attackFrameCounter == 1){
            canAttackFromCoolDown = false;
        }

        if(attackFrameCounter == attackFramePoint1) {
            createAttackInstance();
        }

        if(attackFrameCounter >= attackFramePoint2){
            attackFrameCounter = 0;
            currentlyAttacking = false;
        }
    }

    public void getAttackImage(){
        int imageNumber = (attackFrameCounter * maxAttackImage) / attackFramePoint2;
        image = attackDown[imageNumber];
    }

    public void drawGetWalkSpriteNumber(){
        if (gp.gameState == gp.playState && !stunned && !frozen) {
            spriteCounter++;
            if (spriteCounter > walkSpriteChangeFrameValue) {
                walkSpriteNum = (walkSpriteNum + 1) % 9;
                spriteCounter = 0;
            }
        }
    }

    public void createAttackInstance(){
        if(targetEntity != null) {
            new CHILD_Arrow(gp, this, targetEntity, 3, 10);
        }
    }

    public void handleDeathRelated(){
        if(life <= 0){
            isDying = true;

            gp.allFightingEntities.remove(this);
            for (int i = 0; i < gp.fighters[1].length; i++) {
                if(gp.fighters[gp.currentMap][i] == this){
                    gp.fighters[gp.currentMap][i] = null;
                    break;
                }
            }
            specialOnDeath();
        }
    }

    public void specialOnDeath(){
        gp.player.kidSpecialWinExperience(level, experienceValue, name);
        gp.player.checkIfLvLUp();
        gp.npc[gp.currentMap][8] = new NPC_ArrowChild(gp);
        gp.npc[gp.currentMap][8].worldX = worldX;
        gp.npc[gp.currentMap][8].worldY = worldY;
        gp.npc[gp.currentMap][8].setDefaultSpawn();
    }
}
