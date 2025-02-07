package entity;

import main.GamePanel;
import tool.SingingNote;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public abstract class NPC extends Entity{
    public boolean talk;
    public String currentDialogue = "";
    public int spriteChangeFrameValue = 3;
    // SINGING
    public int singingCounter = 0;
    public boolean createMusicNotes = false;

    public BufferedImage singerNoteImage = null;
    public final ArrayList<SingingNote> notes = new ArrayList<>();
    public final Random random = new Random();
    public int heroXAtNoteCreation = gp.player.worldX;
    public int heroYAtNoteCreation = gp.player.worldY;
    public int speedCounter = 0;
    public boolean stopWhenPlayerNear = false;

    public NPC(GamePanel gp){
        super(gp);
    }

    public void update() {
        setActionAI(); // special stuff for subclass

        decideIfPlayerNear();

        if(actionWhenNear1){
            actionIfPlayerNear();
        }

        if(walking){
            setDirectionFromRandomMovement();
            checkCollision();
            moveIfNoCollision();
            setWalkingSpriteNumber();
            setWalkingImage();
        }
    }

    /*
    public void draw2(Graphics2D g2){
        int screenX = worldX - gp.anchorX + GamePanel.halfWidth;
        int screenY = worldY - gp.anchorY + GamePanel.halfHeight;

        image = walkImages[0][0];

        if(worldX + GamePanel.tileSize > gp.anchorX - GamePanel.halfWidth &&
                worldX - GamePanel.tileSize < gp.anchorX + GamePanel.halfWidth &&
                worldY + GamePanel.tileSize > gp.anchorY - GamePanel.halfHeight &&
                worldY - GamePanel.tileSize < gp.anchorY + GamePanel.halfHeight){
            g2.drawImage(image, screenX, screenY, null);
        }
    }*/

    public void draw(Graphics2D g2){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            g2.drawImage(image, screenX, screenY,null);

            drawSpecial(g2);
            drawSpecial(g2,screenX, screenY);
        }
    }

    public void setWalkingSpriteNumber() {
        spriteCounter++;
        if (spriteCounter > spriteChangeFrameValue) {
            walkSpriteNum = (walkSpriteNum + 1) % maxWalkSpriteArrayLength;
            spriteCounter = 0;
        }
    }

    public void setWalkingImage(){
        switch (direction) {
            case "left" -> image = walkImages[3][walkSpriteNum];
            case "right" -> image = walkImages[1][walkSpriteNum];
            case "up" -> image = walkImages[0][walkSpriteNum];
            case "down" -> image = walkImages[2][walkSpriteNum];
        }
    }

    public void decideIfPlayerNear(){
        int distance = middleDistance(gp.player);

        if(!actionWhenNear1){
            if(distance < GamePanel.tileSize * 3 / 2){
                actionWhenNear1 = true;
                getNearHeadDialogue();
            }
        }

        if(actionWhenNearCounter > 60 && distance >= 2 * GamePanel.tileSize){
            actionWhenNearCounter = 0;
            actionWhenNear1 = false;
            if(stopWhenPlayerNear){
                walking = true;
            }
        }
        if(actionWhenNear1){
            actionWhenNearCounter++;
        }
    }


    public void drawSpecial(Graphics2D g2){

    }

    public void drawSpecial(Graphics2D g2, int x, int y){

    }

    public void prepareSingingNoteImage(){
        singerNoteImage = setup("/glitter/musicnote", 20,14);
    }

    public void drawSingingNotes(Graphics2D g2){
        int screenX = screenMiddleX();
        int screenY = screenMiddleY();

        if(!createMusicNotes){
            heroXAtNoteCreation = gp.player.worldX;
            heroYAtNoteCreation = gp.player.worldY;
            for (int i = 0; i < 4; i++) {
                notes.add(new SingingNote(screenX,screenY));
            }
            createMusicNotes = true;
        }

        for (int i = 0; i < notes.size(); i++) {
            SingingNote note = notes.get(i);
            note.noteCounter++;
            if(note.noteCounter % 3 == 0){
                note.y--;
                note.alpha -= 0.02f;
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, note.alpha)));
            g2.drawImage(singerNoteImage,
                    note.x - gp.player.worldX + heroXAtNoteCreation,
                    note.y - gp.player.worldY + heroYAtNoteCreation,
                    null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset alpha

            if (note.noteCounter >= 110){
                note.alpha = 1f;
                note.noteCounter = (int) (Math.random() * 40) - 30;
                note.x = note.startX - 20 + (int) (Math.random() * 40);
                note.y = note.startY - 20 + (int) (Math.random() * 40);
            }
        }
    }

    public void executeIfPathReached(){

    }

    public void actionIfPlayerNear(){
        if(stopWhenPlayerNear){
            walking = false;
        }
    }

    public void getNearHeadDialogue(){

    }

}
