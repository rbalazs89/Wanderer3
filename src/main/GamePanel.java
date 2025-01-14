package main;

import ai.PathFinder;
import data.ProgressQuest;
import data.SaveLoad;
import entity.Entity;
import entity.EntityImageLoader;
import entity.Player;
import environment.EnvironmentManager;
import item.AllInventoryPages;
import item.ItemGenerator;
import object.SuperObject;
import object.puzzle.harrypotterpuzzle.HarryPotterPuzzle;
import tile.Map;
import tile.MapBlockManager;
import tile.DecorManager;
import tile.TileManager;
import tool.DamageNumber;
import tool.UtilityTool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.awt.Color.BLACK;
import static main.Main.window;

public class GamePanel extends JPanel implements Runnable{

    //SAVELOAD:
    public ProgressQuest progress = new ProgressQuest(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    public int saveSlotNumber;
    /////////////////////////////////////////////////////////////////////
    public ItemGenerator itemGenerator = new ItemGenerator(this);

    //private BufferedImage bufferedImage; //full screen
    //private Graphics2D g2d; //full screen
    // settings:
    public boolean visibleHitBox;
    public boolean visibleExpValue;
    public boolean visibleDamageNumbersDoneToYou;
    public boolean visibleDamageNumbersDoneByYou;
    public EntityImageLoader entityImageLoader = new EntityImageLoader(this);

    // SCREEN SETTINGS:
    // 1024 x 768
    static final int originalTileSize = 64;
    public static final int maxMapNumber = 100;
    public DataBaseClass1 dataBase1 = new DataBaseClass1(this);
    static final int scale = 1;
    public static final int tileSize = originalTileSize * scale;
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024
    public final int screenHeight = tileSize * maxScreenRow; // 768

    //WORLD SETTINGS
    //public final int maxWorldCol = 50;
    //public final int maxWorldRow = 50;
    public int currentMapMaxCol;
    public int currentMapMaxRow;
    public int currentMap = 1; // TODO should load from save file

    //full screen settings:
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    //BufferedImage tempScreen;
    //Graphics2D g2;

    //SYSTEM:
    public static final int fps = 60;
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public HarryPotterPuzzle harryPotterPuzzle = new HarryPotterPuzzle(this);

    Config config = new Config(this);
    Thread gameThread;
    private int transitionCounter = 0;

    // ENTITY AND OBJECT:
    public ArrayList<Entity> allFightingEntities = new ArrayList<>();
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public Entity[][] npc = new Entity[maxMapNumber][30];
    public Entity[][] fighters = new Entity[maxMapNumber][30];
    ArrayList<Entity> entityList = new ArrayList<>(); // to draw based on Y coordinate
    public ArrayList<Entity> attacks = new ArrayList<>(); // melee attacks
    public ArrayList<Entity> spells = new ArrayList<>(); // all spells
    public ArrayList<Entity> vfx = new ArrayList<>();
    public ArrayList<DamageNumber> damageNumbers = new ArrayList<>();
    public PathFinder pFinder = new PathFinder(this);
    public Map map = new Map(this);
    public MapBlockManager mapBlockManager = new MapBlockManager(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    public DecorManager decorManager = new DecorManager(this);

    //UI :
    public AllInventoryPages allInventoryPages = new AllInventoryPages(this);

    public UI ui = new UI(this);

    public SuperObject[][] obj = new SuperObject[maxMapNumber][30]; // solid doors, pickables on touch, events on touch etc
    public CollisionChecker cChecker= new CollisionChecker(this);
    public ArrayList<SuperObject> interactObjects = new ArrayList<>(); // pickables with interact key, must be updated based on current ma

    //GAME STATE:
    public int gameState;
    public final int transitionState = -1;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int inventoryState = 5;
    public final int mainMenuState = 6;

    public int inventorySubState;
    public final int inventoryOnlyState = 500; // only inventory open
    public final int inventoryStorageState1 = 511; // storage chest + inventory open + first tab open
    public final int inventoryStorageState2 = 512; // storage chest + inventory open + second tab open
    public final int inventoryStorageState3 = 513; // storage chest + inventory open + second tab open
    public final int inventoryStorageState4 = 514; // storage chest + inventory open + second tab open
    public final int inventoryShopState = 520; // inventory + some NPC shop open
    public int mainMenuSubState = 600;
    public final int mainMenuSubState0 = 600;
    public final int mainMenuHelpState = 610;
    public final int mainMenuOptionsState = 620;
    public final int mainMenuShowControlsState = 630;
    public final int mapState = 7;
    public final int deathState = 8;
    public final int skillPageState = 9;
    public final int puzzleStateHarryPotter = 1001;
    public final int talentPageState = 10;
    public final int gameEndState = 11;
    public final int newGameLoadingState = 12;
    public final int loadSavedGameLoadingState = 13;

    public final  UtilityTool uTool = new UtilityTool(this);

    //TODO different resolution, I couldn't make resizing work properly

    // MOUSE:
    public Robot robot;
    public int windowX, windowY, windowX2, windowY2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearDataWhenExit(){
        spells.clear();
        attacks.clear();
        vfx.clear();
        interactObjects.clear();
        allFightingEntities.clear();
    }

    public void setupNewGame(){
        //TODO clear inventory
        player.makeDefaultHero();
        aSetter.setObject();
        resetPuzzles();
        spells.clear();
        vfx.clear();

        //
        currentMap = 1;
        mapBlockManager.update();
        tileM.getTileManagerDataCurrentMap(currentMap);
        map.createMapScreenImage();
        eManager.update();
        //

        for(int i = 0; i < obj[1].length; i++){
            if(obj[currentMap][i] != null && obj[currentMap][i].interactable){
                interactObjects.add(obj[currentMap][i]);
            }
        }

        aSetter.setFighter();

        for (int i = 0; i < fighters[1].length; i++) {
            if(fighters[currentMap][i] != null){
                allFightingEntities.add(fighters[currentMap][i]);
            }
        }

        allFightingEntities.add(player);
        aSetter.setNPC();
        startSinging(currentMap);
    }

    public void setupGame() {
        eManager.setup();
        gameState = titleState;
        playMusic(74);
        stopMusic();
        playMusic(74);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();

                repaint(); // original

                //drawToTempScreen(); // different resolution
                //drawToScreen(); // different resolution

                delta--;
                drawCount++;
            }
            if (timer >= 1000000000){
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            restrictMouseToWindow();
        }
    }

    public void update() {
        if(gameState == transitionState){
            transitionCounter++;
            if (transitionCounter > 4){
                transitionCounter = 0;
                gameState = playState;
            }
        }
        else if(gameState == loadSavedGameLoadingState){
            transitionCounter ++;
            if(transitionCounter == 1){
                saveLoad.load(saveSlotNumber);
            }
            if(transitionCounter >= 60){
                transitionCounter = 0;
                gameState = playState;
            }
        }
        else if(gameState == newGameLoadingState){
            transitionCounter ++;
            if(transitionCounter == 1){
                setupNewGame();
            }
            if(transitionCounter >= 60){
                transitionCounter = 0;
                gameState = playState;
            }
        }
        else if(gameState == playState){
            player.update();

            for (int i = 0; i < attacks.size(); i++) {
                attacks.get(i).update();
            }

            for (int i = 0; i < spells.size(); i++) {
                spells.get(i).update();
            }

            for (int i = 0; i < vfx.size(); i++) {
                vfx.get(i).update();
            }

            for(int i = 0 ; i < npc[1].length; i ++){
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for(int i = 0; i < fighters[1].length; i ++){
                if(fighters[currentMap][i] != null){
                    fighters[currentMap][i].update();
                }
            }

            for(int i = 0; i < damageNumbers.size(); i ++){
                damageNumbers.get(i).update();
            }
        }
        else if(gameState == inventoryState){
            cChecker.checkObjectToInteract(); // to allow  finding suitable place if multiple items dropped from inventory
        }
        else if(gameState == gameEndState){
            // do nothing
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(gameState != transitionState && gameState != loadSavedGameLoadingState && gameState != newGameLoadingState) {

            long drawStart = 0;
            if (keyH.checkDrawTime) {
                drawStart = System.nanoTime();
            }
            if (gameState == titleState) {
                //ui.draw(g2);
            } else if (gameState == mapState) {
                map.drawFullMap(g2);
            } else if (gameState == gameEndState){
                    ui.drawGameEnd(g2);
            } else {
                tileM.draw(g2);
                decorManager.draw(g2);
                mapBlockManager.draw(g2, this);
                for (int i = 0; i < obj[1].length; i++) {
                    if (obj[currentMap][i] != null && !obj[currentMap][i].interactable) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }

                for (int i = 0; i < interactObjects.size(); i++) {
                    if (interactObjects.get(i) != null) {
                        interactObjects.get(i).draw(g2, this);
                    }
                }

                for (int i = 0; i < vfx.size(); i++) {
                    vfx.get(i).draw(g2);
                }

                for (int i = 0; i < npc[1].length; i++) {
                    if (npc[currentMap][i] != null) {
                        entityList.add(npc[currentMap][i]);
                    }
                }

                for (int i = 0; i < fighters[1].length; i++) {
                    if (fighters[currentMap][i] != null) {
                        entityList.add(fighters[currentMap][i]);
                    }
                }

                //SORT
                entityList.add(player);
                Collections.sort(entityList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity e1, Entity e2) {
                        int result = Integer.compare(e1.worldY, e2.worldY);
                        return result;
                    }
                });


                for (int i = 0; i < entityList.size(); i++) {
                    entityList.get(i).draw(g2);
                }

                entityList.clear();

                for (int i = 0; i < attacks.size(); i++) {
                    attacks.get(i).draw(g2);
                }

                for (int i = 0; i < spells.size(); i++) {
                    spells.get(i).draw(g2);
                }

                for(int i = 0; i < damageNumbers.size(); i ++){
                    damageNumbers.get(i).draw(g2);
                }

                eManager.draw(g2);
                map.drawMiniMap(g2);
            }
            ui.draw(g2);
            if (keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);

                int x = 10;
                int y = 400;
                int lineHeight = 20;
                String[] debugStringToDraw = {"WorldX " + player.worldX,
                        "WorldY " + player.worldY, "Col " + (player.worldX + player.solidArea.x) / tileSize,
                        "Row " + (player.worldY + player.solidArea.y) / tileSize,
                        "Draw Time" + passed};
                for (int i = 0; i < debugStringToDraw.length; i++) {
                    g2.drawString(debugStringToDraw[i], x, y);
                    y += lineHeight;
                }
            }
        }
        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void testMusic(int i) {
        music.setFile(i);
        music.play();
    }

    private void restrictMouseToWindow() {
        if (Main.window.isFocused()) {
            Insets insets = Main.window.getInsets();

            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            Point windowLocation = Main.window.getLocationOnScreen();

            int mouseX = (int) mouseLocation.getX();
            int mouseY = (int) mouseLocation.getY();
            int windowX = (int) windowLocation.getX() + insets.left + 1;
            int windowY = (int) windowLocation.getY();
            int windowWidth = Main.window.getWidth() - insets.left - insets.right;
            int windowHeight = Main.window.getHeight() - insets.bottom;

            int restrictedX = Math.max(windowX, Math.min(mouseX, windowX + windowWidth - 3));
            int restrictedY = Math.max(windowY, Math.min(mouseY, windowY + windowHeight - 2));

            if (mouseX != restrictedX || mouseY != restrictedY) {
                robot.mouseMove(restrictedX, restrictedY);
            }
        }
    }

    //for screen resize method:
    //TODO not working well
    /*
    public void drawToTempScreen() {
        //chatgpt
        g2.setColor(BLACK);
        g2.fillRect(0, 0, screenWidth2, screenHeight2);
        //chatgptend

        long drawStart = 0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        if(gameState == titleState){
            //ui.draw(g2);
        }
        else {

            tileM.draw(g2);

            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }

            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i <monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            //SORT
            entityList.add(player);
            Collections.sort(entityList, new Comparator<Entity>(){
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            for(int i = 0; i < entityList.size(); i ++){
                entityList.get(i).draw(g2);
            }
            entityList.clear();

            for (int i = 0; i < interactObjects.size(); i++) {
                if(interactObjects.get(i) != null){
                    interactObjects.get(i).draw(g2, this);
                }
            }

            for (int i = 0; i < attacks.size(); i++) {
                attacks.get(i).draw(g2);
            }

            for (int i = 0; i < spells.size(); i++) {
                spells.get(i).draw(g2);
            }
        }

        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 20;
            String[] debugStringToDraw = {"WorldX " + player.worldX,
                    "WorldY " + player.worldY, "Col " + (player.worldX + player.solidArea.x)/tileSize,
                    "Row " + (player.worldY + player.solidArea.y)/tileSize,
                    "Draw Time" + passed} ;
            for (int i = 0; i < debugStringToDraw.length; i++) {
                g2.drawString(debugStringToDraw[i],x ,y);
                y += lineHeight;
            }
        }
        ui.draw(g2);

    }*/

        /*public void drawToScreen2() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    //screen resize
    //chatgptdrawtoscreen
    public void drawToScreen() {
        BufferStrategy bufferStrategy = Main.window.getBufferStrategy();
        if (bufferStrategy == null) {
            return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync(); // Sync the display on some systems
    }*/

    public void setFullScreen () {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startSinging(int currentMap){
        stopMusic();
        int number = dataBase1.getMusicNumber(currentMap);
        if(number != -1){
            playMusic(number);
        }
    }

    public void mapSwitch(){
        //assets:
        if (currentMap == 1){
            aSetter.ghostsNearCemetery();
        }
        else if(currentMap == 7){
            aSetter.setBigBroFighter();
        }
        else if(currentMap == 9){
            aSetter.setBigBroNPC();
        }

        gameState = transitionState;

        mapBlockManager.update();
        tileM.getTileManagerDataCurrentMap(currentMap);
        map.createMapScreenImage();

        spells.clear();
        attacks.clear();
        interactObjects.clear();

        for (int i = 0; i < obj[1].length; i++) {
            if(obj[currentMap][i] != null){
                if(obj[currentMap][i].interactable){
                    interactObjects.add(obj[currentMap][i]);
                }
            }
        }

        allFightingEntities.clear();
        for (int i = 0; i < fighters[1].length; i++) {
            if(fighters[currentMap][i] != null){
                allFightingEntities.add(fighters[currentMap][i]);
            }
        }
        allFightingEntities.add(player);

        eManager.update();

        player.refreshPlayerStatsNoItems();
        startSinging(currentMap);
    }

    public void resetPuzzles(){
        obj[10][29] = null;
    }
}
