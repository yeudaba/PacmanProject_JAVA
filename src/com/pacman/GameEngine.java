//Created by:
//Student name: Yeuda Baza
//ID: 208029819
package com.pacman;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameEngine {
    private static GameEngine game = null; // Singleton instance of GameEngine

    // 2D array representing the game board with different entity types
    public final int[][] board = {
            {2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2, 2},
            {2, 1, 1, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 1, 1, 2},
            {2, 1, 2, 2, 2, 0, 1, 1, 1, 1, 1, 0, 2, 2, 2, 1, 2},
            {0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 2, 2, 2, 1, 2, 2, 2, 0, 1, 1, 1, 1},
            {1, 2, 2, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0 ,2, 2, 1},
            {1, 2, 2, 1, 2, 0, 1, 2, 2, 2, 1, 0, 2, 1, 2, 2, 1},
            {1, 1, 1, 1, 2, 0, 1, 1, 3, 1, 1, 0, 2, 1, 1, 1, 1},
            {1, 2, 2, 1, 2, 0, 1, 2, 2, 2, 1, 0, 2, 1, 2, 2, 1},
            {1, 2, 2, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0 ,2, 2, 1},
            {1, 1, 1, 1, 0, 2, 2, 2, 1, 2, 2, 2, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0},
            {2, 1, 2, 2, 2, 0, 1, 1, 1, 1, 1, 0, 2, 2, 2, 1, 2},
            {2, 1, 1, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 1, 1, 2},
            {2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2, 2}
    };

    private final Random random = new Random(); // Random object for generating random values
    private final ArrayList<Entity> ghosts = new ArrayList<>(); // List to hold ghost entities
    private final ArrayList<Entity> walls = new ArrayList<>(); // List to hold wall entities
    private final ArrayList<Entity> gums = new ArrayList<>(); // List to hold gum entities
    private int timeElapsed = 0; // Counter for time elapsed in the game
    private boolean running = false; // Flag to check if the game is running
    private boolean firstRun = true; // Flag to check if it's the first run of the game

    private GameEngine() {
        init(); // Initialize the game engine
    }

    public static GameEngine instance() {
        if (game == null) { // Check if the instance is null
            game = new GameEngine(); // Create a new instance if it is
        }
        return game; // Return the singleton instance
    }

    public boolean isFirstRun() {
        return firstRun; // Return if it's the first run
    }

    public ArrayList<Entity> getWalls() {
        return walls; // Return the list of walls
    }

    public ArrayList<Entity> getGums() {
        return gums; // Return the list of gums
    }

    public ArrayList<Entity> getGhosts() {
        return ghosts; // Return the list of ghosts
    }

    private void init() {
        walls.clear(); // Clear the walls list
        ghosts.clear(); // Clear the ghosts list
        gums.clear(); // Clear the gums list

        // Populate the walls and gums based on the board configuration
        for (int i = 0; i < GameWindow.TILE_COUNT; i++) {
            for (int j = 0; j < GameWindow.TILE_COUNT; j++) {
                if (board[i][j] == EntityType.GUM) { // Check for gum objects
                    gums.add(new Gum(GameWindow.TILE_SIZE * j, GameWindow.TILE_SIZE * i, 3)); // Add gum to the list
                }
                if (board[i][j] == EntityType.WALL) { // Check for wall objects
                    walls.add(new Wall(GameWindow.TILE_SIZE * j, GameWindow.TILE_SIZE * i, GameWindow.TILE_SIZE)); // Add wall to the list
                }
            }
        }
        // Initialize ghosts
        Random random = new Random(); // Create a new random object
        int idx = random.nextInt(gums.size()); // Get a random index for gum
        Ghost ghost = new Ghost(gums.get(idx).XPos(), gums.get(idx).YPos(), 20); // Create a new ghost at the gum's position
        ghosts.add(ghost); // Add the ghost to the list

        // Create additional ghosts based on the prototype pattern
        for(int i=0; i<4; i++) {
            Ghost newGhost = ghost.copy(); // Create a copy of the ghost
            idx = random.nextInt(gums.size()); // Get a new random index for gum
            newGhost.setPos(gums.get(idx).XPos(), gums.get(idx).YPos()); // Set the new position for the ghost
            newGhost.setImage(String.format("ghost%d.png", i+1)); // Set the image for the ghost
            ghosts.add(newGhost); // Add the new ghost to the list
        }
    }

    public boolean isRunning() {
        return running; // Return if the game is currently running
    }

    public void start() {
        if (!firstRun) // If it's not the first run
            init(); // Reinitialize the game
        firstRun = false; // Set firstRun to false
        Pacman.instance().start(); // Start the Pacman instance
        for (Entity ghost : ghosts) {
            ((Ghost) ghost).start(); // Start each ghost
        }
        running = true; // Set running to true
    }

    public void stop() {
        Pacman.instance().stop(); // Stop the Pacman instance
        for (Entity ghost : ghosts) {
            ((Ghost) ghost).stop(); // Stop each ghost
        }
        running = false; // Set running to false
    }

    public boolean isLose() {
        return Pacman.instance().getLiveState() instanceof StateDead; // Check if Pacman is dead
    }

    public boolean isWon() {
        boolean allDestroyed = true; // Flag to check if all gums are destroyed
        for (Entity gum : gums) {
            if (gum.isDestroyed()) // If the gum is destroyed
                continue; // Continue to the next gum
            allDestroyed = false; // Set flag to false if any gum is not destroyed
            break; // Exit the loop
        }
        return allDestroyed; // Return if all gums are destroyed
    }

    public boolean isEnd() {
        if (isLose()) // Check if the game is lost
            return true; // Return true if lost
        return isWon(); // Return the result of isWon
    }

    public void notifyKeyPress(int key) {
        if (key == GameControlKey.KEY_GAME_START && !running) { // Check for game start key
            start(); // Start the game
        }
        if (key == GameControlKey.KEY_GAME_EXIT && !running) { // Check for game exit key
            System.exit(0); // Exit the application
        }
        Pacman.instance().notifyKeyPress(key); // Notify Pacman of the key press
    }

    public void render(Graphics2D g) {
        Pacman.instance().render(g); // Render the Pacman instance
        for (Entity gum : gums) {
            gum.render(g); // Render each gum
        }
        for (Entity ghost : ghosts) {
            ghost.render(g); // Render each ghost
        }
        timeElapsed++; // Increment the time elapsed
        timeElapsed %= 300; // Reset timeElapsed every 300 ticks
        if (timeElapsed == 0 && !isEnd()) { // Check if it's time to destroy/revive ghosts
            int maxGhosts = 5; // Maximum number of ghosts
            int liveGhosts = random.nextInt(maxGhosts) + 1; // Random number of live ghosts
            int ghostCount = 0; // Counter for live ghosts
            for (Entity ghost : ghosts) {
                if (ghostCount < liveGhosts) { // If the count is less than liveGhosts
                    if (ghost.isDestroyed()) { // If the ghost is destroyed
                        int idx = random.nextInt(gums.size()); // Get a random index for gum
                        ghost.setPos(gums.get(idx).XPos(), gums.get(idx).YPos()); // Set the ghost's position
                        ((Ghost) ghost).revive(); // Revive the ghost
                    }
                } else
                    ghost.destroy(); // Destroy the ghost if count exceeds liveGhosts
                ghostCount++; // Increment the ghost count
            }
        }
    }

    public Entity collideEntity(Entity e, int entityType) {
        if (e.isDestroyed()) // Check if the entity is destroyed
            return null; // Return null if it is
        ArrayList<Entity> entities = new ArrayList<>(); // List to hold entities for collision
        if (entityType == EntityType.WALL)
            entities = game.getWalls(); // Get walls if entityType is WALL
        if (entityType == EntityType.GUM)
            entities = game.getGums(); // Get gums if entityType is GUM
        if (entityType == EntityType.GHOST)
            entities = game.getGhosts(); // Get ghosts if entityType is GHOST

        int offset = (GameWindow.TILE_SIZE - e.Size()) / 2; // Calculate offset for bounding box
        Rectangle bBox = new Rectangle(e.XPos() + offset, e.YPos() + offset, e.Size(), e.Size()); // Create bounding box
        for (Entity other : entities) {
            if (other.isDestroyed()) continue; // Skip destroyed entities
            if (other.boundingBox().intersects(bBox)) // Check for intersection
                return other; // Return the colliding entity
        }
        return null; // Return null if no collision
    }
}

