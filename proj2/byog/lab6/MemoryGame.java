package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        int x = width / 2;
        int y = height / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        if(!gameOver){
            Font smallFont = new Font("Arial", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(x, height - 1, playerTurn? "Type!": "Watch!");
            StdDraw.textRight(width - 1, height - 1, "You're a star!");
            StdDraw.line(0, height - 2, width, height - 2);
        }

        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(500);
            drawFrame(" ");
            StdDraw.pause(500);
        }
    }

    /** Read keystrokes char and return string*/
    public String solicitNCharsInput(int n) {
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            drawFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        playerTurn = false;
        //TODO: Establish Game loop
        for(round = 1; !gameOver; round++) {
            String msg = "Round " + round;
            drawFrame(msg);
            StdDraw.pause(1500);
            String puzzle = generateRandomString(round);
            flashSequence(puzzle);
            playerTurn = true;
            String input = solicitNCharsInput(round);
            playerTurn = false;
            if(!input.equals(puzzle))
                gameOver = true;
        }
        round -= 1;
        String failString = "Game Over! You made it to round: " + round;
        drawFrame(failString);
    }

}
