package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    public static JFrame window;
    public static void main(String[] args) {

        Image cursorImage = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/ui/pointer.png"));
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(1, 1), "customCursor");

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Wanderer 2");
        window.setCursor(customCursor);
        //window.setUndecorated(true); // top bar gone, better to keep it, if user wants to close

        GamePanel gamePanel = new GamePanel();
        gamePanel.config.loadConfig(); // loads settings from options
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

        //window.createBufferStrategy(3); // Create the buffer strategy for the JFrame, result looks somewhat worse
        //
    }
}