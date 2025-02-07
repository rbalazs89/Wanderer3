package environment;

import main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Lighting2 {
    private final GamePanel gp;
    private BufferedImage darknessFilter;
    //private final List<Light> lights;
    private final Random random = new Random();

    // Player light cache
    private RadialGradientPaint playerLightGradient;
    private int cachedLightRadius;
    private int cachedMultiplier;
    private Color[] cachedColors;
    private float[] cachedFractions;

    // Environmental light config
    private static final int BASE_LIGHT_RADIUS = 80;
    private static final Color LIGHT_COLOR = new Color(255, 220, 100, 120);

    // Reusable light buffer
    private BufferedImage lightBuffer;
    private Graphics2D bufferGraphics;
    private static final int MIN_LIGHT_RADIUS = 10;

    public Lighting2(GamePanel gp) {
        this.gp = gp;
        //this.lights = new ArrayList<>();
        initializeLightBuffer();
        setupPlayerLightCache();
    }

    private void initializeLightBuffer() {
        lightBuffer = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = lightBuffer.createGraphics();
        bufferGraphics.setComposite(AlphaComposite.Src);
    }

    public void updateOnMapSwitch() {
        int newRadius = gp.dataBase1.currentMapLightingRadius(gp.currentMap);
        int multiplier = Math.min(20, gp.dataBase1.currentMapDarknessMultiplier(gp.currentMap));

        // Ensure radius is valid before creating gradient
        newRadius = Math.max(MIN_LIGHT_RADIUS, newRadius);

        if(newRadius != cachedLightRadius || multiplier != cachedMultiplier) {
            createPlayerLightGradient(newRadius, multiplier);
        }

        //generateEnvironmentalLights();
    }

    // ADDED MISSING METHOD
    /*private void generateEnvironmentalLights() {
        lights.clear();
        int mapWidth = gp.currentMapMaxCol * GamePanel.tileSize;
        int mapHeight = gp.currentMapMaxRow * GamePanel.tileSize;

        for(int i = 0; i < 15; i++) {
            int worldX = random.nextInt(mapWidth);
            int worldY = random.nextInt(mapHeight);
            lights.add(new Light(worldX, worldY, BASE_LIGHT_RADIUS, LIGHT_COLOR));
        }
    }*/

    private void setupPlayerLightCache() {
        cachedFractions = new float[] {0f, 0.3f, 0.6f, 0.9f, 1f};
        cachedColors = new Color[5];
    }

    private void createPlayerLightGradient(int radius, int multiplier) {
        int centerX = gp.screenWidth/2;
        int centerY = gp.screenHeight/2;

        // Validate and clamp radius
        int safeRadius = Math.max(MIN_LIGHT_RADIUS, radius);

        float[] alphas = {0f, 0.02f, 0.08f, 0.2f, 0.3f};
        for(int i = 0; i < cachedColors.length; i++) {
            cachedColors[i] = new Color(0, 0, 0, Math.min(alphas[i] * multiplier, 0.7f));
        }

        playerLightGradient = new RadialGradientPaint(
                centerX, centerY, safeRadius, cachedFractions, cachedColors
        );

        cachedLightRadius = safeRadius;
        cachedMultiplier = multiplier;
    }

    public void update() {
        //animateLights();
    }

    /*private void animateLights() {
        for(Light light : lights) {
            light.update();
        }
    }*/

    public void draw(Graphics2D g2) {
        drawPlayerLight(g2);
        //drawEnvironmentalLights(g2);
    }

    private void drawPlayerLight(Graphics2D g2) {
        g2.setPaint(playerLightGradient);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

    /*private void drawEnvironmentalLights(Graphics2D g2) {
        bufferGraphics.setBackground(new Color(0, 0, 0, 0));
        bufferGraphics.clearRect(0, 0, gp.screenWidth, gp.screenHeight);

        for(Light light : lights) {
            Point pos = worldToScreen(light.x, light.y);
            drawLight(bufferGraphics, pos.x, pos.y, light.currentRadius, light.color);
        }

        g2.drawImage(lightBuffer, 0, 0, null);
    }*/

    private void drawLight(Graphics2D g, int x, int y, int radius, Color color) {
        float[] dist = {0f, 0.8f, 1f};
        Color[] colors = {
                new Color(color.getRed(), color.getGreen(), color.getBlue(), 180),
                new Color(color.getRed(), color.getGreen(), color.getBlue(), 60),
                new Color(0, 0, 0, 0)
        };

        RadialGradientPaint gradient = new RadialGradientPaint(
                x, y, radius, dist, colors
        );

        g.setPaint(gradient);
        g.fillOval(x - radius, y - radius, radius*2, radius*2);
    }

    private Point worldToScreen(int worldX, int worldY) {
        return new Point(
                worldX - gp.player.worldX + gp.screenWidth/2,
                worldY - gp.player.worldY + gp.screenHeight/2
        );
    }

    /*private static class Light {
        final int x, y;
        final Color color;
        int baseRadius;
        int currentRadius;
        float phase;

        Light(int x, int y, int radius, Color color) {
            this.x = x;
            this.y = y;
            this.baseRadius = Math.max(10, radius); // Minimum 10 pixels
            this.color = color;
            this.phase = (float) Math.random() * 100f;
        }

        void update() {
            currentRadius = Math.max(5, (int)(baseRadius + Math.sin(phase) * 5));
            phase += 0.1f;
        }
    }*/
}