package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class OBJ_Lever extends SuperObject{

    GamePanel gp;

    public OBJ_Lever(GamePanel gp) {
        super(gp);
        this.gp = gp;
        interactable = true;
        image = setup("/objects/interact/lever_p0");
        getGlitterImages();
    }

    public void interact(){
        interactable = false;
        image = setup("/objects/interact/lever_pulled");
        for (int i = 0; i < interactThisList.size(); i++) {
            interactThisList.get(i).interactByOtherObject();
        }
        interactThisList.clear();
        gp.playSE(32);

        //updated quest progress if needed
        gp.progress.setProgressInteract(this);
    }

    // call this at start at asset setter when user already interacted with it, same as interact, but no sound
    public void progressInteract(){
        interactable = false;
        image = setup("/objects/interact/lever_pulled");
        for (int i = 0; i < interactThisList.size(); i++) {
            interactThisList.get(i).interactByOtherObject();
        }
        interactThisList.clear();
    }
}
