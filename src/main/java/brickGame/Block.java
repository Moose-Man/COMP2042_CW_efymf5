package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * this class is for generating blocks and managing block-ball collision
 */
public class Block implements Serializable {

    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;


    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;

    /**
     * this method initializes the blocks and their locations in a 2d grid
     * @param row the row of the block
     * @param column the column of the block
     * @param color the color f the block
     * @param type the type of block it is
     */
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    /**
     * creates visual representation of the block grid
     */
    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }

    }

    private double prevBlockRight;
    private double prevBlockLeft;
    private double prevBlockTop;
    private double prevBlockBottom;

    /**
     * This method is used to determine if the ball hits a block, and which side of the block the ball has collided with
     * @param xBall x coordinate of the ball
     * @param yBall y coordinate of the ball
     * @param ballRadius the balls raidus
     * @param xBallDirection the x direction of the ball
     * @param yBallDirection the y direction of the ball
     * @return exits function with the collision type
     */
    public int checkHitToBlock(double xBall, double yBall, double ballRadius, double xBallDirection, double yBallDirection) {
        if (isDestroyed) {
            return NO_HIT;
        }

        double blockRight = x + width;
        double blockLeft = x;
        double blockTop = y;
        double blockBottom = y + height;

        if (xBall + ballRadius >= blockLeft && xBall - ballRadius <= blockRight &&
                yBall + ballRadius >= blockTop && yBall - ballRadius <= blockBottom) {
            // Ball is within the bounds of the block

            if (xBall + ballRadius >= blockLeft && xBall - ballRadius <= blockLeft && xBall <= blockLeft
                    && xBallDirection == 1 && blockLeft != prevBlockRight) {
                // Ball hits the left side of the block
                return HIT_LEFT;
            } else if (xBall - ballRadius <= blockRight && xBall + ballRadius >= blockRight && xBall >= blockRight
                            && xBallDirection == -1 && blockRight != prevBlockLeft) {
                // Ball hits the right side of the block
                return HIT_RIGHT;
            }

            if (yBall + ballRadius >= blockTop && yBall - ballRadius <= blockTop && yBallDirection == -1
                    && blockTop != prevBlockBottom) {
                // Ball hits the top side of the block
                return HIT_TOP;
            } else if (yBall - ballRadius <= blockBottom && yBall + ballRadius >= blockBottom && yBallDirection == 1
                            && blockBottom != prevBlockTop) {
                // Ball hits the bottom side of the block
                return HIT_BOTTOM;
            }
        }
        prevBlockRight = blockRight;
        prevBlockLeft = blockLeft;
        prevBlockTop = blockTop;
        prevBlockBottom = blockBottom;

        return NO_HIT;
    }

    /**
     * gets the padding for the top of the block
     * @return returns the padding for the top of the block
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }

    /**
     * gets the horizontal padding of the block
     * @return returns the horizontal padding of the block
     */
    public static int getPaddingH() {
        return block.paddingH;
    }

    /**
     * gets block height
     * @return returns block height
     */
    public static int getHeight() {
        return block.height;
    }

    /**
     * gets block height
     * @return returns block height
     */
    public static int getWidth() {
        return block.width;
    }

}
