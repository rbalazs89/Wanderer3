package item;

import entity.Player;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MomoJuice {
    public static final int flaskPictureSize = 40;

    public BufferedImage emptyFlask = setupSheet ("/items/flask/flask", 0,0, 128, 128, flaskPictureSize, flaskPictureSize);
    public BufferedImage fullFlask = setupSheet("/items/flask/flask", 128, 0, 128, 128, flaskPictureSize, flaskPictureSize);
    public BufferedImage finalImage = new BufferedImage(flaskPictureSize, flaskPictureSize, BufferedImage.TYPE_INT_ARGB);

    public int charge;
    public int previousCharge;
    public int baseMaxCharge = 5;
    public int maxCharge;
    public int healthValuePerCharge = 40;
    public boolean available = true;
    public int momoCounter = 0;
    Player player;
    public MomoJuice(Player player){
        this.player = player;
        maxCharge = baseMaxCharge;
        charge = maxCharge;
        previousCharge = 0;
        updateImage();
    }

    public void updateImage(){
        if (previousCharge != charge){
            makeImage();
        }
        previousCharge = charge;
    }

    public void makeImage(){
        Graphics2D g2d = finalImage.createGraphics();
        int filledHeight = Math.max(1, (int)((double)charge / maxCharge * flaskPictureSize));
        int emptyHeight = flaskPictureSize - filledHeight;

        if (emptyHeight == 0) {
            emptyHeight = 1;
            filledHeight = flaskPictureSize - emptyHeight;
        }

        g2d.drawImage(emptyFlask.getSubimage(0, 0, flaskPictureSize, emptyHeight), 0, 0, null);
        g2d.drawImage(fullFlask.getSubimage(0, emptyHeight, flaskPictureSize, filledHeight), 0, emptyHeight, null);
        g2d.dispose();
    }

    public void drinkCharge(){
        if(available && charge > 0 && player.life != player.maxLife) {
            player.winLife(healthValuePerCharge);
            charge--;
            available = false;
            player.gp.playSE(47);
        } else if(available && charge == 0){
            player.gp.ui.addMessage("Out of pumpkin juice charges.");
        } else if(charge > 0 && !available){
            player.gp.ui.addMessage("Can't drink yet.");
        } else if(available && charge > 0){
            player.gp.ui.addMessage("You have max health.");
        }
    }

    public void refreshMomo(){
        if(!available) {
            momoCounter++;
            if (momoCounter >= 120) {
                available = true;
                momoCounter = 0;
            }
        }
    }

    public void refill(){
        player.gp.playSE(48);
        player.gp.ui.addMessage("Refilled pumpkin bottle");
        charge = maxCharge;
    }

    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupSheet(String imageName, int x, int y, int width, int height, int scaleWidth, int scaleHeight) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
            image = uTool.scaleImage(image, scaleWidth, scaleHeight);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
