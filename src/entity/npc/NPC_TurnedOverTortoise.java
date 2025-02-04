package entity.npc;

import entity.NPC;
import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class NPC_TurnedOverTortoise extends NPC {
    private int dialogueChoice = 0;
    public NPC_TurnedOverTortoise(GamePanel gp){
        super(gp);
        direction = "left";
        getImage();
        spriteChangeFrameValue = 3;
    }

    public void getImage() {
        walkImages = new BufferedImage[4][3];
        maxWalkSpriteArrayLength = 3;

        for (int i = 0; i < 3; i++) {
            //left
            walkImages[3][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 48, 48, 48, 64, 64);
        }
        image = walkImages[3][0];
    }

    public void update(){
        setWalkingSpriteNumber();
        setWalkingImage();
        setActionAI();
    }

    public void setActionAI(){
        setActionWhenNear();
    }

    public void setActionWhenNear() {
        int distance = middleDistance(gp.player);

        if(distance < GamePanel.tileSize * 10 && distance > GamePanel.tileSize * 2){
            actionLockCounter ++;
            if(actionLockCounter >= 400){
                talk = true;
                setupCurrentDialogueWhenFar(dialogueChoice);
                dialogueChoice = (dialogueChoice + 1) % 8;
                actionLockCounter = 0;
            }
        } else if (distance <= GamePanel.tileSize * 2){
            actionLockCounter ++;
            if(actionLockCounter >= 400){
                talk = true;
                setupCurrentDialogueWhenPlayerNear(dialogueChoice);
                dialogueChoice = (dialogueChoice + 1) % 8;
                actionLockCounter = 0;
            }
        } else {
            talk = false;
        }
    }

    public void drawSpecial(Graphics2D g2){
        if(talk){
            drawDialogue(g2);
        }
    }

    public void drawDialogue(Graphics2D g2){
        g2.setFont(talkingFont);
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<String> lines = splitTextIntoLines(currentDialogue, fm, 130 - 2 * 10);
        int textHeight = fm.getHeight();
        int boxHeight = textHeight * lines.size() + 2 * 10;
        drawHoverGuide(lines, screenMiddleX() - 65,screenMiddleY() - boxHeight - 60, 130, g2);
    }


    public BufferedImage setupSheet(String imageName, int x, int y, int width, int height, int scaleWidth, int scaleHeight) {
        BufferedImage image = null;
        UtilityTool uTool = new UtilityTool();
        try {
            // Load the image
            image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));

            // Crop the image
            image = image.getSubimage(x, y, width, height);

            // Scale the image
            image = uTool.scaleImage(image, scaleWidth, scaleHeight);

            // Create a graphics context to perform the vertical mirroring
            int w = image.getWidth();
            int h = image.getHeight();
            BufferedImage mirroredImage = new BufferedImage(w, h, image.getType());
            Graphics2D g2d = mirroredImage.createGraphics();

            // Apply vertical reflection (flip the image vertically)
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);  // Scale vertically (flip the image)
            tx.translate(0, -h);  // Translate to the correct position after scaling
            g2d.drawImage(image, tx, null);
            g2d.dispose();

            // Set the mirrored image as the result
            image = mirroredImage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
    public void setupCurrentDialogueWhenFar(int dialogueCounter){
        //int randomNumber = ThreadLocalRandom.current().nextInt(10);
        switch (dialogueCounter) {
            case 0:
                currentDialogue = "Maybe if I stay very still, gravity will get bored and let me go.";
                break;
            case 1:
                currentDialogue = "Alright, let’s think. If I shift my weight juuuust right—aaand now I’m even more stuck. Great.";
                break;
            case 2:
                currentDialogue = "This wouldn’t have happened if I had gone left instead of right. Or if I had stayed in bed.";
                break;
            case 3:
                currentDialogue = "Hoo boy, that was quite the tumble. Really nailed the landing, though! 10 out of 10 on the backspin.";
                break;
            case 4:
                currentDialogue = "What if I just start yelling? Turtles don’t usually yell, but maybe today’s the day we change history.";
                break;
            case 5:
                currentDialogue = "I knew I should have skipped leg day. Now look at me—flailing like an overturned omelet.";
                break;
            case 6:
                currentDialogue = "I wonder what the sky tastes like. Huh. Been staring at it long enough, might as well start asking questions";
                break;
            case 7:
                currentDialogue = "This is a sign. A sign that I should rethink all my life choices. First step: never go on a walk ever again.";
                break;
            default:
                currentDialogue = "I like turtles";
                break;
        }
    }

    public void setupCurrentDialogueWhenPlayerNear(int dialogueCounter){
        //int randomNumber = ThreadLocalRandom.current().nextInt(10);
        switch (dialogueCounter) {
            case 0:
                currentDialogue = "Oh, thank goodness! A kind soul has arrived! … Wait. Why are you just standing there?";
                break;
            case 1:
                currentDialogue = "I can explain! You see, I was testing gravity… and, uh… yep, still works. Now please help me up!";
                break;
            case 2:
                currentDialogue = "I swear, if you take one more step away from me, I will—oh no, I can’t even roll menacingly!";
                break;
            case 3:
                currentDialogue = "This is fine. Totally fine. I’ll just start a new life here. Upside-down. Turtles are adaptable, right?";
                break;
            case 4:
                currentDialogue = "You know, some turtles can flip themselves back over… but I am NOT one of those turtles.";
                break;
            case 5:
                currentDialogue = "I bet if I had arms like you, I’d be helping YOU right now. Just saying.";
                break;
            case 6:
                currentDialogue = "Oh sure, take your time! I love staring at the sky for hours… It’s not like I have things to do!";
                break;
            case 7:
                currentDialogue = "Listen, if you help me, I promise I won’t follow you home and become your weirdly loyal pet. Maybe.";
                break;
            default:
                currentDialogue = "I like turtles";
                break;
        }
    }
}
