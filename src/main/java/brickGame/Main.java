package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    public int level = 0;
    private double xPaddle = 0.0f;
    private double centerBreakX;
    private double yPaddle = 640.0f;
    private final int breakWidth     = 130;
    private final int breakHeight    = 30;
    private final int halfBreakWidth = breakWidth / 2;
    public final int sceneWidth = 500;
    public final int sceneHeight = 700;
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;
    private Circle ball;
    private double xBall;
    private double yBall;
    private double xBallDirection;
    private double yBallDirection;
    private boolean paused = false;
    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;
    private Rectangle rect;
    private final int ballRadius = 15;
    private int destroyedBlockCount = 0;
    private final double v = 1.000;
    public int  heart    = 3;
    public int  score    = 0;
    public long time     = 0;
    public long goldTime = 0;
    private GameEngine engine;
    public static String savePath    = "C:/save/save.mdds";
    public static String savePathDir = "C:/save/";
    private final ArrayList<Block> blocks = new ArrayList<Block>();
    private final ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    private final Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };
    public  Pane             root;
    public Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    public boolean loadFromSave = false;

    Stage  primaryStage;

    private boolean menuShown = false;

    public void initializeGame() {
        heart = 3;
        score = 0;
        level = 0;
        xPaddle = 0.0f;
        yPaddle = 640.0f;
        destroyedBlockCount = 0;
        centerBreakX = 0;
        xBall = 200;
        yBall = 350;
        xBallDirection = 0;
        yBallDirection = 0;
        paused = false;
        isGoldStatus = false;
        isExistHeartBlock = false;
        ball = null;
        rect = null;
        goDownBall = true;
        goRightBall = true;
        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;
        collideToRightBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
        collideToBottomBlock = false;
        vX = 3.000;
        vY = 3.000;
        resetCollideFlags();
        time = 0;
        goldTime = 0;
        blocks.clear();
        chocos.clear();
        menuShown = false;
    }

    public synchronized void showMenu() {
        Menu menu = new Menu(this, primaryStage);
        menu.showMenu();
        menuShown = true;
    }

    private void loadGameState() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();

        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStatus;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        collideToBreak = loadSave.collideToBreak;
        collideToBreakAndMoveToRight = loadSave.collideToBreakAndMoveToRight;
        collideToRightWall = loadSave.collideToRightWall;
        collideToLeftWall = loadSave.collideToLeftWall;
        collideToRightBlock = loadSave.collideToRightBlock;
        collideToBottomBlock = loadSave.collideToBottomBlock;
        collideToLeftBlock = loadSave.collideToLeftBlock;
        collideToTopBlock = loadSave.collideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = 0;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xPaddle = loadSave.xBreak;
        yPaddle = loadSave.yBreak;
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

        blocks.clear();
        chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        if (!menuShown) {
            showMenu();
            return;
        }

        if (!loadFromSave) {
            level++;

            initBoard();
            if (level > 1 && level != 2) {
                Platform.runLater(() -> new Score().showMessage("Level Up :)", this));
            }
            if (level == 2) {
                new Score().showWin(this);
                new Score().writeScoreToFile(score);
                return;
            }
        }

        initBall();
        paddle();

        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);

        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        engine = new GameEngine();
        engine.setOnAction(this);
        engine.start();

        if (loadFromSave) {
            loadFromSave = false;
        }
    }

    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            case DOWN:
                break;
            case S:
                saveGame();
                break;
            case P:
                if(!paused) {
                    paused = true;
                    System.out.println("paused");
                    engine.stop();
                    break;
                } else {
                    paused = false;
                    System.out.println("unpaused");
                    engine.start();
                    break;
                }


        }
    }

    private void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xPaddle == (sceneWidth - breakWidth) && direction == RIGHT) {
                        return;
                    }
                    if (xPaddle == 0 && direction == LEFT) {
                        return;
                    }
                    if (direction == RIGHT) {
                        xPaddle++;
                    } else {
                        xPaddle--;
                    }
                    centerBreakX = xPaddle + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }


    private void initBall() {
        //Random random = new Random();
        xBall = 200;
        yBall = 350;
        ball = new Circle();
        ball.setRadius(ballRadius);

        ball.setFill(new ImagePattern(new Image("shuriken.png")));

        // Create a rotation animation for the ball
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), ball);
        rotateTransition.setByAngle(360); // Rotate 360 degrees
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotateTransition.play();
    }

    private void paddle() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xPaddle);
        rect.setY(yPaddle);

        ImagePattern pattern = new ImagePattern(new Image("block.png"));

        rect.setFill(pattern);
    }


    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private boolean collideToBreak              = false;
    private boolean collideToBreakAndMoveToRight = true;
    private boolean collideToRightWall          = false;
    private boolean collideToLeftWall           = false;
    private boolean collideToRightBlock         = false;
    private boolean collideToBottomBlock        = false;
    private boolean collideToLeftBlock          = false;
    private boolean collideToTopBlock           = false;

    private double vX = 3.000;
    private double vY = 3.000;


    private void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    private void setPhysicsToBall() {
        if (goDownBall) {
            yBall += vY;
            yBallDirection = -1;
        } else {
            yBall -= vY;
            yBallDirection = 1;
        }

        if (goRightBall) {
            xBall += vX;
            xBallDirection = 1;
        } else {
            xBall -= vX;
            xBallDirection = -1;
        }

        if (yBall-ballRadius <= 0) {
            resetCollideFlags();
            goDownBall = true;
            return;
        }

        if (yBall+ballRadius >= sceneHeight) {
            goDownBall = false;
            resetCollideFlags();
            if (!isGoldStatus) {
                heart--;
                System.out.println("total hearts now:"+heart);
                new Score().show((double) sceneWidth / 2, (double) sceneHeight / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }

            }
        }

        if (yBall >= yPaddle - ballRadius) {
            if (xBall >= xPaddle && xBall <= xPaddle + breakWidth) {
                resetCollideFlags();
                collideToBreak = true;
                goDownBall = false;

                double relation = (xBall - centerBreakX) / ((double) breakWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                }

                collideToBreakAndMoveToRight = (xBall + ballRadius - centerBreakX > 0);
            }
        }

        if (xBall+ballRadius >= sceneWidth) {
            resetCollideFlags();
            collideToRightWall = true;
        }

        if (xBall-ballRadius <= 0) {
            resetCollideFlags();
            collideToLeftWall = true;
        }

        if (collideToBreak) {
            goRightBall = collideToBreakAndMoveToRight;
        }

        //Wall Collide

        if (collideToRightWall) {
            goRightBall = false;
        }

        if (collideToLeftWall) {
            goRightBall = true;
        }

        //Block Collide

        if (collideToRightBlock) {
            goRightBall = true;
        }

        if (collideToLeftBlock) {
            goRightBall = false;
        }

        if (collideToTopBlock) {
            goDownBall = false;
        }

        if (collideToBottomBlock) {
            goDownBall = true;
        }


    }


    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            nextLevel();
        }
    }

    private void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(heart);
                    outputStream.writeInt(destroyedBlockCount);


                    outputStream.writeDouble(xBall);
                    outputStream.writeDouble(yBall);
                    outputStream.writeDouble(xPaddle);
                    outputStream.writeDouble(yPaddle);
                    outputStream.writeDouble(centerBreakX);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(vX);


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStatus);
                    outputStream.writeBoolean(goDownBall);
                    outputStream.writeBoolean(goRightBall);
                    outputStream.writeBoolean(collideToBreak);
                    outputStream.writeBoolean(collideToBreakAndMoveToRight);
                    outputStream.writeBoolean(collideToRightWall);
                    outputStream.writeBoolean(collideToLeftWall);
                    outputStream.writeBoolean(collideToRightBlock);
                    outputStream.writeBoolean(collideToBottomBlock);
                    outputStream.writeBoolean(collideToLeftBlock);
                    outputStream.writeBoolean(collideToTopBlock);

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    Platform.runLater(() -> new Score().showMessage("Game Saved", Main.this));


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        assert outputStream != null;
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void loadGame() {

        loadGameState();

        try {
            loadFromSave = true;
            start(primaryStage);
            if (isGoldStatus){
                root.getStyleClass().add("goldRoot");
                ball.setFill(new ImagePattern(new Image("goldShuriken.png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nextLevel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    vX = 2.000;

                    engine.stop();
                    resetCollideFlags();
                    goDownBall = true;

                    isGoldStatus = false;
                    isExistHeartBlock = false;


                    time = 0;
                    goldTime = 0;

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void restartGame() {

        try {
            level = 0;
            heart = 3;
            score = 0;
            destroyedBlockCount = 0;
            resetCollideFlags();
            goDownBall = true;

            isGoldStatus = false;
            isExistHeartBlock = false;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                rect.setX(xPaddle);
                rect.setY(yPaddle);
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall, ballRadius, xBallDirection, yBallDirection);
                if (hitCode != Block.NO_HIT) {
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    resetCollideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(choco.choco);
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldShuriken.png")));
                        System.out.println("gold shuriken");
                        root.getStyleClass().add("goldRoot");
                        isGoldStatus = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        System.out.println("Collide to right block");
                        System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                        collideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        System.out.println("Collide to bottom block");
                        System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                        collideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        System.out.println("Collide to left block");
                        System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                        collideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        System.out.println("Collide to top block"); //test code to view what collision type is done
                        System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                        collideToTopBlock = true;
                    }

                }
            }
        }

    }


    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();


        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("shuriken.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > sceneHeight || choco.taken) {
                continue;
            }
            if (choco.y >= yPaddle && choco.y <= yPaddle + breakHeight && choco.x >= xPaddle && choco.x <= xPaddle + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
    }


    @Override
    public void onTime(long time) {
        this.time += time;
    }
}