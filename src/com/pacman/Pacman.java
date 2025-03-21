//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Objects;

public class Pacman extends Entity implements IMovable, Runnable {

    // Initial position constants for Pacman
    private static final int initialXPos = GameWindow.TILE_SIZE * 8; // X position
    private static final int initialYPos = GameWindow.TILE_SIZE * 8; // Y position
    private static Pacman pacman = null; // Singleton instance of Pacman
    private final PropertyChangeSupport changeSupport; // Support for property change listeners
    private Image imageLeft, imageRight, imageUp, imageDown, imageInitial, imageCurrent; // Images for different directions
    private State liveState; // Current state of Pacman for state pattern
    private int controlKey = GameControlKey.KEY_NONE; // Current control key pressed
    private int speed, speedX, speedY, stepsInTile, eatenGums; // Movement and game state variables
    private boolean running; // Indicates if Pacman is running

    // Private constructor for singleton pattern
    private Pacman(int posX, int posY, int size) {
        super(posX, posY, size); // Call to superclass constructor
        try {
            // Load images for Pacman in different directions
            imageLeft = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pacman_left.png")));
            imageRight = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pacman_right.png")));
            imageUp = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pacman_up.png")));
            imageDown = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pacman_down.png")));
            imageInitial = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pacman.png")));
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if image loading fails
        }

        changeSupport = new PropertyChangeSupport(this); // Initialize property change support

        initialize(); // Initialize game state

        // Create and start a new thread for Pacman
        Thread thread = new Thread(this, "Pacman-Thread");
        thread.start();
    }

    // Singleton instance method
    public static Pacman instance() {
        if (pacman == null) {
            pacman = new Pacman(initialXPos, initialYPos, 20); // Create new instance if not already created
        }
        return pacman; // Return the singleton instance
    }

    // Initialize game state variables
    private void initialize() {
        speedX = 0; // Horizontal speed
        speedY = 0; // Vertical speed
        stepsInTile = 0; // Steps taken in the current tile
        eatenGums = 0; // Count of eaten gums
        setLiveState(new StateLive(3)); // Set initial live state with 3 lives
        controlKey = GameControlKey.KEY_NONE; // No control key pressed initially
        imageCurrent = imageInitial; // Set current image to initial
        speed = GameWindow.TILE_SIZE / GameWindow.STEPS_IN_TILE; // Calculate speed based on tile size
        running = false; // Set running state to false
    }

    // Getter for live state
    public State getLiveState() {
        return liveState; // Return current live state
    }

    // Setter for live state
    public void setLiveState(State liveState) {
        this.liveState = liveState; // Update live state
    }

    // Add a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener); // Add listener to change support
    }

    // Remove a property change listener
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener); // Remove listener from change support
    }

    // Notify listeners about the change in lives state
    public void notifyLivesState() {
        changeSupport.firePropertyChange("hit", liveState.getLives() + 1, liveState.getLives()); // Notify about life change
    }

    // Notify key press event
    public void notifyKeyPress(int key) {
        controlKey = key; // Update control key based on key press
    }

    // Start the game
    public void start() {
        initialize(); // Reinitialize game state
        running = true; // Set running state to true
        changeSupport.firePropertyChange("hit", 0, 3); // Notify listeners about initial lives
        changeSupport.firePropertyChange("eat", 100, 0); // Notify listeners about initial score
    }

    // Stop the game
    public void stop() {
        running = false; // Set running state to false
    }

    @Override
    public synchronized void update() {
        // Update direction when entity is in center of tile
        stepsInTile %= GameWindow.STEPS_IN_TILE; // Reset stepsInTile if it exceeds tile steps
        if (stepsInTile == 0) { // Check if at the center of the tile
            speedX = 0; // Reset horizontal speed
            speedY = 0; // Reset vertical speed
            switch(controlKey) { // Determine direction based on control key
                case GameControlKey.KEY_MOVE_UP:
                    speedY = -speed; // Move up
                    imageCurrent = imageUp; // Set current image to up
                    break;
                case GameControlKey.KEY_MOVE_DOWN:
                    speedY = speed; // Move down
                    imageCurrent = imageDown; // Set current image to down
                    break;
                case GameControlKey.KEY_MOVE_LEFT:
                    speedX = -speed; // Move left
                    imageCurrent = imageLeft; // Set current image to left
                    break;
                case GameControlKey.KEY_MOVE_RIGHT:
                    speedX = speed; // Move right
                    imageCurrent = imageRight; // Set current image to right
                    break;
                default:
                    break; // No movement
            }
        }

        // Update position based on speed
        xPos += speedX; // Update X position
        yPos += speedY; // Update Y position

        // Check collision with wall
        if (GameEngine.instance().collideEntity(this, EntityType.WALL) != null) {
            // Return back if collision detected
            xPos -= speedX; // Revert X position
            yPos -= speedY; // Revert Y position
            stepsInTile = 0; // Reset steps in tile
        } else {
            stepsInTile++; // Increment steps in tile
        }

        // Check collision with gum
        Entity gum = GameEngine.instance().collideEntity(this, EntityType.GUM);
        if (gum != null) {
            gum.destroy(); // Destroy the gum entity
            changeSupport.firePropertyChange("eat", eatenGums, ++eatenGums); // Notify listeners about gum eaten
        }

        // Check collision with ghost
        if (GameEngine.instance().collideEntity(this, EntityType.GHOST) != null) {
            liveState.next(this); // Update live state
            liveState.notifyChange(this); // Notify state change

            // Reset position and state upon collision
            xPos = initialXPos; // Reset X position
            yPos = initialYPos; // Reset Y position
            stepsInTile = 0; // Reset steps in tile
            speedX = 0; // Reset horizontal speed
            speedY = 0; // Reset vertical speed
            controlKey = GameControlKey.KEY_NONE; // Reset control key
        }

        // Keep entity within frame boundaries
        int boundMax = GameWindow.TILE_SIZE * (GameWindow.TILE_COUNT - 1); // Calculate maximum bounds
        xPos = Math.min(Math.max(xPos, 0), boundMax); // Constrain X position within bounds
        yPos = Math.min(Math.max(yPos, 0), boundMax); // Constrain Y position within bounds
    }

    @Override
    public void render(Graphics2D g2d) {
        int offset = (GameWindow.TILE_SIZE - size) / 2; // Calculate offset for centering
        g2d.drawImage(imageCurrent, xPos + offset, yPos + offset, size, size, null); // Draw current image at position
    }

    @Override
    public void run() {
        while (true) { // Continuous loop for updating
            if (running) // Check if running
                update(); // Update game state
            try {
                Thread.sleep(GameWindow.UPDATE_TIME_INTERVAL); // Sleep for update interval
            } catch (InterruptedException e) {
                e.printStackTrace(); // Print stack trace if interrupted
                break; // Exit loop on interruption
            }
        }
    }
}
