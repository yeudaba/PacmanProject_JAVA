//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import java.awt.*;

public class Wall extends Entity { // Class Wall extends the Entity class
    public Wall(int posX, int posY, int size) { // Constructor for Wall class, initializes position and size
        super(posX, posY, size); // Calls the constructor of the superclass Entity with position and size
    }

    @Override
    public void render(Graphics2D g) { // Overridden render method to draw the Wall
    }
}
