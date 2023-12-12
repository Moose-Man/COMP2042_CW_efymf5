# COMP2042_CW_efymf5

Compilation Instructions
-
- these assume intellij usage
1. Extract the project from the zip file
2. Open a new project
3. delete the main folder of the new project
4. replace it with the main file from my project
5. install the javafx library
6. and you should be good to go!



Implemented and Working Properly
-
- Main menu: when user opens game, a menu screen appears with 4 fully functioning clickable images: 'Start New Game', 'load game', 'Exit' and 'Score Board'.
- Score Board: when user clicks Score Board option from main menu, a dialogue box pops up with all the users winning scores. 
- Re-done start button: now a clickable image rather than a button, functions by calling the start method of the main class, which has been refactored to begin the game instantly instead of showing buttons.
- Re-done load game button: now a clickable image rather than a button, functions by calling the loadGame method of the main class.
- Exit button: clickable image that exits from the program.
- Main Menu styling: Main Menu has its own styling that appears whenever the menu is shown.
- Spinning 'ball' (shuriken in this version!): the shuriken, or 'ball' now spins when on screen.
- Load save functionality: prevously was not working, now reloads game state of saved game when clicked - change directory to match your file structure if necessary, should work normally regardless.
- Save functionality: previously was not working, now saves game state correctly when 's' key is clicked.
- Pause functionality added: pressing the 'p' key pauses the game, freezing the paddle and ball in place until the 'p' key is pressed again.

Implemented but Not Working Properly
-
- additional collision logic - ball clipping into blocks:
  - logic for ball and block collision detection has been modified in Block.java for the method 'checkHitToBlock' in order to make the ball bounce off of the blocks at its circumference rather than at its center - this feature does not work correctly, and the ball still often clips into blocks before bouncing.
  - steps taken to address the issue included adding ball radius to the conditional statements checking whether or not the ball was at the boundary of the block or not, and adding logic to check if the ball's circumference was within the bounds of the block or not. 
  - this should have made it so that the ball bounces at its circumference, but this is not the case.
- additional collision logic - ball bouncing off of the touchig edges of blocks
  - this can occur if the ball approaches the seam between two touching blocks, causing it to bounce off of that edge instead of the edge it is supposed to bounce off of. 
  - steps taken to address this included adding logic to check the balls direction before registering a collision, such that a ball cannot bounce in the same direction it is already moving in (this part works), and adding logic such that the ball cannot bounce off of an edge if it has the same x or y coordinate as the edge the ball bounced off of prior (this does not work).


Features Not Implemented
-
- I have not implemented a feature to add media effects into the program, due to a combination of lack of understanding of how I could do so, and the lack of time to investigate methods due to my late starting of this coursework.
- I have not implemented a feature to show clickable image views when the player pauses, as I cannot figure out how to get them off of the screen after the game is unpaused, resulting in them permanently staying there. They do not function as normal in this situation as well, so I opted not to implement them.


New Java Classes
-
- Menu.java - contains code for the main menu's implementation, including the styling of the menu page, the clickable image views and what happens when each image is clicked


Modified Java Classes
- 
- fixed spelling errors in variable names across the project file
- Main.java
    - start method has been completely refactored. Previously, when the program was run and the game window was opened, you would see two buttons, 'start new game' and 'load game'.
      - now, the buttons are gone and all trace of them has been removed from the program. The start method instead instantly begins the game once it is executed, which is after the 'restart' or 'start new game' images are clicked.
    - the loadGame method has been refactored slightly to clean up the code, taking its initializations into their own method 'loadGameState', which loadGame then calls upon execution.
    - loadGame method has also been ammended to change the background and ball image to those of the gold ball and gold background in the situation that the game was saved while the ball was gold.
    - new method showMenu is called whenever the program first runs or the 'back to main menu' image is clicked. serves to show the main menu to the user.
    - new method initializeGame re-initializes all necessary game values to what their values were at program startup. This was implemented after the 'back to main menu' button was added, since if a user chose to start a new game after clicking that button, and without having this feature, the values they would begin with would not be initialized correctly.
    - pause functionality added in the switch case managing player controls.
    - ball initialized x and y values were random, now they are set to an exact number to stop the ball spawning in unusual positions, costing the user a heart instantly, or appearing within the blocks, which it should not do.
    - modified the logic for collision with the boundaries of the game window to take into account ball radius, so that collisions at those boundaries look cleaner. -
    - modified code outputting "level up :)", since it did not appear when it was supposed to. solved by using Platform.runlater, to ensure that the javafx thread does not ignore this UI change when it is called if the thread is busy.-
    - prior to total removal of the 'start new game' and 'load game' buttons, they would briefly appear when the level changes. Modified code when next level is initialized to no longer include the start game and load game buttons as children of the root node.
      - this is a moot change now, since the buttons in question are gone, but still worth mentioning. the code in question can be found in my commit with tag 'fixed up loading and save game issues', at the if statement beginning line 136.

