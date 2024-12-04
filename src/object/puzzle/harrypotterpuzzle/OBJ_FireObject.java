package object.puzzle.harrypotterpuzzle;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_FireObject extends SuperObject {

    public int imageCounter = 0;
    public int currentImage = 0;

    BufferedImage image[] = new BufferedImage[11];

    public OBJ_FireObject(GamePanel gp){
        super(gp);
        getImage();
        interactable = false;
        collision = true;
        solidArea.x = 1;
        solidArea.y = 1;
        solidArea.height = 62;
        solidArea.width = 62;

    }

    public void getImage(){
        for (int i = 0; i < 11; i++) {
         image[i] = setupSheet2("/puzzle/harrypotter/fireobject", i * 64, 0, 64, 128);
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX +  gp.tileSize  > gp.player.worldX - gp.player.screenX &&
                worldX -  gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY +  gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY -  gp.tileSize < gp.player.worldY + gp.player.screenY){

            imageCounter++;
            if(imageCounter == 8){
                currentImage ++;
                imageCounter = 0;
            }
            if (currentImage == 11){
                currentImage = 0;
            }
            //screenY = screenY - gp.tileSize;
            g2.drawImage(image[currentImage], screenX, screenY, gp.tileSize, gp.tileSize,null);
        }
    }



    public BufferedImage setupSheet2(String imageName, int x, int y, int width, int height) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
