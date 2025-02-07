package tile;

import main.GamePanel;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map{
    BufferedImage worldMap;
    public boolean miniMapOn = false;
    GamePanel gp;

    public Map(GamePanel gp){
        this.gp = gp;
        createMapScreenImage();
    }

    public void createMapScreenImage() {
        int worldMapWidth = GamePanel.tileSize * gp.currentMapMaxCol;
        int worldMapHeight = GamePanel.tileSize * gp.currentMapMaxRow;

        worldMap = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)worldMap.createGraphics();
        int col = 0;
        int row = 0;

        while(col < gp.currentMapMaxCol && row < gp.currentMapMaxRow){
            int tileNum = gp.tileM.mapTileNum[col][row];
            int x = GamePanel.tileSize * col;
            int y = GamePanel.tileSize * row;
            g2.drawImage(gp.tileM.tile[tileNum].image, x, y, null);
            col ++;
                    if(col == gp.currentMapMaxCol){
                        col = 0;
                        row ++;
                    }
        }
        g2.dispose();
    }

    public void drawFullMap(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        float aspectRatio = Math.max(0.5f, Math.min((float) gp.currentMapMaxCol / gp.currentMapMaxRow, 2.0f));

        int scaledWidth;
        int scaledHeight;

        if (gp.screenWidth / aspectRatio <= gp.screenHeight) {
            scaledWidth = gp.screenWidth;
            scaledHeight = (int) (gp.screenWidth / aspectRatio);
        } else {
            scaledWidth = (int) (gp.screenHeight * aspectRatio);
            scaledHeight = gp.screenHeight;
        }

        int x = gp.screenWidth / 2 - scaledWidth / 2;
        int y = gp.screenHeight / 2 - scaledHeight / 2;

        g2.drawImage(worldMap, x, y, scaledWidth, scaledHeight, null);

        for (int i = 0; i < gp.mapBlockManager.mapBlock.length; i++) {
            MapBlock currentMapBlock = gp.mapBlockManager.mapBlock[i];
            if (currentMapBlock != null) {
                int blockX = (int) ((currentMapBlock.worldX / (float) (gp.currentMapMaxCol * GamePanel.tileSize)) * scaledWidth);
                int blockY = (int) ((currentMapBlock.worldY / (float) (gp.currentMapMaxRow * GamePanel.tileSize)) * scaledHeight);
                int blockWidth = (int) (currentMapBlock.image.getWidth() * ((float) scaledWidth / worldMap.getWidth()));
                int blockHeight = (int) (currentMapBlock.image.getHeight() * ((float) scaledHeight / worldMap.getHeight()));

                g2.drawImage(currentMapBlock.image, x + blockX, y + blockY, blockWidth, blockHeight, null);
            }
        }
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                SuperObject currentObject = gp.obj[gp.currentMap][i];
                if (currentObject != null) {
                    if(currentObject.image != null){
                        int objX = (int) ((currentObject.worldX / (float) (gp.currentMapMaxCol * GamePanel.tileSize)) * scaledWidth);
                        int objY = (int) ((currentObject.worldY / (float) (gp.currentMapMaxRow * GamePanel.tileSize)) * scaledHeight);
                        int objWidth = (int) (currentObject.image.getWidth() * ((float) scaledWidth / worldMap.getWidth()));
                        int objHeight = (int) (currentObject.image.getHeight() * ((float) scaledHeight / worldMap.getHeight()));
                        g2.drawImage(currentObject.image, x + objX, y + objY, objWidth, objHeight, null);
                    }
                }
            }
        }

        int playerX = (int) ((gp.player.worldMiddleX() / (float)(gp.currentMapMaxCol * GamePanel.tileSize)) * scaledWidth);
        int playerY = (int) ((gp.player.worldMiddleY() / (float)(gp.currentMapMaxRow * GamePanel.tileSize)) * scaledHeight);
        int playerSize = 64;
        g2.drawImage(gp.player.idleDown1, x + playerX - playerSize / 2, y + playerY - playerSize / 2, playerSize, playerSize,null);


    }

    public void drawMiniMap(Graphics2D g2) {
        if (miniMapOn) {
            int width = 200;
            int height = 200;
            int x = gp.screenWidth - width - 20;
            int y = 20;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
            g2.drawImage(worldMap, x, y, width, height, null);

            double scaleX = (double) (GamePanel.tileSize * gp.currentMapMaxCol) / width;
            double scaleY = (double) (GamePanel.tileSize * gp.currentMapMaxRow) / height;

            // Draw the player
            int playerX = (int) (x + gp.player.worldMiddleX() / scaleX);
            int playerY = (int) (y + gp.player.worldMiddleY() / scaleY);
            int playerSize = Math.min(Math.max(12, (int) (gp.tileSize / Math.min(scaleX, scaleY))), 24);
            g2.drawImage(gp.player.idleDown1, playerX - playerSize / 2, playerY - playerSize / 2, playerSize, playerSize, null);

            // Draw the objects
            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                SuperObject currentObject = gp.obj[gp.currentMap][i];
                if (currentObject != null) {
                    if(currentObject.image != null){
                        int objX = (int) (x + currentObject.worldX / scaleX);
                        int objY = (int) (y + currentObject.worldY / scaleY);
                        int objWidth = (int) (currentObject.image.getWidth() / scaleX);
                        int objHeight = (int) (currentObject.image.getHeight() / scaleY);
                        g2.drawImage(currentObject.image, objX, objY, objWidth, objHeight, null);
                    }
                }
            }

            // Draw the map blocks
            for (int i = 0; i < gp.mapBlockManager.mapBlock.length; i++) {
                MapBlock currentMapBlock = gp.mapBlockManager.mapBlock[i];
                if (currentMapBlock != null) {
                    int blockX = (int) (x + currentMapBlock.worldX / scaleX);
                    int blockY = (int) (y + currentMapBlock.worldY / scaleY);
                    int blockWidth = (int) (currentMapBlock.image.getWidth() / scaleX);
                    int blockHeight = (int) (currentMapBlock.image.getHeight() / scaleY);
                    g2.drawImage(currentMapBlock.image, blockX, blockY, blockWidth, blockHeight, null);
                }
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
