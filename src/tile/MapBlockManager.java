package tile;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

//class
public class MapBlockManager {
    GamePanel gp;
    MapBlock[] mapBlock = new MapBlock[100];
    public BufferedImage[] savedImages;

    public MapBlockManager(GamePanel gp) {
        this.gp = gp;
        blockSetter();
        savedImages = new BufferedImage[100];
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        for (int i = 0; i < mapBlock.length; i++) {
            //if (mapBlock[i] != null) {
            if(mapBlock[i] == null) {
                return;
            }
            if(mapBlock[i].image != null){
                    int blockWidth = mapBlock[i].image.getWidth();
                    int blockHeight = mapBlock[i].image.getHeight();

                    int screenX = mapBlock[i].worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = mapBlock[i].worldY - gp.player.worldY + gp.player.screenY;

                    if (mapBlock[i].worldX + blockWidth > gp.player.worldX - gp.player.screenX &&
                            mapBlock[i].worldX   < gp.player.worldX + gp.player.screenX + GamePanel.tileSize &&
                            mapBlock[i].worldY + blockHeight > gp.player.worldY - gp.player.screenY &&
                            mapBlock[i].worldY  < gp.player.worldY + gp.player.screenY + GamePanel.tileSize) {
                        g2.drawImage(mapBlock[i].image, screenX, screenY, null);
                    }
               // }
            }
        }
    }

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            // noinspection ConstantConditions
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update(){ //need to update at every map change
        clearArray();
        blockSetter();
    }

    public void clearArray(){
        Arrays.fill(mapBlock, null);
    }

