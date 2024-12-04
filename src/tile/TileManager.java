package tile;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public boolean drawPath = false;
    public BufferedImage tileSheetImage;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[999];
        getTileImage();
        //getTileImageFromSheetAct1(); didnt work well, better load time, but draw method is gives black lines sometimes
        getTileManagerDataCurrentMap(gp.currentMap);
    }

    public void getTileManagerDataCurrentMap(int mapNumber){
        getMapDimensions("/maps/map" + mapNumber + ".txt");
        mapTileNum = new int[gp.currentMapMaxCol][gp.currentMapMaxRow];
        loadMap("/maps/map" + mapNumber + ".txt");
    }

    public void getTileImage() {
        //setup(0, "act1/grass2", false);
        setup2(0, "act1/grass", false); //1
        setup(1,"act1/grass_wall", true); //2
        setup(2,"act1/Ggrass_2", true); //1
        setup(3,"act1/water_1", true); // 3
        setup(4,"act1/water_2", true); // 4
        setup(5,"act1/water_3", true); // 5
        setup(6,"act1/water_4", true); // 6
        setup(7,"act1/grasswater_1", true); // 7
        setup(8,"act1/grasswater_2", true); // 8
        setup(9,"act1/grasswater_3", true); // 9
        setup(10,"act1/grasswater_4", true); // 10
        setup(11,"act1/grasswater_5", true); // 11
        setup(12,"act1/grasswater_6", true); // 12
        setup(13,"act1/grasswater_7", true); // 13
        setup(14,"act1/grasswater_8", true); // 14
        setup(15,"act1/grasswateredge_1", true); // 15
        setup(16,"act1/grasswateredge_2", true); // 16
        setup(17,"act1/grasswateredge_3", true); // 17
        setup(18,"act1/grasswateredge_4", true); // 18
        setup(19,"act1/grasswater_7", false); // 13
        setup(20,"act1/grasswater_3", false); // 9
        setup(21,"act1/grassroad", false); // 19
        setup(22,"act1/grassdirt_1", false); // 20th 21th
        setup(23,"act1/grassdirt_3", false); // 22
        setup(24,"act1/grassdirt_4", false); // 23
        setup(25,"act1/grassdirt_5", false); // 24
        setup(26,"act1/grassdirt_6", false); // 25
        setup(27,"act1/grassdirt_7", false); // 26
        setup(28,"act1/dirt", false); // 27
        setup(29,"act1/dirt", true); // 27
        setup(30,"act1/rock_1",false); // delete
        setup(31,"act1/rock_2",false); // delete
        setup(32,"act1/rock_3",false); // delete
        setup(33,"act1/rock_4",false); // delete
        setup2(34,"act1/cave", false); // 28
        setup(35,"act1/grassrock1", true); // 29
        setup(36,"act1/grassrock2", true); // 30th
        setup(37,"act1/grassdirt_edge1",false);  //31
        setup(38,"act1/grassdirt_edge2", false); // 32
        setup(39,"act1/grassdirt_edge3", false); // 33
        setup(40,"act1/grassdirt_edge4", false); // 34
        setup(41,"act1/grassroad", true); // duplicate but collision different // 19
        setup(42,"act1/fence1", true); // 35
        setup(43,"act1/fence2", true); // 36
        setup(44,"act1/fenceedge1", true); // 37
        setup(45,"act1/fenceedge2", true); // 38
        setup(46,"act1/fenceedge3", true); // 39
        setup(47,"act1/fenceedge4", true); // 40th
        setup(48, "act1/templetile", false);
        setup(49, "act1/templetile", true);
        setup(50, "act1/churchbench_left", true);
        setup(51, "act1/churchbench_right", true);
        setup(52, "act1/church_wall", true);
        setup2(53, "act1/stoneside", true);
        setup2(54, "act1/stonetop", true);
        setup2(55, "act1/stoneside_left", true);
        setup2(56, "act1/carpetedge1", false); // not used
        setup2(57, "act1/carpetedge2", false); // not used
        setup2(58, "act1/carpetedge3", false); // not used
        setup2(59, "act1/carpetedge4", false); // not used
        setup2(60, "act1/carpetleft", false);
        setup2(61, "act1/carpetright", false);
        setup2(62, "act1/wood", false); // 50th
        setup2(63, "act1/woodside_left", true);
        setup2(64, "act1/woodside_right", true);
        setup2(65, "act1/woodside_top", true);
        setup2(66, "act1/woodside_down", true);
        setup2(67, "act1/wood", true); // duplicate diff collision 5 10
        setup2(68, "act1/cellar", false);
        setup2(69, "act1/stonewall_1", true);
        setup2(70, "act1/stonewall_2", true);
        setup2(71, "act1/stonedesk_1", true);
        setup2(72, "act1/stonedesk_2", true);
        //setup2(73, "act1/stonedesk_1", true); // duplicate mistake
        setup2(74, "act1/stonebarrel", true); // 60th
        setup2(75, "act1/stonesmalldesk", true);
        setup(76, "act1/pebble", true); // not used
        setup2(77, "act1/wateredge1", true);
        setup2(78, "act1/wateredge2", true);
        setup2(79, "act1/wateredge3", true);
        setup2(80, "act1/wateredge4", true);
        setup2(81, "act1/wateredge5", true);
        setup2(82, "act1/wateredge6", true);
        setup2(83, "act1/wateredge7", true);
        setup2(84, "act1/wateredge8", true);
        setup2(85, "act1/cavewater", true); // 70th
        setup2(86, "act1/wateredge3", false); // duplicate for collision 79
        setup2(87, "act1/wateredge7", false); // duplicate for collision 83
        setup2(88, "act1/cavestairs", false);
        setup2(89, "act1/cave_a", true);
        setup2(90, "act1/cave_b", true);
        setup2(91, "act1/cave_c", true);
        setup2(92, "act1/cave_d", true);
        setup2(93, "act1/stonetop_nowall", true); // end row skip
        setup2(94, "act1/stonetop_nowall2", true); // end row skip
        setup2(95, "act1/stonetop_nowall3", true); // end row skip
        setup2(96, "act1/cave", true); // duplicate for collision
        setup2(97, "act1/bosstile2", false); // not used
        setup2(98, "act1/bosstile3", false);
        //skip 99
        setup(99, "nullimage", false); // endblock
        setup2(100, "act1/bosswall", true);
        setup2(101, "act1/bosswall_side_left", true);
        setup2(102, "act1/bosswall_side_right", true);
        setup2(103, "act1/bosswall_side_bottom", true); //80th
        setup2(104, "act1/totemtile", false);
    }

    public void getTileImageFromSheetAct1(){
        tileSheetImage = setup("act1/tileSheet");
        setupTileFromSheetAct1(0, 1, 1, false);
        setupTileFromSheetAct1(1, 1, 2, true);
        setupTileFromSheetAct1(2, 1, 1, true);
        setupTileFromSheetAct1(3, 1, 3, true);
        setupTileFromSheetAct1(4, 1, 4, true);
        setupTileFromSheetAct1(5, 1, 5, true);
        setupTileFromSheetAct1(6, 1, 6, true);
        setupTileFromSheetAct1(7, 1, 7, true);
        setupTileFromSheetAct1(8, 1, 8, true);
        setupTileFromSheetAct1(9, 1, 9, true);
        setupTileFromSheetAct1(10, 1, 10, true);
        setupTileFromSheetAct1(11, 2, 1, true);
        setupTileFromSheetAct1(12, 2, 2, true);
        setupTileFromSheetAct1(13, 2, 3, true);
        setupTileFromSheetAct1(14, 2, 4, true);
        setupTileFromSheetAct1(15, 2, 5, true);
        setupTileFromSheetAct1(16, 2, 6, true);
        setupTileFromSheetAct1(17, 2, 7, true);
        setupTileFromSheetAct1(18, 2, 8, true);
        setupTileFromSheetAct1(19, 2, 3, false);
        setupTileFromSheetAct1(20, 1, 9, false);
        setupTileFromSheetAct1(21, 2, 9, false);
        setupTileFromSheetAct1(22, 2, 10, false);
        setupTileFromSheetAct1(23, 3, 2, false);
        setupTileFromSheetAct1(24, 3, 3, false);
        setupTileFromSheetAct1(25, 3, 4, false);
        setupTileFromSheetAct1(26, 3, 5, false);
        setupTileFromSheetAct1(27, 3, 6, false);
        setupTileFromSheetAct1(28, 3, 7, false);
        setupTileFromSheetAct1(29, 3, 7, true);
        setupTileFromSheetAct1(34, 3, 8, false);
        setupTileFromSheetAct1(35, 3, 9, true);
        setupTileFromSheetAct1(36, 3, 10, true);
        setupTileFromSheetAct1(37, 4, 1, false);
        setupTileFromSheetAct1(38, 4, 2, false);
        setupTileFromSheetAct1(39, 4, 3, false);
        setupTileFromSheetAct1(40, 4, 4, false);
        setupTileFromSheetAct1(41, 2, 9, true);
        setupTileFromSheetAct1(42, 4, 5, true);
        setupTileFromSheetAct1(43, 4, 6, true);
        setupTileFromSheetAct1(44, 4, 7, true);
        setupTileFromSheetAct1(45, 4, 8, true);
        setupTileFromSheetAct1(46, 4, 9, true);
        setupTileFromSheetAct1(47, 4, 10, true);
        setupTileFromSheetAct1(48, 5, 1, false);
        setupTileFromSheetAct1(49, 5, 1, true);
        setupTileFromSheetAct1(50, 5, 2, true);
        setupTileFromSheetAct1(51, 5, 3, true);
        setupTileFromSheetAct1(52, 5, 4, true);
        setupTileFromSheetAct1(53, 5, 5, true);
        setupTileFromSheetAct1(54, 5, 6, true);
        setupTileFromSheetAct1(55, 5, 7, true);
        setupTileFromSheetAct1(60, 5, 8, false);
        setupTileFromSheetAct1(61, 5, 9, false);
        setupTileFromSheetAct1(62, 5, 10, false);
        setupTileFromSheetAct1(63, 6, 1, true);
        setupTileFromSheetAct1(64, 6, 2, true);
        setupTileFromSheetAct1(65, 6, 3, true);
        setupTileFromSheetAct1(66, 6, 4, true);
        setupTileFromSheetAct1(67, 5, 10, true);
        setupTileFromSheetAct1(68, 6, 5, false);
        setupTileFromSheetAct1(69, 5, 6, true);
        setupTileFromSheetAct1(70, 5, 7, true);
        setupTileFromSheetAct1(71, 5, 8, true);
        setupTileFromSheetAct1(72, 5, 9, true);
        setupTileFromSheetAct1(74, 5, 10, true);
        setupTileFromSheetAct1(75, 6, 1, true);
        setupTileFromSheetAct1(77, 6, 2, true);
        setupTileFromSheetAct1(78, 6, 3, true);
        setupTileFromSheetAct1(79, 6, 4, true);
        setupTileFromSheetAct1(80, 6, 5, true);
        setupTileFromSheetAct1(81, 6, 6, true);
        setupTileFromSheetAct1(82, 6, 7, true);
        setupTileFromSheetAct1(83, 6, 8, true);
        setupTileFromSheetAct1(84, 6, 9, true);
        setupTileFromSheetAct1(85, 6, 10, true);
        setupTileFromSheetAct1(86, 6, 4, false);
        setupTileFromSheetAct1(87, 6, 8, false);
        setupTileFromSheetAct1(88, 7, 1, false);
        setupTileFromSheetAct1(89, 7, 2, true);
        setupTileFromSheetAct1(90, 7, 3, true);
        setupTileFromSheetAct1(91, 7, 4, true);
        setupTileFromSheetAct1(92, 7, 5, true);
        setupTileFromSheetAct1(96, 3, 8, true);
        setupTileFromSheetAct1(98, 7, 6, false);
        setupTileFromSheetAct1(99, 8, 10, false);
        setupTileFromSheetAct1(100, 7, 7, true);
        setupTileFromSheetAct1(101, 7, 8, true);
        setupTileFromSheetAct1(102, 7, 9, true);
        setupTileFromSheetAct1(103, 7, 10, true);
        setupTileFromSheetAct1(104, 8, 1, false);
        setupTileFromSheetAct1(93, 8, 2, true);
        setupTileFromSheetAct1(94, 8, 3, true);
        setupTileFromSheetAct1(95, 8, 4, true);
    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setupTileFromSheetAct1(int index, int row, int column, boolean collision){
        tile[index] = new Tile();
        BufferedImage subImage = tileSheetImage.getSubimage((column - 1) * 64,(row - 1) * 64,64,64);
        tile[index].image = subImage;
        tile[index].collision = collision;
    }

    public BufferedImage setup(String imagePath){
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath +".png"));
            return image;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setup2(int index, String imagePath, boolean collision){
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath +".png"));
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.currentMapMaxCol && row < gp.currentMapMaxRow){
                String line = br.readLine();
                while(col < gp.currentMapMaxCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col ++;
                }
                if(col == gp.currentMapMaxCol){
                    col = 0;
                    row ++;
                }

            }
            br.close();
        }
        catch (Exception e){

        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;
        while(worldCol < gp.currentMapMaxCol && worldRow < gp.currentMapMaxRow){

            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;
            if(worldCol == gp.currentMapMaxCol) {
                worldCol = 0;
                worldRow ++;
            }
        }
        if(drawPath){
            g2.setColor(new Color(255,0,0,70));
            for(int i = 0; i < gp.pFinder.pathList.size(); i ++){
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }

    public void getMapDimensions(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            if (line != null) {
                gp.currentMapMaxCol = line.split(" ").length;
            }
            int rowCount = 0;
            while (line != null) {
                rowCount++;
                line = br.readLine();
            }
            gp.currentMapMaxRow = rowCount;

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
