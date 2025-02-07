package entity.npc;

import entity.NPC;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_NapTimeEnforcer extends NPC {
    //kindergarten teacher
    public NPC_NapTimeEnforcer(GamePanel gp){
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

        spriteChangeFrameValue = 6;
        headDialogueString = "I like turtles";

        getImage();
        getSpecialData();
        direction = "down";
        setWalkingImage();
        walking = false;
        singing = true;
    }

    public void getImage(){
        prepareSingingNoteImage();

        maxWalkSpriteArrayLength = 8;
        walkImages = new BufferedImage[4][maxWalkSpriteArrayLength];


        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //down
            walkImages[2][i] = setupSheet("/entity/npcnaptimeneforcer/kindergartenteacher", i * 77, 0, 77, 77, 64, 64);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //left
            walkImages[3][i] = setupSheet("/entity/npcnaptimeneforcer/kindergartenteacher", i * 77, 77, 77, 77, 64, 64);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            //right
            walkImages[1][i] = setupSheet("/entity/npcnaptimeneforcer/kindergartenteacher", i * 77, 77 * 2, 77, 77, 64, 64);
        }
        for (int i = 0; i < maxWalkSpriteArrayLength; i++) {
            walkImages[0][i] = setupSheet("/entity/npcnaptimeneforcer/kindergartenteacher", i * 77, 77 * 3, 77, 77, 64, 64);
        }
        image = walkImages[0][0];
    }

    public void getSpecialData(){

    }

    public void update() {
        setActionAI();

        if(!singing){
            checkCollision();
            moveIfNoCollision();
        }

        if(walking){
            setWalkingSpriteNumber();
            setWalkingImage();
        }
    }

    public void setActionAI(){
        if(singing){
            singingCounter ++;
        }

        if(singingCounter >= 4140){
            gp.stopMusic();
            singing = false;
            walking = true;
            for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
                if(gp.npc[gp.currentMap][i] != null){
                    if(gp.npc[gp.currentMap][i].singing){
                        gp.npc[gp.currentMap][i].singing = false;
                        gp.npc[gp.currentMap][i].walking = true;
                    }

                }
            }
        }

        if(walking){
            setDirectionFromRandomMovement();
        }

        //setActionWhenNear();
    }

    public void drawSpecial(Graphics2D g2) {
        if(singing){
            drawSingingNotes(g2);
        }
    }
}
