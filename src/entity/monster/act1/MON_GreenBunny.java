package entity.monster.act1;

import entity.Fighter;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class MON_GreenBunny extends Fighter {

    public MON_GreenBunny(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Yellow bunny";
        sleepDistance = 4;
        solidArea.x = gp.tileSize / 16 * 1;
        solidArea.y = gp.tileSize / 16 * 4;
        solidArea.width = gp.tileSize / 16 * 14;
        solidArea.height = gp.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 10 * gp.tileSize;
        attackSoundReferenceNumber = 42;
        sleeping = true;

        //Balance:
        level = 2;
        damageOnContactValue = 10;
        attackCoolDownValue = 300;
        attackChanceWhenAvailable = 99;
        attackDamage = 15;
        defaultSpeed = 1;
        meleeAttackRange =  (int) (0.75 * gp.tileSize);
        maxLife = 180;
        experienceValue = 170;

        attackFramePoint1 = 50;
        attackFramePoint2 = 100;
        aggroAtThisDistance = 4 * gp.tileSize;
        shouldTryToAttackRange = (int) (2.5 * gp.tileSize);

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        gp.entityImageLoader.matchImagesGreenBunny(this);
    }

    public void playDeathSound(){
        gp.playSE(46);
    }
}
