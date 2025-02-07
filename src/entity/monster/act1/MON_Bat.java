package entity.monster.act1;

import entity.Fighter;
import entity.attacks.melee.MON_MeleeAttack2;
import main.GamePanel;

public class MON_Bat extends Fighter {

    public MON_Bat(GamePanel gp){
        super(gp);

        entityType = 2;
        drawHpBar = true;
        collisionEntity = false;
        name = "One-eyed bat";
        solidArea.x = GamePanel.tileSize / 16;
        solidArea.y = GamePanel.tileSize / 16 * 4;
        solidArea.width = GamePanel.tileSize / 16 * 14;
        solidArea.height = GamePanel.tileSize / 16 * 12;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImages();
        goBackToSpawnMaxDistance = 20 * GamePanel.tileSize;

        //Balance:
        level = 5;
        damageOnContactValue = 10;
        attackCoolDownValue = 100;
        attackChanceWhenAvailable = 20;
        attackDamage = 15;
        defaultSpeed = 4;
        meleeAttackRange =  (int) (0.85 * GamePanel.tileSize);
        maxLife = 170;
        experienceValue = 250;

        attackFramePoint1 = 40;
        attackFramePoint2 = 80;
        aggroAtThisDistance = 3 * GamePanel.tileSize;
        shouldTryToAttackRange = 2 * GamePanel.tileSize;

        ///
        life = maxLife;
        speed = defaultSpeed;
    }

    private void getImages() {
        gp.entityImageLoader.matchImagesBat(this);
    }

    public void createAttackInstance() {
        new MON_MeleeAttack2(gp, attackFramePoint2 - attackFramePoint1, attackDirection, meleeAttackRange, this);
    }
}
