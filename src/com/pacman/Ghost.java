//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Ghost extends Entity implements Runnable, IMovable {

    private final Random random; // Random object for generating random directions
    private final int speed; // Speed of the ghost
    private int speedX; // Speed in the X direction
    private int speedY; // Speed in the Y direction
    private int stepsInTile; // Steps taken in the current tile
    private Image imgGhost; // Image representation of the ghost
    private boolean running = false; // Flag to indicate if the ghost is running

    // Constructor to initialize the ghost's position, size, and image
    public Ghost(int posX, int posY, int size) {
        super(posX, posY, size); // Call to the superclass constructor
        try {
            // Load the ghost image from resources
            imgGhost = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("ghost4.png")));
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if image loading fails
        }
        speedX = 0; // Initialize X speed to 0
        speedY = 0; // Initialize Y speed to 0
        stepsInTile = 0; // Initialize steps in tile to 0
        speed = GameWindow.TILE_SIZE / GameWindow.STEPS_IN_TILE; // Calculate speed based on game settings

        random = new Random(); // Initialize random object
        Thread thread = new Thread(this, "Ghost-Thread"); // Create a new thread for the ghost
        thread.start(); // Start the ghost thread
    }

    // Method to create a copy of the ghost
    public Ghost copy() {
        return new Ghost(XPos(), YPos(), Size()); // Return a new Ghost instance with the same position and size
    }

    // Method to set a new image for the ghost
    public void setImage(String imgName) {
        try {
            // Load the new ghost image from resources
            imgGhost = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(imgName)));
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if image loading fails
        }
    }

    // Method to revive the ghost
    public void revive() {
        destroyed = false; // Set the destroyed flag to false
    }

    // Method to start the ghost's movement
    public void start() {
        running = true; // Set running flag to true
    }

    // Method to stop the ghost's movement
    public void stop() {
        running = false; // Set running flag to false
    }

    // Method to render the ghost on the screen
    @Override
    public void render(Graphics2D g) {
        if (destroyed) // Check if the ghost is destroyed
            return; // Exit if the ghost is destroyed
        int pos = (GameWindow.TILE_SIZE - size) / 2; // Calculate position for centering the ghost
        g.drawImage(imgGhost, xPos + pos, yPos + pos, size, size, null); // Draw the ghost image
    }

    // Method that runs in a separate thread to update the ghost's state
    @Override
    public void run() {
        while (true) { // Infinite loop for continuous updating
            if (running) // Check if the ghost is running
                update(); // Update the ghost's state
            try {
                Thread.sleep(GameWindow.UPDATE_TIME_INTERVAL * 4); // Pause the thread for a specified interval
            } catch (InterruptedException e) {
                e.printStackTrace(); // Print stack trace if interrupted
                break; // Exit the loop if interrupted
            }
        }
    }

    // Method to update the ghost's position and state
    @Override
    public synchronized void update() {
        stepsInTile %= GameWindow.STEPS_IN_TILE; // Reset steps in tile if it exceeds the limit
        if (stepsInTile == 0) { // Check if it's time to change direction
            // set random direction
            int direction = random.nextInt(4); // Generate a random direction (0-3)
            speedX = 0; // Reset X speed
            speedY = 0; // Reset Y speed
            switch (direction) { // Determine speed based on the random direction
                case 0:
                    speedY = -speed; // Move up
                    break;
                case 1:
                    speedY = speed; // Move down
                    break;
                case 2:
                    speedX = -speed; // Move left
                    break;
                case 3:
                    speedX = speed; // Move right
                    break;
                default:
                    break; // No movement
            }
        }

        // update position
        xPos += speedX; // Update X position
        yPos += speedY; // Update Y position

        // check collision with wall
        if (GameEngine.instance().collideEntity(this, EntityType.WALL) != null) { // Check for collision with walls
            // return back
            xPos -= speedX; // Revert X position
            yPos -= speedY; // Revert Y position
            stepsInTile = 0; // Reset steps in tile
        } else {
            stepsInTile++; // Increment steps in tile
        }

        // keep entity in frame
        int boundMax = GameWindow.TILE_SIZE * (GameWindow.TILE_COUNT - 1); // Calculate maximum bounds
        xPos = Math.min(Math.max(xPos, 0), boundMax); // Keep X position within bounds
        yPos = Math.min(Math.max(yPos, 0), boundMax); // Keep Y position within bounds
    }
}
