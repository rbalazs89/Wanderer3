package tool;

import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FadingParticlesSuper {
    public ArrayList<FadingParticle> fadingParticles = new ArrayList<>();
    public BufferedImage image;
    public Entity originEntity;
    public int particleArraySize;

    public FadingParticlesSuper(BufferedImage image, Entity originEntity, int particleArraySize){
        this.image = image;
        this.originEntity = originEntity;
        this.particleArraySize = particleArraySize;
        for (int i = 0; i < particleArraySize; i++) {
            fadingParticles.add(new FadingParticle());
        }
    }

    public void update(){
        for (int i = 0; i < particleArraySize; i++) {
            fadingParticles.get(i).update();
        }
    }

    public void draw(Graphics2D g2, int entityScreenX, int entityScreenY){
        for (int i = 0; i < particleArraySize; i++) {
            FadingParticle currentParticle = fadingParticles.get(i);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, currentParticle.alpha)));
            g2.drawImage(image,currentParticle.x + entityScreenX,currentParticle.y + entityScreenY,null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
