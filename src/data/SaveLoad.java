package data;

import entity.playerrelated.PlayerTalents;
import main.GamePanel;

import java.io.*;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save" + gp.saveSlotNumber + ".dat")));
            DataStorage ds = new DataStorage();

            ds.life = gp.player.life;
            ds.mana = gp.player.mana;
            ds.gold = gp.player.gold;

            //LVL RELATED:
            ds.level = gp.player.level;
            ds.currentExperience = gp.player.experience;
            ds.previousLevelExp = gp.player.previousLevelExp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.unSpentStats = gp.player.unSpentStats;
            ds.strengthFromLvl = gp.player.strengthFromLvl;
            ds.dexterityFromLvl = gp.player.dexterityFromLvl;
            ds.enduranceFromLvl = gp.player.enduranceFromLvl;
            ds.intelligenceFromLvl = gp.player.intelligenceFromLvl;
            ds.maxLifeFromUp = gp.player.maxLifeFromUp;
            ds.maxManaFromUp = gp.player.maxManaFromUp;

            //TALENTS:
            ds.unSpentTalentPoints = gp.player.unSpentTalentPoints;
            for (int i = 0; i < PlayerTalents.talentList.length; i++) {
                ds.talentPoints[i] = PlayerTalents.talentList[i].currentPointsOnTalent;
            }

            //SKILL:
            ds.unSpentSkillPoints = gp.player.unSpentSkillPoints;
            for (int i = 0; i < 12; i++) {
                ds.skillPoints[i] = gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell;
            }
            for (int i = 0; i < 6; i++) {
                if(gp.player.equippedSpellList[i] != null){
                    ds.equippedSkills[i] = gp.player.equippedSpellList[i].uniqueSpellID;
                } else ds.equippedSkills[i] = -1;
            }

            //SAVE PROGRESS:
            for (int i = 0; i < 7; i++) {
                ds.act1BookPickedUp[i] = gp.progress.act1BookPickedUp[i];
            }

            for (int i = 0; i < 5; i++) {
                ds.act1InteractedObjects[i] = gp.progress.act1InteractedObjects[i];
            }

            //SAVE INVENTORY:
            //equipped items page:
            for (int i = 0; i < 8; i++) {
                if(gp.allInventoryPages.playerEquipmentPage.slots[0][i].placedItem != null){
                    ds.equippedItems[i] = gp.allInventoryPages.playerEquipmentPage.slots[0][i].placedItem.itemCode;
                } else {
                    ds.equippedItems[i] = 0;
                }
            }
            //inventory page + storage
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 99;  i ++){
                    int row = i / 9;
                    int col = i - row * 9;
                    if(j == 0) {
                        if (gp.allInventoryPages.playerInventoryPage.slots[row][col].placedItem != null) {
                            ds.inventoryPages[j][i] = gp.allInventoryPages.playerInventoryPage.slots[row][col].placedItem.itemCode;
                        }
                        else {
                            ds.inventoryPages[j][i] = 0;
                        }
                    }
                    if(j > 0){
                        if(gp.allInventoryPages.storagePages[j - 1].slots[row][col].placedItem != null){
                            ds.inventoryPages[j][i] = gp.allInventoryPages.storagePages[j - 1].slots[row][col].placedItem.itemCode;
                        }
                        else {
                            ds.inventoryPages[j][i] = 0;
                        }
                    }
                }
            }
            gp.stopMusic();
            oos.writeObject(ds);

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void load(int saveSlotNumber){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save" + saveSlotNumber + ".dat")));
            DataStorage ds = (DataStorage)ois.readObject();

            gp.player.life = ds.life;
            gp.player.mana = ds.mana;
            gp.player.gold = ds.gold;

            // LVL RELATED:
            gp.player.level = ds.level;
            gp.player.experience = ds.currentExperience;
            gp.player.previousLevelExp = ds.previousLevelExp;
            gp.player.nextLevelExp = ds.nextLevelExp;

            gp.player.maxLifeFromUp = ds.maxLifeFromUp;
            gp.player.maxManaFromUp = ds.maxManaFromUp;
            gp.player.unSpentStats = ds.unSpentStats;
            gp.player.strengthFromLvl = ds.strengthFromLvl;
            gp.player.dexterityFromLvl = ds.dexterityFromLvl;
            gp.player.enduranceFromLvl = ds.enduranceFromLvl;
            gp.player.intelligenceFromLvl = ds.intelligenceFromLvl;

            //TALENTS:
            gp.player.unSpentTalentPoints = ds.unSpentTalentPoints;
            for (int i = 0; i < PlayerTalents.talentList.length; i++) {
                PlayerTalents.talentList[i].currentPointsOnTalent = ds.talentPoints[i];
            }

            //SKILL:
            gp.player.unSpentSkillPoints = ds.unSpentSkillPoints;
            for (int i = 0; i < 12; i++) {
                gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell = ds.skillPoints[i];
            }

            for (int i = 0; i < 6; i++) {
                if(ds.equippedSkills[i] != -1){
                    gp.player.equippedSpellList[i] = gp.player.allSpellList.allPlayerAvailableSpells[ds.equippedSkills[i]];
                }
            }

            //Progress:
            for (int i = 0; i < 7; i++) {
                if (i < ds.act1BookPickedUp.length) {
                    gp.progress.act1BookPickedUp[i] = ds.act1BookPickedUp[i];
                } else {
                    // Default value for new entries in older save files
                    gp.progress.act1BookPickedUp[i] = false;
                }
            }

            for (int i = 0; i < 5; i++) {
                if (i < ds.act1InteractedObjects.length) {
                    gp.progress.act1InteractedObjects[i] = ds.act1InteractedObjects[i];
                } else {
                    // Default value for new entries in older save files
                    gp.progress.act1InteractedObjects[i] = false;
                }
            }

            //LOAD INVENTORY:

            //equipped items page:
            for (int i = 0; i < 8; i++) {
                if(ds.equippedItems[i] != 0){
                    gp.allInventoryPages.playerEquipmentPage.slots[0][i].placedItem = gp.itemGenerator.generateItemBasedOnID(ds.equippedItems[i]);
                }
            }

            //inventory page:
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 99; i++) {
                    int row = i / 9;
                    int col = i % 9; // This is equivalent to i - (row * 9)
                    if( j == 0) {
                        if(ds.inventoryPages[j][i] != 0) {
                            gp.allInventoryPages.playerInventoryPage.slots[row][col].placedItem = gp.itemGenerator.generateItemBasedOnID(ds.inventoryPages[j][i]);
                        }
                    } else {
                        if(ds.inventoryPages[j][i] != 0){
                            gp.allInventoryPages.storagePages[j-1].slots[row][col].placedItem = gp.itemGenerator.generateItemBasedOnID(ds.inventoryPages[j][i]);
                        }
                    }
                }
            }

            //storage:
            gp.stopMusic();
            reloadSomeSetupElements();

            gp.player.momoJuice.charge = gp.player.momoJuice.maxCharge;
            gp.player.refreshPlayerStatsWithItems();

        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    // same as respawn, but have to reload some setup elements too
    public void reloadSomeSetupElements(){
        gp.currentMap = gp.dataBase1.getRespawnData()[0];
        gp.player.worldX = gp.dataBase1.getRespawnData()[1];
        gp.player.worldY = gp.dataBase1.getRespawnData()[2];

        gp.aSetter.setObject();
        gp.aSetter.setFighter();
        gp.aSetter.setNPC();

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

        gp.allFightingEntities.clear();
        for (int i = 0; i < gp.fighters[1].length; i++) {
            if(gp.fighters[gp.currentMap][i] != null){
                gp.allFightingEntities.add(gp.fighters[gp.currentMap][i]);
            }
        }
        gp.allFightingEntities.add(gp.player);

        gp.eManager.update();

        gp.currentMap = gp.dataBase1.getRespawnData()[0];
        gp.player.worldX = gp.dataBase1.getRespawnData()[1];
        gp.player.worldY = gp.dataBase1.getRespawnData()[2];

        gp.startSinging(gp.currentMap);
    }
}
