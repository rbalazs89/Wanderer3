package environment;

import main.DataBaseClass1;
import main.GamePanel;
import tool.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnvironmentManager {

    GamePanel gp;
    Lighting lighting;
    DataBaseClass1 dataBaseClass1;

    public EnvironmentManager(GamePanel gp){
        this.gp = gp;
    }

    public void setup(){
        lighting = new Lighting(gp);
    }

    public void update() {
        lighting.update();
    }

    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }



}
