package entity.monster.act1;

import entity.Fighter;
import entity.attacks.projectile.MON_PikaLightning;
import main.GamePanel;

public class MON_PikaPika extends Fighter {

    public MON_PikaPika(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Yellow bunny";
        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 10 * GamePanel.tileSize;
        sleepDistance = 4;
        sleeping = true;

        //Balance:
        damageOnContactValue = 10;
        attackCoolDownValue = 100;
        attackChanceWhenAvailable = 1;
        attackDamage = 15;
        defaultSpeed = 1;
        maxLife = 130;
        experienceValue = 180;
        attackStunStrength = 20;

        attackFramePoint1 = 60;
        attackFramePoint2 = 70;
        aggroAtThisDistance = 4 * GamePanel.tileSize;
        shouldTryToAttackRange = 5 * GamePanel.tileSize;

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        gp.entityImageLoader.matchImagesYellowBunny(this);
    }

    public void setActionAIPath(){
        if(targetEntity != null){
            if(actionLockCounter == 0){
                int i = random.nextInt(10); // 0, 1 , 2
                if(i < 3){
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

            int i = random.nextInt(400) + 1;
            if(i <= attackChanceWhenAvailable && canAttackFromCoolDown && canAttack) {
                if(targetEntity != null){
                    if(middleDistance(targetEntity) < shouldTryToAttackRange){
                        currentlyAttacking = true;
                    }
                }
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
        if (targetEntity != null) {
            new MON_PikaLightning(gp,this, targetEntity, 4, 13, 0);
        }
    }

    public void playDeathSound(){
        gp.playSE(46);
    }
}

