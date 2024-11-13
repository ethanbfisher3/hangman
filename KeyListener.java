import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    private static int lastPressedKey = -1;

    public KeyListener() {}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // records the last key pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isAlphabetic(e.getKeyChar())) {
            lastPressedKey = e.getKeyCode();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static int getLastPressedKey() {
        return lastPressedKey;
    }

    public static void resetLastPressedKey() {
        lastPressedKey = -1;
    }
    
}
