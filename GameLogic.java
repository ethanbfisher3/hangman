import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class GameLogic {

    // word list for the game
    private static final String[] WORDS = { "philosophical", "beekeeper", "walkway", "peekaboo", "gigahertz", "mnemonic", "wristwatch", "espionage", "stronghold", "jukeboxes" };

    // font for the game
    private static final Font TITLE_FONT = new Font("SansSerif", Font.PLAIN, 48);
    private static final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 24);

    // the hangman center position
    private static final Point HANGMAN_POSITION = new Point(200, 375);

    private int wrongGuesses;
    private int correctGuesses;
    private String word;
    private ArrayList<Character> guessedLetters;
    private boolean lastGuessWrong;

    // the game graphics
    private Graphics2D g;

    // initialize all variables
    public GameLogic(Graphics gameGraphics) {
        this.g = (Graphics2D) gameGraphics;

        wrongGuesses = 0;
        word = WORDS[(int) (Math.random() * WORDS.length)];
        guessedLetters = new ArrayList<>();
        lastGuessWrong = false;
        wrongGuesses = 0;
        correctGuesses = 0;
    }

    public void update(float dt) {
        // get the last key pressed by the user
        int lastPressedKey = KeyListener.getLastPressedKey();
        char guess = (char) lastPressedKey;

        // if the game is over, prompt for Q or P to quit or play again
        if (isGameOver()) {
            if (guess == 'Q')
                System.exit(0);
            else if (guess == 'P') {
                reset();
                KeyListener.resetLastPressedKey();
            }
            return;
        }

        // if the game is not over, register each guess as a correct or incorrect guess
        // and remember the letters that have been guessed
        if (lastPressedKey != -1 && !guessedLetters.contains(guess)) {
            guessedLetters.add(guess);
            KeyListener.resetLastPressedKey();
            
            lastGuessWrong = word.indexOf(Character.toLowerCase(guess)) == -1;
            if (lastGuessWrong)
                wrongGuesses++;
            else
                correctGuesses++;
        }
    }

    public void draw() {
        // set the color and font to draw the title and welcome the user
        g.setColor(Color.black);
        g.setFont(TITLE_FONT);
        int width = g.getFontMetrics().stringWidth("Welcome to Hangman!");
        g.drawString("Welcome to Hangman!", HangmanPanel.WIDTH / 2 - width / 2, 50);

        drawHangman();
        drawWord();

        g.setFont(NORMAL_FONT);
        g.drawString("Enter a Letter:", 400, 150);

        // draw the last key pressed by the user
        int lastPressedKey = KeyListener.getLastPressedKey();
        if (lastPressedKey != -1)
            g.drawString((char)lastPressedKey + "", 570, 150);

        // notify the user that the last guess was wrong if it was
        if (lastGuessWrong)
            g.drawString("Wrong Guess!", 100, 125);

        // let the user know the word if the game is over and prompt a Q or P input to quit or play again
        if (isGameOver()) {
            g.drawString("Game Over! The word was:", 300, 450);
            g.drawString(word.toUpperCase(), 300, 475);

            g.drawString("Quit (Q)", 300, 500);
            g.drawString("Play Again (P)", 300, 525);
        }

        // draw already guessed letters
        int numberDisplayed = 0;
        for (int i = 0; i < guessedLetters.size(); i++) {
            if (word.toUpperCase().indexOf(guessedLetters.get(i)) == -1) {
                g.drawString(guessedLetters.get(i) + "", 50 + numberDisplayed * 35, 160);
                numberDisplayed++;
            }
        }

        // draw the amount of guesses, both correct and incorrect
        g.drawString("Total Guesses: " + (wrongGuesses + correctGuesses), 400, 175);
        g.drawString("Incorrect Guesses: " + wrongGuesses, 400, 200);
        g.drawString("Correct Guesses: " + correctGuesses, 400, 225);
    }

    private void drawHangman() {
        // set the color and stroke to draw the hangman
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(8));

        // draw hangman and rope thing based on the center position
        int x = HANGMAN_POSITION.x;
        int y = HANGMAN_POSITION.y;

        // draw the thing holding the hangman
        g.drawLine(x - 175, 500, x - 25, 500);
        g.drawLine(x - 100, 500, x - 100, 200);
        g.drawLine(x - 100, 200, x, 200);
        g.drawLine(x, y - 175, x, y - 130);

        // draw based on the number of wrong guesses
        switch(wrongGuesses) {
            case 6:
                g.drawLine(x - 50, y + 100, x, y + 50);
            case 5:
                g.drawLine(x, y + 50, x + 50, y + 100);
            case 4:
                g.drawLine(x - 50, y - 25, x, y);
            case 3:
                g.drawLine(x, y, x + 50, y - 25);
            case 2:
                g.drawLine(x, y - 50, x, y + 50);
            case 1:
                g.drawOval(x - 40, y - 130, 80, 80);
        }
    }

    // draw dashes for each letter and the letter if it has been guessed
    private void drawWord() {
        int letterCount = word.length();
        for (int i = 0; i < letterCount; i++) {
            g.drawLine(300 + i * 35, 350, 320 + i * 35, 350);
            if (guessedLetters.contains(word.toUpperCase().charAt(i))) {
                g.drawString(word.charAt(i) + "", 300 + i * 35, 320);
            }
        }
    }

    // the game is over if the word has been guessed or 
    // there have been 6 wrong guesses
    private boolean isGameOver() {
        if (wrongGuesses >= 6)
            return true;

        for (char letter : word.toUpperCase().toCharArray()) {
            if (!guessedLetters.contains(letter))
                return false;
        }

        return true;
    }

    // reset all variables and pick a new random word,
    // making sure it isn't the same as the last word
    private void reset() {
        wrongGuesses = 0;
        
        String newWord;
        do {
            newWord = WORDS[(int) (Math.random() * WORDS.length)];
        } while(word.equals(newWord));
        this.word = newWord;

        guessedLetters = new ArrayList<>();
        lastGuessWrong = false;
        wrongGuesses = 0;
        correctGuesses = 0;
    }
}
