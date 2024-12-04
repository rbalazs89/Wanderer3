package entity;

import entity.attacks.MON_DaggerGlobe;
import entity.attacks.MON_DragonHead;
import entity.attacks.player.Spells;
import entity.attacks.projectile.SHADOW_DaggerProjectile;
import entity.attacks.projectile.SHADOW_Projectile;
import entity.attacks.vfx.ColdAura;
import entity.attacks.vfx.JumpImpactSmoke;
import entity.monster.act1.MON_Bat;
import entity.monster.act1.MON_Ogre;
import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static object.SuperObject.adjustTransparency;

public class EntityImageLoader {
    public BufferedImage[] dyingGreenBunny = new BufferedImage[9];
    public BufferedImage[] attackUpGreenBunny = new BufferedImage[9];
    public BufferedImage[] attackRightGreenBunny = new BufferedImage[9];
    public BufferedImage[] attackDownGreenBunny = new BufferedImage[9];
    public BufferedImage[] attackLeftGreenBunny = new BufferedImage[9];
    public BufferedImage[] hurtGreenBunny = new BufferedImage[9];
    public BufferedImage[] walkUpGreenBunny = new BufferedImage[9];
    public BufferedImage[] walkRightGreenBunny = new BufferedImage[9];
    public BufferedImage[] walkDownGreenBunny = new BufferedImage[9];
    public BufferedImage[] walkLeftGreenBunny = new BufferedImage[9];
    ///////////////////////////////////////////////////////////////////////
    public BufferedImage[] dyingYellowBunny = new BufferedImage[9];
    public BufferedImage[] attackUpYellowBunny = new BufferedImage[9];
    public BufferedImage[] attackRightYellowBunny = new BufferedImage[9];
    public BufferedImage[] attackDownYellowBunny = new BufferedImage[9];
    public BufferedImage[] attackLeftYellowBunny = new BufferedImage[9];
    public BufferedImage[] hurtYellowBunny = new BufferedImage[9];
    public BufferedImage[] walkUpYellowBunny = new BufferedImage[9];
    public BufferedImage[] walkRightYellowBunny = new BufferedImage[9];
    public BufferedImage[] walkDownYellowBunny = new BufferedImage[9];
    public BufferedImage[] walkLeftYellowBunny = new BufferedImage[9];
    //////////////////////////////////////////////////////////////////////////
    public BufferedImage[] dyingWolf = new BufferedImage[9];
    public BufferedImage[] attackUpWolf = new BufferedImage[9];
    public BufferedImage[] attackRightWolf = new BufferedImage[9];
    public BufferedImage[] attackDownWolf = new BufferedImage[9];
    public BufferedImage[] attackLeftWolf = new BufferedImage[9];
    public BufferedImage[] walkUpWolf = new BufferedImage[9];
    public BufferedImage[] walkRightWolf = new BufferedImage[9];
    public BufferedImage[] walkDownWolf = new BufferedImage[9];
    public BufferedImage[] walkLeftWolf = new BufferedImage[9];
    ///////////////////////////////////////////////////////////
    public BufferedImage[] walkUpSkeleton = new BufferedImage[9];
    public BufferedImage[] walkRightSkeleton = new BufferedImage[9];
    public BufferedImage[] walkDownSkeleton = new BufferedImage[9];
    public BufferedImage[] walkLeftSkeleton = new BufferedImage[9];
    public BufferedImage[] attackUpSkeleton = new BufferedImage[9];
    public BufferedImage[] attackRightSkeleton = new BufferedImage[9];
    public BufferedImage[] attackDownSkeleton = new BufferedImage[9];
    public BufferedImage[] attackLeftSkeleton = new BufferedImage[9];
    public BufferedImage[] dyingSkeleton = new BufferedImage[5];
    public BufferedImage[] glitterImages = new BufferedImage[8];
    public BufferedImage[] blueAttackProjectile = new BufferedImage[15];
    public BufferedImage[] coldAuraImages = new BufferedImage[20];

