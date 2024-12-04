package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    //Rectangle eventRect;

    public EventHandler (GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.currentMapMaxCol][gp.currentMapMaxRow];
        int col = 0;
        int row = 0;
        while(col < gp.currentMapMaxCol && row < gp.currentMapMaxRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = gp.tileSize/2-1;
            eventRect[col][row].y = gp.tileSize/2-1;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.currentMapMaxCol){
                col = 0;
                row ++;
            }

        }

        /*eventRect = new Rectangle();
        eventRect.x = gp.tileSize / 2 - 1;
        eventRect.y = gp.tileSize / 2 - 1;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;*/
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;

            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }

    public void checkEvent() {

        //CHECK if the player character is more than 1 tile away form the last event:
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldX - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent) {
            if (hit(26, 25, "any")) {
                damagePit(26, 25, gp.playState);
            }
        }
    }

    /*public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol * gp.tileSize + eventRect.x;
        eventRect.y = eventRow * gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)) {
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }*/

    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.g2.setColor(Color.YELLOW);
        gp.ui.g2.drawString("pitfall", 200,200);
        gp.player.life -= -10;
        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
        }
    }
}
