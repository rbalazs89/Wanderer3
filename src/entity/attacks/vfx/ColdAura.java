package entity.attacks.vfx;

import entity.Entity;
import entity.attacks.player.Spells;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColdAura extends Spells {
    public BufferedImage images[] = new BufferedImage[20];

    public ColdAura (GamePanel gp, Entity originEntity) {
        super(gp);
        this.originEntity = originEntity;
        maxLife = 120;
        life = maxLife;
        addImage();
        gp.vfx.add(this);
    }

    public void addImage(){
        gp.entityImageLoader.matchBlueAuraImages(this);
    }

    public void update(){
        life --;
        if (life <= 0){
            gp.vfx.remove(this);
        }
    }

    public void draw(Graphics2D g2){
        int screenX = originEntity.screenX() - 22 ;
        int screenY = originEntity.screenY() + gp.tileSize / 2;
        image = images[Math.min(19,(maxLife - life) / (maxLife/20))];
        g2.drawImage(image,screenX,screenY,96,64,null);
    }
}
