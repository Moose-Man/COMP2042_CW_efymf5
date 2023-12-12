package brickGame;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * this class creates the main menu window and manages the events on button (image view) click
 */
public class Menu {

    private Main game;
    private Stage primaryStage;

    /**
     * creates an instance of the menu
     * @param game
     * @param stage
     */
    public Menu(Main game, Stage stage) {
        this.game = game;
        this.primaryStage = stage;
    }

    /**
     * this method the menu button images and their click events, and the menu background
     */
    public void showMenu(){
        // Create image views
        ImageView newGameImage = new ImageView(new Image("start-new-game.png"));
        ImageView loadGameImage = new ImageView(new Image("LoadGame.png"));
        ImageView exitImage = new ImageView(new Image("Exit.png"));
        ImageView scoreBoardImage = new ImageView(new Image("ScoreBoard.png"));


        // Set images' fit width and height
        newGameImage.setFitWidth(300);
        newGameImage.setFitHeight(130);
        loadGameImage.setFitWidth(300);
        loadGameImage.setFitHeight(130);
        exitImage.setFitWidth(300);
        exitImage.setFitHeight(130);
        scoreBoardImage.setFitWidth(300);
        scoreBoardImage.setFitHeight(130);

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

        scoreBoardImage.setOnMouseClicked(e -> {
            showScoreBoard();
        });

        // Layout
        VBox menuLayout = new VBox(newGameImage, loadGameImage, exitImage, scoreBoardImage);
        menuLayout.getStyleClass().add("vbox");
        menuLayout.setId("menuRoot");
        menuLayout.setAlignment(Pos.CENTER);

        // Create scene
        Scene menuScene = new Scene(menuLayout, game.sceneWidth, game.sceneHeight);
        menuScene.getStylesheets().add("style.css");

        // Show menu
        primaryStage.setScene(menuScene);
        primaryStage.show();

    }

    /**
     * this method creates a dialogue box displaying the scores the user had on wins
     */
    private void showScoreBoard() {
        StringBuilder scores = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("C:/save/scores.mdds"))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display scores in a dialog box
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score Board");
        alert.setHeaderText(null);
        alert.setContentText("Scores:\n" + scores.toString());

        TextArea textArea = new TextArea(scores.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }
}