    public void blockSetter(){
        switch (gp.currentMap){
            case 1:
                setMap1();
                break;
            case 2:
                break;
            case 3:
                setMap3();
//                mapBlock[0] = new MapBlock();
//                mapBlock[0].worldX = 2624;
//                mapBlock[0].worldY = 2400;
//                mapBlock[0].image = setup("/mapblock/a1/tortoiseden", GamePanel.tileSize * 3, GamePanel.tileSize * 2);
//
//                mapBlock[1] = new MapBlock();
//                mapBlock[1].worldX = 38 * GamePanel.tileSize;
//                mapBlock[1].worldY = 28 * GamePanel.tileSize;
//                mapBlock[1].image = setup("/mapblock/a1/hole", GamePanel.tileSize, GamePanel.tileSize / 2);

                break;
            case 5:
                setMap5();
                break;
            case 6:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 10 * GamePanel.tileSize;
                mapBlock[0].worldY = (int)(0.5 * GamePanel. tileSize);
                mapBlock[0].image = setup("/mapblock/a1/church_window");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 3 * GamePanel.tileSize;
                mapBlock[1].worldY = (int)(0.5 * GamePanel. tileSize);
                mapBlock[1].image = setup("/mapblock/a1/church_window");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = GamePanel.tileSize;
                mapBlock[2].worldY = 5 * GamePanel.tileSize;
                mapBlock[2].image = setup("/mapblock/a1/church_candle");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = GamePanel.tileSize;
                mapBlock[3].worldY = 7 * GamePanel.tileSize;
                mapBlock[3].image = setup("/mapblock/a1/church_candle");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = GamePanel.tileSize;
                mapBlock[4].worldY = 9 * GamePanel.tileSize;
                mapBlock[4].image = setup("/mapblock/a1/church_candle");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = GamePanel.tileSize;
                mapBlock[5].worldY = 11 * GamePanel.tileSize;
                mapBlock[5].image = setup("/mapblock/a1/church_candle");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = GamePanel.tileSize;
                mapBlock[6].worldY = 13 * GamePanel.tileSize;
                mapBlock[6].image = setup("/mapblock/a1/church_candle");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = GamePanel.tileSize;
                mapBlock[7].worldY = 15 * GamePanel.tileSize;
                mapBlock[7].image = setup("/mapblock/a1/church_candle");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = (int)(12.5 * GamePanel.tileSize);
                mapBlock[8].worldY = 5 * GamePanel.tileSize;
                mapBlock[8].image = setup("/mapblock/a1/church_candle2");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = (int)(12.5 * GamePanel.tileSize);
                mapBlock[9].worldY = 7 * GamePanel.tileSize;
                mapBlock[9].image = setup("/mapblock/a1/church_candle2");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = (int)(12.5 * GamePanel.tileSize);
                mapBlock[10].worldY = 9 * GamePanel.tileSize;
                mapBlock[10].image = setup("/mapblock/a1/church_candle2");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = (int)(12.5 * GamePanel.tileSize);
                mapBlock[11].worldY = 11 * GamePanel.tileSize;
                mapBlock[11].image = setup("/mapblock/a1/church_candle2");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = (int)(12.5 * GamePanel.tileSize);
                mapBlock[12].worldY = 13 * GamePanel.tileSize;
                mapBlock[12].image = setup("/mapblock/a1/church_candle2");

                mapBlock[13] = new MapBlock();
                mapBlock[13].worldX = (int)(12.5 * GamePanel.tileSize);
                mapBlock[13].worldY = 15 * GamePanel.tileSize;
                mapBlock[13].image = setup("/mapblock/a1/church_candle2");
                break;

            case 7:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = -1 * GamePanel.tileSize;
                mapBlock[0].worldY = 5 * GamePanel. tileSize;
                mapBlock[0].image = setup("/mapblock/a1/woodstairs");
                break;

            case 8:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 5 * GamePanel.tileSize;
                mapBlock[0].worldY = (3 * GamePanel. tileSize);
                mapBlock[0].image = setup("/mapblock/a1/desk2");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 3 * GamePanel.tileSize;
                mapBlock[1].worldY = (int) (0.5 * GamePanel. tileSize);
                mapBlock[1].image = setup("/mapblock/a1/shopwindow");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 9 * GamePanel.tileSize;
                mapBlock[2].worldY = (int) (0.5 * GamePanel. tileSize);
                mapBlock[2].image = setup("/mapblock/a1/shopwindow");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = GamePanel.tileSize;
                mapBlock[3].worldY = 5 * GamePanel. tileSize;
                mapBlock[3].image = setup("/mapblock/a1/benchside");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 11 * GamePanel.tileSize;
                mapBlock[4].worldY = (int) (1.5 * GamePanel. tileSize);
                mapBlock[4].image = setup("/mapblock/a1/fireplacewood");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = GamePanel.tileSize;
                mapBlock[5].worldY = 8 * GamePanel. tileSize;
                mapBlock[5].image = setup("/mapblock/a1/benchside");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = GamePanel.tileSize;
                mapBlock[6].worldY = 8 * GamePanel. tileSize;
                mapBlock[6].image = setup("/mapblock/a1/benchside");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 4 * GamePanel.tileSize;
                mapBlock[7].worldY = 7 * GamePanel. tileSize;
                mapBlock[7].image = setup("/mapblock/a1/bluecarpet");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = (int)(12.6 * GamePanel.tileSize);
                mapBlock[8].worldY = 4 * GamePanel. tileSize;
                mapBlock[8].image = setup("/mapblock/a1/showcaseside");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = 2 * GamePanel.tileSize;
                mapBlock[9].worldY = 3 * GamePanel. tileSize;
                mapBlock[9].image = setup("/mapblock/a1/desk");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = 9 * GamePanel.tileSize;
                mapBlock[10].worldY = 3 * GamePanel. tileSize;
                mapBlock[10].image = setup("/mapblock/a1/rack");
                break;
            case 9:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = GamePanel.tileSize;
                mapBlock[0].worldY = 3 * GamePanel. tileSize;
                mapBlock[0].image = setup("/mapblock/a1/woodentrapdoor");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 5 * GamePanel.tileSize;
                mapBlock[1].worldY = (int)(1.1 * GamePanel. tileSize);
                mapBlock[1].image = setup("/mapblock/a1/bookcase");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 8 * GamePanel.tileSize;
                mapBlock[2].worldY = 2 * GamePanel. tileSize;
                mapBlock[2].image = setup("/mapblock/a1/bookcase2");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 12 * GamePanel.tileSize;
                mapBlock[3].worldY = 3 * GamePanel. tileSize;
                mapBlock[3].image = setup("/mapblock/a1/bluebed");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 12 * GamePanel.tileSize;
                mapBlock[4].worldY = 6 * GamePanel. tileSize;
                mapBlock[4].image = setup("/mapblock/a1/redbed");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = GamePanel.tileSize;
                mapBlock[5].worldY = 9 * GamePanel. tileSize;
                mapBlock[5].image = setup("/mapblock/a1/doublebed");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 7 * GamePanel.tileSize;
                mapBlock[6].worldY = 6 * GamePanel. tileSize;
                mapBlock[6].image = setup("/mapblock/a1/fruitdesk");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 6 * GamePanel.tileSize;
                mapBlock[7].worldY = (int)(6.5 * GamePanel. tileSize);
                mapBlock[7].image = setup("/mapblock/a1/smallchair");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = 8 * GamePanel.tileSize;
                mapBlock[8].worldY = (int)(6.5 * GamePanel. tileSize);
                mapBlock[8].image = setup("/mapblock/a1/smallchair");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = 4 * GamePanel.tileSize;
                mapBlock[9].worldY = 2 * GamePanel. tileSize;
                mapBlock[9].image = setup("/mapblock/a1/homecabinet");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = GamePanel.tileSize;
                mapBlock[10].worldY = 5 * GamePanel. tileSize;
                mapBlock[10].image = setup("/mapblock/a1/desk_home");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = 2 * GamePanel.tileSize;
                mapBlock[11].worldY = (int)(5.5 * GamePanel. tileSize);
                mapBlock[11].image = setup("/mapblock/a1/smallchair");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = 10 * GamePanel.tileSize;
                mapBlock[12].worldY = 9 * GamePanel. tileSize;
                mapBlock[12].image = setup("/mapblock/a1/cabinet");
                break;

            case 10:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 18 * GamePanel.tileSize;
                mapBlock[0].worldY = 13 * GamePanel.tileSize;
                mapBlock[0].image = setup("/mapblock/a1/hpdesk");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = GamePanel.tileSize;
                mapBlock[1].worldY = 2 * GamePanel.tileSize;
                mapBlock[1].image = setup("/mapblock/a1/angelstatueleft");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = GamePanel.tileSize;
                mapBlock[2].worldY = 5 * GamePanel.tileSize;
                mapBlock[2].image = setup("/mapblock/a1/angelstatueleft");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = GamePanel.tileSize;
                mapBlock[3].worldY = 8 * GamePanel.tileSize;
                mapBlock[3].image = setup("/mapblock/a1/angelstatueleft");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 10 * GamePanel.tileSize;
                mapBlock[4].worldY = 2 * GamePanel.tileSize;
                mapBlock[4].image = setup("/mapblock/a1/angelstatueright");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 10 * GamePanel.tileSize;
                mapBlock[5].worldY = 5 * GamePanel.tileSize;
                mapBlock[5].image = setup("/mapblock/a1/angelstatueright");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 10 * GamePanel.tileSize;
                mapBlock[6].worldY = 8 * GamePanel.tileSize;
                mapBlock[6].image = setup("/mapblock/a1/angelstatueright");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 5 * GamePanel.tileSize;
                mapBlock[7].worldY = 3 * GamePanel.tileSize;
                mapBlock[7].image = setup("/mapblock/a1/redcarpet");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = 5 * GamePanel.tileSize;
                mapBlock[8].worldY = 0;
                mapBlock[8].image = setup("/mapblock/a1/carpetstairs");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = 13 * GamePanel.tileSize;
                mapBlock[9].worldY = 8 * GamePanel.tileSize;
                mapBlock[9].image = setup("/mapblock/a1/bigcabinetmagic");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = 22 * GamePanel.tileSize;
                mapBlock[10].worldY = 12 * GamePanel.tileSize;
                mapBlock[10].image = setup("/mapblock/a1/magiccabinet");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = 23 * GamePanel.tileSize;
                mapBlock[11].worldY = (int)(19.6 * GamePanel.tileSize);
                mapBlock[11].image = setup("/mapblock/a1/potioncabinet");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = 22 * GamePanel.tileSize;
                mapBlock[12].worldY = 17 * GamePanel.tileSize;
                mapBlock[12].image = setup("/mapblock/a1/cauldron");
                break;

            case 11:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 4 * GamePanel.tileSize;
                mapBlock[0].worldY = GamePanel.tileSize;
                mapBlock[0].image = setup("/mapblock/a1/knightstatue");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 2 * GamePanel.tileSize;
                mapBlock[2].worldY = 0;
                mapBlock[2].image = setup("/mapblock/a1/bigsword");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 13 * GamePanel.tileSize;
                mapBlock[3].worldY = GamePanel.tileSize;
                mapBlock[3].image = setup("/mapblock/a1/crest");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 10 * GamePanel.tileSize;
                mapBlock[4].worldY = (int)(0.5 * GamePanel.tileSize);
                mapBlock[4].image = setup("/mapblock/a1/axes");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 11 * GamePanel.tileSize;
                mapBlock[5].worldY = GamePanel.tileSize;
                mapBlock[5].image = setup("/mapblock/a1/knightstatue");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 4 * GamePanel.tileSize;
                mapBlock[6].worldY = (int)(12.5 * GamePanel.tileSize);
                mapBlock[6].image = setup("/mapblock/a1/bluecarpet2");
                break;
            case 13:
                setMap13();
                break;
        }
    }
    public void setMap5(){
        mapBlock[0] = new MapBlock();
        mapBlock[0].worldX = 669;
        mapBlock[0].worldY = 44;
        mapBlock[0].image = setup("/mapblock/a1/window2");

        mapBlock[1] = new MapBlock();
        mapBlock[1].worldX = 297;
        mapBlock[1].worldY = 45;
        mapBlock[1].image = setup("/mapblock/a1/shopwindow");

        mapBlock[2] = new MapBlock();
        mapBlock[2].worldX = 105;
        mapBlock[2].worldY = 45;
        mapBlock[2].image = setup("/mapblock/a1/shopwindow");

        mapBlock[3] = new MapBlock();
        mapBlock[3].worldX = 900;
        mapBlock[3].worldY = 45;
        mapBlock[3].image = setup("/mapblock/a1/window2");

        mapBlock[4] = new MapBlock();
        mapBlock[4].worldX = 500;
        mapBlock[4].worldY = 100;
        mapBlock[4].image = setup("/mapblock/a1/fireplacewood");

        mapBlock[5] = new MapBlock();
        mapBlock[5].worldX = 1058;
        mapBlock[5].worldY = 173;
        mapBlock[5].image = setup("/mapblock/a1/insideflower3");

        mapBlock[6] = new MapBlock();
        mapBlock[6].worldX = 202;
        mapBlock[6].worldY = 186;
        mapBlock[6].image = setup("/mapblock/a1/insideflower3");

        mapBlock[7] = new MapBlock();
        mapBlock[7].worldX = 60;
        mapBlock[7].worldY = 118;
        mapBlock[7].image = setup("/mapblock/a1/homecabinet");

        mapBlock[8] = new MapBlock();
        mapBlock[8].worldX = 603;
        mapBlock[8].worldY = 204;
        mapBlock[8].image = setup("/mapblock/a1/smallchair3");

        mapBlock[9] = new MapBlock();
        mapBlock[9].worldX = 878;
        mapBlock[9].worldY = 204;
        mapBlock[9].image = setup("/mapblock/a1/smallchair3");

        mapBlock[10] = new MapBlock();
        mapBlock[10].worldX = 441;
        mapBlock[10].worldY = 222;
        mapBlock[10].image = setup("/mapblock/a1/smallchair3");

        mapBlock[11] = new MapBlock();
        mapBlock[11].worldX = 764;
        mapBlock[11].worldY = 224;
        mapBlock[11].image = setup("/mapblock/a1/smallchair3");

        mapBlock[12] = new MapBlock();
        mapBlock[12].worldX = 69;
        mapBlock[12].worldY = 273;
        mapBlock[12].image = setup("/mapblock/a1/insideflower1");

        mapBlock[13] = new MapBlock();
        mapBlock[13].worldX = 985;
        mapBlock[13].worldY = 275;
        mapBlock[13].image = setup("/mapblock/a1/sidechair");

        mapBlock[14] = new MapBlock();
        mapBlock[14].worldX = 320;
        mapBlock[14].worldY = 287;
        mapBlock[14].image = setup("/mapblock/a1/smallchair3");

        mapBlock[15] = new MapBlock();
        mapBlock[15].worldX = 384;
        mapBlock[15].worldY = 254;
        mapBlock[15].image = setup("/mapblock/a1/longdeskfood");

        mapBlock[16] = new MapBlock();
        mapBlock[16].worldX = 1061;
        mapBlock[16].worldY = 352;
        mapBlock[16].image = setup("/mapblock/a1/basket1");

        mapBlock[17] = new MapBlock();
        mapBlock[17].worldX = 1048;
        mapBlock[17].worldY = 392;
        mapBlock[17].image = setup("/mapblock/a1/basket1");

        mapBlock[18] = new MapBlock();
        mapBlock[18].worldX = 61;
        mapBlock[18].worldY = 370;
        mapBlock[18].image = setup("/mapblock/a1/insideflower2");

        mapBlock[19] = new MapBlock();
        mapBlock[19].worldX = 830;
        mapBlock[19].worldY = 368;
        mapBlock[19].image = setup("/mapblock/a1/smallchair3");

        mapBlock[20] = new MapBlock();
        mapBlock[20].worldX = 561;
        mapBlock[20].worldY = 372;
        mapBlock[20].image = setup("/mapblock/a1/smallchair3");

        mapBlock[21] = new MapBlock();
        mapBlock[21].worldX = 684;
        mapBlock[21].worldY = 381;
        mapBlock[21].image = setup("/mapblock/a1/smallchair3");

        mapBlock[22] = new MapBlock();
        mapBlock[22].worldX = 438;
        mapBlock[22].worldY = 388;
        mapBlock[22].image = setup("/mapblock/a1/smallchair3");

        mapBlock[23] = new MapBlock();
        mapBlock[23].worldX = 136;
        mapBlock[23].worldY = 465;
        mapBlock[23].image = setup("/mapblock/a1/smallchair2");

        mapBlock[24] = new MapBlock();
        mapBlock[24].worldX = 197;
        mapBlock[24].worldY = 465;
        mapBlock[24].image = setup("/mapblock/a1/smallchair2");

        mapBlock[25] = new MapBlock();
        mapBlock[25].worldX = 65;
        mapBlock[25].worldY = 470;
        mapBlock[25].image = setup("/mapblock/a1/smallchair2");

        mapBlock[26] = new MapBlock();
        mapBlock[26].worldX = 64;
        mapBlock[26].worldY = 513;
        mapBlock[26].image = setup("/mapblock/a1/3desk");

        mapBlock[27] = new MapBlock();
        mapBlock[27].worldX = 760;
        mapBlock[27].worldY = 521;
        mapBlock[27].image = setup("/mapblock/a1/bedsidecabinet");

        mapBlock[28] = new MapBlock();
        mapBlock[28].worldX = 811;
        mapBlock[28].worldY = 490;
        mapBlock[28].image = setup("/mapblock/a1/eatcabinet");

        mapBlock[29] = new MapBlock();
        mapBlock[29].worldX = 905;
        mapBlock[29].worldY = 456;
        mapBlock[29].image = setup("/mapblock/a1/smalldesk2");

        mapBlock[30] = new MapBlock();
        mapBlock[30].worldX = 1026;
        mapBlock[30].worldY = 609;
        mapBlock[30].image = setup("/mapblock/a1/toy6");

        mapBlock[31] = new MapBlock();
        mapBlock[31].worldX = 82;
        mapBlock[31].worldY = 574;
        mapBlock[31].image = setup("/mapblock/a1/smallchair");

        mapBlock[32] = new MapBlock();
        mapBlock[32].worldX = 1004;
        mapBlock[32].worldY = 678;
        mapBlock[32].image = setup("/mapblock/a1/toy7");

        mapBlock[33] = new MapBlock();
        mapBlock[33].worldX = 913;
        mapBlock[33].worldY = 743;
        mapBlock[33].image = setup("/mapblock/a1/toy4");

        mapBlock[34] = new MapBlock();
        mapBlock[34].worldX = 963;
        mapBlock[34].worldY = 749;
        mapBlock[34].image = setup("/mapblock/a1/toy5");

        mapBlock[35] = new MapBlock();
        mapBlock[35].worldX = 989;
        mapBlock[35].worldY = 813;
        mapBlock[35].image = setup("/mapblock/a1/toy3");

        mapBlock[36] = new MapBlock();
        mapBlock[36].worldX = 865;
        mapBlock[36].worldY = 826;
        mapBlock[36].image = setup("/mapblock/a1/toy2");

        mapBlock[37] = new MapBlock();
        mapBlock[37].worldX = 411;
        mapBlock[37].worldY = 660;
        mapBlock[37].image = setup("/mapblock/a1/bluecarpet");

        mapBlock[38] = new MapBlock();
        mapBlock[38].worldX = 937;
        mapBlock[38].worldY = 837;
        mapBlock[38].image = setup("/mapblock/a1/toy4");

        mapBlock[39] = new MapBlock();
        mapBlock[39].worldX = 1030;
        mapBlock[39].worldY = 844;
        mapBlock[39].image = setup("/mapblock/a1/toy7");

        mapBlock[40] = new MapBlock();
        mapBlock[40].worldX = 70;
        mapBlock[40].worldY = 697;
        mapBlock[40].image = setup("/mapblock/a1/bookshelf2");

        mapBlock[41] = new MapBlock();
        mapBlock[41].worldX = 158;
        mapBlock[41].worldY = 697;
        mapBlock[41].image = setup("/mapblock/a1/bookshelf3");

        mapBlock[42] = new MapBlock();
        mapBlock[42].worldX = 790;
        mapBlock[42].worldY = 880;
        mapBlock[42].image = setup("/mapblock/a1/toy1");

        mapBlock[43] = new MapBlock();
        mapBlock[43].worldX = 960;
        mapBlock[43].worldY = 893;
        mapBlock[43].image = setup("/mapblock/a1/toy6");

        mapBlock[44] = new MapBlock();
        mapBlock[44].worldX = 1060;
        mapBlock[44].worldY = 606;
        mapBlock[44].image = setup("/mapblock/a1/sidecabinet");

        mapBlock[45] = new MapBlock();
        mapBlock[45].worldX = 881;
        mapBlock[45].worldY = 926;
        mapBlock[45].image = setup("/mapblock/a1/toy3");

        mapBlock[46] = new MapBlock();
        mapBlock[46].worldX = 1026;
        mapBlock[46].worldY = 940;
        mapBlock[46].image = setup("/mapblock/a1/smallcabinet");
    }

