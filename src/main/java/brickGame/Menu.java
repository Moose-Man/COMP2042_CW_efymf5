package brickGame;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {

    private Main game;
    private Stage primaryStage;

    public Menu(Main game, Stage stage) {
        this.game = game;
        this.primaryStage = stage;
    }

    public void showMenu(){

        // Create buttons
        Button newGameBtn = new Button("New Game");
        Button loadGameBtn = new Button ("Load Game");
        Button exitBtn = new Button("Exit");

        // Set button actions
        newGameBtn.setOnAction(e -> {
            try {
                game.start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        loadGameBtn.setOnAction(e -> {
            game.loadGame();
        });

        exitBtn.setOnAction(e -> {
            Platform.exit();
        });

        // Layout
        VBox menuLayout = new VBox(20, newGameBtn, loadGameBtn, exitBtn);

        // Create scene
        Scene menuScene = new Scene(menuLayout, game.sceneWidth, game.sceneHeight);

        // Show menu
        primaryStage.setScene(menuScene);
        primaryStage.show();

    }
}
//package brickGame;
//
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.image.Image;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundImage;
//import javafx.scene.layout.BackgroundPosition;
//import javafx.scene.layout.BackgroundRepeat;
//import javafx.scene.layout.BackgroundSize;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Polygon;
//import javafx.stage.Stage;
//import javafx.scene.image.ImageView;
//import java.nio.file.Paths;
//
//public class Menu extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        // Set up the root node (StackPane)
//        StackPane root = new StackPane();
//        root.setPrefSize(500, 700);
//
//        // Load the custom background image
//        Image backgroundImage = new Image("bg.jpg");
//
//        // Create a background with the image
//        BackgroundImage background = new BackgroundImage(
//                backgroundImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,
//                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
//        );
//
//        // Set the background to the root node
//        root.setBackground(new Background(background));
//
//        // Create custom hexagonal button
//        HexagonalButton startButton = new HexagonalButton("Start","ball.png");
//        startButton.setOnMouseClicked(e -> {
//            // Handle the action when the "Start" button is clicked
//            System.out.println( "Start button clicked at: (" + e.getX() + ", " + e.getY() + ")");
//            try {
//                // Create an instance of the Main class and call its start method
//                Main mainGame = new Main();
//                mainGame.start(primaryStage);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        HexagonalButton exitButton = new HexagonalButton("Exit","goldball.png");
//        exitButton.setOnMouseClicked(e -> {
//            // Handle the action when the "Exit" button is clicked
//            System.out.println("Exiting game");
//            primaryStage.close();
//            System.exit(0); // Terminate the program
//        });
//
//        // Add buttons to the root node
//        root.getChildren().addAll(startButton, exitButton);
//
//        // Set the layout for the buttons
//        StackPane.setMargin(startButton, new Insets(200, 0, 0, 50));
//        StackPane.setMargin(exitButton, new Insets(350, 300, 0, 200));
//
//        // Create the scene
//        Scene scene = new Scene(root);
//
//        // Set the stage properties
//        primaryStage.setTitle("Game Main Menu");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//
//
//    }
//
//
//    // Custom HexagonalButton class
//    private static class HexagonalButton extends StackPane {
//
//        private static final double HEXAGON_RADIUS = 130.0;
//
//        public HexagonalButton(String text, String imagePath) {
//            // Create an ImageView with the specified image
//            Image buttonImage = new Image(imagePath);
//            ImageView imageView = new ImageView(buttonImage);
//            double buttonWidth = 100; // Set your desired width
//            double buttonHeight = 100; // Set your desired height
//
//            imageView.setFitWidth(buttonWidth);
//            imageView.setFitHeight(buttonHeight);
//
//
//            // Add the ImageView to the HexagonalButton
//            getChildren().addAll(imageView);
//        }
//    }
//
//}
