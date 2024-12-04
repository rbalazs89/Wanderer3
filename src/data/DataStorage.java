package data;

import java.io.Serializable;

public class DataStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    public int level;
    int currentExperience;
    int previousLevelExp;
    int nextLevelExp;
    int gold;
    int unSpentStats;
    int dexterityFromLvl;
    int strengthFromLvl;
    int enduranceFromLvl;
    int intelligenceFromLvl;
    int life;
    int mana;
    int unSpentSkillPoints;
    int unSpentTalentPoints;
    int maxManaFromUp;
    int maxLifeFromUp;
    int[] talentPoints = new int[7];
    int[] skillPoints = new int[12];
    int[] equippedSkills = new int[6];
    boolean[] act1BookPickedUp = new boolean[8];
    boolean[] act1InteractedObjects = new boolean[4];

    //equipped items:
    int[] equippedItems = new int[8];

    //Inventory
    //1st number: inventory location - > 0: player inventory; 1: storage chest 1; 2: storage chest 2...
    //2nd number: location within the inventory 9 columns x 11 rows
    //number itself: item code
    int[][]inventoryPages = new int[5][99];

}
