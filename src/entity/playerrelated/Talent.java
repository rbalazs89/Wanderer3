package entity.playerrelated;

import entity.Player;

import java.awt.image.BufferedImage;

public class Talent {

    public int currentPointsOnTalent;
    Player player;
    public BufferedImage image;
    public String description;
    public int oneLevelValue;
    public int finalValue;
    public boolean showHelp;
    public Talent(Player player){
        this.player = player;
    }
}
