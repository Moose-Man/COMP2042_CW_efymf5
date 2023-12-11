package brickGame;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;


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
        menuLayout.setAlignment(Pos.CENTER);
        // Create scene
        Scene menuScene = new Scene(menuLayout, game.sceneWidth, game.sceneHeight);

        // Show menu
        primaryStage.setScene(menuScene);
        primaryStage.show();

    }
}