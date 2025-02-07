package tile;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class DecorManager {
    GamePanel gp;
    public int[][] mapLocationNum;
    Decor[] images = new Decor[300];
    public DecorManager(GamePanel gp) {
        this.gp = gp;
        getDecorImage();
        getTileManagerDataCurrentMap(gp.currentMap);
    }

    private void getDecorImage() {
        setup2(1,"act1/flower1", true);
        setup2(2,"act1/flower2", true);
        setup2(8,"act1/rock1", true);
        setup2(9,"act1/rock2", true);
        setup2(10,"act1/rock3", true);
        setup2(11,"act1/rock4", true);
        setup2(12,"act1/rock5", true);
        setup2(13,"act1/rock6", true);
        setup2(15,"act1/cemeteryrocks", true);
        setup2(16,"act1/cemeteryrocks2", true);
        setup2(17,"act1/tombstone1", true);
        setup2(18,"act1/tombstone2", true);
        setup2(19,"act1/tombstone3", true);
        setup2(20,"act1/tombstone4", true);
        //setup2(21, "act1/grassgood1", true);
        setup2(22, "act1/flower_garden7", true);
        setup2(23, "act1/flower_garden8", true);
        setup2(24, "act1/flower_garden9", true);
        setup2(25, "act1/flower_garden10", true);
        setup2(26, "act1/flower_garden1", true);
        setup2(27, "act1/flower_garden2", true);
        setup2(28, "act1/flower_garden3", true);
        setup2(29, "act1/flower_garden4", true);
        setup2(30, "act1/flower_garden5", true);
        setup2(31, "act1/flower_garden6", true);
    }

    public void getTileManagerDataCurrentMap(int mapNumber){
        //
        if (gp.currentMap == 1 || gp.currentMap == 2 || gp.currentMap == 3 || gp.currentMap == 4 || gp.currentMap == 6 || gp.currentMap == 7) {
            mapLocationNum = new int[gp.currentMapMaxCol][gp.currentMapMaxRow];
            loadCurrentMapDecor("/decormap/decor" + mapNumber + ".txt");
        }
    }

    private void loadCurrentMapDecor(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.currentMapMaxCol && row < gp.currentMapMaxRow){
                String line = br.readLine();
                while(col < gp.currentMapMaxCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapLocationNum[col][row] = num;
                    col ++;
                }
                if(col == gp.currentMapMaxCol){
                    col = 0;
                    row ++;
                }

            }
            br.close();
        }
        catch (Exception ignored){

        }
    }

    public void draw(Graphics2D g2) {
        if (gp.currentMap == 1) {
            int worldCol = 0;
            int worldRow = 0;
            while (worldCol < gp.currentMapMaxCol && worldRow < gp.currentMapMaxRow) {
                int tileNum = mapLocationNum[worldCol][worldRow];

                if (tileNum != 0) {
                    int worldX = worldCol * GamePanel.tileSize;
                    int worldY = worldRow * GamePanel.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;
                    Decor currentDecor = images[tileNum];

                    if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                            worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY) {
                        if(currentDecor.allowRandomPosWithinTile){
                            g2.drawImage(currentDecor.image, screenX + currentDecor.posXWithinTile, screenY + currentDecor.posYWithinTile, null);
                        } else {
                            g2.drawImage(currentDecor.image, screenX, screenY, null);
                        }
                    }
                }
                worldCol++;
                if (worldCol == gp.currentMapMaxCol) {
                    worldCol = 0;
                    worldRow++;
                }
            }
        }
    }

    public void setup(int index, String imagePath){
        UtilityTool uTool = new UtilityTool();
        images[index] = new Decor();
        try {
            // noinspection ConstantConditions
            images[index].image = ImageIO.read(getClass().getResourceAsStream("/decor/" + imagePath +".png"));
            images[index].image = uTool.scaleImage(images[index].image, GamePanel.tileSize, GamePanel.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //same but no resize
    public void setup2(int index, String imagePath, boolean allowRandomWithinTile){
        Decor decor = new Decor();
        Random random = new Random();
        decor.allowRandomPosWithinTile = allowRandomWithinTile;

        images[index] = decor;
        try {
            // noinspection ConstantConditions
            images[index].image = ImageIO.read(getClass().getResourceAsStream("/decor/" + imagePath +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        if(allowRandomWithinTile){
            decor.posYWithinTile = random.nextInt(GamePanel.tileSize - decor.image.getWidth() + 1);
            decor.posXWithinTile = random.nextInt(GamePanel.tileSize - decor.image.getHeight() + 1);
        }
    }
}
