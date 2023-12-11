package brickGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Score {
    public void show(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        main.root.getChildren().add(label);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        label.setScaleX(label.getScaleX() + 1);
                        label.setScaleY(label.getScaleY() + 1);
                        label.setOpacity(label.getOpacity() - 1 / 20.0);
                    }
                }),
                new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        main.root.getChildren().remove(label);
                    }
                })
        );

        timeline.setCycleCount(20);
        timeline.play();
    }

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        main.root.getChildren().add(label);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        label.setScaleX(Math.abs(label.getScaleX() - 3));
                        label.setScaleY(Math.abs(label.getScaleY() - 3));
                        label.setOpacity(label.getOpacity() - 1 / 20.0);
                    }
                }),
                new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        main.root.getChildren().remove(label);
                    }
                })
        );

        timeline.setCycleCount(20);
        timeline.play();
    }

    public void showGameOver(final Main main) {
        Label label = new Label("Game Over :(");
        label.setTranslateX(200);
        label.setTranslateY(250);
        label.setScaleX(2);
        label.setScaleY(2);

        Button restart = new Button("Restart");
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.restartGame();
            }
        });

        Button backToMenu = new Button("go back to main menu");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.initializeGame();
                main.showMenu();
            }
        });

        Button exit = new Button("exit game");

        exit.setOnAction(e -> {
            Platform.exit();
        });

        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(restart, backToMenu, exit);
        buttons.setTranslateX(main.sceneWidth/2-68 - buttons.getWidth()/2);
        buttons.setTranslateY(main.sceneHeight/2 - buttons.getHeight()/2);

        main.root.getChildren().addAll(label, buttons);
    }

    public void showWin(final Main main) {
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);

        Label label = new Label("You Win :)");
        label.setTranslateX(200);
        label.setTranslateY(250);
        label.setScaleX(2);
        label.setScaleY(2);

        Button restart = new Button("restart");
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.restartGame();
            }
        });

        Button backToMenu = new Button("go back to main menu");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.initializeGame();
                main.showMenu();
            }

        });

        Button exit = new Button("exit game");

        exit.setOnAction(e -> {
            Platform.exit();
        });

        //VBox buttons = new VBox(20, restart, backToMenu);

        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(restart, backToMenu, exit);
        buttons.setTranslateX(main.sceneWidth/2-68 - buttons.getWidth()/2);
        buttons.setTranslateY(main.sceneHeight/2 - buttons.getHeight()/2);

        main.root.getChildren().addAll(label, buttons);
    }
}