//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

public class StateLive extends State { // Class StateLive extends the State class to represent the live state of Pacman.
    public StateLive(int lives) { // Constructor that initializes the StateLive with a specified number of lives.
        super(lives); // Calls the constructor of the parent class State to set the number of lives.
    }

    @Override // Annotation indicating that this method overrides a method in the parent class.
    public void next(Pacman pacman) { // Method to transition to the next state based on Pacman's lives.
        numOfLives--; // Decrease the number of lives by one.
        if (numOfLives < 0) { // Check if the number of lives is less than zero.
            pacman.setLiveState(new StateDead(numOfLives)); // If lives are less than zero, change Pacman's state to StateDead.
        }
    }
}
