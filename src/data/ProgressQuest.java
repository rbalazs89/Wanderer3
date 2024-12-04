package data;

import main.GamePanel;
import object.SuperObject;

public class ProgressQuest {

    /**
     * I need to figure out how to do this class.
     * TBD: set certain entities/drops/when map loads based on some of the numbers below
     */

    //numbers have to match with asset setter for correct save file

    /**
     * Have to match with asset setter:
     *
     * 0: bunnies +
     * 1: fire mage +
     * 2: shop1 +
     * 3: shop2 +
     * 4: near church +
     * 5: puzzle1 pillars +
     * 6: puzzle2 harry potter puzzle +
     * 7 cave lvl1 +
     */
    public boolean[] act1BookPickedUp = new boolean[8];
    public boolean[] act1InteractedObjects = new boolean[4];
    /** 0: water lever
     *  1: wooden sword
     *  2: family boots picked up
     *  3: lever in cave
     */

    GamePanel gp;
    public ProgressQuest(GamePanel gp){
        this.gp = gp;
    }

    public void setProgressInteract(SuperObject object){
        if(gp.currentMap == 1){
            if(gp.obj[1][10] != null){
                if(object == gp.obj[1][10]){
                    act1InteractedObjects[0] = true;
                }
            }
            if(object.item != null){
                if(object.item != null){
                    if (object.item.itemCode == 1){
                        act1InteractedObjects[1] = true;
                        gp.ui.addMessage("Press 'I' to equip the sword you found.");
                    }
                }
            }
        }
        else if(gp.currentMap == 7){
            if(object != null){
                if(object.item != null) {
                    if(object.item.itemCode == 2){
                        gp.ui.addMessage("You found a shiny pair of boots.");
                        gp.npc[9][1].headDialogueString = "Good job clearing the cellar!";
                        act1InteractedObjects[2] = true;
                    }
                }
            }
        }
        else if (gp.currentMap == 2){
            if(gp.obj[2][6] != null && object != null){
                if(object == gp.obj[2][6]){
                    act1InteractedObjects[3] = true;
                }
            }
        }
    }

    // id number sometimes matches asset number, if no asset number available its negative and must be unique
    // taken unique: -3, -4
    public void setTalentBook(int mapNumber, int identificationNumber){
        switch (mapNumber){
            case 1:{
                if (identificationNumber == 2) {
                    act1BookPickedUp[4] = true;
                    break;
                }
                break;
            }
            case 3:{
                if (identificationNumber == -1) {
                    act1BookPickedUp[0] = true;
                    break;
                }
            break;
            }
            case 4:{
                if (identificationNumber == -1) {
                    act1BookPickedUp[1] = true;
                }
                break;
            }
            case 10:
                if(identificationNumber == -1){
                    act1BookPickedUp[5] = true;
                    break;
                } else if(identificationNumber == 2){
                    act1BookPickedUp[6] = true;
                    break;
                }
                break;

            case 8:
                if(identificationNumber == -3){
                    act1BookPickedUp[2] = true;
                    break;
                }
                else if (identificationNumber == -4){
                    act1BookPickedUp[3] = true;
                    break;
                }
                break;
            case 2:
                if (identificationNumber == 8) {
                    act1BookPickedUp[7] = true;
                    break;
                }
                break;
        }
    }
}
