# COMP2042_CW_efymf5

welcome to brick breaker! made with javaFX

changes:

fixed spelling errors in variable names:

-renamed all colides to collides

-renamed all stauts to status

-renamed scenHeigt to sceneHeight


refactored class GameEngine to no longer use threads, now uses inbuilt javaFX library function AnimationTimer to keep track of time

Added a pause feature in class Main.java

refactored Block.java to take into account ball radius

refactored Score.java in order to no longer use threads

refactored Main class to make collisions with bottom wall work properly (added resetCollideFlags(); to if statement where ball hits bottom wall)

refactored block class to prevent balls from bouncing in the same direction that they are already moving in 


