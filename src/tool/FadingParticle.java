package tool;

import main.GamePanel;

public class FadingParticle {
    public int x;
    public int y;
    public float alpha = 1.0f;
    public int ageCounter = 0;

    public FadingParticle() {
        resetFields();
    }

    public void update(){
        ageCounter ++;
        if(ageCounter % 3 == 0){
            y--;
            alpha -= 0.02f;
        }
        if(ageCounter >= 80){
            resetFields();
        }
    }

    public void resetFields(){
        alpha = 1f;
        x = (int) (Math.random() * GamePanel.tileSize) - GamePanel.tileSize/8;
        y = (int) (Math.random() * GamePanel.tileSize * 1 / 2);
        ageCounter = (int) (Math.random() * 50) - 30;
    }
}
