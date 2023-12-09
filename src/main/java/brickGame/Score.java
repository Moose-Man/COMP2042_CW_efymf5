package brickGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
                        label.setScaleX(Math.abs(label.getScaleX() - 10));
                        label.setScaleY(Math.abs(label.getScaleY() - 10));
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
        restart.setTranslateX(220);
        restart.setTranslateY(300);
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.restartGame();
            }
        });

        main.root.getChildren().addAll(label, restart);
    }

    public void showWin(final Main main) {
        Label label = new Label("You Win :)");
        label.setTranslateX(200);
        label.setTranslateY(250);
        label.setScaleX(2);
        label.setScaleY(2);

        main.root.getChildren().addAll(label);
    }
}