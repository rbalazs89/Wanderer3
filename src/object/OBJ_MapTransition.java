package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_MapTransition extends SuperObject{
    int nextMapNumber;
    int playerPosX;
    int playerPosY;

    public OBJ_MapTransition(GamePanel gp, int nextMapNumber, int playerPosX, int playerPosY) {
        super(gp);
        this.nextMapNumber = nextMapNumber;
        this.playerPosX = playerPosX;
        this.playerPosY = playerPosY;
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 17;
        solidArea.height = 17;
        name = "MapTransition";

        try {
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream("/nullimage.png"));
            uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pickup(){
        gp.currentMap = nextMapNumber;
        gp.mapSwitch();
        gp.player.worldX = playerPosX;
        gp.player.worldY = playerPosY;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        if (gp.visibleHitBox) {
            super.draw(g2, gp);
        }
    }
}
