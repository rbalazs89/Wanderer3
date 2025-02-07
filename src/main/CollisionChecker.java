package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class CollisionChecker {
    //TODO maxWorldRow on the current map not global
    GamePanel gp;
    //ArrayList<Monster> interactingWithPlayer = new ArrayList<>();
    public boolean interactableCameInRange = false;
    public SuperObject currentInteractableObject;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkMapEnd(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        if(entityTopWorldY < 0){
            entity.worldY = entity.worldY + entity.speed + 1;
            return;
        }
        if(entityBottomWorldY > GamePanel.tileSize * gp.currentMapMaxRow - 3 * entity.speed){
            entity.worldY = entity.worldY - entity.speed - 1;
            return;
        }
        if (entityLeftWorldX < 0) {
            entity.worldX = entity.worldX + entity.speed + 1;
            return;
        }
        if(entityRightWorldX > GamePanel.tileSize * gp.currentMapMaxCol - 3 * entity.speed) {
            entity.worldX = entity.worldX - entity.speed - 1;
        }
    }

    //player and entity collision with environment:
    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / GamePanel.tileSize;
        int entityRightCol = entityRightWorldX / GamePanel.tileSize;
        int entityTopRow = entityTopWorldY / GamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / GamePanel.tileSize;

        int tileNum1, tileNum2;

        if(entityTopWorldY < 0){
            entity.worldY = entity.worldY + entity.speed + 1;
            return;
        }
        if(entityBottomWorldY > GamePanel.tileSize * gp.currentMapMaxRow - 3 * entity.speed){
            entity.worldY = entity.worldY - entity.speed - 1;
            return;
        }
        if (entityLeftWorldX < 0) {
            entity.worldX = entity.worldX + entity.speed + 1;
            return;
        }
        if(entityRightWorldX > GamePanel.tileSize * gp.currentMapMaxCol - 3 * entity.speed) {
            entity.worldX = entity.worldX - entity.speed - 1;
            return;
        }

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / GamePanel.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                if (entity instanceof Player) {
                    if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldX += Math.ceil(gp.player.speed / 2);
                    } else if (!gp.tileM.tile[tileNum1].collision && gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldX -= Math.ceil(gp.player.speed / 2);
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / GamePanel.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                if (entity instanceof Player) {
                    if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldX += Math.ceil(gp.player.speed / 2);
                    } else if (!gp.tileM.tile[tileNum1].collision && gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldX -= Math.ceil(gp.player.speed / 2);
                    }

                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / GamePanel.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                if (entity instanceof Player) {
                    if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldY += Math.ceil(gp.player.speed / 2);
                    } else if (!gp.tileM.tile[tileNum1].collision && gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldY -= Math.ceil(gp.player.speed / 2);
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / GamePanel.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                if (entity instanceof Player) {
                    if (gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldY += Math.ceil(gp.player.speed / 2);
                    } else if (!gp.tileM.tile[tileNum1].collision && gp.tileM.tile[tileNum2].collision) {
                        gp.player.worldY -= Math.ceil(gp.player.speed / 2);
                    }
                }
            }
        }
    }

    // object collision with player and with entity:
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for(int i = 0; i < gp.obj[1].length; i ++){
            if(gp.obj[gp.currentMap][i] != null){
                int solidAreaDefaultXOBJ = gp.obj[gp.currentMap][i].solidArea.x;
                int solidAreaDefaultYOBJ = gp.obj[gp.currentMap][i].solidArea.y;
                int solidAreaDefaultX = entity.solidArea.x;
                int solidAreaDefaultY = entity.solidArea.y;

                // GET entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // GET the objects solid area position
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }

                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if(gp.obj[gp.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player){
                        index = i;
                    }
                }

                //reset position:
                entity.solidArea.x = solidAreaDefaultX;
                entity.solidArea.y = solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = solidAreaDefaultXOBJ;
                gp.obj[gp.currentMap][i].solidArea.y = solidAreaDefaultYOBJ;
            }
        }
        return index;
    }

    //TODO this is called multiple times in one frame, method could be separated and made less resource intensive
    public SuperObject checkObjectToInteract(){
        ArrayList<SuperObject> interactables = new ArrayList<>();
        for (int i = 0; i < gp.interactObjects.size(); i++) {
            SuperObject current = gp.interactObjects.get(i);
            if(current.interactable
            && Math.abs(current.worldMiddleX() - gp.player.worldMiddleX()) < GamePanel.tileSize
            && Math.abs(current.worldMiddleY() - gp.player.worldMiddleY()) < GamePanel.tileSize){
                interactables.add(gp.interactObjects.get(i));
            }
        }

        for (int i = 0; i < interactables.size(); i++) {
            SuperObject current = gp.interactObjects.get(i);
            int deltaY = current.worldY - gp.player.worldY;
            int deltaX = current.worldX - gp.player.worldX;
            current.distanceFromPlayer = (int)Math.sqrt(deltaY * deltaY + deltaX * deltaX);
        }


        Collections.sort(interactables, new Comparator<SuperObject>() {
            @Override
            public int compare(SuperObject o1, SuperObject o2) {
                return Integer.compare(o1.distanceFromPlayer, o2.distanceFromPlayer);
            }
        });

        //order by distance end

        if(currentInteractableObject != null && interactables.isEmpty()){
            currentInteractableObject.showGlitter = false;
        }

        if(!interactables.isEmpty()){
            interactableCameInRange = true;
            if(currentInteractableObject != null && currentInteractableObject != interactables.get(0)){
                currentInteractableObject.showGlitter = false;
            }
            currentInteractableObject = interactables.get(0);
            interactables.get(0).showGlitter = true;
        }
        if(!interactables.isEmpty()){
            return interactables.get(0);
        }
        return null;
    }

    //NPC OR MONSTER COLLISION WITH PLAYER
    /*
    public int checkEntity(Entity entity, Entity[][] target){
        int index = 999;

        for(int i = 0; i < target[1].length; i ++){
            if(target[gp.currentMap][i] != null){

                // GET entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // GET the objects solid area position
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea) && target[gp.currentMap][i] != entity){
                    if(target[gp.currentMap][i].collisionEntity) {
                        entity.collisionOn = true;
                    }
                    index = i;
                }
                //reset position:
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }*/

    public boolean dropItemTileCollisionCheck(int worldX, int worldY){
        int x1 = Math.min(Math.max((worldX + 16) / 64,0),gp.currentMapMaxCol - 1);
        int x2 = Math.max(Math.min((worldX + 48) / 64,gp.currentMapMaxCol - 1),0);
        int y1 = Math.min(Math.max((worldY + 16) / 64,0), gp.currentMapMaxRow - 1);
        int y2 = Math.max(Math.min((worldY + 48) / 64,gp.currentMapMaxRow - 1),0);
        int tileNum1 = gp.tileM.mapTileNum[x1][y1];
        int tileNum2 = gp.tileM.mapTileNum[x1][y2];
        int tileNum3 = gp.tileM.mapTileNum[x2][y1];
        int tileNum4 = gp.tileM.mapTileNum[x2][y2];
        return !gp.tileM.tile[tileNum1].collision && !gp.tileM.tile[tileNum2].collision && !gp.tileM.tile[tileNum3].collision && !gp.tileM.tile[tileNum4].collision;
    }

    //entity collision with player:
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        // GET entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // GET the objects solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }

        if(entity.solidArea.intersects(gp.player.solidArea)) {
            if (entity.collisionEntity) {
                entity.collisionOn = true;
            }
            contactPlayer = true;
        }

        //reset position:
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }
}