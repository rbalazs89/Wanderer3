package entity.monster.act1;

import entity.Fighter;
import entity.attacks.melee.MON_MeleeAttack2;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class MON_Bat extends Fighter {

    public MON_Bat(GamePanel gp){
        super(gp);
        this.gp = gp;

        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "One-eyed bat";
        solidArea.x = gp.tileSize / 16 * 1;
        solidArea.y = gp.tileSize / 16 * 4;
        solidArea.width = gp.tileSize / 16 * 14;
        solidArea.height = gp.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 20 * gp.tileSize;

        //Balance:
        level = 5;
        damageOnContactValue = 10;
        attackCoolDownValue = 100;
        attackChanceWhenAvailable = 20;
        attackDamage = 15;
        defaultSpeed = 4;
        meleeAttackRange =  (int) (0.85 * gp.tileSize);
        maxLife = 170;
        experienceValue = 250;

        attackFramePoint1 = 40;
        attackFramePoint2 = 80;
        aggroAtThisDistance = 3 * gp.tileSize;
        shouldTryToAttackRange = (int) (2 * gp.tileSize);

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        gp.entityImageLoader.matchImagesBat(this);
        /*
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
            dying[i] = setupSheet("/entity/monster/act1/bat/death", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            hurt[i] = setupSheet("/entity/monster/act1/bat/hurts", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            walkUp[i] = setupSheet("/entity/monster/act1/bat/up", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            walkRight[i] = setupSheet("/entity/monster/act1/bat/right", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            walkDown[i] = setupSheet("/entity/monster/act1/bat/down", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            walkLeft[i] = setupSheet("/entity/monster/act1/bat/left", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            attackUp[i] = setupSheet("/entity/monster/act1/bat/attackup", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            attackRight[i] = setupSheet("/entity/monster/act1/bat/attackright", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            attackDown[i] = setupSheet("/entity/monster/act1/bat/attackdown", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            attackLeft[8-i] = setupSheet("/entity/monster/act1/bat/attackleft", i * 88,0,88, 88, gp.tileSize, gp.tileSize);
        }

         */
    }


    public void createAttackInstance() {
        new MON_MeleeAttack2(gp, attackFramePoint2 - attackFramePoint1, attackDirection, meleeAttackRange, this);
    }
}
