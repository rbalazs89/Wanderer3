package tile;

import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//class
public class MapBlockManager {
    GamePanel gp;
    MapBlock mapBlock[] = new MapBlock[30];

    public MapBlockManager(GamePanel gp) {
        this.gp = gp;
        blockSetter();
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        this.gp = gp;
        for (int i = 0; i < mapBlock.length; i++) {
            if (mapBlock[i] != null) {
                if(mapBlock[i].image != null){
                    int blockWidth = mapBlock[i].image.getWidth();
                    int blockHeight = mapBlock[i].image.getHeight();

                    int screenX = mapBlock[i].worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = mapBlock[i].worldY - gp.player.worldY + gp.player.screenY;

                    if (mapBlock[i].worldX + blockWidth > gp.player.worldX - gp.player.screenX &&
                            mapBlock[i].worldX   < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                            mapBlock[i].worldY + blockHeight > gp.player.worldY - gp.player.screenY &&
                            mapBlock[i].worldY  < gp.player.worldY + gp.player.screenY + gp.tileSize) {
                        g2.drawImage(mapBlock[i].image, screenX, screenY, null);
                    }
                }
            }
        }
    }

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName +".png"));
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
        for (int i = 0; i < mapBlock.length; i++) {
            mapBlock[i] = null;
        }
    }

    public void blockSetter(){
        switch (gp.currentMap){
            case 1:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 35 * gp.tileSize;
                mapBlock[0].worldY = 25 * gp. tileSize;
                mapBlock[0].image = setup("/mapblock/a1/house1");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 44 * gp.tileSize;
                mapBlock[1].worldY = 2 * gp. tileSize;
                mapBlock[1].image = setup("/mapblock/a1/starttent");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 13* gp.tileSize;
                mapBlock[2].worldY = 1 * gp. tileSize;
                mapBlock[2].image = setup("/mapblock/a1/tent_2");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 13* gp.tileSize;
                mapBlock[3].worldY = 5 * gp. tileSize;
                mapBlock[3].image = setup("/mapblock/a1/3desk");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 18* gp.tileSize;
                mapBlock[4].worldY = 1 * gp. tileSize;
                mapBlock[4].image = setup("/mapblock/a1/barrel2");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 14* gp.tileSize;
                mapBlock[5].worldY = 6 * gp. tileSize;
                mapBlock[5].image = setup("/mapblock/a1/smallchair2");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 12* gp.tileSize;
                mapBlock[6].worldY = 2 * gp. tileSize;
                mapBlock[6].image = setup("/mapblock/a1/logs");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 8* gp.tileSize;
                mapBlock[7].worldY = 1 * gp. tileSize;
                mapBlock[7].image = setup("/mapblock/a1/carriage");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = 0 * gp.tileSize;
                mapBlock[8].worldY = 0 * gp. tileSize;
                mapBlock[8].image = setup("/mapblock/a1/cave2");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = (int)(22.8 * gp.tileSize);
                mapBlock[9].worldY = 23 * gp. tileSize;
                mapBlock[9].image = setup("/mapblock/a1/statue");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = (int)(5 * gp.tileSize);
                mapBlock[10].worldY = 16 * gp. tileSize;
                mapBlock[10].image = setup("/mapblock/a1/churcha1");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = 34 * gp.tileSize;
                mapBlock[11].worldY = 31 * gp. tileSize;
                mapBlock[11].image = setup("/mapblock/a1/shopsign");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = 35 * gp.tileSize;
                mapBlock[12].worldY = 35 * gp. tileSize;
                mapBlock[12].image = setup("/mapblock/a1/house2");

                mapBlock[13] = new MapBlock();
                mapBlock[13].worldX = 24 * gp.tileSize;
                mapBlock[13].worldY = 27 * gp. tileSize;
                mapBlock[13].image = setup("/mapblock/a1/fruittree");

                mapBlock[14] = new MapBlock();
                mapBlock[14].worldX = 24 * gp.tileSize;
                mapBlock[14].worldY = 29 * gp. tileSize;
                mapBlock[14].image = setup("/mapblock/a1/fruittree");

                mapBlock[15] = new MapBlock();
                mapBlock[15].worldX = 24 * gp.tileSize;
                mapBlock[15].worldY = 31 * gp. tileSize;
                mapBlock[15].image = setup("/mapblock/a1/fruittree");

                mapBlock[16] = new MapBlock();
                mapBlock[16].worldX = 24 * gp.tileSize;
                mapBlock[16].worldY = 33 * gp. tileSize;
                mapBlock[16].image = setup("/mapblock/a1/fruittree");

                mapBlock[17] = new MapBlock();
                mapBlock[17].worldX = 24 * gp.tileSize;
                mapBlock[17].worldY = 35 * gp. tileSize;
                mapBlock[17].image = setup("/mapblock/a1/fruittree");

                mapBlock[18] = new MapBlock();
                mapBlock[18].worldX = 24 * gp.tileSize;
                mapBlock[18].worldY = 37 * gp. tileSize;
                mapBlock[18].image = setup("/mapblock/a1/fruittree");

                mapBlock[19] = new MapBlock();
                mapBlock[19].worldX = 24 * gp.tileSize;
                mapBlock[19].worldY = 39 * gp. tileSize;
                mapBlock[19].image = setup("/mapblock/a1/fruittree");

                mapBlock[20] = new MapBlock();
                mapBlock[20].worldX = 24 * gp.tileSize;
                mapBlock[20].worldY = 41 * gp. tileSize;
                mapBlock[20].image = setup("/mapblock/a1/fruittree");
                break;
            case 2:
                break;
            case 6:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 10 * gp.tileSize;
                mapBlock[0].worldY = (int)(0.5 * gp. tileSize);
                mapBlock[0].image = setup("/mapblock/a1/church_window");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 3 * gp.tileSize;
                mapBlock[1].worldY = (int)(0.5 * gp. tileSize);
                mapBlock[1].image = setup("/mapblock/a1/church_window");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 1 * gp.tileSize;
                mapBlock[2].worldY = 5 * gp.tileSize;
                mapBlock[2].image = setup("/mapblock/a1/church_candle");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 1 * gp.tileSize;
                mapBlock[3].worldY = 7 * gp.tileSize;
                mapBlock[3].image = setup("/mapblock/a1/church_candle");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 1 * gp.tileSize;
                mapBlock[4].worldY = 9 * gp.tileSize;
                mapBlock[4].image = setup("/mapblock/a1/church_candle");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 1 * gp.tileSize;
                mapBlock[5].worldY = 11 * gp.tileSize;
                mapBlock[5].image = setup("/mapblock/a1/church_candle");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 1 * gp.tileSize;
                mapBlock[6].worldY = 13 * gp.tileSize;
                mapBlock[6].image = setup("/mapblock/a1/church_candle");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 1 * gp.tileSize;
                mapBlock[7].worldY = 15 * gp.tileSize;
                mapBlock[7].image = setup("/mapblock/a1/church_candle");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = (int)(12.5 * gp.tileSize);
                mapBlock[8].worldY = 5 * gp.tileSize;
                mapBlock[8].image = setup("/mapblock/a1/church_candle2");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = (int)(12.5 * gp.tileSize);
                mapBlock[9].worldY = 7 * gp.tileSize;
                mapBlock[9].image = setup("/mapblock/a1/church_candle2");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = (int)(12.5 * gp.tileSize);
                mapBlock[10].worldY = 9 * gp.tileSize;
                mapBlock[10].image = setup("/mapblock/a1/church_candle2");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = (int)(12.5 * gp.tileSize);
                mapBlock[11].worldY = 11 * gp.tileSize;
                mapBlock[11].image = setup("/mapblock/a1/church_candle2");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = (int)(12.5 * gp.tileSize);
                mapBlock[12].worldY = 13 * gp.tileSize;
                mapBlock[12].image = setup("/mapblock/a1/church_candle2");

                mapBlock[13] = new MapBlock();
                mapBlock[13].worldX = (int)(12.5 * gp.tileSize);
                mapBlock[13].worldY = 15 * gp.tileSize;
                mapBlock[13].image = setup("/mapblock/a1/church_candle2");
                break;

            case 7:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = -1 * gp.tileSize;
                mapBlock[0].worldY = 5 * gp. tileSize;
                mapBlock[0].image = setup("/mapblock/a1/woodstairs");
                break;

            case 8:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 5 * gp.tileSize;
                mapBlock[0].worldY = (3 * gp. tileSize);
                mapBlock[0].image = setup("/mapblock/a1/desk2");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 3 * gp.tileSize;
                mapBlock[1].worldY = (int) (0.5 * gp. tileSize);
                mapBlock[1].image = setup("/mapblock/a1/shopwindow");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 9 * gp.tileSize;
                mapBlock[2].worldY = (int) (0.5 * gp. tileSize);
                mapBlock[2].image = setup("/mapblock/a1/shopwindow");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 1 * gp.tileSize;
                mapBlock[3].worldY = 5 * gp. tileSize;
                mapBlock[3].image = setup("/mapblock/a1/benchside");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 11 * gp.tileSize;
                mapBlock[4].worldY = (int) (1.5 * gp. tileSize);
                mapBlock[4].image = setup("/mapblock/a1/fireplacewood");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 1 * gp.tileSize;
                mapBlock[5].worldY = 8 * gp. tileSize;
                mapBlock[5].image = setup("/mapblock/a1/benchside");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 1 * gp.tileSize;
                mapBlock[6].worldY = 8 * gp. tileSize;
                mapBlock[6].image = setup("/mapblock/a1/benchside");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 4 * gp.tileSize;
                mapBlock[7].worldY = 7 * gp. tileSize;
                mapBlock[7].image = setup("/mapblock/a1/bluecarpet");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = (int)(12.6 * gp.tileSize);
                mapBlock[8].worldY = 4 * gp. tileSize;
                mapBlock[8].image = setup("/mapblock/a1/showcaseside");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = 2 * gp.tileSize;
                mapBlock[9].worldY = 3 * gp. tileSize;
                mapBlock[9].image = setup("/mapblock/a1/desk");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = 9 * gp.tileSize;
                mapBlock[10].worldY = 3 * gp. tileSize;
                mapBlock[10].image = setup("/mapblock/a1/rack");
                break;
            case 9:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 1 * gp.tileSize;
                mapBlock[0].worldY = 3 * gp. tileSize;
                mapBlock[0].image = setup("/mapblock/a1/woodentrapdoor");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 5 * gp.tileSize;
                mapBlock[1].worldY = (int)(1.1 * gp. tileSize);
                mapBlock[1].image = setup("/mapblock/a1/bookcase");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 8 * gp.tileSize;
                mapBlock[2].worldY = 2 * gp. tileSize;
                mapBlock[2].image = setup("/mapblock/a1/bookcase2");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 12 * gp.tileSize;
                mapBlock[3].worldY = 3 * gp. tileSize;
                mapBlock[3].image = setup("/mapblock/a1/bluebed");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 12 * gp.tileSize;
                mapBlock[4].worldY = 6 * gp. tileSize;
                mapBlock[4].image = setup("/mapblock/a1/redbed");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 1 * gp.tileSize;
                mapBlock[5].worldY = 9 * gp. tileSize;
                mapBlock[5].image = setup("/mapblock/a1/doublebed");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 7 * gp.tileSize;
                mapBlock[6].worldY = 6 * gp. tileSize;
                mapBlock[6].image = setup("/mapblock/a1/fruitdesk");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 6 * gp.tileSize;
                mapBlock[7].worldY = (int)(6.5 * gp. tileSize);
                mapBlock[7].image = setup("/mapblock/a1/smallchair");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = 8 * gp.tileSize;
                mapBlock[8].worldY = (int)(6.5 * gp. tileSize);
                mapBlock[8].image = setup("/mapblock/a1/smallchair");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = 4 * gp.tileSize;
                mapBlock[9].worldY = 2 * gp. tileSize;
                mapBlock[9].image = setup("/mapblock/a1/homecabinet");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = 1 * gp.tileSize;
                mapBlock[10].worldY = 5 * gp. tileSize;
                mapBlock[10].image = setup("/mapblock/a1/desk_home");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = 2 * gp.tileSize;
                mapBlock[11].worldY = (int)(5.5 * gp. tileSize);
                mapBlock[11].image = setup("/mapblock/a1/smallchair");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = 10 * gp.tileSize;
                mapBlock[12].worldY = (int)(9 * gp. tileSize);
                mapBlock[12].image = setup("/mapblock/a1/cabinet");
                break;

            case 10:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 18 * gp.tileSize;
                mapBlock[0].worldY = 13 * gp.tileSize;
                mapBlock[0].image = setup("/mapblock/a1/hpdesk");

                mapBlock[1] = new MapBlock();
                mapBlock[1].worldX = 1 * gp.tileSize;
                mapBlock[1].worldY = 2 * gp.tileSize;
                mapBlock[1].image = setup("/mapblock/a1/angelstatueleft");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 1 * gp.tileSize;
                mapBlock[2].worldY = 5 * gp.tileSize;
                mapBlock[2].image = setup("/mapblock/a1/angelstatueleft");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 1 * gp.tileSize;
                mapBlock[3].worldY = 8 * gp.tileSize;
                mapBlock[3].image = setup("/mapblock/a1/angelstatueleft");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 10 * gp.tileSize;
                mapBlock[4].worldY = 2 * gp.tileSize;
                mapBlock[4].image = setup("/mapblock/a1/angelstatueright");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 10 * gp.tileSize;
                mapBlock[5].worldY = 5 * gp.tileSize;
                mapBlock[5].image = setup("/mapblock/a1/angelstatueright");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 10 * gp.tileSize;
                mapBlock[6].worldY = 8 * gp.tileSize;
                mapBlock[6].image = setup("/mapblock/a1/angelstatueright");

                mapBlock[7] = new MapBlock();
                mapBlock[7].worldX = 5 * gp.tileSize;
                mapBlock[7].worldY = 3 * gp.tileSize;
                mapBlock[7].image = setup("/mapblock/a1/redcarpet");

                mapBlock[8] = new MapBlock();
                mapBlock[8].worldX = 5 * gp.tileSize;
                mapBlock[8].worldY = 0 * gp.tileSize;
                mapBlock[8].image = setup("/mapblock/a1/carpetstairs");

                mapBlock[9] = new MapBlock();
                mapBlock[9].worldX = 13 * gp.tileSize;
                mapBlock[9].worldY = (int)(8 * gp.tileSize);
                mapBlock[9].image = setup("/mapblock/a1/bigcabinetmagic");

                mapBlock[10] = new MapBlock();
                mapBlock[10].worldX = 22 * gp.tileSize;
                mapBlock[10].worldY = (int)(12 * gp.tileSize);
                mapBlock[10].image = setup("/mapblock/a1/magiccabinet");

                mapBlock[11] = new MapBlock();
                mapBlock[11].worldX = (int)(23 * gp.tileSize);
                mapBlock[11].worldY = (int)(19.6 * gp.tileSize);
                mapBlock[11].image = setup("/mapblock/a1/potioncabinet");

                mapBlock[12] = new MapBlock();
                mapBlock[12].worldX = 22 * gp.tileSize;
                mapBlock[12].worldY = 17 * gp.tileSize;
                mapBlock[12].image = setup("/mapblock/a1/cauldron");
                break;

            case 11:
                mapBlock[0] = new MapBlock();
                mapBlock[0].worldX = 4 * gp.tileSize;
                mapBlock[0].worldY = 1 * gp.tileSize;
                mapBlock[0].image = setup("/mapblock/a1/knightstatue");

                mapBlock[2] = new MapBlock();
                mapBlock[2].worldX = 2 * gp.tileSize;
                mapBlock[2].worldY = 0 * gp.tileSize;
                mapBlock[2].image = setup("/mapblock/a1/bigsword");

                mapBlock[3] = new MapBlock();
                mapBlock[3].worldX = 13 * gp.tileSize;
                mapBlock[3].worldY = 1 * gp.tileSize;
                mapBlock[3].image = setup("/mapblock/a1/crest");

                mapBlock[4] = new MapBlock();
                mapBlock[4].worldX = 10 * gp.tileSize;
                mapBlock[4].worldY = (int)(0.5 * gp.tileSize);
                mapBlock[4].image = setup("/mapblock/a1/axes");

                mapBlock[5] = new MapBlock();
                mapBlock[5].worldX = 11 * gp.tileSize;
                mapBlock[5].worldY = 1 * gp.tileSize;
                mapBlock[5].image = setup("/mapblock/a1/knightstatue");

                mapBlock[6] = new MapBlock();
                mapBlock[6].worldX = 4 * gp.tileSize;
                mapBlock[6].worldY = (int)(12.5 * gp.tileSize);
                mapBlock[6].image = setup("/mapblock/a1/bluecarpet2");
                break;
        }
    }
}
