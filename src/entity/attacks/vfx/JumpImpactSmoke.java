package entity.attacks.vfx;

import entity.Entity;
import entity.attacks.player.Spells;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JumpImpactSmoke extends Spells {

    public BufferedImage images[] = new BufferedImage[24];

    public JumpImpactSmoke(GamePanel gp, Entity originEntity){
        super(gp);
        this.originEntity = originEntity;
        getImages();
        life = 24;
        maxLife = life;
        gp.vfx.add(this);
        getImages();
        worldX = originEntity.worldX;
        worldY = originEntity.worldY + 32;
    }

    public void getImages(){
        gp.entityImageLoader.matchJumpImpactImages(this);
    }

    public void update(){
        life --;
        if (life <= 0){
            gp.vfx.remove(this);
        }
    }

    public void draw(Graphics2D g2){
        image = images[Math.min(maxLife - life,23)];
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.drawImage(image, screenX,screenY,null);
    }



}
