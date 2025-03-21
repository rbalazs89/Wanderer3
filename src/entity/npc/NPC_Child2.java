package entity.npc;

import entity.NPC;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Child2 extends NPC {

    public NPC_Child2(GamePanel gp) {
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

        spriteChangeFrameValue = 10;
        headDialogueString = "I like turtles";

        getImage();
        getSpecialData();
        singing = true;
        walking = false;
        direction = "left";
        //setWalkingImage();
    }

    public void getSpecialData() {

    }

    public void getImage() {
        prepareSingingNoteImage();

        maxWalkSpriteArrayLength = 3;
        walkImages = new BufferedImage[4][maxWalkSpriteArrayLength];


        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //down
            walkImages[2][i] = setupSheet("/entity/npckid/childsprites", 144 + i * 48, 0,48, 48, 64, 64);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //left
            walkImages[3][i] = setupSheet("/entity/npckid/childsprites", 144 + i * 48, 48,48, 48, 64, 64);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //up
            walkImages[0][i] = setupSheet("/entity/npckid/childsprites",  144 + i * 48, 144,48, 48, 64, 64);
        }
        //right
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            walkImages[1][i] = setupSheet("/entity/npckid/childsprites",  144 + i * 48, 96,48, 48, 64, 64);
        }
        image = walkImages[3][1];
    }

    public void update() {
        setActionAI();

        if(!singing){
            checkCollision();
            moveIfNoCollision();
        }

        if (walking) {
            speedFixer();
            setDirectionFromRandomMovement();
            setWalkingSpriteNumber();
            setWalkingImage();
        }
    }

    public void drawSpecial(Graphics2D g2) {
        if(singing){
            drawSingingNotes(g2);
        }
    }

    public void setActionAI() {

    }

    public void speedFixer(){
        speedCounter ++;
        speed = 0;
        if(speedCounter % 2 == 0){
            speedCounter = 0;
            speed = defaultSpeed;
        }
    }

    @Override
    public void drawSpecial(Graphics2D g2, int x, int y) {

    }
}