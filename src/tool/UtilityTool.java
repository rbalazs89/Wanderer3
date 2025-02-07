package tool;

import entity.Entity;
import main.GamePanel;
import object.OBJ_DroppedItem;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class    UtilityTool {

    GamePanel gp;

    public UtilityTool(){

    }

    public UtilityTool(GamePanel gp){
        this.gp = gp;
    }

    //create spritesheet from another spritesheet:
    public void createSpriteSheet(int startNumber, int endNumber, String imagePath, String endFileName, int cutDistance){

        BufferedImage originalSheet = null;
        try {
            // noinspection ConstantConditions
            originalSheet = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        int spriteWidth = 128; // Original sprite width
        int spriteHeight = 128; // Original sprite height
        int spritesPerRow = 24; // Number of sprites per row in the original sheet
        int newWidth = 64; // New width for resizing
        int newHeight = 64; // New height for resizing

        BufferedImage newSheet = createSpriteSheetHelperMethod(originalSheet, startNumber, endNumber, spriteWidth, spriteHeight, spritesPerRow, newWidth, newHeight, cutDistance);
        try {
            ImageIO.write(newSheet, "png", new File("C:\\Users\\Balazs\\Desktop\\realgoodassets\\saved\\" + endFileName + ".png"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public  BufferedImage createSpriteSheetHelperMethod(BufferedImage originalSheet, int startNumber, int endNumber, int spriteWidth, int spriteHeight, int spritesPerRow, int newWidth, int newHeight, int cutDistance) {
        // Calculate the number of sprites to be included
        int numSprites = endNumber - startNumber + 1;

        // Calculate the number of rows and columns for the new sprite sheet
        int rows = (numSprites + spritesPerRow - 1) / spritesPerRow; // Round up division

        // Create a new buffered image for the new sprite sheet
        BufferedImage newSheet = new BufferedImage(newWidth * spritesPerRow, newHeight * rows, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newSheet.createGraphics();

        // Loop through each sprite in the range and draw it on the new sprite sheet
        for (int i = 0; i < numSprites; i++) {
            int spriteIndex = startNumber + i;
            int originalRow = spriteIndex / spritesPerRow;
            int originalCol = spriteIndex % spritesPerRow;

            int srcX = originalCol * spriteWidth;
            int srcY = originalRow * spriteHeight;

            int destX = (i % spritesPerRow) * newWidth;
            int destY = (i / spritesPerRow) * newHeight;

            BufferedImage sprite = originalSheet.getSubimage(srcX + cutDistance, srcY + cutDistance, spriteWidth - 2 * cutDistance, spriteHeight - 2 * cutDistance);
            g2d.drawImage(sprite, destX, destY, newWidth, newHeight, null);
        }

        g2d.dispose();
        return newSheet;
    }

    public void saveSheetHelper(String inputFile, String destination){
        int numImages = 9;
        BufferedImage[] images = new BufferedImage[numImages];
        int width = 0;
        int height = 0;

        // Load all images and determine the dimensions of the sprite sheet
        for (int i = 0; i < numImages; i++) {
            String imageName = "/tempImages/" + inputFile + (i + 1) +"";
            images[i] = loadImage(imageName);
            if (images[i] != null) {
                width = images[i].getWidth();
                height = images[i].getHeight();
            }
        }

        // Create a new BufferedImage for the sprite sheet
        BufferedImage spriteSheet = new BufferedImage(width * numImages, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = spriteSheet.createGraphics();

        // Draw each image onto the sprite sheet
        for (int i = 0; i < numImages; i++) {
            if (images[i] != null) {
                g2d.drawImage(images[i], i * width, 0, null);
            }
        }
        g2d.dispose();

        // Save the sprite sheet
        try {
            File outputfile = new File("C:\\Users\\Balazs\\Desktop\\realgoodassets\\saved\\" +destination+ ".png");
            ImageIO.write(spriteSheet, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage loadImage(String imageName) {
        BufferedImage image = null;
        try {
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            //image = ImageIO.read(Player.class.getResourceAsStream(imageName + ".png"));
            if (image == null) {
                System.err.println("Error: Image not found - " + imageName + ".png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0,width, height, null);
        g2.dispose();
        return scaledImage;
    }

    public BufferedImage mergeImages(BufferedImage baseImage, BufferedImage overlayImage) {
        BufferedImage mergedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mergedImage.createGraphics();
        g2d.drawImage(baseImage, 0, 0, null);
        g2d.drawImage(overlayImage, 0, 0, null);
        g2d.dispose();

        return mergedImage;
    }

    public static BufferedImage greenify(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int totalBlackPixels = 0;

        // First pass to count black pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = original.getRGB(x, y);
                Color color = new Color(rgba, true);
                int blackness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                if (blackness < 50) { // Consider a pixel black if its brightness is below a threshold
                    totalBlackPixels++;
                }
            }
        }

        // Calculate the greenifying factor based on the number of black pixels
        double greenifyFactor = (double) totalBlackPixels / (width * height);

        // Second pass to apply the greenifying effect
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = original.getRGB(x, y);
                Color color = new Color(rgba, true);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();

                // Adjust the green component based on the greenifying factor
                int newGreen = (int) Math.min(255, green + greenifyFactor * 255);

                Color newColor = new Color(red, newGreen, blue, color.getAlpha());
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
    }

    public static BufferedImage addBlueTint(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int blueTint = 200; // Adjust the intensity of the blue tint as needed

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = original.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xFF; // Extract alpha value

                // Check if pixel is fully transparent
                if (alpha == 0) {
                    result.setRGB(x, y, rgba); // Copy pixel as is
                    continue; // Skip tinting
                }

                Color color = new Color(rgba);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Increase the blue component to add a blue tint
                blue = Math.min(blue + blueTint, 255);

                Color newColor = new Color(red, green, blue, alpha);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
    }


    public BufferedImage setup1(String imageName, int width, int height) {
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
    public BufferedImage setup2(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupSheetResize(String imageName, int x, int y, int width, int height, int scaleWidth, int scaleHeight) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
            image = uTool.scaleImage(image, scaleWidth, scaleHeight);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupSheet(String imageName, int x, int y, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = image.getSubimage(x, y, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage makeTransparent(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = original.getRGB(x, y);
                Color color = new Color(rgba, true);
                int alpha = color.getAlpha() / 2; // Set alpha to 50% of its original value
                Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
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
                Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), blackness);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
    }

    public int[] findPlaceForDroppedItem2(int x, int y) {
        int[] result = new int[2];
        Rectangle tempRectangle = new Rectangle();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int j = random.nextInt(129) - 64;
            int k = random.nextInt(129) - 64;

            if (gp.cChecker.dropItemTileCollisionCheck(x + j, y + k)) {
                boolean collision = false;
                for (int l = 0; l < gp.interactObjects.size(); l++) {
                    tempRectangle.x = gp.interactObjects.get(l).solidArea.x + gp.interactObjects.get(l).worldX;
                    tempRectangle.y = gp.interactObjects.get(l).solidArea.y + gp.interactObjects.get(l).worldY;
                    tempRectangle.width = gp.interactObjects.get(l).solidArea.width;
                    tempRectangle.height = gp.interactObjects.get(l).solidArea.height;

                    if (tempRectangle.intersects(new Rectangle(x + j + 16, y + k + 16, 32, 32))) {
                        collision = true;
                        break;
                    }
                }
                if (!collision) {
                    result[0] = x + j;
                    result[1] = y + k;
                    return result;
                }
            }

            j = random.nextInt(250) - 125;
            k = random.nextInt(250) - 125;

            if (gp.cChecker.dropItemTileCollisionCheck(x + j, y + k)) {
                boolean collision = false;
                for (int l = 0; l < gp.interactObjects.size(); l++) {
                    tempRectangle.x = gp.interactObjects.get(l).solidArea.x + gp.interactObjects.get(l).worldX;
                    tempRectangle.y = gp.interactObjects.get(l).solidArea.y + gp.interactObjects.get(l).worldY;
                    tempRectangle.width = gp.interactObjects.get(l).solidArea.width;
                    tempRectangle.height = gp.interactObjects.get(l).solidArea.height;

                    if (tempRectangle.intersects(new Rectangle(x + j + 16, y + k + 16, 32, 32))) {
                        collision = true;
                        break;
                    }
                }
                if (!collision) {
                    result[0] = x + j;
                    result[1] = y + k;
                    return result;
                }
            }
        }

        // If finding a suitable random position fails, generate somewhere near the player
        int j = random.nextInt(32) - 16;
        int k = random.nextInt(32) - 16;
        result[0] = j + x;
        result[1] = k + y;
        return result;
    }


    public void itemDropFromEnemy(Entity entity, OBJ_DroppedItem item){
        int[] tempArray = findPlaceForDroppedItem2(entity.worldMiddleX(), entity.worldMiddleY());
        item.worldX = tempArray[0];
        item.worldY = tempArray[1];
        gp.interactObjects.add(item);
    }

    public void itemDropFromChest(SuperObject obj, OBJ_DroppedItem item){
        int[] tempArray = findPlaceForDroppedItem2(obj.worldMiddleXForDrop(), obj.worldMiddleYForDrop());
        item.worldX = tempArray[0];
        item.worldY = tempArray[1];
        gp.interactObjects.add(item);
    }
}
