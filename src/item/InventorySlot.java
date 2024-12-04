package item;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class InventorySlot {

    BufferedImage image;
    public Item placedItem;

    public InventorySlot(){
        image = createGradientImage(48,48);
    }

    private BufferedImage createGradientImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // Create a radial gradient that starts with gray at the center and goes to black at the edges
        float[] fractions = {0f, 1f};
        Color[] colors = {new Color(36,36,36), Color.BLACK};
        RadialGradientPaint radialGradient = new RadialGradientPaint(
                new Point2D.Double(width / 2, height / 2),
                Math.min(width, height) / 2,
                fractions,
                colors,
                MultipleGradientPaint.CycleMethod.NO_CYCLE
        );

        g2.setPaint(radialGradient);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        return image;
    }
}
