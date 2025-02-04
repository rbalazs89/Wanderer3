package entity;

import main.GamePanel;
import object.OBJ_DroppedGold;
import object.OBJ_DroppedItem;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    public Font talkingFont = new Font("Calibri", Font.PLAIN, 13);
    public BufferedImage[] walkUp;
    public BufferedImage[] walkRight;
    public BufferedImage[] walkDown;
    public BufferedImage[] walkLeft;
    public BufferedImage[][] walkImages;
    public int maxWalkSpriteArrayLength;
    public boolean sleeping;
    public boolean stunned;
    public int stunStrength;
    //public Graphics2D g2;
    public Random random = new Random(); // movement
    public int worldX, worldY;
    public int speed;
    public int defaultSpeed;

    public boolean singing = false;
    public boolean walking = true;
    public static final int deathTimer = 120;
    public int movementSpritesNumber = 9;
    public int movementSpriteChangeFrame = 7;
    public boolean isDying = false;
    public boolean targetPathFollowed = false;
    public int deathTimeCounter = 0;

    public GamePanel gp;
    public BufferedImage image;

    //TODO reogranize into arrays, reorganize only player ones
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
            attackUpRight1, attackUpRight2, attackDownRight1, attackDownRight2, attackUpLeft1, attackUpLeft2, attackDownLeft1, attackDownLeft2;

    public BufferedImage up1, up2, up3, up4, up5, up6, up7, up8, up9,
            down1, down2, down3, down4, down5, down6, down7, down8, down9,
            left1, left2, left3, left4, left5, left6, left7, left8, left9,
            right1, right2, right3, right4, right5, right6, right7, right8, right9,
            death1, death2, death3, death4, death5, death6, death7, death8, death9, death10,
            castUp1, castUp2, castUp3, castUp4, castUp5, castUp6, castUp7, castUp8, castUp9,
            castRight1, castRight2, castRight3, castRight4, castRight5, castRight6, castRight7, castRight8, castRight9,
            castDown1, castDown2, castDown3, castDown4, castDown5, castDown6, castDown7, castDown8, castDown9,
            castLeft1, castLeft2, castLeft3, castLeft4, castLeft5, castLeft6, castLeft7, castLeft8, castLeft9;


    public String direction;
    public String headDialogueString;
    public int attackDamage = 0;
    public int damageOnContactValue = 0;
    public int spriteCounter = 0;
    public int attackFrameCounter = 0;
    public int attackSpriteNum = 0;
    public int spriteNum = 1;
    public int walkSpriteNum = 0;
    public int idleSpriteNum = 0;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int attackSoundReferenceNumber; //
    public int entityType = 10; // 0 = player, 1 = npc, 2 = monster, 3 = hostile to all, 4 = vulnerable npc, 5 = friendly fighter

    //AI RELATED:
    public int spawnX, spawnY;
    public boolean isGoingBackToSpawn = false;
    public int actionLockCounter = 0;
    public boolean actionWhenNear1 = false;
    public int actionWhenNearCounter = 0;
    public int randomMovementCheckIfCollidedCounter = 0;
    //public String name; //not used for now
    //public boolean collisionTiles = true; // always true
    public boolean collisionEntity = false; // better false for npcs ? collision with other entities
    public String dialogues[] = new String[20];

    public int dialogueIndex = 0;
    public String name; //for debug only

    /// to avoid new instances:
    Color c1 = new Color(0,0,0,120);
    Color c2 = new Color(255,255,255);
    BasicStroke stroke = new BasicStroke(1);
    // utility

    //MONSTERS:
    public int contactGraceCounter = 0;
    //public boolean contactGraced = false;
    public int contactGraceFrame = 30;
    public int life;
    public int maxLife;
    public int resist[] = new int[4];
    public int armor = 0;
    public double spellDmgModifier = 1;
    public int aiBehaviourNumber = 0;
    public boolean frozen = false;
    public int goalCol = 0;
    public int goalRow = 0;

    public Entity(GamePanel gp){

        this.gp = gp;
        direction = "down";
        speed = 1;
        solidArea = new Rectangle(3 * gp.tileSize / 16,
                gp.tileSize * 5/ 16,
                gp.tileSize * 10 / 16,
                gp.tileSize * 11 / 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public Entity() {

    }

    public void speak() {
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    public void setActionAI(){

    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //gp.cChecker.checkEntity(this, gp.npc); // npc no collision anyway
        //gp.cChecker.checkEntity(this, gp.fighters); // check monster with eachother
    }

    public void update() {
        setActionAI();
        checkCollision();
        moveIfNoCollision();
        /*if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }*/
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            switch(direction) {
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
            }
            if (gp.gameState == gp.playState) {
                spriteCounter++;
                if (spriteCounter > 60) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }

            g2.drawImage(image, screenX, screenY,null);
            if(actionWhenNear1){
                nearHeadDialogue(g2);
            }
        }
    }

    public void moveIfNoCollision(){
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
    }

    public void nearHeadDialogue(Graphics2D g2) {
        Font titleFont = new Font("Calibri", Font.PLAIN, 13);
        g2.setFont(titleFont);
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<String> lines = splitTextIntoLines(headDialogueString, fm, 130 - 2 * 10);
        int textHeight = fm.getHeight();
        int boxHeight = textHeight * lines.size() + 2 * 10;
        drawHoverGuide(headDialogueString, screenMiddleX() - 65,screenMiddleY() - boxHeight - 30, 130, g2);
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

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupSheet(BufferedImage image, int x, int y, int width, int height, int scaleWidth, int scaleHeight) {
        UtilityTool uTool = new UtilityTool();
        image = image.getSubimage(x, y, width, height);
        image = uTool.scaleImage(image, scaleWidth, scaleHeight);
        return image;
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

    public BufferedImage setupSheet(BufferedImage image, int x, int y, int width, int height) {
        image = image.getSubimage(x, y, width, height);
        return image;
    }

    public BufferedImage setupImage(String imageName){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
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

    public void loseLife(int value, int type, Entity originEntity, Entity sufferingEntity, boolean isContactDamage, GamePanel gp) {

    }

    public void death(){

    }


    //AI reaction, override locally:
    public void actionToDamageAI(Entity damageReceivedFrom) {
        sleeping = false;
    }


    public int worldMiddleX(){
        return worldX + solidArea.x + solidArea.width / 2;
    }

    public int worldMiddleY(){
        return worldY + solidArea.y + solidArea.height / 2;
    }

    public int screenMiddleX(){
        return worldX - gp.player.worldX + gp.player.screenX + solidArea.x + solidArea.width/2;
    }

    public int screenX(){
        return worldX - gp.player.worldX + gp.player.screenX;
    }

    public int screenY(){
        return worldY - gp.player.worldY + gp.player.screenY;
    }

    public int screenMiddleY(){
        return worldY - gp.player.worldY + gp.player.screenY + solidArea.y + solidArea.height/2;
    }

    public int auraRadius() { // used for hit detection
        int radius = (int) Math.sqrt(Math.pow(solidArea.width / 2, 2) + Math.pow(solidArea.height/2, 2));
        return radius;
    }

    public int middleDistance(Entity entity){
        return (int) Math.sqrt(Math.pow(worldMiddleX() - entity.worldMiddleX(), 2) + Math.pow(worldMiddleY() - entity.worldMiddleY(), 2));
    }

    public Rectangle worldSolidRectangle(){
        Rectangle entityWorldRectangle;
        entityWorldRectangle = new Rectangle();
        entityWorldRectangle.x = worldX + solidArea.x;
        entityWorldRectangle.y = worldY + solidArea.y;
        entityWorldRectangle.width = solidArea.width;
        entityWorldRectangle.height = solidArea.height;
        return entityWorldRectangle;
    }

    public Rectangle screenSolidRectangle(){
        Rectangle entityScreenRectangle = new Rectangle();
        entityScreenRectangle.x = worldX + solidArea.x - gp.player.worldX + gp.player.screenX;
        entityScreenRectangle.y = worldY + solidArea.y - gp.player.worldY + gp.player.screenY;
        entityScreenRectangle.width = solidArea.width;
        entityScreenRectangle.height = solidArea.height;
        return entityScreenRectangle;
    }

    public boolean isHostile(Entity originEntity, Entity attackedEntity){
        if(originEntity.entityType == 0 && (attackedEntity.entityType == 2 || attackedEntity.entityType == 3 || attackedEntity.entityType == 4) ||
                        originEntity.entityType == 2 && (attackedEntity.entityType == 0 || attackedEntity.entityType == 3 || attackedEntity.entityType == 5) ||
                        originEntity.entityType == 3 && (attackedEntity.entityType == 0 || attackedEntity.entityType == 2 || attackedEntity.entityType == 5) ||
                        originEntity.entityType == 5 && (attackedEntity.entityType == 2 || attackedEntity.entityType == 3)){
            return true;
        } else return false;
    }

    public void setActionWhenNear() {
        if(middleDistance(gp.player) < GamePanel.tileSize * 3 / 2){
            actionWhenNear1 = true;
        }

        if(actionWhenNearCounter > 300){
            actionWhenNearCounter = 0;
            actionWhenNear1 = false;
        }

        actionWhenNearCounter++;
    }

    public ArrayList<String> splitTextIntoLines(String text, FontMetrics fm, int maxWidth) {
        ArrayList<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();
        int lineWidth = 0;

        for (char c : text.toCharArray()) {
            if (c == '\n') {
                // Add the current line to lines and reset
                line.append(word);
                lines.add(line.toString());
                line = new StringBuilder();
                word = new StringBuilder();
                lineWidth = 0;
            } else if (Character.isWhitespace(c)) {
                if (lineWidth + fm.stringWidth(word.toString()) > maxWidth) {
                    lines.add(line.toString());
                    line = new StringBuilder();
                    lineWidth = 0;
                }
                line.append(word).append(c);
                lineWidth += fm.stringWidth(word.toString() + c);
                word = new StringBuilder();
            } else {
                word.append(c);
            }
        }

        // Add the last word to the line and the line to lines
        if (lineWidth + fm.stringWidth(word.toString()) > maxWidth) {
            lines.add(line.toString());
            lines.add(word.toString());
        } else {
            line.append(word);
            lines.add(line.toString());
        }

        return lines;
    }

    //ENTITY AI & REACTION:
    public void drawHoverGuide(String text, int x, int y, int width, Graphics2D g2) {
        int padding = 10;
        int fixedWidth = width;

        // Get font metrics for the current font
        FontMetrics fm = g2.getFontMetrics();
        int textHeight = fm.getHeight();

        // Split text into lines
        ArrayList<String> lines = splitTextIntoLines(text, fm, fixedWidth - 2 * padding);
        int boxHeight = textHeight * lines.size() + 2 * padding;

        // Draw the semi-transparent black box
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, fixedWidth, boxHeight, 10, 10);

        // Draw the silver border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, fixedWidth, boxHeight, 10, 10);

        // Draw the text
        g2.setColor(Color.WHITE);
        int textY = y + padding + fm.getAscent();
        for (String line : lines) {
            g2.drawString(line, x + padding, textY);
            textY += textHeight;
        }
    }

    public void drawHoverGuide(ArrayList<String> lines, int x, int y, int width, Graphics2D g2) {
        int padding = 10;
        int fixedWidth = width;

        // Get font metrics for the current font
        FontMetrics fm = g2.getFontMetrics();
        int textHeight = fm.getHeight();

        // Split text into lines
        int boxHeight = textHeight * lines.size() + 2 * padding;

        // Draw the semi-transparent black box
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, fixedWidth, boxHeight, 10, 10);

        // Draw the silver border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, fixedWidth, boxHeight, 10, 10);

        // Draw the text
        g2.setColor(Color.WHITE);
        int textY = y + padding + fm.getAscent();
        for (String line : lines) {
            g2.drawString(line, x + padding, textY);
            textY += textHeight;
        }
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

    public void searchPath(int goalCol, int goalRow, boolean stopAtEnd){

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search() == true) {

            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";

            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
            if (worldX == nextX && worldY == nextY) {
                gp.pFinder.pathList.remove(0);
            }

            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            if (stopAtEnd) {
                if (nextCol == goalCol && nextRow == goalRow) {
                    isGoingBackToSpawn = false;
                    targetPathFollowed = false;
                    speed = defaultSpeed;
                    executeIfPathReached();
                }
            }
        }
    }

    public boolean searchPathBoolean(int goalCol, int goalRow, boolean stopAtEnd){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()) {

            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";

            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
            if (worldX == nextX && worldY == nextY) {
                gp.pFinder.pathList.remove(0);
            }

            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            if (stopAtEnd) {
                if (nextCol == goalCol && nextRow == goalRow) {
                    isGoingBackToSpawn = false;
                    targetPathFollowed = false;
                    speed = defaultSpeed;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void setDirectionFromRandomMovement(){
        actionLockCounter ++;
        randomMovementCheckIfCollidedCounter++;
        if (randomMovementCheckIfCollidedCounter > 10){
            randomMovementCheckIfCollidedCounter = 0;
            checkCollision();
            if (collisionOn){
                actionLockCounter = 200;
            }
        }

        if(actionLockCounter > random.nextInt(40) + 120) {
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (25 < i && i <= 50) {
                direction = "down";
            }
            if (50 < i && i <= 75) {
                direction = "left";
            }
            if (75 < i && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    // dont forget to set up null area separately
    public void setBoundAreaNPC(int x1Tile, int y1Tile, int x2Tile, int y2Tile){
        if (worldX < x1Tile * gp.tileSize || worldY < y1Tile * gp.tileSize || worldX > x2Tile * gp.tileSize || worldY > y2Tile * gp.tileSize){
            targetPathFollowed = true;
        }
    }

    public void setDefaultSpawn(){
        spawnX = worldX;
        spawnY = worldY;
    }

    public void receiveStun(int strength){
        stunned = true;
        stunStrength = strength;
    }

    public void itemDrop(int dropLevel, int minimumTier, boolean isThisABoss){
        OBJ_DroppedItem myItem = new OBJ_DroppedItem(gp);
        myItem.item = gp.itemGenerator.generateItemDrop(dropLevel, minimumTier, isThisABoss);
        myItem.image = myItem.addTransparentBracketForItemDrop(myItem.item.imageInventory);
        int[] tempArray = gp.uTool.findPlaceForDroppedItem2(worldMiddleX(), worldMiddleY());
        myItem.worldX = tempArray[0];
        myItem.worldY = tempArray[1];
        gp.interactObjects.add(myItem);
    }

    public void dropGoldReward(int goldValue){
        OBJ_DroppedGold myItem = new OBJ_DroppedGold(gp);
        myItem.goldValue = goldValue;
        int[] tempArray = gp.uTool.findPlaceForDroppedItem2(worldMiddleX(), worldMiddleY());
        myItem.worldX = tempArray[0];
        myItem.worldY = tempArray[1];
        gp.interactObjects.add(myItem);
    }

    public void setAI(int AINumber){
        aiBehaviourNumber = AINumber;
    }

    public void executeIfPathReached(){

    }
}