    public BufferedImage[] daggerImages = new BufferedImage[12];
    public BufferedImage[] jumpImpactImages = new BufferedImage[24];
    public BufferedImage[] fireDragonHead = new BufferedImage[27];

    public BufferedImage[] dyingBat = new BufferedImage[9];
    public BufferedImage[] walkUpBat = new BufferedImage[9];
    public BufferedImage[] walkRightBat = new BufferedImage[9];
    public BufferedImage[] walkDownBat = new BufferedImage[9];
    public BufferedImage[] walkLeftBat = new BufferedImage[9];
    public BufferedImage[] attackUpBat = new BufferedImage[9];
    public BufferedImage[] attackRightBat = new BufferedImage[9];
    public BufferedImage[] attackDownBat = new BufferedImage[9];
    public BufferedImage[] attackLeftBat = new BufferedImage[9];
    //ogre:
    private BufferedImage up1Ogre, up2Ogre, right1Ogre, right2Ogre, down1Ogre, down2Ogre, left1Ogre, left2Ogre,
            attackUp1Ogre, attackUp2Ogre, attackRight1Ogre, attackRight2Ogre, attackDown1Ogre, attackDown2Ogre, attackLeft1Ogre, attackLeft2Ogre;
    GamePanel gp;
    public EntityImageLoader(GamePanel gp){
        this.gp = gp;
        getImages();
    }

