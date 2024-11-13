import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

// this class contains the graphics and game loop for the game
public class HangmanPanel extends JPanel implements Runnable {

    // the width and height of the game
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // framerate
    public static final int FPS = 60;

    // image for drawing each frame
    private BufferedImage image;
    private Graphics imageGraphics;

    // is the game running?
    private boolean running;

    public HangmanPanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // make the image to display everything on and make it all white to begin
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        imageGraphics = image.getGraphics();
        imageGraphics.setColor(Color.white);
        imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        // allows this JPanel to display graphics in the game
        setFocusable(true);
        setLayout(null);
        requestFocus();
    }

    @Override
    public void run() {
        running = true;

        // create the game logic
        GameLogic gameLogic = new GameLogic(imageGraphics);

        // time management variables
        long start;
        float elapsed = 0;

        // every frame, update and draw
        while (running) {
            start = System.currentTimeMillis();

            // update and draw
            gameLogic.update(elapsed);
            gameLogic.draw();

            draw();

            // after updating, wait some time in order to keep the framerate at 60
            elapsed = System.currentTimeMillis() - start;
            float millisPerFrame = 1000F / FPS;
            if (elapsed < millisPerFrame) {
                try {
                    Thread.sleep((long) (millisPerFrame - elapsed));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // draw the image to the screen
    private void draw() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);

        imageGraphics.setColor(Color.white);
        imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public Graphics getImageGraphics() {
        return imageGraphics;
    }
}
