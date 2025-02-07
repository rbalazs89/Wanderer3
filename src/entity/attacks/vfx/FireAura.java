package entity.attacks.vfx;

import entity.Entity;
import entity.attacks.player.Spells;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FireAura extends Spells {

    BufferedImage[] images = new BufferedImage[20];

    public FireAura (GamePanel gp, Entity originEntity) {
        super(gp);
        this.originEntity = originEntity;
        maxLife = 80;
        life = maxLife;
        addImage();
        gp.vfx.add(this);
    }

    public void addImage(){
        BufferedImage tempImage = setupImage("/spell/fireauravfx/fireaura");
        for (int i = 0; i < 20; i++) {
            images[i] = setupSheet(tempImage,i * 128,0,128,64, 96, 48);
        }
    }

    public void update(){
        life --;
        if (life <= 0){
            gp.vfx.remove(this);
        }
    }

    public void draw(Graphics2D g2){
        int screenX = originEntity.screenX() - 22 ;
        int screenY = originEntity.screenY() + GamePanel.tileSize / 2;
        image = images[Math.min(19,(maxLife - life) / 4)];
        g2.drawImage(image,screenX,screenY,null);
    }
}
