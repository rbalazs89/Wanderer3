package tool;

public class SingingNote {
    public int x;
    public int y;
    public float alpha = 1.0f;
    public int noteCounter;
    public int startX;
    public int startY;
    public SingingNote(int x, int y) {
        this.x = x;
        startX = x;
        this.y = y;
        startY = y;
        this.x = x - 20 + (int) (Math.random() * 40);
        this.y = y - 20 + (int) (Math.random() * 40);
        noteCounter = (int) (Math.random() * 100) - 30;
        //currentScreenX = x;
        //currentScreenY = y;
    }
}