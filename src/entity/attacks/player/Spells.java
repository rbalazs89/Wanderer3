package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Spells extends Entity {
    public  int mouseLocationX; public int mouseLocationY;
    public int level;
    public  int damageType = 0;
    public  int damage;
    public static final Color transparentRed = new Color(255, 0, 0, 128);
    public ArrayList<Entity> hitEnemies = new ArrayList<>();
    public BufferedImage image;
    public  Entity originEntity;
    public  boolean soundPlayed = false;
    public  boolean hitAnEnemy = false;
    public int roundedDeltaX1;
    public int roundedDeltaY1;
    public AffineTransformOp op;
    public int xDiff; //adjust every nth frame
    public int yDiff;//adjust every nth frame
    public int adjustCounter = 0;//adjust every nth frame
    public int originEntityXAtCast;
    public int originEntityYAtCast;
    public int auraRadiusSpell = 10;

    public Spells(GamePanel gp){
        super(gp);
    }

    protected Spells() {
    }

    public void update(){

    }

    public void draw(Graphics2D g2){

    }

    public int getEntityMidDistanceFromCoord(int x, int y, Entity entity){
        return (int)Math.sqrt(Math.pow(x - entity.screenMiddleX(), 2) + Math.pow(y - entity.screenMiddleY(), 2));
    }
}