    public void setMap1(){
        mapBlock[0] = new MapBlock();
        mapBlock[0].worldX = 1152;
        mapBlock[0].worldY = 64;
        mapBlock[0].image = setup("/mapblock/a1/barrel2");

        mapBlock[1] = new MapBlock();
        mapBlock[1].worldX = 0;
        mapBlock[1].worldY = 0;
        mapBlock[1].image = setup("/mapblock/a1/cave2");

        mapBlock[2] = new MapBlock();
        mapBlock[2].worldX = 768;
        mapBlock[2].worldY = 128;
        mapBlock[2].image = setup("/mapblock/a1/logs");

        mapBlock[3] = new MapBlock();
        mapBlock[3].worldX = 832;
        mapBlock[3].worldY = 64;
        mapBlock[3].image = setup("/mapblock/a1/tent_2");

        mapBlock[4] = new MapBlock();
        mapBlock[4].worldX = 512;
        mapBlock[4].worldY = 64;
        mapBlock[4].image = setup("/mapblock/a1/carriage");

        mapBlock[5] = new MapBlock();
        mapBlock[5].worldX = 2816;
        mapBlock[5].worldY = 128;
        mapBlock[5].image = setup("/mapblock/a1/starttent");

        mapBlock[6] = new MapBlock();
        mapBlock[6].worldX = 832;
        mapBlock[6].worldY = 320;
        mapBlock[6].image = setup("/mapblock/a1/3desk");

        mapBlock[7] = new MapBlock();
        mapBlock[7].worldX = 896;
        mapBlock[7].worldY = 384;
        mapBlock[7].image = setup("/mapblock/a1/smallchair2");

        mapBlock[8] = new MapBlock();
        mapBlock[8].worldX = 2498;
        mapBlock[8].worldY = 1012;
        mapBlock[8].image = setup("/mapblock/a1/playground2");

        mapBlock[9] = new MapBlock();
        mapBlock[9].worldX = 2375;
        mapBlock[9].worldY = 1089;
        mapBlock[9].image = setup("/mapblock/a1/playground6");

        mapBlock[10] = new MapBlock();
        mapBlock[10].worldX = 2552;
        mapBlock[10].worldY = 1118;
        mapBlock[10].image = setup("/mapblock/a1/playground5");

        mapBlock[11] = new MapBlock();
        mapBlock[11].worldX = 2368;
        mapBlock[11].worldY = 1262;
        mapBlock[11].image = setup("/mapblock/a1/playground4");

        mapBlock[12] = new MapBlock();
        mapBlock[12].worldX = 2555;
        mapBlock[12].worldY = 1273;
        mapBlock[12].image = setup("/mapblock/a1/playground1");

        mapBlock[13] = new MapBlock();
        mapBlock[13].worldX = 2560;
        mapBlock[13].worldY = 1405;
        mapBlock[13].image = setup("/mapblock/a1/playground3");

        mapBlock[14] = new MapBlock();
        mapBlock[14].worldX = 320;
        mapBlock[14].worldY = 1024;
        mapBlock[14].image = setup("/mapblock/a1/churcha1");

        mapBlock[15] = new MapBlock();
        mapBlock[15].worldX = 1984;
        mapBlock[15].worldY = 1046;
        mapBlock[15].image = setup("/mapblock/a1/house3");

        mapBlock[16] = new MapBlock();
        mapBlock[16].worldX = 1459;
        mapBlock[16].worldY = 1472;
        mapBlock[16].image = setup("/mapblock/a1/statue");

        mapBlock[17] = new MapBlock();
        mapBlock[17].worldX = 1536;
        mapBlock[17].worldY = 1728;
        mapBlock[17].image = setup("/mapblock/a1/fruittree");

        mapBlock[18] = new MapBlock();
        mapBlock[18].worldX = 1536;
        mapBlock[18].worldY = 1856;
        mapBlock[18].image = setup("/mapblock/a1/fruittree");

        mapBlock[19] = new MapBlock();
        mapBlock[19].worldX = 2240;
        mapBlock[19].worldY = 1600;
        mapBlock[19].image = setup("/mapblock/a1/house1");

        mapBlock[20] = new MapBlock();
        mapBlock[20].worldX = 2176;
        mapBlock[20].worldY = 1984;
        mapBlock[20].image = setup("/mapblock/a1/shopsign");

        mapBlock[21] = new MapBlock();
        mapBlock[21].worldX = 1536;
        mapBlock[21].worldY = 1984;
        mapBlock[21].image = setup("/mapblock/a1/fruittree");

        mapBlock[22] = new MapBlock();
        mapBlock[22].worldX = 1536;
        mapBlock[22].worldY = 2112;
        mapBlock[22].image = setup("/mapblock/a1/fruittree");

        mapBlock[23] = new MapBlock();
        mapBlock[23].worldX = 1155;
        mapBlock[23].worldY = 1918;
        mapBlock[23].image = setup("/mapblock/a1/library");

        mapBlock[24] = new MapBlock();
        mapBlock[24].worldX = 1536;
        mapBlock[24].worldY = 2240;
        mapBlock[24].image = setup("/mapblock/a1/fruittree");

        mapBlock[25] = new MapBlock();
        mapBlock[25].worldX = 1536;
        mapBlock[25].worldY = 2368;
        mapBlock[25].image = setup("/mapblock/a1/fruittree");

        mapBlock[26] = new MapBlock();
        mapBlock[26].worldX = 1536;
        mapBlock[26].worldY = 2496;
        mapBlock[26].image = setup("/mapblock/a1/fruittree");

        mapBlock[27] = new MapBlock();
        mapBlock[27].worldX = 2240;
        mapBlock[27].worldY = 2240;
        mapBlock[27].image = setup("/mapblock/a1/house2");

        mapBlock[28] = new MapBlock();
        mapBlock[28].worldX = 1536;
        mapBlock[28].worldY = 2624;
        mapBlock[28].image = setup("/mapblock/a1/fruittree");

        mapBlock[29] = new MapBlock();
        mapBlock[29].worldX = 1536;
        mapBlock[29].worldY = 2624;
        mapBlock[29].image = setup("/mapblock/a1/fruittree");

        mapBlock[30] = new MapBlock();
        mapBlock[30].worldX = 1536;
        mapBlock[30].worldY = 2624;
        mapBlock[30].image = setup("/mapblock/a1/fruittree");

        mapBlock[31] = new MapBlock();
        mapBlock[31].worldX = 1536;
        mapBlock[31].worldY = 2624;
        mapBlock[31].image = setup("/mapblock/a1/fruittree");

        mapBlock[32] = new MapBlock();
        mapBlock[32].worldX = 1536;
        mapBlock[32].worldY = 2624;
        mapBlock[32].image = setup("/mapblock/a1/fruittree");

        mapBlock[33] = new MapBlock();
        mapBlock[33].worldX = 1536;
        mapBlock[33].worldY = 2624;
        mapBlock[33].image = setup("/mapblock/a1/fruittree");
    }

