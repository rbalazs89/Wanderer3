package entity.monster.act1;

import entity.Entity;
import entity.Fighter;
import main.GamePanel;
import object.OBJ_DroppedItem;

import java.awt.*;
import java.util.Random;

public class MON_GreenSlime extends Fighter {

    //GamePanel gp;

    //public int contactGraceCounter = 0;
    //public boolean contactGraced = false;
    //public int contactGraceFrame = 30;
    //public int entityType = 2;
    //public boolean contactGraceCollision = false; can use the same field

    /*public int attackGraceCounter = 0;
    public boolean attackGraced = false;
    public static int attackGraceFrame = 60;*/
    int counterMagic1 = 0;
    boolean canCastMagic1 = true;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        entityType = 2;
        name = "Blue slime";
        speed = 0;
        maxLife = 80;
        life = maxLife;
        solidArea.x = GamePanel.tileSize / 16 * 4;
        solidArea.y = GamePanel.tileSize / 16 * 9;
        solidArea.width = GamePanel.tileSize / 16 * 8;
        solidArea.height = GamePanel.tileSize / 16 * 7;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
        getImage();
        damageOnContactValue = 10;
        entityType = 2;
        experienceValue = 90;
        drawHpBar = true;
        collisionEntity = false;
    }

    public void getImage(){
        int tileSize = GamePanel.tileSize;

        up1 = setup("/entity/monster/act1/slime/slime_1", tileSize, tileSize);
        up2 = setup("/entity/monster/act1/slime/slime_2", tileSize, tileSize);
        up3 = setup("/entity/monster/act1/slime/slime_3", tileSize, tileSize);
        up4 = setup("/entity/monster/act1/slime/slime_4", tileSize, tileSize);
        up5 = setup("/entity/monster/act1/slime/slime_5", tileSize, tileSize);
        up6 = setup("/entity/monster/act1/slime/slime_6", tileSize, tileSize);
        up7 = setup("/entity/monster/act1/slime/slime_7", tileSize, tileSize);

        death1 = setup("/entity/monster/act1/slime/slime_death_1", tileSize, tileSize);
        death2 = setup("/entity/monster/act1/slime/slime_death_2", tileSize, tileSize);
        death3 = setup("/entity/monster/act1/slime/slime_death_3", tileSize, tileSize);
        death4 = setup("/entity/monster/act1/slime/slime_death_4", tileSize, tileSize);
        death5 = setup("/entity/monster/act1/slime/slime_death_5", tileSize, tileSize);
        death6 = setup("/entity/monster/act1/slime/slime_death_6", tileSize, tileSize);
        death7 = setup("/entity/monster/act1/slime/slime_death_7", tileSize, tileSize);

    }

    public void setActionAI(){

        if(!isDying) {
            actionLockCounter++;
            if (actionLockCounter > 120) {
                int i = random.nextInt(100) + 1;
                if (i <= 25) {
                    direction = "down";
                }
                if (25 < i && i <= 50) {
                    direction = "up";
                }
                if (50 < i && i <= 75) {
                    direction = "right";
                }
                if (75 < i) {
                    direction = "left";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY){

            if(!isDying) {
                if (spriteNum == 0) {
                    image = up1;
                } else if (spriteNum == 1) {
                    image = up2;
                } else if (spriteNum == 2) {
                    image = up3;
                } else if (spriteNum == 3) {
                    image = up4;
                } else if (spriteNum == 4) {
                    image = up5;
                } else if (spriteNum == 5) {
                    image = up6;
                } else if (spriteNum == 6) {
                    image = up7;
                }
                if (gp.gameState == gp.playState) {
                    spriteCounter++;
                    if (spriteCounter > 7) {
                        spriteNum = (spriteNum + 1) % 7;
                        spriteCounter = 0;
                    }
                }
            }

            if(isDying){
                damageOnContactValue = 3;
                int multiplier = 5;
                if(deathTimeCounter <= multiplier){
                    image = death1;
                } else if(deathTimeCounter < multiplier * 2){
                    image = death2;
                } else if (deathTimeCounter < multiplier * 3){
                    image = death3;
                } else if (deathTimeCounter < multiplier * 4){
                    image = death4;
                } else if (deathTimeCounter < multiplier * 5){
                    image = death5;
                } else if (deathTimeCounter < multiplier * 6){
                    image = death6;
                } else if (deathTimeCounter < multiplier * 7){
                    image = death7;
                }
            }
        }

        if(drawHpBar) {
            double oneScale = (double) GamePanel.tileSize / maxLife;
            double hpBarValue = oneScale * life;
            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(screenX - 1, screenY - 1, GamePanel.tileSize + 2, 12);
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX, screenY, (int) hpBarValue, 10);
        }

        g2.drawImage(image, screenX, screenY,null);
        if(gp.visibleHitBox) {
            Color transparentRed = new Color(255, 0, 0, 128);
            g2.setColor(transparentRed);
            g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void update() {
        setActionAI();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //gp.cChecker.checkEntity(this,gp.npc); // npc no collision anyway
        //gp.cChecker.checkEntity(this, gp.fighters); // check monster with eachother

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        //monster contacts player and gives damage:
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if(contactPlayer){
            monsterDamagesPlayerOnContact();
        }

        if (contactGraced && contactGraceCounter < contactGraceFrame) {
            contactGraceCounter++;
        }
        if (contactGraceCounter >= contactGraceFrame) {
            contactGraceCounter = 0;
            contactGraced = false;
        }

        if(life <= 0){
            isDying = true;
            speed = 0;
        }

        if(isDying){
            if(deathTimeCounter == 10){
                death();
            }
            deathTimeCounter++;
            if (deathTimeCounter > deathTimer){
                for (int i = 0; i < gp.fighters.length; i++) {
                    if(gp.fighters[gp.currentMap][i] == this){
                        gp.fighters[gp.currentMap][i] = null;
                        break;
                    }
                }
            }
        }
        if(!canCastMagic1){
            counterMagic1++;
            if(counterMagic1 > 100){
                canCastMagic1 = true;
                counterMagic1 = 0;
            }
        }
    }

    public void death(){
        gp.playSE(16);
        dropItem();
        gp.allFightingEntities.remove(this);
        //gp.player.experience += gp.dataBase1.levelDifferenceExperience(gp.player.level, level, experienceValue);
        gp.player.winExperience(level, experienceValue, name);
        gp.player.checkIfLvLUp();
    }

    public void actionToDamageAI(Entity damageReceivedFrom){
        direction = damageReceivedFrom.direction;
        actionLockCounter = 0;
    }

    public void dropItem() {
        if(!gp.progress.act1InteractedObjects[1]){
            if(gp.fighters[1][0] != null){
                if(gp.fighters[1][0] == this){
                    OBJ_DroppedItem myItem  = new OBJ_DroppedItem(gp);
                    myItem.item = gp.itemGenerator.generateItemBasedOnID(1);
                    myItem.image = myItem.addTransparentBracketForItemDrop(myItem.item.imageInventory);
                    gp.uTool.itemDropFromEnemy(this, myItem);
                }
            }
        } else {
            Random random = new Random();
            int chance1 = random.nextInt(itemDropChance) + 1;
            if(chance1 >= 10){
                itemDrop(level, 0, isBoss);
            }
        }
    }
}
