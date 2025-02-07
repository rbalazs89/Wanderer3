package object;
import entity.Entity;
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
    public Rectangle solidArea = new Rectangle(0,0, GamePanel.tileSize /2, GamePanel.tileSize /2);
    UtilityTool uTool = new UtilityTool();
    public ArrayList<Glitter> glitters = new ArrayList<>();
    public BufferedImage[] glitterImages = new BufferedImage[8];
    private boolean createdGlitters = false;
    public boolean showGlitter = false; // if entity shouldShowGlitter + player in interactable range
    public boolean shouldShowGlitter = true; // if interactable but i dont want it to be telegraphed for some reason
    public int distanceFromPlayer;
    public int heroXAtGlitterCreation = 0;
    public int heroYAtGlitterCreation = 0;
    public ArrayList<SuperObject> interactThisList = new ArrayList<>();
    public int interactSoundNumber;
    public boolean currentlyInteracting = false;
    public String textToShow;

    public Color transparentBlack = new Color(0,0,0, 125);
    public Color silver = new Color(192,192,192);

    public BasicStroke threeWideStroke = new BasicStroke(3);
    Font titleFont = new Font("Calibri", Font.PLAIN, 16);

    public SuperObject(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize,null);

            if(showGlitter && shouldShowGlitter){
                drawInteractableGlitter(g2);
            }
            drawSpecial(g2);
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

    public int worldMiddleX() {
        return worldX + solidArea.x + solidArea.width / 2;
    }

    public int worldMiddleY() {
        return worldY + solidArea.y + solidArea.height / 2;
    }

    public void getGlitterImages(){
        System.arraycopy(gp.entityImageLoader.glitterImages, 0, glitterImages, 0, 8);
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
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = uTool.scaleImage(image, width, height);
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
                int alpha = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
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
        else return GamePanel.tileSize / 2;
    }

    public int worldMiddleYForDrop(){
        if(image != null){
            return worldY + image.getHeight() / 2;
        }
        else return GamePanel.tileSize / 2;
    }

    public void drawSpecial(Graphics2D g2){

    }

    /*public void drawBookTextScreen(Graphics2D g2) {
        int x = 2 * GamePanel.tileSize;
        int y = GamePanel.tileSize * 3/2;
        int width = gp.screenWidth - ( 4 * GamePanel.tileSize);
        int height = setLinesFromTextAndGetHeight();

        drawSubWindow(x, y, width, height, g2);

        x += GamePanel.tileSize / 2;
        y += GamePanel.tileSize;

        g2.setFont(titleFont); //Font("Calibri", Font.PLAIN, 13);
        for(String line : textToShow.split("/n")){
            g2.drawString(line, x, y);
            y += 30; //is 30 good? probably not
        }
    }

    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2){

        g2.setColor(transparentBlack);
        g2.fillRoundRect(x, y , width, height, 35, 35);

        g2.setColor(Color.white);
        g2.setStroke (threeWideStroke);
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 25, 25);
    }

    public int setLinesFromTextAndGetHeight(){
        textToShow = textToShow; // need to transform it to have "/n"s

        //get height from text values
        int height = 0;
        return height;
    }*/

    public void drawBookTextScreen(Graphics2D g2) {
        int x = 2 * GamePanel.tileSize;
        int y = GamePanel.tileSize * 3 / 2;
        int width = gp.screenWidth - (4 * GamePanel.tileSize);
        int height = setLinesFromTextAndGetHeight(g2, width);

        drawSubWindow(x, y, width, height, g2);

        x += GamePanel.tileSize / 2;
        y += GamePanel.tileSize;

        g2.setFont(titleFont);
        g2.setColor(Color.white);

        for (String line : formattedLines) {
            g2.drawString(line, x, y);
            y += g2.getFontMetrics().getHeight(); // Dynamically adjust line spacing
        }
    }

    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        g2.setColor(transparentBlack);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.white);
        g2.setStroke(threeWideStroke);
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 25, 25);
    }

    private final ArrayList<String> formattedLines = new ArrayList<>();

    public int setLinesFromTextAndGetHeight(Graphics2D g2, int width) {
        formattedLines.clear();

        FontMetrics fm = g2.getFontMetrics(titleFont);
        int lineHeight = fm.getHeight();
        int maxWidth = width - GamePanel.tileSize; // Padding for text

        // Replace "/n" with actual newlines
        String[] paragraphs = textToShow.split("/n");

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                if (fm.stringWidth(currentLine + word) > maxWidth) {
                    formattedLines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    if (!currentLine.isEmpty()) currentLine.append(" ");
                    currentLine.append(word);
                }
            }
            if (!currentLine.isEmpty()) formattedLines.add(currentLine.toString());

            // Add a blank line for paragraph separation
            formattedLines.add("");
        }

        return formattedLines.size() * lineHeight + GamePanel.tileSize; // Add padding
    }

    public int middleDistance(Entity entity){
        return (int) Math.sqrt(Math.pow(worldMiddleX() - entity.worldMiddleX(), 2) + Math.pow(worldMiddleY() - entity.worldMiddleY(), 2));
    }

}
