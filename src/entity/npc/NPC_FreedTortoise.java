package entity.npc;

import entity.NPC;
import main.GamePanel;
import tool.FadingParticlesSuper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NPC_FreedTortoise extends NPC {
    BufferedImage specialImage;
    private String currentDialogue = "";
    private int dialogueCounter = 0;
    private boolean hasGivenReward = false;
    private FadingParticlesSuper hearts;
    private boolean showHearts = true;
    private int heartCounter = 0;
    public NPC_FreedTortoise(GamePanel gp){
        super(gp);
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(GamePanel.tileSize / 16,
                GamePanel.tileSize / 16,
                GamePanel.tileSize * 14 / 16,
                GamePanel.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collisionEntity = false;

        spriteChangeFrameValue = 20;
        headDialogueString = "I like turtles";

        getImage();
        getSpecialData();

    }

    public void getSpecialData(){
        talk = true;
        getDialogueString(0);
        dialogueCounter = 350;
        goalCol = 42;
        goalRow = 38;
        targetPathFollowed = true;
    }

    public void getImage() {
        hearts = new FadingParticlesSuper(setup("/vfxparticles/heart"), this,6);

        walkImages = new BufferedImage[4][3];
        maxWalkSpriteArrayLength = 3;

        for (int i = 0; i < 3; i++) {
            //down
            walkImages[2][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 0, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            //left
            walkImages[3][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 48, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            //right
            walkImages[1][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 96, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            walkImages[0][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 144, 48, 48, 64, 64);
        }
        image = walkImages[0][0];
        specialImage = setup("/entity/npcturtle/turtlespecial", GamePanel.tileSize, GamePanel.tileSize);
    }

    public void update(){
        handleHearts();

        setActionAI();

        checkCollision();
        moveIfNoCollision();

        if(walking){
            setWalkingSpriteNumber();
            setWalkingImage();
        }
    }

    public void drawSpecial(Graphics2D g2) {
        if(talk){
            drawDialogue(g2);
        }
    }

    public void drawSpecial(Graphics2D g2, int x, int y) {
        if(showHearts){
            hearts.draw(g2,x,y);
        }
    }

    public void drawDialogue(Graphics2D g2) {
        g2.setFont(talkingFont);
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<String> lines = splitTextIntoLines(currentDialogue, fm, 130 - 2 * 10);
        int textHeight = fm.getHeight();
        int boxHeight = textHeight * lines.size() + 2 * 10;
        drawHoverGuide(lines, screenMiddleX() - 65,screenMiddleY() - boxHeight - 30, 130, g2);
    }

    public void setActionAI() {

        if(!hasGivenReward){
            tortoiseSpeed();
        }

        if (targetPathFollowed) {
            if(!searchPathBoolean(goalCol, goalRow, true)){
                setDirectionFromRandomMovement();
            }
            if(talk){
                keepDialogueScriptComingWhileWalking();
            }
        } else if (!hasGivenReward){
            dropGoldReward(100);
            hasGivenReward = true;
            targetPathFollowed = false;
            walking = false;
            direction = "down";
            speed = 0;
            image = specialImage;
        }
    }

    public void handleHearts(){
        if(showHearts){
            heartCounter ++;
            if(heartCounter > 300){
                showHearts = false;
            }
            hearts.update();
        }
    }

    public void keepDialogueScriptComingWhileWalking(){
        dialogueCounter ++;
        if(dialogueCounter >= 400){
            getDialogueString(dialogueIndex);
            dialogueIndex = (dialogueIndex + 1);
            dialogueCounter = 0;
            if(dialogueIndex > 4) {
                talk = false;
            }
        }
    }

    public void getDialogueString(int dialogueNumber) {

        switch (dialogueIndex) {
            // turtle related:
            case 0 -> currentDialogue = "Oh, sweet, merciful ground! I missed you so much!";
            case 1 ->
                    currentDialogue = "That was the worst experience of my entire life. And I once accidentally sat on a cactus.";
            case 2 ->
                    currentDialogue = "You're a true hero! A champion of turtles everywhere! I would write you a poem, but, uh… I forgot how to write.";
            case 3 -> currentDialogue = "Follow me back to my den, a true hero like you deserves a reward.";
        }
    }

    public void tortoiseSpeed(){
        speedCounter ++;
        speed = 0;
        if (speedCounter >= 3) {
            speed = defaultSpeed;
            speedCounter = 0;
        }
    }
}
