package entity.playerrelated;

import entity.Player;

import java.awt.image.BufferedImage;

public class PlayerSpell {
    Player player;
    public BufferedImage image;
    public int currentPointsOnSpell;
    public boolean showHelp;
    public String description;
    public int requiredLevel;
    public int uniqueSpellID;
    public PlayerSpell(Player player){
        this.player = player;
    }
}
