package entity.npc;

import entity.NPC;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NPC_Librarian extends NPC {

    public NPC_Librarian(GamePanel gp) {
        super(gp);
        direction = "down";
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(GamePanel.tileSize / 16,
                GamePanel.tileSize / 16,
                GamePanel.tileSize * 14 / 16,
                GamePanel.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collisionEntity = false;
        getImage();
        stopWhenPlayerNear = true;
        spriteChangeFrameValue = 20;
        headDialogueString = "Feel free to read any of the books, but ";
    }

    public void getImage(){

        maxWalkSpriteArrayLength = 8;
        walkImages = new BufferedImage[4][maxWalkSpriteArrayLength];


        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //down
            walkImages[2][i] = setupSheet("/entity/npclibrarian/librarian", i * 77, 0, 77, 77, 66, 66);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //left
            walkImages[3][i] = setupSheet("/entity/npclibrarian/librarian", i * 77, 77, 77, 77, 66, 66);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //right
            walkImages[1][i] = setupSheet("/entity/npclibrarian/librarian", i * 77, 77 * 2, 77, 77, 66, 66);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            walkImages[0][i] = setupSheet("/entity/npclibrarian/librarian", i * 77, 77 * 3, 77, 77, 66, 66);
        }
        image = walkImages[0][0];
    }

    public void drawSpecial(Graphics2D g2){
        if(actionWhenNear1){
            drawDialogue(g2);
        }
    }

    public void drawDialogue(Graphics2D g2){
        g2.setFont(talkingFont);
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<String> lines = splitTextIntoLines(headDialogueString, fm, 130 - 2 * 10);
        int textHeight = fm.getHeight();
        int boxHeight = textHeight * lines.size() + 2 * 10;
        drawHoverGuide(lines, screenMiddleX() - 65,screenMiddleY() - boxHeight - 30, 130, g2);
    }

    public void getNearHeadDialogue() {
        String[] dialogues = {
                "Feel free to read any of the books, but handle them with care. Some of these tomes are older than the village itself.",
                "Knowledge is free to those who seek it, but respect the books—they have served many before you and will serve many after.",
                "Ah, a curious mind! Read as much as you like, but return the books as you found them. A torn page is a tragedy, you know.",
                "Books are like fragile treasures—priceless, but easily ruined. Read to your heart’s content, just treat them well.",
                "I see you have an interest in literature! Just remember, a book well-kept is a book well-loved."
        };
        headDialogueString = dialogues[random.nextInt(dialogues.length)];
    }
}
