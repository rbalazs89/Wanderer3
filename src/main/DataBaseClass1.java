package main;
public class DataBaseClass1 {
    /** this class has no real logic related to game process, just functions that return numbers */
    GamePanel gp;
    public DataBaseClass1(GamePanel gp){
        this.gp = gp;
    }

    // put here rather than player class for better readability
    public static int getRequiredExpForNextLevel(int currentLevel) {
        return switch (currentLevel) {
            case 0, 1 -> 500;
            case 2 -> 1000;
            case 3 -> 2000;
            case 4 -> 4000;
            case 5 -> 8000;
            case 6 -> 13000;
            case 7 -> 19000;
            case 8 -> 26000;
            case 9 -> 34000;
            case 10 -> 48000;
            case 11 -> 65000;
            case 12 -> 85000;
            case 13 -> 110000;
            case 14 -> 136000;
            case 15 -> 166000;
            case 16 -> 200000;
            default -> ((35000 * currentLevel) - 360000);
        };
    }

    public int getMusicNumber(int mapNumber){
        switch (mapNumber){
            case 0: return -1;
            case 1: return 77;
            case 2:
            case 10:
                return 75;
            case 3: return 76;
            case 4: return 76;
            case 5: return 81;
            case 6: return 58;
            case 8:
            case 9:
                return 59;
            case 11:
            {
                if (gp.fighters != null) {
                    if (gp.fighters[11][0] == null) {
                        return -1;
                    } else return 66;
                } else return -1;
            }
            case 12: return 67;

        }
        return -1;
    }
    public int[] getRespawnData(){

        int[] respawn = new int[3];
        if(gp.currentMap < 20){
            // boss room a1
            if(gp.currentMap == 11) {
                gp.player.diedToA1Boss = Math.min(gp.player.diedToA1Boss + 1, 2);
            }
            if(gp.currentMap == 11 && gp.player.diedToA1Boss == 2) {
                respawn[0] = 10;
                respawn[1] = GamePanel.tileSize * 5;
                respawn[2] = GamePanel.tileSize * 8;
                return respawn;
            }

            if(!gp.progress.act1InteractedObjects[0]){
                respawn[0] = 1;
                respawn[1] = GamePanel.tileSize * 47;
                respawn[2] = GamePanel.tileSize * 5;
                return respawn;
            } else {
                respawn[0] = 1;
                respawn[1] = GamePanel.tileSize * 23;
                respawn[2] = GamePanel.tileSize * 25;
                return respawn;
            }
        }/*
        respawn[0] = 0;
        respawn[1] = 0;
        respawn[2] = 0;*/
        return respawn;
    }

    public int currentMapLightingRadius(int map){
        return switch (map) {
            case 1 -> 900;
            case 2 -> 600;
            case 3 -> 800;
            case 4 -> 810;
            default -> 820;
        };
    }


    public int currentMapDarknessMultiplier(int map){
        return switch (map) {
            case 1 -> 12;
            case 2 -> 20;
            case 3 -> 15;
            default -> 11;
        };
    }

    //better to have mana value before spell instance is created because of the casting time
    // 0 fire ball +
    // 1 fire wall +
    // 2 flame cloak +
    // 3 dragon totem +
    // 4 ice bolt +
    // 5 afterimage
    // 6 frozen orb +
    // 7 ice shield
    // 8 lightning
    // 9 nova
    // 10 teleport
    // 11 energy shield
    public int getRequiredManaForSpell(int spellUniqueID, int currentSpellLevel){
        if(currentSpellLevel == 0){
            return 0;
        }
        if(currentSpellLevel > 10){
            currentSpellLevel = 10;
        }

        switch (spellUniqueID){
            case 0: return 10;
            case 1: return 30;
            case 2: return 20;
            case 3: return 52;
            case 4: return 15;
            case 5: return 0;
            case 6: return 61;
            case 7: return 0;
            case 8: return 14;
            case 9: return 19;
            case 10: {
                return 80 - currentSpellLevel * 6;
            }
            case 11: return 1;
        }
        return 0;
    }

    public int levelDifferenceExperience(int playerLevel, int monsterLevel, int monsterExperience){

        if(monsterLevel + 5 >= playerLevel){
            return monsterExperience;
        }


        int levelDifference = Math.min(playerLevel - monsterLevel,10);
        switch (levelDifference) {
            case 6 -> monsterExperience = (int) (monsterExperience * 0.81);
            case 7 -> monsterExperience = (int) (monsterExperience * 0.62);
            case 8 -> monsterExperience = (int) (monsterExperience * 0.43);
            case 9 -> monsterExperience = (int) (monsterExperience * 0.24);
            case 10 -> monsterExperience = (int) (monsterExperience * 0.05);
        }

        return monsterExperience;
    }
}
