package brickGame;

import javafx.animation.AnimationTimer;

/**
 * this class keeps track of time and allows for constant checking of program state
 */
public class GameEngine {

    private long currTime = 0;
    private OnAction onAction;
    private AnimationTimer timer;
    private long TimeOfLastUpdate = 0;

    /**
     * updates onAction's value
     * @param onAction
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * initializes and starts timer
     */
    public void start() {
        TimeOfLastUpdate = System.nanoTime();
        onAction.onInit();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedNanoSeconds = now - TimeOfLastUpdate;
                long elapsedMilliSeconds = (long) (elapsedNanoSeconds / 1_000_000.0);

                onAction.onUpdate();
                onAction.onPhysicsUpdate();

                TimeOfLastUpdate = now;
                currTime = elapsedMilliSeconds;
                onAction.onTime(currTime);

            }
        };
        timer.start();
    }

    /**
     * stops timer
     */
    public void stop() {
        timer.stop();
    }

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}