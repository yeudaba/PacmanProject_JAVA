
//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import java.awt.*;

public class Gum extends Entity { // Class Gum extends the Entity class, representing a gum object
    private static final Color GUM_COLOR = Color.YELLOW; // Constant color for the gum, set to yellow

    public Gum(int posX, int posY, int size) { // Constructor for Gum, initializing position and size
        super(posX, posY, size); // Call to the superclass constructor to set position and size
    }

    @Override
    public void render(Graphics2D g2d) { // Method to render the gum on the graphics context
        if (isDestroyed()) { // Check if the gum is destroyed
            return; // Exit the method if the gum is destroyed
        }
        drawGum(g2d); // Call to draw the gum if it is not destroyed
    }

    private void drawGum(Graphics2D g2d) { // Method to draw the gum on the graphics context
        g2d.setColor(GUM_COLOR); // Set the color for drawing to the gum color
        int offset = calculateOffset(); // Calculate the offset for centering the gum
        g2d.fillOval(xPos + offset, yPos + offset, size, size); // Draw the gum as a filled oval
    }

    private int calculateOffset() { // Method to calculate the offset for centering the gum
        return (GameWindow.TILE_SIZE - size) / 2; // Return the calculated offset based on tile size and gum size
    }
}