- Score.java
  - changed the buttons from being buttons to image views, so that stylization could be done easily.
  - got rid of all threading within the class as it was often a cause of the game crashing and the '+1' label becoming stuck on screen.
    - could not figure out how to improve the threading, so opted to remove it entirely and redesign the class using the java fx animation library.
  - added image views for the game over and game win screens, so that the user could opt to restart, go back to main menu, or exit the program from both the win and loss screens.
  - added the writeScoreToFile method, that takes the score as a parameter in Main.java after the player wins, to write their score to the score file.

- Block.java
  - ammended logic to ensure that collisions were being detected properly
  - added logic to improve collision detection, which currently only somewhat works:
    - the ball is no longer able to bounce in the same direction that it is currently moving in, which could happen when the ball was approaching the corner of a block.
    - logic to prevent the ball from bouncing off of the 'seam' between two touching blocks was implemented, but not functioning as intended as the error still persists.

- LoadSave.java
  -added a catch statement for the situation that the user attempts to use the load save feature without having anything saved in the save file.

- GameEngine.java
  - majorly refactored this class due to the prevalent use of threads within it, which was associated with many errors that came with the initial game code. Again, threads are beyond my understanding, so I opted to remove them entirely and redo the functionality of the class using the java animation library, specifically AnimationTimer.
  - particularly, use of '.stop()' for the threads was problematic as it did not care whether the thread was mid-execution when being force stopped.
  - initially, frames per second, or 'fps', was used to calculate elapsed time in milliseconds. I changed this mechanism, and instead calculate elapsed time by constantly checking the systems time in milliseconds.



Unexpected Problems
-
- an unexpected issue that I was unable to fix is that the ball, after being loaded via loadGame, would appear in a different x coordinate that it was supposed to, to the left of the original position of the ball when saved.
  - i inspected the load game method and the values of the balls x-coordinates when being initalized, but I could not find the cause of the error, and so it remains in the program.
- an unexpected issue I often got at the beginning of my work on these files was that the ball would freeze in place, but the blocks were still observed to be breaking. Until now, I do not know exactly what caused the issue, but I suspect it to have been the threading of the game engine class, as the ball stopped randomly freezing after I refactored the game engine.
- an unexpected issue I got after refactoring the game engine was that the gold time for the ball would be permanent, and it would stay golden after becoming golden until the next level.
  - I solved this issue by changing the onTime method in my main class, which, with the previous engine, had been set 'this.time = time;', but needed to be ammended to 'this.time += time;', since I found through testing with outputs that time was constantly at 0 throughout the entire runtime and therefore the condition to ending gold time was never met. 
- an unexpected issue I had after getting the save functionality and load game functionality working was that the loaded game would sometimes end the level prematurely, without destroying all the blocks, or not end the level after destroying all the blocks.
  - the issue was solved by investigating loadGame using output statements to test the values for total destroyed blocks and total blocks. I found that the total blocks from loadGame was the same as the number of blocks not destroyed, causing an issue since for example, if a level initially had 8 blocks, and in the loaded game, it had 6 blocks, the destroyed blocks count would be at 2, and the total block count would be at 6, meaning the level would progress after just 4 of 6 blocks had been destroyed.
    - i changed the value of destroyed block count to 0 when being initialized from loadGame to solve this issue.
- an unexpected issue I had was one that i encountered farther along into game development, which was that the heart counter would instantly decrease to 0 after the ball would hit the bottom wall of the window at a near 90-degree angle.
  - i solved this issue by adding a 'resetCollideFlags();' statement in the code at what is now line 397 in the main class. It being missing meant that the ball would not bounce properly and maintain contact with the surface, causing the heart counter to rapidily decrement according to passing game time.