//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Board extends JPanel implements PropertyChangeListener { // Board class extending JPanel and implementing PropertyChangeListener
    private int lives = 3; // Variable to track the number of lives
    private int score = 0; // Variable to track the score
    private ArrayList<Image> imageLives = new ArrayList<>(); // List to hold images representing lives

    public Board() { // Constructor for the Board class
        setPreferredSize(new Dimension(360, 30)); // Setting the preferred size of the board
        setBackground(Color.black); // Setting the background color to black
        for(int i=0; i<4; i++) { // Loop to load life images
            try {
                // Loading the image for each life and adding it to the list
                Image img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("lives_%d.png", i))));
                imageLives.add(img); // Adding the loaded image to the imageLives list
            }
            catch(IOException e) { // Catching any IOException that may occur
                e.printStackTrace(); // Printing the stack trace for debugging
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) { // Overriding paintComponent method for custom painting
        super.paintComponent(g); // Calling the superclass method to ensure proper rendering
        drawGameInfo(g); // Calling method to draw game information
    }

    private void drawGameInfo(Graphics g) { // Method to draw game information on the board
        Graphics2D g2d = (Graphics2D) g; // Casting Graphics to Graphics2D for advanced control
        g2d.setColor(Color.green); // Setting the color for drawing text to green
        g2d.setFont(new Font("Courier", Font.PLAIN, 20)); // Setting the font for the text
        // Image img = imageLives.get(Math.max(lives, 0));
        // g.drawImage(img, 10, 10, 350, 150, null);
        g2d.drawString(String.format("Lives: %d", Math.max(lives, 0)), 10, 25); // Drawing the number of lives on the board
        g2d.drawString(String.format("Gums Eaten: %d", score), 190, 25); // Drawing the score on the board
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { // Handling property change events
        String propertyName = evt.getPropertyName(); // Retrieving the name of the property that changed
        if ("eat".equals(propertyName)) { // Checking if the property is "eat"
            score = (int) evt.getNewValue(); // Updating the score with the new value
        } else if ("hit".equals(propertyName)) { // Checking if the property is "hit"
            lives = (int) evt.getNewValue(); // Updating the lives with the new value
        }
        repaint(); // Requesting a repaint to update the display
    }
}
