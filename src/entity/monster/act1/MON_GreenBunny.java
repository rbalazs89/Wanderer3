package entity.monster.act1;

import entity.Fighter;
import main.GamePanel;

public class MON_GreenBunny extends Fighter {

    public MON_GreenBunny(GamePanel gp){
        super(gp);
        this.gp = gp;
        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "Yellow bunny";
        sleepDistance = 4;
        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 10 * GamePanel.tileSize;
        attackSoundReferenceNumber = 42;
        sleeping = true;

        //Balance:
        level = 2;
        damageOnContactValue = 10;
        attackCoolDownValue = 300;
        attackChanceWhenAvailable = 99;
        attackDamage = 15;
        defaultSpeed = 1;
        meleeAttackRange =  (int) (0.75 * GamePanel.tileSize);
        maxLife = 180;
        experienceValue = 170;

        attackFramePoint1 = 50;
        attackFramePoint2 = 100;
        aggroAtThisDistance = 4 * GamePanel.tileSize;
        shouldTryToAttackRange = (int) (2.5 * GamePanel.tileSize);

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
