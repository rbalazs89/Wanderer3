package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import entity.attacks.projectile.MON_PikaLightning;
import main.GamePanel;
import object.OBJ_DroppedItem;
import object.OBJ_DroppedTalentBook;

import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_RedBunny extends Fighter {

    public MON_RedBunny(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Red bunny";
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
        level = 6;
        damageOnContactValue = 10;
        attackCoolDownValue = 100;
        attackChanceWhenAvailable = 1;
        attackDamage = 15;
        defaultSpeed = 1;
        meleeAttackRange =  (int) (0.75 * GamePanel.tileSize);
        maxLife = 610;
        experienceValue = 950;
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
        dying = new BufferedImage[9];
        attackUp = new BufferedImage[9];
        attackRight = new BufferedImage[9];
        attackDown = new BufferedImage[9];
        attackLeft = new BufferedImage[9];
        hurt = new BufferedImage[9];
        walkUp = new BufferedImage[9];
        walkRight = new BufferedImage[9];
        walkDown = new BufferedImage[9];
        walkLeft = new BufferedImage[9];

        for (int i = 0; i < 9; i++) {
            dying[i] = setupSheet2("/entity/monster/act1/redbunny/death", i * 64,0,64, 64);
            hurt[i] = setupSheet2("/entity/monster/act1/redbunny/hurts", i * 64,0,64, 64);
            walkUp[i] = setupSheet2("/entity/monster/act1/redbunny/up", i * 64,0,64, 64);
            walkRight[i] = setupSheet2("/entity/monster/act1/redbunny/right", i * 64,0,64, 64);
            walkDown[i] = setupSheet2("/entity/monster/act1/redbunny/down", i * 64,0,64, 64);
            walkLeft[8-i] = setupSheet2("/entity/monster/act1/redbunny/left", i * 64,0,64, 64);
            attackUp[i] = setupSheet2("/entity/monster/act1/redbunny/attackup", i * 64,0,64, 64);
            attackRight[i] = setupSheet2("/entity/monster/act1/redbunny/attackright", i * 64,0,64, 64);
            attackDown[i] = setupSheet2("/entity/monster/act1/redbunny/attackdown", i * 64,0,64, 64);
            attackLeft[8-i] = setupSheet2("/entity/monster/act1/redbunny/attackleft", i * 64,0,64, 64);
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
            gp.playSE(43);
        }
    }


    public void createAttackInstance() {
        if (targetEntity != null) {
            new MON_PikaLightning(gp,this, targetEntity, 4, 15, 20);
        }
    }

    public void playDeathSound(){
        gp.playSE(46);
    }

    public void dropItem(){
        if(!gp.progress.act1BookPickedUp[0]){
            OBJ_DroppedTalentBook book = new OBJ_DroppedTalentBook(gp);
            int[] tempArray = gp.uTool.findPlaceForDroppedItem2(worldMiddleX(), worldMiddleY());
            book.worldX = tempArray[0];
            book.worldY = tempArray[1];
            gp.interactObjects.add(book);
        } else {
            itemDrop(level, 0, isBoss);
        }
    }
}
