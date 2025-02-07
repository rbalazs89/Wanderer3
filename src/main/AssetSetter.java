package main;

//import entity.Monster;
import entity.allied.A_Brother;
import entity.monster.act1.*;
import entity.npc.*;
import object.*;
import object.puzzle.harrypotterpuzzle.OBJ_FireObject;
import object.puzzle.harrypotterpuzzle.OBJ_HarryPotterPuzzle;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AssetSetter {

    /**
     * maps txt list:
     * 1: village music done
     * 2: cave  got music but not good
     * 3: bunnies map
     * 4: wolves + mage music good
     * 5 kindergarten
     * 6: church + music good
     * 7: house cellar
     * 8: shop + music good
     * 9: my house + music good
     * 10: cave 2
     * 11: boss room + music good
     * //12: a1 ending music taken
     * 13: library
     */

    GamePanel gp;
    int tileSize = GamePanel.tileSize;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {
        int currentMap = 1;
        setObjectMap1(currentMap);

        currentMap = 2;
        setObjectMap2(currentMap); // cave

        currentMap = 3;
        setObjectMap3(currentMap); // pikapika

        currentMap = 4;
        setObjectMap4(currentMap); // mage

        currentMap = 5;
        setObjectMap5(currentMap); // kindergarten

        currentMap = 6;
        setObjectMap6(currentMap); // church

        currentMap = 7;
        setObjectMap7(currentMap); // cellar

        currentMap = 8;
        setObjectMap8(currentMap); // shop

        currentMap = 9;
        setObjectMap9(currentMap); // my house

        currentMap = 10;
        setObjectMap10(currentMap); // cave 2

        currentMap = 11;
        setObjectMap11(currentMap); // boss map

        currentMap = 13;
        setObjectMap13(currentMap); // library
    }


    public void setNPC() {
        int currentMap = 1;
        setNPCMap1(currentMap);

        currentMap = 3;
        setNPCMap3(currentMap);

        currentMap = 4;
        setNPCMap4(currentMap);

        currentMap = 5;
        setNPCMap5(currentMap);

        currentMap = 6;
        setNPCMap6(currentMap);

        currentMap = 8;
        setNPCMap8(currentMap);

        currentMap = 9;
        setNPCMap9(currentMap);

        currentMap = 13;
        setNPCMap13(currentMap);
    }


    public void setFighter(){
        int currentMap = 1;
        setFighterMap1(currentMap);

        currentMap = 2;
        setFighterMap2(currentMap);

        currentMap = 3;
        setFighterMap3(currentMap);

        currentMap = 4;
        setFighterMap4(currentMap);

        currentMap = 7;
        setFighterMap7(currentMap);

        currentMap = 10;
        setFighterMap10(currentMap);

        currentMap = 11;
        setFighter11(currentMap);
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

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    //Always reload
    public void ghostsNearCemetery(){

        int currentMap = 1;

        gp.fighters[currentMap][26] = new MON_Ghost(gp);
        gp.fighters[currentMap][26].worldX = tileSize * 10;
        gp.fighters[currentMap][26].worldY = tileSize * 29;
        gp.fighters[currentMap][26].setDefaultSpawn();

        gp.fighters[currentMap][27] = new MON_Ghost(gp);
        gp.fighters[currentMap][27].worldX = tileSize * 4;
        gp.fighters[currentMap][27].worldY = tileSize * 33;
        gp.fighters[currentMap][27].setDefaultSpawn();

        gp.fighters[currentMap][28] = new MON_Ghost(gp);
        gp.fighters[currentMap][28].worldX = tileSize * 4;
        gp.fighters[currentMap][28].worldY = tileSize * 30;
        gp.fighters[currentMap][28].setDefaultSpawn();

        gp.fighters[currentMap][29] = new MON_Ghost(gp);
        gp.fighters[currentMap][29].worldX = tileSize * 9;
        gp.fighters[currentMap][29].worldY = tileSize * 31;
        gp.fighters[currentMap][29].setDefaultSpawn();
    }

    public void setTurtleInCampBasedOnQuestA1(){
        if(gp.progress.act1InteractedObjects[4]){
            gp.npc[1][6] = new NPC_CampTortoise(gp);
            gp.npc[1][6].worldX = tileSize * 10;
            gp.npc[1][6].worldY = tileSize * 10;
        }
    }

    public void setObjectMap1(int currentMap){
        gp.obj[currentMap][0] = new OBJ_MapTransitionPickable(gp,8,7 * GamePanel.tileSize,10 * GamePanel.tileSize);
        gp.obj[currentMap][0].worldX = 37 * tileSize;
        gp.obj[currentMap][0].worldY = (int) (30.5 * tileSize);
        gp.obj[currentMap][0].interactSoundNumber = 56;

        gp.obj[currentMap][1] = new OBJ_MapTransitionPickable(gp,6,7 * GamePanel.tileSize,18 * GamePanel.tileSize);
        gp.obj[currentMap][1].worldX = (int) (7.7 * tileSize);
        gp.obj[currentMap][1].worldY = (int) (22.5 * tileSize);
        gp.obj[currentMap][1].interactSoundNumber = 56;

        if(!gp.progress.act1BookPickedUp[4]){
            gp.obj[currentMap][2] = new OBJ_TalentBook(gp);
            gp.obj[currentMap][2].worldX = 4 * tileSize;
            gp.obj[currentMap][2].worldY = 16 * tileSize;
        }

        gp.obj[currentMap][3] = new OBJ_MapTransitionPickable(gp,9,7 * GamePanel.tileSize,10 * GamePanel.tileSize);
        gp.obj[currentMap][3].worldX = 37 * tileSize;
        gp.obj[currentMap][3].worldY = 40 * tileSize;
        gp.obj[currentMap][3].interactSoundNumber = 56;

        gp.obj[currentMap][5] = new OBJ_StorageChest(gp);
        gp.obj[currentMap][5].worldX = 17 * tileSize;
        gp.obj[currentMap][5].worldY = tileSize;

        gp.obj[currentMap][6] = new OBJ_MapTransition(gp, 2,2 * GamePanel.tileSize,3 * GamePanel.tileSize);
        gp.obj[currentMap][6].worldX = (int)(0.5 * tileSize);
        gp.obj[currentMap][6].worldY = (int)(0.5 * tileSize);

        gp.obj[currentMap][8] = new OBJ_BridgeLeft(gp);
        gp.obj[currentMap][8].worldX = 27 * tileSize;
        gp.obj[currentMap][8].worldY = 5 * tileSize;

        gp.obj[currentMap][9] = new OBJ_BridgeRight(gp);
        gp.obj[currentMap][9].worldX = 28 * tileSize;
        gp.obj[currentMap][9].worldY = 5 * tileSize;

        gp.obj[currentMap][10] = new OBJ_Lever(gp);
        gp.obj[currentMap][10].worldX = 29 * tileSize;
        gp.obj[currentMap][10].worldY = 3 * tileSize;
        gp.obj[currentMap][10].interactThisList.add(gp.obj[currentMap][9]);
        if(gp.progress.act1InteractedObjects[0]){
            gp.obj[currentMap][10].progressInteract(); // separate method not to cause sound on loading
        }

        gp.obj[currentMap][11] = new OBJ_MapTransition(gp, 3, tileSize, tileSize * 2);
        gp.obj[currentMap][11].worldX = 48 * tileSize;
        gp.obj[currentMap][11].worldY = 49 * tileSize;

        gp.obj[currentMap][12] = new OBJ_MapTransition(gp, 4, tileSize * 41, tileSize * 2);
        gp.obj[currentMap][12].worldX = 16 * tileSize;
        gp.obj[currentMap][12].worldY = 49 * tileSize;

        gp.obj[currentMap][13] = new OBJ_MomoRefillOnce(gp,1);
        gp.obj[currentMap][13].worldX = 26 * tileSize;
        gp.obj[currentMap][13].worldY = 28 * tileSize;

        gp.obj[currentMap][14] = new OBJ_MomoRefillOnce(gp,2);
        gp.obj[currentMap][14].worldX = (int)(26.5 * tileSize);
        gp.obj[currentMap][14].worldY = 31 * tileSize;

        gp.obj[currentMap][15] = new OBJ_MomoRefillOnce(gp,3);
        gp.obj[currentMap][15].worldX = (int)(27.5 * tileSize);
        gp.obj[currentMap][15].worldY = 35 * tileSize;

        gp.obj[currentMap][16] = new OBJ_MomoRefillOnce(gp,4);
        gp.obj[currentMap][16].worldX = 27 * tileSize;
        gp.obj[currentMap][16].worldY = (int)(38.5 * tileSize);

        gp.obj[currentMap][17] = new OBJ_MapTransitionPickable(gp, 5, 9 * GamePanel.tileSize, (int)(15.5 * GamePanel.tileSize));
        gp.obj[currentMap][17].worldX = 33 * tileSize;
        gp.obj[currentMap][17].worldY = 21 * tileSize;

        gp.obj[currentMap][18] = new OBJ_A1Statue(gp);
        gp.obj[currentMap][18].worldX = 23 * tileSize;
        gp.obj[currentMap][18].worldY = 23 * tileSize;

        gp.obj[currentMap][19] = new OBJ_MapTransitionPickable(gp, 13, 9 * GamePanel.tileSize, (int)(9.5 * GamePanel.tileSize));
        gp.obj[currentMap][19].worldX = 20 * tileSize;
        gp.obj[currentMap][19].worldY = 35 * tileSize;
    }

    public void setObjectMap2(int currentMap){
        gp.obj[currentMap][0] = new OBJ_MapTransition(gp, 1, tileSize, 2 * tileSize);
        gp.obj[currentMap][0].worldX = 0;
        gp.obj[currentMap][0].worldY = 3 * tileSize;
        gp.obj[currentMap][0].image = setup("/objects/door/stairsup", tileSize, tileSize);

        gp.obj[currentMap][1] = new OBJ_MapTransition(gp, 10, 5 * tileSize, 34 * tileSize);
        gp.obj[currentMap][1].worldX = 22 * tileSize;
        gp.obj[currentMap][1].worldY = 2 * tileSize;

        gp.obj[currentMap][2] = new OBJ_Door(gp);
        gp.obj[currentMap][2].worldX = 37 * tileSize;
        gp.obj[currentMap][2].worldY = 22 * tileSize;

        gp.obj[currentMap][3] = new OBJ_BridgeLeft(gp);
        gp.obj[currentMap][3].worldX = 18 * tileSize;
        gp.obj[currentMap][3].worldY = 5 * tileSize;

        gp.obj[currentMap][4] = new OBJ_BridgeRight(gp);
        gp.obj[currentMap][4].worldX = 19 * tileSize;
        gp.obj[currentMap][4].worldY = 5 * tileSize;

        gp.obj[currentMap][5] = new OBJ_Lever(gp);
        gp.obj[currentMap][5].worldX = 42 * tileSize;
        gp.obj[currentMap][5].worldY = 36 * tileSize;
        gp.obj[currentMap][5].interactThisList.add(gp.obj[currentMap][2]);

        gp.obj[currentMap][6] = new OBJ_Lever(gp);
        gp.obj[currentMap][6].worldX = 22 * tileSize;
        gp.obj[currentMap][6].worldY = 7 * tileSize;
        gp.obj[currentMap][6].interactThisList.add(gp.obj[currentMap][4]);
        if(gp.progress.act1InteractedObjects[3]){
            gp.obj[currentMap][6].progressInteract();
        }

        OBJ_TreasureChest chest1 = new OBJ_TreasureChest(gp);
        chest1.chestType = 2;
        gp.obj[currentMap][7] = chest1;
        gp.obj[currentMap][7].worldX = 27 * tileSize;
        gp.obj[currentMap][7].worldY = 20 * tileSize;

        if(!gp.progress.act1BookPickedUp[7]){
            gp.obj[currentMap][8] = new OBJ_TalentBook(gp);
            gp.obj[currentMap][8].worldX = (int)(17.5 * tileSize);
            gp.obj[currentMap][8].worldY = 14 * tileSize;
        }
    }

    public void setObjectMap3(int currentMap){
        gp.obj[currentMap][0] = new OBJ_MomoRefillOnce(gp, 1);
        gp.obj[currentMap][0].worldX = 27 * tileSize;
        gp.obj[currentMap][0].worldY = 30 * tileSize;

        gp.obj[currentMap][1] = new OBJ_MapTransition(gp, 1, tileSize * 46, tileSize * 48);
        gp.obj[currentMap][1].worldX = tileSize;
        gp.obj[currentMap][1].worldY = 0;

        if(!gp.progress.act1InteractedObjects[4]){
            gp.obj[currentMap][2] = new OBJ_TurtleHelper(gp);
            gp.obj[currentMap][2].worldX = tileSize * 38;
            gp.obj[currentMap][2].worldY = tileSize * 28;
        }

        gp.obj[currentMap][3] = new OBJ_Mushroom(gp);
        gp.obj[currentMap][3].worldX = 12 * tileSize;
        gp.obj[currentMap][3].worldY = 33 * tileSize;

        gp.obj[currentMap][4] = new OBJ_Mushroom(gp);
        gp.obj[currentMap][4].worldX = 19 * tileSize;
        gp.obj[currentMap][4].worldY = 31 * tileSize;
    }

    public void setObjectMap4(int currentMap){
        gp.obj[currentMap][0] = new OBJ_MapTransition(gp, 1, tileSize * 16, tileSize * 48);
        gp.obj[currentMap][0].worldX = 41 * tileSize;
        gp.obj[currentMap][0].worldY = 0;

        gp.obj[currentMap][1] = new OBJ_Mushroom(gp);
        gp.obj[currentMap][1].worldX = 8 * tileSize;
        gp.obj[currentMap][1].worldY = 21 * tileSize;

        gp.obj[currentMap][2] = new OBJ_Mushroom(gp);
        gp.obj[currentMap][2].worldX = 4 * tileSize;
        gp.obj[currentMap][2].worldY = 20 * tileSize;

        gp.obj[currentMap][3] = new OBJ_Mushroom(gp);
        gp.obj[currentMap][3].worldX = 5 * tileSize;
        gp.obj[currentMap][3].worldY = 23 * tileSize;
    }

    private void setObjectMap5(int currentMap) {
        gp.obj[currentMap][0] = new OBJ_MapTransitionPickable(gp, 1, tileSize * 33, tileSize * 22);
        gp.obj[currentMap][0].worldX = 9 * tileSize;
        gp.obj[currentMap][0].worldY = 16 * tileSize;
    }

    public void setObjectMap6(int currentMap){
        gp.obj[currentMap][0] = new OBJ_Altar(gp);
        gp.obj[currentMap][0].worldX = 5 * tileSize;
        gp.obj[currentMap][0].worldY = (int)(0.5 * tileSize);

        gp.obj[currentMap][1] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][1].worldX = (int)(6.5 * tileSize);
        gp.obj[currentMap][1].worldY = (int)(6.5 * tileSize);
        ((OBJ_BookInLibrary) gp.obj[currentMap][1]).setTextExorcism();

        gp.obj[currentMap][2] = new OBJ_MapTransitionPickable(gp,1,8 * GamePanel.tileSize,23 * GamePanel.tileSize);
        gp.obj[currentMap][2].worldX = 7 * tileSize;
        gp.obj[currentMap][2].worldY = 19 * tileSize;
        gp.obj[currentMap][2].interactSoundNumber = 56;
        gp.obj[currentMap][2].image = setup("/entity/monster/act1/mimicdoor/mimic1", tileSize, tileSize);
    }

    public void setObjectMap7(int currentMap) {
        gp.obj[currentMap][0] = new OBJ_MapTransitionPickable(gp,9,2 * GamePanel.tileSize,3 * GamePanel.tileSize);
        gp.obj[currentMap][0].worldX = 0;
        gp.obj[currentMap][0].worldY = 7 * tileSize;
        gp.obj[currentMap][0].interactSoundNumber = 60;

        OBJ_TreasureChest chest1 = new OBJ_TreasureChest(gp);
        chest1.chestType = 1;
        gp.obj[currentMap][1] = chest1;
        gp.obj[currentMap][1].worldX = 46 * tileSize;
        gp.obj[currentMap][1].worldY = 6 * tileSize;
        gp.obj[currentMap][1].interactable = false;
        if(gp.progress.act1InteractedObjects[2]){
            gp.obj[currentMap][1].progressInteract();
        }
    }

    public void setObjectMap8(int currentMap){
        gp.obj[currentMap][0] = new OBJ_MapTransitionPickable(gp,1,37 * GamePanel.tileSize,32 * GamePanel.tileSize);
        gp.obj[currentMap][0].worldX = 7 * tileSize;
        gp.obj[currentMap][0].worldY = 11 * tileSize;
        gp.obj[currentMap][0].interactSoundNumber = 56;
        gp.obj[currentMap][0].image = setup("/entity/monster/act1/mimicdoor/mimic1", tileSize, tileSize);

        gp.obj[currentMap][1] = new OBJ_NPCShopA1House(gp);
        gp.obj[currentMap][1].worldX = (int) (6.5 * tileSize);
        gp.obj[currentMap][1].worldY = 6 * tileSize;
    }

    public void setObjectMap9(int currentMap){
        gp.obj[currentMap][0] = new OBJ_MapTransitionPickable(gp,1,37 * GamePanel.tileSize,41 * GamePanel.tileSize);
        gp.obj[currentMap][0].worldX = 7 * tileSize;
        gp.obj[currentMap][0].worldY = 11 * tileSize;
        gp.obj[currentMap][0].interactSoundNumber = 56;
        gp.obj[currentMap][0].image = setup("/entity/monster/act1/mimicdoor/mimic1", tileSize, tileSize);

        gp.obj[currentMap][1] = new OBJ_MapTransitionPickable(gp,7,2 * GamePanel.tileSize,7 * GamePanel.tileSize);
        gp.obj[currentMap][1].worldX = tileSize;
        gp.obj[currentMap][1].worldY = 3 * tileSize;
        gp.obj[currentMap][1].interactSoundNumber = 60;

        gp.obj[currentMap][2] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][2].worldX = 1 * tileSize;
        gp.obj[currentMap][2].worldY = 7 * tileSize;
        ((OBJ_BookInLibrary)(gp.obj[currentMap][2])).setTextLetterToMyFamilyFromCaptain();
    }

    public void setObjectMap10(int currentMap){
        //index 29 reserved for puzzle, don't use
        gp.obj[currentMap][0] = new OBJ_HarryPotterPuzzle(gp);
        gp.obj[currentMap][0].worldX = 18 * tileSize;
        gp.obj[currentMap][0].worldY = 13 * tileSize;

        if(!gp.progress.act1BookPickedUp[6]) {
            gp.obj[currentMap][1] = new OBJ_FireObject(gp);
            gp.obj[currentMap][1].worldX = 24 * tileSize;
            gp.obj[currentMap][1].worldY = 11 * tileSize;
        }

        if(!gp.progress.act1BookPickedUp[6]){
            gp.obj[currentMap][2] = new OBJ_TalentBook(gp);
            gp.obj[currentMap][2].worldX = 24 * tileSize;
            gp.obj[currentMap][2].worldY = 9 * tileSize;
        }

        gp.obj[currentMap][3] = new OBJ_MapTransition(gp, 2, 22 * tileSize,4 * tileSize);
        gp.obj[currentMap][3].worldX = 5 * tileSize;
        gp.obj[currentMap][3].worldY = 37 * tileSize;

        gp.obj[currentMap][4] = new OBJ_MapTransition(gp, 11, 5 * tileSize,10 * tileSize);
        gp.obj[currentMap][4].solidArea.width = 128;
        gp.obj[currentMap][4].worldX = 5 * tileSize;
        gp.obj[currentMap][4].worldY = 0;

        gp.obj[currentMap][5] = new OBJ_Door(gp);
        gp.obj[currentMap][5].worldX = 11 * tileSize;
        gp.obj[currentMap][5].worldY = 30 * tileSize;

        gp.obj[currentMap][6] = new OBJ_Lever(gp);
        gp.obj[currentMap][6].worldX = tileSize;
        gp.obj[currentMap][6].worldY = 28 * tileSize;
        gp.obj[currentMap][6].interactThisList.add(gp.obj[currentMap][5]);
    }

    private void setObjectMap11(int currentMap){
        gp.obj[currentMap][0] = new OBJ_A1Ending(gp);
        gp.obj[currentMap][0].worldX = (int)(6.5 * tileSize);
        gp.obj[currentMap][0].worldY = tileSize;

        gp.obj[currentMap][1] = new OBJ_FinalDoor(gp);
        gp.obj[currentMap][1].worldX = 6 * tileSize;
        gp.obj[currentMap][1].worldY = 0;
    }

    private void setObjectMap13(int currentMap){
        gp.obj[currentMap][0] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][0].worldX = 4 * tileSize;
        gp.obj[currentMap][0].worldY = 3 * tileSize;
        ((OBJ_BookInLibrary) gp.obj[currentMap][0]).setTextTownStatue();

        gp.obj[currentMap][1] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][1].worldX = 6 * tileSize;
        gp.obj[currentMap][1].worldY = 3 * tileSize;
        ((OBJ_BookInLibrary) gp.obj[currentMap][1]).setTextMushrooms();

        gp.obj[currentMap][2] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][2].worldX = 8 * tileSize;
        gp.obj[currentMap][2].worldY = 3 * tileSize;
        ((OBJ_BookInLibrary) gp.obj[currentMap][2]).setTextTurtles();

        gp.obj[currentMap][3] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][3].worldX = 10 * tileSize;
        gp.obj[currentMap][3].worldY = 3 * tileSize;
        ((OBJ_BookInLibrary) gp.obj[currentMap][3]).setTextPumpkinTrade();

        gp.obj[currentMap][4] = new OBJ_BookInLibrary(gp);
        gp.obj[currentMap][4].worldX = 12 * tileSize;
        gp.obj[currentMap][4].worldY = 3 * tileSize;
        ((OBJ_BookInLibrary) gp.obj[currentMap][4]).setTextUnderWater();

        gp.obj[currentMap][5] = new OBJ_MapTransitionPickable(gp, 1, 20 * tileSize, 36 * tileSize);
        gp.obj[currentMap][5].worldX = 9 * tileSize;
        gp.obj[currentMap][5].worldY = 10    * tileSize;
    }

    private void setNPCMap1(int currentMap) {
        //6 and 8 taken
        gp.npc[currentMap][0] = new NPC_Cow(gp);
        gp.npc[currentMap][0].worldX = tileSize * 26;
        gp.npc[currentMap][0].worldY = tileSize * 21;

        gp.npc[currentMap][1] = new NPC_Cow(gp);
        gp.npc[currentMap][1].worldX = tileSize * 27;
        gp.npc[currentMap][1].worldY = tileSize * 21;

        gp.npc[currentMap][2] = new NPC_Horse(gp);
        gp.npc[currentMap][2].worldX = tileSize * 22;
        gp.npc[currentMap][2].worldY = tileSize * 4;
        gp.npc[currentMap][2].aiBehaviourNumber = 1;

        gp.npc[currentMap][3] = new NPC_CaravanBro(gp);
        gp.npc[currentMap][3].worldX = tileSize * 22;
        gp.npc[currentMap][3].worldY = tileSize * 4;
        gp.npc[currentMap][3].aiBehaviourNumber = 1;

        gp.npc[currentMap][4] = new NPC_Pumpkin(gp);
        gp.npc[currentMap][4].worldX = tileSize * 33;
        gp.npc[currentMap][4].worldY = tileSize * 33;

        gp.npc[currentMap][5] = new NPC_Doggy(gp);
        gp.npc[currentMap][5].worldX = tileSize * 23;
        gp.npc[currentMap][5].worldY = tileSize * 22;

        setTurtleInCampBasedOnQuestA1(); // uses index 6

        // index 8 used by npc_arrowchild spawned by fighter entity
    }

    private void setNPCMap13(int currentMap){
        gp.npc[currentMap][0] = new NPC_Librarian(gp);
        gp.npc[currentMap][0].worldX = tileSize * 5;
        gp.npc[currentMap][0].worldY = tileSize * 5;

    }



    private void setNPCMap3(int currentMap) {
        if(!gp.progress.act1InteractedObjects[4]){
            gp.npc[currentMap][0] = new NPC_TurnedOverTortoise(gp);
            gp.npc[currentMap][0].worldX = tileSize * 38;
            gp.npc[currentMap][0].worldY = tileSize * 28;
        }
    }
    private void setNPCMap4(int currentMap) {
        gp.npc[currentMap][0] = new NPC_Butterflies(gp);
        gp.npc[currentMap][0].worldX = tileSize * 28;
        gp.npc[currentMap][0].worldY = tileSize * 3;
        gp.npc[currentMap][0].setDefaultSpawn();

        gp.npc[currentMap][1] = new NPC_Butterflies(gp);
        gp.npc[currentMap][1].worldX = tileSize * 21;
        gp.npc[currentMap][1].worldY = tileSize * 19;
        gp.npc[currentMap][1].setDefaultSpawn();

    }
    private void setNPCMap5(int currentMap) {
        //uses index 0...4
        setSingersKindergarten(currentMap);
    }

    private void setNPCMap6(int currentMap) {
        gp.npc[currentMap][0] = new NPC_Priest(gp);
        gp.npc[currentMap][0].worldX = tileSize * 6;
        gp.npc[currentMap][0].worldY = tileSize * 13;
        gp.npc[currentMap][0].aiBehaviourNumber = 1;
    }

    private void setNPCMap8(int currentMap) {
        gp.npc[currentMap][0] = new NPC_Shopkeeper(gp);
        gp.npc[currentMap][0].worldX = (int)(tileSize * 6.5);
        gp.npc[currentMap][0].worldY = tileSize * 4;
    }

    private void setNPCMap9(int currentMap) {
        gp.npc[currentMap][0] = new NPC_Mom(gp);
        gp.npc[currentMap][0].worldX = tileSize * 5;
        gp.npc[currentMap][0].worldY = tileSize * 5;

        gp.npc[currentMap][1] = new NPC_Dad(gp);
        gp.npc[currentMap][1].worldX = tileSize * 8;
        gp.npc[currentMap][1].worldY = tileSize * 8;

        setBigBroNPC(); // uses index 2
    }

    private void setFighterMap1(int currentMap) {
        gp.fighters[currentMap][0] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][0].worldX = tileSize * 33 ;
        gp.fighters[currentMap][0].worldY = tileSize * 4;
        gp.fighters[currentMap][0].speed = 1;

        gp.fighters[currentMap][1] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][1].worldX = tileSize * 37 ;
        gp.fighters[currentMap][1].worldY = tileSize * 11;

        gp.fighters[currentMap][2] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][2].worldX = tileSize * 39 ;
        gp.fighters[currentMap][2].worldY = tileSize *10;

        gp.fighters[currentMap][3] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][3].worldX = tileSize * 48;
        gp.fighters[currentMap][3].worldY = tileSize * 10;

        gp.fighters[currentMap][4] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][4].worldX = tileSize * 43;
        gp.fighters[currentMap][4].worldY = tileSize * 12;

        gp.fighters[currentMap][5] = new ENEMY_Child(gp);
        gp.fighters[currentMap][5].worldX = tileSize * 34;
        gp.fighters[currentMap][5].worldY = tileSize * 18;

        ghostsNearCemetery(); //uses index 26....29
    }

    private void setFighterMap2(int currentMap) {
        gp.fighters[currentMap][0] = new MON_Ogre(gp);
        gp.fighters[currentMap][0].worldX = tileSize * 15 ;
        gp.fighters[currentMap][0].worldY = tileSize * 3;
        gp.fighters[currentMap][0].setDefaultSpawn();

        gp.fighters[currentMap][1] = new MON_Bat(gp);
        gp.fighters[currentMap][1].worldX = tileSize * 15 ;
        gp.fighters[currentMap][1].worldY = tileSize * 3;
        gp.fighters[currentMap][1].setDefaultSpawn();

        gp.fighters[currentMap][2] = new MON_Ogre(gp);
        gp.fighters[currentMap][2].worldX = tileSize * 6 ;
        gp.fighters[currentMap][2].worldY = tileSize * 4;
        gp.fighters[currentMap][2].setDefaultSpawn();

        gp.fighters[currentMap][3] = new MON_Bat(gp);
        gp.fighters[currentMap][3].worldX = tileSize * 5;
        gp.fighters[currentMap][3].worldY = tileSize * 17;
        gp.fighters[currentMap][3].setDefaultSpawn();

        gp.fighters[currentMap][4] = new MON_Ogre(gp);
        gp.fighters[currentMap][4].worldX = tileSize * 9 ;
        gp.fighters[currentMap][4].worldY = tileSize * 15;
        gp.fighters[currentMap][4].setDefaultSpawn();

        gp.fighters[currentMap][5] = new MON_Bat(gp);
        gp.fighters[currentMap][5].worldX = tileSize * 9 ;
        gp.fighters[currentMap][5].worldY = tileSize * 18;
        gp.fighters[currentMap][5].setDefaultSpawn();

        gp.fighters[currentMap][6] = new MON_Ogre(gp);
        gp.fighters[currentMap][6].worldX = tileSize * 3 ;
        gp.fighters[currentMap][6].worldY = tileSize * 21;
        gp.fighters[currentMap][6].setDefaultSpawn();

        gp.fighters[currentMap][7] = new MON_Bat(gp);
        gp.fighters[currentMap][7].worldX = tileSize * 7 ;
        gp.fighters[currentMap][7].worldY = tileSize * 24;
        gp.fighters[currentMap][7].setDefaultSpawn();

        gp.fighters[currentMap][8] = new MON_Ogre(gp);
        gp.fighters[currentMap][8].worldX = tileSize * 11 ;
        gp.fighters[currentMap][8].worldY = tileSize * 27;
        gp.fighters[currentMap][8].setDefaultSpawn();

        gp.fighters[currentMap][9] = new MON_Bat(gp);
        gp.fighters[currentMap][9].worldX = tileSize * 4 ;
        gp.fighters[currentMap][9].worldY = tileSize * 32;
        gp.fighters[currentMap][9].setDefaultSpawn();

        gp.fighters[currentMap][10] = new MON_Ogre(gp);
        gp.fighters[currentMap][10].worldX = tileSize * 7;
        gp.fighters[currentMap][10].worldY = tileSize * 34;
        gp.fighters[currentMap][10].setDefaultSpawn();

        /////////////////////////////////////////////
        gp.fighters[currentMap][11] = new MON_Bat(gp);
        gp.fighters[currentMap][11].worldX = tileSize * 20 ;
        gp.fighters[currentMap][11].worldY = tileSize * 31;
        gp.fighters[currentMap][11].setDefaultSpawn();

        gp.fighters[currentMap][12] = new MON_Ogre(gp);
        gp.fighters[currentMap][12].worldX = tileSize * 20;
        gp.fighters[currentMap][12].worldY = tileSize * 30;
        gp.fighters[currentMap][12].setDefaultSpawn();

        gp.fighters[currentMap][13] = new MON_Bat(gp);
        gp.fighters[currentMap][13].worldX = tileSize * 20;
        gp.fighters[currentMap][13].worldY = tileSize * 29;
        gp.fighters[currentMap][13].setDefaultSpawn();

        gp.fighters[currentMap][14] = new MON_Ogre(gp);
        gp.fighters[currentMap][14].worldX = tileSize * 27;
        gp.fighters[currentMap][14].worldY = tileSize * 32;
        gp.fighters[currentMap][14].setDefaultSpawn();

        gp.fighters[currentMap][15] = new MON_Bat(gp);
        gp.fighters[currentMap][15].worldX = tileSize * 28 ;
        gp.fighters[currentMap][15].worldY = tileSize * 28;
        gp.fighters[currentMap][15].setDefaultSpawn();

        gp.fighters[currentMap][16] = new MON_Ogre(gp);
        gp.fighters[currentMap][16].worldX = tileSize * 33;
        gp.fighters[currentMap][16].worldY = tileSize * 33;
        gp.fighters[currentMap][16].setDefaultSpawn();

        gp.fighters[currentMap][17] = new MON_Bat(gp);
        gp.fighters[currentMap][17].worldX = tileSize * 39 ;
        gp.fighters[currentMap][17].worldY = tileSize * 33;
        gp.fighters[currentMap][17].setDefaultSpawn();

        gp.fighters[currentMap][18] = new MON_Ogre(gp);
        gp.fighters[currentMap][18].worldX = tileSize * 39;
        gp.fighters[currentMap][18].worldY = tileSize * 26;
        gp.fighters[currentMap][18].setDefaultSpawn();

        gp.fighters[currentMap][19] = new MON_Bat(gp);
        gp.fighters[currentMap][19].worldX = tileSize * 35 ;
        gp.fighters[currentMap][19].worldY = tileSize * 23;
        gp.fighters[currentMap][19].setDefaultSpawn();

        gp.fighters[currentMap][20] = new MON_Ogre(gp);
        gp.fighters[currentMap][20].worldX = tileSize * 34;
        gp.fighters[currentMap][20].worldY = tileSize * 19;
        gp.fighters[currentMap][20].setDefaultSpawn();

        gp.fighters[currentMap][21] = new MON_Bat(gp);
        gp.fighters[currentMap][21].worldX = tileSize * 34 ;
        gp.fighters[currentMap][21].worldY = tileSize * 13;
        gp.fighters[currentMap][21].setDefaultSpawn();

        gp.fighters[currentMap][22] = new MON_Ogre(gp);
        gp.fighters[currentMap][22].worldX = tileSize * 40;
        gp.fighters[currentMap][22].worldY = tileSize * 9;
        gp.fighters[currentMap][22].setDefaultSpawn();

        gp.fighters[currentMap][23] = new MON_Bat(gp);
        gp.fighters[currentMap][23].worldX = tileSize * 31 ;
        gp.fighters[currentMap][23].worldY = tileSize * 5;
        gp.fighters[currentMap][23].setDefaultSpawn();

        gp.fighters[currentMap][24] = new MON_Ogre(gp);
        gp.fighters[currentMap][24].worldX = tileSize * 26;
        gp.fighters[currentMap][24].worldY = tileSize * 18;
        gp.fighters[currentMap][24].setDefaultSpawn();

        gp.fighters[currentMap][25] = new MON_Ogre(gp);
        gp.fighters[currentMap][25].worldX = tileSize * 25 ;
        gp.fighters[currentMap][25].worldY = tileSize * 18;
        gp.fighters[currentMap][25].setDefaultSpawn();

        gp.fighters[currentMap][26] = new MON_Bat(gp);
        gp.fighters[currentMap][26].worldX = tileSize * 25;
        gp.fighters[currentMap][26].worldY = tileSize * 20;
        gp.fighters[currentMap][26].setDefaultSpawn();
    }

    private void setFighterMap3(int currentMap) {
        gp.fighters[currentMap][0] = new MON_PikaPika(gp);
        gp.fighters[currentMap][0].worldX = tileSize * 11;
        gp.fighters[currentMap][0].worldY = tileSize * 12;
        gp.fighters[currentMap][0].setDefaultSpawn();

        gp.fighters[currentMap][1] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][1].worldX = tileSize * 13;
        gp.fighters[currentMap][1].worldY = tileSize * 12;
        gp.fighters[currentMap][1].setDefaultSpawn();

        gp.fighters[currentMap][2] = new MON_RedBunny(gp);
        gp.fighters[currentMap][2].worldX = tileSize * 38;
        gp.fighters[currentMap][2].worldY = tileSize * 3;
        gp.fighters[currentMap][2].setDefaultSpawn();

        gp.fighters[currentMap][3] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][3].worldX = tileSize * 7;
        gp.fighters[currentMap][3].worldY = tileSize * 4;
        gp.fighters[currentMap][3].speed = 1;
        gp.fighters[currentMap][3].setDefaultSpawn();

        gp.fighters[currentMap][4] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][4].worldX = tileSize * 3;
        gp.fighters[currentMap][4].worldY = tileSize * 8;
        gp.fighters[currentMap][4].speed = 1;
        gp.fighters[currentMap][4].setDefaultSpawn();

        gp.fighters[currentMap][5] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][5].worldX = tileSize * 3;
        gp.fighters[currentMap][5].worldY = tileSize * 10;
        gp.fighters[currentMap][5].speed = 1;
        gp.fighters[currentMap][5].setDefaultSpawn();

        gp.fighters[currentMap][6] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][6].worldX = tileSize * 41;
        gp.fighters[currentMap][6].worldY = tileSize * 32;
        gp.fighters[currentMap][6].setDefaultSpawn();

        gp.fighters[currentMap][7] = new MON_GreenSlime(gp);
        gp.fighters[currentMap][7].worldX = tileSize * 43;
        gp.fighters[currentMap][7].worldY = tileSize * 37;
        gp.fighters[currentMap][7].setDefaultSpawn();

        gp.fighters[currentMap][8] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][8].worldX = tileSize * 4;
        gp.fighters[currentMap][8].worldY = tileSize * 21;
        gp.fighters[currentMap][8].setDefaultSpawn();

        gp.fighters[currentMap][9] = new MON_PikaPika(gp);
        gp.fighters[currentMap][9].worldX = tileSize * 7;
        gp.fighters[currentMap][9].worldY = tileSize * 24;
        gp.fighters[currentMap][9].setDefaultSpawn();

        gp.fighters[currentMap][10] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][10].worldX = tileSize * 8;
        gp.fighters[currentMap][10].worldY = tileSize * 28;
        gp.fighters[currentMap][10].setDefaultSpawn();

        gp.fighters[currentMap][11] = new MON_PikaPika(gp);
        gp.fighters[currentMap][11].worldX = tileSize * 3;
        gp.fighters[currentMap][11].worldY = tileSize * 32;
        gp.fighters[currentMap][11].setDefaultSpawn();

        gp.fighters[currentMap][12] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][12].worldX = tileSize * 5;
        gp.fighters[currentMap][12].worldY = tileSize * 36;
        gp.fighters[currentMap][12].setDefaultSpawn();

        gp.fighters[currentMap][13] = new MON_PikaPika(gp);
        gp.fighters[currentMap][13].worldX = tileSize * 13;
        gp.fighters[currentMap][13].worldY = tileSize * 28;
        gp.fighters[currentMap][13].setDefaultSpawn();

        gp.fighters[currentMap][13] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][13].worldX = tileSize * 15;
        gp.fighters[currentMap][13].worldY = tileSize * 23;
        gp.fighters[currentMap][13].setDefaultSpawn();

        gp.fighters[currentMap][14] = new MON_PikaPika(gp);
        gp.fighters[currentMap][14].worldX = tileSize * 18;
        gp.fighters[currentMap][14].worldY = tileSize * 19;
        gp.fighters[currentMap][14].setDefaultSpawn();

        gp.fighters[currentMap][15] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][15].worldX = tileSize * 20;
        gp.fighters[currentMap][15].worldY = tileSize * 24;
        gp.fighters[currentMap][15].setDefaultSpawn();

        gp.fighters[currentMap][16] = new MON_PikaPika(gp);
        gp.fighters[currentMap][16].worldX = tileSize * 22;
        gp.fighters[currentMap][16].worldY = tileSize * 10;
        gp.fighters[currentMap][16].setDefaultSpawn();

        gp.fighters[currentMap][17] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][17].worldX = tileSize * 23;
        gp.fighters[currentMap][17].worldY = tileSize * 5;
        gp.fighters[currentMap][17].setDefaultSpawn();

        gp.fighters[currentMap][18] = new MON_PikaPika(gp);
        gp.fighters[currentMap][18].worldX = tileSize * 20;
        gp.fighters[currentMap][18].worldY = tileSize * 3;
        gp.fighters[currentMap][18].setDefaultSpawn();

        gp.fighters[currentMap][19] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][19].worldX = tileSize * 13;
        gp.fighters[currentMap][19].worldY = tileSize * 2;
        gp.fighters[currentMap][19].setDefaultSpawn();

        gp.fighters[currentMap][20] = new MON_PikaPika(gp);
        gp.fighters[currentMap][20].worldX = tileSize * 13;
        gp.fighters[currentMap][20].worldY = tileSize * 3;
        gp.fighters[currentMap][20].setDefaultSpawn();

        gp.fighters[currentMap][21] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][21].worldX = tileSize * 35;
        gp.fighters[currentMap][21].worldY = tileSize * 17;
        gp.fighters[currentMap][21].setDefaultSpawn();

        gp.fighters[currentMap][22] = new MON_PikaPika(gp);
        gp.fighters[currentMap][22].worldX = tileSize * 37;
        gp.fighters[currentMap][22].worldY = tileSize * 22;
        gp.fighters[currentMap][22].setDefaultSpawn();

        gp.fighters[currentMap][23] = new MON_GreenBunny(gp);
        gp.fighters[currentMap][23].worldX = tileSize * 40;
        gp.fighters[currentMap][23].worldY = tileSize * 25;
        gp.fighters[currentMap][23].setDefaultSpawn();
    }

    public void setFighterMap4(int currentMap){
        gp.fighters[currentMap][0] = new MON_Wolf(gp);
        gp.fighters[currentMap][0].worldX = tileSize * 20;
        gp.fighters[currentMap][0].worldY = tileSize * 20;
        gp.fighters[currentMap][0].setDefaultSpawn();

        gp.fighters[currentMap][1] = new MON_RedWizard(gp);
        gp.fighters[currentMap][1].worldX = tileSize * 12;
        gp.fighters[currentMap][1].worldY = tileSize * 8;
        gp.fighters[currentMap][1].setDefaultSpawn();

        gp.fighters[currentMap][2] = new MON_Wolf(gp);
        gp.fighters[currentMap][2].worldX = tileSize * 10;
        gp.fighters[currentMap][2].worldY = tileSize * 19;
        gp.fighters[currentMap][2].setDefaultSpawn();

        gp.fighters[currentMap][3] = new MON_Wolf(gp);
        gp.fighters[currentMap][3].worldX = tileSize * 6;
        gp.fighters[currentMap][3].worldY = tileSize * 22;
        gp.fighters[currentMap][3].setDefaultSpawn();

        gp.fighters[currentMap][4] = new MON_Wolf(gp);
        gp.fighters[currentMap][4].worldX = tileSize * 15;
        gp.fighters[currentMap][4].worldY = tileSize * 22;
        gp.fighters[currentMap][4].setDefaultSpawn();

        gp.fighters[currentMap][5] = new MON_Wolf(gp);
        gp.fighters[currentMap][5].worldX = tileSize * 21;
        gp.fighters[currentMap][5].worldY = tileSize * 16;
        gp.fighters[currentMap][5].setDefaultSpawn();

        gp.fighters[currentMap][6] = new MON_Wolf(gp);
        gp.fighters[currentMap][6].worldX = tileSize * 23;
        gp.fighters[currentMap][6].worldY = tileSize * 10;
        gp.fighters[currentMap][6].setDefaultSpawn();

        gp.fighters[currentMap][7] = new MON_Wolf(gp);
        gp.fighters[currentMap][7].worldX = tileSize * 22;
        gp.fighters[currentMap][7].worldY = tileSize * 8;
        gp.fighters[currentMap][7].setDefaultSpawn();

        gp.fighters[currentMap][8] = new MON_Wolf(gp);
        gp.fighters[currentMap][8].worldX = tileSize * 30;
        gp.fighters[currentMap][8].worldY = tileSize * 17;
        gp.fighters[currentMap][8].setDefaultSpawn();

        gp.fighters[currentMap][9] = new MON_Wolf(gp);
        gp.fighters[currentMap][9].worldX = tileSize * 31;
        gp.fighters[currentMap][9].worldY = tileSize * 10;
        gp.fighters[currentMap][9].setDefaultSpawn();

        gp.fighters[currentMap][10] = new MON_Wolf(gp);
        gp.fighters[currentMap][10].worldX = tileSize * 32;
        gp.fighters[currentMap][10].worldY = tileSize * 6;
        gp.fighters[currentMap][10].setDefaultSpawn();

        gp.fighters[currentMap][11] = new MON_Wolf(gp);
        gp.fighters[currentMap][11].worldX = tileSize * 41;
        gp.fighters[currentMap][11].worldY = tileSize * 9;
        gp.fighters[currentMap][11].setDefaultSpawn();

        gp.fighters[currentMap][12] = new MON_Wolf(gp);
        gp.fighters[currentMap][12].worldX = tileSize * 41;
        gp.fighters[currentMap][12].worldY = tileSize * 15;
        gp.fighters[currentMap][12].setDefaultSpawn();

        gp.fighters[currentMap][13] = new MON_Wolf(gp);
        gp.fighters[currentMap][13].worldX = tileSize * 36;
        gp.fighters[currentMap][13].worldY = tileSize * 19;
        gp.fighters[currentMap][13].setDefaultSpawn();
    }

    public void setFighterMap7(int currentMap){
        setBigBroFighter();

        gp.fighters[currentMap][1] = new MON_Skeleton(gp);
        gp.fighters[currentMap][1].worldX = GamePanel.tileSize * 11;
        gp.fighters[currentMap][1].worldY = GamePanel.tileSize * 9;
        gp.fighters[currentMap][1].setDefaultSpawn();

        gp.fighters[currentMap][2] = new MON_Skeleton(gp);
        gp.fighters[currentMap][2].worldX = GamePanel.tileSize * 12;
        gp.fighters[currentMap][2].worldY = GamePanel.tileSize * 9;
        gp.fighters[currentMap][2].setDefaultSpawn();

        gp.fighters[currentMap][3] = new MON_Skeleton(gp);
        gp.fighters[currentMap][3].worldX = GamePanel.tileSize * 16;
        gp.fighters[currentMap][3].worldY = GamePanel.tileSize * 3;
        gp.fighters[currentMap][3].setDefaultSpawn();

        gp.fighters[currentMap][4] = new MON_Skeleton(gp);
        gp.fighters[currentMap][4].worldX = GamePanel.tileSize * 16;
        gp.fighters[currentMap][4].worldY = GamePanel.tileSize * 10;
        gp.fighters[currentMap][4].setDefaultSpawn();

        gp.fighters[currentMap][5] = new MON_Skeleton(gp);
        gp.fighters[currentMap][5].worldX = GamePanel.tileSize * 22;
        gp.fighters[currentMap][5].worldY = GamePanel.tileSize * 9;
        gp.fighters[currentMap][5].setDefaultSpawn();

        gp.fighters[currentMap][6] = new MON_Skeleton(gp);
        gp.fighters[currentMap][6].worldX = GamePanel.tileSize * 22;
        gp.fighters[currentMap][6].worldY = GamePanel.tileSize * 4;
        gp.fighters[currentMap][6].setDefaultSpawn();

        gp.fighters[currentMap][7] = new MON_Skeleton(gp);
        gp.fighters[currentMap][7].worldX = GamePanel.tileSize * 25;
        gp.fighters[currentMap][7].worldY = GamePanel.tileSize * 6;
        gp.fighters[currentMap][7].setDefaultSpawn();

        gp.fighters[currentMap][8] = new MON_Skeleton(gp);
        gp.fighters[currentMap][8].worldX = GamePanel.tileSize * 29;
        gp.fighters[currentMap][8].worldY = GamePanel.tileSize * 10;
        gp.fighters[currentMap][8].setDefaultSpawn();

        gp.fighters[currentMap][9] = new MON_Skeleton(gp);
        gp.fighters[currentMap][9].worldX = GamePanel.tileSize * 29;
        gp.fighters[currentMap][9].worldY = GamePanel.tileSize * 10;
        gp.fighters[currentMap][9].setDefaultSpawn();

        gp.fighters[currentMap][10] = new MON_Skeleton(gp);
        gp.fighters[currentMap][10].worldX = GamePanel.tileSize * 35;
        gp.fighters[currentMap][10].worldY = GamePanel.tileSize * 7;
        gp.fighters[currentMap][10].setDefaultSpawn();

        gp.fighters[currentMap][11] = new MON_Skeleton(gp);
        gp.fighters[currentMap][11].worldX = GamePanel.tileSize * 44;
        gp.fighters[currentMap][11].worldY = GamePanel.tileSize * 3;
        gp.fighters[currentMap][11].setDefaultSpawn();

        gp.fighters[currentMap][12] = new MON_Skeleton(gp);
        gp.fighters[currentMap][12].worldX = GamePanel.tileSize * 45;
        gp.fighters[currentMap][12].worldY = GamePanel.tileSize * 4;
        gp.fighters[currentMap][12].setDefaultSpawn();

        gp.fighters[currentMap][13] = new MON_Skeleton(gp);
        gp.fighters[currentMap][13].worldX = GamePanel.tileSize * 45;
        gp.fighters[currentMap][13].worldY = GamePanel.tileSize * 8;
        gp.fighters[currentMap][13].setDefaultSpawn();

        gp.fighters[currentMap][14] = new MON_Skeleton(gp);
        gp.fighters[currentMap][14].worldX = GamePanel.tileSize * 44;
        gp.fighters[currentMap][14].worldY = GamePanel.tileSize * 9;
        gp.fighters[currentMap][14].setDefaultSpawn();

        gp.fighters[currentMap][15] = new MON_SkeletonKing(gp);
        gp.fighters[currentMap][15].worldX = GamePanel.tileSize * 45;
        gp.fighters[currentMap][15].worldY = GamePanel.tileSize * 6;
        gp.fighters[currentMap][15].setDefaultSpawn();
    }

    public void setFighterMap10(int currentMap){
        gp.fighters[currentMap][0] = new MON_Totem(gp);
        gp.fighters[currentMap][0].worldX = GamePanel.tileSize * 17;
        gp.fighters[currentMap][0].worldY = GamePanel.tileSize * 28;
        gp.fighters[currentMap][0].setAI(1);

        gp.fighters[currentMap][1] = new MON_Totem(gp);
        gp.fighters[currentMap][1].worldX = GamePanel.tileSize * 20;
        gp.fighters[currentMap][1].worldY = GamePanel.tileSize * 28;
        gp.fighters[currentMap][1].setAI(1);

        gp.fighters[currentMap][2] = new MON_Totem(gp);
        gp.fighters[currentMap][2].worldX = GamePanel.tileSize * 17;
        gp.fighters[currentMap][2].worldY = GamePanel.tileSize * 31;
        gp.fighters[currentMap][2].setAI(1);

        gp.fighters[currentMap][3] = new MON_Totem(gp);
        gp.fighters[currentMap][3].worldX = GamePanel.tileSize * 20;
        gp.fighters[currentMap][3].worldY = GamePanel.tileSize * 31;
        gp.fighters[currentMap][3].setAI(1);

        gp.fighters[currentMap][4] = new MON_MimicDoor(gp);
        gp.fighters[currentMap][4].worldX = GamePanel.tileSize * 11;
        gp.fighters[currentMap][4].worldY = GamePanel.tileSize * 14;
    }

    public void setFighter11(int currentMap){
        gp.fighters[currentMap][0] = new MON_ShadowBoss(gp);
        gp.fighters[currentMap][0].worldX = GamePanel.tileSize * 10;
        gp.fighters[currentMap][0].worldY = GamePanel.tileSize * 5;
    }

    public void setBigBroFighter(){
        if(!gp.progress.act1InteractedObjects[2]) {
            gp.fighters[7][0] = new A_Brother(gp);
            gp.fighters[7][0].worldX = GamePanel.tileSize;
            gp.fighters[7][0].worldY = GamePanel.tileSize * 3;
        } else {
            gp.fighters[7][0] = null;
        }
    }

    public void setBigBroNPC(){
        if(gp.progress.act1InteractedObjects[2]){
            gp.npc[9][2] = new NPC_Bro(gp);
            gp.npc[9][2].worldX = tileSize * 6;
            gp.npc[9][2].worldY = tileSize * 6;
        }
    }

    public void setSingersKindergarten(int currentMap){
        gp.npc[currentMap][0] = new NPC_NapTimeEnforcer(gp);
        gp.npc[currentMap][0].worldX = 8 * tileSize;
        gp.npc[currentMap][0].worldY = 8 * tileSize;

        gp.npc[currentMap][1] = new NPC_Child1(gp);
        gp.npc[currentMap][1].worldX = 8 * tileSize;
        gp.npc[currentMap][1].worldY = 10 * tileSize;

        gp.npc[currentMap][2] = new NPC_Child2(gp);
        gp.npc[currentMap][2].worldX = 10 * tileSize;
        gp.npc[currentMap][2].worldY = 11 * tileSize;

        gp.npc[currentMap][3] = new NPC_Child3(gp);
        gp.npc[currentMap][3].worldX = 8 * tileSize;
        gp.npc[currentMap][3].worldY = 12 * tileSize;

        gp.npc[currentMap][4] = new NPC_Child4(gp);
        gp.npc[currentMap][4].worldX = 6 * tileSize;
        gp.npc[currentMap][4].worldY = 11 * tileSize;
    }
}
