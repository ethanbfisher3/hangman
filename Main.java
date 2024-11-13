import javax.swing.JFrame;

/**
 * Hangman
 * Author: Ethan Fisher
 * Date: 11/13/2024
 * Time: 3 hours
 */

public class Main {
    public static void main(String[] args) {

        // create the game panel object
        HangmanPanel panel = new HangmanPanel();

        // create the window that displays the game and adjust its size
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener());
        frame.setVisible(true);
        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null); 

        // start the game logic in a separate thread
        new Thread(panel).start();

    }
}