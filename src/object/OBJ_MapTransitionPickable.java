package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_MapTransitionPickable extends SuperObject{
    GamePanel gp;
    int nextMapNumber;
    int playerPosX;
    int playerPosY;

    public OBJ_MapTransitionPickable(GamePanel gp, int nextMapNumber, int playerPosX, int playerPosY) {
        super(gp);
        this.gp = gp;
        this.nextMapNumber = nextMapNumber;
        this.playerPosX = playerPosX;
        this.playerPosY = playerPosY;
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 17;
        solidArea.height = 17;
        interactable = true;
        name = "MapTransitionPickable";
        getGlitterImages();
        interactSoundNumber = -1;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/nullimage.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void interact(){
        gp.currentMap = nextMapNumber;
        if(interactSoundNumber != -1){
            gp.playSE(interactSoundNumber);
        }
        gp.mapSwitch();
        gp.player.worldX = playerPosX;
        gp.player.worldY = playerPosY;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        if (gp.visibleHitBox) {
            super.draw(g2, gp);
        } else {
            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
        }
    }
}
