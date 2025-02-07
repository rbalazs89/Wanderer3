package object;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_A1Statue extends SuperObject{
    private BufferedImage[] sheet = new BufferedImage[6];
    private int eyeCounter = 0;
    private int currentImageNumber = 0;
    private boolean eyeopening = false;
    private boolean eyesOpened = false;
    private int counterToStartOpening = 0;

    public OBJ_A1Statue(GamePanel gp) {
        super(gp);
        for (int i = 0; i < 6; i++) {
            sheet[5-i] = setupSheet("/objects/interact/eyesheet",1058 / 6 * i, 0, 1058/6, 100, 7,5);
        }
        image = sheet[5];
    }


    public BufferedImage setupSheet(String imageName, int x, int y, int width, int height, int scaleWidth, int scaleHeight) {
        BufferedImage image = null;
        UtilityTool uTool = new UtilityTool();
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
            image = uTool.scaleImage(image, scaleWidth, scaleHeight);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;

        int deltaY = worldY - gp.player.worldY;
        int deltaX = worldX - gp.player.worldX;
        distanceFromPlayer = (int)Math.sqrt(deltaY * deltaY + deltaX * deltaX);
        if(distanceFromPlayer > 6 * GamePanel.tileSize && distanceFromPlayer < 10 * GamePanel.tileSize){
            counterToStartOpening ++;
            if(counterToStartOpening > 300){
                eyeopening = true;
            }

        } else {
            counterToStartOpening = 0;
            eyeopening = false;
            eyeCounter = 0;
            eyesOpened = false;
            currentImageNumber = 5;
        }

        if(eyeopening){
            getFramesIfPlayerOutside();
        }


        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + 2 * GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - 2 *GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + 2 * GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - 2 * GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            if(eyeopening){
                g2.drawImage(image, screenX + 16, screenY + 20,null);
                g2.drawImage(image, screenX + 28, screenY + 20,null);
            }

        }
    }
    public void getFramesIfPlayerOutside(){
        if(eyeopening && !eyesOpened){
            eyeCounter ++;
            if(eyeCounter > 10){
                currentImageNumber --;
                if(currentImageNumber <= 0 ){
                    //currentImageNumber = 0;
                    eyesOpened = true;
                }
                eyeCounter = 0;
                image = sheet[currentImageNumber];
            }
        }
    }

    public void interact(){
        //TODO
        // plan is that players will be able to touch then stone if fast enough with teleport skill
        // opens another higher level crypt dungeon between cemetery and statue
    }

}
