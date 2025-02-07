package entity.playerrelated;

import entity.Player;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;

public class PlayerTalents {
    Player player;

//    0: life %;
//    1: mana %;
//    2: sword attack range;
//    3: life regen;
//    4: mana regen;
//    5: spell casting speed;
//    6: spell cooldown speed;

    public static Talent[] talentList = new Talent[7];

    public PlayerTalents(Player player){
        this.player = player;
        createTalents(); // from save file
        updateTalentList();
    }

    public void createTalents(){ // save file
        //int imageSize = 95;
        //(baseMaxLife + (int)((endurance - 10) * 1.2) + maxLifeFromUp);

        Talent talent0 = new Talent(player);
        talentList[0] = talent0;
        talent0.description = "Life%";
        talent0.oneLevelValue = 1;
        talent0.image = setupSheet("/ui/talentpage/talentsheet", 0, 0, 95, 95);

        Talent talent1 = new Talent(player);
        talentList[1] = talent1;
        talent1.description = "mana%";
        talent1.oneLevelValue = 1;
        talent1.image =  setupSheet("/ui/talentpage/talentsheet", 95, 0, 95, 95);
        talent1.currentPointsOnTalent = 1;

        Talent talent2 = new Talent(player);
        talentList[2] = talent2;
        talent2.description = "sword attack range";
        talent2.oneLevelValue = 1;
        talent2.currentPointsOnTalent = 0;
        talent2.image = setupSheet("/ui/talentpage/talentsheet", 190, 0, 95, 95);

        Talent talent3 = new Talent(player);
        talentList[3] = talent3;
        talent3.description = "life regen";
        talent3.oneLevelValue = 1;
        talent3.image = setupSheet("/ui/talentpage/talentsheet", 285, 0, 95, 95);

        Talent talent4 = new Talent(player);
        talentList[4] = talent4;
        talent4.description = "mana regen";
        talent4.oneLevelValue = 1;
        talent4.image = setupSheet("/ui/talentpage/talentsheet", 380, 0, 95, 95);

        Talent talent5 = new Talent(player);
        talentList[5] = talent5;
        talent5.description = "spell cast time";
        talent5.oneLevelValue = 1;
        talent5.image = setupSheet("/ui/talentpage/talentsheet", 475, 0, 95, 95);

        Talent talent6 = new Talent(player);
        talentList[6] = talent6;
        talent6.description = "spell cooldown";
        talent6.oneLevelValue = 1;
        talent6.image = setupSheet("/ui/talentpage/talentsheet", 570, 0, 95, 95);
    }

    public void updateTalentList() {
        for (Talent talent : talentList) {
            talent.finalValue = talent.currentPointsOnTalent * talent.oneLevelValue;
            talentDescription();
        }
    }

    public void resetAllTalents(){
        int points = 0;
        for (int i = 0; i < talentList.length; i++) {
            points += talentList[i].currentPointsOnTalent;
            talentList[i].currentPointsOnTalent = 0;
        }
        player.unSpentTalentPoints = player.unSpentTalentPoints + points;
        updateTalentList();
        updatePlayerStatsFromTalent();
    }

    public void updatePlayerStatsFromTalent() {
        for (int i = 0; i < talentList.length; i++) {
            switch (i){
                case 0:
                    player.maxLifeFromTalent = (int)(player.maxLifeWithoutTalent() * (double)(talentList[i].finalValue) / 100);
                    break;
                case 1:
                    player.maxManaFromTalent = (int)(player.maxManaWithoutTalent() * (double)(talentList[i].finalValue) / 100);
                    break;
                case 2:
                    player.swordAttackRangeFromTalent = talentList[i].finalValue;
                    break;
                case 3:
                    player.lifeRegenPointFromTalent = talentList[i].finalValue;
                    break;
                case 4:
                    player.manaRegenPointFromTalent = talentList[i].finalValue;
                    break;
                case 5:
                    player.castPoint1FromTalent = talentList[i].finalValue;
                    break;
                case 6:
                    player.castPoint2FromTalent = talentList[i].finalValue;
                    break;
            }
        }

    }
    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions// noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void talentDescription(){
        //(baseMaxLife + (int)((endurance - 10) * 1.2) + maxLifeFromUp);
        for (int i = 0; i < talentList.length; i++) {
            switch (i){
                case 0:
                    talentList[i].description =  "Life increase\nEach point increases life by 1%." +
                            "\n\nBase life: " + Player.baseMaxLife +
                            "\nLife from level up: " + player.maxLifeFromUp +
                            "\nLife from items: " + player.maxLifeFromItem +
                            "\nLife from endurance: " + (int)((player.endurance) * 1.2) +
                            "\nThis talent increases life by: " + player.maxLifeFromTalent +
                            "\nCurrent maximum life: " + player.maxLife;
                    break;
                case 1:
                    talentList[i].description = "Mana increase\nEach point increases mana by 1%." +
                            "\n\nBase mana: " + Player.baseMaxMana +
                            "\nMana from level up: " + player.maxManaFromUp +
                            "\nMana from items: " + player.maxManaFromItem +
                            "\nMana from intelligence: " + (int)((player.intelligence) * 2) +
                            "\nThis talent increases mana by: " + player.maxManaFromTalent +
                            "\nCurrent maximum mana: " + player.maxMana;
                    break;
                case 2:
                    talentList[i].description = "Sword attack range:" +
                            "\nEach point increases sword attack range by 1 unit" +
                            "\n\nBase attack range: 80 units" +
                            "\nAttack range increase from dexterity: " + player.getAttackRangeFromDex() +
                            "\nAttack range from items: " + player.swordAttackRangeFromItems +
                            "\nAttack range from this talent: " + player.swordAttackRangeFromTalent +
                            "\nCurrent attack range: " + (player.attackRangeIncrease + 80);
                    break;
                case 3:
                    double lifeRegenPerSecond = (60 / (double) player.lifeRegenPoint);
                    talentList[i].description = "Life regeneration\n" +
                            "\nEach point increases life regeneration by about 1%"+
                            "\n\nBase life regeneration: 0.50 life/sec" +
                            "\nCurrent life regeneration: " + String.format(Locale.US, "%.2f", lifeRegenPerSecond) + "/sec";
                    break;
                case 4:
                    double manaRegenPerSecond = (60 / (double) player.manaRegenPoint);
                    talentList[i].description = "Mana regeneration" +
                            "\nEach point increases mana regeneration by about 1%"+
                            "\n\nBase mana regeneration: 0.50/sec" +
                            "\nCurrent mana regeneration: " + String.format(Locale.US, "%.2f", manaRegenPerSecond) +"/sec";
                    break;
                case 5:
                    talentList[i].description = "Faster spell cast speed" +
                            "\nThis means the time that passes between pressing skill button and the actual spell appearing." +
                            "\n\nBase cast time: " + String.format(Locale.US, "%.2f", (double) Player.baseCastPoint1 / 60) + " sec" +
                            "\nCurrent cast time: " + String.format(Locale.US, "%.2f", (double)player.castPoint1 / 60) + " sec";
                    break;
                case 6:
                    talentList[i].description = "Lower spell cooldown" +
                            "\n\nBase cooldown time " + String.format(Locale.US, "%.2f", (double) Player.baseCastPoint2 / 60) + " sec" +
                            "\nCurrent cooldown time: " + String.format(Locale.US, "%.2f", (double)player.castPoint2/ 60) + " sec";
                    break;
            }
        }
    }
    public void updateTalentAndPlayer(){
        updateTalentList();
        updatePlayerStatsFromTalent();
    }

    public BufferedImage setupSheet(String imageName, int x, int y, int width, int height) {
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}