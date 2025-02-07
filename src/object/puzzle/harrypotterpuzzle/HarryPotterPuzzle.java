package object.puzzle.harrypotterpuzzle;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HarryPotterPuzzle {
    GamePanel gp;
    Graphics2D g2;
    public Potion[] potions = new Potion[7];
    public boolean startFireAdded = false;
    public boolean startFireRemoved = false;
    public BufferedImage letterImage;
    public BufferedImage deskImage;
    public boolean englishVersion;

    public HarryPotterPuzzle(GamePanel gp){
        this.gp = gp;
        letterImage = setup("/puzzle/harrypotter/letter");
        deskImage = setup("/puzzle/harrypotter/desk");
        setupPotions();
        englishVersion = true;
    }


    public void draw (Graphics2D g2){
        this.g2 = g2;

        g2.setColor(gp.ui.c1);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        g2.drawImage(deskImage, 0,300, 750,450, null);
        g2.drawImage(letterImage, 540,100, null);


        for (int i = 0; i < 7; i++) {
            if(!potions[i].drank){
                if(i == 2){
                    g2.drawImage(potions[i].image,50 + i * 70 + 20, 400 - potions[i].image.getHeight(), null);
                } else g2.drawImage(potions[i].image,50 + i * 70, 400 - potions[i].image.getHeight(), null);
            }
        }


        g2.setFont(new Font("Calibri", Font.PLAIN, 26));
        int x = 79;
        int y = 420;
        for (int i = 0; i < 7; i++) {
            String text = " " + (i + 1);
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 1, y + 1);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            if(gp.ui.commandNum == i){
                g2.drawString(">", x - 5, y);
                g2.setColor(Color.GRAY);
                g2.drawString(text, x, y);
            }
            x += 70;
        }

        x = 36;
        y = 520;
        g2.setFont(new Font("Calibri", Font.PLAIN, 30));
        g2.setColor(Color.BLACK);
        g2.drawString("BACK", x + 3, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString("BACK", x, y);

        if(gp.ui.commandNum == 7){
            g2.drawString(">", x - 15, y);
            g2.setColor(Color.GRAY);
            g2.drawString("BACK", x, y);
        }
        x = 540;
        y = 70;
        String text = "Hungarian";
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 1, y + 1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        text = "English";
        x += 290;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 1, y + 1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        if(gp.ui.commandNum != gp.ui.previousCommandNum){
            gp.playSE(3);
            gp.ui.previousCommandNum = gp.ui.commandNum;
        }
    }

    public void refreshPotions(){
        for (int i = 0; i < 7; i++) {
            potions[0].drank = false;
            startFireRemoved = false;
        }
    }
    public void changeLetterImage(int i){
        if (i == 0) {
            letterImage = setup("/puzzle/harrypotter/letter");
        } else if (i == 1) {
            letterImage = setup("/puzzle/harrypotter/englishversion");
        }
    }

    public void drinkEffect(Potion potion){
        for (int i = 0; i < 7; i++) {
            if(potions[i] == potion){
                potions[i].drank = true;
                break;
            }
        }

        switch (potion.type) {
            case 0 -> {
                gp.ui.addMessage("You drank some tasty wine");
                gp.player.winMana(20);
                gp.playSE(47);
                break;
            }
            case 1 -> {
                gp.playSE(47);
                gp.ui.addMessage("You drank poison and died.");
                gp.player.life = -100;
                gp.gameState = gp.playState;
                break;
            }
            case 2 -> {
                gp.playSE(47);
                gp.ui.addMessage("Tastes like chicken. You are free to go back if you wish.");
                gp.player.winMana(5);
                if (gp.obj[10][29] != null) {
                    gp.obj[10][29] = null;
                }
                startFireRemoved = true;
                break;
            }
            case 3 -> {
                gp.playSE(47);
                gp.ui.addMessage("The way ahead is open.");
                if (gp.obj[10][1] != null) {
                    gp.obj[10][1] = null;
                }
                if (gp.obj[10][29] != null) {
                    gp.obj[10][29] = null;
                }
                gp.obj[10][0].interactable = false;
                gp.gameState = gp.playState;
                gp.progress.act1InteractedObjects[3] = true;
                break;
            }
        }

        if(gp.obj[10][0] != null){
            if (gp.obj[10][0] instanceof OBJ_HarryPotterPuzzle) {
                OBJ_HarryPotterPuzzle tempInstance = (OBJ_HarryPotterPuzzle) gp.obj[10][0];
                tempInstance.getUpdatedImage();
            }
        }
    }

    public void setupPotions() {
        potions[0] = new Potion();
        potions[0].image = setup("/puzzle/harrypotter/potion0");
        potions[0].drank = false;
        potions[0].type = 1;

        potions[1] = new Potion();
        potions[1].image = setup("/puzzle/harrypotter/potion1");
        potions[1].drank = false;
        potions[1].type = 0;

        potions[2] = new Potion();
        potions[2].image = setup("/puzzle/harrypotter/potion2");
        potions[2].drank = false;
        potions[2].type = 3;

        potions[3] = new Potion();
        potions[3].image = setup("/puzzle/harrypotter/potion3");
        potions[3].drank = false;
        potions[3].type = 1;

        potions[4] = new Potion();
        potions[4].image = setup("/puzzle/harrypotter/potion4");
        potions[4].drank = false;
        potions[4].type = 1;

        potions[5] = new Potion();
        potions[5].image = setup("/puzzle/harrypotter/potion5");
        potions[5].drank = false;
        potions[5].type = 0;

        potions[6] = new Potion();
        potions[6].image = setup("/puzzle/harrypotter/potion6");
        potions[6].drank = false;
        potions[6].type = 2;
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
