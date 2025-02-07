package entity.playerrelated;

import entity.Player;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerSpellList {
    Player player;
    public PlayerSpell[] allPlayerAvailableSpells = new PlayerSpell[12];

    public PlayerSpellList(Player player){
        this.player = player;
        createSpellList();
        updateDescription();
    }

    private void createSpellList() {
        PlayerSpell fireBall = new PlayerSpell(player);
        allPlayerAvailableSpells[0] = fireBall;
        fireBall.currentPointsOnSpell = 1;
        fireBall.image = setupSheet("/ui/skillpage/spellicons",0,0,70,70);
        fireBall.requiredLevel = 1;
        fireBall.uniqueSpellID = 0;
        fireBall.description = "";

        PlayerSpell fireWall = new PlayerSpell(player);
        allPlayerAvailableSpells[1] = fireWall;
        fireWall.currentPointsOnSpell = 0;
        fireWall.image = setupSheet("/ui/skillpage/spellicons",70,0,70,70);
        fireWall.requiredLevel = 6;
        fireWall.uniqueSpellID = 1;
        fireWall.description = "";

        PlayerSpell flameCloak = new PlayerSpell(player);
        allPlayerAvailableSpells[2] = flameCloak;
        flameCloak.currentPointsOnSpell = 0;
        flameCloak.image = setupSheet("/ui/skillpage/spellicons",140,0,70,70);
        flameCloak.requiredLevel = 18;
        flameCloak.uniqueSpellID = 2;
        flameCloak.description = "";

        PlayerSpell hydra = new PlayerSpell(player);
        allPlayerAvailableSpells[3] = hydra;
        hydra.currentPointsOnSpell = 0;
        hydra.image = setupSheet("/ui/skillpage/spellicons",210,0,70,70);
        hydra.requiredLevel = 30;
        hydra.uniqueSpellID = 3;
        hydra.description = "";

        PlayerSpell iceBolt = new PlayerSpell(player);
        allPlayerAvailableSpells[4] = iceBolt;
        iceBolt.currentPointsOnSpell = 0;
        iceBolt.image = setupSheet("/ui/skillpage/spellicons",280,0,70,70);
        iceBolt.requiredLevel = 1;
        iceBolt.uniqueSpellID = 4;
        iceBolt.description = "";

        PlayerSpell afterImage = new PlayerSpell(player);
        allPlayerAvailableSpells[5] = afterImage;
        afterImage.currentPointsOnSpell = 0;
        afterImage.image = setupSheet("/ui/skillpage/spellicons",350,0,70,70);
        afterImage.requiredLevel = 12;
        afterImage.uniqueSpellID = 5;
        afterImage.description = "";

        PlayerSpell frozenOrb = new PlayerSpell(player);
        allPlayerAvailableSpells[6] = frozenOrb;
        frozenOrb.currentPointsOnSpell = 0;
        frozenOrb.image = setupSheet("/ui/skillpage/spellicons",420,0,70,70);
        frozenOrb.requiredLevel = 24;
        frozenOrb.uniqueSpellID = 6;
        frozenOrb.description = "";

        PlayerSpell frozenShield = new PlayerSpell(player);
        allPlayerAvailableSpells[7] = frozenShield;
        frozenShield.currentPointsOnSpell = 0;
        frozenShield.image = setupSheet("/ui/skillpage/spellicons",490,0,70,70);
        frozenShield.requiredLevel = 30;
        frozenShield.uniqueSpellID = 7;
        frozenShield.description = "";

        PlayerSpell lightning = new PlayerSpell(player);
        allPlayerAvailableSpells[8] = lightning;
        lightning.currentPointsOnSpell = 0;
        lightning.image = setupSheet("/ui/skillpage/spellicons",560,0,70,70);
        lightning.requiredLevel = 6;
        lightning.uniqueSpellID = 8;
        lightning.description = "";

        PlayerSpell nova = new PlayerSpell(player);
        allPlayerAvailableSpells[9] = nova;
        nova.currentPointsOnSpell = 0;
        nova.image = setupSheet("/ui/skillpage/spellicons",630,0,70,70);
        nova.requiredLevel = 12;
        nova.uniqueSpellID = 9;
        nova.description = "";

        PlayerSpell teleport = new PlayerSpell(player);
        allPlayerAvailableSpells[10] = teleport;
        teleport.currentPointsOnSpell = 0;
        teleport.image = setupSheet("/ui/skillpage/spellicons",700,0,70,70);
        teleport.requiredLevel = 18;
        teleport.uniqueSpellID = 10;
        teleport.description = "";

        PlayerSpell energyShield = new PlayerSpell(player);
        allPlayerAvailableSpells[11] = energyShield;
        energyShield.currentPointsOnSpell = 0;
        energyShield.image = setupSheet("/ui/skillpage/spellicons",770,0,70,70);
        energyShield.requiredLevel = 24;
        energyShield.uniqueSpellID = 11;
        energyShield.description = "";
    }

    public void updateDescription(){
        allPlayerAvailableSpells[0].description = "Fire Ball" +
                "\nThis is an active spell." +
                "\nRequired level: 1." +
                "\n\nProduces a blue fire ball that flies towards the target location" +
                "\nGoes through walls." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(0, allPlayerAvailableSpells[0].currentPointsOnSpell) +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(0,allPlayerAvailableSpells[0].currentPointsOnSpell)) + "." +
                "\n\nNext level:" +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(0,allPlayerAvailableSpells[0].currentPointsOnSpell + 1)) + ".";

        allPlayerAvailableSpells[1].description = "Fire Wall" +
                "\nThis is an active spell." +
                "\nRequired level: 6." +
                "\n\nProduces a wall of flame at the target location." +
                "\nThe wall continuously damages hostile creatures." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(1, allPlayerAvailableSpells[1].currentPointsOnSpell) +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(1,allPlayerAvailableSpells[1].currentPointsOnSpell)) + "." +
                "\n\nNext level:" +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(1,allPlayerAvailableSpells[1].currentPointsOnSpell + 1)) + ".";


        //TODO REDO:
        int[] cloakData = getFlameCloakData(allPlayerAvailableSpells[2].currentPointsOnSpell);
        int[] cloakData2 = getFlameCloakData(allPlayerAvailableSpells[2].currentPointsOnSpell + 1);
        allPlayerAvailableSpells[2].description = "Flame Cloak" +
                "\nThis is an enchantment spell." +
                "\nRequired level: 18." +
                "\n\nCreates a flame cloak around the hero that lasts for a given time." +
                "\nThe flame cloak provides health regeneration and fire resistance." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(2, allPlayerAvailableSpells[2].currentPointsOnSpell) +
                "\nFire resistance: " + cloakData[0] + "." +
                "\nLife regeneration increased by: " + cloakData[1] + "%." +
                "\nDuration: " + cloakData[2] + "seconds." +
                "\n\nNext level:" +
                "\nFire resistance: " + cloakData2[0] + "." +
                "\nLife regeneration increased by: " + cloakData2[1] + "%." +
                "\nDuration: " + cloakData2[2] + "seconds.";

        allPlayerAvailableSpells[3].description = "Dragon totem" +
                "\nThis is an active summon spell." +
                "\nRequired level: 30." +
                "\n\nCreates a dragon totem at the target location that fires at hostile creatures on its own." +
                "\nThe dragon totem does not move." +
                "\nOnly one dragon totem can be active at a time." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(3, allPlayerAvailableSpells[3].currentPointsOnSpell) +
                "\nDuration: 10 seconds." +
                "\nDamage: " + (int)(getSpellDamage(3, allPlayerAvailableSpells[3].currentPointsOnSpell)) + "." +
                "\n\nNext level:" +
                "\nDamage: " + (int)(getSpellDamage(3, allPlayerAvailableSpells[3].currentPointsOnSpell + 1)) + ".";

        allPlayerAvailableSpells[4].description = "Ice bolt" +
                "\nThis is an active spell." +
                "\nRequired level: 1." +
                "\n\nProduces an icicle that flies towards targeted\nlocation." +
                "\nIt is destroyed when hits a wall." +
                "\nIt can freeze some enemies for a short time." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(4, allPlayerAvailableSpells[4].currentPointsOnSpell) +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(4, allPlayerAvailableSpells[4].currentPointsOnSpell)) + "." +
                "\n\nNext level:" +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(4, allPlayerAvailableSpells[4].currentPointsOnSpell + 1)) + ".";

        allPlayerAvailableSpells[5].description = "Afterimage" +
                "\nThis is a passive spell." +
                "\nRequired level: 12." +
                "\n\nProduces a solid frozen statue of the hero each time he dashes." +
                "\nThe statue shatters after a second, damaging hostile creatures nearby." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(5, allPlayerAvailableSpells[5].currentPointsOnSpell) +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(5, allPlayerAvailableSpells[5].currentPointsOnSpell)) + "." +
                "\n\nNext level:" +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(5, allPlayerAvailableSpells[5].currentPointsOnSpell + 1)) + ".";

        int[] iceOrbData = getIceOrbData(allPlayerAvailableSpells[6].currentPointsOnSpell);
        int[] iceOrbData2 = getIceOrbData(allPlayerAvailableSpells[6].currentPointsOnSpell + 1);
        allPlayerAvailableSpells[6].description = "Frozen orb" +
                "\nThis is an active spell." +
                "\nRequired level: 24." +
                "\n\nProduces a frozen orb slowly flying towards target area. " +
                "\nThe orb randomly shoots small icicles." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(6, allPlayerAvailableSpells[6].currentPointsOnSpell) +
                "\nOrb damage: " + (int)(player.spellDmgModifier * iceOrbData[0]) + "." +
                "\nSmall icicle damage: " + (int)(player.spellDmgModifier * iceOrbData[1]) + "." +
                "\n\nNext level:" +
                "\nOrb damage: " + (int)(player.spellDmgModifier * iceOrbData2[0]) + "." +
                "\nSmall icicle damage: " + (int)(player.spellDmgModifier * iceOrbData2[1]) + ".";


        allPlayerAvailableSpells[7].description = "Ice shield" +
                "\nThis is a passive spell." +
                "\nRequired level: 30." +
                "\n\nThe next instance of damage that would hurt the hero " +
                "\nis absorbed instead. A percentage of the absorbed damage" +
                "\nis added to the next sword attack of the hero."  +
                "\nThe absorb effect can occur only once" +
                "\nper 18 seconds." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(7, allPlayerAvailableSpells[7].currentPointsOnSpell) +
                "\nDamage: " + getSpellDamage(7, allPlayerAvailableSpells[7].currentPointsOnSpell) + "." +
                "\n\nNext level:" +
                "\nDamage: " + getSpellDamage(7, allPlayerAvailableSpells[7].currentPointsOnSpell + 1) + ".";

        allPlayerAvailableSpells[8].description = "Lightning" +
                "\nThis is an active spell." +
                "\nRequired level: 6." +
                "\n\nProduces a lightning that instantly hits the enemy closest to the target area." +
                "\nIf there is another enemy nearby, the lightning bounces to that enemy as well for half damage. " +
                "\nThe bouncing effect can occur only once."  +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(8, allPlayerAvailableSpells[8].currentPointsOnSpell)) + "."+
                "\n\nNext level:" +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(8, allPlayerAvailableSpells[8].currentPointsOnSpell + 1)) + ".";

        allPlayerAvailableSpells[9].description = "Nova" +
                "\nThis is an active spell." +
                "\nRequired level: 12" +
                "\n\nProduces a slowly and continuously expanding circle. It deals lightning damage to hostile creatures that it hits." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(9, allPlayerAvailableSpells[9].currentPointsOnSpell) +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(9, allPlayerAvailableSpells[9].currentPointsOnSpell)) + "." +
                "\n\nNext level:" +
                "\nDamage: " + (int)(player.spellDmgModifier * getSpellDamage(9, allPlayerAvailableSpells[9].currentPointsOnSpell + 1)) + ".";

        allPlayerAvailableSpells[10].description = "Teleport" +
                "\nThis is an active spell." +
                "\nRequired level: 18" +
                "\n\nTeleports the hero to the targeted area." +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(10, allPlayerAvailableSpells[10].currentPointsOnSpell) + "." +
                "\n\nNext level:" +
                "\nMana cost: " + player.gp.dataBase1.getRequiredManaForSpell(10, allPlayerAvailableSpells[10].currentPointsOnSpell + 1);


        allPlayerAvailableSpells[11].description = "Energy shield" +
                "\nThis is an enhancement spell." +
                "\nRequired level: 24" +
                "\n\nLasts for 300 seconds." +
                "\n50% of damage received is absorbed and removed from mana instead." +
                "\nWith additional points less mana is removed."  +
                "\nPercentage of damage removed as mana " + ((int)(getEnergyShieldPercentage(allPlayerAvailableSpells[11].currentPointsOnSpell) * 100)) + " %." +
                "\n\nNext level:" +
                "\nPercentage of damage removed as mana " + ((getEnergyShieldPercentage(allPlayerAvailableSpells[11].currentPointsOnSpell + 1) * 100)) + " %.";
    }


    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public float getEnergyShieldPercentage(int level){
        if(level == 0){
            return 0f;
        }
        if(level > 10){
            return 10;
        }
        return 0.5f - (level - 1) * 0.037f;
    }

    public int[] getFlameCloakData(int level){
        int[] cloakData = new int[3];
        // 0: life regen
        // 1: fire resist
        // 2: duration
        if(level > 10){
            level = 10;
        }

        cloakData[0] = (int)(level * 1.5);
        cloakData[1] = (int)(level);
        cloakData[2] = 30 + level * 5;
        if(level == 0){
            cloakData[0] = 0;
            cloakData[1] = 0;
            cloakData[2] = 0;
        }
        return cloakData;
    }

    public int[] getIceOrbData(int level){
        int[] iceOrbData = new int[2];
        // 0: orb damage
        // 1: icicle damage
        iceOrbData[0] = 15 + level * 10;
        iceOrbData[1] = 10 + level * 8;

        if(level == 0){
            iceOrbData[0] = 0;
            iceOrbData[1] = 0;
        }

        return iceOrbData;

    }

    public int getSpellDamage(int uniqueSpellID, int spellLevel){
        if(spellLevel == 0){
            return 0;
        }
        switch (uniqueSpellID){
            case 0:
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 35;
                    case 3: return 40;
                    case 4: return 45;
                    case 5: return 50;
                    case 6: return 55;
                    case 7: return 60;
                    case 8: return 65;
                    case 9: return 70;
                    case 10: return 75;
                }
            case 1:
                switch (spellLevel) {
                    case 1: return 10;
                    case 2: return 14;
                    case 3: return 18;
                    case 4: return 22;
                    case 5: return 26;
                    case 6: return 30;
                    case 7: return 36;
                    case 8: return 42;
                    case 9: return 48;
                    case 10: return 54;
                }
            case 2:
                switch (spellLevel) {
                    case 1: return 20;
                    case 2: return 24;
                    case 3: return 28;
                    case 4: return 32;
                    case 5: return 37;
                    case 6: return 43;
                    case 7: return 49;
                    case 8: return 54;
                    case 9: return 60;
                    case 10: return 65;
                }
            case 3:
                switch (spellLevel) {
                    case 1: return 20;
                    case 2: return 24;
                    case 3: return 28;
                    case 4: return 32;
                    case 5: return 37;
                    case 6: return 43;
                    case 7: return 49;
                    case 8: return 54;
                    case 9: return 60;
                    case 10: return 65;
                }
            case 4:
                switch (spellLevel) {
                    case 1: return 24;
                    case 2: return 28;
                    case 3: return 32;
                    case 4: return 37;
                    case 5: return 43;
                    case 6: return 48;
                    case 7: return 53;
                    case 8: return 58;
                    case 9: return 63;
                    case 10: return 69;
                }
            case 5: // afterimage
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
            case 6: // frozen orb
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
            case 7: // ice shield
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
            case 8: //lightning
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
            case 9: //
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
            case 10:
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
            case 11:
                switch (spellLevel) {
                    case 1: return 30;
                    case 2: return 40;
                    case 3: return 50;
                    case 4: return 60;
                    case 5: return 70;
                    case 6: return 80;
                    case 7: return 90;
                    case 8: return 100;
                    case 9: return 110;
                    case 10: return 130;
                }
        }
        return 0;
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
