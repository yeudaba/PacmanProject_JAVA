Created by:
Student name: Yeuda Baza
ID: 208029819
1. How to run Pacman Game
- Requirements
Please make sure you have installed Java on your machine.
- Run
Please unzip Pacman.jar file from the zip file, which is compiled Pacman game.
Navigate to the directory where Pacman.jar is located.

Open terminal and run following command

java -jar Pacman.jar

You can also run the game any JAVA ide (I used IntelliJ).


2. How to play Pacman Game
Once you execute the command, the Pacman game window should appear.
You can start game to press 's' key or exit game to press 'x'.

Use arrow keys to move pacman through the maze. 
Number of lives of Pacman and gums eaten are shown on game window while playing.
When you win or loose, game shows state and you can start new game or exit game.

3. Design pattern used
- Singlton 
It ensures Paman and GameEngine classes have only one instance and provides a global access point to them. It is implemented in Pacama.java and GameEngine.java.

- Observer
It allows Pacman object to notify Board object about change of lives and gums eaten. 
Pacman's PropertyChangeSupport notifies observer(Board object) about changes in Pacman's live state and the number of gums eaten.
It is implemented in Pacman.java and Board.java.

- State
It allows game to stop when number of lives of Pacman object becomes zero. Pacman object has State object and changes state.
It is implemented in Pacman.java.

- Factory Method
It creates several objects like Pacman, Ghost, Gum and Wall object.
It is implemented in GameEngine.java.

- Prototype
It allows cloning Ghost objects without coupling to it's specific classe.
Ghost class has copy method to clone ghost with different colors.
It is implemented in Ghost.java.
