//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

public abstract class State { // Abstract class representing the state of a game character
    protected int numOfLives; // Variable to store the number of lives

    // Constructor to initialize the number of lives
    public State(int lives) {
        this.numOfLives = lives; // Assigning the passed lives to the class variable
    }

    // Method to retrieve the current number of lives
    public int getLives() {
        return numOfLives; // Returning the number of lives
    }

    // Method to notify the Pacman object about a change in lives state
    public void notifyChange(Pacman pacman) {
        pacman.notifyLivesState(); // Invoking the notifyLivesState method on the Pacman object
    }

    // Abstract method to define the next state of Pacman
    public abstract void next(Pacman pacman); // This method must be implemented by subclasses
}