    public void getImages(){
        BufferedImage tempImage = setupImage("/glitter/darkglitter");
        for (int i = 0; i < 7; i++) {
            glitterImages[i] = setupSheet3(tempImage,i * 70,0,70, 70, 20,20);
            glitterImages[i] = adjustTransparency(glitterImages[i]);
        }
        //ogre:
        up1Ogre = setup("/entity/monster/act1/ogre/orc_up_1", gp.tileSize, gp.tileSize);
        up2Ogre = setup("/entity/monster/act1/ogre/orc_up_2", gp.tileSize, gp.tileSize);
        down1Ogre = setup("/entity/monster/act1/ogre/orc_down_1", gp.tileSize, gp.tileSize);
        down2Ogre = setup("/entity/monster/act1/ogre/orc_down_2", gp.tileSize, gp.tileSize);
        right1Ogre = setup("/entity/monster/act1/ogre/orc_right_1", gp.tileSize, gp.tileSize);
        right2Ogre = setup("/entity/monster/act1/ogre/orc_right_2", gp.tileSize, gp.tileSize);
        left1Ogre = setup("/entity/monster/act1/ogre/orc_left_1", gp.tileSize, gp.tileSize);
        left2Ogre = setup("/entity/monster/act1/ogre/orc_left_2", gp.tileSize, gp.tileSize);

        attackUp1Ogre = setup("/entity/monster/act1/ogre/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2Ogre = setup("/entity/monster/act1/ogre/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1Ogre = setup("/entity/monster/act1/ogre/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2Ogre = setup("/entity/monster/act1/ogre/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackRight1Ogre = setup("/entity/monster/act1/ogre/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2Ogre = setup("/entity/monster/act1/ogre/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
        attackLeft1Ogre = setup("/entity/monster/act1/ogre/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2Ogre = setup("/entity/monster/act1/ogre/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);

        //bat:
        tempImage = setupImage("/entity/monster/act1/bat/batSheet");
        for (int i = 0; i < 9; i++) {
            dyingBat[i] = setupSheet3(tempImage, i * 88,0,88, 88, gp.tileSize, gp.tileSize);
            walkUpBat[i] = setupSheet3(tempImage, i * 88,88 *1,88, 88, gp.tileSize, gp.tileSize);
            walkRightBat[i] = setupSheet3(tempImage, i * 88, 88 * 2,88, 88, gp.tileSize, gp.tileSize);
            walkDownBat[i] = setupSheet3(tempImage, i * 88, 88 * 3,88, 88, gp.tileSize, gp.tileSize);
            walkLeftBat[i] = setupSheet3(tempImage, i * 88, 88 * 4,88, 88, gp.tileSize, gp.tileSize);
            attackUpBat[i] = setupSheet3(tempImage, i * 88, 88 * 5,88, 88, gp.tileSize, gp.tileSize);
            attackRightBat[i] = setupSheet3(tempImage, i * 88, 88 * 6,88, 88, gp.tileSize, gp.tileSize);
            attackDownBat[i] = setupSheet3(tempImage, i * 88,88 * 7,88, 88, gp.tileSize, gp.tileSize);
            attackLeftBat[8-i] = setupSheet3(tempImage, i * 88,88 * 8,88, 88, gp.tileSize, gp.tileSize);
            //hurtBat[i] = setupSheet("/entity/monster/act1/bat/hurts", i * 88,0,88, 88, gp.tileSize, gp.tileSize); not used for now
        }

        //green bunny:
        tempImage = setupImage("/entity/monster/act1/greenbunny/greenBunnySheet");
        for (int i = 0; i < 9; i++) {
            dyingGreenBunny[i] = setupSheet2(tempImage, i * 64,0,64, 64);
            walkUpGreenBunny[i] = setupSheet2(tempImage, i * 64,64,64, 64);
            walkRightGreenBunny[i] = setupSheet2(tempImage, i * 64,2 *64,64, 64);
            walkDownGreenBunny[i] = setupSheet2(tempImage, i * 64,3 * 64,64, 64);
            walkLeftGreenBunny[8-i] = setupSheet2(tempImage, i * 64,4 * 64,64, 64);
            attackUpGreenBunny[i] = setupSheet2(tempImage, i * 64,5 * 64,64, 64);
            attackRightGreenBunny[i] = setupSheet2(tempImage, i * 64,6 * 64,64, 64);
            attackDownGreenBunny[i] = setupSheet2(tempImage, i * 64,7 * 64,64, 64);
            attackLeftGreenBunny[8-i] = setupSheet2(tempImage, i * 64,8 * 64,64, 64);
            //hurtGreenBunny[i] = setupSheet2(tempImage, i * 64,9 * 64,64, 64);
        }

        //wolf:
        tempImage = setupImage("/entity/monster/act1/wolf/wolfSheet");
        for (int i = 0; i < 9; i++) {
            dyingWolf[i] = setupSheet2(tempImage, i * 64,0,64, 64);
            walkUpWolf[i] = setupSheet2(tempImage, i * 64, 64,64, 64);
            walkRightWolf[i] = setupSheet2(tempImage, i * 64,64 * 2,64, 64);
            walkDownWolf[i] = setupSheet2(tempImage, i * 64,64 * 3,64, 64);
            walkLeftWolf[8-i] = setupSheet2(tempImage, i * 64,64 * 4,64, 64);
            attackUpWolf[i] = setupSheet2(tempImage, i * 64,64 * 5,64, 64);
            attackRightWolf[i] = setupSheet2(tempImage, i * 64,64 * 6,64, 64);
            attackDownWolf[i] = setupSheet2(tempImage, i * 64,64 * 7,64, 64);
            attackLeftWolf[8-i] = setupSheet2(tempImage, i * 64,64 * 8,64, 64);
        }

        //yellow bunny:
        tempImage = setupImage("/entity/monster/act1/yellowbunny/yellowBunnySheet");
        for (int i = 0; i < 9; i++) {
            dyingYellowBunny[i] = setupSheet2(tempImage, i * 64,0,64, 64);
            walkUpYellowBunny[i] = setupSheet2(tempImage, i * 64,64,64, 64);
            walkRightYellowBunny[i] = setupSheet2(tempImage, i * 64,64 * 2,64, 64);
            walkDownYellowBunny[i] = setupSheet2(tempImage, i * 64, 64 * 3,64, 64);
            walkLeftYellowBunny[8-i] = setupSheet2(tempImage, i * 64, 64 * 4,64, 64);
            attackUpYellowBunny[i] = setupSheet2(tempImage, i * 64,64 * 5,64, 64);
            attackRightYellowBunny[i] = setupSheet2(tempImage, i * 64,64 * 6,64, 64);
            attackDownYellowBunny[i] = setupSheet2(tempImage, i * 64,64 * 7,64, 64);
            attackLeftYellowBunny[8-i] = setupSheet2(tempImage, i * 64,64 * 8,64, 64);
            //hurtYellowBunny[i] = setupSheet2("/entity/monster/act1/yellowbunny/hurts", i * 64,0,64, 64);
        }

        //skeleton:
        tempImage = setupImage("/entity/monster/act1/skeleton/skeletonSheet");
        for (int i = 0; i < 9; i++) {
            walkUpSkeleton[i] = setupSheet2(tempImage, i * 128,0,128, 128);
            walkRightSkeleton[i] = setupSheet2(tempImage, i * 128,128,128, 128);
            walkLeftSkeleton[8- i] = setupSheet2(tempImage, i * 128,128 * 2,128, 128);
            walkDownSkeleton[i] = setupSheet2(tempImage, i * 128,128 * 3,128, 128);
            attackUpSkeleton[i] = setupSheet2(tempImage, i * 128,128 * 4,128, 128);
            attackRightSkeleton[i] = setupSheet2(tempImage, i * 128,128 * 5,128, 128);
            attackLeftSkeleton[8-i] = setupSheet2(tempImage, i * 128,128 * 6,128, 128);
            attackDownSkeleton[i] = setupSheet2(tempImage, i * 128,128 * 7,128, 128);
        }

        tempImage = setupImage("/entity/monster/act1/skeleton/death");
        for (int i = 0; i < 5; i++) {
            dyingSkeleton[i] = setupSheet2(tempImage, i * 128, 0, 128, 128);
        }

        //VFX, spells:

        //smoke:
        tempImage = setupImage("/spell/impactsmoke/smoke");
        for (int i = 0; i < 24; i++) {
            jumpImpactImages[i] = setupSheet2(tempImage, i*64, 0, 64,64);
        }


        //dragonhead:
        tempImage = setupImage("/spell/dragonhead/dragonhead");
        for (int i = 0; i < 27; i++) {
            fireDragonHead[i] = setupSheet3(tempImage, i * 64 , 0 , 64,64, 96,96 );
        }

        //dagger images
        tempImage = setupImage("/spell/dagger/dagger");
        for (int i = 0; i < 12; i++) {
            daggerImages[i] = setupSheet2(tempImage, i * 64, 0, 64,64);
        }

        //blue attack projectile
        tempImage = setupImage("/spell/blueprojectile/blueprojectile2");
        for (int i = 0; i < 14; i++) {
            blueAttackProjectile[i] = setupSheet2(tempImage, i * 64, 0, 64,64);
        }
        //blue aura
        tempImage = setupImage("/spell/coldauravfx/coldaura");
        for (int i = 0; i < 20; i++) {
            coldAuraImages[i] = setupSheet2(tempImage,i * 167,0,167,100);
        }

    }
    public void matchOgreImages(MON_Ogre ogre){
        ogre.up1 = up1Ogre;
        ogre.up2 = up2Ogre;
        ogre.right1 = right1Ogre;
        ogre.right2 = right2Ogre;
        ogre.down1 = down1Ogre;
        ogre.down2 = down2Ogre;
        ogre.left1 = left1Ogre;
        ogre.left2 = left2Ogre;
        ogre.attackUp1 = attackUp1Ogre;
        ogre.attackUp2 = attackUp2Ogre;
        ogre.attackRight1 = attackRight1Ogre;
        ogre.attackRight2 = attackRight2Ogre;
        ogre.attackDown1 = attackDown1Ogre;
        ogre.attackDown2 = attackDown2Ogre;
        ogre.attackLeft1 = attackLeft1Ogre;
        ogre.attackLeft2 = attackLeft2Ogre;
    }

    public void matchJumpImpactImages(JumpImpactSmoke jumpImpactSmoke){
        jumpImpactSmoke.images = jumpImpactImages;
    }

    public void matchImagesGreenBunny(Fighter fighter){
        fighter.dying = dyingGreenBunny;
        fighter.hurt = hurtGreenBunny;
        fighter.walkUp = walkUpGreenBunny;
        fighter.walkRight = walkRightGreenBunny;
        fighter.walkDown = walkDownGreenBunny;
        fighter.walkLeft = walkLeftGreenBunny;
        fighter.attackUp = attackUpGreenBunny;
        fighter.attackRight = attackRightGreenBunny;
        fighter.attackDown = attackDownGreenBunny;
        fighter.attackLeft = attackLeftGreenBunny;
    }

    public void matchDaggerProjectile(SHADOW_DaggerProjectile daggerProjectile){
        for (int i = 0; i < 11; i++) {
            daggerProjectile.images[i] = daggerImages[i+1];
        }
    }

    public void matchFireDragonImages(MON_DragonHead dragonHead){
        for (int i = 0; i < 27; i++) {
            dragonHead.images = fireDragonHead;
        }
    }
    public void matchImagesBat(MON_Bat bat){
        bat.dying = dyingBat;
        bat.walkUp = walkUpBat;
        bat.walkRight = walkRightBat;
        bat.walkDown = walkDownBat;
        bat.walkLeft = walkLeftBat;
        bat.attackUp = attackUpBat;
        bat.attackRight = attackRightBat;
        bat.attackDown = attackDownBat;
        bat.attackLeft = attackLeftBat;
    }

    public void matchBlueAttackProjectile(SHADOW_Projectile projectile){
        projectile.images = blueAttackProjectile;
    }

    public void matchBlueAuraImages(ColdAura coldAura){
        coldAura.images = coldAuraImages;
    }

    public void matchDaggerImagesGlobe(MON_DaggerGlobe daggerGlobe){
        daggerGlobe.images[0] = daggerImages[0];
        daggerGlobe.images[1] = daggerImages[1];
    }

    public void matchImagesYellowBunny(Fighter fighter){
        fighter.dying = dyingYellowBunny;
        fighter.hurt = hurtYellowBunny;
        fighter.walkUp = walkUpYellowBunny;
        fighter.walkRight = walkRightYellowBunny;
        fighter.walkDown = walkDownYellowBunny;
        fighter.walkLeft = walkLeftYellowBunny;
        fighter.attackUp = attackUpYellowBunny;
        fighter.attackRight = attackRightYellowBunny;
        fighter.attackDown = attackDownYellowBunny;
        fighter.attackLeft = attackLeftYellowBunny;
    }

    public void matchImagesWolf(Fighter fighter){
        fighter.dying = dyingWolf;
        fighter.walkUp = walkUpWolf;
        fighter.walkRight = walkRightWolf;
        fighter.walkDown = walkDownWolf;
        fighter.walkLeft = walkLeftWolf;
        fighter.attackUp = attackUpWolf;
        fighter.attackRight = attackRightWolf;
        fighter.attackDown = attackDownWolf;
        fighter.attackLeft = attackLeftWolf;
    }

    public void matchImagesSkeleton(Fighter fighter){
        fighter.dying = dyingSkeleton;
        fighter.walkUp = walkUpSkeleton;
        fighter.walkRight = walkRightSkeleton;
        fighter.walkDown = walkDownSkeleton;
        fighter.walkLeft = walkLeftSkeleton;
        fighter.attackUp = attackUpSkeleton;
        fighter.attackRight = attackRightSkeleton;
        fighter.attackDown = attackDownSkeleton;
        fighter.attackLeft = attackLeftSkeleton;
    }

    public BufferedImage setupSheet2(BufferedImage image, int x, int y, int width, int height) {
        image = image.getSubimage(x, y, width, height);
        return image;
    }

    public BufferedImage setupSheet2(String imageName, int x, int y, int width, int height, int scaleHeigth, int scaleWidth) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupSheet3(BufferedImage image, int x, int y, int width, int height, int scaleHeigth, int scaleWidth) {
        UtilityTool uTool = new UtilityTool();
        image = image.getSubimage(x, y, width, height);
        image = uTool.scaleImage(image, scaleWidth, scaleHeigth);
        return image;
    }

    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupImage(String imageName){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