    public void setMap3(){
        mapBlock[0] = new MapBlock();
        mapBlock[0].worldX = 2624;
        mapBlock[0].worldY = 2400;
        mapBlock[0].image = setup("/mapblock/a1/tortoiseden");

        mapBlock[1] = new MapBlock();
        mapBlock[1].worldX = 2432;
        mapBlock[1].worldY = 1792;
        mapBlock[1].image = setup("/mapblock/a1/hole");
    }

    public void setMap13(){
        mapBlock[0] = new MapBlock();
        mapBlock[0].worldX = 73;
        mapBlock[0].worldY = 61;
        mapBlock[0].image = setup("/mapblock/a1/bookshelf2");

        mapBlock[1] = new MapBlock();
        mapBlock[1].worldX = 168;
        mapBlock[1].worldY = 61;
        mapBlock[1].image = setup("/mapblock/a1/bookshelf3");

        mapBlock[2] = new MapBlock();
        mapBlock[2].worldX = 832;
        mapBlock[2].worldY = 61;
        mapBlock[2].image = setup("/mapblock/a1/bookshelf3");

        mapBlock[3] = new MapBlock();
        mapBlock[3].worldX = 925;
        mapBlock[3].worldY = 135;
        mapBlock[3].image = setup("/mapblock/a1/bookcase2");

        mapBlock[4] = new MapBlock();
        mapBlock[4].worldX = 87;
        mapBlock[4].worldY = 493;
        mapBlock[4].image = setup("/mapblock/a1/smallchair3");

        mapBlock[5] = new MapBlock();
        mapBlock[5].worldX = 130;
        mapBlock[5].worldY = 449;
        mapBlock[5].image = setup("/mapblock/a1/hpdesk");

        mapBlock[6] = new MapBlock();
        mapBlock[6].worldX = 1056;
        mapBlock[6].worldY = 320;
        mapBlock[6].image = setup("/mapblock/a1/sidecabinet");

        mapBlock[7] = new MapBlock();
        mapBlock[7].worldX = 384;
        mapBlock[7].worldY = 479;
        mapBlock[7].image = setup("/mapblock/a1/bluecarpet2");

        mapBlock[8] = new MapBlock();
        mapBlock[8].worldX = 384;
        mapBlock[8].worldY = 479;
        mapBlock[8].image = setup("/mapblock/a1/bluecarpet2");
    }
}
