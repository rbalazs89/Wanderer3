package environment;

import main.DataBaseClass1;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Lighting {

    GamePanel gp;
    static BufferedImage darknessFilter;

    public Lighting(GamePanel gp){
        this.gp = gp;
        setLighting();
    }

    public void setLighting() {
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Get the center x and y of the light circle
        int centerX = gp.player.screenX + (gp.tileSize) / 2;
        int centerY = gp.player.screenY + (gp.tileSize) / 2;

        // Create a smoother gradient effect
        Color color[] = new Color[12];
        float fraction[] = new float[12];

        int multiplier = Math.min(20,gp.dataBase1.currentMapDarknessMultiplier(gp.currentMap));

        color[0] = new Color(0, 0, 0, 0);
        color[1] = new Color(0, 0, 0, 0.005f * multiplier);
        color[2] = new Color(0, 0, 0, 0.010f * multiplier);
        color[3] = new Color(0, 0, 0, 0.015f * multiplier);
        color[4] = new Color(0, 0, 0, 0.020f * multiplier);
        color[5] = new Color(0, 0, 0, 0.025f * multiplier);
        color[6] = new Color(0, 0, 0, 0.030f * multiplier);
        color[7] = new Color(0, 0, 0, 0.035f * multiplier);
        color[8] = new Color(0, 0, 0, 0.040f * multiplier);
        color[9] = new Color(0, 0, 0, 0.045f * multiplier);
        color[10] = new Color(0, 0, 0, 0.050f * multiplier);
        color[11] = color[10];

        fraction[0] = 0f;
        fraction[1] = 0.1f;
        fraction[2] = 0.2f;
        fraction[3] = 0.3f;
        fraction[4] = 0.4f;
        fraction[5] = 0.5f;
        fraction[6] = 0.6f;
        fraction[7] = 0.7f;
        fraction[8] = 0.8f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        // Create a gradation paint settings
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.dataBase1.currentMapLightingRadius(gp.currentMap), fraction, color);

        // Set the gradient data on g2
        g2.setPaint(gPaint);

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }

    public void update() {
        setLighting();
    }
}

