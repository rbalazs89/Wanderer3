package tile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FineCollisionMapManager {

    // experimental class for now
    // every tile divided into 4x4 for finer collision handling
    // file is read from another txt file
    //TODO need to be careful with openable door objects with collision
    //TODO need to add openable door objects to collision map

    private final TileManager tileM;
    public boolean[][] fineTileCollision;
    public boolean[][] roughTileCollision;
    public static int maxFineCol;
    public static int maxFineRow;

    public FineCollisionMapManager(TileManager tileM){
        this.tileM = tileM;
        updateOnMapSwitch();
    }

    public void updateOnMapSwitch(){
        int currentMapNumber = tileM.gp.currentMap;
        String filePath = "/maps/mapcollision" + currentMapNumber + ".txt";
        getMapDimensions(filePath);
        fineTileCollision = new boolean[maxFineCol][maxFineRow];
        readFile(filePath);
    }

    public void readFile(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < maxFineCol && row < maxFineRow){
                String line = br.readLine();
                while(col < maxFineCol) {
                    String[] numbers = line.split("");

                    int num = Integer.parseInt(numbers[col]);

                    if(num == 1){
                        fineTileCollision[col][row] = true;
                    } else if (num == 0){
                        fineTileCollision[col][row] = false;
                    }

                    col ++;
                }
                if(col == maxFineCol){
                    col = 0;
                    row ++;
                }

            }
            br.close();
        }
        catch (Exception ignored){

        }
    }

    public void getMapDimensions(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            if (line != null) {
                maxFineCol = line.length();
            }
            int rowCount = 0;
            while (line != null) {
                rowCount++;
                line = br.readLine();
            }
            maxFineRow = rowCount;

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRoughFromFine(){
        roughTileCollision = new boolean[maxFineCol/4][maxFineRow/4];

        for (int i = 0; i < fineTileCollision.length; i++) {
            for (int j = 0; j < fineTileCollision[0].length; j++) {
                if(fineTileCollision[i][j]){
                    roughTileCollision[i / 4][j / 4] = true;
                }
            }
        }
    }
}
