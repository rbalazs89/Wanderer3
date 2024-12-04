package entity.attacks.player;
import entity.Entity;

import main.GamePanel;

import java.awt.*;

public class PlayerAttack extends MeleeAttack {

    public PlayerAttack(GamePanel gp, Entity originEntity, int attackType){
        super(gp);
        this.gp = gp;
        maxLife = 15;
        life = maxLife;
        this.originEntity = originEntity;
        attackRectangle = getRectangle(attackType);
        if(gp.visibleHitBox) {
            image = setup("/other/redtransparent", attackRectangle.width, attackRectangle.height);
        } else image = null;
        soundEffectsHit[0] = 4;
        soundEffectsHit[1] = 9;
        soundEffectsMiss[0] = 4;
        soundEffectsMiss[1] = 5;
        soundEffectsHitBarrel[0] = 12;
        soundEffectsHitBarrel[1] = 2;
    }

    public Rectangle getRectangle(int attackType){
        Rectangle myRectangle = new Rectangle(0,0,0,0);
        if (attackType == 1) {
            myRectangle.height = (GamePanel.tileSize * 5/4) + gp.player.attackRangeIncrease;
            myRectangle.width = (GamePanel.tileSize * 3/2);
            myRectangle.x = gp.player.worldX - GamePanel.tileSize * 1/4;
            myRectangle.y = gp.player.worldY - GamePanel.tileSize * 3/4 - gp.player.attackRangeIncrease;
        }
        else if (attackType == 2) {
            myRectangle.height = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.width = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.x = gp.player.worldX + GamePanel.tileSize * 2/4;
            myRectangle.y = gp.player.worldY - GamePanel.tileSize * 3/4 - (int)(gp.player.attackRangeIncrease * 0.7);
        }
        else if (attackType == 3) {
            myRectangle.height = (GamePanel.tileSize * 3/2);
            myRectangle.width = (GamePanel.tileSize * 5/4) + gp.player.attackRangeIncrease;
            myRectangle.x = gp.player.worldX + GamePanel.tileSize * 2/4;
            myRectangle.y = gp.player.worldY - GamePanel.tileSize * 1/4;
        }
        else if (attackType == 4) {
            myRectangle.height = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.width = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.x = gp.player.worldX + GamePanel.tileSize * 2/4;
            myRectangle.y = gp.player.worldY + GamePanel.tileSize * 2/4;
        }
        else if (attackType == 5) {
            myRectangle.height = (GamePanel.tileSize * 5/4) + gp.player.attackRangeIncrease;
            myRectangle.width = (GamePanel.tileSize * 3/2);
            myRectangle.x = gp.player.worldX - GamePanel.tileSize * 1/4;
            myRectangle.y = gp.player.worldY + GamePanel.tileSize * 2/4;
        }
        else if (attackType == 6) {
            myRectangle.height = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.width = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.x = gp.player.worldX - GamePanel.tileSize * 3/4 - (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.y = gp.player.worldY + GamePanel.tileSize * 2/4;
        }
        else if (attackType == 7) {
            myRectangle.height = (GamePanel.tileSize * 3/2);
            myRectangle.width = (GamePanel.tileSize * 5/4) + gp.player.attackRangeIncrease;
            myRectangle.x = gp.player.worldX - GamePanel.tileSize * 3/4 - gp.player.attackRangeIncrease;
            myRectangle.y = gp.player.worldY - GamePanel.tileSize * 1/4;
        }
        else if (attackType == 8) {
            myRectangle.height = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.width = (GamePanel.tileSize * 5/4) + (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.x = gp.player.worldX - GamePanel.tileSize * 3/4 - (int)(gp.player.attackRangeIncrease * 0.7);
            myRectangle.y = gp.player.worldY - GamePanel.tileSize * 3/4 - (int)(gp.player.attackRangeIncrease * 0.7);
        }
        return myRectangle;
    }
}
