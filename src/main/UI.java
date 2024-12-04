package main;

import data.DataStorage;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class UI {
    GamePanel gp;
    public Graphics2D g2;
    Font arial_40, arial_80B;
    public String currentDialogue = "nothing";
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("0.00", symbols);
    private BufferedImage UIImages[] = new BufferedImage[30];
    public Color c1 = new Color(0,0,0,120);

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    // HP AND MANA BAR:
    private static final Color healthBarColor = new Color(143,38,38);
    private static final Color healthBarColor2 = new Color(255,114,118);

    private static final Color manaBarColor = new Color(31, 59, 179);
    private static final Color manaBarColor2 = new Color(75, 140, 176);
    public boolean lifeRecentlyChanged = false;
    int lifeToDraw1;
    int lifeToDraw2;
    int savedHealthDelta;
    int barHeight = 13;
    int healthBarCounter = 0;
    private boolean manaRecentlyChanged = false;
    int manaToDraw1;
    int manaToDraw2;
    int savedManaDelta = 0;
    int manaBarCounter = 0;

    // drawing ui stuff:
    public int commandNum = 0;
    public int optionsCommandNum = 0;
    public int previousOptionsCommandNum = 0;
    public int titleScreenSubState = 0;
    public int previousCommandNum = 0;

    //turn on hover strings:
    public boolean showStrGuide = false;
    public boolean showDexGuide = false;
    public boolean showIntGuide = false;
    public boolean showEndGuide = false;
    public boolean drawTestOn = true;
    public int mainMenuCommandNum;
    public boolean testMusicPlaying = false;
    public int previousMusicScale;
    public boolean canRespawn;
    public int canRespawnCounter;
    public String drawStringText[] = new String[81];

    //AVOID NEW INSTANCE:
    Color myYellow = new Color(248,248,186);
    Color startColor1 = new Color(60, 45, 30);
    Color endColor1 = new Color(45,30,15);
    Color startColor2 = new Color(72, 50, 25,234);
    Color endColor2 = new Color(45, 25, 10,234);

    Color startColor3 = new Color(124,90,60);
    Color endColor3 = new Color(204, 153, 102);
    Color startColor4 = new Color(39, 26, 13);
    Color endColor4 = new Color(20, 13, 7);
    Color mainTitleColor = new Color(172 + 60, 172 + 30 , 172);
    Color silver = new Color(192,192,192);
    Color myGrey = new Color(120, 120, 120);
    Stroke stroke3 = new BasicStroke(3);
    Stroke stroke2 = new BasicStroke(2);
    Stroke stroke1 = new BasicStroke(1);
    Font calibri20 = new Font("Calibri", Font.PLAIN, 20);
    Font calibri40 = new Font("Calibri", Font.PLAIN, 40);
    public int[] saveSlotUI = new int[5];

    public UI (GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        getImages();

        lifeToDraw1 = gp.player.life;
        lifeToDraw2 = gp.player.life;
        manaToDraw1 = gp.player.mana;
        manaToDraw2 = gp.player.mana;
        previousMusicScale = gp.music.volumeScale;

        getSaveSlots();
    }

    public void getSaveSlots() {
        for (int i = 0; i < saveSlotUI.length; i++) {
            String fileName = "save" + (i) + ".dat";
            File saveFile = new File(fileName);

            if (saveFile.exists() && saveFile.length() > 0) {  // Check if the file exists and is not empty
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                    DataStorage ds = (DataStorage) ois.readObject();
                    saveSlotUI[i] = ds.level;
                } catch (EOFException e) {
                    System.err.println("EOFException: File " + fileName + " is incomplete or corrupted.");
                    saveSlotUI[i] = 0; // Handle the specific EOFException
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    saveSlotUI[i] = 0; // Handle other IO and class not found exceptions
                }
            } else {
                saveSlotUI[i] = 0; // If the save file does not exist or is empty, set the level to 0
            }
        }
    }

    public void draw (Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // TITLE:
        if(gp.gameState == gp.titleState){
            if(titleScreenSubState == 0){
                drawTitleScreen();
            }
            else if (titleScreenSubState == 1){
                drawLoadScreen();
            }

            else if(titleScreenSubState == 2) {
                drawShowControlTitle();
            }

            else if(titleScreenSubState == 3) {
                drawStartGame();
            }
        }

        // PLAY:
        else if(gp.gameState == gp.playState) {
            drawHealthBar();
            drawManaBar();
            drawJuice();
        }

        // PAUSE:
        else if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawHealthBar();
            drawManaBar();
            drawJuice();
        }

        //DIALOGUE: TODO need te rework this
        else if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            drawHealthBar();
            drawManaBar();
        }

        //CHARACTER SCREEN:
        else if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }

        //INVENTORY SCREEN:
        else if(gp.gameState == gp.inventoryState) {
            if(gp.inventorySubState == gp.inventoryOnlyState) {
                drawHealthBar();
                drawManaBar();
                drawJuice();
            }
            else if(gp.inventorySubState == gp.inventoryStorageState1
                    || gp.inventorySubState == gp.inventoryStorageState2
                    || gp.inventorySubState == gp.inventoryStorageState3
                    || gp.inventorySubState == gp.inventoryStorageState4){
                drawStorageScreen();
            }
            else if(gp.inventorySubState == gp.inventoryShopState) {
                drawShopScreen();
            }
            drawInventoryScreen();
            gp.allInventoryPages.draw(g2);
        }

        //MAIN MENU SCREEN
        else if(gp.gameState == gp.mainMenuState){
            if(gp.mainMenuSubState == gp.mainMenuSubState0){
                drawMainMenuScreen();
            }
            else if(gp.mainMenuSubState == gp.mainMenuShowControlsState){
                drawShowControlsScreen();
            }
            else if (gp.mainMenuSubState == gp.mainMenuHelpState){
                drawHelpScreen();
            } else if (gp.mainMenuSubState == gp.mainMenuOptionsState) {
                drawOptionsScreen();
            }
            gp.keyH.enterPressed = false;
        }
        else if(gp.gameState == gp.skillPageState){
            drawSkillTable();
        }
        else if(gp.gameState == gp.talentPageState){
            drawTalentTable();
        }

        //DEATH STATE:
        else if(gp.gameState == gp.deathState){
            drawDeathState();
            if(!canRespawn){
                canRespawnCounter++;
            }
            if(canRespawnCounter > 120){
                canRespawn = true;
                canRespawnCounter = 0;
            }
        }
        else if(gp.gameState == gp.puzzleStateHarryPotter){
            gp.harryPotterPuzzle.draw(g2);
        }

        //SHOW CONTROLS SCREEN:
        drawMessage();
        if(drawTestOn){
            drawTest();
        }
    }



    // only for ending :
    private int endingCounter = 0;
    private float opacity = 0.0f; // For transition effect
    private boolean fadingIn = true; // Flag to control fade-in
    private boolean screenChanged = true; // Flag to track screen changes

    public void drawGameEnd(Graphics2D g2) {
        endingCounter++;

        // START:
        if (endingCounter == 1) {
            gp.saveLoad.save();
            gp.startSinging(12); // Set ending music

            drawStringText[0] = "Congratulations on completing the game";
            drawStringText[1] = "Lead Developer: Balázs R";
            drawStringText[2] = "Producer: Balázs R";
            drawStringText[3] = "Art Director: Balázs R";
            drawStringText[4] = "Technical Director: Balázs R";
            drawStringText[5] = "Gameplay Programmer: Balázs R";
            drawStringText[6] = "Graphics Programmer: Balázs R";
            drawStringText[7] = "Physics Programmer: Balázs R";
            drawStringText[8] = "AI Programmer: Balázs R";
            drawStringText[9] = "Network Programmer: Balázs R";
            drawStringText[10] = "Sound Engineer: Balázs R";
            drawStringText[11] = "Composer: Balázs R";
            drawStringText[12] = "Level Designer: Balázs R";
            drawStringText[13] = "Cinematics Director: Balázs R";
            drawStringText[14] = "Voice Actor: Balázs R";
            drawStringText[15] = "Quality Assurance: Balázs R";
            drawStringText[16] = "Localization Manager: Balázs R";
            drawStringText[17] = "Marketing Director: Balázs R";
            drawStringText[18] = "Public Relations: Balázs R";
            drawStringText[19] = "Community Manager: Balázs R";
            drawStringText[20] = "Customer Support: Balázs R";
            drawStringText[21] = "User Interface Designer: Balázs R";
            drawStringText[22] = "Visual Effects Artist: Balázs R";
            drawStringText[23] = "Animator: Balázs R";
            drawStringText[24] = "Character Artist: Balázs R";
            drawStringText[25] = "Concept Artist: Balázs R";
            drawStringText[26] = "Environment Artist: Balázs R";
            drawStringText[27] = "Texture Artist: Balázs R";
            drawStringText[28] = "2D Modeler: Balázs R";
            drawStringText[29] = "Story Writer: Balázs R";
            drawStringText[30] = "Investor Group Manager: Balázs R";
            drawStringText[31] = "Global Operations Agent: Balázs R";
            drawStringText[32] = "Cutscene Director: Balázs R";
            drawStringText[33] = "Customer Research Architect: Balázs R";
            drawStringText[34] = "Motion Capture Specialist: Balázs R";
            drawStringText[35] = "Producer Assistant: Balázs R";
            drawStringText[36] = "Assistant Director: Balázs R";
            drawStringText[37] = "Sound Designer: Balázs R";
            drawStringText[38] = "Music Composer: Balázs R";
            drawStringText[39] = "Sound Effects Specialist: Balázs R";
            drawStringText[40] = "Dialogue Editor: Balázs R";
            drawStringText[41] = "Lighting Artist: Balázs R";
            drawStringText[42] = "Shader Artist: Balázs R";
            drawStringText[43] = "Rigging Artist: Balázs R";
            drawStringText[44] = "Pipeline Technical Director: Balázs R";
            drawStringText[45] = "Build Engineer: Balázs R";
            drawStringText[46] = "Game Tester: Balázs R";
            drawStringText[47] = "Beta Tester Coordinator: Balázs R";
            drawStringText[48] = "Compliance Tester: Balázs R";
            drawStringText[49] = "Release Manager: Balázs R";
            drawStringText[50] = "Localization Tester: Balázs R";
            drawStringText[51] = "Certification Specialist: Balázs R";
            drawStringText[52] = "External Producer: Balázs R";
            drawStringText[53] = "Publishing Producer: Balázs R";
            drawStringText[54] = "Senior Communications Developer: Balázs R";
            drawStringText[55] = "Additional Voices: Balázs R";
            drawStringText[56] = "Motion Capture Actor: Balázs R";
            drawStringText[57] = "Stunt Coordinator: Balázs R";
            drawStringText[58] = "Stunt Performer: Balázs R";
            drawStringText[59] = "Marketing Manager: Balázs R";
            drawStringText[60] = "Web Developer: Balázs R";
            drawStringText[61] = "Community Support: Balázs R";
            drawStringText[62] = "Forum Moderator: Balázs R";
            drawStringText[63] = "Social Media Manager: Balázs R";
            drawStringText[64] = "Graphic Designer: Balázs R";
            drawStringText[65] = "Packaging Designer: Balázs R";
            drawStringText[66] = "Merchandise Designer: Balázs R";
            drawStringText[67] = "Event Coordinator: Balázs R";
            drawStringText[68] = "Financial Analyst: Balázs R";
            drawStringText[69] = "Investor Relations: Balázs R";
            drawStringText[70] = "Legal Counsel: Balázs R";
            drawStringText[71] = "HR Manager: Balázs R";
            drawStringText[72] = "Recruiter: Balázs R";
            drawStringText[73] = "Office Manager: Balázs R";
            drawStringText[74] = "Executive Assistant: Balázs R";
            drawStringText[75] = "Janitor: Balázs R";
            drawStringText[76] = "Catering: Balázs R";
            drawStringText[77] = "IT Support: Balázs R";
            drawStringText[78] = "Systems Administrator: Balázs R";
            drawStringText[79] = "Network Engineer: Balázs R";
            drawStringText[80] = "Shadow Monster Scream Sound Origin: Balázs R";
        }

        // Determine which screen to display and if a screen change has occurred
        int currentScreen = (endingCounter / (30 * 60)) % 3;
        int previousScreen = ((endingCounter - 1) / (30 * 60)) % 3;
        if (currentScreen != previousScreen) {
            screenChanged = true;
            fadingIn = true; // Trigger fade-in for screen change
            opacity = 0.0f; // Reset opacity for new screen
        }

        // Display the current screen image
        if (currentScreen == 0) {
            g2.drawImage(UIImages[18], 0, 0, null);
        } else if (currentScreen == 1) {
            g2.drawImage(UIImages[19], 0, 0, null);
        } else if (currentScreen == 2) {
            g2.drawImage(UIImages[20], 0, 0, null);
        }

        // Handle the fade-in effect only when screen changes
        if (fadingIn && screenChanged) {
            opacity += 0.01f; // Adjust the increment value for desired fade-in speed
            if (opacity >= 1.0f) {
                opacity = 1.0f;
                fadingIn = false;
                screenChanged = false; // Reset screen change flag after fade-in completes
            }
        }

        // Apply the opacity to create the fade-in effect
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - opacity));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset opacity

        // Draw the scrolling credits text with additional spacing
        g2.setFont(calibri40);
        g2.setColor(Color.white);
        int lineSpacing = 10; // Additional spacing between lines
        for (int i = 0; i < drawStringText.length; i++) {
            FontMetrics metrics = g2.getFontMetrics(calibri40);
            int textWidth = metrics.stringWidth(drawStringText[i]);
            int textX = (gp.screenWidth - textWidth) / 2;
            int textY = gp.screenHeight + i * (metrics.getHeight() + lineSpacing) - endingCounter;
            g2.drawString(drawStringText[i], textX, textY);
        }

        // END
        if (endingCounter == 90 * 60) {
            gp.stopMusic();
            endingCounter = 0;
            gp.gameState = gp.titleState;
            titleScreenSubState = 0;
            gp.playMusic(74);
        }
    }

    private void drawStartGame() {
        g2.drawImage(UIImages[0],0,0,gp.screenWidth,gp.screenHeight,null);
        g2.setColor(c1);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        String[] texts = {"EMPTY SLOT", "EMPTY SLOT", "EMPTY SLOT", "EMPTY SLOT", "EMPTY SLOT"};
        int x;
        int y = 300;

        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            if(saveSlotUI[i] != 0){
                text = "Save slot taken. Level " + saveSlotUI[i] + " Hero.";
            }
            x = getXForCenteredText(text);
            y += gp.tileSize;

            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if (commandNum == i) {
                g2.drawString(">", x - gp.tileSize, y);
                g2.setColor(Color.GRAY);
                g2.drawString(text, x, y);
            }
        }

        // Draw the "BACK" option separately
        String text = "BACK";
        x = 200;
        y += 1.5 * gp.tileSize;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 3, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (commandNum == 5) {
            g2.drawString(">", x - gp.tileSize, y);
            g2.setColor(Color.GRAY);
            g2.drawString(text, x, y);
        }

        if(previousCommandNum != commandNum){
            gp.playSE(3);
            previousCommandNum = commandNum;
        }
    }


    public void drawSkillTable() {
        int x = gp.getWidth()/2;
        int y = 1;
        int WIDTH = gp.tileSize * 8 - 1;
        int HEIGHT = gp.tileSize * 12 - 2;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        gradient = new GradientPaint(0, 0, startColor2, 0, HEIGHT, endColor2);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        gradient = new GradientPaint(0, y, startColor1, 0, y + 40, endColor1);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(stroke1);
        g2.setColor(silver);
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(silver);
        g2.setStroke(stroke2);
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String text = "SKILLS AND TALENTS";
        g2.setFont(calibri20);
        g2.setColor(Color.black);
        g2.drawString(text, x / 2 + getXForCenteredText(text), y + 22);
        g2.setColor(mainTitleColor);
        g2.drawString(text, x / 2 + getXForCenteredText(text) + 2, y + 22 + 2);

        int buttonWidth = 250;
        int buttonHeight = 40;
        int buttonX = x + 3;
        int buttonY = y + 43;
        String[] buttonLabels = {"Skills", "Talents"};

        for (int i = 0; i < 2; i++) {
            // Draw button background
            startColor = new Color(72, 50, 25,220);
            endColor = new Color(45, 25, 10,220);
            if(i == 0){
                startColor = new Color(124,90,60);
                endColor = new Color(204, 153, 102);
            }
            gradient = new GradientPaint(buttonX, buttonY, startColor, buttonX, buttonY + buttonHeight, endColor);
            g2.setPaint(gradient);
            g2.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            // Draw button border
            g2.setColor(silver);
            g2.setStroke(stroke1);
            g2.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            // Draw button text
            String buttonText = buttonLabels[i];
            Font buttonFont = new Font("Calibri", Font.PLAIN, 18);
            g2.setFont(buttonFont);
            FontMetrics fm = g2.getFontMetrics(buttonFont);
            int textX = buttonX + (buttonWidth - fm.stringWidth(buttonText)) / 2;
            int textY = buttonY + ((buttonHeight - fm.getHeight()) / 2) + fm.getAscent() + 2;
            g2.setColor(silver);
            g2.drawString(buttonText, textX, textY);

            // Adjust x position for the next button
            buttonX += buttonWidth + 5;
        }

        x = 528;
        y = 105;
        g2.setColor(myYellow);
        g2.setFont(new Font("Calibri", Font.PLAIN, 18));
        g2.drawString("To equip a spell hover the mouse over a spell icon that has", x, y);
        g2.drawString("points on it and press 1, 2, 3, 4, 5 or right click.", x, y + 20);
        g2.drawString("You get one skill point after each level.", x, y + 40);
        int lineHeight = 40;

        y += 60;
        gradient = new GradientPaint(x, y, startColor4, x, y + lineHeight, endColor4);
        g2.setPaint(gradient);
        g2.fillRect(x, y, 180, lineHeight);

        // Silver bracket addition:
        g2.setColor(myGrey);
        g2.setStroke(stroke3);
        g2.drawRect(x - 1, y - 1, 180, lineHeight + 1);
        // Draw silver brackets around the boxes
        g2.setColor(silver);
        g2.setStroke(stroke1);
        g2.drawRect(x - 1, y - 1, 180, lineHeight + 1);
        g2.setColor(new Color(139, 0, 0));
        g2.drawString("Remaining points: " + gp.player.unSpentSkillPoints, x + 10, y + 25);

        int slotBoxWidth = 65;
        int slotBoxHeight = 65;
        int slotSpacing = 16;
        int slotsPerRow = 6;
        // Starting position for the first slot
        int slotX = x;
        int slotY = y + lineHeight + 40;

        String[] slotCaptions = {"1", "2", "3", "4", "5", "Right Click"};

        for (int i = 0; i < 6; i++) {
            int currentSlotX = slotX + (i % slotsPerRow) * (slotBoxWidth + slotSpacing);
            int currentSlotY = slotY;

            // Draw caption above the slot box
            g2.setColor(silver);
            g2.setFont(new Font("Calibri", Font.PLAIN, 18));
            g2.drawString(slotCaptions[i], currentSlotX + (slotBoxWidth / 2) - g2.getFontMetrics().stringWidth(slotCaptions[i]) / 2, currentSlotY - 5);

            // Draw gradient background for the slot box
            gradient = new GradientPaint(currentSlotX, currentSlotY, startColor4, currentSlotX, currentSlotY + slotBoxHeight, endColor4);
            g2.setPaint(gradient);
            g2.fillRect(currentSlotX, currentSlotY, slotBoxWidth, slotBoxHeight);

            // Draw bracket around the slot box
            g2.setColor(myGrey);
            g2.setStroke(stroke3);
            g2.drawRect(currentSlotX - 1, currentSlotY - 1, slotBoxWidth + 1, slotBoxHeight + 1);
            g2.setColor(silver);
            g2.setStroke(stroke1);
            g2.drawRect(currentSlotX - 1, currentSlotY - 1, slotBoxWidth + 1, slotBoxHeight + 1);

            if(gp.player.equippedSpellList[i] != null){
                g2.drawImage(gp.player.equippedSpellList[i].image,currentSlotX, currentSlotY, slotBoxWidth - 1, slotBoxHeight - 1, null);
            }
        }

        int spellBoxWidth = 60;
        int spellBoxHeight = 60;
        int spellSpacing = 60;
        int spellColumnX = slotX + 70; // Use the same x-coordinate for the spell column
        int spellStartY = slotY + slotBoxHeight + 5; // Adjust starting y position for spells
        int maxLevel = 30;
        int pointsBoxWidth = 33; // Width to accommodate 2 digits
        int pointsBoxHeight = 20; // Height of the box

        for (int i = 0; i < gp.player.allSpellList.allPlayerAvailableSpells.length; i++) {
            int currentSpellX = spellColumnX + (i / 4) * (spellBoxWidth + spellSpacing);
            int requiredLevel = gp.player.allSpellList.allPlayerAvailableSpells[i].requiredLevel;
            int currentSpellY = spellStartY + (int)((requiredLevel / (double)maxLevel) * (gp.tileSize * 12 - 5 - spellStartY - spellBoxHeight));

            // Draw the spell image
            g2.drawImage(gp.player.allSpellList.allPlayerAvailableSpells[i].image, currentSpellX, currentSpellY, spellBoxWidth, spellBoxHeight, null);

            //draw grey if 0 skill point
            if (gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell == 0) {
                g2.setColor(new Color(100, 100, 100, 160));
                g2.fillRect(currentSpellX, currentSpellY, spellBoxWidth, spellBoxHeight);
            }

            // Draw bracket around the spell image
            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(currentSpellX - 1, currentSpellY - 1, spellBoxWidth + 1, spellBoxHeight + 1);
            g2.setColor(silver);
            g2.setStroke(stroke1);
            g2.drawRect(currentSpellX - 1, currentSpellY - 1, spellBoxWidth + 1, spellBoxHeight + 1);

            // Draw gradient background and bracket for current points
            int pointsX = currentSpellX + spellBoxWidth + 8;
            int pointsY = currentSpellY + spellBoxHeight - pointsBoxHeight - 5;

            GradientPaint pointsGradient = new GradientPaint(pointsX, pointsY, startColor4, pointsX, pointsY + pointsBoxHeight, endColor4);
            g2.setPaint(pointsGradient);
            g2.fillRect(pointsX, pointsY, pointsBoxWidth, pointsBoxHeight);

            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(stroke3);
            g2.drawRect(pointsX - 1, pointsY - 1, pointsBoxWidth + 1, pointsBoxHeight + 1);
            g2.setColor(silver);
            g2.setStroke(stroke1);
            g2.drawRect(pointsX - 1, pointsY - 1, pointsBoxWidth + 1, pointsBoxHeight + 1);

            // Draw the current points on spell in the bottom right corner
            Font buttonFont = new Font("Calibri", Font.PLAIN, 18);
            g2.setFont(buttonFont);
            FontMetrics fm = g2.getFontMetrics(buttonFont);
            int pointsTextWidth = fm.stringWidth(gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell + "");
            int pointsTextX = pointsX + pointsBoxWidth - pointsTextWidth - 5;
            int pointsTextY = pointsY + pointsBoxHeight - 5;

            g2.setColor(silver);
            g2.drawString(gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell + "", pointsTextX, pointsTextY);
        }

        Font descriptionFont = new Font("Calibri", Font.PLAIN, 18);
        g2.setFont(descriptionFont);

        //draw hover
        for (int i = 0; i < gp.player.allSpellList.allPlayerAvailableSpells.length; i++) {
            if (gp.player.allSpellList.allPlayerAvailableSpells[i].showHelp) {
                y = 250 + ((gp.player.allSpellList.allPlayerAvailableSpells[i].requiredLevel * 4));
                drawHoverGuide(gp.player.allSpellList.allPlayerAvailableSpells[i].description, 110, y, 390);
            }
        }
    }

    public void drawTalentTable() {
        int x = gp.getWidth()/2;
        int y = 1;
        int WIDTH = gp.tileSize * 8 - 1;
        int HEIGHT = gp.tileSize * 12 - 2;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        gradient = new GradientPaint(0, 0, startColor2, 0, HEIGHT, endColor2);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        gradient = new GradientPaint(0, y, startColor1, 0, y + 40, endColor2);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(stroke1);
        g2.setColor(silver);
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(silver);
        g2.setStroke(stroke2);
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String text = "SKILLS AND TALENTS";
        g2.setFont(calibri20);
        g2.setColor(Color.black);
        g2.drawString(text, x / 2 + getXForCenteredText(text), y + 22);
        g2.setColor(mainTitleColor);
        g2.drawString(text, x / 2 + getXForCenteredText(text) + 2, y + 22 + 2);

        int buttonWidth = 250;
        int buttonHeight = 40;
        int buttonX = x + 3;
        int buttonY = y + 43;
        String[] buttonLabels = {"Skills", "Talents"};

        for (int i = 0; i < 2; i++) {
            // Draw button background
            startColor = new Color(72, 50, 25,220);
            endColor = new Color(45, 25, 10,220);
            if(i == 1){
                startColor = new Color(124,90,60);
                endColor = new Color(204, 153, 102);
            }
            gradient = new GradientPaint(buttonX, buttonY, startColor, buttonX, buttonY + buttonHeight, endColor);
            g2.setPaint(gradient);
            g2.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            // Draw button border
            g2.setColor(silver);
            g2.setStroke(stroke1);
            g2.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            // Draw button text
            String buttonText = buttonLabels[i];
            Font buttonFont = new Font("Calibri", Font.PLAIN, 18);
            g2.setFont(buttonFont);
            FontMetrics fm = g2.getFontMetrics(buttonFont);
            int textX = buttonX + (buttonWidth - fm.stringWidth(buttonText)) / 2;
            int textY = buttonY + ((buttonHeight - fm.getHeight()) / 2) + fm.getAscent() + 2;
            g2.setColor(new Color(192, 192, 192));
            g2.drawString(buttonText, textX, textY);

            // Adjust x position for the next button
            buttonX += buttonWidth + 5;
        }

        x = 528;
        y = 105;
        g2.setColor(myYellow);
        g2.setFont(new Font("Calibri", Font.PLAIN, 18));
        g2.drawString("Talents are minor passive abilities that benefit the hero.", x, y);
        g2.drawString("You can get talent points by reading talent books.", x, y + 20);
        g2.drawString("You can reset all skill, talent and stat points at the temple.", x, y + 40);
        int lineHeight = 40;

        y += 60;
        startColor = new Color(39, 26, 13);
        endColor = new Color(20, 13, 7);
        gradient = new GradientPaint(x, y, startColor, x, y + lineHeight, endColor);;
        g2.setPaint(gradient);
        g2.fillRect(x, y, 180, lineHeight);

        // Silver bracket addition:
        g2.setColor(myGrey);
        g2.setStroke(stroke3);
        g2.drawRect(x - 1, y - 1, 180, lineHeight + 1);
        // Draw silver brackets around the boxes
        g2.setColor(silver);
        g2.setStroke(stroke1);
        g2.drawRect(x - 1, y - 1, 180, lineHeight + 1);
        g2.setColor(new Color(139, 0, 0));
        g2.drawString("Remaining points: " + gp.player.unSpentTalentPoints, x + 10, y + 25);

        x = 545;
        y = 240;

        Font buttonFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(buttonFont);

        int talentImageSize = 95;
        int talentSpacing = 70;
        int talentsPerRow = 3; // Number of talents per row
        int pointsBoxWidth = 33; // Width to accommodate 2 digits
        int pointsBoxHeight = 20; // Height of the box

        for (int i = 0; i < 7; i++) {
            int talentX = x + (i % talentsPerRow) * (talentImageSize + talentSpacing);
            int talentY = y + (i / talentsPerRow) * (talentImageSize + talentSpacing);

            // Draw the image
            g2.drawImage(gp.player.playerTalents.talentList[i].image, talentX, talentY, null);

            // Draw bracket around the image
            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(talentX - 1, talentY - 1, talentImageSize + 1, talentImageSize + 1);
            g2.setColor(new Color(192, 192, 192));
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(talentX - 1, talentY - 1, talentImageSize + 1, talentImageSize + 1);

            // Check if currentPointsOnTalent is 0 and draw transparent grey rectangle
            if (gp.player.playerTalents.talentList[i].currentPointsOnTalent == 0) {
                g2.setColor(new Color(100, 100, 100, 200));
                g2.fillRect(talentX, talentY, talentImageSize, talentImageSize);
            }

            // Draw gradient background and bracket for current points
            int pointsX = talentX + talentImageSize + 8;
            int pointsY = talentY + talentImageSize - pointsBoxHeight - 5;

            startColor = new Color(39, 26, 13);
            endColor = new Color(20, 13, 7);
            GradientPaint pointsGradient = new GradientPaint(pointsX, pointsY, startColor, pointsX, pointsY + pointsBoxHeight, endColor);
            g2.setPaint(pointsGradient);
            g2.fillRect(pointsX, pointsY, pointsBoxWidth, pointsBoxHeight);

            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(pointsX - 1, pointsY - 1, pointsBoxWidth + 1, pointsBoxHeight + 1);
            g2.setColor(new Color(192, 192, 192));
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(pointsX - 1, pointsY - 1, pointsBoxWidth + 1, pointsBoxHeight + 1);

            // Draw the current points on talent in the bottom right corner
            FontMetrics fm = g2.getFontMetrics(buttonFont);
            int pointsTextWidth = fm.stringWidth(gp.player.playerTalents.talentList[i].currentPointsOnTalent + "");
            int pointsTextX = pointsX + pointsBoxWidth - pointsTextWidth - 5;
            int pointsTextY = pointsY + pointsBoxHeight - 5;

            g2.setColor(new Color(192, 192, 192));
            g2.drawString(gp.player.playerTalents.talentList[i].currentPointsOnTalent + "", pointsTextX, pointsTextY);
        }

        Font descriptionFont = new Font("Calibri", Font.PLAIN, 18);
        g2.setFont(descriptionFont);

        //draw hover
        for (int i = 0; i < gp.player.playerTalents.talentList.length; i++) {
            if (gp.player.playerTalents.talentList[i].showHelp) {
                y = 200 + i / 3 * 165;
                drawHoverGuide(gp.player.playerTalents.talentList[i].description, 220, y, 280);
            }
        }
    }

    private void drawMainMenuScreen() {
        int x = gp.getWidth()/2 - gp.tileSize * 3;
        int y = gp.tileSize;
        int WIDTH = gp.tileSize * 6;
        int HEIGHT = gp.tileSize * 6;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, 0, startColor, 0, HEIGHT, endColor);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        startColor = new Color(60, 45, 30);
        endColor = new Color(45,30,15);
        gradient = new GradientPaint(0, y, startColor, 0, y + 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String text = "MAIN MENU";
        Font titleFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString(text, getXForCenteredText(text), y + 22);
        g2.setColor(new Color(172 + 60, 172 + 30 , 172));
        g2.drawString(text, getXForCenteredText(text) + 2, y + 22 + 2);

        //draw the options:
        g2.setFont(new Font("Calibri", Font.PLAIN, 20));
        x = x + 2 * gp.tileSize;
        y = y + 2 * gp.tileSize;
        text = "SAVE AND EXIT";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if(commandNum == 0){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        y = y + gp.tileSize*2/3;
        text = "HELP";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if(commandNum == 1){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        y = y + gp.tileSize*2/3;
        text = "OPTIONS";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 2){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        y = y + gp.tileSize*2/3;
        text = "SHOW CONTROLS";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 3){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        y = y + gp.tileSize*2/3;
        text = "BACK TO GAME";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 4){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        if(previousCommandNum != commandNum){
            gp.playSE(3);
            previousCommandNum = commandNum;
        }
    }

    public void drawHelpScreen(){
        //TODO HELP SCREEN
        int x = gp.getWidth()/2 - gp.tileSize * 3;
        int y = gp.tileSize;
        int WIDTH = gp.tileSize * 6;
        int HEIGHT = gp.tileSize * 8;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, 0, startColor, 0, HEIGHT, endColor);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        startColor = new Color(60, 45, 30);
        endColor = new Color(45,30,15);
        gradient = new GradientPaint(0, y, startColor, 0, y + 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String titleText = "GAME GUIDE";
        Font titleFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString(titleText, getXForCenteredText(titleText), y + 22);
        g2.setColor(new Color(172 + 60, 172 + 30 , 172));
        g2.drawString(titleText, getXForCenteredText(titleText) + 2, y + 22 + 2);

        //TBD
        g2.setFont(new Font("Calibri", Font.PLAIN, 18));
        g2.setColor(new Color(248,248,186));
        g2.drawString("TBD", x + 20, y + 70);

        //Draw back button
        g2.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("Back", x + 22, gp.tileSize * 9 - 10 );
        g2.setColor(Color.YELLOW);
        g2.drawString("Back", x + 22 + 1, gp.tileSize * 9 - 10 + 1);
        g2.drawString(" > ", x + 5, gp.tileSize * 9 - 10);

        if(gp.keyH.enterPressed){
            commandNum = 0;
            gp.mainMenuSubState = gp.mainMenuSubState0;
            gp.playSE(30);
        }
    }

    private void drawOptionsScreen(){

        int x = gp.getWidth()/2 - gp.tileSize * 3;
        int y = gp.tileSize;
        int WIDTH = gp.tileSize * 6;
        int HEIGHT = gp.tileSize * 8;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, 0, startColor, 0, HEIGHT, endColor);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        startColor = new Color(60, 45, 30);
        endColor = new Color(45,30,15);
        gradient = new GradientPaint(0, y, startColor, 0, y + 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String titleText = "OPTIONS";
        Font titleFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString(titleText, getXForCenteredText(titleText), y + 22);
        g2.setColor(new Color(172 + 60, 172 + 30 , 172));
        g2.drawString(titleText, getXForCenteredText(titleText) + 2, y + 22 + 2);

        //Options
        String text;

        g2.setFont(new Font("Calibri", Font.PLAIN, 20));;
        y = y + 2 * gp.tileSize;
        x = x + 25;
        text = "MUSIC";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if(commandNum == 0){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
            if(previousMusicScale != gp.music.volumeScale && !testMusicPlaying){
                gp.playMusic(31);
                testMusicPlaying = true;
                previousMusicScale = gp.music.volumeScale;
            }
        }

        int barX = x + 100;
        int barY = y - 25;
        int width = WIDTH * 3 / 5;
        int height = gp.tileSize * 3 / 5;

        startColor = new Color(20, 15, 10);
        endColor = new Color(62,45,30);
        gradient = new GradientPaint(barX, barY, startColor, barX, barY + height, endColor);
        g2.setPaint(gradient);
        g2.fillRect(barX, barY, width, height);

        //fill music bar
        startColor = new Color(124,90,60);
        endColor = new Color(204, 153, 102);
        gradient = new GradientPaint(barX, barY, startColor, barX, barY + height, endColor);
        g2.setPaint(gradient);
        g2.fillRect(barX , barY , (int)(((gp.music.volumeScale - 1.0) / 10.0) * width), height);
        g2.setColor(new Color(192,192,192));
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(barX, barY, width, height);

        // SOUND:
        y = y + gp.tileSize;
        barY = barY + gp.tileSize;
        text = "SOUND";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if(commandNum == 1){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }
        startColor = new Color(20, 15, 10);
        endColor = new Color(62,45,30);
        gradient = new GradientPaint(barX, barY, startColor, barX, barY + height, endColor);
        g2.setPaint(gradient);
        g2.fillRect(barX, barY, width, height);

        //fill sound bar
        startColor = new Color(124,90,60);
        endColor = new Color(204, 153, 102);
        gradient = new GradientPaint(barX, barY, startColor, barX, barY + height, endColor);
        g2.setPaint(gradient);
        g2.fillRect(barX , barY , (int)(((gp.se.volumeScale - 1.0) / 10.0) * width), height);
        g2.setColor(new Color(192,192,192));
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(barX, barY, width, height);

        //visible hitbox option
        y = y + gp.tileSize;
        text = "Visible hitbox";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 2){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        //checkbox
        int checkBoxY = 295;
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(637, 295,38, 38, 2, 2);
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(637,295, 38, 38, 2,2);
        if(gp.visibleHitBox){
            g2.drawImage(UIImages[17], 637,295, null);
        }


        // visible exp numbers option
        y = y + gp.tileSize;
        text = "Visible experience value";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 3){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        //checkbox
        checkBoxY += gp.tileSize;
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(637, checkBoxY,38, 38, 2, 2);
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(637,checkBoxY, 38, 38, 2,2);
        if(gp.visibleExpValue){
            g2.drawImage(UIImages[17], 637,checkBoxY, null);
        }

        // visible damage done to you
        y = y + gp.tileSize;
        text = "Visible health loss number";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 4){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        //checkbox
        checkBoxY += gp.tileSize;
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(637, checkBoxY,38, 38, 2, 2);
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(637,checkBoxY, 38, 38, 2,2);
        if(gp.visibleDamageNumbersDoneToYou){
            g2.drawImage(UIImages[17], 637,checkBoxY, null);
        }

        // visible damage done by you
        y = y + gp.tileSize;
        text = "Visible damage number";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString(text, x + 2, y + 2);
        if (commandNum == 5){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        //checkbox
        checkBoxY += gp.tileSize;
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(637, checkBoxY,38, 38, 2, 2);
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(637,checkBoxY, 38, 38, 2,2);
        if(gp.visibleDamageNumbersDoneByYou){
            g2.drawImage(UIImages[17], 637,checkBoxY, null);
        }

        //Draw back button
        y = HEIGHT + gp.tileSize - 10;
        text = "Back";
        g2.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("Back", x, y);
        g2.setColor(new Color(172 + 60, 172 + 30, 172));
        g2.drawString("Back", x + 2, y + 2);
        if (commandNum == 6){
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x+2, y+2);
            g2.drawString(">", x - 15,y);
        }

        if(previousCommandNum != commandNum){
            testMusicPlaying = false;
            gp.stopMusic();
            gp.playSE(3);
            previousCommandNum = commandNum;
        }
        if(gp.keyH.enterPressed){
            if(commandNum == 6){
                testMusicPlaying = false;
                gp.stopMusic();

                commandNum = 0;
                gp.mainMenuSubState = gp.mainMenuSubState0;
                gp.playSE(30);
            }
            else if(commandNum == 2){
                gp.visibleHitBox = !gp.visibleHitBox;
                gp.playSE(4);
            }
            else if(commandNum == 3){
                gp.visibleExpValue = !gp.visibleExpValue;
                gp.playSE(4);
            }
            else if(commandNum == 4){
                gp.visibleDamageNumbersDoneToYou = !gp.visibleDamageNumbersDoneToYou;
                gp.playSE(4);
            }
            else if(commandNum == 5){
                gp.visibleDamageNumbersDoneByYou = !gp.visibleDamageNumbersDoneByYou;
                gp.playSE(4);
            }
        }
        gp.config.saveConfig();
    }

    public void drawShowControlTitle() {
        g2.drawImage(UIImages[0],0,0,gp.screenWidth,gp.screenHeight,null);
        g2.setColor(c1);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x = gp.getWidth()/2 - gp.tileSize * 3;
        int y = gp.tileSize;
        int WIDTH = gp.tileSize * 6;
        int HEIGHT = gp.tileSize * 8 + 20;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, 0, startColor, 0, HEIGHT, endColor);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        startColor = new Color(60, 45, 30);
        endColor = new Color(45,30,15);
        gradient = new GradientPaint(0, y, startColor, 0, y + 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String titleText = "SHOW CONTROLS";
        Font titleFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString(titleText, getXForCenteredText(titleText), y + 22);
        g2.setColor(new Color(172 + 60, 172 + 30 , 172));
        g2.drawString(titleText, getXForCenteredText(titleText) + 2, y + 22 + 2);


        g2.setFont(new Font("Calibri", Font.PLAIN, 18));
        g2.setColor(new Color(248,248,186));
        String[] text = {"W", "A", "S", "D" , "I", "U", "E", "T", "Q", "M", "LSHIFT", "ESC", "Mouse left", "Mouse right", "1", "2", "3", "4", "5", "SPACE"};
        String[] text2 = {"Move up", "Move left", "Move down", "Move right", "Open inventory", "Open character screen", "Interact", "Open skill/talent page", "Drink from flask", "Open map", "Show minimap", "Open menu", "Sword attack", "Spell slot 1", "Spell slot 2", "Spell slot 3", "Spell slot 4", "Spell slot 5", "Spell slot 6", "Dash"};
        for (int i = 0; i < text.length; i++) {
            y += 23;
            g2.drawString(text[i], x + 15, y + 40);
            g2.drawString("-", x + 115, y + 40);
            g2.drawString(text2[i], x + 135, y + 40);
        }

        g2.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("Back", x + 22, gp.tileSize * 9 + 10 );
        g2.setColor(Color.YELLOW);
        g2.drawString("Back", x + 22 + 1, gp.tileSize * 9 + 10 + 1);
        g2.drawString(" > ", x + 5, gp.tileSize * 9 + 10);

        if(gp.keyH.enterPressed){
            commandNum = 0;
            //gp.mainMenuSubState = gp.mainMenuSubState0;
            gp.playSE(30);
        }
    }

    public void drawShowControlsScreen() {
        int x = gp.getWidth()/2 - gp.tileSize * 3;
        int y = gp.tileSize;
        int WIDTH = gp.tileSize * 6;
        int HEIGHT = gp.tileSize * 8 + 20;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, 0, startColor, 0, HEIGHT, endColor);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        startColor = new Color(60, 45, 30);
        endColor = new Color(45,30,15);
        gradient = new GradientPaint(0, y, startColor, 0, y + 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String titleText = "SHOW CONTROLS";
        Font titleFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString(titleText, getXForCenteredText(titleText), y + 22);
        g2.setColor(new Color(172 + 60, 172 + 30 , 172));
        g2.drawString(titleText, getXForCenteredText(titleText) + 2, y + 22 + 2);


        g2.setFont(new Font("Calibri", Font.PLAIN, 18));
        g2.setColor(new Color(248,248,186));
        String[] text = {"W", "A", "S", "D" , "I", "U", "E", "T", "Q", "M", "LSHIFT", "ESC", "Mouse left", "Mouse right", "1", "2", "3", "4", "5", "SPACE"};
        String[] text2 = {"Move up", "Move left", "Move down", "Move right", "Open inventory", "Open character screen", "Interact", "Open skill/talent page", "Drink from flask", "Open map", "Show minimap", "Open menu", "Sword attack", "Spell slot 1", "Spell slot 2", "Spell slot 3", "Spell slot 4", "Spell slot 5", "Spell slot 6", "Dash"};
        for (int i = 0; i < text.length; i++) {
            y += 23;
            g2.drawString(text[i], x + 15, y + 40);
            g2.drawString("-", x + 115, y + 40);
            g2.drawString(text2[i], x + 135, y + 40);
        }

        g2.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("Back", x + 22, gp.tileSize * 9 - 10 + 20);
        g2.setColor(Color.YELLOW);
        g2.drawString("Back", x + 22 + 1, gp.tileSize * 9 - 10 + 1 + 20);
        g2.drawString(" > ", x + 5, gp.tileSize * 9 - 10 + 20);

        if(gp.keyH.enterPressed){
            commandNum = 0;
            gp.mainMenuSubState = gp.mainMenuSubState0;
            gp.playSE(30);
        }
    }

    private void drawLoadScreen() {
        g2.drawImage(UIImages[0],0,0,gp.screenWidth,gp.screenHeight,null);
        g2.setColor(c1);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        String[] texts = {"EMPTY SLOT", "EMPTY SLOT", "EMPTY SLOT", "EMPTY SLOT", "EMPTY SLOT"};
        int x;
        int y = 300;

        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            if(saveSlotUI[i] != 0){
                text = "Load Level " + saveSlotUI[i] + " Hero.";
            }
            x = getXForCenteredText(text);
            y += gp.tileSize;

            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if (commandNum == i) {
                g2.drawString(">", x - gp.tileSize, y);
                g2.setColor(Color.GRAY);
                g2.drawString(text, x, y);
            }
        }

        // Draw the "BACK" option separately
        String text = "BACK";
        x = 200;
        y += 1.5 * gp.tileSize;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 3, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if (commandNum == 5) {
            g2.drawString(">", x - gp.tileSize, y);
            g2.setColor(Color.GRAY);
            g2.drawString(text, x, y);
        }

        if(previousCommandNum != commandNum){
            gp.playSE(3);
            previousCommandNum = commandNum;
        }
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }
    public void drawDeathState(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String text1 = "You have died.";
        String text2 = "Press enter to respawn.";
        int x = getXForCenteredText(text1);
        int y = gp.tileSize * 4;

        // SHADOW
        g2.setColor(new Color(42,0,0));
        g2.drawString(text1,x+4,y+4 - 70);

        // MAIN COLOR
        g2.setColor(Color.white);
        g2.setColor(new Color(194,24,7));
        g2.drawString(text1,x,y - 70);


        x = getXForCenteredText(text2);
        y = y + 50;
        // SHADOW
        g2.setColor(new Color(42,0,0));
        g2.drawString(text2,x+4,y+4 - 70);

        // MAIN COLOR
        g2.setColor(Color.white);
        g2.setColor(new Color(194,24,7));
        g2.drawString(text2,x,y - 70);
    }

    public void drawInventoryScreen(){
        int WIDTH = 484; //  460
        int HEIGHT = 768; // 630
        int x = 540;
        int y = 0;
        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,220);
        endColor = new Color(45, 25, 10,220);
        gradient = new GradientPaint(0, y, startColor, 0, HEIGHT, endColor);
        g2.setPaint(gradient);
        g2.drawImage(UIImages[5],x + 2, y + 2 ,WIDTH - 4, HEIGHT - 4,null);
        g2.fillRoundRect(x-1, y-1, WIDTH, HEIGHT, 10, 10);

        // Draw title bar
        startColor = new Color(80, 80, 80);
        endColor = new Color(140, 125, 140);
        gradient = new GradientPaint(0, y, startColor, 0, 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, 0, WIDTH - 2, 35, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x-1, y+1, WIDTH - 1, HEIGHT - 1, 10, 10);

        // Draw title text
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        titleFont = titleFont.deriveFont(Map.of(TextAttribute.TRACKING, 0.1)); // Adjust letter spacing
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString("Character Inventory", 150 + x, 26);
        g2.setColor(new Color(192, 192, 192));
        g2.drawString("Character Inventory", 150 + x + 1, 26 + 1);

        //Box for inventory slots
        startColor = new Color(40, 40, 40);
        endColor = new Color(0, 0, 0);
        gradient = new GradientPaint(0, y+42, startColor, 0, 82 + y + 42, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x + 22, y + 42, 440, 82, 10, 10);
        g2.setColor(new Color(100,100,100));
        g2.drawRoundRect(x + 22, y + 42, 440, 82, 10, 10);

        // draw gold
        gradient = new GradientPaint(0, y+140, startColor, 0, y +180, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x + 52, y + 140, 120, 30, 10, 10);
        g2.setColor(new Color(100,100,100));
        g2.drawRoundRect(x + 52, y + 140, 120, 30, 10, 10);
        g2.drawImage(UIImages[14],x + 22,y + 140,null);
        g2.setColor(new Color(192, 192, 192));
        g2.drawString(gp.player.gold + "", x + 60, y + 160);

        //draw black boxes
        x += 34;
        y += 59;
        for (int i = 0; i < 8; i++) {
            g2.drawImage(UIImages[6 + i], x, y, null);
            x += 52;
        }
    }

    public void drawMessage(){
        int messageX = gp.tileSize/2;
        int messageY = gp.tileSize * 9;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));

        for (int i = 0; i < message.size(); i++) {
            if(message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX, messageY);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX + 1, messageY + 1);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 20;

                if(messageCounter.get(i) > 400){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawStorageScreen() {
        int WIDTH = 484;
        int HEIGHT = 768;
        int x = 0;
        int y = 0;
        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,220);
        endColor = new Color(45, 25, 10,220);
        gradient = new GradientPaint(0, y, startColor, 0, HEIGHT, endColor);
        g2.setPaint(gradient);
        g2.drawImage(UIImages[5],x + 2, y + 2 ,WIDTH - 4, HEIGHT - 4,null);
        g2.fillRoundRect(x-1, y-1, WIDTH, HEIGHT, 10, 10);

        // Draw title bar
        startColor = new Color(80, 80, 80);
        endColor = new Color(140, 125, 140);
        gradient = new GradientPaint(0, y, startColor, 0, 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, 0, WIDTH - 2, 35, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x-1, y+1, WIDTH - 1, HEIGHT - 1, 10, 10);

        // Draw title text
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        titleFont = titleFont.deriveFont(Map.of(TextAttribute.TRACKING, 0.1)); // Adjust letter spacing
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString("Personal Storage", 150 + x, 26);
        g2.setColor(new Color(192, 192, 192));
        g2.drawString("Personal Storage", 150 + x + 1, 26 + 1);

        //Draw stash icon
        g2.drawImage(UIImages[15], x + 26, y + 75, null);

        // Draw buttons
        int buttonWidth = 82;
        int buttonHeight = 65;
        int buttonX = x + 115;
        int buttonY = y + 85;
        String[] buttonLabels = {"I", "II", "III", "IV"};

        for (int i = 0; i < 4; i++) {
            // Draw button background
            startColor = new Color(72, 50, 25,220);
            endColor = new Color(45, 25, 10,220);
            if((gp.inventorySubState == gp.inventoryStorageState1 && i == 0)
            || (gp.inventorySubState == gp.inventoryStorageState2 && i == 1)
            || (gp.inventorySubState == gp.inventoryStorageState3 && i == 2)
            || (gp.inventorySubState == gp.inventoryStorageState4 && i == 3)){
                startColor = new Color(124,90,60);
                endColor = new Color(204, 153, 102);
            }
            gradient = new GradientPaint(buttonX, buttonY, startColor, buttonX, buttonY + buttonHeight, endColor);
            g2.setPaint(gradient);
            g2.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            // Draw button border
            g2.setColor(new Color(192, 192, 192));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            // Draw button text
            String buttonText = buttonLabels[i];
            Font buttonFont = new Font("Arial", Font.PLAIN, 18);
            g2.setFont(buttonFont);
            FontMetrics fm = g2.getFontMetrics(buttonFont);
            int textX = buttonX + (buttonWidth - fm.stringWidth(buttonText)) / 2;
            int textY = buttonY + ((buttonHeight - fm.getHeight()) / 2) + fm.getAscent();
            g2.setColor(new Color(192, 192, 192));
            g2.drawString(buttonText, textX, textY);

            // Adjust x position for the next button
            buttonX += buttonWidth + 10;
        }
    }

    public void drawShopScreen() {
        int WIDTH = 484;
        int HEIGHT = 768;
        int x = 0;
        int y = 0;
        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, y, startColor, 0, HEIGHT, endColor);
        g2.setPaint(gradient);
        g2.drawImage(UIImages[16],x + 2, y + 37 ,null);
        g2.fillRoundRect(x-1, y-1, WIDTH, HEIGHT, 10, 10);
        g2.setColor(new Color(192,192,192));
        g2.setFont(new Font("Calibri", Font.PLAIN, 16));
        g2.drawString("You can sell and buy items here.",  x + 20,y + 60);

        // Draw title bar
        startColor = new Color(80, 80, 80);
        endColor = new Color(140, 125, 140);
        gradient = new GradientPaint(0, y, startColor, 0, 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, 0, WIDTH - 2, 35, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x-1, y+1, WIDTH - 1, HEIGHT - 1, 10, 10);

        // Draw title text
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        titleFont = titleFont.deriveFont(Map.of(TextAttribute.TRACKING, 0.1));
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString("Store", 180 + x, 26);
        g2.setColor(new Color(192, 192, 192));
        g2.drawString("Store", 180 + x + 1, 26 + 1);

    }

    private void drawCharacterScreen() {
        int WIDTH = 500; //  460
        int HEIGHT = 700; // 630
        Color startColor;
        Color endColor;
        GradientPaint gradient;

        /// Draw background
        g2.setColor(new Color(30, 30, 30));
        g2.fillRoundRect(5, 5, WIDTH-10, HEIGHT-10, 18, 18);

        // Draw content area
        startColor = new Color(72, 50, 25,220);
        endColor = new Color(45, 25, 10,220);
        gradient = new GradientPaint(0, 50, startColor, 0, HEIGHT - 70, endColor);
        g2.setPaint(gradient);
        g2.drawImage(UIImages[5],15, 45,WIDTH - 30, HEIGHT - 60,null);
        g2.fillRoundRect(15, 45, WIDTH - 30, HEIGHT - 60, 3, 3);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(10, 10, WIDTH - 20, HEIGHT - 20, 10, 10);

        // Draw title bar
        startColor = new Color(80, 80, 80);
        endColor = new Color(140, 125, 140); // Adjusted end color
        gradient = new GradientPaint(0, 10, startColor, 0, 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(10, 10, WIDTH - 20, 30, 10, 10);

        // Draw title text
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        titleFont = titleFont.deriveFont(Map.of(TextAttribute.TRACKING, 0.1)); // Adjust letter spacing
        g2.setFont(titleFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Level " + gp.player.level + " Hero", 20, 30);

        //DRAW EXPERIENCE BAR:
        int x = 30;
        int y = 45;
        int width = 440;
        int height = 35;
        int arc = 1;
        startColor = new Color(20, 15, 10);
        endColor = new Color(62,45,30);
        gradient = new GradientPaint(x, y, startColor, x, y + height, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, width, height, arc, arc);

        //exp bar filled area:
        startColor = new Color(124,90,60);
        endColor = new Color(204, 153, 102);
        gradient = new GradientPaint(x, y, startColor, x, y + height, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x , y , (int)(((double)(gp.player.experience - gp.player.previousLevelExp) / (gp.player.nextLevelExp - gp.player.previousLevelExp)) * width), height, arc, arc);

        //exp bar bracket:
        g2.setColor(new Color(192,192,192));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x,y, width,height, arc, arc);

        //experience value:
        String expText = "Experience this level: " + (gp.player.experience - gp.player.previousLevelExp) + " / Next level: " + (gp.player.nextLevelExp - gp.player.previousLevelExp);
        Font font = new Font("Arial", Font.BOLD, 16);
        g2.setFont(font);
        g2.setColor(new Color(192, 192, 192));

        FontMetrics metrics = g2.getFontMetrics(font);
        int textWidth = metrics.stringWidth(expText);
        int textHeight = metrics.getHeight();
        int textX = x + (width - textWidth) / 2;
        int textY = y + ((height - textHeight) / 2) + metrics.getAscent();

        g2.drawString(expText, textX, textY);
        // experience value
        expText = "Current Experience: " + gp.player.experience;
        g2.drawString(expText, x, y + 61);

        // Draw attributes
        Color myYellow = new Color(248,248,186);
        //g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        x = 30;
        y = 125;
        int lineHeight = 40;
        String[] attributes = {"Strength", "Dexterity", "Endurance", "Intelligence"};
        int[] values = {gp.player.strength, gp.player.dexterity, gp.player.endurance, gp.player.intelligence};
        for (int i = 0; i < attributes.length; i++) {
            // Draw black box for attribute values
            startColor = new Color(39, 26, 13);
            endColor = new Color(20, 13, 7);
            gradient = new GradientPaint(x, y, startColor, x, y + lineHeight, endColor);;
            g2.setPaint(gradient);
            g2.fillRect(x, y, 150, lineHeight);
            // Silver bracket addition:
            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(x - 1, y - 1, 100, lineHeight + 1); // Bracket around name
            g2.drawRect(x + 100 - 1, y - 1, 50, lineHeight + 1); // Bracket around value
            // Draw silver brackets around the boxes
            g2.setColor(new Color(192, 192, 192)); // Silver color
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(x - 1, y - 1, 100, lineHeight + 1); // Bracket around name
            g2.drawRect(x + 100 - 1, y - 1, 50, lineHeight + 1); // Bracket around value
            // Draw attribute value
            g2.setColor(myYellow);
            g2.drawString(attributes[i], x + 5, y + lineHeight - 15);
            // Draw value in small box
            g2.setColor(myYellow);
            g2.drawString(String.valueOf(values[i]), x + 115, y + lineHeight - 15);
            y += lineHeight + 20;
            //Add pictures::
            if(gp.player.unSpentStats != 0) {
                g2.drawImage(UIImages[4], 185, y - 62, null);
            }
        }

        //UNSPENT STATS:
        startColor = new Color(39, 26, 13);
        endColor = new Color(20, 13, 7);
        gradient = new GradientPaint(x, y, startColor, x, y + lineHeight, endColor);;
        g2.setPaint(gradient);
        g2.fillRect(x, y, 180, lineHeight);
        // Silver bracket addition:
        g2.setColor(new Color(120, 120, 120));
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x - 1, y - 1, 180, lineHeight + 1); // Bracket around name
        // Draw silver brackets around the boxes
        g2.setColor(new Color(192, 192, 192)); // Silver color
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(x - 1, y - 1, 180, lineHeight + 1); // Bracket around name
        g2.setColor(new Color(139, 0, 0));
        g2.drawString("Remaining points: " + gp.player.unSpentStats, x + 5, y + lineHeight - 15);
        y += lineHeight + 20;

        //Draw resistances
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        x = 30;
        y = y + 10;
        lineHeight = 40;
        String[] attributes2 = {"Armor", "Physical resistance", "Fire resistance", "Cold resistance", "Lightning resistance"};
        int[] values2 = {gp.player.armor, gp.player.resist[0], gp.player.resist[1], gp.player.resist[2], gp.player.resist[3]};
        for (int i = 0; i < attributes2.length; i++) {
            // Draw black box for attribute values
            startColor = new Color(39, 26, 13);
            endColor = new Color(20, 13, 7);
            gradient = new GradientPaint(x, y, startColor, x, y + lineHeight, endColor);;
            g2.setPaint(gradient);
            g2.fillRect(x, y, 215, lineHeight);
            // Silver bracket addition:
            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(x - 1, y - 1, 160, lineHeight + 1); // Bracket around name
            g2.drawRect(x + 160 - 1, y - 1, 55, lineHeight + 1); // Bracket around value
            // Draw silver brackets around the boxes
            g2.setColor(new Color(192, 192, 192)); // Silver color
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(x - 1, y - 1, 160, lineHeight + 1); // Bracket around name
            g2.drawRect(x + 160 - 1, y - 1, 55, lineHeight + 1); // Bracket around value
            // Draw attribute value
            g2.setColor(myYellow);
            g2.drawString(attributes2[i], x + 5, y + lineHeight - 15);
            // Draw value in small box
            g2.setColor(myYellow);
            if(i==0){
                g2.drawString(String.valueOf(values2[i]), x + 175, y + lineHeight - 15);
            } else {
                g2.drawString(String.valueOf(values2[i]) + " %", x + 175, y + lineHeight - 15);
            }
            y += lineHeight + 10;
        }

        //Draw calculated stats:
        x = 270;
        y = 120;
        lineHeight = 40;

        int tomesFound = 1;
        for (int i = 0; i < gp.progress.act1BookPickedUp.length; i++) {
            if(gp.progress.act1BookPickedUp[i]){
                tomesFound ++;
            }
        }
        float attackSpeed = (gp.player.attackFramePoint1 + gp.player.attackFramePoint2) / 60.00f;
        float cooldownValue = (gp.player.castPoint1) / 60.00f;
        float manaRegen = ((60.00f / gp.player.manaRegenPoint) * 60.0f);
        float lifeRegen = ((60.00f / gp.player.lifeRegenPoint) * 60.0f);
        String[] attributes3 = {
                "Life:",
                "Mana:",
                "Sword attack",
                "Run speed",
                "Spell damage",
                "Attack / sec",
                "Spell cooldown",
                "Life regen",
                "Mana regen",
                "Sword range ",
                "Tomes found "};

        String[] values3 = {
                gp.player.life + " / " + gp.player.maxLife,
                gp.player.mana + " / " + gp.player.maxMana,
                " " + gp.player.attackDamage,
                " " + (gp.player.speedFromItems + gp.player.defaultSpeed),
                " " + gp.player.spellDmgModifier + "%",
                df.format(attackSpeed),
                df.format(cooldownValue) + " sec",
                df.format(lifeRegen) + " / min ",
                df.format(manaRegen) + " / min ",
                String.valueOf(gp.tileSize * 5 / 4 + gp.player.attackRangeIncrease),
                String.valueOf(tomesFound)};
        for (int i = 0; i < attributes3.length; i++) {
            // Draw black box for attribute values
            startColor = new Color(39, 26, 13);
            endColor = new Color(20, 13, 7);
            gradient = new GradientPaint(x, y, startColor, x, y + lineHeight, endColor);;
            g2.setPaint(gradient);
            g2.fillRect(x, y, 200, lineHeight);
            // Silver bracket addition:
            g2.setColor(new Color(120, 120, 120));
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(x - 1, y - 1, 200, lineHeight + 1); // Bracket around name

            // Draw silver brackets around the boxes
            g2.setColor(new Color(192, 192, 192)); // Silver color
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(x - 1, y - 1, 200, lineHeight + 1); // Bracket around name

            // Draw attribute value
            g2.setColor(myYellow);
            g2.drawString(attributes3[i], x + 5, y + lineHeight - 15);
            // Draw value in small box
            g2.setColor(myYellow);
            g2.drawString(String.valueOf(values3[i]), x + 115, y + lineHeight - 15);
            y += lineHeight + 3;
        }

        //hovering guide:
        if(showStrGuide){
            drawHoverGuide("Strength increases sword attack damage.", 160,110);
        }
        else if(showDexGuide){
            drawHoverGuide("Dex slightly increases damage, sword attack speed and sword range.", 160,170);
        }
        else if(showEndGuide){
            drawHoverGuide("Increases physical resist and maximum life.", 160,230);
        }
        else if(showIntGuide){
            drawHoverGuide("Intelligence increases spell power, elemental resistances, cast speed, spell cooldown.", 160,290);
        }
    }

    public void drawHoverGuide(String text, int x, int y) {
        int padding = 10;
        int fixedWidth = 200;

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

        //g2.drawString(text,gp.player.screenX, gp.player.screenY);
        for (String line : lines) {
            g2.drawString(line, x + padding, textY);
            textY += textHeight;
        }
    }

    private void drawHoverGuide(String text, int x, int y, int width) {
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

    private void drawTitleScreen() {
            g2.drawImage(UIImages[0],0,0,gp.screenWidth,gp.screenHeight,null);
            g2.setColor(c1);
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "The Wanderer 2";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.BLACK);
            g2.drawString(text,x+4,y+4 - 70);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text,x,y - 70);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.WHITE);

            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize*3;
            g2.setColor(Color.BLACK);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
                g2.setColor(Color.gray);
                g2.drawString(text,x,y);
            }

            text = "LOAD GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.BLACK);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if( commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
                g2.setColor(Color.gray);
                g2.drawString(text,x,y);
            }

            text = "SHOW CONTROLS";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.BLACK);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if( commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
                g2.setColor(Color.gray);
                g2.drawString(text,x,y);
            }

            text = "QUIT GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.setColor(Color.BLACK);
            g2.drawString(text,x+3,y+3);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
            if( commandNum == 3) {
                g2.drawString(">", x-gp.tileSize, y);
                g2.setColor(Color.gray);
                g2.drawString(text,x,y);
            }

        if(previousCommandNum != commandNum){
            gp.playSE(3);
            previousCommandNum = commandNum;
        }
    }

    private void drawDialogueScreen() {
        int x = 2 * gp.tileSize;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - ( 4 * gp.tileSize);
        int height = (int)(gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        x += gp.tileSize/2;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));
        for(String line : currentDialogue.split("/n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0, 125);
        g2.setColor(c);
        g2.fillRoundRect(x, y , width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke (new BasicStroke(3));
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 25, 25);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        g2.setFont(arial_40);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100));
        g2.setColor(Color.white);
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text,x,y);
    }

    private void drawHealthBar() {
        if(gp.player.life < 0){
            gp.player.life = 0;
        }
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }

        int xhb = 5;
        int yhb = 5;
        g2.drawImage(UIImages[1],xhb,yhb, null);
        xhb += 10;
        g2.drawImage(UIImages[2],xhb,yhb, (gp.player.maxLife) + 2, 32, null);
        g2.drawImage(UIImages[3],xhb + gp.player.maxLife + 2, yhb, null);
        g2.setColor(healthBarColor);
        xhb = xhb + 1;
        yhb = yhb + 9;
        g2.fillRect(xhb, yhb, lifeToDraw2, barHeight);

        //}
        if(lifeToDraw1 != gp.player.life){
            lifeRecentlyChanged = true;
        }
        if(lifeRecentlyChanged) {

            healthBarCounter ++;
            savedHealthDelta = Math.abs(lifeToDraw2 - gp.player.life);

            if(lifeToDraw2 < gp.player.life){
                g2.setColor(healthBarColor);
                g2.fillRect(xhb, yhb, lifeToDraw2, barHeight);
                g2.setColor(healthBarColor2);
                g2.fillRect(xhb + lifeToDraw2, yhb, savedHealthDelta, barHeight);

            }if (lifeToDraw2 > gp.player.life) {
                g2.setColor(healthBarColor);
                g2.fillRect(xhb, yhb, gp.player.life, barHeight);
                g2.setColor(healthBarColor2);
                g2.fillRect(xhb + gp.player.life, yhb, savedHealthDelta, barHeight);
            }

            if (lifeToDraw1 != gp.player.life){
                healthBarCounter = 0;
            }

            if(healthBarCounter > 20){
                healthBarCounter = 0;
                lifeRecentlyChanged = false;
                lifeToDraw2 = gp.player.life;
            }
        }
        lifeToDraw1 = gp.player.life;
    }

    public void drawJuice(){
        g2.drawImage(gp.player.momoJuice.finalImage, 10, 75, null);
    }

    private void drawManaBar() {
        if(gp.player.mana < 0){
            gp.player.mana = 0;
        }
        if(gp.player.mana > gp.player.maxMana){
            gp.player.mana = gp.player.maxMana;
        }

        int xhb = 5;
        int yhb = 40;
        g2.drawImage(UIImages[1],xhb,yhb, null);
        xhb += 10;
        g2.drawImage(UIImages[2],xhb,yhb, (gp.player.maxMana) + 2, 32, null);
        g2.drawImage(UIImages[3],xhb + gp.player.maxMana + 2, yhb, null);
        g2.setColor(manaBarColor);
        xhb = xhb + 1;
        yhb = yhb + 9;
        g2.fillRect(xhb, yhb, manaToDraw2, barHeight);

        if(manaToDraw1 != gp.player.mana){
            manaRecentlyChanged = true;
        }
        if(manaRecentlyChanged) {

            manaBarCounter ++;
            savedManaDelta = Math.abs(manaToDraw2 - gp.player.mana);

            if(manaToDraw2 < gp.player.mana){
                g2.setColor(manaBarColor);
                g2.fillRect(xhb, yhb, manaToDraw2, barHeight);
                g2.setColor(manaBarColor2);
                g2.fillRect(xhb + manaToDraw2, yhb, savedManaDelta, barHeight);

            }if (manaToDraw2 > gp.player.mana) {
                g2.setColor(manaBarColor);
                g2.fillRect(xhb, yhb, gp.player.mana, barHeight);
                g2.setColor(manaBarColor2);
                g2.fillRect(xhb + gp.player.mana, yhb, savedManaDelta, barHeight);
            }

            if (manaToDraw1 != gp.player.mana){
                manaBarCounter = 0;
            }

            if(manaBarCounter > 20){
                manaBarCounter = 0;
                manaRecentlyChanged = false;
                manaToDraw2 = gp.player.mana;
            }
        }
        manaToDraw1 = gp.player.mana;
    }

    public void drawFancySubWindow(int x, int y, int WIDTH, int HEIGHT) {

        x = gp.getWidth()/2 - gp.tileSize * 3;
        y = gp.tileSize;
        WIDTH = gp.tileSize * 6;
        HEIGHT = gp.tileSize * 8;

        Color startColor;
        Color endColor;
        GradientPaint gradient;

        //Draw brown background
        startColor = new Color(72, 50, 25,234);
        endColor = new Color(45, 25, 10,234);
        gradient = new GradientPaint(0, 0, startColor, 0, HEIGHT, endColor);
        g2.drawImage(UIImages[5],x+2, y+2, WIDTH-2, HEIGHT-2, null);
        g2.setPaint(gradient);
        g2.fillRoundRect(x,y,WIDTH,HEIGHT, 10, 10);

        // Draw title bar
        int titleBarHeight = 40;
        startColor = new Color(60, 45, 30);
        endColor = new Color(45,30,15);
        gradient = new GradientPaint(0, y, startColor, 0, y + 40, endColor);
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, WIDTH, titleBarHeight, 10, 10);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(192,192,192));
        g2.drawRoundRect(x,y,WIDTH,titleBarHeight, 10, 10);

        // Draw window border
        g2.setColor(new Color(192, 192, 192));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

        // Draw title text
        String titleText = "MAIN MENU";
        Font titleFont = new Font("Calibri", Font.PLAIN, 20);
        g2.setFont(titleFont);
        g2.setColor(Color.black);
        g2.drawString(titleText, getXForCenteredText(titleText), y + 22);
        g2.setColor(new Color(172 + 60, 172 + 30 , 172));
        g2.drawString(titleText, getXForCenteredText(titleText) + 2, y + 22 + 2);
    }

    private ArrayList<String> splitTextIntoLines(String text, FontMetrics fm, int maxWidth) {
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
    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    private int getXToAlignRight(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    public void drawTest(){
        g2.drawImage(gp.player.testImage, 100, 100,null);
    }

    private BufferedImage createGradientImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // Create a radial gradient that starts with gray at the center and goes to black at the edges
        float[] fractions = {0f, 1f};
        Color[] colors = {new Color(36,36,36), Color.BLACK};
        RadialGradientPaint radialGradient = new RadialGradientPaint(
                new Point2D.Double(width / 2, height / 2),
                Math.min(width, height) / 2,
                fractions,
                colors,
                MultipleGradientPaint.CycleMethod.NO_CYCLE
        );

        g2.setPaint(radialGradient);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        return image;
    }
    private void getImages() {
            UIImages[0] = setup("/ui/background2");
            UIImages[1] = setup("/ui/healthbar_left");
            UIImages[2] = setup("/ui/healthbar_middle");
            UIImages[3] = setup("/ui/healthbar_right");
            UIImages[4] = setup("/ui/statupicon");
            UIImages[5] = setup("/ui/uibackground", 460, 630); //marble

            BufferedImage tempImage = setupImage("/ui/invsheet");
            UIImages[6] = setupSheet(tempImage, 0, 0,48,48);
            UIImages[7] = setupSheet(tempImage,  48,0, 48,48);
            UIImages[8] = setupSheet(tempImage, 96, 0,48,48);
            UIImages[9] = setupSheet(tempImage, 144, 0,48,48);
            UIImages[10] = setupSheet(tempImage,  192, 0, 48,48);
            UIImages[11] = setupSheet(tempImage,  240, 0, 48,48);
            UIImages[12] = setupSheet(tempImage,  288,0, 48,48);
            UIImages[13] = setupSheet(tempImage,  336,0, 48,48);

            UIImages[14] = setup("/ui/goldicon");
            UIImages[15] = setup("/objects/interact/treasurechest", 80, 80);
            UIImages[16] = setup("/ui/trader3");
            UIImages[17] = setup("/ui/donesymbol");
            UIImages[18] = setup("/ui/endingscreens/ending_bunny");
            UIImages[19] = setup("/ui/endingscreens/ending_mage");
            UIImages[20] = setup("/ui/endingscreens/ending_cellar");
    }

    public static BufferedImage adjustTransparency(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = original.getRGB(x, y);
                Color color = new Color(rgba, true);

                // Calculate the blackness (inverse of brightness)
                int blackness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                // Calculate the alpha based on the blackness
                int alpha = blackness;

                // Create a new color with the same RGB but modified alpha
                Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
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
}
