//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameWindow extends JPanel implements Runnable, KeyListener {
    // Constant defining the size of each tile in the game
    public static final int TILE_SIZE = 20;
    // Constant defining the number of tiles in the game
    public static final int TILE_COUNT = 17;
    // Constant defining the number of steps within a tile
    public static final int STEPS_IN_TILE = 4;
    // Constant defining the update time interval in milliseconds
    public static final int UPDATE_TIME_INTERVAL = 20; // ms
    // Constant defining the width of the game window
    private static final int WINDOW_WIDTH = 340;
    // Constant defining the height of the game window
    private static final int WINDOW_HEIGHT = 340;

    // BufferedImage to hold the scene image
    private final BufferedImage imageScene;
    // Board object representing the game board
    private final Board boardPanel;
    // Image for the background of the game
    private Image imageBackground;
    // Thread for running the game loop
    private Thread gameThread;
    // Variable to hold the current control key pressed
    private int controlKey;

    // Constructor for initializing the game window
    public GameWindow() {
        setFocusable(true); // Allow the window to receive focus
        requestFocus(); // Request focus for the window
        loadBackgroundImage(); // Load the background image
        // Initialize the image scene with specified width and height
        imageScene = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        // Initialize the board panel
        boardPanel = new Board();
        // Add property change listener to the Pacman instance
        Pacman.instance().addPropertyChangeListener(boardPanel);
        // Set the initial control key to none
        controlKey = GameControlKey.KEY_NONE;
        // Add key listener to the window
        addKeyListener(this);
    }

    // Method to load the background image from resources
    private void loadBackgroundImage() {
        try {
            // Read the background image from the resources
            imageBackground = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("board.png")));
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of an IOException
        }
    }

    // Getter method to retrieve the board panel
    public Board getBoardPanel() {
        return boardPanel;
    }

    // Override method to add notification for the game thread
    @Override
    public void addNotify() {
        super.addNotify(); // Call the superclass method
        // Check if the game thread is not already running
        if (gameThread == null) {
            // Create a new thread for the game
            gameThread = new Thread(this, "GameThread");
            gameThread.start(); // Start the game thread
        }
    }

    // Method to display the introductory screen
    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(Color.white); // Set color for the intro screen background
        // Fill a rectangle for the intro screen
        g2d.fillRect(50, WINDOW_HEIGHT / 2 - 100, WINDOW_WIDTH - 100, 70);
        g2d.setColor(Color.cyan); // Set color for the rectangle border
        // Draw the border of the intro screen rectangle
        g2d.drawRect(50, WINDOW_HEIGHT / 2 - 100, WINDOW_WIDTH - 100, 70);

        // Get the game title
        String title = getGameTitle();
        // Draw the centered game title string
        drawCenteredString(g2d, title, new Font("Helvetica", Font.BOLD, 20), Color.red, WINDOW_HEIGHT / 2 - 70 + 5);

        // String for the game guide
        String strGuide = "Press 's' to start or 'x' to exit.";
        // Draw the centered guide string
        drawCenteredString(g2d, strGuide, new Font("Helvetica", Font.BOLD, 12), Color.black, WINDOW_HEIGHT / 2 - 70 + 30);
    }

    // Method to get the game title based on the game state
    private String getGameTitle() {
        // Check if it's the first run of the game
        if (GameEngine.instance().isFirstRun()) {
            return "New Game"; // Return title for new game
        } else if (GameEngine.instance().isWon()) {
            return "You won the game!"; // Return title for winning
        } else {
            return "You lost the game!"; // Return title for losing
        }
    }

    // Method to draw a centered string on the graphics context
    private void drawCenteredString(Graphics2D g2d, String text, Font font, Color color, int yPosition) {
        FontMetrics metrics = g2d.getFontMetrics(font); // Get font metrics for the specified font
        g2d.setColor(color); // Set the color for the string
        g2d.setFont(font); // Set the font for the string
        // Calculate the x position to center the string
        int xPosition = (WINDOW_WIDTH - metrics.stringWidth(text)) / 2;
        // Draw the string at the calculated position
        g2d.drawString(text, xPosition, yPosition);
    }

    // Method to draw the game frame
    private void drawFrame() {
        Graphics2D g2d = (Graphics2D) imageScene.getGraphics(); // Get graphics context for the image scene
        // Draw the background image
        g2d.drawImage(imageBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        // Render the game engine graphics
        GameEngine.instance().render(g2d);
        // Check if the game is not running
        if (!GameEngine.instance().isRunning()) {
            showIntroScreen(g2d); // Show the intro screen
        }
        g2d.dispose(); // Dispose of the graphics context
        // Draw the image scene onto the window
        this.getGraphics().drawImage(imageScene, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
    }

    // Override method to run the game loop
    @Override
    public void run() {
        while (true) {
            // GameEngine.instance().notifyKeyPress(controlKey); // Notify key press (commented out)
            drawFrame(); // Draw the current frame
            // Check if the game is running and has ended
            if (GameEngine.instance().isRunning() && GameEngine.instance().isEnd()) {
                GameEngine.instance().stop(); // Stop the game engine
            }
            try {
                Thread.sleep(UPDATE_TIME_INTERVAL); // Sleep for the update time interval
            } catch (InterruptedException e) {
                e.printStackTrace(); // Print stack trace in case of interruption
                break; // Break the loop if interrupted
            }
        }
    }

    // Override method for key typed event (not implemented)
    @Override
    public void keyTyped(KeyEvent e) {}

    // Override method for key pressed event
    @Override
    public void keyPressed(KeyEvent e) {
        // Switch case to handle different key presses
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controlKey = GameControlKey.KEY_MOVE_UP; // Set control key for moving up
                break;
            case KeyEvent.VK_DOWN:
                controlKey = GameControlKey.KEY_MOVE_DOWN; // Set control key for moving down
                break;
            case KeyEvent.VK_LEFT:
                controlKey = GameControlKey.KEY_MOVE_LEFT; // Set control key for moving left
                break;
            case KeyEvent.VK_RIGHT:
                controlKey = GameControlKey.KEY_MOVE_RIGHT; // Set control key for moving right
                break;
            case KeyEvent.VK_S:
                controlKey = GameControlKey.KEY_GAME_START; // Set control key for starting the game
                break;
            case KeyEvent.VK_SPACE:
                controlKey = GameControlKey.KEY_GAME_STOP; // Set control key for stopping the game
                break;
            case KeyEvent.VK_X:
                controlKey = GameControlKey.KEY_GAME_EXIT; // Set control key for exiting the game
                break;
            default:
                break; // Do nothing for unhandled keys
        }
        // Notify the game engine of the key press
        GameEngine.instance().notifyKeyPress(controlKey);
    }

    // Override method for key released event (not implemented)
    @Override
    public void keyReleased(KeyEvent e) {}
}

