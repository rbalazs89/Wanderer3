package item;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ItemGenerator {
    GamePanel gp;
    public ItemGenerator(GamePanel gp){
        this.gp = gp;
    }

    /**
     TYPE:
     helm = 101....130 included
     101 ...110 -> tier 0 rarity 0;
     111 ... 114 -> tier 0 rarity 1;
     115 -> tier 0 rarity 2
     116 ... 125 -> tier 1 rarity 0;
     125 ... 129 -> tier 1 rarity 1
     130 -> tier 1 rarity 2

     armor = 201...
     weapon = 301...
     shield = 401....
     gloves = 501...
     boots = 601...
     belt = 701....
     amulet = 801...

     TIER:
     only 0 and 1 for now

     RARITY:
     0, 1, 2 for now

     */

    public Item generateItemDrop(int monsterLevel, int minimumTier, boolean isBoss){
        int type = 0;
        int tier = 0;
        int rarity = 0;

        type = generateRandomType();
        tier = generateRandomTier(monsterLevel, minimumTier);
        rarity = generateRandomRarity(isBoss);

        int itemID = generateRandomItemIDBasedOnGivenAttributes(type, tier, rarity);

        return generateItemBasedOnID(itemID);
    }

    private int generateRandomRarity(boolean isBoss) {
        Random random = new Random();
        double randomValue = random.nextDouble();

        if (isBoss) {
            if (randomValue < 0.50) {
                return 0; // 50% for rarity 0
            } else if (randomValue < 0.80) {
                return 1; // 30% for rarity 1
            } else {
                return 2; // 20% for rarity 2
            }
        } else {
            if (randomValue < 0.70) {
                return 0; // 70% for rarity 0
            } else if (randomValue < 0.95) {
                return 1; // 25% for rarity 1
            } else {
                return 2; // 5% for rarity 2
            }
        }
    }

    private int generateRandomTier(int monsterLevel, int minimumTier) {
        Random random = new Random();

        double probabilityOfTier1 = (monsterLevel - 1) / 9.0; // linear transition from 0 at level 1 to 1 at level 10

        if (minimumTier > 0) {
            return minimumTier;
        } else if (random.nextDouble() < probabilityOfTier1) {
            return 1;
        } else {
            return 0;
        }
    }


    public int generateRandomItemIDBasedOnGivenAttributes(int type, int tier, int rarity){

        Random random = new Random();
        int generatedNumber = 0;

        generatedNumber = type * 100 + tier * 15;
        int addNumber = 0;

        if(rarity == 0){
            addNumber = random.nextInt(9) + 1;
        } else if (rarity == 1){
            addNumber = random.nextInt(3) + 1;
            addNumber = addNumber + 10;
        } else if (rarity == 2){
            addNumber = 15;
        }
        generatedNumber += addNumber;
        return generatedNumber;
    }

    private int generateRandomType() {
        Random random = new Random();
        double randomValue = random.nextDouble() * 7.5; // total weight is 7.5

        if (randomValue < 1) {
            return 1;
        } else if (randomValue < 2) {
            return 2;
        } else if (randomValue < 3) {
            return 3;
        } else if (randomValue < 4) {
            return 4;
        } else if (randomValue < 5) {
            return 5;
        } else if (randomValue < 6) {
            return 6;
        } else if (randomValue < 7) {
            return 7;
        } else {
            return 8; // Amulet
        }
    }

    public Item generateItemBasedOnID(int i){
        if(i == 0){
            System.out.println("this should never appear, itemID is 0");
            return null;
        }

        Item myItem = new Item();
        switch (i){
            case 101:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.strengthStat = 1;
                myItem.lifeStat = 4;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;
            case 102:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.dexterityStat = 1;
                myItem.resistStat[1] = 3;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;
            case 103:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.enduranceStat = 1;
                myItem.resistStat[2] = 3;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;
            case 104:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.intellectStat = 1;
                myItem.resistStat[3] = 4;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;
            case 105:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.intellectStat = 1;
                myItem.resistStat[3] = 5;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;
            case 106:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 5;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 1;
                break;
            case 107:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[2] = 5;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 1;
                break;
            case 108:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[3] = 5;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;
            case 109:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[0] = 1;
                myItem.saleGoldCost = 40;
                myItem.imageNumber = 1;
                break;
            case 110:
                myItem.name = "Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.strengthStat = 1;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;
            case 111:
                myItem.name = "Horned Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.strengthStat = 1;
                myItem.dexterityStat = 1;
                myItem.manaStat = 3;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 2;
                break;
            case 112:
                myItem.name = "Horned Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.strengthStat = 1;
                myItem.dexterityStat = 1;
                myItem.lifeStat = 3;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 2;
                break;
            case 113:
                myItem.name = "Horned Helm";
                myItem.type = "helm";
                myItem.armorStat = 1;
                myItem.strengthStat = 1;
                myItem.intellectStat = 1;
                myItem.lifeStat = 4;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 2;
                break;
            case 114:
                myItem.name = "Horned Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 3;
                myItem.resistStat[2] = 4;
                myItem.lifeStat = 4;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 2;
                break;
            case 115:
                myItem.name = "Iron Sentinel";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 5;
                myItem.resistStat[2] = 4;
                myItem.lifeStat = 4;
                myItem.manaRegen = 1;
                myItem.saleGoldCost = 62;
                myItem.imageNumber = 2;
                break;
            case 116:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 4;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 3;
                break;
            case 117:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[2] = 4;
                myItem.manaStat = 7;
                myItem.saleGoldCost = 40;
                myItem.imageNumber = 3;
                break;
            case 118:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[3] = 4;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 3;
                break;
            case 119:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.intellectStat = 2;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 43;
                myItem.imageNumber = 3;
                break;
            case 120:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.dexterityStat = 2;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 43;
                myItem.imageNumber = 3;
                break;
            case 121:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[0] = 1;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 43;
                myItem.imageNumber = 3;
                break;
            case 122:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 4;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 44;
                myItem.imageNumber = 3;
                break;
            case 123:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[2] = 4;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 44;
                myItem.imageNumber = 3;
                break;
            case 124:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.resistStat[3] = 4;
                myItem.manaStat = 8;
                myItem.saleGoldCost = 44;
                myItem.imageNumber = 3;
                break;
            case 125:
                myItem.name = "Sallet";
                myItem.type = "helm";
                myItem.armorStat = 3;
                myItem.strengthStat = 3;
                myItem.saleGoldCost = 45;
                myItem.imageNumber = 3;
                break;
            case 126:
                myItem.name = "Full Helm";
                myItem.type = "helm";
                myItem.armorStat = 3;
                myItem.strengthStat = 4;
                myItem.intellectStat = 2;
                myItem.manaStat = 7;
                myItem.saleGoldCost = 51;
                myItem.imageNumber = 4;
                break;
            case 127:
                myItem.name = "Full Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 2;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;
            case 128:
                myItem.name = "Full Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.enduranceStat = 2;
                myItem.lifeRegen = 1;
                myItem.resistStat[1] = 1;
                myItem.saleGoldCost = 51;
                myItem.imageNumber = 4;
                break;
            case 129:
                myItem.name = "Full Helm";
                myItem.type = "helm";
                myItem.armorStat = 2;
                myItem.enduranceStat = 2;
                myItem.resistStat[2] = 4;
                myItem.resistStat[3] = 5;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;
            case 130:
                myItem.name = "Vanguard";
                myItem.type = "helm";
                myItem.armorStat = 3;
                myItem.lifeStat = 12;
                myItem.dexterityStat = 2;
                myItem.lifeRegen = 1;
                myItem.enduranceStat = 2;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 80;
                myItem.imageNumber = 4;
                break;
            case 201:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.lifeStat = 5;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;
            case 202:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 1;
                break;
            case 203:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;
            case 204:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;
            case 205:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.intellectStat = 1;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;
            case 206:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.manaStat = 7;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;
            case 207:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.attackRange = 1;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;
            case 208:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.resistStat[0] = 2;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;
            case 209:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 4;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;
            case 210:
                myItem.name = "Quilted Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.resistStat[2] = 8;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;
            case 211:
                myItem.name = "Leather Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.resistStat[2] = 8;
                myItem.resistStat[3] = 7;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;
            case 212:
                myItem.name = "Leather Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.strengthStat = 1;
                myItem.resistStat[3] = 7;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;
            case 213:
                myItem.name = "Leather Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.enduranceStat = 1;
                myItem.resistStat[1] = 7;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;
            case 214:
                myItem.name = "Leather Armor";
                myItem.type = "armor";
                myItem.armorStat = 2;
                myItem.intellectStat = 1;
                myItem.resistStat[0] = 3;
                myItem.manaStat = 7;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;
            case 215:
                myItem.name = "Hunter's Garb";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.intellectStat = 2;
                myItem.resistStat[0] = 2;
                myItem.resistStat[1] = 6;
                myItem.resistStat[2] = 6;
                myItem.resistStat[3] = 6;
                myItem.manaStat = 7;
                myItem.lifeStat = 8;
                myItem.saleGoldCost = 75;
                myItem.imageNumber = 2;
                break;
            case 216:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 3;
                break;
            case 217:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.manaStat = 12;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 3;
                break;
            case 218:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 43;
                myItem.imageNumber = 3;
                break;
            case 219:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 3;
                break;
            case 220:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.intellectStat = 2;
                myItem.manaStat = 4;
                myItem.saleGoldCost = 46;
                myItem.imageNumber = 3;
                break;
            case 221:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.dexterityStat = 2;
                myItem.manaStat = 1;
                myItem.saleGoldCost = 45;
                myItem.imageNumber = 3;
                break;
            case 222:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.dexterityStat = 2;
                myItem.lifeStat = 2;
                myItem.saleGoldCost = 44;
                myItem.imageNumber = 3;
                break;
            case 223:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.resistStat[0] = 3;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 43;
                myItem.imageNumber = 3;
                break;
            case 224:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.resistStat[1] = 8;
                myItem.manaStat = 2;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 3;
                break;
            case 225:
                myItem.name = "Breast Plate";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.manaStat = 4;
                myItem.lifeStat = 12;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 3;
                break;
            case 226:
                myItem.name = "Plate armor";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.manaStat = 12;
                myItem.lifeStat = 12;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;
            case 227:
                myItem.name = "Plate armor";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.manaStat = 7;
                myItem.lifeStat = 15;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;
            case 228:
                myItem.name = "Plate armor";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.resistStat[1] = 25;
                myItem.lifeStat = 15;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;
            case 229:
                myItem.name = "Plate armor";
                myItem.type = "armor";
                myItem.armorStat = 3;
                myItem.resistStat[0] = 5;
                myItem.lifeStat = 8;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;

            case 230:
                myItem.name = "Titan’s Plate";
                myItem.type = "armor";
                myItem.armorStat = 4;
                myItem.resistStat[0] = 3;
                myItem.resistStat[1] = 9;
                myItem.resistStat[3] = 10;
                myItem.lifeStat = 12;
                myItem.manaStat = 12;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;

            case 301:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 5;
                myItem.attackRange = 3;
                myItem.saleGoldCost = 15;
                myItem.imageNumber = 1;
                break;

            case 302:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.saleGoldCost = 12;
                myItem.imageNumber = 1;
                break;

            case 303:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.lifeStat = 4;
                myItem.saleGoldCost = 13;
                myItem.imageNumber = 1;
                break;

            case 304:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.lifeStat = 6;
                myItem.saleGoldCost = 15;
                myItem.imageNumber = 1;
                break;

            case 305:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 15;
                myItem.imageNumber = 1;
                break;

            case 306:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 13;
                myItem.imageNumber = 1;
                break;

            case 307:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 1;
                myItem.intellectStat = 5;
                myItem.manaRegen = 1;
                myItem.saleGoldCost = 14;
                myItem.imageNumber = 1;
                break;

            case 308:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 16;
                myItem.imageNumber = 1;
                break;

            case 309:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 17;
                myItem.imageNumber = 1;
                break;

            case 310:
                myItem.name = "Wooden sword";
                myItem.type = "sword";
                myItem.damageStat = 6;
                myItem.attackRange = 1;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 17;
                myItem.imageNumber = 1;
                break;

            case 311:
                myItem.name = "Short sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 1;
                myItem.dexterityStat = 1;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 29;
                myItem.imageNumber = 2;
                break;

            case 312:
                myItem.name = "Short sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 25;
                myItem.imageNumber = 2;
                break;
            case 313:
                myItem.name = "Short sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 1;
                myItem.dexterityStat = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 28;
                myItem.imageNumber = 2;
                break;

            case 314:
                myItem.name = "Short sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 1;
                myItem.intellectStat = 1;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 27;
                myItem.imageNumber = 2;
                break;

            case 315:
                myItem.name = "Warbringer";
                myItem.type = "sword";
                myItem.damageStat = 8;
                myItem.attackRange = 2;
                myItem.intellectStat = 1;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 60;
                myItem.imageNumber = 2;
                break;

            case 316:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 3;
                break;
            case 317:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 3;
                break;
            case 318:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 39;
                myItem.imageNumber = 3;
                break;

            case 319:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.intellectStat = 1;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 3;
                break;

            case 320:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 37;
                myItem.imageNumber = 3;
                break;
            case 321:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 8;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 3;
                break;

            case 322:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 8;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 3;
                break;

            case 323:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 8;
                myItem.lifeStat = 8;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 3;
                break;

            case 324:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.lifeStat = 6;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 3;
                break;

            case 325:
                myItem.name = "Long sword";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 3;
                break;

            case 326:
                myItem.name = "Falchion";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.strengthStat = 2;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;
            case 327:
                myItem.name = "Falchion";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;

            case 328:
                myItem.name = "Falchion";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.lifeStat = 17;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;

            case 329:
                myItem.name = "Falchion";
                myItem.type = "sword";
                myItem.damageStat = 7;
                myItem.attackRange = 2;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 1;
                myItem.manaStat = 13;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 4;
                break;

            case 330:
                myItem.name = "Darkbane";
                myItem.type = "sword";
                myItem.damageStat = 9;
                myItem.attackRange = 3;
                myItem.dexterityStat = 3;
                myItem.enduranceStat = 3;
                myItem.attackFramePoint2 = 1;
                myItem.manaStat = 9;
                myItem.saleGoldCost = 71;
                myItem.imageNumber = 4;
                break;

            case 401:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;
            case 402:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;
            case 403:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.intellectStat = 1;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 1;
                break;

            case 404:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;

            case 405:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.resistStat[1] = 6;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;

            case 406:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.resistStat[2] = 6;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;

            case 407:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.resistStat[3] = 6;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;

            case 408:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[0] = 2;
                myItem.saleGoldCost =37;
                myItem.imageNumber = 1;
                break;

            case 409:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.lifeStat = 8;
                myItem.saleGoldCost =37;
                myItem.imageNumber = 1;
                break;

            case 410:
                myItem.name = "Wooden Shield";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;

            case 411:
                myItem.name = "Oakguard";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.lifeStat = 13;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 2;
                break;

            case 412:
                myItem.name = "Oakguard";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.lifeStat = 11;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 52;
                myItem.imageNumber = 2;
                break;

            case 413:
                myItem.name = "Oakguard";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.lifeStat = 11;
                myItem.resistStat[1] = 7;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;

            case 414:
                myItem.name = "Oakguard";
                myItem.type = "shield";
                myItem.armorStat = 2;
                myItem.dexterityStat = 2;
                myItem.resistStat[2] = 6;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;

            case 415:
                myItem.name = "Sylvan Barrier";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.dexterityStat = 2;
                myItem.strengthStat = 2;
                myItem.resistStat[3] = 6;
                myItem.resistStat[2] = 6;
                myItem.resistStat[1] = 6;
                myItem.saleGoldCost = 62;
                myItem.imageNumber = 2;
                break;

            case 416:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.lifeStat = 13;
                myItem.saleGoldCost = 51;
                myItem.imageNumber = 3;
                break;

            case 417:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 54;
                myItem.imageNumber = 3;
                break;

            case 418:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 3;
                break;

            case 419:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 57;
                myItem.imageNumber = 3;
                break;

            case 420:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 59;
                myItem.imageNumber = 3;
                break;

            case 421:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[2] = 10;
                myItem.saleGoldCost = 58;
                myItem.imageNumber = 3;
                break;

            case 422:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[3] = 11;
                myItem.saleGoldCost = 56;
                myItem.imageNumber = 3;
                break;

            case 423:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[1] = 9;
                myItem.saleGoldCost = 55;
                myItem.imageNumber = 3;
                break;

            case 424:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[1] = 9;
                myItem.saleGoldCost = 54;
                myItem.imageNumber = 3;
                break;

            case 425:
                myItem.name = "Wooden Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[0] = 5;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 3;
                break;

            case 426:
                myItem.name = "Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.strengthStat= 2;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;

            case 427:
                myItem.name = "Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.enduranceStat = 2;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;
            case 428:
                myItem.name = "Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.resistStat[2] = 8;
                myItem.resistStat[1] = 7;
                myItem.resistStat[3] = 9;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;

            case 429:
                myItem.name = "Buckler";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.lifeStat = 19;
                myItem.resistStat[1] = 7;
                myItem.resistStat[3] = 9;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;

            case 430:
                myItem.name = "Steelclash";
                myItem.type = "shield";
                myItem.armorStat = 3;
                myItem.lifeStat = 19;
                myItem.resistStat[1] = 11;
                myItem.resistStat[3] = 11;
                myItem.strengthStat = 2;
                myItem.dexterityStat = 6;
                myItem.attackFramePoint2 = 1;
                myItem.manaStat = 11;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 4;
                break;

            case 501:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.lifeStat = 8;
                myItem.saleGoldCost = 32;
                myItem.imageNumber = 1;
                break;

            case 502:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.resistStat[0] = 3;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;

            case 503:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.resistStat[1] = 8;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;

            case 504:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.resistStat[2] = 8;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;

            case 505:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.resistStat[3] = 8;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 506:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 507:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 508:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 509:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.intellectStat = 1;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 510:
                myItem.name = "Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.manaStat = 11;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 511:
                myItem.name = "Heavy Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.manaStat = 12;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 2;
                break;

            case 512:
                myItem.name = "Heavy Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 2;
                myItem.attackFramePoint2 = 1;
                myItem.lifeStat = 20;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 2;
                break;

            case 513:
                myItem.name = "Heavy Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 2;
                break;

            case 514:
                myItem.name = "Heavy Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.resistStat[0] = 2;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 2;
                break;

            case 515:
                myItem.name = "Magefist";
                myItem.type = "gloves";
                myItem.armorStat = 0;
                myItem.castFramePoint1 = 1;
                myItem.castFramePoint2 = 1;
                myItem.manaRegen = 1;
                myItem.lifeRegen = 1;
                myItem.resistStat[1] = 5;
                myItem.intellectStat = 4;
                myItem.saleGoldCost = 76;
                myItem.imageNumber = 2;
                break;

            case 516:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.lifeRegen = 1;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 3;
                break;

            case 517:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.resistStat[1] = 7;
                myItem.saleGoldCost = 43;
                myItem.imageNumber = 3;
                break;

            case 518:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 39;
                myItem.imageNumber = 3;
                break;

            case 519:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 49;
                myItem.imageNumber = 3;
                break;


            case 520:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 48;
                myItem.imageNumber = 3;
                break;

            case 521:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 47;
                myItem.imageNumber = 3;
                break;

            case 522:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 4;
                myItem.saleGoldCost = 46;
                myItem.imageNumber = 3;
                break;

            case 523:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 4;
                myItem.saleGoldCost = 45;
                myItem.imageNumber = 3;
                break;

            case 524:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.lifeRegen = 1;
                myItem.saleGoldCost = 46;
                myItem.imageNumber = 3;
                break;

            case 525:
                myItem.name = "Fine Leather Gloves";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.dexterityStat = 2;
                myItem.resistStat[3] = 10;
                myItem.saleGoldCost = 46;
                myItem.imageNumber = 3;
                break;

            case 526:
                myItem.name = "Light gauntlet";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.castFramePoint1 = 1;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 2;
                myItem.lifeStat = 10;
                myItem.saleGoldCost = 50;
                myItem.imageNumber = 4;
                break;

            case 527:
                myItem.name = "Light gauntlet";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.castFramePoint1 = 1;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 2;
                myItem.resistStat[3] = 9;
                myItem.saleGoldCost = 49;
                myItem.imageNumber = 4;
                break;

            case 528:
                myItem.name = "Light gauntlet";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.castFramePoint1 = 1;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 2;
                myItem.resistStat[1] = 7;
                myItem.saleGoldCost = 48;
                myItem.imageNumber = 4;
                break;

            case 529:
                myItem.name = "Light gauntlet";
                myItem.type = "gloves";
                myItem.armorStat = 1;
                myItem.attackFramePoint2 = 1;
                myItem.castFramePoint1 = 1;
                myItem.dexterityStat = 2;
                myItem.intellectStat = 2;
                myItem.resistStat[0] = 3;
                myItem.saleGoldCost = 47;
                myItem.imageNumber = 4;
                break;

            case 530:
                myItem.name = "Steelrend";
                myItem.type = "gloves";
                myItem.armorStat = 0;
                myItem.attackFramePoint2 = 2;
                myItem.strengthStat = 11;
                myItem.damageStat = 1;
                myItem.lifeRegen = 1;
                myItem.dexterityStat = 3;
                myItem.saleGoldCost = 88;
                myItem.imageNumber = 4;
                break;

            case 601:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 7;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;

            case 602:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 8;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 34;
                myItem.imageNumber = 1;
                break;

            case 603:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 6;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 31;
                myItem.imageNumber = 1;
                break;

            case 604:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 2;
                myItem.resistStat[0] = 2;
                myItem.saleGoldCost = 37;
                myItem.imageNumber = 1;
                break;

            case 605:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.resistStat[1] = 6;
                myItem.resistStat[2] = 6;
                myItem.saleGoldCost = 37;
                myItem.imageNumber = 1;
                break;

            case 606:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 4;
                myItem.resistStat[3] = 6;
                myItem.saleGoldCost = 37;
                myItem.imageNumber = 1;
                break;

            case 607:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.manaStat = 7;
                myItem.lifeStat = 8;
                myItem.saleGoldCost = 37;
                myItem.imageNumber = 1;
                break;

            case 608:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.manaStat = 10;
                myItem.intellectStat = 1;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 1;
                break;

            case 609:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 1;
                break;

            case 610:
                myItem.name = "Leather Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.enduranceStat = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 1;
                break;

            case 611:
                myItem.name = "Heavy Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.enduranceStat = 1;
                myItem.strengthStat = 1;
                myItem.lifeStat = 8;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 2;
                break;

            case 612:
                myItem.name = "Leather boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.intellectStat = 1;
                myItem.enduranceStat = 1;
                myItem.manaStat= 8;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 2;
                break;

            case 613:
                myItem.name = "Leather boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.enduranceStat = 2;
                myItem.resistStat[1] = 8;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 2;
                break;

            case 614:
                myItem.name = "Leather boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.intellectStat = 1;
                myItem.resistStat[3] = 7;
                myItem.manaStat= 8;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 2;
                break;

            case 615:
                myItem.name = "Tearhaunch";
                myItem.type = "boots";
                myItem.armorStat = 2;
                myItem.enduranceStat = 2;
                myItem.lifeStat = 11;
                myItem.strengthStat = 2;
                myItem.speedStat = 0.5f;
                myItem.saleGoldCost = 101;
                myItem.imageNumber = 2;
                break;

            case 616:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.strengthStat = 2;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 50;
                myItem.imageNumber = 3;
                break;

            case 617:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.intellectStat = 2;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 51;
                myItem.imageNumber = 3;
                break;

            case 618:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.manaStat = 13;
                myItem.lifeStat = 14;
                myItem.saleGoldCost = 59;
                myItem.imageNumber = 3;
                break;

            case 619:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.resistStat[1] = 9;
                myItem.lifeStat = 13;
                myItem.saleGoldCost = 58;
                myItem.imageNumber = 3;
                break;

            case 620:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.resistStat[2] = 9;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 57;
                myItem.imageNumber = 3;
                break;

            case 621:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.manaStat = 13;
                myItem.lifeStat = 12;
                myItem.saleGoldCost = 56;
                myItem.imageNumber = 3;
                break;

            case 622:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.resistStat[3] = 9;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 55;
                myItem.imageNumber = 3;
                break;

            case 623:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.enduranceStat = 4;
                myItem.saleGoldCost = 54;
                myItem.imageNumber = 3;
                break;

            case 624:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 17;
                myItem.resistStat[1] = 15;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 3;
                break;

            case 625:
                myItem.name = "Crimson Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.manaStat = 14;
                myItem.resistStat[3] = 11;
                myItem.saleGoldCost = 53;
                myItem.imageNumber = 3;
                break;

            case 626:
                myItem.name = "Plated Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.manaStat = 13;
                myItem.resistStat[3] = 15;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 63;
                myItem.imageNumber = 4;
                break;

            case 627:
                myItem.name = "Plated Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 14;
                myItem.resistStat[1] = 16;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 56;
                myItem.imageNumber = 4;
                break;

            case 628:
                myItem.name = "Plated Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.lifeStat = 16;
                myItem.lifeRegen = 1;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 55;
                myItem.imageNumber = 4;
                break;

            case 629:
                myItem.name = "Plated Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.resistStat[0] = 4;
                myItem.lifeRegen = 1;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 54;
                myItem.imageNumber = 4;
                break;

            case 630:
                myItem.name = "Windshriek";
                myItem.type = "boots";
                myItem.armorStat = 3;
                myItem.manaRegen = 1;
                myItem.lifeRegen = 1;
                myItem.speedStat = 0.5f;
                myItem.resistStat[3] = 12;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 110;
                myItem.imageNumber = 4;
                break;

            case 701:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.intellectStat = 1;
                myItem.saleGoldCost = 33;
                myItem.imageNumber = 1;
                break;

            case 702:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.enduranceStat = 1;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 1;
                break;

            case 703:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.lifeStat = 7;
                myItem.saleGoldCost = 40;
                myItem.imageNumber = 1;
                break;

            case 704:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.manaStat = 7;
                myItem.saleGoldCost = 39;
                myItem.imageNumber = 1;
                break;

            case 705:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 38;
                myItem.imageNumber = 1;
                break;

            case 706:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.dexterityStat = 1;
                myItem.saleGoldCost = 37;
                myItem.imageNumber = 1;
                break;

            case 707:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[0] = 3;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 708:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[0] = 3;
                myItem.saleGoldCost = 35;
                myItem.imageNumber = 1;
                break;

            case 709:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[1] = 6;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 710:
                myItem.name = "Sash";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[2] = 7;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 1;
                break;

            case 711:
                myItem.name = "Light Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[2] = 7;
                myItem.resistStat[3] = 7;
                myItem.saleGoldCost = 36;
                myItem.imageNumber = 2;
                break;

            case 712:
                myItem.name = "Light Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[0] = 3;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;

            case 713:
                myItem.name = "Light Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.resistStat[1] = 7;
                myItem.lifeStat = 11;
                myItem.saleGoldCost = 41;
                myItem.imageNumber = 2;
                break;

            case 714:
                myItem.name = "Light Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 1;
                myItem.enduranceStat = 1;
                myItem.strengthStat = 1;
                myItem.saleGoldCost = 42;
                myItem.imageNumber = 2;
                break;

            case 715:
                myItem.name = "Ironclasp";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 3;
                myItem.enduranceStat = 2;
                myItem.strengthStat = 2;
                myItem.resistStat[0] = 5;
                myItem.resistStat[1] = 5;
                myItem.saleGoldCost = 101;
                myItem.imageNumber = 2;
                break;

            case 716:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 46;
                myItem.imageNumber = 3;
                break;

            case 717:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 45;
                myItem.imageNumber = 3;
                break;

            case 718:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.intellectStat = 2;
                myItem.saleGoldCost = 44;
                myItem.imageNumber = 3;
                break;

            case 719:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.dexterityStat = 2;
                myItem.saleGoldCost = 47;
                myItem.imageNumber = 3;
                break;

            case 720:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.lifeStat = 14;
                myItem.saleGoldCost = 48;
                myItem.imageNumber = 3;
                break;

            case 721:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.manaStat = 15;
                myItem.saleGoldCost = 49;
                myItem.imageNumber = 3;
                break;

            case 722:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.resistStat[0] = 4;
                myItem.saleGoldCost = 52;
                myItem.imageNumber = 3;
                break;

            case 723:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.resistStat[1] = 11;
                myItem.saleGoldCost = 54;
                myItem.imageNumber = 3;
                break;

            case 724:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.resistStat[2] = 13;
                myItem.saleGoldCost = 51;
                myItem.imageNumber = 3;
                break;

            case 725:
                myItem.name = "Sharkskin Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.resistStat[3] = 12;
                myItem.saleGoldCost = 52;
                myItem.imageNumber = 3;
                break;

            case 726:
                myItem.name = "War Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.lifeStat = 13;
                myItem.resistStat[3] = 12;
                myItem.saleGoldCost = 61;
                myItem.imageNumber = 4;
                break;

            case 727:
                myItem.name = "War Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.lifeStat = 14;
                myItem.manaStat = 16;
                myItem.saleGoldCost = 61;
                myItem.imageNumber = 4;
                break;

            case 728:
                myItem.name = "War Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.strengthStat = 2;
                myItem.lifeStat = 17;
                myItem.saleGoldCost = 61;
                myItem.imageNumber = 4;
                break;

            case 729:
                myItem.name = "War Belt";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 2;
                myItem.enduranceStat = 2;
                myItem.strengthStat = 2;
                myItem.saleGoldCost = 61;
                myItem.imageNumber = 4;
                break;

            case 730:
                myItem.name = "Verdant Loop";
                myItem.type = "belt";
                myItem.armorStat = 1;
                myItem.juiceCharge = 3;
                myItem.enduranceStat = 4;
                myItem.lifeStat = 55;
                myItem.lifeRegen = 3;
                myItem.resistStat[1] = 15;
                myItem.strengthStat = 3;
                myItem.saleGoldCost = 102;
                myItem.imageNumber = 4;
                break;

            case 801:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.attackRange = 2;
                myItem.saleGoldCost = 103;
                myItem.imageNumber = 1;
                break;

            case 802:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.lifeStat= 30;
                myItem.saleGoldCost = 120;
                myItem.imageNumber = 1;
                break;

            case 803:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.manaStat= 31;
                myItem.saleGoldCost = 109;
                myItem.imageNumber = 1;
                break;

            case 804:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.attackFramePoint2 = 1;
                myItem.saleGoldCost = 108;
                myItem.imageNumber = 1;
                break;

            case 805:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.juiceCharge = 1;
                myItem.saleGoldCost = 107;
                myItem.imageNumber = 1;
                break;

            case 806:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.resistStat[0] = 5;
                myItem.saleGoldCost = 106;
                myItem.imageNumber = 1;
                break;

            case 807:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.resistStat[1] = 25;
                myItem.saleGoldCost = 105;
                myItem.imageNumber = 1;
                break;

            case 808:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.resistStat[2] = 25;
                myItem.saleGoldCost = 104;
                myItem.imageNumber = 1;
                break;

            case 809:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.resistStat[3] = 25;
                myItem.saleGoldCost = 104;
                myItem.imageNumber = 1;
                break;

            case 810:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.castFramePoint1 = 1;
                myItem.saleGoldCost = 104;
                myItem.imageNumber = 1;
                break;

            case 811:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.castFramePoint2 = 1;
                myItem.castFramePoint1 = 1;
                myItem.saleGoldCost = 110;
                myItem.imageNumber = 2;
                break;

            case 812:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.resistStat[1] = 20;
                myItem.resistStat[0] = 5;
                myItem.saleGoldCost = 110;
                myItem.imageNumber = 2;
                break;

            case 813:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.lifeStat = 25;
                myItem.resistStat[0] = 5;
                myItem.saleGoldCost = 110;
                myItem.imageNumber = 2;
                break;

            case 814:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 1;
                myItem.manaRegen = 1;
                myItem.manaStat = 26;
                myItem.lifeStat = 25;
                myItem.saleGoldCost = 111;
                myItem.imageNumber = 2;
                break;

            case 815:
                myItem.name = "Mahim-Oak Curio";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.manaStat = 31;
                myItem.lifeStat = 32;
                myItem.juiceCharge = 1;
                myItem.attackRange = 3;
                myItem.castFramePoint2 = 1;
                myItem.castFramePoint1 = 1;
                myItem.saleGoldCost = 401;
                myItem.imageNumber = 2;
                break;

            case 816:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.manaStat = 31;
                myItem.saleGoldCost = 222;
                myItem.imageNumber = 3;
                break;

            case 817:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.lifeStat = 32;
                myItem.saleGoldCost = 228;
                myItem.imageNumber = 3;
                break;

            case 818:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.attackFramePoint2 = 2;
                myItem.saleGoldCost = 227;
                myItem.imageNumber = 3;
                break;

            case 819:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.castFramePoint1 = 2;
                myItem.saleGoldCost = 226;
                myItem.imageNumber = 3;
                break;

            case 820:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.castFramePoint2 = 2;
                myItem.saleGoldCost = 225;
                myItem.imageNumber = 3;
                break;

            case 821:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.resistStat[0] = 9;
                myItem.saleGoldCost = 224;
                myItem.imageNumber = 3;
                break;

            case 822:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.resistStat[1] = 35;
                myItem.saleGoldCost = 227;
                myItem.imageNumber = 3;
                break;

            case 823:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.resistStat[2] = 35;
                myItem.saleGoldCost = 226;
                myItem.imageNumber = 3;
                break;

            case 824:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.resistStat[3] = 35;
                myItem.saleGoldCost = 225;
                myItem.imageNumber = 3;
                break;

            case 825:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.juiceCharge = 1;
                myItem.saleGoldCost = 225;
                myItem.imageNumber = 3;
                break;

            case 826:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.juiceCharge = 1;
                myItem.attackRange = 3;
                myItem.saleGoldCost = 277;
                myItem.imageNumber = 4;
                break;

            case 827:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.juiceCharge = 1;
                myItem.lifeStat = 35;
                myItem.saleGoldCost = 277;
                myItem.imageNumber = 4;
                break;

            case 828:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.manaStat = 31;
                myItem.lifeStat = 35;
                myItem.saleGoldCost = 277;
                myItem.imageNumber = 4;
                break;

            case 829:
                myItem.name = "Amulet";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.manaStat = 31;
                myItem.enduranceStat = 2;
                myItem.saleGoldCost = 277;
                myItem.imageNumber = 4;
                break;

            case 830:
                myItem.name = "Ebonpiercer";
                myItem.type = "amulet";
                myItem.lifeRegen = 2;
                myItem.manaRegen = 2;
                myItem.attackFramePoint2 = 3;
                myItem.attackRange = 5;
                myItem.dexterityStat = 6;
                myItem.strengthStat = 9;
                myItem.saleGoldCost = 546;
                myItem.imageNumber = 4;
                break;



        }


        switch (i) {
            //starting wooden sword, dropped by first moving slime
            case 1:
                myItem.name = "Timber Tickle";
                myItem.type = "sword";
                myItem.damageStat = 4;
                myItem.attackRange = 1;
                myItem.saleGoldCost = 1;
                myItem.imageNumber = 1;
                break;

            //boots in dungeon, family chest drop in cellar
            case 2:
                myItem.name = "Family Boots";
                myItem.type = "boots";
                myItem.armorStat = 1;
                myItem.speedStat = 0.5f;
                myItem.saleGoldCost = 1;
                myItem.imageNumber = 4;
                break;
        }
        myItem.imageInventory = myItem.generateItemImage();
        addItemImageBackgroundBasedOnRarity(myItem, i);
        myItem.itemCode = i;
        myItem.buyGoldCost = myItem.saleGoldCost * 5;
        myItem.generateHoverDescription();
        return myItem;
    }

    public void addItemImageBackgroundBasedOnRarity(Item item, int i){
        if (i > 100){
            if((i % 100) % 15 == 0){
                item.makeGoldishItemPic();
            }
            int tempNumber = (i % 100) % 15;
            if (tempNumber == 11 || tempNumber == 12 || tempNumber == 13 || tempNumber == 14) {
                item.makeBlueishItemPic();
            }
        }
    }

    public Item generateBookItem1(){
        Item myItem = new Item();
        myItem.name = "Book";
        myItem.imageInventory  = setup("/objects/pickables/talentbook",48, 48);
        myItem.buyGoldCost = 300;
        myItem.itemCode = 3;
        myItem.hoverDescription = "Talent book";
        return myItem;
    }

    public Item generateBookItem2(){
        Item myItem = new Item();
        myItem.name = "Book";
        myItem.itemCode = 4;
        myItem.imageInventory  = setup("/objects/pickables/talentbook",48, 48);
        myItem.buyGoldCost = 1000;
        myItem.hoverDescription = "Talent book\nMore expensive, but does the same.";
        return myItem;
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
}
