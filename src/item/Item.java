package item;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Item {
    //Helm, Armor, Weapon, Shield, Gloves, Boots, Belt, Amulet
    public String type;
    public String name = "itemname001";
    public int imageNumber; // between 1 incl and 4 incl
    public String hoverDescription = "hoverdescription001\nndi";

    //Attributes:
    public int armorStat;
    public float speedStat;
    public int strengthStat;
    public int dexterityStat;
    public int enduranceStat;
    public int intellectStat;
    public int lifeStat;
    public int manaStat;
    public int damageStat;
    public int[] resistStat = new int[4];
    public int attackFramePoint1;
    public int attackFramePoint2;
    public int castFramePoint1;
    public int castFramePoint2;
    public int manaRegen;
    public int lifeRegen;
    public int attackRange;
    public int juiceCharge;
    public int itemCode = -1;

    public BufferedImage imageInventory;
    int buyGoldCost = 100;
    int saleGoldCost = 100;

    GamePanel gp;
    public Item(GamePanel gp){
        this.gp = gp;
    }

    public Item(){

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
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }


    public void generateHoverDescription(){
        hoverDescription = name;
        if(armorStat != 0){
            hoverDescription += "\n Armor: " + armorStat;
        }
        if(damageStat != 0){
            hoverDescription += "\n Sword damage: +" +damageStat;
        }
        if(speedStat != 0){
            if(speedStat == 0.5f){
                hoverDescription += "\n Slightly increases walk speed";
            } else if(speedStat == 1f){
                hoverDescription += "\n Greatly increases walk speed";
            } else {
                hoverDescription += "\n Increases walk speed";
            }
        }
        if(strengthStat != 0){
            hoverDescription += "\n Strength: +" + strengthStat;
        }
        if(dexterityStat != 0){
            hoverDescription += "\n Dexterity: +" + dexterityStat;
        }
        if(enduranceStat != 0){
            hoverDescription += "\n Endurance: +" + enduranceStat;
        }
        if(intellectStat != 0){
            hoverDescription += "\n Intelligence: +" + intellectStat;
        }
        if(lifeStat != 0){
            hoverDescription += "\n Maximum life: +" + lifeStat;
        }
        if(manaStat != 0){
            hoverDescription += "\n Maximum mana: +" + manaStat;
        }
        if(resistStat[0] != 0){
            hoverDescription += "\n Physical resistance: +" + resistStat[0];
        }
        if(resistStat[1] != 0){
            hoverDescription += "\n Fire resistance: +" + resistStat[1];
        }
        if(resistStat[2] != 0){
            hoverDescription += "\n Cold resistance: +" + resistStat[2];
        }
        if(resistStat[3] != 0){
            hoverDescription += "\n Lightning resistance: +" + resistStat[3];
        }

        if(attackFramePoint2 != 0){
            if(attackFramePoint2 == 1){
                hoverDescription += "\n Slightly increases attack speed";
            }
            else if(attackFramePoint2 == 2){
                hoverDescription += "\n Increases attack speed";
            }
            else if(attackFramePoint2 == 3){
                hoverDescription += "\n Greatly attack speed";
            }

        }
        if(castFramePoint1 != 0){
            if(castFramePoint1 == 1){
                hoverDescription += "\n Slightly increases cast speed";
            } else {
                hoverDescription += "\n Increases cast speed";
            }

        }
        if(castFramePoint2 != 0){
            hoverDescription += "\n Decreases cooldown";
        }

        if(manaRegen != 0) {
            hoverDescription += "\n Slightly increases mana regeneration.";
        }
        if(lifeRegen != 0) {
            hoverDescription += "\n Slightly increases life regeneration.";
        }
        if(attackRange != 0){
            hoverDescription += "\n Sword range: +" + attackRange;
        }
        if(juiceCharge != 0){
            hoverDescription += "\n Increases maximum flask charges by " + juiceCharge;
        }
        if(saleGoldCost != 0){
            hoverDescription += "\n\nSale: " + saleGoldCost + " gold.";
        }
        if(buyGoldCost != 0){
            hoverDescription += "\nPrice in shop: " + buyGoldCost + " gold.";
        }
    }


    public BufferedImage generateItemImage() {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/items/itemsheet.png"));
            int width = 48;
            int height = 48;
            int x = 0;
            int y = 0;
            switch(type){
                case "helm":
                    y = 0;
                    break;
                case "armor":
                    y = 48;
                    break;
                case "sword":
                    y = 96;
                    break;
                case "shield":
                    y = 144;
                    break;
                case "gloves":
                    y = 192;
                    break;
                case "boots":
                    y = 240;
                    break;
                case "belt":
                    y = 288;
                    break;
                case "amulet":
                    y = 336;
                    break;

            }

            switch(imageNumber){
                case 1:
                    x = 0;
                    break;
                case 2:
                    x = 48;
                    break;
                case 3:
                    x = 96;
                    break;
                case 4:
                    x = 144;
                    break;
            }
            image = image.getSubimage(x, y, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }


        return image;
    }

    public void makeBlueishItemPic() {
        if (this.imageInventory == null) {
            throw new IllegalStateException("Image inventory is not initialized.");
        }

        int width = this.imageInventory.getWidth();
        int height = this.imageInventory.getHeight();

        BufferedImage blueishBackground = createBlueishGradientImage(width, height);

        Graphics2D g2 = blueishBackground.createGraphics();
        g2.drawImage(this.imageInventory, 0, 0, null);
        g2.dispose();

        this.imageInventory = blueishBackground;
    }

    private BufferedImage createBlueishGradientImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

       float[] fractions = {0f, 1f};
        Color[] colors = {new Color(36, 36, 36, 128), new Color(0, 0, 128, 128)}; // Slightly blueish color with transparency
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

    public void makeGoldishItemPic() {
        if (this.imageInventory == null) {
            throw new IllegalStateException("Image inventory is not initialized.");
        }

        int width = this.imageInventory.getWidth();
        int height = this.imageInventory.getHeight();

        BufferedImage goldishBackground = createGoldishGradientImage(width, height);

       Graphics2D g2 = goldishBackground.createGraphics();
        g2.drawImage(this.imageInventory, 0, 0, null);
        g2.dispose();

        this.imageInventory = goldishBackground;
    }

    private BufferedImage createGoldishGradientImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        float[] fractions = {0f, 1f};
        Color[] colors = {new Color(36, 36, 36, 128), new Color(255, 165, 0, 128)}; // Goldish color with transparency
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
}
