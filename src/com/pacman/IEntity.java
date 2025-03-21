//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import java.awt.*;

public interface IEntity {
    // Method to get the size of the entity
    int Size();

    // Method to set the size of the entity
    void setSize(int size);

    // Method to get the X position of the entity
    int XPos();

    // Method to get the Y position of the entity
    int YPos();

    // Method to set the position of the entity using X and Y coordinates
    void setPos(int posX, int posY);

    // Method to get the bounding box of the entity, represented as a Rectangle
    Rectangle boundingBox();

    // Method to render the entity using Graphics2D
    void render(Graphics2D g);
}
