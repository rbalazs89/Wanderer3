package environment;

import main.GamePanel;
import java.awt.*;

public class EnvironmentManager {
    GamePanel gp;
    Lighting lighting;
    Lighting2 lighting2;

    public EnvironmentManager(GamePanel gp){
        this.gp = gp;
    }

    public void setup(){
        //lighting = new Lighting(gp);
        lighting = new Lighting(gp);
    }

    public void updateOnMapSwitch() {
        //lighting.updateOnMapSwitch();
        lighting.updateOnMapSwitch();
    }

    public void draw(Graphics2D g2){
        //lighting.draw(g2);
        lighting.draw(g2);
    }

    public void updateEveryLoop(){
        //lighting2.update();
    }
}
