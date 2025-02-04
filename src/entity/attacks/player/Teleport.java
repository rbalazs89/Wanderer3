package entity.attacks.player;

import main.GamePanel;

import java.awt.*;

public class Teleport extends Spells {
    public Teleport(GamePanel gp) {
        super(gp);
        life = 60;
        maxLife = life;
        gp.spells.add(this);
        image = setup("/spell/teleport/teleportimage");
    }

    public void update() {
        if(life == maxLife){
            int[] location = findValidTeleportLocation();
            gp.player.worldX = location[0];
            gp.player.worldY = location[1];
            gp.playSE(26);
        }
        life --;

        if(life < 0){
            gp.spells.remove(this);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, gp.player.screenX, gp.player.screenY + 8, null);
    }

    public int[] findValidTeleportLocation() {
        int[] validLocation = new int[2];
        int mouseX = gp.mouseH.mouseX;
        int mouseY = gp.mouseH.mouseY;

        if(checkPlayerTile(mouseX,mouseY)){
            validLocation[0] = mouseX - 32 + gp.player.worldX - gp.player.screenX;
            validLocation[1] = mouseY - 32 + gp.player.worldY - gp.player.screenY;
            return validLocation;
        }
        mouseX = mouseX + 16;
        if(checkPlayerTile(mouseX,mouseY)){
            validLocation[0] = mouseX - 32 + gp.player.worldX - gp.player.screenX;
            validLocation[1] = mouseY - 32 + gp.player.worldY - gp.player.screenY;
            return validLocation;
        }
        mouseX = mouseX - 32;
        if(checkPlayerTile(mouseX,mouseY)){
            validLocation[0] = mouseX - 32 + gp.player.worldX - gp.player.screenX;
            validLocation[1] = mouseY - 32 + gp.player.worldY - gp.player.screenY;
            return validLocation;
        }

        mouseX = mouseX + 16;
        mouseY = mouseY - 16;
        if(checkPlayerTile(mouseX,mouseY)){
            validLocation[0] = mouseX - 32 + gp.player.worldX - gp.player.screenX;
            validLocation[1] = mouseY - 32 + gp.player.worldY - gp.player.screenY;
            return validLocation;
        }
        mouseY = mouseY + 32;
        if(checkPlayerTile(mouseX,mouseY)){
            validLocation[0] = mouseX - 32 + gp.player.worldX - gp.player.screenX;
            validLocation[1] = mouseY - 32 + gp.player.worldY - gp.player.screenY;
            return validLocation;
        }
        validLocation[0] = gp.player.worldX;
        validLocation[1] = gp.player.worldY;
        return validLocation;
    }

    public boolean checkPlayerTile(int desiredScreenMiddleX, int desiredScreenMiddleY) {
        int x1 = (desiredScreenMiddleX - gp.player.screenX + gp.player.worldX - 20);
        int x2 = (desiredScreenMiddleX - gp.player.screenX + gp.player.worldX + 20);
        int y1 = (desiredScreenMiddleY - gp.player.screenY + gp.player.worldY - 20);
        int y2 = (desiredScreenMiddleY - gp.player.screenY + gp.player.worldY + 28);
        if(x1 < 0 || y1 < 0 || x2 > (gp.currentMapMaxCol - 1) * GamePanel.tileSize || y2 > (gp.currentMapMaxRow - 1) * GamePanel.tileSize){
            return false;
        }
        x1 = x1 / 64;
        x2 = x2 / 64;
        y1 = y1 / 64;
        y2 = y2 / 64;
        int tileNum1 = gp.tileM.mapTileNum[x1][y1];
        int tileNum2 = gp.tileM.mapTileNum[x1][y2];
        int tileNum3 = gp.tileM.mapTileNum[x2][y1];
        int tileNum4 = gp.tileM.mapTileNum[x2][y2];
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum3].collision || gp.tileM.tile[tileNum4].collision) {
            return false;
        }
        Rectangle tempRectangle = new Rectangle(0, 0, 64, 64);
        Rectangle desiredRectangle = new Rectangle((desiredScreenMiddleX - gp.player.screenX + gp.player.worldX - 20), (desiredScreenMiddleY - gp.player.screenY + gp.player.worldY - 20), 40, 48);
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                if (gp.obj[gp.currentMap][i].collision) {
                    tempRectangle.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                    tempRectangle.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
                    tempRectangle.width = gp.obj[gp.currentMap][i].solidArea.width;
                    tempRectangle.height = gp.obj[gp.currentMap][i].solidArea.height;
                }
                if (tempRectangle.intersects(desiredRectangle)) {
                    return false;
                }
            }
        }
        return true;
    }
}