package object;

import main.GamePanel;

public class OBJ_TalentBook extends SuperObject {

    public OBJ_TalentBook(GamePanel gp) {
        super(gp);
        this.gp = gp;
        image = setup("/objects/pickables/talentbook");
    }

    public void pickup() {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] != null){
                if(gp.obj[gp.currentMap][i] == this){
                    gp.obj[gp.currentMap][i] = null;
                    gp.player.pickUpTalentBook();
                    gp.progress.setTalentBook(gp.currentMap, i);
                    return;
                }
            }
        }
    }
}
