package entity.attacks;

import entity.Entity;
import entity.attacks.player.Spells;
import entity.attacks.projectile.SHADOW_DaggerProjectile;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MON_DaggerGlobe extends Spells {

    public BufferedImage[] images = new BufferedImage[2];
    int startDiffX; int startDiffY;

    public MON_DaggerGlobe(GamePanel gp, int startDiffX, int startDiffY, Entity originEntity){
        super(gp);
        this.gp = gp;
        this.startDiffX = startDiffX;
        this.startDiffY = startDiffY;
        this.originEntity = originEntity;
        entityType = 2;
        life = 2400;
        maxLife = life;
        getImages();
        gp.spells.add(this);
    }

    public void getImages(){
        gp.entityImageLoader.matchDaggerImagesGlobe(this);
    }

    public void update(){
        life--;

        if(originEntity.isDying){
            life = 0;
        }

        if (life < maxLife - 80){
            int randomNumber = random.nextInt(600) + 1;
            if(randomNumber == 600){
                gp.playSE(71);
                int middleDistance;
                int smallestDistance = 9 * gp.tileSize;
                int smallestDistanceIndex = -1;
                for (int i = 0; i < gp.allFightingEntities.size(); i++) {
                    Entity currentEntity = gp.allFightingEntities.get(i);
                    if(isHostile(currentEntity, this)){
                        middleDistance = middleDistance(currentEntity);
                        if(middleDistance < smallestDistance) {
                            smallestDistance = middleDistance;
                            smallestDistanceIndex = i;
                        }
                    }
                }
                if (smallestDistanceIndex != -1){
                    new SHADOW_DaggerProjectile(gp,this,gp.allFightingEntities.get(smallestDistanceIndex),0, images[1]);
                    life = 0;
                }

            }
        }

        worldX = originEntity.worldX + startDiffX;
        worldY = originEntity.worldY + startDiffY;

        if(life <= 0){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2){
        if(life > maxLife - 80){
            image = images[0];
        } else {
            image = images[1];
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, null);
    }
}
