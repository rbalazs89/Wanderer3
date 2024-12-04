package object;
import item.Item;

import main.GamePanel;
import tool.Glitter;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class SuperObject {

    public GamePanel gp;
    public Item item;
    public boolean interactable = false;
    public BufferedImage image;
    public String name = "ok";
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,gp.tileSize/2, gp.tileSize/2);
    UtilityTool uTool = new UtilityTool();
    public ArrayList<Glitter> glitters = new ArrayList<>();
    public BufferedImage glitterImages[] = new BufferedImage[8];
    private boolean createdGlitters = false;
    public boolean showGlitter = false; // if entity shouldShowGlitter + player in interactable range
    public boolean shouldShowGlitter = true;
    public int distanceFromPlayer;
    public int heroXAtGlitterCreation = 0;
    public int heroYAtGlitterCreation = 0;
    public ArrayList<SuperObject> interactThisList = new ArrayList<>();
    public int interactSoundNumber;

    public SuperObject(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX +  gp.tileSize  > gp.player.worldX - gp.player.screenX &&
                worldX -  gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY +  gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY -  gp.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);

            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
        }
    }

    public void drawInteractableGlitter(Graphics2D g2){
        int screenX = screenMiddleX();
        int screenY = screenMiddleY();

        if(gp.cChecker.interactableCameInRange){
            if(!createdGlitters){
                heroXAtGlitterCreation = gp.player.worldX;
                heroYAtGlitterCreation = gp.player.worldY;
                for (int i = 0; i < 6; i++) {
                    glitters.add(new Glitter(screenX,screenY));
                }
                createdGlitters = true;
            }

            for (int i = 0; i < glitters.size(); i++) {
                int magicNumber = 7;
                glitters.get(i).glitterCounter++;
                if(glitters.get(i).glitterCounter % 3 == 0){
                    glitters.get(i).y--;
                }
                int pictureNumber = (glitters.get(i).glitterCounter / magicNumber );
                g2.drawImage(glitterImages[pictureNumber],
                        glitters.get(i).x - gp.player.worldX + heroXAtGlitterCreation,
                        glitters.get(i).y - gp.player.worldY + heroYAtGlitterCreation,
                        18,18,null);
                if (glitters.get(i).glitterCounter == 7 * magicNumber - 1){
                    glitters.get(i).hasReachedTurning = true;
                }
                if(glitters.get(i).hasReachedTurning){
                    glitters.get(i).glitterCounter = glitters.get(i).glitterCounter - 2;
                }
                if (glitters.get(i).glitterCounter < 0){
                    glitters.get(i).hasReachedTurning = false;
                    glitters.get(i).glitterCounter = (int) (Math.random() * 47);
                    glitters.get(i).x = glitters.get(i).startX - 20 + (int) (Math.random() * 40);
                    glitters.get(i).y = glitters.get(i).startY - 20 + (int) (Math.random() * 40);
                }
            }
        }
    }

    public int screenMiddleX(){
        return worldX - gp.player.worldX + gp.player.screenX + solidArea.x + solidArea.width/2;
    }

    public int screenMiddleY(){
        return worldY - gp.player.worldY + gp.player.screenY + solidArea.y + solidArea.height/2;
    }

    public int middleX() {
        return worldX + solidArea.x + solidArea.width / 2;
    }

    public int middleY() {
        return worldY + solidArea.y + solidArea.height / 2;
    }

    public void getGlitterImages(){
        for (int i = 0; i < 8 ; i++) {
            glitterImages[i] = gp.entityImageLoader.glitterImages[i];
        }
    }

    public void itemDrop(int dropLevel, int minimumTier, boolean isThisABoss){
        OBJ_DroppedItem myItem = new OBJ_DroppedItem(gp);
        myItem.item = gp.itemGenerator.generateItemDrop(dropLevel, minimumTier, isThisABoss);
        myItem.image = myItem.addTransparentBracketForItemDrop(myItem.item.imageInventory);
        int[] tempArray = gp.uTool.findPlaceForDroppedItem2(worldX + 32, worldY + 32);
        myItem.worldX = tempArray[0];
        myItem.worldY = tempArray[1];
        gp.interactObjects.add(myItem);
    }

    public BufferedImage addTransparentBracketForItemDrop(BufferedImage image) {

        if (image.getWidth() != 48 || image.getHeight() != 48) {
            throw new IllegalArgumentException("Input image must be 48x48 in size.");
        }

        Image resizedImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        BufferedImage resizedBufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dResized = resizedBufferedImage.createGraphics();
        g2dResized.drawImage(resizedImage, 0, 0, null);
        g2dResized.dispose();

        BufferedImage resultImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dResult = resultImage.createGraphics();

        int x = (64 - 32) / 2;
        int y = (64 - 32) / 2;
        g2dResult.drawImage(resizedBufferedImage, x, y, null);
        g2dResult.dispose();

        return resultImage;
    }

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage adjustTransparency(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = original.getRGB(x, y);
                Color color = new Color(rgba, true);
                int blackness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                int alpha = blackness;
                Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
    }

    //this method is for events and objects that can be picked up without interaction key
    public void pickup(){

    }

    public void interactByOtherObject(){

    }

    public void update(){

    }

    public void interact(){

    }

    public void progressInteract() {

    }
    public int worldMiddleXForDrop(){
        if(image != null){
            return worldX + image.getWidth() / 2;
        }
        else return 32;
    }

    public int worldMiddleYForDrop(){
        if(image != null){
            return worldY + image.getHeight() / 2;
        }
        else return 32;
    }

}
