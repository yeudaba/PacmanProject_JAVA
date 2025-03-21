//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import java.awt.*;

public abstract class Entity implements IEntity {
    // Position on the X-axis
    protected int xPos;
    // Position on the Y-axis
    protected int yPos;
    // Size of the entity
    protected int size;
    // Flag indicating if the entity is destroyed
    protected boolean destroyed;

    // Constructor to initialize the entity's position and size
    public Entity(int xPos, int yPos, int size) {
        this.xPos = xPos; // Set the X position
        this.yPos = yPos; // Set the Y position
        this.size = size; // Set the size of the entity
    }

    // Method to get the size of the entity
    public int Size() {
        return size; // Return the size
    }

    // Method to set the size of the entity
    public void setSize(int size) {
        this.size = size; // Update the size
    }

    // Method to get the X position of the entity
    public int XPos() {
        return xPos; // Return the X position
    }

    // Method to get the Y position of the entity
    public int YPos() {
        return yPos; // Return the Y position
    }

    // Method to set the position of the entity
    public void setPos(int posX, int posY) {
        this.xPos = posX; // Update the X position
        this.yPos = posY; // Update the Y position
    }

    // Method to check if the entity is destroyed
    public boolean isDestroyed() {
        return destroyed; // Return the destroyed status
    }

    // Method to mark the entity as destroyed
    public void destroy() {
        destroyed = true; // Set the destroyed flag to true
    }

    // Method to get the bounding box of the entity for collision detection
    public Rectangle boundingBox() {
        int offset = (GameWindow.TILE_SIZE - size) / 2; // Calculate the offset for centering
        return new Rectangle(xPos + offset, yPos + offset, size, size); // Return the bounding box
    }

    // Abstract method to render the entity, to be implemented by subclasses
    public abstract void render(Graphics2D g);
}
