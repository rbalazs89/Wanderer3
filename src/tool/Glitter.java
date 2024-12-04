package tool;

public class Glitter {

    public int x;
    public int y;
    public int startX;
    public int startY;
    public boolean hasReachedTurning = false;
    public int glitterCounter;
    public int currentScreenX;
    public int currentScreenY;
    public Glitter(int x, int y){
        this.x = x;
        startX = x;
        this.y = y;
        startY = y;
        glitterCounter = (int) (Math.random() * 47);
        this.x = x - 20 + (int) (Math.random() * 40);
        this.y = y - 20 + (int) (Math.random() * 40);
        currentScreenX = x;
        currentScreenY = y;
    }
}
