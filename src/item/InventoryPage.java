package item;
// TODO THESE ALL HAVE TO BE SAVED IN SAVE FILE

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InventoryPage {

    GamePanel gp;

    //coordinates
    public int startX;
    public int startY;
    public int xDiff;
    public int yDiff;
    public int maxRow;
    public int maxColumn;
    public InventorySlot[][] slots;
    public InventoryPage(GamePanel gp){
        this.gp = gp;
    }

    public void generatePlayerInventory(){
        startX = 547;
        startY = 190;
        xDiff = 52;
        yDiff = 52;
        maxRow = 11;
        maxColumn = 9;
        slots = new InventorySlot[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                slots[i][j] = new InventorySlot();
            }
        }
    }

    public void generatePlayerEquipped() {
        startX = 574;
        startY = 59;
        xDiff = 52;
        yDiff = 52;
        maxRow = 1;
        maxColumn = 8;
        slots = new InventorySlot[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                slots[i][j] = new InventorySlot();
            }
        }
        //TODO fill slots from save file
    }


    public void generatePersonalStorage1() {
        startX = 7;
        startY = 190;
        xDiff = 52;
        yDiff = 52;
        maxRow = 11;
        maxColumn = 9;
        slots = new InventorySlot[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                slots[i][j] = new InventorySlot();
            }
        }
    }

    public void generatePersonalStorage2() {
        startX = 7;
        startY = 190;
        xDiff = 52;
        yDiff = 52;
        maxRow = 11;
        maxColumn = 9;
        slots = new InventorySlot[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                slots[i][j] = new InventorySlot();
            }
        }
    }

    public void generatePersonalStorage3() {
        startX = 7;
        startY = 190;
        xDiff = 52;
        yDiff = 52;
        maxRow = 11;
        maxColumn = 9;
        slots = new InventorySlot[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                slots[i][j] = new InventorySlot();
            }
        }
    }

    public void generatePersonalStorage4() {
        startX = 7;
        startY = 190;
        xDiff = 52;
        yDiff = 52;
        maxRow = 11;
        maxColumn = 9;
        slots = new InventorySlot[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                slots[i][j] = new InventorySlot();
            }
        }
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
}
