//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame { // Class declaration for MainFrame extending JFrame

    private static final int FRAME_WIDTH = 340; // Constant for frame width
    private static final int FRAME_HEIGHT = 340; // Constant for frame height

    public MainFrame() { // Constructor for MainFrame
        initializeFrame(); // Call method to initialize the frame settings
        addComponentsToFrame(); // Call method to add components to the frame
    }

    private void initializeFrame() { // Method to set up the frame properties
        setTitle("Pacman"); // Set the title of the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set the default close operation
        setResizable(false); // Disable resizing of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void addComponentsToFrame() { // Method to add components to the frame
        JPanel mainPanel = createMainPanel(); // Create the main panel
        setContentPane(mainPanel); // Set the content pane to the main panel
        pack(); // Adjust the frame to fit the preferred sizes of its components
        setVisible(true); // Make the frame visible
    }

    private JPanel createMainPanel() { // Method to create the main panel
        JPanel mainPanel = new JPanel(); // Instantiate a new JPanel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout

        GameWindow gamePanel = new GameWindow(); // Create an instance of GameWindow
        gamePanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); // Set preferred size for game panel
        gamePanel.getBoardPanel().setPreferredSize(new Dimension(FRAME_WIDTH, 30)); // Set preferred size for board panel

        mainPanel.add(gamePanel.getBoardPanel()); // Add board panel to main panel
        mainPanel.add(gamePanel); // Add game panel to main panel
        return mainPanel; // Return the constructed main panel
    }

    public static void main(String[] args) { // Main method to launch the application
        EventQueue.invokeLater(() -> { // Schedule a job for the event dispatch thread
            new MainFrame(); // Create a new instance of MainFrame
        });
    }
}

