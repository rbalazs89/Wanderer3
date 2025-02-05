package entity;

import entity.attacks.player.*;
import entity.attacks.vfx.JumpImpactSmoke;
import entity.playerrelated.PlayerSpell;
import entity.playerrelated.PlayerSpellList;
import entity.playerrelated.PlayerTalents;
import item.InventoryPage;
import item.Item;
import item.MomoJuice;
import main.DataBaseClass1;
import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;
import tool.DamageNumber;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Player extends Entity {
    KeyHandler keyH;
    BufferedImage image = null;

    public final int screenX;
    public final int screenY;
    public int dashSpeed = 10;
    public boolean gracedCollision = true; //player only, no collision during graced time with monsters
    public boolean attacking = false;
    public boolean canMove = true;
    public boolean canAttack = true;
    public boolean canCast = true;
    public boolean canCastFromCoolDown = true;
    public boolean canCastFromStun = true;
    public boolean casting = false;
    public boolean haveWaveCloak = false;
    public boolean damageAbsorbAvailable;
    public int currentlyCastingSpellSlot; //
    //public int[] spellSlots = new int[6];
    public int castFrameCounter = 0;
    public int castSpriteNum = 0;
    public String directionAtSpellInstance;
    public int attackDirection = 1;
    // 1 = north | 2 = northeast | 3 = east .... 8 = northwest || based on angle.
    public int lifeRegenCounter;
    public int manaRegenCounter;
    public int manaRegenPoint;
    public int lifeRegenPoint;
    public int castPoint1, castPoint2, castPoint1FromItems, castPoint2FromItems;
    public static final int baseCastPoint1 = 80;
    public static final int baseCastPoint2 = 160;
    public static final int baseAttackFramePoint1 = 12;
    public static final int baseAttackFramePoint2 = 30;
    public int attackFramePoint1;
    public int attackFramePoint2;
    public int attackFramePointFromItems1;
    public int attackFramePointFromItems2;
    int waveCounter = 0;
    //DASHING:
    public boolean dashing = false;
    public int dashingCounter = 0;
    public int dashingCoolDownCounter = 0;
    //public static final int stopDashingAt = 20;
    public static final int dashingCoolDown = 120;
    public boolean canDashFromCoolDown = true; // dash not on cd
    public boolean canStartDash = true; // not attacking or casting or stunned
    boolean diagonalMoveToggle = false;
    boolean movingAvailable = true;
    public boolean currentlyMoving = false;
    public int idleActivationCounter = 0;
    public boolean isCurrentlyIdle = true;
    public int stunnedCounter = 0;
    public int energyShieldLevel = 0;
    public BufferedImage idleDown1, idleDown2, idleDown3, idleDown4, idleDown5, idleDown6, idleDown7, idleDown8, idleDown9,
            idleRight1, idleRight2, idleRight3, idleRight4, idleRight5, idleRight6, idleRight7, idleRight8, idleRight9,
            idleUp1, idleUp2, idleUp3, idleUp4, idleUp5, idleUp6, idleUp7, idleUp8, idleUp9,
            idleLeft1, idleLeft2, idleLeft3, idleLeft4, idleLeft5, idleLeft6, idleLeft7, idleLeft8, idleLeft9,
            dashLeft, dashRight, dashUp, dashDown;
    public boolean interactingButtonPressed = false;
    public boolean canInteract = true;
    int tempScreenX = 0;
    int tempScreenY = 0;
    public static final int baseManaRegenPoint = 100;
    public int manaRegenPointFromItems;
    public int manaRegenPointFromTalent;
    public static final int baseLifeRegenPoint = 120;
    public int lifeRegenPointFromItems;
    public int lifeRegenPointFromTalent;
    public int lifeRegenPointFromSkill;
    public int fireResistFromSkill;
    public int maxMana;
    public int maxManaFromUp;
    public int maxLifeFromUp;
    public int maxLifeFromItem;
    public int maxManaFromItem;
    public int maxLifeFromTalent;
    public int maxManaFromTalent;
    public int mana;
    public int armor = 0;
    public int level = 30;
    public int strength;
    public int dexterity;
    public int endurance;
    public int intelligence;
    public int gold = 100;
    public int experience;
    public int nextLevelExp = 500;
    public int previousLevelExp = 0;
    public static final int baseStrength = 10;
    public static final int baseDexterity = 10;
    public static final int baseEndurance = 10;
    public static final int baseIntelligence = 10;
    public static final int baseMaxLife = 100;
    public static final int baseMaxMana = 50;
    public int attackRangeIncrease = 0;
    public int strengthFromLvl = 0; // means stats, not lvlup itself
    public int dexterityFromLvl = 0; // means stats, not lvlup itself
    public int enduranceFromLvl = 0; // means stats, not lvlup itself
    public int intelligenceFromLvl = 0; // means stats, not lvlup itself
    public int permIntelligenceBonus = 0;
    public int permStrengthBonus = 0;
    public int permDexterityBonus = 0;
    public int permEnduranceBonus = 0;
    public int strengthFromItems = 0;
    public int dexterityFromItems = 0;
    public int armorFromItems = 0;
    public int enduranceFromItems = 0;
    public int intelligenceFromItems = 0;
    public int juiceChargeFromItems = 0;
    public float speedFromItems = 0;
    public int[] baseResist = {0,0,0,0};
    public int[] resistFromItems = {0,0,0,0};
    public int baseAttackDamage = 9;
    public int attackDamageFromItems = 0;
    public int swordAttackRangeFromItems;
    public int swordAttackRangeFromTalent;
    public int unSpentStats = 10;
    public int waveDamage = 0;

    public PlayerSpellList allSpellList = new PlayerSpellList(this);
    public PlayerSpell[] equippedSpellList = new PlayerSpell[6];
    public BufferedImage[] hurtUp = new BufferedImage[9];
    public BufferedImage[] hurtRight = new BufferedImage[9];
    public BufferedImage[] hurtDown = new BufferedImage[9];
    public BufferedImage[] hurtLeft = new BufferedImage[9];
    private boolean hasHalfSpeed = false; // determine half speedd by items
    private int halfSpeedCounter = 0;
    public int diedToA1Boss = 0;
    public BufferedImage testImage;
    //Talent:
    public int unSpentTalentPoints = 1;
    public int unSpentSkillPoints = 1;

    //SKILL RELATED STUFF:
    public boolean haveImageShatter = false;
    public int castPoint1FromTalent;
    public int castPoint2FromTalent;
    public PlayerTalents playerTalents = new PlayerTalents(this);
    private int castCoolDownCounter = 0;
    private int speedWithoutDashing;
    public MomoJuice momoJuice = new MomoJuice(this);
    private int diagonalCounter = 0;
    public double uiSpeed = 0;
    public boolean areEnemiesNearby = false;
    public int checkNearbyCounter = 0;
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        defaultSpeed = 2;
        speed = defaultSpeed;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        getCastPlayerImage();
        getHurtPlayerImage();
        screenX = gp.screenWidth/2 - GamePanel.tileSize /2;
        screenY = gp.screenHeight/2 - GamePanel.tileSize /2;
        name = "csibészvitéz";

        solidArea = new Rectangle(
                3 * GamePanel.tileSize / 16,
                GamePanel.tileSize * 3/ 16,
                GamePanel.tileSize * 10 / 16,
                GamePanel.tileSize * 12 / 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        maxLife = 100; // savefile
        life = 100; // savefile
        maxMana = 50; // savefile
        mana = 50; //savefile

        Arrays.fill(resist, 0);

        //dashSpeed = speed + 8;
        //attackArea1.width = GamePanel.tileSize * 5 / 4;
        //attackArea1.height = GamePanel. tileSize * 5 / 4;
        attackDamage = 10;
        entityType = 0;
        refreshPlayerStatsNoItems();
    }

    public void makeDefaultHero(){
        // todo experience & items
        gp.harryPotterPuzzle.refreshPotions();
        level = 1;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 100;
        life = 100;
        maxMana = 50;
        mana = 50;

        experience = 0;
        previousLevelExp = 0;
        nextLevelExp = 500;
        gold = 100;
        unSpentStats = 0;
        dexterityFromLvl = 0;
        strengthFromLvl = 0;
        enduranceFromLvl = 0;
        intelligenceFromLvl = 0;
        maxManaFromUp = 0;
        maxLifeFromUp = 0;

        //todo change this if I implement estus flask upgrades
        momoJuice.baseMaxCharge = 5;
        momoJuice.charge = 5;
        momoJuice.healthValuePerCharge = 40;

        //talent:
        for (int i = 0; i < 7; i++) {
            PlayerTalents.talentList[i].currentPointsOnTalent = 0;
        }
        unSpentTalentPoints = 1;

        //spells:
        unSpentSkillPoints = 0;
        for (int i = 1; i < 12; i++) {
            allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell = 0;
        }
        allSpellList.allPlayerAvailableSpells[0].currentPointsOnSpell = 1;

        for (int i = 0; i < 5; i++) {
            equippedSpellList[i] = null;
        }
        equippedSpellList[5] = allSpellList.allPlayerAvailableSpells[0];

        worldX = 47 * gp.tileSize;
        worldY = 5 * gp.tileSize;
        direction = "down";

        haveWaveCloak = false;
        haveImageShatter = false;
        lifeRegenPointFromSkill = 0;
        fireResistFromSkill = 0;
        energyShieldLevel = 0;

        dashing = false;
        canCast = true;
        canAttack = true;
        casting = false;
        attacking = false;
        attackFrameCounter = 0;
        castFrameCounter = 0;
        canCastFromCoolDown = true;
        canStartDash = true;
        canDashFromCoolDown = true;
        stunned = false;
        stunnedCounter = 0;
        stunStrength = 0;
        canInteract = true;

        // clear progress:
        Arrays.fill(gp.progress.act1InteractedObjects, false);

        Arrays.fill(gp.progress.act1BookPickedUp, false);

        // clear storage pages inventory
        for (int i = 0; i < gp.allInventoryPages.storagePages.length; i++) {
            for (int j = 0; j < gp.allInventoryPages.storagePages[i].slots.length; j++) {
                for (int k = 0; k < gp.allInventoryPages.storagePages[i].slots[j].length; k++) {
                    gp.allInventoryPages.storagePages[i].slots[j][k].placedItem = null;
                }
            }
        }

        // clear player inventory page
        for (int i = 0; i < gp.allInventoryPages.playerInventoryPage.slots.length; i++) {
            for (int j = 0; j < gp.allInventoryPages.playerInventoryPage.slots[i].length; j++) {
                gp.allInventoryPages.playerInventoryPage.slots[i][j].placedItem = null;
            }
        }

        // clear player equipment page
        for (int i = 0; i < gp.allInventoryPages.playerEquipmentPage.slots.length; i++) {
            for (int j = 0; j < gp.allInventoryPages.playerEquipmentPage.slots[i].length; j++) {
                gp.allInventoryPages.playerEquipmentPage.slots[i][j].placedItem = null;
            }
        }

        refreshPlayerStatsWithItems();
    }

    //TODO save data from txt file
    public void lvlUp(){
        unSpentStats = unSpentStats + 5;
        unSpentSkillPoints ++;
        level ++;
        maxLifeFromUp ++;
        maxManaFromUp ++;
        refreshPlayerStatsNoItems();
        gp.playSE(19);
        gp.ui.addMessage("You have grown stronger.");
        gp.ui.addMessage("Press U to distribute your hard earned attribute points.");
        gp.ui.addMessage("Press T to distribute skill points.");
    }

    public void checkIfLvLUp(){
        if(experience >= nextLevelExp){
            previousLevelExp = nextLevelExp;
            nextLevelExp = nextLevelExp + DataBaseClass1.getRequiredExpForNextLevel(level);
            lvlUp();
        }
    }

    public void winExperience(int monsterLevel, int experienceValue, String monsterName){
        int wonValue = gp.dataBase1.levelDifferenceExperience(level, monsterLevel, experienceValue);
        experience += wonValue;
        if(gp.visibleExpValue){
            gp.ui.addMessage("Slaying " + monsterName + " gave you " + experienceValue + " EXP points.");
        }
    }

    public void kidSpecialWinExperience(int monsterLevel, int experienceValue, String monsterName){
        int wonValue = gp.dataBase1.levelDifferenceExperience(level, monsterLevel, experienceValue);
        experience += wonValue;
        if(gp.visibleExpValue){
            gp.ui.addMessage("Smacking the " + monsterName + " gave you " + experienceValue + " EXP points.");
        }
    }

    public void getAllItemStats(){
        makeItemStatsNull();
        InventoryPage currentInventoryPage = gp.allInventoryPages.playerEquipmentPage;
        for (int i = 0; i < 8; i++) {
            if(currentInventoryPage.slots[0][i].placedItem != null) {
                getOneItemStat(currentInventoryPage.slots[0][i].placedItem);
            }
        }
    }
    public void getOneItemStat(Item item){
        armorFromItems += item.armorStat;
        speedFromItems += item.speedStat;
        strengthFromItems += item.strengthStat;
        dexterityFromItems += item.dexterityStat;
        enduranceFromItems += item.enduranceStat;
        intelligenceFromItems += item.intellectStat;
        maxLifeFromItem += item.lifeStat;
        maxManaFromItem += item.manaStat;
        for (int i = 0; i < 4; i++) {
            resistFromItems[i] += item.resistStat[i];
        }
        attackDamageFromItems += item.damageStat;
        attackFramePointFromItems1 += item.attackFramePoint1;
        attackFramePointFromItems2 += item.attackFramePoint2;
        castPoint1FromItems += item.castFramePoint1;
        castPoint2FromItems += item.castFramePoint2;
        manaRegenPointFromItems += item.manaRegen;
        lifeRegenPointFromItems += item.lifeRegen;
        swordAttackRangeFromItems += item.attackRange;
        juiceChargeFromItems +=item.juiceCharge;
    }

    public void makeItemStatsNull(){
        armorFromItems = 0;
        speedFromItems = 0;
        strengthFromItems = 0;
        dexterityFromItems = 0;
        enduranceFromItems = 0;
        intelligenceFromItems = 0;
        maxLifeFromItem = 0;
        maxManaFromItem = 0;
        for (int i = 0; i < 4; i++) {
            resistFromItems[i] = 0;
        }
        attackDamageFromItems = 0;
        attackFramePointFromItems1 = 0;
        attackFramePointFromItems2 = 0;
        castPoint1FromItems = 0;
        castPoint2FromItems = 0;
        manaRegenPointFromItems = 0;
        lifeRegenPointFromItems = 0;
        swordAttackRangeFromItems = 0;
        juiceChargeFromItems = 0;
    }

    //reset stuff on changing items & level up etc...
    public void refreshPlayerStatsNoItems() {

        playerTalents.updatePlayerStatsFromTalent();

        speed = defaultSpeed + (int)(speedFromItems);
        speedWithoutDashing = speed;

        if(speedFromItems % 1 == 0.5){
            hasHalfSpeed = true;
        } else hasHalfSpeed = false;

        armor = armorFromItems;
        strength = baseStrength + strengthFromItems + strengthFromLvl + permStrengthBonus;
        dexterity = baseDexterity + dexterityFromItems + dexterityFromLvl + permDexterityBonus;
        endurance = baseEndurance + enduranceFromItems + enduranceFromLvl + permEnduranceBonus;
        intelligence = baseIntelligence + intelligenceFromItems + intelligenceFromLvl + permIntelligenceBonus;
        maxLife = maxLifeWithoutTalent() + maxLifeFromTalent;
        maxMana = maxManaWithoutTalent() + maxManaFromTalent;

        for (int i = 1; i < 4; i++) {
            resist[i] = Math.min(baseResist[i] + resistFromItems[i] + (int)((intelligence - 10) * 0.2), 75);
            if (i == 1){ //cloak buff
                resist[i] = Math.min(baseResist[i] + resistFromItems[i] + fireResistFromSkill + (int)((intelligence - 10) * 0.2), 75);
            }
        }

        resist[0] = Math.min(baseResist[0] + resistFromItems[0] + (int)((strength + endurance - 20) * 0.2), 75);

        attackDamage = (int)((baseAttackDamage + attackDamageFromItems) * ((double)strength / 70.0 + 1) * ((double)dexterity / 155.0 + 1.4));
        spellDmgModifier = (100.0 + intelligence) / 100;
        attackFramePoint1 = Math.max(baseAttackFramePoint1 - attackFramePointFromItems1 - dexterity / 18, 3);
        attackFramePoint2 = Math.max(baseAttackFramePoint2 - attackFramePointFromItems2 - dexterity / 13, 10);

        castPoint1 = Math.max(baseCastPoint1 - castPoint1FromTalent - castPoint1FromItems - intelligence / 16, 10);
        castPoint2 = Math.max(baseCastPoint2 - castPoint2FromTalent - castPoint2FromItems - intelligence / 16, 20);

        manaRegenPoint = baseManaRegenPoint - manaRegenPointFromItems - manaRegenPointFromTalent;
        lifeRegenPoint = baseLifeRegenPoint - lifeRegenPointFromItems - lifeRegenPointFromTalent - lifeRegenPointFromSkill;

        attackRangeIncrease = swordAttackRangeFromItems + swordAttackRangeFromTalent + getAttackRangeFromDex();
        momoJuice.maxCharge = momoJuice.baseMaxCharge + juiceChargeFromItems;

        if(checkIfShatterRemoved()){
            haveImageShatter = true;
        } else haveImageShatter = false;

        if(checkIfWaveCloakRemoved()){
            haveWaveCloak = true;
        } else haveWaveCloak = false;
    }

    public int getAttackRangeFromDex(){
        return (dexterity - 10)/4;
    }

    public void refreshPlayerStatsWithItems(){
        getAllItemStats();
        refreshPlayerStatsNoItems();
    }

    public int maxLifeWithoutTalent(){
        return (baseMaxLife + (int)(endurance * 1.2) + maxLifeFromUp + maxLifeFromItem);
    }

    public int maxManaWithoutTalent(){
        return baseMaxMana + (intelligence * 2) + maxManaFromUp + maxManaFromItem;
    }

    public void resetLevels(){
        // reskill
        gp.playSE(57);
        gp.ui.addMessage("You can reassign your attribute points, talent points and skill points.");

        //reset stats:
        unSpentStats = unSpentStats + strengthFromLvl + dexterityFromLvl + enduranceFromLvl + intelligenceFromLvl;
        strengthFromLvl = 0;
        dexterityFromLvl = 0;
        enduranceFromLvl = 0;
        intelligenceFromLvl = 0;

        //reset skills
        int tempInt = 0;
        for (int i = 0; i < allSpellList.allPlayerAvailableSpells.length; i++) {
            tempInt += allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell;
            allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell = 0;
        }
        unSpentSkillPoints = unSpentSkillPoints + tempInt;

        Arrays.fill(equippedSpellList, null);

        //reset talents:
        playerTalents.resetAllTalents();

        haveImageShatter = false;
        haveWaveCloak = false;
        energyShieldLevel = 0;

        refreshPlayerStatsNoItems();
        life = maxLife;
    }

    //changing map
    public void setDefaultValues() {
        worldX = 47 * GamePanel.tileSize;
        worldY = 5 * GamePanel.tileSize;
        direction = "down";
    }

    public void getCastPlayerImage(){
        int downimages = GamePanel.tileSize;
        castUp1 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 ,40,196, 196, gp.tileSize, gp.tileSize);
        castUp2 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 1,40,196, 196, gp.tileSize, gp.tileSize);
        castUp3 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 2,40,196, 196, gp.tileSize, gp.tileSize);
        castUp4 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 3,40,196, 196, gp.tileSize, gp.tileSize);
        castUp5 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 4,40,196, 196, gp.tileSize, gp.tileSize);
        castUp6 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 5,40,196, 196, gp.tileSize, gp.tileSize);
        castUp7 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 6,40,196, 196, gp.tileSize, gp.tileSize);
        castUp8 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 7,40,196, 196, gp.tileSize, gp.tileSize);
        castUp9 = setupSheet("/entity/updatedplayer/cast/castsheet_up", 30 + 256 * 8,40,196, 196, gp.tileSize, gp.tileSize);

        castRight1 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 ,40,196, 196, gp.tileSize, gp.tileSize);
        castRight2 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 1,40,196, 196, gp.tileSize, gp.tileSize);
        castRight3 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 2,40,196, 196, gp.tileSize, gp.tileSize);
        castRight4 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 3,40,196, 196, gp.tileSize, gp.tileSize);
        castRight5 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 4,40,196, 196, gp.tileSize, gp.tileSize);
        castRight6 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 5,40,196, 196, gp.tileSize, gp.tileSize);
        castRight7 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 6,40,196, 196, gp.tileSize, gp.tileSize);
        castRight8 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 7,40,196, 196, gp.tileSize, gp.tileSize);
        castRight9 = setupSheet("/entity/updatedplayer/cast/castsheet_right", 30 + 256 * 8,40,196, 196, gp.tileSize, gp.tileSize);

        castDown1 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 ,40,196, 216, gp.tileSize, downimages);
        castDown2 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 1,40,196, 216, gp.tileSize, downimages);
        castDown3 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 2,40,196, 216, gp.tileSize, downimages);
        castDown4 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 3,40,196, 216, gp.tileSize, downimages);
        castDown5 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 4,40,196, 216, gp.tileSize, downimages);
        castDown6 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 5,40,196, 216, gp.tileSize, downimages);
        castDown7 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 6,40,196, 216, gp.tileSize, downimages);
        castDown8 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 7,40,196, 216, gp.tileSize, downimages);
        castDown9 = setupSheet("/entity/updatedplayer/cast/castsheet_down", 30 + 256 * 8,40,196, 216, gp.tileSize, downimages);

        castLeft9 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 ,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft8 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 1,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft7 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 2,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft6 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 3,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft5 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 4,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft4 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 5,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft3 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 6,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft2 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 7,40,196, 196, gp.tileSize, gp.tileSize);
        castLeft1 = setupSheet("/entity/updatedplayer/cast/castsheet_left", 30 + 256 * 8,40,196, 196, gp.tileSize, gp.tileSize);
    }

    public void getPlayerImage() {
        up1 = setupSheet("/entity/updatedplayer/up/run_up_1", 0,0, 180, 190, GamePanel.tileSize, GamePanel.tileSize);
        up2 = setup("/entity/updatedplayer/up/run_up_2", GamePanel.tileSize, GamePanel.tileSize);
        up3 = setup("/entity/updatedplayer/up/run_up_3", GamePanel.tileSize, GamePanel.tileSize);
        up4 = setup("/entity/updatedplayer/up/run_up_4", GamePanel.tileSize, GamePanel.tileSize);
        up5 = setup("/entity/updatedplayer/up/run_up_5", GamePanel.tileSize, GamePanel.tileSize);
        up6 = setup("/entity/updatedplayer/up/run_up_6", GamePanel.tileSize, GamePanel.tileSize);
        up7 = setup("/entity/updatedplayer/up/run_up_7", GamePanel.tileSize, GamePanel.tileSize);
        up8 = setup("/entity/updatedplayer/up/run_up_8", GamePanel.tileSize, GamePanel.tileSize);
        up9 = setup("/entity/updatedplayer/up/run_up_9", GamePanel.tileSize, GamePanel.tileSize);
        down1 = setup("/entity/updatedplayer/down/run_down_1", GamePanel.tileSize, GamePanel.tileSize);
        down2 = setup("/entity/updatedplayer/down/run_down_2", GamePanel.tileSize, GamePanel.tileSize);
        down3 = setup("/entity/updatedplayer/down/run_down_3", GamePanel.tileSize, GamePanel.tileSize);
        down4 = setup("/entity/updatedplayer/down/run_down_4", GamePanel.tileSize, GamePanel.tileSize);
        down5 = setup("/entity/updatedplayer/down/run_down_5", GamePanel.tileSize, GamePanel.tileSize);
        down6 = setup("/entity/updatedplayer/down/run_down_6", GamePanel.tileSize, GamePanel.tileSize);
        down7 = setup("/entity/updatedplayer/down/run_down_7", GamePanel.tileSize, GamePanel.tileSize);
        down8 = setup("/entity/updatedplayer/down/run_down_8", GamePanel.tileSize, GamePanel.tileSize);
        down9 = setup("/entity/updatedplayer/down/run_down_9", GamePanel.tileSize, GamePanel.tileSize);
        left1 = setup("/entity/updatedplayer/left/run_left_1", GamePanel.tileSize, GamePanel.tileSize);
        left2 = setup("/entity/updatedplayer/left/run_left_2", GamePanel.tileSize, GamePanel.tileSize);
        left3 = setup("/entity/updatedplayer/left/run_left_3", GamePanel.tileSize, GamePanel.tileSize);
        left4 = setup("/entity/updatedplayer/left/run_left_4", GamePanel.tileSize, GamePanel.tileSize);
        left5 = setup("/entity/updatedplayer/left/run_left_5", GamePanel.tileSize, GamePanel.tileSize);
        left6 = setup("/entity/updatedplayer/left/run_left_6", GamePanel.tileSize, GamePanel.tileSize);
        left7 = setup("/entity/updatedplayer/left/run_left_7", GamePanel.tileSize, GamePanel.tileSize);
        left8 = setup("/entity/updatedplayer/left/run_left_8", GamePanel.tileSize, GamePanel.tileSize);
        left9 = setup("/entity/updatedplayer/left/run_left_9", GamePanel.tileSize, GamePanel.tileSize);
        right1 = setup("/entity/updatedplayer/right/run_right_1", GamePanel.tileSize, GamePanel.tileSize);
        right2 = setup("/entity/updatedplayer/right/run_right_2", GamePanel.tileSize, GamePanel.tileSize);
        right3 = setup("/entity/updatedplayer/right/run_right_3", GamePanel.tileSize, GamePanel.tileSize);
        right4 = setup("/entity/updatedplayer/right/run_right_4", GamePanel.tileSize, GamePanel.tileSize);
        right5 = setup("/entity/updatedplayer/right/run_right_5", GamePanel.tileSize, GamePanel.tileSize);
        right6 = setup("/entity/updatedplayer/right/run_right_6", GamePanel.tileSize, GamePanel.tileSize);
        right7 = setup("/entity/updatedplayer/right/run_right_7", GamePanel.tileSize, GamePanel.tileSize);
        right8 = setup("/entity/updatedplayer/right/run_right_8", GamePanel.tileSize, GamePanel.tileSize);
        right9 = setup("/entity/updatedplayer/right/run_right_9", GamePanel.tileSize, GamePanel.tileSize);

        idleDown1 = setup("/entity/updatedplayer/idledown/idle_down_1", GamePanel.tileSize, GamePanel.tileSize);
        idleDown2 = setup("/entity/updatedplayer/idledown/idle_down_2", GamePanel.tileSize, GamePanel.tileSize);
        idleDown3 = setup("/entity/updatedplayer/idledown/idle_down_3", GamePanel.tileSize, GamePanel.tileSize);
        idleDown4 = setup("/entity/updatedplayer/idledown/idle_down_4", GamePanel.tileSize, GamePanel.tileSize);
        idleDown5 = setup("/entity/updatedplayer/idledown/idle_down_5", GamePanel.tileSize, GamePanel.tileSize);
        idleDown6 = setup("/entity/updatedplayer/idledown/idle_down_6", GamePanel.tileSize, GamePanel.tileSize);
        idleDown7 = setup("/entity/updatedplayer/idledown/idle_down_7", GamePanel.tileSize, GamePanel.tileSize);
        idleDown8 = setup("/entity/updatedplayer/idledown/idle_down_8", GamePanel.tileSize, GamePanel.tileSize);
        idleDown9 = setup("/entity/updatedplayer/idledown/idle_down_9", GamePanel.tileSize, GamePanel.tileSize);
        idleUp1 = setup("/entity/updatedplayer/idleup/idle_up_1", GamePanel.tileSize, GamePanel.tileSize);
        idleUp2 = setup("/entity/updatedplayer/idleup/idle_up_2", GamePanel.tileSize, GamePanel.tileSize);
        idleUp3 = setup("/entity/updatedplayer/idleup/idle_up_3", GamePanel.tileSize, GamePanel.tileSize);
        idleUp4 = setup("/entity/updatedplayer/idleup/idle_up_4", GamePanel.tileSize, GamePanel.tileSize);
        idleUp5 = setup("/entity/updatedplayer/idleup/idle_up_5", GamePanel.tileSize, GamePanel.tileSize);
        idleUp6 = setup("/entity/updatedplayer/idleup/idle_up_6", GamePanel.tileSize, GamePanel.tileSize);
        idleUp7 = setup("/entity/updatedplayer/idleup/idle_up_7", GamePanel.tileSize, GamePanel.tileSize);
        idleUp8 = setup("/entity/updatedplayer/idleup/idle_up_8", GamePanel.tileSize, GamePanel.tileSize);
        idleUp9 = setup("/entity/updatedplayer/idleup/idle_up_9", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft1 = setup("/entity/updatedplayer/idleleft/idle_left_1", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft2 = setup("/entity/updatedplayer/idleleft/idle_left_2", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft3 = setup("/entity/updatedplayer/idleleft/idle_left_3", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft4 = setup("/entity/updatedplayer/idleleft/idle_left_4", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft5 = setup("/entity/updatedplayer/idleleft/idle_left_5", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft6 = setup("/entity/updatedplayer/idleleft/idle_left_6", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft7 = setup("/entity/updatedplayer/idleleft/idle_left_7", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft8 = setup("/entity/updatedplayer/idleleft/idle_left_8", GamePanel.tileSize, GamePanel.tileSize);
        idleLeft9 = setup("/entity/updatedplayer/idleleft/idle_left_9", GamePanel.tileSize, GamePanel.tileSize);
        idleRight1 = setup("/entity/updatedplayer/idleright/idle_right_1", GamePanel.tileSize, GamePanel.tileSize);
        idleRight2 = setup("/entity/updatedplayer/idleright/idle_right_2", GamePanel.tileSize, GamePanel.tileSize);
        idleRight3 = setup("/entity/updatedplayer/idleright/idle_right_3", GamePanel.tileSize, GamePanel.tileSize);
        idleRight4 = setup("/entity/updatedplayer/idleright/idle_right_4", GamePanel.tileSize, GamePanel.tileSize);
        idleRight5 = setup("/entity/updatedplayer/idleright/idle_right_5", GamePanel.tileSize, GamePanel.tileSize);
        idleRight6 = setup("/entity/updatedplayer/idleright/idle_right_6", GamePanel.tileSize, GamePanel.tileSize);
        idleRight7 = setup("/entity/updatedplayer/idleright/idle_right_7", GamePanel.tileSize, GamePanel.tileSize);
        idleRight8 = setup("/entity/updatedplayer/idleright/idle_right_8", GamePanel.tileSize, GamePanel.tileSize);
        idleRight9 = setup("/entity/updatedplayer/idleright/idle_right_9", GamePanel.tileSize, GamePanel.tileSize);

        dashUp = setup("/entity/updatedplayer/up/dash_up", GamePanel.tileSize, GamePanel.tileSize);
        dashDown = setup("/entity/updatedplayer/down/dash_down", GamePanel.tileSize, GamePanel.tileSize);
        dashRight = setup("/entity/updatedplayer/right/dash_right", GamePanel.tileSize, GamePanel.tileSize);
        dashLeft = setup("/entity/updatedplayer/left/dash_left", GamePanel.tileSize, GamePanel.tileSize);
    }

    private void getHurtPlayerImage() {
        for (int i = 0; i < 9; i++) {
            hurtUp[i] = setupSheet("/entity/updatedplayer/stunned/hurtsheet_up", 30 + i * 256,40,196, 196, GamePanel.tileSize, GamePanel.tileSize);
            hurtRight[i] = setupSheet("/entity/updatedplayer/stunned/hurtsheet_right", 30 + i * 256,40,196, 196, GamePanel.tileSize, GamePanel.tileSize);
            hurtDown[i] = setupSheet("/entity/updatedplayer/stunned/hurtsheet_down", 30 + i * 256,40,196, 196, GamePanel.tileSize, GamePanel.tileSize);
        }
        for (int i = 8; i > -1; i--) {
            hurtLeft[i] = setupSheet("/entity/updatedplayer/stunned/hurtsheet_left", 30 + i * 256,40,196, 196, GamePanel.tileSize, GamePanel.tileSize);
        }
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/entity/updatedplayer/attack/attack_up_1", (int)(GamePanel.tileSize * 4/3), (int)(GamePanel.tileSize * 19/12));
        attackUp2 = setup("/entity/updatedplayer/attack/attack_up_2", (int)(GamePanel.tileSize * 4/3), (int)(GamePanel.tileSize * 19/12));
        attackUpRight1 = setup("/entity/updatedplayer/attack/attack_upright_1", (int)(gp.tileSize * 19/12), (int)(GamePanel.tileSize * 19/12));
        attackUpRight2 = setup("/entity/updatedplayer/attack/attack_upright_2", (int)(gp.tileSize * 19/12), (int)(GamePanel.tileSize * 19/12));
        attackRight1 = setup("/entity/updatedplayer/attack/attack_right_1", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 4/3));
        attackRight2 = setup("/entity/updatedplayer/attack/attack_right_2", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 4/3));
        attackDown1 = setup("/entity/updatedplayer/attack/attack_down_1", (int)(gp.tileSize * 4/3), (int)(gp.tileSize * 19/12));
        attackDown2 = setup("/entity/updatedplayer/attack/attack_down_2", (int)(gp.tileSize * 4/3), (int)(gp.tileSize * 19/12));
        attackDownRight1 = setup("/entity/updatedplayer/attack/attack_downright_1", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 19/12));
        attackDownRight2 = setup("/entity/updatedplayer/attack/attack_downright_2", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 19/12));
        attackDownLeft1 = setup("/entity/updatedplayer/attack/attack_downleft_1", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 19/12));
        attackDownLeft2 = setup("/entity/updatedplayer/attack/attack_downleft_2", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 19/12));
        attackLeft1 = setup("/entity/updatedplayer/attack/attack_left_1", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 4/3));
        attackLeft2 = setup("/entity/updatedplayer/attack/attack_left_2", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 4/3));
        //attackUpLeft1 = setup("/entity/updatedplayer/attack/attack_upleft_1", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 19/12));
        //attackUpLeft2 = setup("/entity/updatedplayer/attack/attack_upleft_2", (int)(gp.tileSize * 19/12), (int)(gp.tileSize * 19/12));
        attackUpLeft1 = setup("/entity/updatedplayer/attack/attack_upleft_1", (int)(GamePanel.tileSize * 19/12), (int)(GamePanel.tileSize * 16/12));
        attackUpLeft2 = setup("/entity/updatedplayer/attack/attack_upleft_2", (int)(GamePanel.tileSize * 19/12), (int)(GamePanel.tileSize * 16/12));
    }

    public void update() {
        if(keyH.lPressed){
            testCommand();
        }

        if(!stunned){
            if (casting) {
                casting();
            }
            if (attacking){
                attacking();
            }
            else if (canMove) {
                if (keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed || dashing) {
                    currentlyMoving = true;
                    isCurrentlyIdle = false;

                    if (dashing) {
                      handleDashing();
                    }

                    getDirectionFromKey();

                    checkPlayerCollision();

                    //camera seems shaky a little, not no movement speed increase from diagonal movement
                    handleDiagonalMovement();

                    if (currentlyMoving) {
                        spriteWhileMoving();
                    }

                }
            }

            if(!currentlyMoving){
                handleNotMoving();
            }
            if(isCurrentlyIdle) {
                handleIdleSprite();
            }

            if(canInteract) {
               handleInteract();
            }
        }

        if(life <= 0){
            gp.gameState = gp.deathState;
        }
        refresh();
        drinkMomoJuice();
    }



    private void handleDiagonalMovement() {
        if (!collisionOn && !attacking) {
            speedCorrection1();

            boolean isDiagonal = keyH.leftPressed && (keyH.upPressed || keyH.downPressed)
                    || keyH.rightPressed && (keyH.upPressed || keyH.downPressed);

            if (isDiagonal) {
                //Add some movements speed when diagonal:
                if(!dashing){
                    diagonalCounter ++;
                    if(diagonalCounter == 3){
                        speed = speedWithoutDashing + 1;
                    } else if (diagonalCounter == 5){
                        speed = speed - 1;
                        diagonalCounter = 0;
                    }
                }

                // Alternate between moving in X and Y directions
                if (diagonalMoveToggle) {
                    worldX += (keyH.leftPressed && !keyH.rightPressed) ? -speed : (keyH.rightPressed && !keyH.leftPressed) ? speed : 0;
                } else {
                    worldY += (keyH.upPressed && !keyH.downPressed) ? -speed : (keyH.downPressed && !keyH.upPressed) ? speed : 0;
                }
                diagonalMoveToggle = !diagonalMoveToggle;

            } else {

                // Handle non-diagonal movement
                if (keyH.leftPressed) {
                    direction = "left";
                    worldX -= speed;
                }
                if (keyH.rightPressed) {
                    direction = "right";
                    worldX += speed;
                }
                if (keyH.upPressed) {
                    direction = "up";
                    worldY -= speed;
                }
                if (keyH.downPressed) {
                    direction = "down";
                    worldY += speed;
                }

            }
            speedCorrection2();
            if (!dashing && !isDiagonal){
                speed = speedWithoutDashing;
            }
        }
    }

    private void getDirectionFromKey() {
        if (keyH.leftPressed) {
            direction = "left";
        }
        if (keyH.rightPressed) {
            direction = "right";
        }
        if (keyH.upPressed) {
            direction = "up";
        }
        if (keyH.downPressed) {
            direction = "down";
        }
    }

    private void checkPlayerCollision(){
        //CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        //CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickupObject(objIndex);

        //CHECK NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc); // no collision but needed for interaction
        //interactNPC(npcIndex); //maybe do with obj instead

        // CHECK EVENTS
        //gp.eHandler.checkEvent(); //TOBE done with objects for now? I see no benefit to handle this with new class event
        //gp.keyH.enterPressed = false;

        // COLLISION MONSTER
        int monsterIndex = gp.cChecker.checkEntity(this, gp.fighters);
        //contactMonster(monsterIndex);
    }

    private void spriteWhileMoving() {
        spriteCounter++;
        if (spriteCounter > 3) {
            walkSpriteNum = (walkSpriteNum + 1) % 9;
            spriteCounter = 0;
        }
    }

    private void handleIdleSprite() {
        spriteCounter ++;
        if(spriteCounter > 3){
            idleSpriteNum = (idleSpriteNum + 1) % 9;
            spriteCounter = 0;
        }
    }

    private void handleNotMoving() {
        idleActivationCounter++;
        if (idleActivationCounter > 30){
            walkSpriteNum = 0;
            idleActivationCounter = 0;
            isCurrentlyIdle = true;
        }
    }

    public void handleDashing() {
        canDashFromCoolDown = false;
        speed = dashSpeed;
        dashingCounter++;
        if(dashingCounter == 1 ){
            new JumpImpactSmoke(gp,this);
            if(haveImageShatter){
                new ImageShatter(gp, (1024 - 32)/2  ,(768 - 32)/2,(1024 - 32)/2 + 175,(768 - 32)/2 + 175);
            }

        }
        canAttack = false;
        canCast = false;
        if (!keyH.downPressed && !keyH.upPressed && !keyH.leftPressed && !keyH.rightPressed) {
            switch (direction) {
                case "up":
                    keyH.upPressed = true;
                    break;
                case "right":
                    keyH.rightPressed = true;
                    break;
                case "down":
                    keyH.downPressed = true;
                    break;
                case "left":
                    keyH.leftPressed = true;
                    break;
            }
        }
        if (dashingCounter > 20) {
            if (!keyH.downPressed2) {
                keyH.downPressed = false;
            }
            if (!keyH.upPressed2) {
                keyH.upPressed = false;
            }
            if (!keyH.leftPressed2) {
                keyH.leftPressed = false;
            }
            if (!keyH.rightPressed2) {
                keyH.rightPressed = false;
            }
            dashingCounter = 0;
            dashing = false;
            speed = speedWithoutDashing;
            canAttack = true;
            canCast = true;
        }
    }

    public void draw(Graphics2D g2) {
        tempScreenX = screenX;
        tempScreenY = screenY;

        if(currentlyMoving){
            drawMoving();
        }
        else if (isCurrentlyIdle && !attacking && !casting) {
            drawIdle();
        }
        else if (attacking) {
            drawAttacking();
        }
        else if (casting){
            drawCast();
        }

        if(dashing){
            drawDashing();
            //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        if(stunned){
            drawStunned();
        }
        g2.drawImage(image, tempScreenX, tempScreenY,null);
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        currentlyMoving = false; // will be set to be true in the update function if moving, need to check if idle

        if(gp.visibleHitBox){
            //rect attacks
            g2.setColor(new Color(0, 225, 0, 128));
            g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

            //aura attacks
            g2.setColor(new Color(250,250,210, 100));
            int radius = auraRadius();
            g2.fillOval(screenMiddleX() - radius, screenMiddleY()-radius, 2 * radius, 2 * radius);
        }
    }

    public void handleInteract(){
        SuperObject currentObject = gp.cChecker.checkObjectToInteract(); //need this to call here before if to draw the glitter
        if (interactingButtonPressed) {
            interactingButtonPressed = false;
            if (currentObject != null) {
                currentObject.interact();
            }
        }
    }

    public void pickupObject(int i) {
        if(i != 999){
            gp.obj[gp.currentMap][i].pickup();
        }
    }

    public void interactNPC(int i) {
        if( i != 999){
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }
    public void interactDistanceNPC(int i){
        if (i != 999){
            gp.npc[gp.currentMap][i].dialogueIndex = 1;
        }
    }


    //dont use, only used from monster class
    public void contactMonster(int i) {
        if(i != 999){
            if(((Fighter)gp.fighters[gp.currentMap][i]).contactGraced == false){
                life = life - gp.fighters[gp.currentMap][i].damageOnContactValue;
                ((Fighter)gp.fighters[gp.currentMap][i]).contactGraced = true;
            }
        }
    }
    public void drawDashing(){
        if(direction.equals("left")){
            image = dashLeft;
        } else if(direction.equals("right")) {
            image = dashRight;
        } else if(direction.equals("up")){
            image = dashUp;
        } else if(direction.equals("down")){
            image = dashDown;
        }
    }

    public void drawAttacking(){
        switch (attackDirection) {
            case 1:
                tempScreenX = screenX - gp.tileSize * 1/6;
                tempScreenY = screenY - gp.tileSize * 8/12;
                if(attackSpriteNum == 1) {
                    image = attackUp1;
                }
                if(attackSpriteNum == 2) {
                    image = attackUp2;
                }
                direction = "up";
                break;
            case 2:
                tempScreenX = screenX - gp.tileSize * 1/12;
                tempScreenY = screenY - gp.tileSize * 8/12;
                if(attackSpriteNum == 1) {
                    image = attackUpRight1;
                }
                if(attackSpriteNum == 2) {
                    image = attackUpRight2;
                }
                direction = "up";
                break;
            case 3:
                tempScreenY = screenY - gp.tileSize * 1/6;
                if(attackSpriteNum == 1) {
                    image = attackRight1;
                }
                if(attackSpriteNum == 2) {
                    image = attackRight2;
                }
                direction = "right";
                break;
            case 4:
                if(attackSpriteNum == 1) {
                    image = attackDownRight1;
                }
                if(attackSpriteNum == 2) {
                    image = attackDownRight2;
                }
                direction = "down";
                break;
            case 5:
                tempScreenX = screenX - gp.tileSize * 1/4;
                tempScreenY = screenY - gp.tileSize * 1/12;
                if(attackSpriteNum == 1) {
                    image = attackDown1;
                }
                if(attackSpriteNum == 2) {
                    image = attackDown2;
                }
                direction = "down";
                break;
            case 6:
                tempScreenX = screenX - gp.tileSize * 4/6;
                tempScreenY = screenY - gp.tileSize * 1/12;
                if(attackSpriteNum == 1) {
                    image = attackDownLeft1;
                }
                if(attackSpriteNum == 2) {
                    image = attackDownLeft2;
                }
                direction = "down";
                break;
            case 7:
                tempScreenX = screenX - gp.tileSize * 4/6;
                tempScreenY = screenY - gp.tileSize * 1/6;
                if(attackSpriteNum == 1) {
                    image = attackLeft1;
                }
                if(attackSpriteNum == 2) {
                    image = attackLeft2;
                }
                direction = "left";
                break;
                /*
            case 8:
                tempScreenX = screenX - gp.tileSize * 4/6;
                tempScreenY = screenY - gp.tileSize * 6/12;
                if(attackSpriteNum == 1) {
                    image = attackUpLeft1;
                }
                if(attackSpriteNum == 2) {
                    image = attackUpLeft2;
                }
                direction = "up";
                break;*/
            case 8:
                tempScreenX = screenX - GamePanel.tileSize * 4/6;
                tempScreenY = screenY - GamePanel.tileSize * 7/12;
                if(attackSpriteNum == 1) {
                    image = attackUpLeft1;
                }
                if(attackSpriteNum == 2) {
                    image = attackUpLeft2;
                }
                direction = "up";
                break;
        }
    }

    private void drawStunned() {
        int stunnedSprite = (int)(stunnedCounter / (25.0 / 9.0)) + 1;
        if (stunnedSprite > 9) {
            stunnedSprite = 9;
            stunnedCounter = 0;
        }
        switch (direction){
            case "up":{
                image = hurtUp[stunnedSprite - 1];
                break;
            }
            case "right":{
                image = hurtRight[stunnedSprite - 1];
                break;
            }
            case "down":{
                image = hurtDown[stunnedSprite - 1];
                break;
            }
            case "left":{
                image = hurtLeft[stunnedSprite - 1];
                break;
            }
        }
    }

    public void attacking(){
        attackFrameCounter++;
        canAttack = false;
        canCast = false;
        canStartDash = false;
        canMove = false;
        canInteract = false;
        if(attackFrameCounter == 1){
            attackSpriteNum = 1;
        }
        if(attackFrameCounter == attackFramePoint1){
            gp.attacks.add(new PlayerAttack(this.gp,this, attackDirection));
        }
        if(attackFrameCounter == attackFramePoint1){
            attackSpriteNum = 2;
        }
        if(attackFrameCounter >= 25) {
            canInteract = true;
            attackFrameCounter = 0;
            attacking = false;
            canAttack = true;
            canCast = true;
            canStartDash = true;
            canMove = true;
        }
    }

    public void drawCast(){

        int framesPerSprite = castPoint1 / 9;
        int castSprite = (castFrameCounter / framesPerSprite) + 1;
        if (castSprite > 9) {
            castSprite = 9;
        }

        switch (direction){
            case "up": {
                if(castSprite == 1){
                    image = castUp1;
                } else if (castSprite == 2){
                    image = castUp2;
                } else if (castSprite == 3){
                    image = castUp3;
                } else if (castSprite == 4){
                    image = castUp4;
                } else if (castSprite == 5){
                    image = castUp5;
                } else if (castSprite == 6){
                    image = castUp6;
                } else if (castSprite == 7){
                    image = castUp7;
                } else if (castSprite == 8){
                    image = castUp8;
                } else if (castSprite == 9){
                    image = castUp9;
                }
                break;
            }
            case "right": {
                if(castSprite == 1){
                    image = castRight1;
                } else if (castSprite == 2){
                    image = castRight2;
                } else if (castSprite == 3){
                    image = castRight3;
                } else if (castSprite == 4){
                    image = castRight4;
                } else if (castSprite == 5){
                    image = castRight5;
                } else if (castSprite == 6){
                    image = castRight6;
                } else if (castSprite == 7){
                    image = castRight7;
                } else if (castSprite == 8){
                    image = castRight8;
                } else if (castSprite == 9){
                    image = castRight9;
                }
                break;
            }
            case "down": {
                if(castSprite == 1){
                    image = castDown1;
                } else if (castSprite == 2){
                    image = castDown2;
                } else if (castSprite == 3){
                    image = castDown3;
                } else if (castSprite == 4){
                    image = castDown4;
                } else if (castSprite == 5){
                    image = castDown5;
                } else if (castSprite == 6){
                    image = castDown6;
                } else if (castSprite == 7){
                    image = castDown7;
                } else if (castSprite == 8){
                    image = castDown8;
                } else if (castSprite == 9){
                    image = castDown9;
                }
                break;
            }
            case "left": {
                if(castSprite == 1){
                    image = castLeft1;
                } else if (castSprite == 2){
                    image = castLeft2;
                } else if (castSprite == 3){
                    image = castLeft3;
                } else if (castSprite == 4){
                    image = castLeft4;
                } else if (castSprite == 5){
                    image = castLeft5;
                } else if (castSprite == 6){
                    image = castLeft6;
                } else if (castSprite == 7){
                    image = castLeft7;
                } else if (castSprite == 8){
                    image = castLeft8;
                } else if (castSprite == 9){
                    image = castLeft9;
                }
                break;
            }
        }
    }

    public void drawIdle() {
        if (idleSpriteNum == 0){
            if(direction.equals("left")){
                image = idleLeft1;
            } else if(direction.equals("right")) {
                image = idleRight1;
            } else if(direction.equals("up")){
                image = idleUp1;
            } else if(direction.equals("down")){
                image = idleDown1;
            }
        } else if (idleSpriteNum == 1){
            if(direction.equals("left")){
                image = idleLeft2;
            } else if(direction.equals("right")) {
                image = idleRight2;
            } else if(direction.equals("up")){
                image = idleUp2;
            }  else if(direction.equals("down")){
                image = idleDown2;
            }
        } else if (idleSpriteNum == 2){
            if(direction.equals("left")){
                image = idleLeft3;
            } else if(direction.equals("right")) {
                image = idleRight3;
            } else if(direction.equals("up")){
                image = idleUp3;
            } else if(direction.equals("down")){
                image = idleDown3;
            }

        } else if (idleSpriteNum == 3){
            if(direction.equals("left")){
                image = idleLeft4;
            } else if(direction.equals("right")) {
                image = idleRight4;
            } else if(direction.equals("up")){
                image = idleUp4;
            } else if(direction.equals("down")){
                image = idleDown4;
            }

        } else if (idleSpriteNum == 4){
            if(direction.equals("left")){
                image = idleLeft5;
            } else if(direction.equals("right")) {
                image = idleRight5;
            } else if(direction.equals("up")){
                image = idleUp5;
            } else if(direction.equals("down")){
                image = idleDown5;
            }

        } else if (idleSpriteNum == 5){
            if(direction.equals("left")){
                image = idleLeft6;
            } else if(direction.equals("right")) {
                image = idleRight6;
            } else if(direction.equals("up")){
                image = idleUp6;
            } else if(direction.equals("down")){
                image = idleDown6;
            }

        } else if (idleSpriteNum == 6){
            if(direction.equals("left")){
                image = idleLeft7;
            } else if(direction.equals("right")) {
                image = idleRight7;
            } else if(direction.equals("up")){
                image = idleUp7;
            } else if(direction.equals("down")){
                image = idleDown7;
            }

        } else if (idleSpriteNum == 7){
            if(direction.equals("left")){
                image = idleLeft8;
            } else if(direction.equals("right")) {
                image = idleRight8;
            } else if(direction.equals("up")){
                image = idleUp8;
            }  else if(direction.equals("down")){
                image = idleDown8;
            }

        } else if (idleSpriteNum == 8){
            if(direction.equals("left")){
                image = idleLeft9;
            } else if(direction.equals("right")) {
                image = idleRight9;
            } else if(direction.equals("up")){
                image = idleUp9;
            }  else if(direction.equals("down")){
                image = idleDown9;
            }
        }
    }

    public void drawMoving(){
        if (walkSpriteNum == 0){
            if(direction.equals("left")){
                image = left1;
            } else if(direction.equals("right")) {
                image = right1;
            } else if(direction.equals("up")){
                image = up1;
            } else if(direction.equals("down")){
                image = down1;
            }
        } else if (walkSpriteNum == 1){
            if(direction.equals("left")){
                image = left2;
            } else if(direction.equals("right")) {
                image = right2;
            } else if(direction.equals("up")){
                image = up2;
            } else if(direction.equals("down")){
                image = down2;
            }
        } else if (walkSpriteNum == 2){
            if(direction.equals("left")){
                image = left3;
            } else if(direction.equals("right")) {
                image = right3;
            } else if(direction.equals("up")){
                image = up3;
            } else if(direction.equals("down")){
                image = down3;
            }

        } else if (walkSpriteNum == 3){
            if(direction.equals("left")){
                image = left4;
            } else if(direction.equals("right")) {
                image = right4;
            } else if(direction.equals("up")){
                image = up4;
            } else if(direction.equals("down")){
                image = down4;
            }

        } else if (walkSpriteNum == 4){
            if(direction.equals("left")){
                image = left5;
            } else if(direction.equals("right")) {
                image = right5;
            } else if(direction.equals("up")){
                image = up5;
            } else if(direction.equals("down")){
                image = down5;
            }

        } else if (walkSpriteNum == 5){
            if(direction.equals("left")){
                image = left6;
            } else if(direction.equals("right")) {
                image = right6;
            } else if(direction.equals("up")){
                image = up6;
            } else if(direction.equals("down")){
                image = down6;
            }

        } else if (walkSpriteNum == 6){
            if(direction.equals("left")){
                image = left7;
            } else if(direction.equals("right")) {
                image = right7;
            } else if(direction.equals("up")){
                image = up7;
            } else if(direction.equals("down")){
                image = down7;
            }

        } else if (walkSpriteNum == 7){
            if(direction.equals("left")){
                image = left8;
            } else if(direction.equals("right")) {
                image = right8;
            } else if(direction.equals("up")){
                image = up8;
            } else if(direction.equals("down")){
                image = down8;
            }

        } else if (walkSpriteNum == 8){
            if(direction.equals("left")){
                image = left9;
            } else if(direction.equals("right")) {
                image = right9;
            } else if(direction.equals("up")){
                image = up9;
            } else if(direction.equals("down")){
                image = down9;
            }
        }
    }
    public void loseLife(int value, int type, Entity originEntity, Entity sufferingEntity, boolean isContactDamage,
                         GamePanel gp) {

        // 0: physical
        // 1: fire
        // 2: cold
        // 3: lightning
        // 4: pure
        if (!damageAbsorbAvailable) {
            if (type == 0) {
                double resistPercentage = 1 - (double) (resist[0]) / 100;
                value = (int) Math.ceil((double) value * resistPercentage - armor);
            } else if (value != 4) {
                double resistPercentage = 1 - (double) (resist[type]) / 100;
                value = (int) Math.ceil((double) value * resistPercentage);
            }
            if (gp.gameState == gp.playState) {
                value = Math.max(1, value);

                //todo make mana shield levels
                if(energyShieldLevel == 0){
                    life = life - value;
                    new DamageNumber(value, originEntity, sufferingEntity, false, gp);
                }
                else{
                    mana = mana - (int)(allSpellList.getEnergyShieldPercentage(energyShieldLevel) * value);
                    life = life - value/2;
                    new DamageNumber(value, originEntity, sufferingEntity, false, gp);
                }
                if (life < 0) {
                    life = 0;
                }
            }
        }
        else {
            waveDamage = value;
            damageAbsorbAvailable = false;
            gp.playSE(39);
        }
    }

    public void winLife(int value){
        if (gp.gameState == gp.playState){
            life = Math.min(value + life, maxLife);
        }
    }

    public void dashing(){
        if(canDashFromCoolDown && canStartDash) {
            dashing = true;
            gp.playSE(15);
        }
    }

    public void casting(){
        castFrameCounter++;
        if(castFrameCounter == 1){
            canStartDash = false;
            canCast = false;
            canAttack = false;
            canMove = false;
            canInteract = false;
            canCastFromCoolDown = false;
        }

        if(castFrameCounter == castPoint1/2){
            makeSpellInstance();
        }

        if(castFrameCounter == castPoint1){
            canAttack = true;
            canStartDash = true;
            canMove = true;
            canInteract = true;
            castFrameCounter = 0;
            casting = false;
            canCast = true;
        }
    }

    public void makeSpellInstance(){
        if(equippedSpellList[currentlyCastingSpellSlot] != null){
            int spellToCast = equippedSpellList[currentlyCastingSpellSlot].uniqueSpellID;
            switch (spellToCast){
                case 0: new BlueFireBall(gp); break;
                case 1: new FireWall(gp); break;
                case 2: new FlameCloak(gp); break;
                case 3: new DragonTotem(gp); break;
                case 4: new IceBolt(gp); break;
                case 5:
                case 7:
                    gp.ui.addMessage("You are trying to cast a passive spell"); break;
                case 6: new FrozenOrb(gp); break;
                case 8: new LightningAttack(gp); break;
                case 9: new Nova(gp); break;
                case 10: new Teleport(gp); break;
                case 11: new EnergyShield(gp); break;
            }
        }
    }

    public boolean haveEnoughManaToCast(int i, int currentSpellLevel){
        int reqMana = gp.dataBase1.getRequiredManaForSpell(i, currentSpellLevel);
        if(reqMana <= mana){
            return true;
        }
        gp.ui.addMessage("Not enough mana.");
        return false;
    }

    private void drinkMomoJuice() {
        if(keyH.qPressed){
            keyH.qPressed = false;
            momoJuice.drinkCharge();
        }
    }

    public void respawn(int[] respawnData){
        //respawn sound
        gp.playSE(73);

        //for now same as maptransition object but enemy health set to max and player health set to half
        gp.gameState = gp.transitionState;

        //puzzle
        gp.resetPuzzles();

        dashing = false;
        canCast = true;
        canAttack = true;
        casting = false;
        attacking = false;
        attackFrameCounter = 0;
        castFrameCounter = 0;
        dashingCounter = 0;
        canCastFromCoolDown = true;

        canStartDash = true;
        canDashFromCoolDown = true;

        stunned = false;
        stunnedCounter = 0;
        stunStrength = 0;

        canInteract = true;

        gp.currentMap = respawnData[0];

        momoJuice.charge = momoJuice.maxCharge;

        if (gp.currentMap == 1){
            gp.aSetter.ghostsNearCemetery();
        }

        gp.mapBlockManager.update();
        gp.tileM.getTileManagerDataCurrentMap(gp.currentMap);
        gp.map.createMapScreenImage();

        gp.spells.clear();
        gp.attacks.clear();

        gp.interactObjects.clear();
        for (int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] != null){
                if(gp.obj[gp.currentMap][i].interactable){
                    gp.interactObjects.add(gp.obj[gp.currentMap][i]);
                }
            }
        }
        for (int i = 0; i < gp.fighters.length; i++) {
            for (int j = 0; j < gp.fighters[i].length; j++) {
                if (gp.fighters[i][j] != null) {
                    gp.fighters[i][j].life = gp.fighters[i][j].maxLife;
                }
            }
        }

        gp.allFightingEntities.clear();
        for (int i = 0; i < gp.fighters[1].length; i++) {
            if(gp.fighters[gp.currentMap][i] != null){
                gp.allFightingEntities.add(gp.fighters[gp.currentMap][i]);
            }
        }
        gp.allFightingEntities.add(gp.player);

        gp.eManager.update();

        gp.player.worldX = respawnData[1];
        gp.player.worldY = respawnData[2];

        gp.player.life = maxLife;
        gp.player.mana = maxMana;

        refreshPlayerStatsNoItems();
        gp.startSinging(gp.currentMap);
    }

    public void winMana(int value){
        gp.player.mana = Math.min(gp.player.mana + value, maxMana);
    }

    public void refresh(){
        manaRegenCounter++;
        lifeRegenCounter++;

        checkNearbyCounter++;

        if(checkNearbyCounter >= 60){
            checkNearbyCounter = 0;
            boolean foundEnemy = false;
            for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                Entity tempEntity = gp.allFightingEntities.get(i);
                if(tempEntity.middleDistance(this) < gp.tileSize * 10 && tempEntity != this
                        && tempEntity.isHostile(this, tempEntity)){
                    foundEnemy = true;
                    break;
                }
            }
            areEnemiesNearby = foundEnemy;
        }


        if(!areEnemiesNearby){
            if (random.nextInt(120) + 1 >= 119){
                winLife(1);
                winMana(1);
            }
        }

        if(mana <= 0){
            mana = 0;
            energyShieldLevel = 0;
            // can happen if hit by certain amount with energy shield on while casting(?)
        }

        if(lifeRegenCounter > lifeRegenPoint){
            winLife(1);
            lifeRegenCounter = 0;
        }
        if(manaRegenCounter > manaRegenPoint){
            manaRegenCounter = 0;
            mana++;
        }

        if(haveWaveCloak & !damageAbsorbAvailable){
            waveCounter++;
            if(waveCounter >= 18 * 60){
                damageAbsorbAvailable = true;
                waveCounter = 0;
            }
        }
        if(!canDashFromCoolDown){
            dashingCoolDownCounter++;
            if(dashingCoolDownCounter > dashingCoolDown){
                dashingCoolDownCounter = 0;
                canDashFromCoolDown = true;
            }
        }
        if(!canCastFromCoolDown){
            castCoolDownCounter++;
            if(castCoolDownCounter >= castPoint2){
                canCastFromCoolDown = true;
            }
        }
        if(stunned) {
            stunStrength--;
            stunnedCounter ++;

            if(dashing){
                dashing = false;
            }

            if(stunStrength < 0){
                stunned = false;
                stunStrength = 0;
                stunnedCounter = 0;

                dashingCounter = 0;
                speed = speedWithoutDashing;

                attackFrameCounter = 0;
                canAttack = true;
                canCast = true;
                canStartDash = true;
                canMove = true;
                canInteract = true;
                castFrameCounter = 0;
            }
        }

        momoJuice.refreshMomo();
        momoJuice.updateImage();
    }

    public boolean checkIfShatterRemoved2(){
        for (int i = 0; i < gp.player.equippedSpellList.length; i++) {
            if(gp.player.equippedSpellList[i] != null){
                if(gp.player.equippedSpellList[i].uniqueSpellID == 5){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfShatterRemoved(){
        for (int i = 0; i < equippedSpellList.length; i++) {
            if(equippedSpellList[i] != null){
                if(equippedSpellList[i].uniqueSpellID == 5){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfWaveCloakRemoved(){
        for (int i = 0; i < equippedSpellList.length; i++) {
            if(equippedSpellList[i] != null){
                if(equippedSpellList[i].uniqueSpellID == 7){
                    return true;
                }
            }
        }
        return false;
    }

    public void testCommand() {
        //gp.ui.drawTestOn = !gp.ui.drawTestOn;
        gp.keyH.lPressed = false;

        //new Teleport(gp);
    }

    public int auraRadius() {
        int radius = (int) Math.sqrt(Math.pow(solidArea.width / 2, 2) + Math.pow(solidArea.height/2, 2));
        return (int)(radius * 0.9);
    }

    public void tryEquipThisSpell(int uniqueSpellID, int spellSlotNumber){
        if(allSpellList.allPlayerAvailableSpells[uniqueSpellID].currentPointsOnSpell > 0 && allSpellList.allPlayerAvailableSpells[uniqueSpellID].showHelp){
            for (int i = 0; i < 6; i++) {
                if(equippedSpellList[i] != null){
                    if(equippedSpellList[i].uniqueSpellID == uniqueSpellID){
                        equippedSpellList[i] = null;
                    }
                }
            }
            equippedSpellList[spellSlotNumber] = allSpellList.allPlayerAvailableSpells[uniqueSpellID];

            if(uniqueSpellID == 5){
                haveImageShatter = true;
            } else if (uniqueSpellID == 7){
                haveWaveCloak = true;
            }

            if(!checkIfShatterRemoved()){
                haveImageShatter = false;
            }

            if(!checkIfWaveCloakRemoved()){
                haveWaveCloak = false;
            }
            gp.playSE(34);
        }
    }

    public void pickUpTalentBook(){
        unSpentTalentPoints ++;
        gp.playSE(44);
        gp.ui.addMessage("You found a talent book.");
        gp.ui.addMessage("Press T to spend talent point.");
    }

    @Override
    public void receiveStun(int strength) {
        if(!stunned){
            attacking = false;
            casting = false;
            stunned = true;
            stunStrength = strength;
            gp.playSE(45);
        }
    }

    public void speedCorrection1(){
        halfSpeedCounter ++;
        if (hasHalfSpeed) {
            if (halfSpeedCounter > 1) {
                speed = speed + 1;
            }
        }
    }

    public void speedCorrection2(){
        if(hasHalfSpeed) {
            if (halfSpeedCounter > 1) {
                speed = speed - 1;
                halfSpeedCounter = 0;
            }
        }
    }

    public void winGold(int value){
        gold = gold + value;
    }
}