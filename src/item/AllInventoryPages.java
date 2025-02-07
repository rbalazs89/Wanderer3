package item;

import main.GamePanel;
import object.OBJ_DroppedItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AllInventoryPages {
    GamePanel gp;
    public InventoryPage playerInventoryPage;
    public InventoryPage playerEquipmentPage;
    public InventoryPage[] storagePages = new InventoryPage[4];
    boolean hasItemGrabbed;
    Item grabbedItem;
    Item itemToBuy;
    int itemToBuyRow = 0;
    int itemToBuyCol = 0;
    public InventoryPage currentShopInventory;
    Graphics2D g2;
    public boolean drawConfirmationWindow = false;
    int currentConfirmWindowX = 0;
    int currentConfirmWindowY = 0;

    public AllInventoryPages(GamePanel gp){
        this.gp = gp;

        playerInventoryPage = new InventoryPage(gp);
        playerInventoryPage.generatePlayerInventory();

        playerEquipmentPage = new InventoryPage(gp);
        playerEquipmentPage.generatePlayerEquipped();

        for (int i = 0; i < storagePages.length; i++) {
            storagePages[i] = new InventoryPage(gp);
        }
        storagePages[0].generatePersonalStorage1();
        storagePages[1].generatePersonalStorage2();
        storagePages[2].generatePersonalStorage3();
        storagePages[3].generatePersonalStorage4();

        currentShopInventory = new InventoryPage(gp);
    }

    public void draw (Graphics2D g2) {
        this.g2 = g2;
        if(gp.mouseH.mouseClickSentInventory){
            gp.mouseH.mouseClickSentInventory = false;
            moveItem();
        }
        drawInventory(g2);
        drawEquipped(g2);

        if(gp.inventorySubState == gp.inventoryStorageState1){
            drawStashStorage1(g2);
        }
        else if(gp.inventorySubState == gp.inventoryStorageState2){
            drawStashStorage2(g2);
        }
        else if(gp.inventorySubState == gp.inventoryStorageState3){
            drawStashStorage3(g2);
        }
        else if(gp.inventorySubState == gp.inventoryStorageState4){
            drawStashStorage4(g2);
        }
        else if(gp.inventorySubState == gp.inventoryShopState){
            drawShop();
        }


        if(drawConfirmationWindow){
            buyConfirmationWindow();
        }
        if(hasItemGrabbed){
            g2.drawImage(grabbedItem.imageInventory, gp.mouseH.mouseX - 25, gp.mouseH.mouseY - 25, null);
        }
        else if(!drawConfirmationWindow){
            drawHover();
        }
    }
    public void drawHover(){
        InventoryPage tempInventoryPage;
        tempInventoryPage = checkValidAreaHover();
        if(tempInventoryPage != null) {
            int col = (gp.mouseH.mouseX - tempInventoryPage.startX) / tempInventoryPage.xDiff;
            int row = (gp.mouseH.mouseY - tempInventoryPage.startY) / tempInventoryPage.yDiff;
            if(tempInventoryPage.slots[row][col].placedItem != null){

                g2.setFont(new Font("Arial", Font.PLAIN, 12));
                drawHoverGuide(
                        tempInventoryPage.slots[row][col].placedItem.hoverDescription,
                        Math.min(tempInventoryPage.startX + col * 52, 1024 - 200),
                        Math.max(0, tempInventoryPage.startY + row * 52 - 100),
                        180);
            }
        }
    }

    public void drawStashStorage1(Graphics2D g2){
        int x = storagePages[0].startX;
        int y = storagePages[0].startY;
        for (int i = 0; i < storagePages[0].maxRow; i++) {
            for (int j = 0; j < storagePages[0].maxColumn; j++) {
                g2.drawImage(storagePages[0].slots[i][j].image, x,y, null);
                if(storagePages[0].slots[i][j].placedItem != null) {
                    g2.drawImage(storagePages[0].slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += storagePages[0].xDiff;
            }
            x = storagePages[0].startX;
            y += storagePages[0].yDiff;
        }
    }

    public void drawStashStorage2(Graphics2D g2){
        int x = storagePages[1].startX;
        int y = storagePages[1].startY;
        for (int i = 0; i < storagePages[1].maxRow; i++) {
            for (int j = 0; j < storagePages[1].maxColumn; j++) {
                g2.drawImage(storagePages[1].slots[i][j].image, x,y, null);
                if(storagePages[1].slots[i][j].placedItem != null) {
                    g2.drawImage(storagePages[1].slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += storagePages[1].xDiff;
            }
            x = storagePages[1].startX;
            y += storagePages[1].yDiff;
        }
    }

    public void drawStashStorage3(Graphics2D g2){
        int x = storagePages[2].startX;
        int y = storagePages[2].startY;
        for (int i = 0; i < storagePages[2].maxRow; i++) {
            for (int j = 0; j < storagePages[2].maxColumn; j++) {
                g2.drawImage(storagePages[2].slots[i][j].image, x,y, null);
                if(storagePages[2].slots[i][j].placedItem != null) {
                    g2.drawImage(storagePages[2].slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += storagePages[2].xDiff;
            }
            x = storagePages[2].startX;
            y += storagePages[2].yDiff;
        }
    }

    public void drawStashStorage4(Graphics2D g2){
        int x = storagePages[3].startX;
        int y = storagePages[3].startY;
        for (int i = 0; i < storagePages[3].maxRow; i++) {
            for (int j = 0; j < storagePages[3].maxColumn; j++) {
                g2.drawImage(storagePages[3].slots[i][j].image, x,y, null);
                if(storagePages[3].slots[i][j].placedItem != null) {
                    g2.drawImage(storagePages[3].slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += storagePages[3].xDiff;
            }
            x = storagePages[3].startX;
            y += storagePages[3].yDiff;
        }
    }

    public void drawInventory(Graphics2D g2){
        int x = playerInventoryPage.startX;
        int y = playerInventoryPage.startY;
        for (int i = 0; i < playerInventoryPage.maxRow; i++) {
            for (int j = 0; j < playerInventoryPage.maxColumn; j++) {
                g2.drawImage(playerInventoryPage.slots[i][j].image, x,y, null);
                if(playerInventoryPage.slots[i][j].placedItem != null) {
                    g2.drawImage(playerInventoryPage.slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += playerInventoryPage.xDiff;
            }
            x = playerInventoryPage.startX;
            y += playerInventoryPage.yDiff;
        }
    }

    public void drawShop(){
        int x = currentShopInventory.startX;
        int y = currentShopInventory.startY;
        for (int i = 0; i < currentShopInventory.maxRow; i++) {
            for (int j = 0; j < currentShopInventory.maxColumn; j++) {
                g2.drawImage(currentShopInventory.slots[i][j].image, x,y, null);
                if(currentShopInventory.slots[i][j].placedItem != null) {
                    g2.drawImage(currentShopInventory.slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += currentShopInventory.xDiff;
            }
            x = currentShopInventory.startX;
            y += currentShopInventory.yDiff;
        }
    }

    public void drawEquipped(Graphics2D g2){
        int x = playerEquipmentPage.startX;
        int y = playerEquipmentPage.startY;
        for (int i = 0; i < playerEquipmentPage.maxRow; i++) {
            for (int j = 0; j < playerEquipmentPage.maxColumn; j++) {
                //g2.drawImage(playerEquipmentPage.slots[i][j].image, x,y, null);
                if(playerEquipmentPage.slots[i][j].placedItem != null) {
                    g2.drawImage(playerEquipmentPage.slots[i][j].placedItem.imageInventory, x, y, null);
                }
                x += playerEquipmentPage.xDiff;
            }
            x = playerEquipmentPage.startX;
            y += playerEquipmentPage.yDiff;
        }
    }

    public void moveItem() {
        if(drawConfirmationWindow) {
            if(gp.mouseH.mouseAreaAbs(currentConfirmWindowX + 10,
                    currentConfirmWindowX + 80, currentConfirmWindowY + 80, currentConfirmWindowY + 100)){
                if(addItemToInventoryFromShop(itemToBuy)){ // check if enuf gold + room
                    if(checkAndHandleSpecialItem(itemToBuy)){ // check if item is special and handles it if it is

                        // buy item if enuf room + gold + not special:
                        outerloop:
                        for (int i = 0; i < playerInventoryPage.maxRow; i++) {
                            for (int j = 0; j < playerInventoryPage.maxColumn; j++) {
                                if (playerInventoryPage.slots[i][j].placedItem == null) {
                                    playerInventoryPage.slots[i][j].placedItem = itemToBuy;
                                    gp.playSE(21);
                                    break outerloop;
                                }
                            }
                        }
                        closeConfirmationAfterSuccessFullTransaction();
                        return;
                    }
                }
            } else if(gp.mouseH.mouseAreaAbs(currentConfirmWindowX + 110,
                    currentConfirmWindowX + 180, currentConfirmWindowY + 80, currentConfirmWindowY + 100)){
                itemToBuy = null;
                drawConfirmationWindow = false;
                return;
            }
        }

        InventoryPage tempInventoryPage;
        if(checkIfValidClickFound() != null){
            tempInventoryPage = checkIfValidClickFound();
        } else return;

        int col = (gp.mouseH.mouseX - tempInventoryPage.startX) / tempInventoryPage.xDiff;
        int row = (gp.mouseH.mouseY - tempInventoryPage.startY) / tempInventoryPage.yDiff;


        if (!drawConfirmationWindow){
            // Grab item if nothing is grabbed
            // Buy an item
            if (!hasItemGrabbed) {
                if(tempInventoryPage == currentShopInventory){
                    if(tempInventoryPage.slots[row][col].placedItem != null){
                        drawConfirmationWindow = true;
                        itemToBuyCol = col;
                        itemToBuyRow = row;
                        currentConfirmWindowX = tempInventoryPage.startX + col * 52;
                        currentConfirmWindowY = tempInventoryPage.startY + row * 52 - 100;
                        itemToBuy = tempInventoryPage.slots[row][col].placedItem;
                    }
                    return;
                }
                else if (tempInventoryPage.slots[row][col].placedItem != null) {
                    grabbedItem = tempInventoryPage.slots[row][col].placedItem;
                    hasItemGrabbed = true;
                    tempInventoryPage.slots[row][col].placedItem = null;
                    gp.playSE(79);
                    return;
                }
            }

            // Place item in empty slot if an item is grabbed
            // sell item
            if(tempInventoryPage.slots[row][col].placedItem == null && !tempInventoryPage.equals(playerEquipmentPage) && hasItemGrabbed) {
                if(tempInventoryPage != currentShopInventory) {
                    tempInventoryPage.slots[row][col].placedItem = grabbedItem;
                    grabbedItem = null;
                    hasItemGrabbed = false;
                    gp.playSE(78);
                    return;
                } else if (gp.inventorySubState == gp.inventoryShopState) {
                    if(sellItemToShop(grabbedItem)){
                        return;
                    }
                }

            }
            //place item in empty EQUIPMENT slot
            if(tempInventoryPage.slots[row][col].placedItem == null && tempInventoryPage.equals(playerEquipmentPage) && hasItemGrabbed) {
                if((col == 0 && grabbedItem.type.equals("sword"))
                || (col == 1 && grabbedItem.type.equals("shield"))
                || (col == 2 && grabbedItem.type.equals("amulet"))
                || (col == 3 && grabbedItem.type.equals("armor"))
                || (col == 4 && grabbedItem.type.equals("helm"))
                || (col == 5 && grabbedItem.type.equals("gloves"))
                || (col == 6 && grabbedItem.type.equals("belt"))
                || (col == 7 && grabbedItem.type.equals("boots"))){
                    tempInventoryPage.slots[row][col].placedItem = grabbedItem;
                    grabbedItem = null;
                    hasItemGrabbed = false;
                    gp.playSE(78);
                    return;
                } else {
                    gp.playSE(23);
                }
            }

            // Switch places with items if one is already grabbed
            if (tempInventoryPage != currentShopInventory && tempInventoryPage.slots[row][col].placedItem != null && !tempInventoryPage.equals(playerEquipmentPage) && hasItemGrabbed) {
                Item temporaryItem = tempInventoryPage.slots[row][col].placedItem;
                tempInventoryPage.slots[row][col].placedItem = grabbedItem;
                grabbedItem = temporaryItem;
                gp.playSE(79);
                return;
            }
            if(tempInventoryPage.slots[row][col].placedItem != null && tempInventoryPage.equals(playerEquipmentPage) && hasItemGrabbed) {
                if((col == 0 && grabbedItem.type.equals("sword"))
                        || (col == 1 && grabbedItem.type.equals("shield"))
                        || (col == 2 && grabbedItem.type.equals("amulet"))
                        || (col == 3 && grabbedItem.type.equals("armor"))
                        || (col == 4 && grabbedItem.type.equals("helm"))
                        || (col == 5 && grabbedItem.type.equals("gloves"))
                        || (col == 6 && grabbedItem.type.equals("belt"))
                        || (col == 7 && grabbedItem.type.equals("boots"))){
                    Item temporaryItem = tempInventoryPage.slots[row][col].placedItem;
                    tempInventoryPage.slots[row][col].placedItem = grabbedItem;
                    grabbedItem = temporaryItem;
                    gp.playSE(78);
                    return;
                } else {
                    gp.playSE(23);
                }
            }
        }
    }

    public InventoryPage checkIfValidClickFound(){
        if(gp.inventorySubState == gp.inventoryOnlyState){
            // drop grabbed item:
            if(gp.mouseH.mouseAreaAbs(0, 536,0,765) && hasItemGrabbed){
                hasItemGrabbed = false;
                OBJ_DroppedItem myItem = new OBJ_DroppedItem(gp);
                myItem.item = grabbedItem;
                myItem.image = myItem.addTransparentBracketForItemDrop(myItem.item.imageInventory);
                int[] tempArray = findPlaceForDroppedItem2(gp.player.worldMiddleX(), gp.player.worldMiddleY());
                myItem.worldX = tempArray[0];
                myItem.worldY = tempArray[1];
                gp.interactObjects.add(myItem);
                grabbedItem = null;
            }
        }
        if(gp.mouseH.mouseAreaAbs(547,1015,190,762)){
            return playerInventoryPage;
        } else if (gp.mouseH.mouseAreaAbs(547,990,59,111)){
            return playerEquipmentPage;
        }
        if(gp.mouseH.mouseAreaAbs(7,474,190, 762)){
            if(gp.inventorySubState == gp.inventoryStorageState1){
                return storagePages[0];
            } if(gp.inventorySubState == gp.inventoryStorageState2){
                return storagePages[1];
            } if(gp.inventorySubState == gp.inventoryStorageState3){
                return storagePages[2];
            } if(gp.inventorySubState == gp.inventoryStorageState4){
                return storagePages[3];
            } if(gp.inventorySubState == gp.inventoryShopState){
                return currentShopInventory;
            }
        }

        return null;
    }

    public InventoryPage checkValidAreaHover() {
        if(gp.mouseH.mouseAreaAbs(547,1015,190,762)){
            return playerInventoryPage;
        } else if (gp.mouseH.mouseAreaAbs(547,990,59,111)){
            return playerEquipmentPage;
        }
        if(gp.mouseH.mouseAreaAbs(7,474,190, 762)){
            if(gp.inventorySubState == gp.inventoryStorageState1){
                return storagePages[0];
            } if(gp.inventorySubState == gp.inventoryStorageState2){
                return storagePages[1];
            } if(gp.inventorySubState == gp.inventoryStorageState3){
                return storagePages[2];
            } if(gp.inventorySubState == gp.inventoryStorageState4){
                return storagePages[3];
            } if(gp.inventorySubState == gp.inventoryShopState){
                return currentShopInventory;
            }
        }
        return null;
    }
    public boolean addItemToInventoryFromShop(Item item){
        if(gp.player.gold < item.buyGoldCost){
            gp.ui.addMessage("Not enough gold");
            gp.playSE(23);
            return false;
        }

        for (int i = 0; i < playerInventoryPage.maxRow; i++) {
            for (int j = 0; j < playerInventoryPage.maxColumn; j++) {
                if(playerInventoryPage.slots[i][j].placedItem == null){
                    return true;
                }
            }
        }
        gp.playSE(23);
        gp.ui.addMessage("Inventory full");
        return false;
    }

    public boolean checkAndHandleSpecialItem(Item item){
        if(item.itemCode == 3){
            gp.player.pickUpTalentBook();
            gp.progress.setTalentBook(gp.currentMap, item.itemCode * -1);
            closeConfirmationAfterSuccessFullTransaction();
            return false;

        } else if (item.itemCode == 4){
            gp.player.pickUpTalentBook();
            gp.progress.setTalentBook(gp.currentMap, item.itemCode * -1);
            closeConfirmationAfterSuccessFullTransaction();
            return false;
        }
        else return true;
    }

    public void closeConfirmationAfterSuccessFullTransaction(){
        gp.player.gold -= itemToBuy.buyGoldCost;
        itemToBuy = null;
        currentShopInventory.slots[itemToBuyRow][itemToBuyCol].placedItem = null;
        drawConfirmationWindow = false;
    }

    public void buyConfirmationWindow() {
        int x = currentConfirmWindowX;
        int y = currentConfirmWindowY;
        int width = 200;
        int height = 110;
        Color c = new Color(0,0,0, 170);
        g2.setColor(c);
        g2.fillRoundRect(x, y , width, height, 35, 35);

        c = new Color(192,192,192);
        g2.setColor(c);
        g2.setStroke (new BasicStroke(3));
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 25, 25);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke (new BasicStroke(1));
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 25, 25);

        // Set font
        Font font = new Font("Arial", Font.PLAIN, 14);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics(font);

        // Calculate positions
        int padding = 10;
        int lineHeight = fm.getHeight();
        int textX = x + padding;
        int textY = y + padding + lineHeight;

        // Draw strings
        g2.drawString("Buy ", textX, textY);
        textY += lineHeight + 5; // Add some extra space between lines

        g2.drawString(itemToBuy.name, textX, textY);
        textY += lineHeight + 5;

        g2.drawString("Gold: " + itemToBuy.buyGoldCost, textX, textY);
        textY += lineHeight + 8;

        g2.drawString("Yes", textX + 22, textY);
        g2.drawString("No", textX + 100 + 26, textY); // Place "No" a bit to the right

        int buttonWidth = 70;
        int buttonHeight = 20;
        int buttonY = y + height - padding - buttonHeight;
        g2.drawRoundRect(textX, buttonY, buttonWidth, buttonHeight, 10, 10);
        g2.drawRoundRect(textX + 100, buttonY, buttonWidth, buttonHeight, 10, 10);
    }

    public boolean addItemToInventoryFromGround(Item item){
        for (int i = 0; i < playerInventoryPage.maxRow; i++) {
            for (int j = 0; j < playerInventoryPage.maxColumn; j++) {
                if(playerInventoryPage.slots[i][j].placedItem == null){
                    playerInventoryPage.slots[i][j].placedItem = item;
                    return true;
                }
            }
        }
        gp.ui.addMessage("Inventory full");
        return false;
    }

    public boolean sellItemToShop(Item item){
        for (int i = 0; i < currentShopInventory.maxRow; i++) {
            for (int j = 0; j < currentShopInventory.maxColumn; j++) {
                if(currentShopInventory.slots[i][j].placedItem == null){
                    gp.player.gold += item.saleGoldCost;
                    currentShopInventory.slots[i][j].placedItem = item;
                    hasItemGrabbed = false;
                    grabbedItem = null;
                    gp.playSE(22);
                    return true;
                }
            }
        }
        gp.playSE(23);
        gp.ui.addMessage("Shop Inventory full.");
        gp.ui.addMessage("Come back later.");
        return false;
    }

    public void handleClosingInventory(){
        gp.player.refreshPlayerStatsWithItems();
        drawConfirmationWindow = false;

        //place back item in inventory
        if(hasItemGrabbed){
            for (int i = 0; i < playerInventoryPage.maxRow; i++) {
                for (int j = 0; j < playerInventoryPage.maxColumn; j++) {
                    if(playerInventoryPage.slots[i][j].placedItem == null){
                        playerInventoryPage.slots[i][j].placedItem = grabbedItem;
                        grabbedItem = null;
                        hasItemGrabbed = false;
                        return;
                    }
                }
            }

            //if inventory is somehow full, place it in stash
            for (int k = 0; k < 4; k++) {
                for (int i = 0; i < storagePages[k].maxRow; i++) {
                    for (int j = 0; j < storagePages[k].maxColumn; j++) {
                        if(storagePages[k].slots[i][j].placedItem == null){
                            storagePages[k].slots[i][j].placedItem = grabbedItem;
                            grabbedItem = null;
                            hasItemGrabbed = false;
                            gp.ui.addMessage("The item that you were holding was put in your personal storage chest");
                            return;
                        }
                    }
                }
            }
            //this rarely supposed to happen, as you cannot hold item anyway, if both stash + inv are full, only from equipped slots
            hasItemGrabbed = false;
            OBJ_DroppedItem myItem = new OBJ_DroppedItem(gp);
            myItem.item = grabbedItem;
            int[] tempArray = findPlaceForDroppedItem2(gp.player.worldMiddleX(), gp.player.worldMiddleY());
            myItem.worldX = tempArray[0];
            myItem.worldY = tempArray[1];
            gp.interactObjects.add(myItem);
            gp.ui.addMessage("The item that you were got put on the ground.");
        }
        gp.player.momoJuice.makeImage();
    }

    private void drawHoverGuide(String text, int x, int y, int width) {
        int padding = 10;

        // Get font metrics for the current font
        FontMetrics fm = g2.getFontMetrics();
        int textHeight = fm.getHeight();

        // Split text into lines
        ArrayList<String> lines = splitTextIntoLines(text, fm, width - 2 * padding);
        int boxHeight = textHeight * lines.size() + 2 * padding;

        // Draw the semi-transparent black box
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, boxHeight, 10, 10);

        // Draw the silver border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, width, boxHeight, 10, 10);

        // Draw the text
        g2.setColor(Color.WHITE);
        int textY = y + padding + fm.getAscent();
        for (String line : lines) {
            g2.drawString(line, x + padding, textY);
            textY += textHeight;
        }
    }

    /*private ArrayList<String> splitTextIntoLines(String text, FontMetrics fm, int maxWidth) {
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
    }*/

    private ArrayList<String> splitTextIntoLines(String text, FontMetrics fm, int maxWidth) {
        ArrayList<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("\n");

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split("\\s+");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                if (fm.stringWidth(currentLine.toString() + " " + word) <= maxWidth) {
                    if (currentLine.length() > 0) {
                        currentLine.append(" ");
                    }
                    currentLine.append(word);
                } else {
                    lines.add(currentLine.toString().trim());
                    currentLine = new StringBuilder(word);
                }
            }

            if (currentLine.length() > 0) {
                lines.add(currentLine.toString().trim());
            }
        }
        return lines;
    }

    public int[] findPlaceForDroppedItem(int x, int y){
        int[] result = new int[2];
        Rectangle tempRectangle = new Rectangle();
        for (int i = 0; i < 6; i++) {
            int j = new Random().nextInt(129) - 64;
            int k = new Random().nextInt(129) - 64;
            int counter = 0;
            if(gp.cChecker.dropItemTileCollisionCheck(x + j, y + k)){
                for (int l = 0; l < gp.interactObjects.size(); l++) {
                   if(!tempRectangle.intersects(new Rectangle(x + j + 16, y + k + 16, 32, 32))){
                       counter ++;
                   } else {
                       counter = 0;
                       //go to next i
                   }
                   if (counter == gp.interactObjects.size()){
                       result[0] = x + j;
                       result[1] = y + k;
                       return result;
                   }
                }
            }
            j = new Random().nextInt(250) - 125;
            k = new Random().nextInt(250) - 125;
            if(gp.cChecker.dropItemTileCollisionCheck(x + j, y + k)){
                for (int l = 0; l < gp.interactObjects.size(); l++) {
                    tempRectangle.x = gp.interactObjects.get(l).solidArea.x + gp.interactObjects.get(l).worldX;
                    tempRectangle.y = gp.interactObjects.get(l).solidArea.y + gp.interactObjects.get(l).worldY;
                    tempRectangle.width = gp.interactObjects.get(l).solidArea.width;
                    tempRectangle.height = gp.interactObjects.get(l).solidArea.height;
                    if(!tempRectangle.intersects(new Rectangle(x + j + 16, y + k + 16,  32, 32))){
                        result[0] = x + j;
                        result[1] = y + k;
                        return result;
                    }
                }
            }
        }
        //if finding suitable random fails just generate somewhere near player
        int j = new Random().nextInt(32) - 16;
        int k = new Random().nextInt(32) - 16;
        result[0] = j + x;
        result[1] = k + y;
        return result;
    }

    //TODO doesnt consider not interactable superobjects
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

        // If finding a suitable random position fails, generate somewhere near the player anyway
        int j = random.nextInt(32) - 16;
        int k = random.nextInt(32) - 16;
        result[0] = j + x;
        result[1] = k + y;
        return result;
    }
}
