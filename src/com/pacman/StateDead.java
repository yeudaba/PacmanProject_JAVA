//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

// Class representing the 'Dead' state of Pacman
public class StateDead extends State {

    // Constructor for StateDead, initializing with the number of lives
    public StateDead(int lives) {
        super(lives); // Call to the parent class constructor to set lives
    }

    // Method to define the next state of Pacman when in the 'Dead' state
    @Override
    public void next(Pacman pacman) {
        // no action is defined for transitioning from the 'Dead' state
    }
}
