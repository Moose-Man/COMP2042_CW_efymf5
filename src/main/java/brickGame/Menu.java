package brickGame;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Menu {

    private Main game;
    private Stage primaryStage;

    public Menu(Main game, Stage stage) {
        this.game = game;
        this.primaryStage = stage;
    }

    public void showMenu(){
        // Create image views
        ImageView newGameImage = new ImageView(new Image("start-new-game.png"));
        ImageView loadGameImage = new ImageView(new Image("LoadGame.png"));
        ImageView exitImage = new ImageView(new Image("Exit.png"));

        // Set images' fit width and height
        newGameImage.setFitWidth(300);
        newGameImage.setFitHeight(130);
        loadGameImage.setFitWidth(300);
        loadGameImage.setFitHeight(130);
        exitImage.setFitWidth(300);
        exitImage.setFitHeight(130);

        // Set image view actions
        newGameImage.setOnMouseClicked(e -> {
            try {
                game.start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        loadGameImage.setOnMouseClicked(e -> {
            game.loadGame();
        });

        exitImage.setOnMouseClicked(e -> {
            Platform.exit();
        });

        // Layout
        VBox menuLayout = new VBox(20, newGameImage, loadGameImage, exitImage);
        menuLayout.getStyleClass().add("vbox");
        menuLayout.setId("menuRoot"); // set id
        menuLayout.setAlignment(Pos.CENTER);

        // Create scene
        Scene menuScene = new Scene(menuLayout, game.sceneWidth, game.sceneHeight);
        menuScene.getStylesheets().add("style.css");

        // Show menu
        primaryStage.setScene(menuScene);
        primaryStage.show();

    }
}