package entity.monster.act1;

import entity.Fighter;
import main.GamePanel;

public class MON_Wolf extends Fighter {
    public MON_Wolf(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Wolf";
        sleepDistance = 4;
        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 10 * GamePanel.tileSize;
        attackSoundReferenceNumber = -1; // make sound at attack start, not attack instance, because this entity moves during his attack
        sleeping = true;

        //Balance:
        level = 4;
        resist[0] = 10;
        damageOnContactValue = 10;
        attackCoolDownValue = 300;
        attackChanceWhenAvailable = 90;
        attackDamage = 30;
        defaultSpeed = 2;
        meleeAttackRange =  (int) (0.9 * GamePanel.tileSize);
        maxLife = 215;
        experienceValue = 190;

        attackFramePoint1 = 50;
        attackFramePoint2 = 100;
        aggroAtThisDistance = 4 * GamePanel.tileSize;
        shouldTryToAttackRange = (int) (2.5 * GamePanel.tileSize);

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        gp.entityImageLoader.matchImagesWolf(this);
    }

    public void playDeathSound(){
        gp.playSE(52);
    }

    public void attacking(){
        if(targetEntity == null){
            attackFrameCounter = 0;
            currentlyAttacking = false;
            return;
        }
        attackFrameCounter ++;
        if (attackFrameCounter == 1) {
            gp.playSE(53);
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
        if(attackFrameCounter < attackFramePoint2/2){
            switch (attackDirection) {
                case "up" -> worldY--;
                case "right" -> worldX++;
                case "down" ->worldY++;
                case "left" -> worldX--;
            }
        } else {
            switch (attackDirection) {
                case "up" ->
                    worldY++;
                case "right" -> worldX--;
                case "down" -> worldY--;
                case "left" -> worldX++;
            }
        }

        //tbd handle which phase can be stunned/knockbacked etc
        if(attackFrameCounter == attackFramePoint1) {
            createAttackInstance();
        }

        if(attackFrameCounter == attackFramePoint2){
            attackFrameCounter = 0;
            currentlyAttacking = false;
        }
    }
}

