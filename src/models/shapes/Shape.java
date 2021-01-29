package models.shapes;

import models.Block;
import models.Moves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// Represents a generic Shape made up of blocks
public abstract class Shape implements Iterable<Block> {
    protected List<Block> blocks;
    String color;
    Direction direction;
    Block b1;
    Block b2;
    Block b3;
    Block b4;

    // EFFECTS: initializes a shape with default direction
    public Shape() {
        blocks = new ArrayList<>();
        direction = Direction.DEFAULT;
    }

    /**
     * Initializes inner 4 blocks
     * @param row1 b1 row position
     * @param col1 b1 column position
     * @param row2 b2 row position
     * @param col2 b2 column position
     * @param row3 b3 row position
     * @param col3 b3 column position
     * @param row4 b4 row position
     * @param col4 b4 column position
     */
    public void initializeBlocks(int row1, int col1, int row2, int col2,
                                 int row3, int col3, int row4, int col4) {
        this.b1 = new Block(col1,row1, color);
        this.b2 = new Block(col2,row2, color);
        this.b3 = new Block(col3,row3, color);
        this.b4 = new Block(col4,row4, color);
        blocks.addAll(Arrays.asList(b1, b2, b3, b4));
    }


    public abstract void rotate(List<String> filledSquares);

    /**
     * Checks if each block can rotate safely, if so rotates them appropriately.
     * Numbers correspond to blocks (row1 and col1 represent row and column of b1).
     * @param filledSquares list of string positions of filled blocks
     * @param row1 b1 row position
     * @param col1 b1 column position
     * @param row2 b2 row position
     * @param col2 b2 column position
     * @param row3 b3 row position
     * @param col3 b3 column position
     * @param row4 b4 row position
     * @param col4 b4 column position
     */
    protected void rotateInnerBlocks(List<String> filledSquares,
                                     int row1, int col1, int row2, int col2,
                                     int row3, int col3, int row4, int col4) {
        Boolean canMoveB1 =  b1.canMove(filledSquares, row1, col1);
        Boolean canMoveB2 = b2.canMove(filledSquares, row2, col2);
        Boolean canMoveB3 = b3.canMove(filledSquares, row3, col3);
        Boolean canMoveB4 = b4.canMove(filledSquares, row4, col4);

        if (canMoveB1 && canMoveB2 && canMoveB3 && canMoveB4) {
            b1.move(row1, col1);
            b2.move(row2, col2);
            b3.move(row3, col3);
            b4.move(row4, col4);
        }
    }

    /**
     *
     * @param filledSquares list of string positions of filled blocks
     * @param move Possible move type
     * @return true if all blocks in shape can move without collision
     */
    public Boolean move(List<String> filledSquares, Moves move) {
        switch (move) {
            case DOWN:
                if (canMoveDown(filledSquares)) {
                    for (Block block: blocks) {
                        block.moveDown();
                    }
                    return true;
                }
                return false;

            case LEFT:
                if (canMoveLeft(filledSquares)) {
                    for (Block block: blocks) {
                        block.moveLeft();
                    }
                    return true;
                }
                return false;

            case RIGHT:
                if (canMoveRight(filledSquares)) {
                    for (Block block: blocks) {
                        block.moveRight();
                    }
                    return true;
                }
                return false;

            default:
                throw new RuntimeException();
        }
    }

    /**
     *
     * @param filledSquares list of string positions of filled blocks
     * @param rowMove units to move in row space
     * @param columnMove units to move in column space
     * @return true if shape can move
     */
    // EFFECTS: checks if shape can move without colliding
    private Boolean canMove(List<String> filledSquares, int rowMove, int columnMove) {
        for (Block block: blocks) {
            if (!block.canMove(filledSquares, rowMove, columnMove)) {
                return false;
            }
        }
        return true;
    }

    public Boolean canMoveDown(List<String> filledSquares) {
        return canMove(filledSquares, 1, 0);
    }

    private Boolean canMoveLeft(List<String> filledSquares) {
        return canMove(filledSquares, 0, -1);
    }

    private Boolean canMoveRight(List<String> filledSquares) {
        return canMove(filledSquares, 0, 1);
    }

    @Override
    public Iterator<Block> iterator() {
        return blocks.iterator();
    }
}
