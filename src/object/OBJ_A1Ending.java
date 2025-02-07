package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_A1Ending extends SuperObject{

    public OBJ_A1Ending(GamePanel gp){
        super(gp);
        this.gp = gp;
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 17;
        solidArea.height = 17;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/nullimage.png"));
            uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pickup() {
        gp.saveLoad.save();
        gp.gameState = gp.gameEndState;
    }
}
