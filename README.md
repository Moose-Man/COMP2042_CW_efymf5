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

refactored main to make the load save button visible to the user

changed save directory to my C drive, since I dont have a D drive (change if necessary for you)

added load save functionality so that user can load their previous save which they make when they press S

added Main Menu to program
- removed previous buttons, program is totally reliant on Main Menu buttons now
- main menu appears on program start up, with start game, load save, and exit buttons, all of which act exactly as you might think

turned ball into a shuriken, and added spinning feature, making it spin instead of staying static

can access main menu from loss and win screens now after refactoring Score.java to accommodate more 

can restart from win screen now

buttons have all been centered whenever they appear
